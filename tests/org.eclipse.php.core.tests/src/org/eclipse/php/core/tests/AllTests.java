/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.core.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.eclipse.php.core.tests.formatter.FormatterTests;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.eclipse.php.core");

		// $JUnit-BEGIN$

		// Model tests:
		// suite.addTest(DomParserTests.suite());
		// suite.addTest(CompilerParserTests.suite());
		// suite.addTest(ErrorReportingTests.suite());
		// suite.addTest(ASTRewriteTests.suite());
		// suite.addTest(ASTMatcherTests.suite());
		// suite.addTest(CommentMapperTests.suite());
		// suite.addTest(StaticScalarExpressionsTests.suite());
		//
		// suite.addTest(CodeAssistTests.suite());
		// suite.addTest(SelectionEngineTests.suite());
		//
		// suite.addTest(ModelStructureTests.suite());
		//
		// suite.addTest(FileNetworkTests.suite());
		// suite.addTest(TypeInferenceTests.suite());
		//
		// suite.addTest(PHPDocParserTests.suite());
		// suite.addTest(PHPDocAwareDeclarationTests.suite());
		//
		// suite.addTest(IncludePathManagerTests.suite());
		// suite.addTest(BindingTests.suite());
		//
		// // Document tests:
		// suite.addTest(DocumentLexerTests.suite());
		suite.addTest(FormatterTests.suite());
		// suite.addTest(PHPPartitionerTests.suite());
		//
		// // phar file support tests
		// suite.addTest(PharFileTest.suite());
		//
		// // PHPElementConciliator test cases
		// // added by qwang.
		// suite.addTest(org.eclipse.php.internal.core.ast.locator.AllTests
		// .suite());
		// // added by zhaozw.
		// suite.addTest(MarkOccurrenceTests.suite());
		//
		// // test language model functions
		// suite.addTest(org.eclipse.php.core.tests.searchEngine.AllTests.suite());

		// $JUnit-END$

		return suite;
	}
}
