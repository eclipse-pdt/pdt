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
import static org.junit.Assert.fail;

import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.php.core.ast.nodes.ASTNode;
import org.eclipse.php.core.ast.nodes.Program;
import org.eclipse.php.core.tests.TestUtils;
import org.eclipse.php.refactoring.core.test.AbstractRefactoringTest;
import org.eclipse.php.refactoring.core.test.FileUtils;
import org.eclipse.php.refactoring.core.utils.ASTUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RenameProcessorTestCase0026942 extends AbstractRefactoringTest {
	private IProject project1;
	private IFile file;

	@Before
	public void setUp() throws Exception {
		project1 = TestUtils.createProject("project1");
		IFolder folder = TestUtils.createFolder(project1, "src");
		file = TestUtils.createFile(folder, "test1.php",
				"<?php class Visitor{ public function isAllowed ($pPermissionCode){return true;}} class NonVisitor{public function isAllowed ($pPermissionCode){return false;}} class EditPage{private $isAllowed = false;public function __construct (){$lVisitor = new Visitor();$this->isAllowed = $lVisitor->isAllowed('EDIT_PAGE');}}class ViewPage{private $isAllowed = false;public function __construct (){$lVisitor = new NonVisitor();$this->isAllowed = $lVisitor->isAllowed('VIEW_PAGE');}}?>");

		TestUtils.waitForIndexer();
	}

	@Test
	public void testRename1() {
		Program program = createProgram(file);

		assertNotNull(program);

		int start = 184;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		RenameClassMemberProcessor processor = new RenameClassMemberProcessor(file, selectedNode);
		processor.setNewElementName("isAllowed1");

		checkInitCondition(processor);

		performChange(processor);

		try {
			String content = FileUtils.getContents(file);
			assertEquals(
					"<?php class Visitor{ public function isAllowed ($pPermissionCode){return true;}} class NonVisitor{public function isAllowed ($pPermissionCode){return false;}} class EditPage{private $isAllowed1 = false;public function __construct (){$lVisitor = new Visitor();$this->isAllowed1 = $lVisitor->isAllowed('EDIT_PAGE');}}class ViewPage{private $isAllowed = false;public function __construct (){$lVisitor = new NonVisitor();$this->isAllowed = $lVisitor->isAllowed('VIEW_PAGE');}}?>",
					content);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testRename2() {
		Program program = createProgram(file);

		assertNotNull(program);

		int start = 290;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		RenameClassMemberProcessor processor = new RenameClassMemberProcessor(file, selectedNode);
		processor.setNewElementName("isAllowed1");

		checkInitCondition(processor);

		performChange(processor);

		try {
			String content = FileUtils.getContents(file);
			assertEquals(
					"<?php class Visitor{ public function isAllowed1 ($pPermissionCode){return true;}} class NonVisitor{public function isAllowed ($pPermissionCode){return false;}} class EditPage{private $isAllowed = false;public function __construct (){$lVisitor = new Visitor();$this->isAllowed = $lVisitor->isAllowed1('EDIT_PAGE');}}class ViewPage{private $isAllowed = false;public function __construct (){$lVisitor = new NonVisitor();$this->isAllowed = $lVisitor->isAllowed('VIEW_PAGE');}}?>",
					content);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected Program createProgram(IFile file) {
		ISourceModule sourceModule = DLTKCore.createSourceModuleFrom(file);
		Program program = null;
		try {
			program = ASTUtils.createProgramFromSource(sourceModule);

		} catch (Exception e) {
			fail(e.getMessage());
		}
		return program;
	}

	@After
	public void tearDown() throws Exception {
		project1.delete(IResource.FORCE, new NullProgressMonitor());
	}
}
