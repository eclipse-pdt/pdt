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
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.ast.nodes.ASTNode;
import org.eclipse.php.internal.core.ast.nodes.Program;
import org.eclipse.php.refactoring.core.test.FileUtils;

public class RenameProcessorTestCase0029408 extends
		AbstractRenameRefactoringTest {
	private IProject project1;
	private IProject project2;
	private IFile file;

	@Override
	protected void setUp() throws Exception {
		System.setProperty("disableStartupRunner", "true");
		PHPCoreTests.waitForIndexer();
		PHPCoreTests.waitForAutoBuild();

		project1 = FileUtils.createProject("project1", PHPVersion.PHP5_3);

		IFolder folder = project1.getFolder("src");

		if (!folder.exists()) {
			folder.create(true, true, new NullProgressMonitor());
		}
		file = folder.getFile("test00294081.php");

		InputStream source = new ByteArrayInputStream(
				"<?php class MyClass{const constant = 'constant value';function showCons1tant() {echo self::constant;}}$class = new MyClass ();$class->showConstant ();echo $class::constant;?>"
						.getBytes());

		if (!file.exists()) {
			file.create(source, true, new NullProgressMonitor());
		} else {
			file.setContents(source, IFile.FORCE, new NullProgressMonitor());
		}

		PHPCoreTests.waitForIndexer();
		PHPCoreTests.waitForAutoBuild();
	}

	@Override
	protected void tearDown() throws Exception {
		project1.delete(IResource.FORCE, new NullProgressMonitor());
	}

	public void testRename() {

		Program program = createProgram(file);

		assertNotNull(program);

		int start = 117;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		RenameClassProcessor processor = new RenameClassProcessor(file,
				selectedNode);

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

	@Override
	protected String getTestDirectory() {
		return "";
	}
}
