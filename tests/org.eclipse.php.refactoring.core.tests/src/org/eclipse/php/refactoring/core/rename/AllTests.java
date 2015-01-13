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

import java.util.List;

import junit.extensions.TestSetup;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.eclipse.php.refactoring.core.test.TestProject;

public class AllTests {

	private static TestProject project;

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for org.eclipse.php.refactoring.core.rename");
		// $JUnit-BEGIN$
		List<TestCase> tests = new RenameClassProcessorTest("").createTest();
		for (TestCase test : tests) {
			suite.addTest(test);
		}
		tests = new RenameTraitProcessorTest("").createTest();
		for (TestCase test : tests) {
			suite.addTest(test);
		}
		// $JUnit-BEGIN$
		tests = new RenameClassMemberProcessorTest("")
				.createTest();
		for (TestCase test : tests) {
			suite.addTest(test);
		}

		tests = new RenameTraitMemberProcessorTest("")
				.createTest();
		for (TestCase test : tests) {
			suite.addTest(test);
		}


		tests = new RenameFunctionProcessorTest("").createTest();
		for (TestCase test : tests) {
			suite.addTest(test);
		}

		tests = new RenameGlobalConstantProcessorTest("").createTest();
		for (TestCase test : tests) {
			suite.addTest(test);
		}

		tests = new RenameGlobalVariableProcessorTest("").createTest();
		for (TestCase test : tests) {
			suite.addTest(test);
		}

		tests = new RenameLocalVariableProcessorTest("").createTest();
		for (TestCase test : tests) {
			suite.addTest(test);
		}

		tests = new RenameResourceProcessorTest("").createTest();
		for (TestCase test : tests) {
			suite.addTest(test);
		}

		suite.addTestSuite(RenameProcessorTestCase0026915.class);
		suite.addTestSuite(RenameProcessorTestCase0026972.class);
		suite.addTestSuite(RenameProcessorTestCase0026988.class);
		suite.addTestSuite(RenameProcessorTestCase0027134.class);
		suite.addTestSuite(RenameFuncProcessorTestCase0027497.class);
		suite.addTestSuite(PHPRenameProcessorRunConfigTestCase0027489.class);
		suite.addTestSuite(PHPRenameProcessorRunConfigTestCase0027489file.class);
		suite.addTestSuite(RenameFolderTestCase1.class);
		suite.addTestSuite(RenameFolderTestCase2.class);
		suite.addTestSuite(RenameFolderTestCase30346.class);
		suite.addTestSuite(RenameLocalVarTest2.class);
		suite.addTestSuite(RenameClassMemberProcessorTest1.class);
		suite.addTestSuite(RenameClassMemberProcessorTest2.class);
		suite.addTestSuite(RenameClassMemberProcessorTest3.class);
		suite.addTestSuite(RenameClassMemberProcessorTest0027555.class);
		suite.addTestSuite(RenameFileTestCase0029095.class);
		suite.addTestSuite(RenameFileWithClass.class);
		suite.addTestSuite(RenameProcessorTestCase0029408.class);
		suite.addTestSuite(RenameProcessorTestCaseZSTD_1006.class);

		// Create a setup wrapper
		TestSetup setup = new TestSetup(suite) {
			@Override
			protected void setUp() throws Exception {
				setUpSuite();
			}

			@Override
			protected void tearDown() throws Exception {
				tearDownSuite();
			}
		};
		return setup;
	}

	protected static void setUpSuite() {
		project = new TestProject("Refactoring");
		System.setProperty("disableStartupRunner", "true");
	}

	protected static void tearDownSuite() {
		try {
			project.delete();
		} catch (Exception e) {
		}
		// System.setProperty("disableStartupRunner",null);
	}
}
