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
package org.eclipse.php.core.tests.compiler_ast.phpdoc;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.Reader;
import java.io.StringReader;

import org.eclipse.core.resources.IProject;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.declarations.Declaration;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.TypeDeclaration;
import org.eclipse.dltk.ast.parser.IModuleDeclaration;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.php.core.compiler.ast.nodes.IPHPDocAwareDeclaration;
import org.eclipse.php.core.compiler.ast.nodes.PHPDocBlock;
import org.eclipse.php.core.compiler.ast.nodes.PHPModuleDeclaration;
import org.eclipse.php.core.tests.TestSuiteWatcher;
import org.eclipse.php.internal.core.compiler.ast.parser.php5.PhpSourceParser;
import org.eclipse.php.core.project.ProjectOptions;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

/**
 * These tests used for checking association between declaration AST node and
 * PHPDoc block assigned to it.
 */
public class PHPDocAwareDeclarationTests {

	@ClassRule
	public static TestWatcher watcher = new TestSuiteWatcher();

	@Test
	public void testVar() throws Exception {
		String str = "<?php  class A { /** My class variable */ var $testVar; } ?>";
		parseAndTest(str);
	}

	@Test
	public void testVarWithModifiers1() throws Exception {
		String str = "<?php  class A { /** My class variable */ public static $testVarWithModifiers1; } ?>";
		parseAndTest(str);
	}

	@Test
	public void testVarWithModifiers2() throws Exception {
		String str = "<?php  class A { /** My class variable */ static public $testVarWithModifiers2; } ?>";
		parseAndTest(str);
	}

	@Test
	public void testVarWithModifiers3() throws Exception {
		String str = "<?php  class A { /** My class variable */ public $testVarWithModifiers3; } ?>";
		parseAndTest(str);
	}

	@Test
	public void testVarWithModifiers4() throws Exception {
		String str = "<?php  class A { /** My class variable */ private static $testVarWithModifiers4; } ?>";
		parseAndTest(str);
	}

	@Test
	public void testVarWithModifiers5() throws Exception {
		String str = "<?php  class A { /** My class variable */ static private $testVarWithModifiers5; } ?>";
		parseAndTest(str);
	}

	@Test
	public void testVarWithModifiers6() throws Exception {
		String str = "<?php  class A { /** My class variable */ private $testVarWithModifiers6; } ?>";
		parseAndTest(str);
	}

	@Test
	public void testVarWithModifiers7() throws Exception {
		String str = "<?php  class A { /** My class variable */ protected static $testVarWithModifiers7; } ?>";
		parseAndTest(str);
	}

	@Test
	public void testVarWithModifiers8() throws Exception {
		String str = "<?php  class A { /** My class variable */ static protected $testVarWithModifiers8; } ?>";
		parseAndTest(str);
	}

	@Test
	public void testVarWithModifiers9() throws Exception {
		String str = "<?php  class A { /** My class variable */ protected $testVarWithModifiers9; } ?>";
		parseAndTest(str);
	}

	@Test
	public void testVarWithModifiers10() throws Exception {
		String str = "<?php  class A { /** My class variable */ static $testVarWithModifiers10; } ?>";
		parseAndTest(str);
	}

	@Test
	public void testClass() throws Exception {
		String str = "<?php  /** Very useful class */ class testClass { } ?>";
		parseAndTest(str);
	}

	@Test
	public void testClassNegative() throws Exception {
		String str = "<?php  /** Very useful class */ ; class testClassNegative { } ?>";
		parseAndTest(str, false);
	}

	@Test
	public void testInterface() throws Exception {
		String str = "<?php  /** Very useful interface */ interface testInterface { } ?>";
		parseAndTest(str);
	}

	@Test
	public void testClassAbstract() throws Exception {
		String str = "<?php  /** Very useful class */ abstract class testClassAbstract { } ?>";
		parseAndTest(str);
	}

	@Test
	public void testClassFinal() throws Exception {
		String str = "<?php  /** Very useful class */ final class testClassFinal { } ?>";
		parseAndTest(str);
	}

	@Test
	public void testMethod() throws Exception {
		String str = "<?php  class A { /** Very useful method */ function testMethod() {} } ?>";
		parseAndTest(str);
	}

	@Test
	public void testMethodWithModifiers1() throws Exception {
		String str = "<?php  class A { /** Very useful method */ public abstract function testMethodWithModifiers1() {} } ?>";
		parseAndTest(str);
	}

	@Test
	public void testMethodWithModifiers2() throws Exception {
		String str = "<?php  class A { /** Very useful method */ abstract public function testMethodWithModifiers2() {} } ?>";
		parseAndTest(str);
	}

	@Test
	public void testMethodWithModifiers3() throws Exception {
		String str = "<?php  class A { /** Very useful method */ public static function testMethodWithModifiers3() {} } ?>";
		parseAndTest(str);
	}

	@Test
	public void testMethodWithModifiers4() throws Exception {
		String str = "<?php  class A { /** Very useful method */ static public function testMethodWithModifiers4() {} } ?>";
		parseAndTest(str);
	}

	@Test
	public void testMethodWithModifiers5() throws Exception {
		String str = "<?php  class A { /** Very useful method */ public function testMethodWithModifiers5() {} } ?>";
		parseAndTest(str);
	}

	@Test
	public void testMethodWithModifiers6() throws Exception {
		String str = "<?php  class A { /** Very useful method */ private abstract function testMethodWithModifiers6() {} } ?>";
		parseAndTest(str);
	}

	@Test
	public void testMethodWithModifiers7() throws Exception {
		String str = "<?php  class A { /** Very useful method */ abstract private function testMethodWithModifiers7() {} } ?>";
		parseAndTest(str);
	}

	@Test
	public void testMethodWithModifiers8() throws Exception {
		String str = "<?php  class A { /** Very useful method */ private static function testMethodWithModifiers8() {} } ?>";
		parseAndTest(str);
	}

	@Test
	public void testMethodWithModifiers9() throws Exception {
		String str = "<?php  class A { /** Very useful method */ static private function testMethodWithModifiers9() {} } ?>";
		parseAndTest(str);
	}

	@Test
	public void testMethodWithModifiers10() throws Exception {
		String str = "<?php  class A { /** Very useful method */ private function testMethodWithModifiers10() {} } ?>";
		parseAndTest(str);
	}

	@Test
	public void testMethodWithModifiers11() throws Exception {
		String str = "<?php  class A { /** Very useful method */ protected abstract function testMethodWithModifiers11() {} } ?>";
		parseAndTest(str);
	}

	@Test
	public void testMethodWithModifiers12() throws Exception {
		String str = "<?php  class A { /** Very useful method */ abstract protected function testMethodWithModifiers12() {} } ?>";
		parseAndTest(str);
	}

	@Test
	public void testMethodWithModifiers13() throws Exception {
		String str = "<?php  class A { /** Very useful method */ protected static function testMethodWithModifiers13() {} } ?>";
		parseAndTest(str);
	}

	@Test
	public void testMethodWithModifiers14() throws Exception {
		String str = "<?php  class A { /** Very useful method */ static protected function testMethodWithModifiers14() {} } ?>";
		parseAndTest(str);
	}

	@Test
	public void testMethodWithModifiers15() throws Exception {
		String str = "<?php  class A { /** Very useful method */ protected function testMethodWithModifiers15() {} } ?>";
		parseAndTest(str);
	}

	@Test
	public void testMethodWithModifiers16() throws Exception {
		String str = "<?php  class A { /** Very useful method */ abstract function testMethodWithModifiers16() {} } ?>";
		parseAndTest(str);
	}

	@Test
	public void testMethodWithModifiers17() throws Exception {
		String str = "<?php  class A { /** Very useful method */ static function testMethodWithModifiers17() {} } ?>";
		parseAndTest(str);
	}

	private void parseAndTest(String str) throws Exception {
		StringReader reader = new StringReader(str);
		String declarationName = new Throwable().fillInStackTrace().getStackTrace()[1].getMethodName();
		parseAndTest(reader, declarationName, str, true);
	}

	private void parseAndTest(String str, boolean positiveTest) throws Exception {
		StringReader reader = new StringReader(str);
		String declarationName = new Throwable().fillInStackTrace().getStackTrace()[1].getMethodName();
		parseAndTest(reader, declarationName, str, positiveTest);
	}

	/**
	 * 
	 * @param reader
	 *            stringReader of inputstream
	 * @param declarationName
	 * @param str
	 * @throws Exception
	 */
	public void parseAndTest(Reader reader, String declarationName, String str, boolean positiveTest) throws Exception {
		IModuleDeclaration program = new PhpSourceParser().parse(reader, null,
				ProjectOptions.useShortTags((IProject) null));
		DeclarationSearcher searcher = new DeclarationSearcher(declarationName);
		((PHPModuleDeclaration) program).traverse(searcher);
		Declaration declaration = searcher.getResult();

		assertNotNull("Can't find declaration AST node for: " + declarationName, declaration);
		assertTrue("Declaration is not PHPDoc aware: " + declarationName,
				declaration instanceof IPHPDocAwareDeclaration);
		PHPDocBlock phpDoc = ((IPHPDocAwareDeclaration) declaration).getPHPDoc();

		if (positiveTest) {
			assertNotNull("No PHPDoc section found for:" + declarationName, phpDoc);
			assertNotNull("PHPDoc doesn't contain short description: " + declarationName, phpDoc.getShortDescription());
		} else {
			assertNull("Declaration node: " + declarationName + " must not contain PHPDoc section", phpDoc);
		}
	}

	class DeclarationSearcher extends ASTVisitor {

		private Declaration declaration;
		private String declarationName;

		public DeclarationSearcher(String declarationName) {
			this.declarationName = declarationName;
		}

		public Declaration getResult() {
			return declaration;
		}

		public boolean visit(Declaration d) throws Exception {
			String name = d.getName();
			if (name.startsWith("$")) {
				name = name.substring(1);
			}
			if (name.equals(declarationName)) {
				declaration = d;
				return false;
			}
			return visitGeneral(d);
		}

		public boolean visit(TypeDeclaration s) throws Exception {
			return visit((Declaration) s);
		}

		public boolean visit(MethodDeclaration s) throws Exception {
			return visit((Declaration) s);
		}

		public boolean visit(Statement s) throws Exception {
			if (s instanceof Declaration) {
				return visit((Declaration) s);
			}
			return visitGeneral(s);
		}
	}
}
