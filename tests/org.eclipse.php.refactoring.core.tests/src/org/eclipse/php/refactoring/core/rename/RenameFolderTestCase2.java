/*******************************************************************************
 * Copyright (c) 2005, 2015 Zend Technologies and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.core.rename;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.php.core.tests.TestUtils;
import org.eclipse.php.refactoring.core.test.AbstractRefactoringTest;
import org.eclipse.php.refactoring.core.test.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RenameFolderTestCase2 extends AbstractRefactoringTest {

	private IProject project1;
	private IFile file;

	@Before
	public void setUp() throws Exception {
		project1 = TestUtils.createProject("project1");
		IFolder folder = TestUtils.createFolder(project1, "src");
		file = TestUtils.createFile(folder, "RenameFolderTestCase21.php", "<?php class TestRenameClass{}?>");
		file = TestUtils.createFile(project1, "RenameFolderTestCase22.php",
				"<?php include './src/RenameFolderTestCase21.php'; ?>");

		TestUtils.waitForIndexer();
	}

	@Test
	public void testRename() {
		RenameFolderProcessor processor = new RenameFolderProcessor(project1.getFolder("src"));
		processor.setNewElementName("src1");
		processor.setUpdateRefernces(true);

		checkInitCondition(processor);

		performChange(processor);
		IFolder folder = project1.getFolder("src1");
		assertTrue(folder.exists());

		try {
			String content = FileUtils.getContents(file);
			assertEquals("<?php include './src1/RenameFolderTestCase21.php'; ?>", content);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@After
	public void tearDown() throws Exception {
		project1.delete(IResource.FORCE, new NullProgressMonitor());
	}

}
