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
package org.eclipse.php.refactoring.core.rename;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IAccessRule;
import org.eclipse.dltk.core.IBuildpathAttribute;
import org.eclipse.dltk.core.IBuildpathEntry;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.php.core.tests.TestUtils;
import org.eclipse.php.refactoring.core.test.AbstractRefactoringTest;
import org.eclipse.php.refactoring.core.test.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RenameProcessorTestCase0026988 extends AbstractRefactoringTest {
	private IProject project1;
	private IProject project2;

	@Before
	public void setUp() throws Exception {
		project1 = TestUtils.createProject("project1");
		IFolder folder = TestUtils.createFolder(project1, "src");
		TestUtils.createFile(folder, "test1.php", "<?php class TestRenameClass{}?>");

		project2 = TestUtils.createProject("project2");
		TestUtils.createFile(project2, "test2.php", "<?php include('src/test1.php'); ?>");

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

	@Test
	public void testRename() {
		RenameFolderProcessor processor = new RenameFolderProcessor(project1);
		processor.setNewElementName("project11");
		processor.setUpdateRefernces(true);

		checkInitCondition(processor);

		performChange(processor);

		final IScriptProject scriptProject = DLTKCore.create(project2.getProject());
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject("project11");

		assertTrue(project.exists());

		// 26988
		assertTrue(FileUtils.isInBuildpath(project.getFullPath(), scriptProject, IBuildpathEntry.BPE_PROJECT));
	}

	@After
	public void tearDown() throws Exception {
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject("project11");
		TestUtils.deleteProject(project);
		project1.delete(IResource.FORCE, new NullProgressMonitor());
		project2.delete(IResource.FORCE, new NullProgressMonitor());
	}
}
