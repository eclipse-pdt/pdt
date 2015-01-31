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
import org.eclipse.php.refactoring.core.test.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RenameFolderTestCase2 extends AbstractRenameRefactoringTest {

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
		file = folder.getFile("RenameFolderTestCase21.php");

		InputStream source = new ByteArrayInputStream(
				"<?php class TestRenameClass{}?>".getBytes());

		if (!file.exists()) {
			file.create(source, true, new NullProgressMonitor());
		} else {
			file.setContents(source, IFile.FORCE, new NullProgressMonitor());
		}

		
		file = project1.getFile("RenameFolderTestCase22.php");

		source = new ByteArrayInputStream(
				"<?php include './src/RenameFolderTestCase21.php'; ?>".getBytes());

		if (!file.exists()) {
			file.create(source, true, new NullProgressMonitor());
		} else {
			file.setContents(source, IFile.FORCE, new NullProgressMonitor());
		}


		PHPCoreTests.waitForIndexer();
		PHPCoreTests.waitForAutoBuild();
	}

	@Test
	public void testRename() {
		RenameFolderProcessor processor = new RenameFolderProcessor(project1
				.getFolder("src"));
		processor.setNewElementName("src1");
		processor.setUpdateRefernces(true);

		checkInitCondition(processor);

		performChange(processor);
		IFolder folder = project1.getFolder("src1");
		assertTrue(folder.exists());

		
		try {
			String content = FileUtils.getContents(file);
			assertEquals(
					"<?php include './src1/RenameFolderTestCase21.php'; ?>",
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
