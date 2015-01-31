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
package org.eclipse.php.refactoring.core.move;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
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
import org.eclipse.php.core.tests.PHPCoreTests;
import org.eclipse.php.refactoring.core.test.FileUtils;
import org.junit.Before;
import org.junit.Test;

public class PHPMoveProcessorTestCase1 {
	private IProject project1;
	private IProject project2;

	@Before
	public void setUp() throws Exception {
		System.setProperty("disableStartupRunner","true");
		PHPCoreTests.waitForIndexer();
		PHPCoreTests.waitForAutoBuild();
		

		project1 = FileUtils.createProject("TestProject11");
		
		IFolder folder = project1.getFolder("src");
		
		if(!folder.exists()){
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

		project2 = FileUtils.createProject("TestProject21");

		IAccessRule[] accesRules = new IAccessRule[0];

		boolean combineAccessRules = false;
		IBuildpathEntry buildPath = DLTKCore.newProjectEntry(project1
				.getProject().getFullPath(), accesRules, combineAccessRules,
				new IBuildpathAttribute[0], false);

		final IScriptProject scriptProject = DLTKCore.create(project2.getProject());

		final List<IBuildpathEntry> entriesList = new ArrayList<IBuildpathEntry>();
		IBuildpathEntry[] entries;
		try {
			entries = scriptProject.getRawBuildpath();
			entriesList.addAll(Arrays.asList(entries));
			entriesList.add(buildPath);
		} catch (ModelException e) {
			e.printStackTrace();
		}

		final IBuildpathEntry[] newEntries = new IBuildpathEntry[entriesList
				.size()];

		scriptProject.setRawBuildpath(null,
				new NullProgressMonitor());
		scriptProject.setRawBuildpath(entriesList
				.toArray(newEntries), new NullProgressMonitor());

		PHPCoreTests.waitForIndexer();
		PHPCoreTests.waitForAutoBuild();
	}
	
	@Test
	public void testMoveing(){
		PHPMoveProcessor processor = new PHPMoveProcessor(project1.getProject().getFolder("src"));
		
		RefactoringStatus status = processor.checkInitialConditions(new NullProgressMonitor());
		
		assertEquals(IStatus.OK, status.getSeverity());
		
		processor.setDestination(project2);
		processor.setUpdateReferences(true);
		
		try {
			Change change = processor.createChange(new NullProgressMonitor());
			change.perform(new NullProgressMonitor());
			PHPCoreTests.waitForAutoBuild();
			
			assertTrue(project2.getFolder("src").exists());
			
			IMarker[] marks = project2.findMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE);
			
			assertEquals(0,marks.length);
			
		} catch (OperationCanceledException e) {
			fail(e.getMessage());
		} catch (CoreException e) {
			fail(e.getMessage());
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
	}
}
