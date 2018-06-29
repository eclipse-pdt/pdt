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
package org.eclipse.php.refactoring.core.extract.variable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.php.core.tests.TestUtils;
import org.eclipse.php.refactoring.core.test.AbstractRefactoringTest;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ExtractVariableRefactoringTestGetVariableName extends AbstractRefactoringTest {

	private IProject project1;
	private IFile file;

	@Before
	public void setUp() throws Exception {
		project1 = TestUtils.createProject("TestProject1");

		IFolder folder = TestUtils.createFolder(project1, "src");
		file = TestUtils.createFile(folder, "ExtractVariableRefactoringTestGetVariableName.php",
				"<?php function foo(){} foo(); $a = 1; $b=1.1; $c=true;$d=\"ab\"; $e = __LINE__;?>");

		TestUtils.waitForIndexer();
	}

	@After
	public void tearDownListSuite() throws Exception {
		project1.delete(IResource.FORCE, new NullProgressMonitor());
	}

	@Test
	public void testExtractMethod() {

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
			processor = new ExtractVariableRefactoring(DLTKCore.createSourceModuleFrom(file), structuredDocument, 23,
					6);

			processor.checkInitialConditions(new NullProgressMonitor());
			String[] names = processor.guessTempNames();

			assertTrue(names.length > 0);
			assertEquals("foo", names[0]);

		} catch (CoreException e) {
			fail(e.getMessage());
		}

	}

	@Test
	public void testExtractVar() {

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
			processor = new ExtractVariableRefactoring(DLTKCore.createSourceModuleFrom(file), structuredDocument, 35,
					1);

			processor.checkInitialConditions(new NullProgressMonitor());
			String[] names = processor.guessTempNames();

			assertTrue(names.length > 0);
			assertEquals("i", names[0]);

		} catch (CoreException e) {
			fail(e.getMessage());
		}

	}

	@Test
	public void testExtractVar1() {

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
			processor = new ExtractVariableRefactoring(DLTKCore.createSourceModuleFrom(file), structuredDocument, 41,
					3);

			processor.checkInitialConditions(new NullProgressMonitor());
			String[] names = processor.guessTempNames();

			assertTrue(names.length > 0);
			assertEquals("d2", names[0]);

		} catch (CoreException e) {
			fail(e.getMessage());
		}

	}

	@Test
	public void testExtractVar2() {

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
			processor = new ExtractVariableRefactoring(DLTKCore.createSourceModuleFrom(file), structuredDocument, 49,
					4);

			processor.checkInitialConditions(new NullProgressMonitor());
			String[] names = processor.guessTempNames();

			assertTrue(names.length > 0);
			assertEquals("bool", names[0]);

		} catch (CoreException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testExtractVar3() {

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
			processor = new ExtractVariableRefactoring(DLTKCore.createSourceModuleFrom(file), structuredDocument, 57,
					4);

			processor.checkInitialConditions(new NullProgressMonitor());
			String[] names = processor.guessTempNames();

			assertTrue(names.length > 0);
			assertEquals("str", names[0]);

		} catch (CoreException e) {
			fail(e.getMessage());
		}

	}

	@Test
	public void testExtractVar4() {

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
			processor = new ExtractVariableRefactoring(DLTKCore.createSourceModuleFrom(file), structuredDocument, 68,
					8);

			processor.checkInitialConditions(new NullProgressMonitor());
			String[] names = processor.guessTempNames();

			assertTrue(names.length > 0);
			assertEquals("line", names[0]);

		} catch (CoreException e) {
			fail(e.getMessage());
		}

	}
}
