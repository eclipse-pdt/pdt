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
package org.eclipse.php.refactoring.core.move;

import static org.junit.Assert.assertEquals;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.php.core.tests.TestUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PHPMoveProcessorTestCase0027202 {
	private IProject project1;
	private IProject project2;

	@Before
	public void setUp() throws Exception {

		project1 = TestUtils.createProject("project1");
		IFolder folder = TestUtils.createFolder(project1, "src");
		TestUtils.createFile(folder, "test1.php", "<?php class TestRenameClass{}?>");

		project2 = TestUtils.createProject("project2");
		folder = TestUtils.createFolder(project2, "src");
		TestUtils.createFile(project2, "test2.php", "<?php include('src/test1.php'); ?>");

		TestUtils.waitForIndexer();
	}

	@After
	public void tearDown() throws Exception {
		project1.delete(IResource.FORCE, new NullProgressMonitor());
		project2.delete(IResource.FORCE, new NullProgressMonitor());
	}

	@Test
	public void testMove() {
		IStructuredSelection selection = new StructuredSelection(project1.getProject().getFolder("/src"));
		PHPMoveProcessor processor = new PHPMoveProcessor(selection);

		processor.setDestination(project2);
		processor.setUpdateReferences(true);

		RefactoringStatus status = processor.checkInitialConditions(new NullProgressMonitor());

		assertEquals(IStatus.OK, status.getSeverity());

		status = processor.checkFinalConditions(new NullProgressMonitor(), null);

		assertEquals(IStatus.ERROR, status.getSeverity());

	}
}
