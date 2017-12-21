/*******************************************************************************
 * Copyright (c) 2017 IBM Corporation and others.
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
	public void useStatementFunctionSet3() throws Exception {
		String str = "<?php use function Foo\\{Bar, Bar2}; \n ?>";
		initialize(str);

		List<UseStatement> statements = getAllOfType(program, UseStatement.class);
		assertTrue("Unexpected list size.", statements.size() == 1);
		statements.get(0).setStatementType(UseStatement.T_NONE);
		assertTrue("Unexpected parts list size.", statements.get(0).parts().size() == 2);
		statements.get(0).parts().get(1).setStatementType(UseStatement.T_FUNCTION);

		rewrite();
		checkResult("<?php use Foo\\{Bar, function Bar2};  \n ?>");
	}

	@Test
	public void useStatementConstSet3() throws Exception {
		String str = "<?php use function Foo\\{Bar, Bar2}; \n ?>";
		initialize(str);

		List<UseStatement> statements = getAllOfType(program, UseStatement.class);
		assertTrue("Unexpected list size.", statements.size() == 1);
		statements.get(0).setStatementType(UseStatement.T_CONST);

		rewrite();
		checkResult("<?php use const Foo\\{Bar, Bar2};  \n ?>");
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
		checkResult("<?php use const Foo\\{Bar, Bar2};  \n ?>");
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
		checkResult("<?php use Foo\\{Bar, Bar2};  \n ?>");
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
		checkResult("<?php use Foo\\{Bar, Bar2};  \n ?>");
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
		checkResult("<?php use Foo\\{Bar, Bar, Bar};  \n ?>");
	}

}
