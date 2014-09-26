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
package org.eclipse.php.core.tests.dom_ast.rewrite;

import java.util.List;

import junit.framework.TestSuite;

import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.ast.nodes.FormalParameter;
import org.eclipse.php.internal.core.ast.nodes.UseStatement;
import org.junit.Assert;

/**
 * AST rewrite test which tests the ASTRewriteAnalyzer implementation.
 * 
 */
public class ASTRewriteTestsPHP56 extends ASTRewriteTestsPHP55 {

	public ASTRewriteTestsPHP56(String name) {
		super(name);
	}

	public static TestSuite suite() {
		return new TestSuite(new Class[] { ASTRewriteTestsPHP56.class,
				NodeDeletionTestsPHP56.class, },
				ASTRewriteTestsPHP56.class.getName());
	}

	@Override
	protected PHPVersion getPHPVersion() {
		return PHPVersion.PHP5_6;
	}

	public void testVariadicParameterSet() throws Exception {
		String str = "<?php function test($tmp) {} \n ?>";
		initialize(str);

		List<FormalParameter> statements = getAllOfType(program,
				FormalParameter.class);
		Assert.assertTrue("Unexpected list size.", statements.size() == 1);
		statements.get(0).setIsVariadic(true);

		rewrite();
		checkResult("<?php function test(...$tmp) {} \n ?>");
	}

	public void testVariadicParameterUnset() throws Exception {
		String str = "<?php function test(...$tmp) {} \n ?>";
		initialize(str);

		List<FormalParameter> statements = getAllOfType(program,
				FormalParameter.class);
		Assert.assertTrue("Unexpected list size.", statements.size() == 1);
		statements.get(0).setIsVariadic(false);

		rewrite();
		checkResult("<?php function test($tmp) {} \n ?>");
	}

	public void testReferenceVariadicParameterSet() throws Exception {
		String str = "<?php function test(&$tmp) {} \n ?>";
		initialize(str);

		List<FormalParameter> statements = getAllOfType(program,
				FormalParameter.class);
		Assert.assertTrue("Unexpected list size.", statements.size() == 1);
		statements.get(0).setIsVariadic(true);

		rewrite();
		checkResult("<?php function test(&...$tmp) {} \n ?>");
	}

	public void testReferenceVariadicParameterUnset() throws Exception {
		String str = "<?php function test(&...$tmp) {} \n ?>";
		initialize(str);

		List<FormalParameter> statements = getAllOfType(program,
				FormalParameter.class);
		Assert.assertTrue("Unexpected list size.", statements.size() == 1);
		statements.get(0).setIsVariadic(false);

		rewrite();
		checkResult("<?php function test(&$tmp) {} \n ?>");
	}

	public void testUseStatementFunctionSet() throws Exception {
		String str = "<?php use Foo\\Bar; \n ?>";
		initialize(str);

		List<UseStatement> statements = getAllOfType(program,
				UseStatement.class);
		Assert.assertTrue("Unexpected list size.", statements.size() == 1);
		statements.get(0).setStatementType(UseStatement.T_FUNCTION);

		rewrite();
		checkResult("<?php use function Foo\\Bar; \n ?>");
	}

	public void testUseStatementConstSet() throws Exception {
		String str = "<?php use Foo\\Bar; \n ?>";
		initialize(str);

		List<UseStatement> statements = getAllOfType(program,
				UseStatement.class);
		Assert.assertTrue("Unexpected list size.", statements.size() == 1);
		statements.get(0).setStatementType(UseStatement.T_CONST);

		rewrite();
		checkResult("<?php use const Foo\\Bar; \n ?>");
	}

	public void testUseStatementNoneSet() throws Exception {
		String str = "<?php use function Foo\\Bar; \n ?>";
		initialize(str);

		List<UseStatement> statements = getAllOfType(program,
				UseStatement.class);
		Assert.assertTrue("Unexpected list size.", statements.size() == 1);
		statements.get(0).setStatementType(UseStatement.T_NONE);

		rewrite();
		checkResult("<?php use Foo\\Bar; \n ?>");
	}

	public void testUseStatementManyPartsConstSet() throws Exception {
		String str = "<?php use function Foo\\Bar, Foo\\Bar2; \n ?>";
		initialize(str);

		List<UseStatement> statements = getAllOfType(program,
				UseStatement.class);
		Assert.assertTrue("Unexpected list size.", statements.size() == 1);
		statements.get(0).setStatementType(UseStatement.T_CONST);

		rewrite();
		checkResult("<?php use const Foo\\Bar, Foo\\Bar2; \n ?>");
	}

	public void testUseStatementManyPartsNoneSet() throws Exception {
		String str = "<?php use function Foo\\Bar, Foo\\Bar2; \n ?>";
		initialize(str);

		List<UseStatement> statements = getAllOfType(program,
				UseStatement.class);
		Assert.assertTrue("Unexpected list size.", statements.size() == 1);
		statements.get(0).setStatementType(UseStatement.T_NONE);

		rewrite();
		checkResult("<?php use Foo\\Bar, Foo\\Bar2; \n ?>");
	}

}
