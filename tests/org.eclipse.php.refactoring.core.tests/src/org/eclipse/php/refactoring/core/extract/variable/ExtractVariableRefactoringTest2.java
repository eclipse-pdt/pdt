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
package org.eclipse.php.refactoring.core.extract.variable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.php.core.tests.TestUtils;
import org.eclipse.php.refactoring.core.test.AbstractRefactoringTest;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ExtractVariableRefactoringTest2 extends AbstractRefactoringTest {

	private IProject project1;
	private IFile file;

	@Before
	public void setUp() throws Exception {
		project1 = TestUtils.createProject("TestProject1");

		IFolder folder = TestUtils.createFolder(project1, "src");
		file = TestUtils.createFile(folder, "ExtractVariableRefactoringTest2.php",
				"<?php $a = 4; $c = $a; $a = 5; $b = $a; ?>");

		TestUtils.waitForIndexer();
	}

	@After
	public void tearDownListSuite() throws Exception {
		project1.delete(IResource.FORCE, new NullProgressMonitor());
	}

	@Test
	public void testExtract() {

		IStructuredModel model = null;
		try {
			model = createUnManagedStructuredModelFor(file);
		} catch (IOException e) {
			fail(e.getMessage());
		} catch (CoreException e) {
			fail(e.getMessage());
		}
		assertNotNull(model);

		IStructuredDocument structuredDocument = model.getStructuredDocument();
		assertNotNull(structuredDocument);

		ExtractVariableRefactoring processor;
		try {
			processor = new ExtractVariableRefactoring(DLTKCore.createSourceModuleFrom(file), structuredDocument, 19,
					2);
			processor.setNewVariableName("c");

			RefactoringStatus status = processor.checkInitialConditions(new NullProgressMonitor());
			assertEquals(Status.OK, status.getSeverity());

			status = processor.checkFinalConditions(new NullProgressMonitor());
			assertEquals(Status.WARNING, status.getSeverity());

		} catch (CoreException e) {
			fail(e.getMessage());
		}
	}

	// Testing extract lefthand side expression.
	@Test
	public void testExtract1() {

		IStructuredModel model = null;
		try {
			model = createUnManagedStructuredModelFor(file);
		} catch (IOException e) {
			fail(e.getMessage());
		} catch (CoreException e) {
			fail(e.getMessage());
		}
		assertNotNull(model);

		IStructuredDocument structuredDocument = model.getStructuredDocument();
		assertNotNull(structuredDocument);

		ExtractVariableRefactoring processor;
		try {
			processor = new ExtractVariableRefactoring(DLTKCore.createSourceModuleFrom(file), structuredDocument, 6, 2);
			processor.setNewVariableName("c");

			RefactoringStatus status = processor.checkInitialConditions(new NullProgressMonitor());
			assertEquals(Status.ERROR, status.getSeverity());

		} catch (CoreException e) {
			fail(e.getMessage());
		}
	}

	// Testing duplicated variable name.
	@Test
	public void testExtract2() {

		IStructuredModel model = null;
		try {
			model = createUnManagedStructuredModelFor(file);
		} catch (IOException e) {
			fail(e.getMessage());
		} catch (CoreException e) {
			fail(e.getMessage());
		}
		assertNotNull(model);

		IStructuredDocument structuredDocument = model.getStructuredDocument();
		assertNotNull(structuredDocument);

		ExtractVariableRefactoring processor;
		try {
			processor = new ExtractVariableRefactoring(DLTKCore.createSourceModuleFrom(file), structuredDocument, 11,
					1);
			processor.setNewVariableName("c");

			RefactoringStatus status = processor.checkInitialConditions(new NullProgressMonitor());
			assertEquals(Status.OK, status.getSeverity());

			status = processor.checkFinalConditions(new NullProgressMonitor());
			assertEquals(Status.WARNING, status.getSeverity());
		} catch (CoreException e) {
			fail(e.getMessage());
		}
	}
}
