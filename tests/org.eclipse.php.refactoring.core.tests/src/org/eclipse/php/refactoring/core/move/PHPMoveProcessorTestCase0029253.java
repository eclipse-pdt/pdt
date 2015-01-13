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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
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

public class PHPMoveProcessorTestCase0029253 extends TestCase {
	private IProject project1;
	private IProject project2;

	@Override
	protected void setUp() throws Exception {
		System.setProperty("disableStartupRunner","true");
		PHPCoreTests.waitForIndexer();
		PHPCoreTests.waitForAutoBuild();
		

		project1 = FileUtils.createProject("TestProject00292531");
		
		IFolder folder = project1.getFolder("src");
		
		if(!folder.exists()){
			folder.create(true, true, new NullProgressMonitor());
		}
		
		folder = folder.getFolder("aaa");
		
		if(!folder.exists()){
			folder.create(true, true, new NullProgressMonitor());
		}
		
		
		IFile file = folder.getFile("test00292531.php");

		InputStream source = new ByteArrayInputStream(
				"<?php class TestRenameClass{}?>".getBytes());

		if (!file.exists()) {
			file.create(source, true, new NullProgressMonitor());
		} else {
			file.setContents(source, IFile.FORCE, new NullProgressMonitor());
		}
		
		folder = project1.getFolder("src/bbb");
		
		if(!folder.exists()){
			folder.create(true, true, new NullProgressMonitor());
		}

		project2 = FileUtils.createProject("TestProject00292532");

		file = project2.getFile("test00292532.php");

		source = new ByteArrayInputStream("<?php include('src/aaa/test00292531.php'); ?>"
				.getBytes());

		if (!file.exists()) {
			file.create(source, true, new NullProgressMonitor());
		} else {
			file.setContents(source, IFile.FORCE, new NullProgressMonitor());
		}
		
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
	
	public void testMoveingFolder0029253(){
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
		
		IFile file = project2.getFile("test00292532.php");
		
		try {
			String content = FileUtils.getContents(file);
			assertEquals("<?php include('src/bbb/aaa/test00292531.php'); ?>", content);
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
}
