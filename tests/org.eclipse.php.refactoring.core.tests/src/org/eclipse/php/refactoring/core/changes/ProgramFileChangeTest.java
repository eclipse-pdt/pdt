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
package org.eclipse.php.refactoring.core.changes;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.text.Region;
import org.eclipse.php.core.tests.PHPCoreTests;
import org.eclipse.php.internal.core.ast.nodes.Program;
import org.eclipse.php.refactoring.core.rename.AbstractRenameRefactoringTest;
import org.eclipse.php.refactoring.core.test.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ProgramFileChangeTest extends AbstractRenameRefactoringTest {
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
		file = folder.getFile("ProgramFilechange.php");

		InputStream source = new ByteArrayInputStream(
				"<?php class Item { public static function foo(){} } class ItemEx extends Item{public static function foo(){}} ItemEx::foo();?>"
						.getBytes());

		if (!file.exists()) {
			file.create(source, true, new NullProgressMonitor());
		} else {
			file.setContents(source, IFile.FORCE, new NullProgressMonitor());
		}

		PHPCoreTests.waitForIndexer();
		PHPCoreTests.waitForAutoBuild();
	}

	@After
	public void tearDown() throws Exception {
		project1.delete(IResource.FORCE, new NullProgressMonitor());
	}

	@Test
	public void testGetProgram() throws Exception {
		Program program = createProgram(file);

		assertNotNull(program);

		ProgramFileChange change = new ProgramFileChange("TestFile", file,
				program);
		assertEquals(program, change.getProgram());

		assertEquals(program, change.getAdapter(Program.class));
	}

	@Test
	public void testGetCurrentContent() throws Exception {
		Program program = createProgram(file);

		assertNotNull(program);

		ProgramFileChange change = new ProgramFileChange("TestFile", file,
				program);

		try {
			String content = change.getCurrentContent(new Region(0, 126), true,
					2, new NullProgressMonitor());
			assertEquals(
					"<?php class Item { public static function foo(){} } class ItemEx extends Item{public static function foo(){}} ItemEx::foo();?>",
					content);
		} catch (CoreException e) {
			fail(e.getMessage());
		}
	}
}
