/*******************************************************************************
 * Copyright (c) 2011, 2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *     Dawid Pakuła - convert to JUnit4
 *******************************************************************************/
package org.eclipse.php.ui.tests.formatter.autoedit;

import static org.junit.Assert.assertTrue;

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DocumentCommand;
import org.eclipse.jface.text.IAutoEditStrategy;
import org.eclipse.jface.text.IDocument;
import org.eclipse.php.core.tests.PDTTUtils;
import org.eclipse.php.core.tests.PdttFile;
import org.eclipse.php.core.tests.TestSuiteWatcher;
import org.eclipse.php.core.tests.TestUtils;
import org.eclipse.php.core.tests.runner.AbstractPDTTRunner.Context;
import org.eclipse.php.core.tests.runner.PDTTList;
import org.eclipse.php.core.tests.runner.PDTTList.AfterList;
import org.eclipse.php.core.tests.runner.PDTTList.BeforeList;
import org.eclipse.php.core.tests.runner.PDTTList.Parameters;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.internal.ui.autoEdit.MainAutoEditStrategy;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.php.ui.tests.PHPTestEditor;
import org.eclipse.php.ui.tests.PHPUiTests;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.junit.After;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.RunWith;
import org.osgi.framework.Bundle;

@RunWith(PDTTList.class)
public class FormatterAutoEditTests {

	@ClassRule
	public static TestWatcher watcher = new TestSuiteWatcher();

	protected static final String DEFAULT_CURSOR = "|";

	protected IProject project;
	protected IFile testFile;
	protected int count;

	protected PHPVersion phpVersion;
	protected String[] fileNames;
	protected PHPStructuredEditor editor;

	@Parameters
	public static final Map<PHPVersion, String[]> TESTS = new LinkedHashMap<PHPVersion, String[]>();

	static {
		TESTS.put(PHPVersion.PHP5,
				new String[] { "/workspace/formatter-autoedit", "/workspace/phpdoc-generation/php5" });
		TESTS.put(PHPVersion.PHP5_3, new String[] { "/workspace/formatter-autoedit",
				"/workspace/phpdoc-generation/php5", "/workspace/phpdoc-generation/php53" });
		TESTS.put(PHPVersion.PHP7_0,
				new String[] { "/workspace/formatter-autoedit", "/workspace/phpdoc-generation/php5",
						"/workspace/phpdoc-generation/php53", "/workspace/phpdoc-generation/php7" });
		TESTS.put(PHPVersion.PHP7_1,
				new String[] { "/workspace/formatter-autoedit", "/workspace/phpdoc-generation/php5",
						"/workspace/phpdoc-generation/php53", "/workspace/phpdoc-generation/php7",
						"/workspace/phpdoc-generation/php71" });
	};

	public FormatterAutoEditTests(PHPVersion version, String[] fileNames) {
		this.phpVersion = version;
		this.fileNames = fileNames;
	}

	@BeforeList
	public void setUpSuite() throws Exception {
		project = TestUtils.createProject("FormatterTests" + phpVersion.name());
		TestUtils.setProjectPhpVersion(project, phpVersion);
	}

	@After
	public void after() throws Exception {
		if (testFile != null) {
			TestUtils.deleteFile(testFile);
		}
	}

	@AfterList
	public void tearDownSuite() throws Exception {
		TestUtils.deleteProject(project);
	}

	@Context
	public static Bundle getBundle() {
		return PHPUiTests.getDefault().getBundle();
	}

	private static String getCursor(PdttFile pdttFile) {
		Map<String, String> config = pdttFile.getConfig();
		return config.get("cursor");
	}

	@Test
	public void formatter(String fileName) throws Exception {
		final PdttFile pdttFile = new PdttFile(PHPUiTests.getDefault().getBundle(), fileName);
		final String cursor = getCursor(pdttFile) != null ? getCursor(pdttFile) : DEFAULT_CURSOR;
		final DocumentCommand cmd = createFile(pdttFile.getFile().trim(), cursor);
		final Exception[] err = new Exception[1];
		final IDocument document = StructuredModelManager.getModelManager().getModelForRead(testFile)
				.getStructuredDocument();
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				try {
					openEditor();
					format(pdttFile, cmd, document);
					closeEditor();
				} catch (Exception e) {
					err[0] = e;
				}
			}
		});
		if (err[0] != null) {
			throw err[0];
		}
		// Compare contents
		PDTTUtils.assertContents(pdttFile.getExpected(), document.get());
	}

	protected DocumentCommand createFile(String data, String cursor) throws Exception {
		int firstOffset = data.indexOf(cursor);
		int lastOffset = data.lastIndexOf(cursor);
		if (lastOffset == -1) {
			throw new IllegalArgumentException("Offset character is not set");
		}
		final DocumentCommand cmd = new DocumentCommand() {
		};
		// replace the offset character(s)
		if (firstOffset == lastOffset) {
			data = data.substring(0, lastOffset) + data.substring(lastOffset + cursor.length());
			cmd.offset = lastOffset;
			cmd.length = 0;
		} else {
			data = data.substring(0, firstOffset) + data.substring(firstOffset + cursor.length(), lastOffset)
					+ data.substring(lastOffset + cursor.length());
			cmd.offset = firstOffset;
			cmd.length = lastOffset - (firstOffset + cursor.length());
		}
		testFile = TestUtils.createFile(project, "test" + (++count) + ".php", data);
		project.getFile("test" + (++count) + ".php");
		// Wait for indexer...
		TestUtils.waitForIndexer();
		return cmd;
	}

	protected void openEditor() throws Exception {
		IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		IWorkbenchPage page = workbenchWindow.getActivePage();
		IEditorInput input = new FileEditorInput(testFile);
		/*
		 * This should take care of testing init, createPartControl,
		 * beginBackgroundOperation, endBackgroundOperation methods
		 */
		IEditorPart part = page.openEditor(input, PHPTestEditor.ID, false);
		if (part instanceof PHPStructuredEditor) {
			editor = (PHPStructuredEditor) part;
		} else {
			assertTrue("Unable to open php editor", false);
		}
	}

	protected void closeEditor() {
		editor.doSave(null);
		editor.getSite().getPage().closeEditor(editor, false);
		editor = null;
	}

	protected void format(final PdttFile pdttFile, final DocumentCommand cmd, final IDocument document)
			throws BadLocationException {
		IAutoEditStrategy indentLineAutoEditStrategy = new MainAutoEditStrategy();
		if (pdttFile.getOther() != null && !pdttFile.getOther().isEmpty()) {
			cmd.text = pdttFile.getOther().substring(0, pdttFile.getOther().length() - 1);
			if (cmd.text != null && cmd.text.trim().length() == 1) {
				// support single (non-blank) character
				// insertion
				cmd.text = cmd.text.trim();
			}
		} else {
			cmd.text = "\n";
		}
		cmd.doit = true;
		cmd.shiftsCaret = true;
		cmd.caretOffset = -1;
		indentLineAutoEditStrategy.customizeDocumentCommand(document, cmd);
		document.replace(cmd.offset, cmd.length, cmd.text);
	}

}
