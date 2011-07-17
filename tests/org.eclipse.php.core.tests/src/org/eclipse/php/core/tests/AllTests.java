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

import org.eclipse.php.core.tests.codeassist.CodeAssistTests;
import org.eclipse.php.core.tests.compiler_ast.parser.CompilerParserTests;
import org.eclipse.php.core.tests.compiler_ast.phpdoc.PHPDocAwareDeclarationTests;
import org.eclipse.php.core.tests.compiler_ast.phpdoc.PHPDocParserTests;
import org.eclipse.php.core.tests.document.lexer.DocumentLexerTests;
import org.eclipse.php.core.tests.document.partitioner.PHPPartitionerTests;
import org.eclipse.php.core.tests.dom_ast.CommentMapperTests;
import org.eclipse.php.core.tests.dom_ast.StaticScalarExpressionsTests;
import org.eclipse.php.core.tests.dom_ast.binding.BindingTests;
import org.eclipse.php.core.tests.dom_ast.matcher.ASTMatcherTests;
import org.eclipse.php.core.tests.dom_ast.parser.DomParserTests;
import org.eclipse.php.core.tests.dom_ast.rewrite.ASTRewriteTests;
import org.eclipse.php.core.tests.errors.ErrorReportingTests;
import org.eclipse.php.core.tests.filenetwork.FileNetworkTests;
import org.eclipse.php.core.tests.formatter.FormatterTests;
import org.eclipse.php.core.tests.includepath.IncludePathManagerTests;
import org.eclipse.php.core.tests.markoccurrence.MarkOccurrenceTests;
import org.eclipse.php.core.tests.model_structure.ModelStructureTests;
import org.eclipse.php.core.tests.phar.PharFileTest;
import org.eclipse.php.core.tests.selection.SelectionEngineTests;
import org.eclipse.php.core.tests.typeinference.TypeInferenceTests;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.eclipse.php.core");

		// $JUnit-BEGIN$

		// Model tests:
		suite.addTest(DomParserTests.suite());
		suite.addTest(CompilerParserTests.suite());
		suite.addTest(ErrorReportingTests.suite());
		suite.addTest(ASTRewriteTests.suite());
		suite.addTest(ASTMatcherTests.suite());
		suite.addTest(CommentMapperTests.suite());
		suite.addTest(StaticScalarExpressionsTests.suite());

		suite.addTest(CodeAssistTests.suite());
		suite.addTest(SelectionEngineTests.suite());

		suite.addTest(ModelStructureTests.suite());

		suite.addTest(FileNetworkTests.suite());
		suite.addTest(TypeInferenceTests.suite());

		suite.addTest(PHPDocParserTests.suite());
		suite.addTest(PHPDocAwareDeclarationTests.suite());

		suite.addTestSuite(IncludePathManagerTests.class);
		suite.addTestSuite(BindingTests.class);

		// Document tests:
		suite.addTest(DocumentLexerTests.suite());
		suite.addTest(FormatterTests.suite());
		suite.addTestSuite(PHPPartitionerTests.class);

		// phar file support tests
		suite.addTest(PharFileTest.suite());

		// PHPElementConciliator test cases
		// added by qwang.
		suite.addTest(org.eclipse.php.internal.core.ast.locator.AllTests
				.suite());
		// added by zhaozw.
		suite.addTest(MarkOccurrenceTests.suite());

		// test language model functions
		suite.addTest(org.eclipse.php.core.tests.searchEngine.AllTests.suite());

		// $JUnit-END$

		return suite;
	}
}
