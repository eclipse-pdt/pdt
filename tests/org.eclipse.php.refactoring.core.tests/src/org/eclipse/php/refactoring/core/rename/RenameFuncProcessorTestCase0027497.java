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
import static org.junit.Assert.assertTrue;

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

public class RenameFuncProcessorTestCase0027497 extends AbstractRefactoringTest {

	private IProject project1;
	private IFile file;

	@Before
	public void setUp() throws Exception {
		project1 = TestUtils.createProject("project1");
		file = TestUtils.createFile(project1, "RenameFuncTest00274972.php",
				"<?php include \"src/RenameFuncTest0027497.php\"; foo274972(); ?>");
		IFolder folder = TestUtils.createFolder(project1, "src");
		file = TestUtils.createFile(folder, "RenameFuncTest0027497.php", "<?php function foo274972(){}; ?>");

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

		int start = 17;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		RenameFunctionProcessor processor = new RenameFunctionProcessor(file, selectedNode);
		processor.setNewElementName("foo2749721");
		processor.setUpdateTextualMatches(true);

		checkInitCondition(processor);

		performChange(processor);

		IFile file = project1.getFile("src/RenameFuncTest0027497.php");
		assertTrue(file.exists());

		try {
			String content = FileUtils.getContents(file);

			assertEquals("<?php function foo2749721(){}; ?>", content);
		} catch (IOException e) {
			e.printStackTrace();
		}

		file = project1.getFile("RenameFuncTest00274972.php");
		assertTrue(file.exists());

		try {
			String content = FileUtils.getContents(file);

			assertEquals("<?php include \"src/RenameFuncTest0027497.php\"; foo2749721(); ?>", content);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
