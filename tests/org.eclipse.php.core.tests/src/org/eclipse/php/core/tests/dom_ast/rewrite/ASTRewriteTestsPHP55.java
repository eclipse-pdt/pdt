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
import org.eclipse.php.core.ast.nodes.Block;
import org.eclipse.php.core.ast.nodes.TryStatement;
import org.eclipse.php.core.ast.nodes.Variable;
import org.eclipse.php.core.ast.nodes.YieldExpression;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

/**
 * AST rewrite test which tests the ASTRewriteAnalyzer implementation.
 * 
 * @author shalom
 */
public class ASTRewriteTestsPHP55 extends ASTRewriteTestsPHP54 {

	@RunWith(org.junit.runners.Suite.class)
	@SuiteClasses({ ASTRewriteTestsPHP55.class, NodeDeletionTestsPHP55.class })
	public static class Suite {
	}

	@Override
	protected PHPVersion getPHPVersion() {
		return PHPVersion.PHP5_5;
	}

	@Test
	public void tryCatchFinallyStatement() throws Exception {
		String str = "<?php try { $error = 'Always throw this error'; } finally { echo '' }\n ?>";
		initialize(str);

		List<TryStatement> statements = getAllOfType(program, TryStatement.class);
		assertTrue("Unexpected list size.", statements.size() == 1);
		Block newBlock = ast.newBlock();
		newBlock.statements().add(ast.newEchoStatement(ast.newScalar("'Hello'")));
		statements.get(0).catchClauses()
				.add(ast.newCatchClause(ast.newIdentifier("Boobo"), ast.newVariable("b"), newBlock));
		rewrite();
		checkResult(
				"<?php try { $error = 'Always throw this error'; } catch (Boobo $b) {\necho 'Hello';\n} finally { echo '' }\n  ?>");
	}

	@Test
	public void yieldExpressionAdd() throws Exception {
		String str = "<?php yield; ?>";
		initialize(str);

		List<YieldExpression> yieldStatements = getAllOfType(program, YieldExpression.class);
		assertTrue("Unexpected list size.", yieldStatements.size() == 1);
		yieldStatements.get(0).setExpression(ast.newVariable("a"));
		rewrite();
		String string = document.get();
		checkResult("<?php yield $a; ?>");
	}

	@Test
	public void yieldKeyAdd() throws Exception {
		String str = "<?php yield $a; ?>";
		initialize(str);

		List<YieldExpression> yieldStatements = getAllOfType(program, YieldExpression.class);
		assertTrue("Unexpected list size.", yieldStatements.size() == 1);
		yieldStatements.get(0).setKey(ast.newVariable("b"));
		rewrite();
		checkResult("<?php yield $b => $a; ?>");
	}

	@Test
	public void yieldExprChange() throws Exception {
		String str = "<?php yield $a; ?>";
		initialize(str);

		List<YieldExpression> yieldStatements = getAllOfType(program, YieldExpression.class);
		assertTrue("Unexpected list size.", yieldStatements.size() == 1);
		((Variable) yieldStatements.get(0).getExpression()).setName(ast.newScalar("b"));
		rewrite();
		checkResult("<?php yield $b; ?>");
	}

	@Test
	public void yieldKeyChange() throws Exception {
		String str = "<?php yield $a => $b; ?>";
		initialize(str);

		List<YieldExpression> yieldStatements = getAllOfType(program, YieldExpression.class);
		assertTrue("Unexpected list size.", yieldStatements.size() == 1);
		((Variable) yieldStatements.get(0).getKey()).setName(ast.newScalar("c"));
		rewrite();
		checkResult("<?php yield $c => $b; ?>");
	}
}
