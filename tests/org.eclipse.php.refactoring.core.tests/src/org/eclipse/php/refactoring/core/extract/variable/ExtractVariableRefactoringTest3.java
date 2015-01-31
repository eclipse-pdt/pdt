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

import static org.junit.Assert.*;

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
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ExtractVariableRefactoringTest3 extends AbstractRefactoringTest {

	private IProject project1;
	private IFile file;

	@Before
	public void setUp() throws Exception {

		System.setProperty("disableStartupRunner", "true");
		PHPCoreTests.waitForIndexer();
		PHPCoreTests.waitForAutoBuild();

		project1 = FileUtils.createProject("TestProject1");

		IFolder folder = project1.getFolder("src");

		if (!folder.exists()) {
			folder.create(true, true, new NullProgressMonitor());
		}
		file = folder.getFile("ExtractVariableRefactoringTest3.php");

		InputStream source = new ByteArrayInputStream(
				"<?php function foo(){$a = 4; $c = $a;} ?>".getBytes());
		if (!file.exists()) {
			file.create(source, true, new NullProgressMonitor());
		} else {
			file.setContents(source, IFile.FORCE, new NullProgressMonitor());
		}
	}

	@After
	public void tearDown() throws Exception {

	}

	// Testing extract lefthand side expression.
	@Test
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
					.createSourceModuleFrom(file), structuredDocument, 26, 1);
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
