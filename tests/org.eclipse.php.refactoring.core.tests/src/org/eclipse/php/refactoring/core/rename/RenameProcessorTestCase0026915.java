/*******************************************************************************
 * Copyright (c) 2005, 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.core.rename;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IBuildpathAttribute;
import org.eclipse.dltk.core.IBuildpathEntry;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.php.core.tests.PHPCoreTests;
import org.eclipse.php.refactoring.core.test.FileUtils;

public class RenameProcessorTestCase0026915 extends
		AbstractRenameRefactoringTest {
	private IProject project1;

	@Override
	protected void setUp() throws Exception {
		PHPCoreTests.waitForIndexer();
		PHPCoreTests.waitForAutoBuild();

		project1 = FileUtils.createProject("project1");

		IFolder folder = project1.getFolder("src");

		if (!folder.exists()) {
			folder.create(true, true, new NullProgressMonitor());
		}
		IFile file = folder.getFile("test1.php");

		InputStream source = new ByteArrayInputStream(
				"<?php class TestRenameClass{}?>".getBytes());

		if (!file.exists()) {
			file.create(source, true, new NullProgressMonitor());
		} else {
			file.setContents(source, IFile.FORCE, new NullProgressMonitor());
		}

		IPath fPath = new Path("/project1/src");
		IPath[] inclusionPattern = new IPath[0];
		IPath[] exclusionPattern = new IPath[0];
		IBuildpathEntry sourceEntry = DLTKCore.newSourceEntry(fPath,
				inclusionPattern, exclusionPattern, new IBuildpathAttribute[0]);

		fPath = new Path("/project1");
		inclusionPattern = new IPath[0];
		exclusionPattern = new IPath[1];
		exclusionPattern[0] = new Path("src/");
		IBuildpathEntry sourceEntry1 = DLTKCore.newSourceEntry(fPath,
				inclusionPattern, exclusionPattern, new IBuildpathAttribute[0]);

		final IScriptProject scriptProject = DLTKCore.create(project1
				.getProject());

		final List<IBuildpathEntry> entriesList = new ArrayList<IBuildpathEntry>();
		IBuildpathEntry[] entries;
		try {
			entries = scriptProject.getRawBuildpath();

			entries = FileUtils.removeEntryFromBuildPath(entries, project1
					.getFullPath());

			entriesList.addAll(Arrays.asList(entries));

			entriesList.add(sourceEntry);
			entriesList.add(sourceEntry1);
		} catch (ModelException e) {
			e.printStackTrace();
		}

		final IBuildpathEntry[] newEntries = new IBuildpathEntry[entriesList
				.size()];

		scriptProject.setRawBuildpath(null, new NullProgressMonitor());
		scriptProject.setRawBuildpath(entriesList.toArray(newEntries),
				new NullProgressMonitor());

		PHPCoreTests.waitForIndexer();
		PHPCoreTests.waitForAutoBuild();
	}

	public void testRename() {
		RenameFolderProcessor processor = new RenameFolderProcessor(project1.getFolder("src"));
		processor.setNewElementName("src1");
		processor.setUpdateRefernces(true);

		checkInitCondition(processor);

		performChange(processor);
		IFolder folder = project1.getFolder("src1");
		assertTrue(folder.exists());
		
		final IScriptProject scriptProject = DLTKCore.create(project1);
		
		FileUtils.isInBuildpath(folder.getFullPath(), scriptProject, IBuildpathEntry.BPE_SOURCE);

	}

	@Override
	protected void tearDown() throws Exception {
		project1.delete(IResource.FORCE, new NullProgressMonitor());
	}

	@Override
	protected String getTestDirectory() {
		return "";
	}
}
