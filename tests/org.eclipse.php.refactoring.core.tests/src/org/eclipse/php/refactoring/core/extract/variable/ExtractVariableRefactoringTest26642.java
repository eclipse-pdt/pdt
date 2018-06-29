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
import static org.junit.Assert.fail;

import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.php.core.tests.TestUtils;
import org.eclipse.php.refactoring.core.test.AbstractRefactoringTest;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ExtractVariableRefactoringTest26642 extends AbstractRefactoringTest {

	private IProject project1;
	private IFile file;

	@Before
	public void setUp() throws Exception {
		project1 = TestUtils.createProject("TestProject1");

		IFolder folder = TestUtils.createFolder(project1, "src");
		file = TestUtils.createFile(folder, "ExtractVariableRefactoringTest26642.php",
				"<?php function tests ($oppp){  return $oppp;}$test = 123;$test1 = 124;echo $test;$test = \"test\" $paste = tests ($test);?>");

		TestUtils.waitForIndexer();
	}

	@After
	public void tearDownListSuite() throws Exception {
		project1.delete(IResource.FORCE, new NullProgressMonitor());
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
			processor = new ExtractVariableRefactoring(DLTKCore.createSourceModuleFrom(file), structuredDocument, 47,
					0);
			processor.setNewVariableName("c");

			checkInitCondition(processor);

		} catch (CoreException e) {
			fail(e.getMessage());
		}

	}

	@Override
	protected void checkInitCondition(Refactoring processor) {
		try {
			RefactoringStatus status = processor.checkInitialConditions(new NullProgressMonitor());
			assertEquals(Status.ERROR, status.getSeverity());
		} catch (OperationCanceledException e1) {
			fail(e1.getMessage());
		} catch (CoreException e1) {
			fail(e1.getMessage());
		}
	}

}
