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
package org.eclipse.php.refactoring.core.extract.function;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.php.core.tests.PHPCoreTests;
import org.eclipse.php.refactoring.core.test.AbstractRefactoringTest;
import org.eclipse.php.refactoring.core.test.FileInfo;
import org.eclipse.php.refactoring.core.test.PdttFileExt;
import org.eclipse.php.refactoring.core.test.TestProject;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;

public class ExtractFunctionRefactoringTest extends AbstractRefactoringTest {
	public ExtractFunctionRefactoringTest(String name) {
		super(name);
	}
	
	
	@Override
	protected TestProject getProject() {
		return new TestProject("RefactoringExtractFunc");
	}

	public List<TestCase> createTest() {
		List<TestCase> tests = new ArrayList<TestCase>();
		try {
			initFiles();
		} catch (Exception e1) {
			return tests;
		}

		for (final String fileName : filesMap.keySet()) {
			final PdttFileExt testFile = filesMap.get(fileName);
			tests.add(new ExtractFunctionRefactoringTest(fileName) {
				@Override
				protected void runTest() throws Throwable {
					IFile file = project.findFile(testFile.getTestFiles().get(0).getName());

					IStructuredModel model = null;
					try {
						model = StructuredModelManager.getModelManager().createUnManagedStructuredModelFor(file);
					} catch (IOException e) {
						fail(e.getMessage());
					} catch (CoreException e) {
						fail(e.getMessage());
					}
					assertNotNull(model);

					IStructuredDocument structuredDocument = model.getStructuredDocument();
					assertNotNull(structuredDocument);

					int start = Integer.valueOf(testFile.getConfig().get("start"));

					int length = Integer.valueOf(testFile.getConfig().get("length"));

					String visibility = testFile.getConfig().get("visibility");
					
					ExtractFunctionRefactoring processor = new ExtractFunctionRefactoring(DLTKCore.createSourceModuleFrom(file), structuredDocument, start, length);

					if("default".equals(visibility)){
						processor.setVisibility(Modifiers.AccDefault);
					}

					if("public".equals(visibility)){
						processor.setVisibility(Modifiers.AccPublic);
					}

					if("prvate".equals(visibility)){
						processor.setVisibility(Modifiers.AccPrivate);
					}
					
					if("protected".equals(visibility)){
						processor.setVisibility(Modifiers.AccProtected);
					}


					processor.setNewFunctionName(testFile.getConfig().get("newName"));

					checkInitCondition(processor);
					performChange(processor);
					checkTestResult(testFile, structuredDocument);
				}

				@Override
				protected void tearDown() throws Exception {

				}
			});
		}
		return tests;
	}

	protected void checkTestResult(PdttFileExt testFile, IStructuredDocument structuredDocument) {
		List<FileInfo> files = testFile.getExpectedFiles();
		for (FileInfo expFile : files) {
			IFile file = project.findFile(expFile.getName());
			assertTrue(file.exists());
			
			String content = structuredDocument.get();
			
//			String newLine = System.getProperty("line.separator");
//			content = content.replaceAll(newLine, "").replaceAll(" ", "");
//			content = content.replaceAll("\n", "").replaceAll(" ", "");
			
			String diff = PHPCoreTests.compareContentsIgnoreWhitespace(expFile.getContents(), content);
			if (diff != null) {
				fail(diff);
			}
		}
	}
	
	@Override
	protected String getTestDirectory() {
		return "/resources/extractfunc/";
	}
}
