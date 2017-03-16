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

public class RenameProcessorTestCaseZSTD_1006 extends AbstractRefactoringTest {
	private IProject project1;

	@Before
	public void setUp() throws Exception {
		project1 = TestUtils.createProject("project1");
		TestUtils.setProjectPhpVersion(project1, PHPVersion.PHP5_3);
	}

	@After
	public void tearDown() throws Exception {
		project1.delete(IResource.FORCE, new NullProgressMonitor());
	}

	@Test
	public void testRename1() throws Exception {

		IFolder folder = TestUtils.createFolder(project1, "src");
		IFile file1 = TestUtils.createFile(folder, "testzstd_1006_1.php", "<?php class MyClass{} $a=new MyClass();?>");

		IFile file2 = TestUtils.createFile(folder, "testzstd_1006_2.php",
				"<?php include 'testzstd_1006_1.php'; class NewClass{protected function foo(){$a = new MyClass();}}?>");

		TestUtils.waitForIndexer();

		Program program = createProgram(file2);

		assertNotNull(program);

		int start = 90;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		RenameClassProcessor processor = new RenameClassProcessor(file1, selectedNode);

		processor.setNewElementName("MyClass1");
		processor.setUpdateTextualMatches(true);

		checkInitCondition(processor);

		performChange(processor);

		try {
			String content = FileUtils.getContents(file1);
			assertEquals("<?php class MyClass1{} $a=new MyClass1();?>", content);

			content = FileUtils.getContents(file2);
			assertEquals(
					"<?php include 'testzstd_1006_1.php'; class NewClass{protected function foo(){$a = new MyClass1();}}?>",
					content);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
