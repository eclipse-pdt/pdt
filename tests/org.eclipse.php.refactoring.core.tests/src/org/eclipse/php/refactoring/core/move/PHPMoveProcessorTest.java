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

import static org.junit.Assert.*;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.php.core.tests.runner.PDTTList;
import org.eclipse.php.internal.core.ast.nodes.Program;
import org.eclipse.php.refactoring.core.test.AbstractRefactoringTest;
import org.eclipse.php.refactoring.core.test.PdttFileExt;
import org.eclipse.php.refactoring.core.test.TestProject;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(PDTTList.class)
public class PHPMoveProcessorTest extends AbstractRefactoringTest {

	
	public PHPMoveProcessorTest(String[] fileNames) {
		super(fileNames);
	}
	
	@Override
	protected TestProject getProject() {
		return new TestProject("RefactoringMove");
	}
	
	@PDTTList.Parameters
	public static String[] dirs = {"/resources/move/"}; //$NON-NLS-1$

	@Test
	public void test(String fileName) throws Exception {
		PdttFileExt testFile = filesMap.get(fileName);
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
			destination = project.createFolder(dest);
		}

		processor.setDestination(destination);

		checkInitCondition(processor);
		performChange(processor);
		checkTestResult(testFile);
	}
}
