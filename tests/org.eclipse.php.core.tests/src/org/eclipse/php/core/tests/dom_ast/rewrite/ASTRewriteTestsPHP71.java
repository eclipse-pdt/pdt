/*******************************************************************************
 * Copyright (c) 2017 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
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
import org.eclipse.php.core.ast.nodes.CatchClause;
import org.eclipse.php.core.ast.nodes.ConditionalExpression;
import org.eclipse.php.core.ast.nodes.NamespaceDeclaration;
import org.eclipse.php.core.ast.nodes.NamespaceName;
import org.eclipse.php.core.ast.nodes.Scalar;
import org.eclipse.php.core.ast.nodes.UseStatement;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

/**
 * AST rewrite test which tests the ASTRewriteAnalyzer implementation.
 * 
 */
public class ASTRewriteTestsPHP71 extends ASTRewriteTestsPHP7 {

	@RunWith(org.junit.runners.Suite.class)
	@SuiteClasses({ ASTRewriteTestsPHP71.class, NodeDeletionTestsPHP71.class })
	public static class Suite {
	}

	@Override
	protected PHPVersion getPHPVersion() {
		return PHPVersion.PHP7_1;
	}

	@Test
	public void useStatementFunctionSet2() throws Exception {
		String str = "<?php use Foo\\{Bar}; \n ?>";
		initialize(str);

		List<UseStatement> statements = getAllOfType(program, UseStatement.class);
		assertTrue("Unexpected list size.", statements.size() == 1);
		assertTrue("Unexpected parts list size.", statements.get(0).parts().size() == 1);
		statements.get(0).parts().get(0).setStatementType(UseStatement.T_FUNCTION);

		rewrite();
		checkResult("<?php use Foo\\{function Bar}; \n ?>");
	}

	@Test
	public void useStatementFunctionSet2b() throws Exception {
		String str = "<?php use \\Foo\\{Bar}; \n ?>";
		initialize(str);

		List<UseStatement> statements = getAllOfType(program, UseStatement.class);
		assertTrue("Unexpected list size.", statements.size() == 1);
		assertTrue("Unexpected parts list size.", statements.get(0).parts().size() == 1);
		statements.get(0).parts().get(0).setStatementType(UseStatement.T_FUNCTION);

		rewrite();
		checkResult("<?php use \\Foo\\{function Bar}; \n ?>");
	}

	@Test
	public void useStatementConstSet2() throws Exception {
		String str = "<?php use Foo\\{Bar}; \n ?>";
		initialize(str);

		List<UseStatement> statements = getAllOfType(program, UseStatement.class);
		assertTrue("Unexpected list size.", statements.size() == 1);
		assertTrue("Unexpected parts list size.", statements.get(0).parts().size() == 1);
		statements.get(0).parts().get(0).setStatementType(UseStatement.T_CONST);

		rewrite();
		checkResult("<?php use Foo\\{const Bar}; \n ?>");
	}

	@Test
	public void useStatementConstSet2b() throws Exception {
		String str = "<?php use \\Foo\\{Bar}; \n ?>";
		initialize(str);

		List<UseStatement> statements = getAllOfType(program, UseStatement.class);
		assertTrue("Unexpected list size.", statements.size() == 1);
		assertTrue("Unexpected parts list size.", statements.get(0).parts().size() == 1);
		statements.get(0).parts().get(0).setStatementType(UseStatement.T_CONST);

		rewrite();
		checkResult("<?php use \\Foo\\{const Bar}; \n ?>");
	}

	@Test
	public void useStatementFunctionSet3() throws Exception {
		String str = "<?php use function Foo\\{Bar, Bar2}; \n ?>";
		initialize(str);

		List<UseStatement> statements = getAllOfType(program, UseStatement.class);
		assertTrue("Unexpected list size.", statements.size() == 1);
		statements.get(0).setStatementType(UseStatement.T_NONE);
		assertTrue("Unexpected parts list size.", statements.get(0).parts().size() == 2);
		statements.get(0).parts().get(1).setStatementType(UseStatement.T_FUNCTION);

		rewrite();
		checkResult("<?php use Foo\\{Bar, function Bar2}; \n ?>");
	}

	@Test
	public void useStatementFunctionSet3b() throws Exception {
		String str = "<?php use function \\Foo\\{Bar, Bar2}; \n ?>";
		initialize(str);

		List<UseStatement> statements = getAllOfType(program, UseStatement.class);
		assertTrue("Unexpected list size.", statements.size() == 1);
		statements.get(0).setStatementType(UseStatement.T_NONE);
		assertTrue("Unexpected parts list size.", statements.get(0).parts().size() == 2);
		statements.get(0).parts().get(1).setStatementType(UseStatement.T_FUNCTION);

		rewrite();
		checkResult("<?php use Foo\\{Bar, function Bar2}; \n ?>");
	}

	@Test
	public void useStatementConstSet3() throws Exception {
		String str = "<?php use function Foo\\{Bar, Bar2}; \n ?>";
		initialize(str);

		List<UseStatement> statements = getAllOfType(program, UseStatement.class);
		assertTrue("Unexpected list size.", statements.size() == 1);
		statements.get(0).setStatementType(UseStatement.T_CONST);

		rewrite();
		checkResult("<?php use const Foo\\{Bar, Bar2}; \n ?>");
	}

	@Test
	public void useStatementConstSet3b() throws Exception {
		String str = "<?php use function \\Foo\\{Bar, Bar2}; \n ?>";
		initialize(str);

		List<UseStatement> statements = getAllOfType(program, UseStatement.class);
		assertTrue("Unexpected list size.", statements.size() == 1);
		statements.get(0).setStatementType(UseStatement.T_CONST);

		rewrite();
		checkResult("<?php use const Foo\\{Bar, Bar2}; \n ?>");
	}

	@Test
	public void useStatementConstSet4() throws Exception {
		String str = "<?php use Foo\\{const Bar, function Bar2}; \n ?>";
		initialize(str);

		List<UseStatement> statements = getAllOfType(program, UseStatement.class);
		assertTrue("Unexpected list size.", statements.size() == 1);
		statements.get(0).setStatementType(UseStatement.T_CONST);
		assertTrue("Unexpected parts list size.", statements.get(0).parts().size() == 2);
		statements.get(0).parts().get(0).setStatementType(UseStatement.T_NONE);
		statements.get(0).parts().get(1).setStatementType(UseStatement.T_NONE);

		rewrite();
		checkResult("<?php use const Foo\\{Bar, Bar2}; \n ?>");
	}

	@Test
	public void useStatementConstSet4b() throws Exception {
		String str = "<?php use \\Foo\\{const Bar, function Bar2}; \n ?>";
		initialize(str);

		List<UseStatement> statements = getAllOfType(program, UseStatement.class);
		assertTrue("Unexpected list size.", statements.size() == 1);
		statements.get(0).setStatementType(UseStatement.T_CONST);
		assertTrue("Unexpected parts list size.", statements.get(0).parts().size() == 2);
		statements.get(0).parts().get(0).setStatementType(UseStatement.T_NONE);
		statements.get(0).parts().get(1).setStatementType(UseStatement.T_NONE);

		rewrite();
		checkResult("<?php use const Foo\\{Bar, Bar2}; \n ?>");
	}

	@Test
	public void useStatementNoneSet2() throws Exception {
		String str = "<?php use Foo\\{const Bar, function Bar2}; \n ?>";
		initialize(str);

		List<UseStatement> statements = getAllOfType(program, UseStatement.class);
		assertTrue("Unexpected list size.", statements.size() == 1);
		statements.get(0).setStatementType(UseStatement.T_NONE);
		assertTrue("Unexpected parts list size.", statements.get(0).parts().size() == 2);
		statements.get(0).parts().get(0).setStatementType(UseStatement.T_NONE);
		statements.get(0).parts().get(1).setStatementType(UseStatement.T_NONE);

		rewrite();
		checkResult("<?php use Foo\\{Bar, Bar2}; \n ?>");
	}

	@Test
	public void useStatementNoneSet2b() throws Exception {
		String str = "<?php use \\Foo\\{const Bar, function Bar2}; \n ?>";
		initialize(str);

		List<UseStatement> statements = getAllOfType(program, UseStatement.class);
		assertTrue("Unexpected list size.", statements.size() == 1);
		statements.get(0).setStatementType(UseStatement.T_NONE);
		assertTrue("Unexpected parts list size.", statements.get(0).parts().size() == 2);
		statements.get(0).parts().get(0).setStatementType(UseStatement.T_NONE);
		statements.get(0).parts().get(1).setStatementType(UseStatement.T_NONE);

		rewrite();
		checkResult("<?php use \\Foo\\{Bar, Bar2}; \n ?>");
	}

	@Test
	public void useStatementNoneSet3() throws Exception {
		String str = "<?php use function Foo\\{Bar, Bar2}; \n ?>";
		initialize(str);

		List<UseStatement> statements = getAllOfType(program, UseStatement.class);
		assertTrue("Unexpected list size.", statements.size() == 1);
		statements.get(0).setStatementType(UseStatement.T_NONE);
		assertTrue("Unexpected parts list size.", statements.get(0).parts().size() == 2);
		statements.get(0).parts().get(0).setStatementType(UseStatement.T_NONE);
		statements.get(0).parts().get(1).setStatementType(UseStatement.T_NONE);

		rewrite();
		checkResult("<?php use Foo\\{Bar, Bar2}; \n ?>");
	}

	@Test
	public void useStatementNoneSet3b() throws Exception {
		String str = "<?php use function \\Foo\\{Bar, Bar2}; \n ?>";
		initialize(str);

		List<UseStatement> statements = getAllOfType(program, UseStatement.class);
		assertTrue("Unexpected list size.", statements.size() == 1);
		statements.get(0).setStatementType(UseStatement.T_NONE);
		assertTrue("Unexpected parts list size.", statements.get(0).parts().size() == 2);
		statements.get(0).parts().get(0).setStatementType(UseStatement.T_NONE);
		statements.get(0).parts().get(1).setStatementType(UseStatement.T_NONE);

		rewrite();
		checkResult("<?php use Foo\\{Bar, Bar2}; \n ?>");
	}

	@Test
	public void useStatementNoneSet4() throws Exception {
		String str = "<?php use Foo\\{function Bar, Bar, const Bar}; \n ?>";
		initialize(str);

		List<UseStatement> statements = getAllOfType(program, UseStatement.class);
		assertTrue("Unexpected list size.", statements.size() == 1);
		statements.get(0).setStatementType(UseStatement.T_NONE);
		assertTrue("Unexpected parts list size.", statements.get(0).parts().size() == 3);
		statements.get(0).parts().get(0).setStatementType(UseStatement.T_NONE);
		statements.get(0).parts().get(1).setStatementType(UseStatement.T_NONE);
		statements.get(0).parts().get(2).setStatementType(UseStatement.T_NONE);

		rewrite();
		checkResult("<?php use Foo\\{Bar, Bar, Bar}; \n ?>");
	}

	@Test
	public void useStatementNoneSet4b() throws Exception {
		String str = "<?php use \\Foo\\{function Bar, Bar, const Bar}; \n ?>";
		initialize(str);

		List<UseStatement> statements = getAllOfType(program, UseStatement.class);
		assertTrue("Unexpected list size.", statements.size() == 1);
		statements.get(0).setStatementType(UseStatement.T_NONE);
		assertTrue("Unexpected parts list size.", statements.get(0).parts().size() == 3);
		statements.get(0).parts().get(0).setStatementType(UseStatement.T_NONE);
		statements.get(0).parts().get(1).setStatementType(UseStatement.T_NONE);
		statements.get(0).parts().get(2).setStatementType(UseStatement.T_NONE);

		rewrite();
		checkResult("<?php use \\Foo\\{Bar, Bar, Bar}; \n ?>");
	}

	@Test
	public void useMultipleExceptions() throws Exception {
		String str = "<?php try { } catch (Exception1 | Exception2 | Exception3 $e) { } \n ?>";
		initialize(str);

		List<CatchClause> catchClauses = getAllOfType(program, CatchClause.class);
		assertTrue("Unexpected list size.", catchClauses.size() == 1);
		assertTrue("Unexpected exceptions list size.", catchClauses.get(0).getClassNames().size() == 3);
		assertTrue("Unexpected content.", catchClauses.get(0).getClassNames().get(2) instanceof NamespaceName);
		((NamespaceName) catchClauses.get(0).getClassNames().get(2)).segments().get(0).setName("Exception4");

		rewrite();
		checkResult("<?php try {} catch (Exception1 | Exception2 | Exception4 $e) {} \n ?>");
	}

	@Test
	public void useCoalesceOperator() throws Exception {
		String str = "<?php $a = $test ?? 0; \n ?>";
		initialize(str);

		List<ConditionalExpression> conditionalExpressions = getAllOfType(program, ConditionalExpression.class);
		assertTrue("Unexpected list size.", conditionalExpressions.size() == 1);
		assertTrue("Unexpected content 1.", conditionalExpressions.get(0).getIfTrue() instanceof Scalar);
		assertTrue("Unexpected content 2.",
				conditionalExpressions.get(0).getIfFalse() == conditionalExpressions.get(0).getCondition());
		assertTrue("Unexpected content 3.",
				conditionalExpressions.get(0).getOperatorType() == ConditionalExpression.OP_COALESCE);
		((Scalar) conditionalExpressions.get(0).getIfTrue()).setStringValue("1");

		rewrite();
		checkResult("<?php $a = $test ?? 1; \n ?>");
	}

	@Test
	public void useBracketedNamespaceDeclaration() throws Exception {
		String str = "<?php namespace A {$a = 5;} \n ?>";
		initialize(str);

		List<NamespaceDeclaration> namespaceDeclarations = getAllOfType(program, NamespaceDeclaration.class);
		assertTrue("Unexpected list size.", namespaceDeclarations.size() == 1);
		assertTrue("Unexpected content 1.", namespaceDeclarations.get(0).isBracketed());
		assertTrue("Unexpected content 2.", namespaceDeclarations.get(0).getBody() != null);
		namespaceDeclarations.get(0).getBody().statements().clear();

		rewrite();
		checkResult("<?php namespace A {} \n ?>");
	}

}
