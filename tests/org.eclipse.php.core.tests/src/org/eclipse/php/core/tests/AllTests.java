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
import org.eclipse.php.core.tests.codeassist.scope.CodeAssistScopeTests;
import org.eclipse.php.core.tests.compiler_ast.parser.CompilerParserTests;
import org.eclipse.php.core.tests.compiler_ast.parser.FindUseStatementByAliasTests;
import org.eclipse.php.core.tests.compiler_ast.parser.FindUseStatementByNamespaceTests;
import org.eclipse.php.core.tests.compiler_ast.parser.GetUseStatementsByTests;
import org.eclipse.php.core.tests.compiler_ast.parser.TraitUseStatementVisitorTests;
import org.eclipse.php.core.tests.compiler_ast.parser.TypeDeclarationVisitorTests;
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
import org.eclipse.php.core.tests.includepath.IncludePathManagerTests;
import org.eclipse.php.core.tests.markoccurrence.MarkOccurrenceTests;
import org.eclipse.php.core.tests.model_structure.ModelStructureTests;
import org.eclipse.php.core.tests.phar.PharFileTest;
import org.eclipse.php.core.tests.phpmodelutils.PHPModelUtilsTests;
import org.eclipse.php.core.tests.selection.SelectionEngineTests;
import org.eclipse.php.core.tests.text.PHPTextSequenceUtilitiesTests;
import org.eclipse.php.core.tests.typeinference.TypeInferenceTests;
import org.junit.ClassRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
		// Model tests:
		DomParserTests.class, CompilerParserTests.class, FindUseStatementByAliasTests.class,
		FindUseStatementByNamespaceTests.class, GetUseStatementsByTests.class, TraitUseStatementVisitorTests.class,
		TypeDeclarationVisitorTests.class, ErrorReportingTests.class, ASTRewriteTests.Suite.class,
		ASTMatcherTests.class, CommentMapperTests.class, StaticScalarExpressionsTests.class, CodeAssistScopeTests.class,
		CodeAssistTests.class, SelectionEngineTests.class, ModelStructureTests.class, TypeInferenceTests.class,
		FileNetworkTests.class, PHPDocParserTests.class, PHPDocAwareDeclarationTests.class,
		IncludePathManagerTests.class, BindingTests.class, PHPModelUtilsTests.class,
		// Document tests:
		DocumentLexerTests.class, PHPPartitionerTests.class,
		// Phar files
		PharFileTest.class,
		// Concilator tests:
		org.eclipse.php.internal.core.ast.locator.AllTests.class, MarkOccurrenceTests.class,
		// test language functions
		org.eclipse.php.core.tests.searchEngine.AllTests.class, PHPTextSequenceUtilitiesTests.class })
public final class AllTests {

	@ClassRule
	public static TestWatcher watcher = new TestAllSuiteWatcher();

}
