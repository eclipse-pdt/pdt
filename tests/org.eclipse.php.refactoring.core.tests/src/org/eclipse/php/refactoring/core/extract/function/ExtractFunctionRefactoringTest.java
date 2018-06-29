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
package org.eclipse.php.refactoring.core.extract.function;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.php.core.tests.TestUtils;
import org.eclipse.php.core.tests.runner.PDTTList;
import org.eclipse.php.refactoring.core.test.AbstractPDTTListRefactoringTest;
import org.eclipse.php.refactoring.core.test.FileInfo;
import org.eclipse.php.refactoring.core.test.PdttFileExt;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.junit.Test;

public class ExtractFunctionRefactoringTest extends AbstractPDTTListRefactoringTest {

	@PDTTList.Parameters
	public static String[] dirs = { "/resources/extractfunc/" }; //$NON-NLS-1$

	public ExtractFunctionRefactoringTest(String[] fileNames) {
		super(fileNames);
	}

	@Test
	public void testExtract(String fileName) {
		PdttFileExt testFile = filesMap.get(fileName);
		IFile file = project.findFile(testFile.getTestFiles().get(0).getName());

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

		int start = Integer.valueOf(testFile.getConfig().get("start"));

		int length = Integer.valueOf(testFile.getConfig().get("length"));

		String visibility = testFile.getConfig().get("visibility");

		ExtractFunctionRefactoring processor = new ExtractFunctionRefactoring(DLTKCore.createSourceModuleFrom(file),
				structuredDocument, start, length);

		if ("default".equals(visibility)) {
			processor.setVisibility(Modifiers.AccDefault);
		}

		if ("public".equals(visibility)) {
			processor.setVisibility(Modifiers.AccPublic);
		}

		if ("prvate".equals(visibility)) {
			processor.setVisibility(Modifiers.AccPrivate);
		}

		if ("protected".equals(visibility)) {
			processor.setVisibility(Modifiers.AccProtected);
		}

		processor.setNewFunctionName(testFile.getConfig().get("newName"));

		checkInitCondition(processor);
		performChange(processor);
		checkTestResult(testFile, structuredDocument);
	}

	protected void checkTestResult(PdttFileExt testFile, IStructuredDocument structuredDocument) {
		List<FileInfo> files = testFile.getExpectedFiles();
		for (FileInfo expFile : files) {
			IFile file = project.findFile(expFile.getName());
			assertTrue(file.exists());

			String content = structuredDocument.get();

			// String newLine = System.getProperty("line.separator");
			// content = content.replaceAll(newLine, "").replaceAll(" ", "");
			// content = content.replaceAll("\n", "").replaceAll(" ", "");

			String diff = TestUtils.compareContentsIgnoreWhitespace(expFile.getContents(), content);
			if (diff != null) {
				fail(diff);
			}
		}
	}
}
