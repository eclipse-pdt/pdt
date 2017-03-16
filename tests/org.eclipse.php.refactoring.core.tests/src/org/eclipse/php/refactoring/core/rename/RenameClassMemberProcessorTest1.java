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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.php.core.ast.nodes.ASTNode;
import org.eclipse.php.core.ast.nodes.Program;
import org.eclipse.php.core.tests.TestUtils;
import org.eclipse.php.refactoring.core.test.AbstractRefactoringTest;
import org.eclipse.php.refactoring.core.test.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RenameClassMemberProcessorTest1 extends AbstractRefactoringTest {

	private IProject project1;
	private IFile file;

	@Before
	public void setUp() throws Exception {
		project1 = TestUtils.createProject("project1");
		IFolder folder = TestUtils.createFolder(project1, "src");
		file = TestUtils.createFile(folder, "test21.php",
				"<?php class Item { public static function foo(){} } class ItemEx extends Item{public static function foo(){}} ItemEx::foo();?>");

		TestUtils.waitForIndexer();
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
