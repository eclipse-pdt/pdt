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
package org.eclipse.php.refactoring.core.rename;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.eclipse.core.resources.IFile;
import org.eclipse.php.internal.core.ast.nodes.ASTNode;
import org.eclipse.php.internal.core.ast.nodes.Program;
import org.eclipse.php.refactoring.core.test.PdttFileExt;

public class RenameGlobalVariableProcessorTest extends AbstractRenameRefactoringTest {

	public RenameGlobalVariableProcessorTest(String fileName) {
		super(fileName);
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
			tests.add(new RenameGlobalVariableProcessorTest(fileName) {
				@Override
				protected void runTest() throws Throwable {
					IFile file = project.findFile(testFile.getTestFiles().get(0).getName());

					Program program = createProgram(file);

					assertNotNull(program);

					int start = Integer.valueOf(testFile.getConfig().get("start"));
					ASTNode selectedNode = locateNode(program, start, 0);
					assertNotNull(selectedNode);

					RenameGlobalVariableProcessor processor = new RenameGlobalVariableProcessor(file, selectedNode);

					processor.setNewElementName(testFile.getConfig().get("newName"));
					processor.setUpdateTextualMatches(Boolean.valueOf(testFile.getConfig().get("updateTextualMatches")));

					checkInitCondition(processor);
					performChange(processor);
					checkTestResult(testFile);
				}
				@Override
				protected void tearDown() throws Exception {

				}	
			});

		}
		return tests;

	}

	@Override
	protected String getTestDirectory() {
		return "/resources/rename/renameGblVar/";
	}

}
