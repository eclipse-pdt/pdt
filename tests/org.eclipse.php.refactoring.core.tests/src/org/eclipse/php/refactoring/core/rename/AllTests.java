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

import org.eclipse.php.refactoring.core.test.TestProject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ RenameClassProcessorTest.class, RenameTraitProcessorTest.class,
		RenameClassMemberProcessorTest.class, RenameTraitMemberProcessorTest.class, RenameFunctionProcessorTest.class,
		RenameGlobalConstantProcessorTest.class, RenameGlobalVariableProcessorTest.class,
		RenameLocalVariableProcessorTest.class, RenameResourceProcessorTest.class, RenameProcessorTestCase0026915.class,
		RenameProcessorTestCase0026972.class, RenameProcessorTestCase0026988.class,
		RenameProcessorTestCase0027134.class, RenameFuncProcessorTestCase0027497.class,
		PHPRenameProcessorRunConfigTestCase0027489.class, PHPRenameProcessorRunConfigTestCase0027489file.class,
		RenameFolderTestCase1.class, RenameFolderTestCase2.class, RenameFolderTestCase30346.class,
		RenameLocalVarTest2.class, RenameClassMemberProcessorTest1.class, RenameClassMemberProcessorTest2.class,
		RenameClassMemberProcessorTest3.class, RenameClassMemberProcessorTest0027555.class,
		RenameFileTestCase0029095.class, RenameFileWithClass.class, RenameProcessorTestCase0029408.class,
		RenameProcessorTestCaseZSTD_1006.class })
public class AllTests {

	private static TestProject project;

	@BeforeClass
	public static void setUpSuite() {
		project = new TestProject("Refactoring");
		System.setProperty("disableStartupRunner", "true");
	}

	@AfterClass
	public static void tearDownSuite() {
		try {
			project.delete();
		} catch (Exception e) {
		}
		// System.setProperty("disableStartupRunner",null);
	}
}
