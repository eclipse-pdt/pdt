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

import org.eclipse.php.refactoring.core.test.TestProject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ ExtractVariableRefactoringTest.class, ExtractVariableRefactoringTest1.class, ExtractVariableRefactoringTest2.class,
		ExtractVariableRefactoringTest3.class, ExtractVariableRefactoringTest27457.class, ExtractVariableRefactoringTest26642.class,
		ExtractVariableRefactoringTestGetVariableName.class })
public class AllTests {

	private static TestProject project;

	@BeforeClass
	public static void setUpSuite() {
		project = new TestProject("RefactoringExtractVar");
		System.setProperty("disableStartupRunner", "true");
	}

	@AfterClass
	public static void tearDownSuite() {
		try {
			project.delete();
		} catch (Exception e) {
		}
		// System.setProperty("disableStartupRunner",null,
	}
}
