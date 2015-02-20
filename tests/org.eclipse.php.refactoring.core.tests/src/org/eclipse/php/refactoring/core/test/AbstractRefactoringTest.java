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
package org.eclipse.php.refactoring.core.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor;
import org.eclipse.php.core.tests.PDTTUtils;
import org.eclipse.php.core.tests.PHPCoreTests;
import org.eclipse.php.core.tests.runner.PDTTList;
import org.eclipse.php.internal.core.ast.nodes.Program;
import org.eclipse.php.refactoring.core.utils.ASTUtils;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.junit.Assert;
import org.osgi.framework.Bundle;

public abstract class AbstractRefactoringTest {

	protected String[] fileNames = null;
	protected static final char OFFSET_CHAR = '|';
	protected TestProject project;
	protected Map<String, PdttFileExt> filesMap = new LinkedHashMap<String, PdttFileExt>();

	public AbstractRefactoringTest() {
	}
	
	protected AbstractRefactoringTest(String[] fileNames) {
		this.fileNames = fileNames;
	}

	@PDTTList.BeforeList
	public void setUpSuite() throws Exception {
		project = getProject();
		initFiles(fileNames);
	}

	protected void initFiles(String[] fileNames) throws Exception {
		project = getProject();
		for (final String fileName : fileNames) {
			final PdttFileExt pdttFile = new PdttFileExt(getBundle(), fileName);
			for (FileInfo testFile : pdttFile.getTestFiles()) {
				project.createFile(testFile.getName(), getContents(pdttFile, testFile));
			}
			filesMap.put(fileName, pdttFile);
		}

		PHPCoreTests.waitForIndexer();
		PHPCoreTests.waitForAutoBuild();
	}

	private String getContents(PdttFileExt pdttFile, FileInfo testFile) {
		String data = testFile.getContents();
		int offset = data.lastIndexOf(OFFSET_CHAR);
		if (offset < 0) {
			return data;
		}
		pdttFile.getConfig().put("start", String.valueOf(offset));
		// replace the offset character
		data = data.substring(0, offset) + data.substring(offset + 1);
		return data;
	}

	protected TestProject getProject() {
		return new TestProject("Refactoring");
	}

	@PDTTList.AfterList
	public void tearDown() throws Exception {
		project.delete();
	}

	protected Program createProgram(IFile file) throws Exception {
		ISourceModule sourceModule = DLTKCore.createSourceModuleFrom(file);
		Program program = null;
		program = ASTUtils.createProgramFromSource(sourceModule);
		return program;
	}

	protected IFile createFile(String name, String content) {
		IFile file = null;
		try {
			file = project.createFile(name, content);
		} catch (CoreException e2) {
			fail(e2.getMessage());
		}
		assertNotNull(file);
		return file;
	}

	protected void performChange(RefactoringProcessor processor) {
		try {
			Change change = processor.createChange(new NullProgressMonitor());
			if (change != null) {
				change.perform(new NullProgressMonitor());
			}
		} catch (OperationCanceledException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} catch (CoreException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	protected void checkInitCondition(RefactoringProcessor processor) {
		try {
			RefactoringStatus status = processor.checkInitialConditions(new NullProgressMonitor());
			assertEquals(Status.OK, status.getSeverity());
		} catch (OperationCanceledException e1) {
			fail(e1.getMessage());
		} catch (CoreException e1) {
			fail(e1.getMessage());
		}
	}

	protected void checkFinalCondition(RefactoringProcessor processor) {
		try {
			RefactoringStatus status = processor.checkFinalConditions(new NullProgressMonitor(), null);
			assertNotSame(Status.ERROR, status.getSeverity());
		} catch (OperationCanceledException e1) {
			fail(e1.getMessage());
		} catch (CoreException e1) {
			fail(e1.getMessage());
		}
	}

	protected void performChange(Refactoring processor) {
		try {
			Change change = processor.createChange(new NullProgressMonitor());
			change.perform(new NullProgressMonitor());
		} catch (OperationCanceledException e) {
			fail(e.getMessage());
		} catch (CoreException e) {
			fail(e.getMessage());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	protected void checkInitCondition(Refactoring processor) {
		try {
			RefactoringStatus status = processor.checkInitialConditions(new NullProgressMonitor());
			assertEquals(Status.OK, status.getSeverity());
		} catch (OperationCanceledException e1) {
			fail(e1.getMessage());
		} catch (CoreException e1) {
			fail(e1.getMessage());
		}
	}

	protected void checkTestResult(PdttFileExt pdttFile) {
		PHPCoreTests.waitForIndexer();
		PHPCoreTests.waitForAutoBuild();
		List<FileInfo> files = pdttFile.getExpectedFiles();
		for (FileInfo expFile : files) {
			IFile file = project.findFile(expFile.getName());
			assertTrue(file.exists());
			try {
				PDTTUtils.assertContents(getContents(pdttFile, expFile), FileUtils.getContents(file));
				// assertEquals(getContents(pdttFile,expFile),
				// FileUtils.getContents(file));
			} catch (IOException e) {
				fail(e.getMessage());
			}
		}
	}

	@PDTTList.Context
	public static Bundle getBundle() {
		return Activator.getDefault().getBundle();
	}
	
	public static IStructuredModel createUnManagedStructuredModelFor(IFile file) throws IOException, CoreException
	{
		IStructuredModel model = null;
		try {
			model = StructuredModelManager.getModelManager().createUnManagedStructuredModelFor(file);
		} catch (Exception e) {
			try {
				Thread.sleep(3000);
				model = StructuredModelManager.getModelManager().createUnManagedStructuredModelFor(file);
			} catch (InterruptedException e1) {
				Assert.fail(e1.getMessage());
			}
		}
		return model;
	}
}
