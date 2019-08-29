/*******************************************************************************
 * Copyright (c) 2017 Alex Xu and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Alex Xu - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.ui.tests.editor;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.tests.PDTTUtils;
import org.eclipse.php.core.tests.PdttFile;
import org.eclipse.php.core.tests.TestSuiteWatcher;
import org.eclipse.php.core.tests.TestUtils;
import org.eclipse.php.core.tests.runner.AbstractPDTTRunner.Context;
import org.eclipse.php.core.tests.runner.PDTTList;
import org.eclipse.php.core.tests.runner.PDTTList.AfterList;
import org.eclipse.php.core.tests.runner.PDTTList.BeforeList;
import org.eclipse.php.core.tests.runner.PDTTList.Parameters;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.php.ui.tests.PHPTestEditor;
import org.eclipse.php.ui.tests.PHPUiTests;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.wst.sse.ui.internal.StructuredTextViewer;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.RunWith;
import org.osgi.framework.Bundle;

@RunWith(PDTTList.class)
public class BracketInserterTests {

	@ClassRule
	public static TestWatcher watcher = new TestSuiteWatcher();

	private IProject project;
	private IFile testFile;
	private PHPVersion phpVersion;
	private PHPStructuredEditor fEditor;

	@Parameters
	public static final Map<PHPVersion, String[]> TESTS = new LinkedHashMap<>();
	public static final String DEFAULT_CURSOR = "|";

	static {
		TESTS.put(PHPVersion.PHP7_1, new String[] { "/workspace/bracket-inserter" });
		TESTS.put(PHPVersion.PHP7_2, new String[] { "/workspace/bracket-inserter" });
		TESTS.put(PHPVersion.PHP7_3, new String[] { "/workspace/bracket-inserter" });
		TESTS.put(PHPVersion.PHP7_4, new String[] { "/workspace/bracket-inserter" });
	};

	@Context
	public static Bundle getBundle() {
		return PHPUiTests.getDefault().getBundle();
	}

	public BracketInserterTests(PHPVersion version, String[] fileNames) {
		this.phpVersion = version;
	}

	@BeforeList
	public void setUpSuite() throws Exception {
		project = TestUtils.createProject("Bracket_Inserter_" + this.phpVersion);
		ResourcesPlugin.getWorkspace().getRoot().getProject("Bracket_Inserter_" + this.phpVersion);
	}

	@AfterList
	public void tearDownSuite() throws Exception {
		TestUtils.deleteProject(project);
	}

	@Test
	public void typing(String fileName) throws Exception {
		final PdttFile pdttFile = new PdttFile(PHPUiTests.getDefault().getBundle(), fileName);
		doTyping(pdttFile);
	}

	private void doTyping(final PdttFile pdttFile) throws Exception {
		final String pdttFileData = pdttFile.getFile();
		final String cursor = getCursor(pdttFile) != null ? getCursor(pdttFile) : DEFAULT_CURSOR;
		final int offset = pdttFileData.lastIndexOf(cursor);
		final String data = pdttFileData.substring(0, offset) + pdttFileData.substring(offset + 1);
		final Exception[] exception = new Exception[1];
		final String[] result = new String[1];
		// Wait for UI
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				try {
					pdttFile.applyPreferences();
					String fileName = Paths.get(pdttFile.getFileName()).getFileName().toString();
					fileName = fileName.substring(0, fileName.indexOf('.')) + ".php";

					char c;
					if (pdttFile.getOther() != null && !pdttFile.getOther().isEmpty()) {
						c = pdttFile.getOther().charAt(0);
					} else {
						c = '\n';
					}

					createFiles(data, fileName);
					openEditor();
					result[0] = executeTyping(offset, c);
					closeEditor();
					deleteFiles();
				} catch (Exception e) {
					exception[0] = e;
				}
			}
		});
		if (exception[0] != null) {
			throw exception[0];
		}
		PDTTUtils.assertContents(pdttFile.getExpected(), result[0]);
	}

	private void deleteFiles() throws Exception {
		if (testFile != null) {
			TestUtils.deleteFile(testFile);
		}
	}

	private String getCursor(PdttFile pdttFile) {
		Map<String, String> config = pdttFile.getConfig();
		return config.get("cursor");
	}

	private void openEditor() throws Exception {
		IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		IWorkbenchPage page = workbenchWindow.getActivePage();
		IEditorInput input = new FileEditorInput(testFile);
		/*
		 * This should take care of testing init, createPartControl,
		 * beginBackgroundOperation, endBackgroundOperation methods
		 */
		IEditorPart part = page.openEditor(input, PHPTestEditor.ID, false);
		if (part instanceof PHPStructuredEditor) {
			fEditor = (PHPStructuredEditor) part;
		} else {
			assertTrue("Unable to open php editor", false);
		}
	}

	private void closeEditor() {
		fEditor.doSave(null);
		fEditor.getSite().getPage().closeEditor(fEditor, false);
		fEditor = null;
	}

	private String executeTyping(int offset, char character) {
		StructuredTextViewer viewer = null;
		Display display = Display.getDefault();
		long timeout = System.currentTimeMillis() + 3000;
		while ((System.currentTimeMillis() < timeout) && ((viewer = fEditor.getTextViewer()) == null)) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		if (viewer == null) {
			fail("fEditor.getTextViewer() returns null for file " + testFile.getFullPath() + "("
					+ testFile.getLocation() + ")");
		}
		StyledText textWidget = viewer.getTextWidget();
		textWidget.setCaretOffset(offset);

		String oldContent = fEditor.getDocument().get();

		Event e = new Event();
		e.character = character;
		e.display = Display.getDefault();
		e.type = SWT.KeyDown;
		e.widget = textWidget;
		VerifyEvent ve = new VerifyEvent(e);

		// Workaround,need to find a better way to test it
		fEditor.getfBracketInserter().verifyKey(ve);

		String newContent = fEditor.getDocument().get();
		if (oldContent.equals(newContent)) {
			// workaround, while the BracketInserter didn't close bracket,
			// insert the character at cursor to get right content
			textWidget.insert(String.valueOf(character));
			return fEditor.getDocument().get();
		}
		return newContent;
	}

	private void createFiles(String content, String fileName) throws Exception {
		testFile = TestUtils.createFile(project, new Path(fileName).lastSegment(), content);
		TestUtils.waitForIndexer();
	}

}
