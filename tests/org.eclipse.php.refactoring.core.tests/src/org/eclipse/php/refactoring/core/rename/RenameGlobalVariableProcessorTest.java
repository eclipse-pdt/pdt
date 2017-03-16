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
import org.eclipse.php.core.ast.nodes.ASTNode;
import org.eclipse.php.core.ast.nodes.Program;
import org.eclipse.php.core.tests.runner.PDTTList;
import org.eclipse.php.refactoring.core.test.AbstractPDTTListRefactoringTest;
import org.eclipse.php.refactoring.core.test.PdttFileExt;
import org.junit.Test;

public class RenameGlobalVariableProcessorTest extends AbstractPDTTListRefactoringTest {

	public RenameGlobalVariableProcessorTest(String[] fileNames) {
		super(fileNames);
	}

	@PDTTList.Parameters
	public static String[] dirs = { "/resources/rename/renameGblVar/" }; //$NON-NLS-1$

	@Test
	public void testRename(String fileName) throws Exception {
		PdttFileExt testFile = filesMap.get(fileName);
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

}
