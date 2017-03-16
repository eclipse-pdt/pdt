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

import static org.junit.Assert.assertNotNull;

import org.eclipse.core.resources.IFile;
import org.eclipse.php.core.ast.nodes.Program;
import org.eclipse.php.core.tests.runner.PDTTList;
import org.eclipse.php.refactoring.core.test.AbstractPDTTListRefactoringTest;
import org.eclipse.php.refactoring.core.test.PdttFileExt;
import org.junit.Test;

public class RenameResourceProcessorTest extends AbstractPDTTListRefactoringTest {
	public RenameResourceProcessorTest(String[] fileNames) {
		super(fileNames);
	}

	@PDTTList.Parameters
	public static String[] dirs = { "/resources/rename/renameResource/" }; //$NON-NLS-1$

	@Test
	public void testRename(String fileName) throws Exception {
		PdttFileExt testFile = filesMap.get(fileName);
		IFile file = project.findFile(testFile.getTestFiles().get(0).getName());

		Program program = createProgram(file);

		assertNotNull(program);

		RenameFileProcessor processor = new RenameFileProcessor(file, program);
		processor.setNewElementName(testFile.getConfig().get("newName"));

		processor.setUpdateRefernces(Boolean.valueOf(testFile.getConfig().get("updateReference")));
		processor.setAttribute(RenameFileProcessor.UPDATECLASSNAME, testFile.getConfig().get("updateClassName"));

		checkInitCondition(processor);
		performChange(processor);
		checkTestResult(testFile);
	}
}
