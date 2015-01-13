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
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.php.core.tests.PHPCoreTests;
import org.eclipse.php.refactoring.core.test.AbstractRefactoringTest;
import org.eclipse.php.refactoring.core.test.FileUtils;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;

public class ExtractVariableRefactoringTestGetVariableName extends
		AbstractRefactoringTest {

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
		file = folder.getFile("ExtractVariableRefactoringTestGetVariableName.php");

		InputStream source = new ByteArrayInputStream(
				"<?php function foo(){} foo(); $a = 1; $b=1.1; $c=true;$d=\"ab\"; $e = __LINE__;?>".getBytes());

		if (!file.exists()) {
			file.create(source, true, new NullProgressMonitor());
		} else {
			file.setContents(source, IFile.FORCE, new NullProgressMonitor());
		}

	}

	@Override
	protected void tearDown() throws Exception {

	}

	public void testExtractMethod() {

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
			processor = new ExtractVariableRefactoring(
					DLTKCore.createSourceModuleFrom(file), structuredDocument,
					23, 6);
			
			processor.checkInitialConditions(new NullProgressMonitor());
			String[] names = processor.guessTempNames();

			assertTrue(names.length>0);
			assertEquals("foo", names[0]);

		} catch (CoreException e) {
			fail(e.getMessage());
		}

	}
	
	public void testExtractVar() {

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
			processor = new ExtractVariableRefactoring(
					DLTKCore.createSourceModuleFrom(file), structuredDocument,
					35, 1);
			
			processor.checkInitialConditions(new NullProgressMonitor());
			String[] names = processor.guessTempNames();

			assertTrue(names.length>0);
			assertEquals("i", names[0]);

		} catch (CoreException e) {
			fail(e.getMessage());
		}

	}
	
	public void testExtractVar1() {

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
			processor = new ExtractVariableRefactoring(
					DLTKCore.createSourceModuleFrom(file), structuredDocument,
					41, 3);
			
			processor.checkInitialConditions(new NullProgressMonitor());
			String[] names = processor.guessTempNames();

			assertTrue(names.length>0);
			assertEquals("d2", names[0]);

		} catch (CoreException e) {
			fail(e.getMessage());
		}

	}
	
	public void testExtractVar2() {

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
			processor = new ExtractVariableRefactoring(
					DLTKCore.createSourceModuleFrom(file), structuredDocument,
					49, 4);
			
			processor.checkInitialConditions(new NullProgressMonitor());
			String[] names = processor.guessTempNames();

			assertTrue(names.length>0);
			assertEquals("bool", names[0]);

		} catch (CoreException e) {
			fail(e.getMessage());
		}
	}
	
	public void testExtractVar3() {

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
			processor = new ExtractVariableRefactoring(
					DLTKCore.createSourceModuleFrom(file), structuredDocument,
					57, 4);
			
			processor.checkInitialConditions(new NullProgressMonitor());
			String[] names = processor.guessTempNames();

			assertTrue(names.length>0);
			assertEquals("str", names[0]);

		} catch (CoreException e) {
			fail(e.getMessage());
		}

	}
	public void testExtractVar4() {

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
			processor = new ExtractVariableRefactoring(
					DLTKCore.createSourceModuleFrom(file), structuredDocument,
					68, 8);
			
			processor.checkInitialConditions(new NullProgressMonitor());
			String[] names = processor.guessTempNames();

			assertTrue(names.length>0);
			assertEquals("line", names[0]);

		} catch (CoreException e) {
			fail(e.getMessage());
		}

	}
}
