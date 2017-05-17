/*******************************************************************************
 * Copyright (c) 2009, 2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     William Candillon - initial API and implementation
 *     Dawid Pakuła - convert to JUnit4
 *******************************************************************************/
package org.eclipse.php.ui.tests.actions;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.dltk.internal.ui.editor.EditorUtility;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.tests.PdttFile;
import org.eclipse.php.core.tests.TestSuiteWatcher;
import org.eclipse.php.core.tests.TestUtils;
import org.eclipse.php.core.tests.runner.AbstractPDTTRunner.Context;
import org.eclipse.php.core.tests.runner.PDTTList;
import org.eclipse.php.core.tests.runner.PDTTList.AfterList;
import org.eclipse.php.core.tests.runner.PDTTList.BeforeList;
import org.eclipse.php.core.tests.runner.PDTTList.Parameters;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.actions.OrganizeUseStatementsAction;
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
import org.junit.After;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.RunWith;
import org.osgi.framework.Bundle;

@SuppressWarnings("restriction")
@RunWith(PDTTList.class)
public class OrganizeUseStatementsActionTests {

	@ClassRule
	public static TestWatcher watcher = new TestSuiteWatcher();

	protected IProject project;
	protected IFile testFile;
	protected IFile[] otherFiles = new IFile[0];
	protected PHPVersion phpVersion;
	protected PHPStructuredEditor fEditor;

	@Parameters
	public static final Map<PHPVersion, String[]> TESTS = new LinkedHashMap<PHPVersion, String[]>();

	static {
		TESTS.put(PHPVersion.PHP7_0,
				new String[] { "/workspace/organize-imports/php53", "/workspace/organize-imports/php54" });
	};

	@Context
	public static Bundle getBundle() {
		return PHPUiTests.getDefault().getBundle();
	}

	public OrganizeUseStatementsActionTests(PHPVersion version, String[] fileNames) {
		this.phpVersion = version;
	}

	@BeforeList
	public void setUpSuite() throws Exception {
		project = TestUtils.createProject("Content Assist_" + this.phpVersion);
		// set auto insert to true,if there are only one proposal in the CA,it
		// will insert the proposal,so we can test CA without UI interaction
		DefaultScope.INSTANCE.getNode(PHPUiPlugin.ID).putBoolean(PHPCoreConstants.CODEASSIST_AUTOINSERT, true);
		TestUtils.setProjectPHPVersion(project, phpVersion);
	}

	@AfterList
	public void tearDownSuite() throws Exception {
		TestUtils.deleteProject(project);
	}

	@After
	public void tearDown() throws Exception {
		if (testFile != null) {
			TestUtils.deleteFile(testFile);
		}
		if (otherFiles != null) {
			for (IFile f : otherFiles) {
				TestUtils.deleteFile(f);
			}
			otherFiles = null;
		}
	}

	@Test
	public void assist(String fileName) throws Exception {
		final PdttFile pdttFile = new PdttFile(PHPUiTests.getDefault().getBundle(), fileName);
		pdttFile.applyPreferences();

		final Exception[] err = new Exception[1];
		Display.getDefault().syncExec(new Runnable() {

			@Override
			public void run() {
				try {
					createFile(pdttFile.getFile(), Long.toString(System.currentTimeMillis()),
							prepareOtherStreams(pdttFile));
					openEditor();
					String result = execute();
					closeEditor();
					if (!pdttFile.getExpected().replace("\r", "").trim().equals(result.replace("\r", "").trim())) {
						StringBuilder errorBuf = new StringBuilder();
						errorBuf.append("\nEXPECTED RESULT:\n-----------------------------\n");
						errorBuf.append(pdttFile.getExpected());
						errorBuf.append("\nACTUAL RESULT:\n-----------------------------\n");
						errorBuf.append(result);
						fail(errorBuf.toString());
					}
				} catch (Exception e) {
					err[0] = e;
				}
			}
		});
		if (err[0] != null) {
			throw err[0];
		}
	}

	protected InputStream[] prepareOtherStreams(PdttFile file) {
		String[] contents = file.getOtherFiles();
		InputStream[] result = new InputStream[contents.length];
		for (int i = 0; i < contents.length; i++) {
			result[i] = new ByteArrayInputStream(contents[i].getBytes());
		}

		return result;
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
			fEditor = (PHPStructuredEditor) part;
		} else {
			assertTrue("Unable to open php editor", false);
		}
	}

	protected void closeEditor() {
		fEditor.doSave(null);
		fEditor.getSite().getPage().closeEditor(fEditor, false);
		fEditor = null;
		if (testFile.exists()) {
			TestUtils.deleteFile(testFile);
		}
	}

	protected String execute()
			throws ExecutionException, NotDefinedException, NotEnabledException, NotHandledException {

		if (fEditor instanceof PHPStructuredEditor) {
			OrganizeUseStatementsAction action = new OrganizeUseStatementsAction(fEditor);
			action.run(EditorUtility.getEditorInputModelElement(fEditor, false));
		}

		return fEditor.getDocument().get();
	}

	protected void createFile(String data, String fileName, InputStream[] other) throws Exception {
		testFile = TestUtils.createFile(project,
				new Path(fileName).removeFileExtension().addFileExtension("php").lastSegment(), data);

		otherFiles = new IFile[other.length];
		for (int i = 0; i < other.length; i++) {
			otherFiles[i] = project.getFile(new Path(i + fileName).addFileExtension("php").lastSegment());
			otherFiles[i].create(other[i], true, null);
		}
		// Wait for indexer...
		TestUtils.waitForIndexer();
	}
}
