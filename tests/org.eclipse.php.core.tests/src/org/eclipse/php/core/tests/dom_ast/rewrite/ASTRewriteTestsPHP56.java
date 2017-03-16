/*******************************************************************************
 * Copyright (c) 2009,2014 IBM Corporation and others.
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
package org.eclipse.php.core.tests.dom_ast.rewrite;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.ast.nodes.FormalParameter;
import org.eclipse.php.core.ast.nodes.UseStatement;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

/**
 * AST rewrite test which tests the ASTRewriteAnalyzer implementation.
 * 
 */
public class ASTRewriteTestsPHP56 extends ASTRewriteTestsPHP55 {

	@RunWith(org.junit.runners.Suite.class)
	@SuiteClasses({ ASTRewriteTestsPHP56.class, NodeDeletionTestsPHP56.class })
	public static class Suite {
	}

	@Override
	protected PHPVersion getPHPVersion() {
		return PHPVersion.PHP5_6;
	}

	@Test
	public void variadicParameterSet() throws Exception {
		String str = "<?php function test($tmp) {} \n ?>";
		initialize(str);

		List<FormalParameter> statements = getAllOfType(program, FormalParameter.class);
		assertTrue("Unexpected list size.", statements.size() == 1);
		statements.get(0).setIsVariadic(true);

		rewrite();
		checkResult("<?php function test(...$tmp) {} \n ?>");
	}

	@Test
	public void variadicParameterUnset() throws Exception {
		String str = "<?php function test(...$tmp) {} \n ?>";
		initialize(str);

		List<FormalParameter> statements = getAllOfType(program, FormalParameter.class);
		assertTrue("Unexpected list size.", statements.size() == 1);
		statements.get(0).setIsVariadic(false);

		rewrite();
		checkResult("<?php function test($tmp) {} \n ?>");
	}

	@Test
	public void referenceVariadicParameterSet() throws Exception {
		String str = "<?php function test(&$tmp) {} \n ?>";
		initialize(str);

		List<FormalParameter> statements = getAllOfType(program, FormalParameter.class);
		assertTrue("Unexpected list size.", statements.size() == 1);
		statements.get(0).setIsVariadic(true);

		rewrite();
		checkResult("<?php function test(&...$tmp) {} \n ?>");
	}

	@Test
	public void referenceVariadicParameterUnset() throws Exception {
		String str = "<?php function test(&...$tmp) {} \n ?>";
		initialize(str);

		List<FormalParameter> statements = getAllOfType(program, FormalParameter.class);
		assertTrue("Unexpected list size.", statements.size() == 1);
		statements.get(0).setIsVariadic(false);

		rewrite();
		checkResult("<?php function test(&$tmp) {} \n ?>");
	}

	@Test
	public void useStatementFunctionSet() throws Exception {
		String str = "<?php use Foo\\Bar; \n ?>";
		initialize(str);

		List<UseStatement> statements = getAllOfType(program, UseStatement.class);
		assertTrue("Unexpected list size.", statements.size() == 1);
		statements.get(0).setStatementType(UseStatement.T_FUNCTION);

		rewrite();
		checkResult("<?php use function Foo\\Bar; \n ?>");
	}

	@Test
	public void useStatementConstSet() throws Exception {
		String str = "<?php use Foo\\Bar; \n ?>";
		initialize(str);

		List<UseStatement> statements = getAllOfType(program, UseStatement.class);
		assertTrue("Unexpected list size.", statements.size() == 1);
		statements.get(0).setStatementType(UseStatement.T_CONST);

		rewrite();
		checkResult("<?php use const Foo\\Bar; \n ?>");
	}

	@Test
	public void useStatementNoneSet() throws Exception {
		String str = "<?php use function Foo\\Bar; \n ?>";
		initialize(str);

		List<UseStatement> statements = getAllOfType(program, UseStatement.class);
		assertTrue("Unexpected list size.", statements.size() == 1);
		statements.get(0).setStatementType(UseStatement.T_NONE);

		rewrite();
		checkResult("<?php use Foo\\Bar; \n ?>");
	}

	@Test
	public void useStatementManyPartsConstSet() throws Exception {
		String str = "<?php use function Foo\\Bar, Foo\\Bar2; \n ?>";
		initialize(str);

		List<UseStatement> statements = getAllOfType(program, UseStatement.class);
		assertTrue("Unexpected list size.", statements.size() == 1);
		statements.get(0).setStatementType(UseStatement.T_CONST);

		rewrite();
		checkResult("<?php use const Foo\\Bar, Foo\\Bar2; \n ?>");
	}

	@Test
	public void useStatementManyPartsNoneSet() throws Exception {
		String str = "<?php use function Foo\\Bar, Foo\\Bar2; \n ?>";
		initialize(str);

		List<UseStatement> statements = getAllOfType(program, UseStatement.class);
		assertTrue("Unexpected list size.", statements.size() == 1);
		statements.get(0).setStatementType(UseStatement.T_NONE);

		rewrite();
		checkResult("<?php use Foo\\Bar, Foo\\Bar2; \n ?>");
	}

}
