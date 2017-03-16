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
import org.eclipse.php.core.tests.TestUtils;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.ast.nodes.ASTNode;
import org.eclipse.php.core.ast.nodes.Program;
import org.eclipse.php.refactoring.core.test.AbstractRefactoringTest;
import org.eclipse.php.refactoring.core.test.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RenameProcessorTestCase0029408 extends AbstractRefactoringTest {
	private IProject project1;
	private IFile file;

	@Before
	public void setUp() throws Exception {
		project1 = TestUtils.createProject("project1");
		TestUtils.setProjectPhpVersion(project1, PHPVersion.PHP5_3);

		IFolder folder = TestUtils.createFolder(project1, "src");
		file = TestUtils.createFile(folder, "test00294081.php",
				"<?php class MyClass{const constant = 'constant value';function showCons1tant() {echo self::constant;}}$class = new MyClass ();$class->showConstant ();echo $class::constant;?>");

		TestUtils.waitForIndexer();
	}

	@After
	public void tearDown() throws Exception {
		project1.delete(IResource.FORCE, new NullProgressMonitor());
	}

	@Test
	public void testRename() throws Exception {

		Program program = createProgram(file);

		assertNotNull(program);

		int start = 117;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		RenameClassProcessor processor = new RenameClassProcessor(file, selectedNode);

		processor.setNewElementName("MyClassNewName");
		processor.setUpdateTextualMatches(true);

		checkInitCondition(processor);

		performChange(processor);

		try {
			String content = FileUtils.getContents(file);
			assertEquals(
					"<?php class MyClassNewName{const constant = 'constant value';function showCons1tant() {echo self::constant;}}$class = new MyClassNewName ();$class->showConstant ();echo $class::constant;?>",
					content);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
