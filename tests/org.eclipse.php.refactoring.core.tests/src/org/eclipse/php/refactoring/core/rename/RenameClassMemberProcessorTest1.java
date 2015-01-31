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
import org.eclipse.php.internal.core.ast.nodes.ASTNode;
import org.eclipse.php.internal.core.ast.nodes.Program;
import org.eclipse.php.refactoring.core.test.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RenameClassMemberProcessorTest1 extends
		AbstractRenameRefactoringTest {

	private IProject project1;
	private IFile file;

	@Before
	public void setUp() throws Exception {
		PHPCoreTests.waitForIndexer();
		PHPCoreTests.waitForAutoBuild();

		project1 = FileUtils.createProject("project1");

		IFolder folder = project1.getFolder("src");

		if (!folder.exists()) {
			folder.create(true, true, new NullProgressMonitor());
		}
		file = folder.getFile("test21.php");

		InputStream source = new ByteArrayInputStream(
				"<?php class Item { public static function foo(){} } class ItemEx extends Item{public static function foo(){}} ItemEx::foo();?>".getBytes());

		if (!file.exists()) {
			file.create(source, true, new NullProgressMonitor());
		} else {
			file.setContents(source, IFile.FORCE, new NullProgressMonitor());
		}

		PHPCoreTests.waitForIndexer();
		PHPCoreTests.waitForAutoBuild();
	}

	@Test
	public void testRename1() throws Exception {
		Program program = createProgram(file);

		assertNotNull(program);

		int start = 120;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);
		
		RenameClassMemberProcessor processor = new RenameClassMemberProcessor(file, selectedNode);
		processor.setNewElementName("foo1");
		
		checkInitCondition(processor);
		
		performChange(processor);
		

		try {
			String content = FileUtils.getContents(file);
			assertEquals(
					"<?php class Item { public static function foo1(){} } class ItemEx extends Item{public static function foo1(){}} ItemEx::foo1();?>",
					content);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@After
	public void tearDown() throws Exception {
		project1.delete(IResource.FORCE, new NullProgressMonitor());
	}
}


