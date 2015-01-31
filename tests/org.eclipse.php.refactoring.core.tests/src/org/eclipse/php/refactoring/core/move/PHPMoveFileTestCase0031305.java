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

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.php.core.tests.PHPCoreTests;
import org.eclipse.php.refactoring.core.test.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PHPMoveFileTestCase0031305 {

	private IProject project1;
	private IFile file;
	private IProject project2;

	@Before
	public void setUp() throws Exception {
		PHPCoreTests.waitForIndexer();
		PHPCoreTests.waitForAutoBuild();

		project1 = FileUtils.createProject("project1");

		file = project1.getFile("PHPMoveFileTestCase0031305.php");

		InputStream source = new ByteArrayInputStream(
				"<?php $phpbb_root_path = './'; include($phpbb_root_path . 'common' );?>"
						.getBytes());

		if (!file.exists()) {
			file.create(source, true, new NullProgressMonitor());
		} else {
			file.setContents(source, IFile.FORCE, new NullProgressMonitor());
		}

		project2 = FileUtils.createProject("project2");
		PHPCoreTests.waitForIndexer();
		PHPCoreTests.waitForAutoBuild();
	}

	@Test
	public void testMove() {
		IStructuredSelection selection = new StructuredSelection(project1
				.getProject().getFile("PHPMoveFileTestCase0031305.php"));

		PHPMoveProcessor processor = new PHPMoveProcessor(selection);

		processor.setDestination(project2);
		processor.setUpdateReferences(true);

		RefactoringStatus status = processor
				.checkInitialConditions(new NullProgressMonitor());

		assertEquals(IStatus.OK, status.getSeverity());

		status = processor
				.checkFinalConditions(new NullProgressMonitor(), null);

		assertEquals(IStatus.OK, status.getSeverity());

	}

	@After
	public void tearDown() throws Exception {
		project1.delete(IResource.FORCE, new NullProgressMonitor());
		project2.delete(IResource.FORCE, new NullProgressMonitor());
	}

}
