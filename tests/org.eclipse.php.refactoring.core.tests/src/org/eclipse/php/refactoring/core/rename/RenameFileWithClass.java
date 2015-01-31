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
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.php.core.tests.PHPCoreTests;
import org.eclipse.php.internal.core.ast.nodes.Program;
import org.eclipse.php.refactoring.core.test.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RenameFileWithClass extends AbstractRenameRefactoringTest {

	private IProject project1;
	private IFile file;
	private IFile file1;

	@Before
	public void setUp() throws Exception {
		PHPCoreTests.waitForIndexer();
		PHPCoreTests.waitForAutoBuild();

		project1 = FileUtils.createProject("project1");

		file = project1.getFile("RenameFileWithClass1.php");

		InputStream source = new ByteArrayInputStream(
				"<?php class RenameFileWithClass1{}?>".getBytes());

		if (!file.exists()) {
			file.create(source, true, new NullProgressMonitor());
		} else {
			file.setContents(source, IFile.FORCE, new NullProgressMonitor());
		}

		
		file1 = project1.getFile("RenameFileWithClass2.php");

		source = new ByteArrayInputStream("<?php include 'RenameFileWithClass1.php'; ?>".getBytes());

		if (!file1.exists()) {
			file1.create(source, true, new NullProgressMonitor());
		} else {
			file1.setContents(source, IFile.FORCE, new NullProgressMonitor());
		}


		PHPCoreTests.waitForIndexer();
		PHPCoreTests.waitForAutoBuild();
	}

	@Test
	public void testRename() throws Exception {
		Program program = createProgram(file);

		assertNotNull(program);

		
		RenameFileProcessor processor = new RenameFileProcessor(file, program);
		processor.setNewElementName("RenameFileWithClass3.php");
		processor.setUpdateRefernces(true);
		processor.setUpdateTextualMatches(false);
		processor.setAttribute(RenameFileProcessor.UPDATECLASSNAME, "true");

		checkInitCondition(processor);

		performChange(processor);
		
		
		IFile file = project1.getFile("RenameFileWithClass3.php");
		assertNotNull(file);
		assertTrue(file.exists());
		
		try {
			String content = FileUtils.getContents(file);
			assertEquals(
					"<?php class RenameFileWithClass3{}?>",
					content);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		try {
			String content = FileUtils.getContents(file1);
			assertEquals(
					"<?php include 'RenameFileWithClass3.php'; ?>",
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
