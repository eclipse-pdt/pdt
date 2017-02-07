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
package org.eclipse.php.ui.tests.contentassist;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.dltk.ui.PreferenceConstants;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.php.core.tests.PDTTUtils;
import org.eclipse.php.core.tests.PdttFile;
import org.eclipse.php.core.tests.TestSuiteWatcher;
import org.eclipse.php.core.tests.TestUtils;
import org.eclipse.php.core.tests.runner.AbstractPDTTRunner.Context;
import org.eclipse.php.core.tests.runner.PDTTList;
import org.eclipse.php.core.tests.runner.PDTTList.AfterList;
import org.eclipse.php.core.tests.runner.PDTTList.BeforeList;
import org.eclipse.php.core.tests.runner.PDTTList.Parameters;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.php.ui.tests.PHPTestEditor;
import org.eclipse.php.ui.tests.PHPUiTests;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Display;
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

@SuppressWarnings("restriction")
@RunWith(PDTTList.class)
public class ContentAssistTests {

	@ClassRule
	public static TestWatcher watcher = new TestSuiteWatcher();

	private IProject project;
	private IFile testFile;
	private IFile[] otherFiles = new IFile[0];
	private PHPVersion phpVersion;
	private PHPStructuredEditor fEditor;

	@Parameters
	public static final Map<PHPVersion, String[]> TESTS = new LinkedHashMap<PHPVersion, String[]>();
	public static final String DEFAULT_CURSOR = "|";

	static {
		TESTS.put(PHPVersion.PHP5, new String[] { "/workspace/codeassist/php5" });
		TESTS.put(PHPVersion.PHP5_3, new String[] { "/workspace/codeassist/php5", "/workspace/codeassist/php53",
				"/workspace/codeassist/php53/classExclusive" });
		TESTS.put(PHPVersion.PHP5_6, new String[] { "/workspace/codeassist/php5", "/workspace/codeassist/php53",
				"/workspace/codeassist/php56" });
		TESTS.put(PHPVersion.PHP7_0, new String[] { "/workspace/codeassist/php5", "/workspace/codeassist/php53",
				"/workspace/codeassist/php56", "/workspace/codeassist/php7" });
		TESTS.put(PHPVersion.PHP7_1, new String[] { "/workspace/codeassist/php5", "/workspace/codeassist/php53",
				"/workspace/codeassist/php56", "/workspace/codeassist/php7", "/workspace/codeassist/php71" });
	};

	@Context
	public static Bundle getBundle() {
		return PHPUiTests.getDefault().getBundle();
	}

	public ContentAssistTests(PHPVersion version, String[] fileNames) {
		this.phpVersion = version;
	}

	@BeforeList
	public void setUpSuite() throws Exception {
		// Set content assist timeout to 60 seconds
		InstanceScope.INSTANCE.getNode(DLTKUIPlugin.PLUGIN_ID).putInt(PreferenceConstants.CODEASSIST_TIMEOUT, 60000);
		project = TestUtils.createProject("Content Assist_" + this.phpVersion);
		ResourcesPlugin.getWorkspace().getRoot().getProject("Content Assist_" + this.phpVersion);
		/*
		 * Set auto insert to true,if there are only one proposal in the CA,it
		 * will insert the proposal,so we can test CA without UI interaction
		 */
		DefaultScope.INSTANCE.getNode(PHPUiPlugin.ID).putBoolean(PHPCoreConstants.CODEASSIST_AUTOINSERT, true);
		TestUtils.setProjectPhpVersion(project, phpVersion);
	}

	@AfterList
	public void tearDownSuite() throws Exception {
		TestUtils.deleteProject(project);
		// Restore content assist default timeout
		int defaultContentAssistTimeout = DefaultScope.INSTANCE.getNode(DLTKUIPlugin.PLUGIN_ID)
				.getInt(PreferenceConstants.CODEASSIST_TIMEOUT, 5000);
		InstanceScope.INSTANCE.getNode(DLTKUIPlugin.PLUGIN_ID).putInt(PreferenceConstants.CODEASSIST_TIMEOUT,
				defaultContentAssistTimeout);
	}

	@Test
	public void assist(String fileName) throws Exception {
		final PdttFile pdttFile = new PdttFile(PHPUiTests.getDefault().getBundle(), fileName);
		doAssist(pdttFile);
	}

	private void doAssist(final PdttFile pdttFile) throws Exception {
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
					createFiles(data, fileName, pdttFile.getOtherFiles());
					openEditor();
					result[0] = executeAutoInsert(offset);
					closeEditor();
					deleteFiles();
				} catch (Exception e) {
					exception[0] = e;
				}
			}
		});
		if (exception[0] != null)
			throw exception[0];
		PDTTUtils.assertContents(pdttFile.getExpected(), result[0]);
	}

	private void deleteFiles() throws Exception {
		if (testFile != null) {
			TestUtils.deleteFile(testFile);
		}
		if (otherFiles != null) {
			for (IFile file : otherFiles) {
				if (file != null) {
					TestUtils.deleteFile(file);
				}
			}
			otherFiles = null;
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

	private String executeAutoInsert(int offset) {
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
		viewer.doOperation(ISourceViewer.CONTENTASSIST_PROPOSALS);
		return fEditor.getDocument().get();
	}

	private void createFiles(String content, String fileName, String[] other) throws Exception {
		testFile = TestUtils.createFile(project, new Path(fileName).lastSegment(), content);
		otherFiles = new IFile[other.length];
		for (int i = 0; i < other.length; i++) {
			otherFiles[i] = TestUtils.createFile(project, new Path(i + fileName).addFileExtension("php").lastSegment(),
					other[i]);
		}
		TestUtils.waitForIndexer();
	}

}
