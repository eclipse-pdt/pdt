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

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.php.core.tests.PHPCoreTests;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.ast.nodes.ASTNode;
import org.eclipse.php.internal.core.ast.nodes.Program;
import org.eclipse.php.refactoring.core.test.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RenameProcessorTestCase0027134 extends
		AbstractRenameRefactoringTest {
	private IProject project1;
	
//	private IFile file1;
//
//	private IFile file2;

	@Before
	public void setUp() throws Exception {
//		System.setProperty("disableStartupRunner", "true");
//		PHPCoreTests.waitForIndexer();
//		PHPCoreTests.waitForAutoBuild();
//
//		project1 = FileUtils.createProject("project1", PHPVersion.PHP5_3);
//
//		IFolder folder = project1.getFolder("src");
//
//		if (!folder.exists()) {
//			folder.create(true, true, new NullProgressMonitor());
//		}
//		
//		file1 = folder.getFile("test1.php");
//
//		InputStream source = new ByteArrayInputStream(
//				"<?php class MyClass{} ?>"
//						.getBytes());
//
//		if (!file1.exists()) {
//			file1.create(source, true, new NullProgressMonitor());
//		} else {
//			file1.setContents(source, IFile.FORCE, new NullProgressMonitor());
//		}
//		
//		file2 = folder.getFile("test2.php");
//
//		source = new ByteArrayInputStream(
//				"<?php class SecondClass extends MyClass{} ?>"
//						.getBytes());
//
//		if (!file2.exists()) {
//			file2.create(source, true, new NullProgressMonitor());
//		} else {
//			file2.setContents(source, IFile.FORCE, new NullProgressMonitor());
//		}
//
//		
//		PHPCoreTests.waitForIndexer();
//		PHPCoreTests.waitForAutoBuild();
	}

	@After
	public void tearDown() throws Exception {
		project1.delete(IResource.FORCE, new NullProgressMonitor());
	}

	@Test
	public void testRename1() throws Exception {
		
		System.setProperty("disableStartupRunner", "true");
		PHPCoreTests.waitForIndexer();
		PHPCoreTests.waitForAutoBuild();

		project1 = FileUtils.createProject("project1", PHPVersion.PHP5_3);

		IFolder folder = project1.getFolder("src");

		if (!folder.exists()) {
			folder.create(true, true, new NullProgressMonitor());
		}
		
		IFile file1 = folder.getFile("test0027134_1.php");

		InputStream source = new ByteArrayInputStream(
				"<?php class MyClass{} ?>"
						.getBytes());

		if (!file1.exists()) {
			file1.create(source, true, new NullProgressMonitor());
		} else {
			file1.setContents(source, IFile.FORCE, new NullProgressMonitor());
		}
		
		IFile file2 = folder.getFile("test0027134_2.php");

		source = new ByteArrayInputStream(
				"<?php include 'test0027134_1.php'; class SecondClass extends MyClass{} ?>"
						.getBytes());

		if (!file2.exists()) {
			file2.create(source, true, new NullProgressMonitor());
		} else {
			file2.setContents(source, IFile.FORCE, new NullProgressMonitor());
		}

		
		PHPCoreTests.waitForIndexer();
		PHPCoreTests.waitForAutoBuild();

		Program program = createProgram(file1);

		assertNotNull(program);

		int start = 12;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		RenameClassProcessor processor = new RenameClassProcessor(file1,
				selectedNode);

		processor.setNewElementName("MyClass1");
		processor.setUpdateTextualMatches(true);

		checkInitCondition(processor);

		performChange(processor);

		try {
			String content = FileUtils.getContents(file1);
			assertEquals(
					"<?php class MyClass1{} ?>",
					content);
			
			content = FileUtils.getContents(file2);
			assertEquals(
					"<?php include 'test0027134_1.php'; class SecondClass extends MyClass1{} ?>",
					content);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testRename2() throws Exception{
		
		System.setProperty("disableStartupRunner", "true");
		PHPCoreTests.waitForIndexer();
		PHPCoreTests.waitForAutoBuild();

		project1 = FileUtils.createProject("project1", PHPVersion.PHP5_3);

		IFolder folder = project1.getFolder("src");

		if (!folder.exists()) {
			folder.create(true, true, new NullProgressMonitor());
		}
		
		IFile file1 = folder.getFile("test100271341.php");

		InputStream source = new ByteArrayInputStream(
				"<?php class MyClass{} ?>"
						.getBytes());

		if (!file1.exists()) {
			file1.create(source, true, new NullProgressMonitor());
		} else {
			file1.setContents(source, IFile.FORCE, new NullProgressMonitor());
		}
		
		IFile file2 = folder.getFile("test00271342.php");

		source = new ByteArrayInputStream(
				"<?php class SecondClass extends MyClass{} ?>"
						.getBytes());

		if (!file2.exists()) {
			file2.create(source, true, new NullProgressMonitor());
		} else {
			file2.setContents(source, IFile.FORCE, new NullProgressMonitor());
		}

		
		PHPCoreTests.waitForIndexer();
		PHPCoreTests.waitForAutoBuild();
		Program program = createProgram(file2);

		assertNotNull(program);

		int start = 33;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		RenameClassProcessor processor = new RenameClassProcessor(file2,
				selectedNode);

		processor.setNewElementName("MyClass2");
		processor.setUpdateTextualMatches(true);

		checkInitCondition(processor);

		performChange(processor);

		try {
			String content = FileUtils.getContents(file1);
			assertEquals(
					"<?php class MyClass2{} ?>",
					content);
			
			content = FileUtils.getContents(file2);
			assertEquals(
					"<?php class SecondClass extends MyClass2{} ?>",
					content);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
