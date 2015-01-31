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

import org.eclipse.php.refactoring.core.test.TestProject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ExtractFunctionRefactoringTest.class})
public class AllTests {

	private static TestProject project;

	@BeforeClass
	public static void setUpSuite() {
		project = new TestProject("RefactoringExtractFunc");
		System.setProperty("disableStartupRunner", "true");
	}

	@AfterClass
	public static void tearDownSuite() {
		try {
			project.delete();
		} catch (Exception e) {
		}
		//		System.setProperty("disableStartupRunner",null);
	}
}
