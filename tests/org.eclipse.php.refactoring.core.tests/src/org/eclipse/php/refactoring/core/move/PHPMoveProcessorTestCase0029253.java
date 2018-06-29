/*******************************************************************************
 * Copyright (c) 2005, 2015 Zend Technologies and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.core.move;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IAccessRule;
import org.eclipse.dltk.core.IBuildpathAttribute;
import org.eclipse.dltk.core.IBuildpathEntry;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.php.core.tests.TestUtils;
import org.eclipse.php.refactoring.core.test.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PHPMoveProcessorTestCase0029253 {
	private IProject project1;
	private IProject project2;

	@Before
	public void setUp() throws Exception {

		project1 = TestUtils.createProject("TestProject00292531");
		IFolder folder = TestUtils.createFolder(project1, "src");

		folder = folder.getFolder("aaa");
		if (!folder.exists()) {
			folder.create(true, true, new NullProgressMonitor());
		}

		TestUtils.createFile(folder, "test00292531.php", "<?php class TestRenameClass{}?>");

		folder = TestUtils.createFolder(project1, "src/bbb");

		project2 = TestUtils.createProject("TestProject00292532");
		TestUtils.createFile(project2, "test00292532.php", "<?php include('src/aaa/test00292531.php'); ?>");

		IAccessRule[] accesRules = new IAccessRule[0];

		boolean combineAccessRules = false;
		IBuildpathEntry buildPath = DLTKCore.newProjectEntry(project1.getProject().getFullPath(), accesRules,
				combineAccessRules, new IBuildpathAttribute[0], false);

		final IScriptProject scriptProject = DLTKCore.create(project2.getProject());

		final List<IBuildpathEntry> entriesList = new ArrayList<>();
		IBuildpathEntry[] entries;
		try {
			entries = scriptProject.getRawBuildpath();
			entriesList.addAll(Arrays.asList(entries));
			entriesList.add(buildPath);
		} catch (ModelException e) {
			e.printStackTrace();
		}

		final IBuildpathEntry[] newEntries = new IBuildpathEntry[entriesList.size()];

		scriptProject.setRawBuildpath(null, new NullProgressMonitor());
		scriptProject.setRawBuildpath(entriesList.toArray(newEntries), new NullProgressMonitor());

		TestUtils.waitForIndexer();
	}

	@After
	public void tearDown() throws Exception {
		project1.delete(IResource.FORCE, new NullProgressMonitor());
		project2.delete(IResource.FORCE, new NullProgressMonitor());
	}

	@Test
	public void testMoveFolder0029253() {
		PHPMoveProcessor processor = new PHPMoveProcessor(project1.getProject().getFolder("/src/aaa"));

		RefactoringStatus status = processor.checkInitialConditions(new NullProgressMonitor());

		assertEquals(IStatus.OK, status.getSeverity());

		processor.setDestination(project1.getProject().getFolder("/src/bbb"));
		processor.setUpdateReferences(true);

		try {
			Change change = processor.createChange(new NullProgressMonitor());
			change.perform(new NullProgressMonitor());
		} catch (OperationCanceledException e) {
			fail(e.getMessage());
		} catch (CoreException e) {
			fail(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

		TestUtils.waitForIndexer();

		IFile file = project2.getFile("test00292532.php");

		try {
			String content = FileUtils.getContents(file);
			assertEquals("<?php include('src/bbb/aaa/test00292531.php'); ?>", content);
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
}
