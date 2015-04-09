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

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.php.core.tests.PHPCoreTests;
import org.eclipse.php.core.tests.PdttFile;
import org.eclipse.php.core.tests.runner.AbstractPDTTRunner.Context;
import org.eclipse.php.core.tests.runner.PDTTList;
import org.eclipse.php.core.tests.runner.PDTTList.AfterList;
import org.eclipse.php.core.tests.runner.PDTTList.BeforeList;
import org.eclipse.php.core.tests.runner.PDTTList.Parameters;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.project.PHPNature;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
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
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.osgi.framework.Bundle;

@SuppressWarnings("restriction")
@RunWith(PDTTList.class)
public class ContentAssistTests {

	protected IProject project;
	protected IFile testFile;
	protected IFile[] otherFiles = new IFile[0];
	protected PHPVersion phpVersion;
	// protected static PHPVersion phpVersion;
	protected PHPStructuredEditor fEditor;

	@Parameters
	public static final Map<PHPVersion, String[]> TESTS = new LinkedHashMap<PHPVersion, String[]>();
	static {
		// TESTS.put(PHPVersion.PHP4, "/workspace/codeassist/php4");
		TESTS.put(PHPVersion.PHP5,
				new String[] { "/workspace/codeassist/php5" });
		TESTS.put(PHPVersion.PHP5_3, new String[] {
				"/workspace/codeassist/php5", "/workspace/codeassist/php53",
				"/workspace/codeassist/php53/classExclusive" });
		TESTS.put(PHPVersion.PHP5_6, new String[] {
				"/workspace/codeassist/php5", "/workspace/codeassist/php53",
				"/workspace/codeassist/php56" });
	};
	protected static final char OFFSET_CHAR = '|';

	@Context
	public static Bundle getBundle() {
		return PHPUiTests.getDefault().getBundle();
	}

	public ContentAssistTests(PHPVersion version, String[] fileNames) {
		this.phpVersion = version;
	}

	@BeforeList
	public void setUpSuite() throws Exception {
		project = ResourcesPlugin.getWorkspace().getRoot()
				.getProject("Content Assist_" + this.phpVersion);
		if (project.exists()) {
			return;
		}

		project.create(null);
		project.open(null);

		// configure nature
		IProjectDescription desc = project.getDescription();
		desc.setNatureIds(new String[] { PHPNature.ID });
		project.setDescription(desc, null);

		// set auto insert to true,if there are only one proposal in the CA,it
		// will insert the proposal,so we can test CA without UI interaction
		PHPUiPlugin.getDefault().getPluginPreferences()
				.setDefault(PHPCoreConstants.CODEASSIST_AUTOINSERT, true);
		PHPCoreTests.setProjectPhpVersion(project, phpVersion);
	}

	@AfterList
	public void tearDownSuite() throws Exception {
		project.close(null);
		project.delete(true, true, null);
		project = null;
	}

	@After
	public void tearDown() throws Exception {
		if (testFile != null) {
			testFile.delete(true, null);
			testFile = null;
		}
		if (otherFiles != null) {
			for (IFile f : otherFiles) {
				f.delete(true, null);
			}
			otherFiles = null;
		}
	}

	@Test
	public void assist(String fileName) throws Exception {
		final PdttFile pdttFile = new PdttFile(PHPUiTests.getDefault()
				.getBundle(), fileName);
		pdttFile.applyPreferences();

		String data = pdttFile.getFile();
		int offset = data.lastIndexOf(OFFSET_CHAR);

		// replace the offset character
		data = data.substring(0, offset) + data.substring(offset + 1);
		createFile(new ByteArrayInputStream(data.getBytes()),
				Long.toString(System.currentTimeMillis()),
				prepareOtherStreams(pdttFile));
		String result = executeAutoInsert(offset);
		closeEditor();
		if (!pdttFile.getExpected().trim().equals(result.trim())) {
			StringBuilder errorBuf = new StringBuilder();
			errorBuf.append("\nEXPECTED COMPLETIONS LIST:\n-----------------------------\n");
			errorBuf.append(pdttFile.getExpected());
			errorBuf.append("\nACTUAL COMPLETIONS LIST:\n-----------------------------\n");
			errorBuf.append(result);
			fail(errorBuf.toString());
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

	protected void closeEditor() {
		fEditor.doSave(null);
		fEditor.getSite().getPage().closeEditor(fEditor, false);
		fEditor = null;
		if (testFile.exists()) {
			try {
				testFile.delete(true, false, null);
				project.refreshLocal(IResource.DEPTH_INFINITE, null);
				project.build(IncrementalProjectBuilder.FULL_BUILD, null);
				PHPCoreTests.waitForIndexer();
			} catch (CoreException e) {
			}

		}
	}

	protected String executeAutoInsert(int offset) {
		StructuredTextViewer viewer = null;
		Display display = Display.getDefault();
		long timeout = System.currentTimeMillis() + 3000;
		while ((System.currentTimeMillis() < timeout)
				&& ((viewer = fEditor.getTextViewer()) == null)) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		if (viewer == null) {
			fail("fEditor.getTextViewer() returns null for file "
					+ testFile.getFullPath() + "(" + testFile.getLocation()
					+ ")");
		}
		StyledText textWidget = viewer.getTextWidget();
		textWidget.setCaretOffset(offset);
		viewer.doOperation(ISourceViewer.CONTENTASSIST_PROPOSALS);
		return fEditor.getDocument().get();
	}

	protected void createFile(InputStream inputStream, String fileName)
			throws Exception {
		createFile(inputStream, fileName, new InputStream[0]);
	}

	protected void createFile(InputStream inputStream, String fileName,
			InputStream[] other) throws Exception {
		testFile = project.getFile(new Path(fileName).removeFileExtension()
				.addFileExtension("php").lastSegment());
		testFile.create(inputStream, true, null);
		otherFiles = new IFile[other.length];
		for (int i = 0; i < other.length; i++) {
			otherFiles[i] = project.getFile(new Path(i + fileName)
					.addFileExtension("php").lastSegment());
			otherFiles[i].create(other[i], true, null);
		}
		project.refreshLocal(IResource.DEPTH_INFINITE, null);

		project.build(IncrementalProjectBuilder.FULL_BUILD, null);
		PHPCoreTests.waitForIndexer();
		IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow();
		IWorkbenchPage page = workbenchWindow.getActivePage();
		IEditorInput input = new FileEditorInput(testFile);
		/*
		 * This should take care of testing init, createPartControl,
		 * beginBackgroundOperation, endBackgroundOperation methods
		 */
		IEditorPart part = page.openEditor(input, "org.eclipse.php.editor",
				true);
		if (part instanceof PHPStructuredEditor) {
			fEditor = (PHPStructuredEditor) part;
		} else {
			assertTrue("Unable to open php editor", false);
		}
	}
}
