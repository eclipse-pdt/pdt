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
import org.eclipse.php.core.ast.nodes.ArrayAccess;
import org.eclipse.php.core.ast.nodes.FunctionDeclaration;
import org.eclipse.php.core.ast.nodes.Identifier;
import org.eclipse.php.core.ast.nodes.StaticFieldAccess;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

/**
 * AST rewrite test which tests the ASTRewriteAnalyzer implementation.
 * 
 */
public class ASTRewriteTestsPHP7 extends ASTRewriteTestsPHP56 {

	@RunWith(org.junit.runners.Suite.class)
	@SuiteClasses({ ASTRewriteTestsPHP7.class, NodeDeletionTestsPHP7.class })
	public static class Suite {
	}

	@Override
	protected PHPVersion getPHPVersion() {
		return PHPVersion.PHP7_0;
	}

	@Test
	public void staticMemberWithArray() throws Exception {
		String str = "<?php MyClass::$$a[5];?>";
		initialize(str);

		List<ArrayAccess> arrayAccess = getAllOfType(program, ArrayAccess.class);
		assertTrue("Unexpected list size.", arrayAccess.size() == 1);
		((StaticFieldAccess) arrayAccess.get(0).getName())
				.setField(ast.newReflectionVariable(ast.newArrayAccess(ast.newVariable("bar"), ast.newScalar("333"))));
		rewrite();
		checkResult("<?php MyClass::$$bar[333][5];?>");
	}

	@Test
	public void functionReturnTypeSet() throws Exception {
		String str = "<?php function test($tmp) {} \n ?>";
		initialize(str);

		List<FunctionDeclaration> statements = getAllOfType(program, FunctionDeclaration.class);
		assertTrue("Unexpected list size.", statements.size() == 1);
		Identifier identifier = ast.newIdentifier("MyClass");
		statements.get(0).setReturnType(identifier);

		rewrite();
		checkResult("<?php function test($tmp): MyClass {} \n ?>");
	}

	@Test
	public void functionReturnTypeUnset() throws Exception {
		String str = "<?php function test($tmp): MyClass {} \n ?>";
		initialize(str);

		List<FunctionDeclaration> statements = getAllOfType(program, FunctionDeclaration.class);
		assertTrue("Unexpected list size.", statements.size() == 1);
		statements.get(0).setReturnType(null);

		rewrite();
		checkResult("<?php function test($tmp) {} \n ?>");
	}

	@Test
	public void methodReturnTypeSet() throws Exception {
		String str = "<?php class Test { public function test($tmp) {} }\n ?>";
		initialize(str);

		List<FunctionDeclaration> statements = getAllOfType(program, FunctionDeclaration.class);
		assertTrue("Unexpected list size.", statements.size() == 1);
		Identifier identifier = ast.newIdentifier("MyClass");
		statements.get(0).setReturnType(identifier);

		rewrite();
		checkResult("<?php class Test { public function test($tmp): MyClass {} }\n ?>");
	}

	@Test
	public void methodReturnTypeUnset() throws Exception {
		String str = "<?php class Test { public function test($tmp): MyClass {} }\n ?>";
		initialize(str);

		List<FunctionDeclaration> statements = getAllOfType(program, FunctionDeclaration.class);
		assertTrue("Unexpected list size.", statements.size() == 1);
		statements.get(0).setReturnType(null);

		rewrite();
		checkResult("<?php class Test { public function test($tmp) {} }\n ?>");
	}

	@Ignore
	public void refernceInstanciation() throws Exception {
		// no longer valid for PHP7+
	}

}
