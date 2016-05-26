/*******************************************************************************
 * Copyright (c) 2009, 2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *     Dawid Pakuła - convert to JUnit4
 *******************************************************************************/
package org.eclipse.php.core.tests;

import org.eclipse.php.core.tests.codeassist.CodeAssistTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
		// Model tests:
		// DomParserTests.class, CompilerParserTests.class,
		// FindUseStatementByAliasTests.class,
		// FindUseStatementByNamespaceTests.class,
		// GetUseStatementsByTests.class, TraitUseStatementVisitorTests.class,
		// TypeDeclarationVisitorTests.class, ErrorReportingTests.class,
		// ASTRewriteTests.Suite.class,
		// ASTMatcherTests.class, CommentMapperTests.class,
		// StaticScalarExpressionsTests.class, CodeAssistTests.class,
		// SelectionEngineTests.class, ModelStructureTests.class,
		// TypeInferenceTests.class, FileNetworkTests.class,
		// PHPDocParserTests.class, PHPDocAwareDeclarationTests.class,
		// IncludePathManagerTests.class, BindingTests.class,
		// // Document tests:
		// DocumentLexerTests.class, PHPPartitionerTests.class,
		// // Phar files
		// PharFileTest.class,
		// // Concilator tests:
		// org.eclipse.php.internal.core.ast.locator.AllTests.class,
		// MarkOccurrenceTests.class,
		// // test language functions
		// org.eclipse.php.core.tests.searchEngine.AllTests.class,
		// PHPTextSequenceUtilitiesTests.class

		CodeAssistTests.class })
public final class AllTests {

}
