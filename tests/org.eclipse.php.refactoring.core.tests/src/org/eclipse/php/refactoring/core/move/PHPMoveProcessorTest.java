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
package org.eclipse.php.refactoring.core.move;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.php.internal.core.ast.nodes.Program;
import org.eclipse.php.refactoring.core.test.AbstractRefactoringTest;
import org.eclipse.php.refactoring.core.test.PdttFileExt;
import org.eclipse.php.refactoring.core.test.TestProject;

public class PHPMoveProcessorTest extends AbstractRefactoringTest {

	public PHPMoveProcessorTest() {
		super();
	}

	public PHPMoveProcessorTest(String name) {
		super(name);
	}
	
	@Override
	protected TestProject getProject() {
		return new TestProject("RefactoringMove");
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
			tests.add(new PHPMoveProcessorTest(fileName) {
				@Override
				protected void runTest() throws Throwable {
					IFile file = project.findFile(testFile.getTestFiles().get(0).getName());

					Program program = createProgram(file);
					assertNotNull(program);
					PHPMoveProcessor processor = new PHPMoveProcessor(file);

					processor.setUpdateReferences(Boolean.valueOf(testFile.getConfig().get("updateReference")));

					IContainer destination = null;

					String dest = testFile.getConfig().get("dest");
					if ("/".equals(dest)) {
						destination = project.getProject();
					} else {
						try {
							destination = project.createFolder(dest);
						} catch (CoreException e) {
							fail(e.getMessage());
						}
					}

					processor.setDestination(destination);

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
		return "/resources/move/";
	}
}
