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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.php.core.tests.PHPCoreTests;
import org.eclipse.php.refactoring.core.test.AbstractRefactoringTest;
import org.eclipse.php.refactoring.core.test.FileUtils;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;

public class ExtractVariableRefactoringTest2 extends AbstractRefactoringTest {

	private IProject project1;
	private IFile file;

	@Override
	protected String getTestDirectory() {

		return "";
	}

	@Override
	protected void setUp() throws Exception {

		System.setProperty("disableStartupRunner", "true");
		PHPCoreTests.waitForIndexer();
		PHPCoreTests.waitForAutoBuild();

		project1 = FileUtils.createProject("TestProject1");

		IFolder folder = project1.getFolder("src");

		if (!folder.exists()) {
			folder.create(true, true, new NullProgressMonitor());
		}
		file = folder.getFile("ExtractVariableRefactoringTest2.php");

		InputStream source = new ByteArrayInputStream(
				"<?php $a = 4; $c = $a; $a = 5; $b = $a; ?>".getBytes());
		if (!file.exists()) {
			file.create(source, true, new NullProgressMonitor());
		} else {
			file.setContents(source, IFile.FORCE, new NullProgressMonitor());
		}
	}

	@Override
	protected void tearDown() throws Exception {

	}

	public void testExtract() {

		IStructuredModel model = null;
		try {
			model = StructuredModelManager.getModelManager()
					.createUnManagedStructuredModelFor(file);
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
			processor = new ExtractVariableRefactoring(DLTKCore
					.createSourceModuleFrom(file), structuredDocument, 19, 2);
			processor.setNewVariableName("c");

			RefactoringStatus status = processor
					.checkInitialConditions(new NullProgressMonitor());
			assertEquals(Status.OK, status.getSeverity());

			status = processor.checkFinalConditions(new NullProgressMonitor());
			assertEquals(Status.WARNING, status.getSeverity());

		} catch (CoreException e) {
			fail(e.getMessage());
		}
	}

	//Testing extract lefthand side expression.
	public void testExtract1() {

		IStructuredModel model = null;
		try {
			model = StructuredModelManager.getModelManager()
					.createUnManagedStructuredModelFor(file);
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
			processor = new ExtractVariableRefactoring(DLTKCore
					.createSourceModuleFrom(file), structuredDocument, 6, 2);
			processor.setNewVariableName("c");

			RefactoringStatus status = processor
					.checkInitialConditions(new NullProgressMonitor());
			assertEquals(Status.ERROR, status.getSeverity());

		} catch (CoreException e) {
			fail(e.getMessage());
		}
	}

	//Testing duplicated variable name.
	public void testExtract2() {

		IStructuredModel model = null;
		try {
			model = StructuredModelManager.getModelManager()
					.createUnManagedStructuredModelFor(file);
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
			processor = new ExtractVariableRefactoring(DLTKCore
					.createSourceModuleFrom(file), structuredDocument, 11, 1);
			processor.setNewVariableName("c");

			RefactoringStatus status = processor
					.checkInitialConditions(new NullProgressMonitor());
			assertEquals(Status.OK, status.getSeverity());
			
			status = processor.checkFinalConditions(new NullProgressMonitor());
			assertEquals(Status.WARNING, status.getSeverity());
		} catch (CoreException e) {
			fail(e.getMessage());
		}
	}
}
