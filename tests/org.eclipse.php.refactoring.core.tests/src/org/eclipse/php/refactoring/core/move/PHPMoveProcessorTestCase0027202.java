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
package org.eclipse.php.refactoring.core.move;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import junit.framework.TestCase;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.php.core.tests.PHPCoreTests;
import org.eclipse.php.refactoring.core.test.FileUtils;

public class PHPMoveProcessorTestCase0027202 extends TestCase {
	private IProject project1;
	private IProject project2;

	@Override
	protected void setUp() throws Exception {
		System.setProperty("disableStartupRunner", "true");
		PHPCoreTests.waitForIndexer();
		PHPCoreTests.waitForAutoBuild();

		project1 = FileUtils.createProject("project1");

		IFolder folder = project1.getFolder("src");

		if (!folder.exists()) {
			folder.create(true, true, new NullProgressMonitor());
		}
		IFile file = folder.getFile("test1.php");

		InputStream source = new ByteArrayInputStream(
				"<?php class TestRenameClass{}?>".getBytes());

		if (!file.exists()) {
			file.create(source, true, new NullProgressMonitor());
		} else {
			file.setContents(source, IFile.FORCE, new NullProgressMonitor());
		}

		project2 = FileUtils.createProject("project2");

		folder = project2.getFolder("src");

		if (!folder.exists()) {
			folder.create(true, true, new NullProgressMonitor());
		}

		file = project2.getFile("test2.php");
		source = new ByteArrayInputStream("<?php include('src/test1.php'); ?>"
				.getBytes());

		if (!file.exists()) {
			file.create(source, true, new NullProgressMonitor());
		} else {
			file.setContents(source, IFile.FORCE, new NullProgressMonitor());
		}

		PHPCoreTests.waitForIndexer();
		PHPCoreTests.waitForAutoBuild();
	}

	public void testMoveing() {
		IStructuredSelection selection = new StructuredSelection(project1.getProject().getFolder("/src"));
		PHPMoveProcessor processor = new PHPMoveProcessor(selection);

		processor.setDestination(project2);
		processor.setUpdateReferences(true);

		RefactoringStatus status = processor
				.checkInitialConditions(new NullProgressMonitor());

		assertEquals(IStatus.OK, status.getSeverity());

		status = processor
				.checkFinalConditions(new NullProgressMonitor(), null);
		
		assertEquals(IStatus.ERROR, status.getSeverity());

	}
}
