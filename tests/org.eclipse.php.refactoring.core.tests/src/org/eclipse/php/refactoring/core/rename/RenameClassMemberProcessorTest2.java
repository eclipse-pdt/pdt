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

public class RenameClassMemberProcessorTest2 extends
		AbstractRenameRefactoringTest {

	private IProject project1;
	private IFile file;

	@Override
	protected void setUp() throws Exception {
		PHPCoreTests.waitForIndexer();
		PHPCoreTests.waitForAutoBuild();

		project1 = FileUtils.createProject("project1");

		IFolder folder = project1.getFolder("src");

		if (!folder.exists()) {
			folder.create(true, true, new NullProgressMonitor());
		}
		file = folder.getFile("test22.php");

		InputStream source = new ByteArrayInputStream(
				"<?php class Item { public static $title;} class ItemEx extends Item{public static $title;} ItemEx::$title;?>"
						.getBytes());

		if (!file.exists()) {
			file.create(source, true, new NullProgressMonitor());
		} else {
			file.setContents(source, IFile.FORCE, new NullProgressMonitor());
		}

		PHPCoreTests.waitForIndexer();
		PHPCoreTests.waitForAutoBuild();
	}

	public void testRename1() {
		Program program = createProgram(file);

		assertNotNull(program);

		int start = 102;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		RenameClassMemberProcessor processor = new RenameClassMemberProcessor(
				file, selectedNode);
		processor.setNewElementName("title1");

		checkInitCondition(processor);

		performChange(processor);

		try {
			String content = FileUtils.getContents(file);
			assertEquals(
					"<?php class Item { public static $title1;} class ItemEx extends Item{public static $title1;} ItemEx::$title1;?>",
					content);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected String getTestDirectory() {
		return "";
	}

	@Override
	protected void tearDown() throws Exception {
		project1.delete(IResource.FORCE, new NullProgressMonitor());
	}
}
