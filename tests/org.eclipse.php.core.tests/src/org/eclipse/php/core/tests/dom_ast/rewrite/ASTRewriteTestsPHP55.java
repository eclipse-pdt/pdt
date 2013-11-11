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

import junit.framework.Assert;
import junit.framework.TestSuite;

import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.ast.nodes.Block;
import org.eclipse.php.internal.core.ast.nodes.TryStatement;
import org.eclipse.php.internal.core.ast.nodes.Variable;
import org.eclipse.php.internal.core.ast.nodes.YieldExpression;

/**
 * AST rewrite test which tests the ASTRewriteAnalyzer implementation.
 * 
 * @author shalom
 */
public class ASTRewriteTestsPHP55 extends ASTRewriteTestsPHP54 {

	public ASTRewriteTestsPHP55(String name) {
		super(name);
	}

	public static TestSuite suite() {
		return new TestSuite(new Class[] { ASTRewriteTestsPHP55.class,
				NodeDeletionTestsPHP55.class, },
				ASTRewriteTestsPHP55.class.getName());
	}

	@Override
	protected PHPVersion getPHPVersion() {
		return PHPVersion.PHP5_5;
	}

	public void testTryCatchFinallyStatement() throws Exception {
		String str = "<?php try { $error = 'Always throw this error'; } finally { echo '' }\n ?>";
		initialize(str);

		List<TryStatement> statements = getAllOfType(program,
				TryStatement.class);
		Assert.assertTrue("Unexpected list size.", statements.size() == 1);
		Block newBlock = ast.newBlock();
		newBlock.statements().add(
				ast.newEchoStatement(ast.newScalar("'Hello'")));
		statements
				.get(0)
				.catchClauses()
				.add(ast.newCatchClause(ast.newIdentifier("Boobo"),
						ast.newVariable("b"), newBlock));
		rewrite();
		checkResult("<?php try { $error = 'Always throw this error'; } catch (Boobo $b) {\necho 'Hello';\n} finally { echo '' }\n  ?>");
	}

	public void testYieldExpressionAdd() throws Exception {
		String str = "<?php yield; ?>";
		initialize(str);

		List<YieldExpression> yieldStatements = getAllOfType(program,
				YieldExpression.class);
		Assert.assertTrue("Unexpected list size.", yieldStatements.size() == 1);
		yieldStatements.get(0).setExpression(ast.newVariable("a"));
		rewrite();
		String string = document.get();
		checkResult("<?php yield $a; ?>");
	}

	public void testYieldKeyAdd() throws Exception {
		String str = "<?php yield $a; ?>";
		initialize(str);

		List<YieldExpression> yieldStatements = getAllOfType(program,
				YieldExpression.class);
		Assert.assertTrue("Unexpected list size.", yieldStatements.size() == 1);
		yieldStatements.get(0).setKey(ast.newVariable("b"));
		rewrite();
		checkResult("<?php yield $b => $a; ?>");
	}

	public void testYieldExprChange() throws Exception {
		String str = "<?php yield $a; ?>";
		initialize(str);

		List<YieldExpression> yieldStatements = getAllOfType(program,
				YieldExpression.class);
		Assert.assertTrue("Unexpected list size.", yieldStatements.size() == 1);
		((Variable) yieldStatements.get(0).getExpression()).setName(ast
				.newScalar("b"));
		rewrite();
		checkResult("<?php yield $b; ?>");
	}

	public void testYieldKeyChange() throws Exception {
		String str = "<?php yield $a => $b; ?>";
		initialize(str);

		List<YieldExpression> yieldStatements = getAllOfType(program,
				YieldExpression.class);
		Assert.assertTrue("Unexpected list size.", yieldStatements.size() == 1);
		((Variable) yieldStatements.get(0).getKey())
				.setName(ast.newScalar("c"));
		rewrite();
		checkResult("<?php yield $c => $b; ?>");
	}
}
