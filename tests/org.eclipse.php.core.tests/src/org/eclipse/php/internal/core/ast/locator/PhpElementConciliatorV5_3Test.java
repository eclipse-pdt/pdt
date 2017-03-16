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
package org.eclipse.php.internal.core.ast.locator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.ast.nodes.ASTNode;
import org.eclipse.php.core.ast.nodes.Program;
import org.junit.Test;

public class PhpElementConciliatorV5_3Test extends AbstractConciliatorTest {

	static {
		phpVersion = PHPVersion.PHP5_3;
	}

	@Test
	public void concileClassName() {
		setFileContent("<?php namespace my\name; class TestRenameClass{} new TestRenameClass(); ?>");

		Program program = createProgram(file);

		assertNotNull(program);

		// select the class name.
		int start = 54;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CLASSNAME, PhpElementConciliator.concile(selectedNode));

		// select the class declaration
		start = 26;
		selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CLASSNAME, PhpElementConciliator.concile(selectedNode));
	}

	@Test
	public void concileClassName1() {
		setFileContent(
				"<?php namespace my\name; class TestRenameClass{} class TestExtendedClass extends TestRenameClass{}?>");

		Program program = createProgram(file);

		assertNotNull(program);

		// select the class name.
		int start = 82;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CLASSNAME, PhpElementConciliator.concile(selectedNode));
	}

	@Test
	public void concileClassName2() {
		setFileContent(
				"<?php namespace my\name; class A{function foo(){}} $a = new A();$a->foo(); my\\name\\A::foo();?>");

		Program program = createProgram(file);

		assertNotNull(program);

		// select the class name.
		int start = 59;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CLASSNAME, PhpElementConciliator.concile(selectedNode));

		start = 82;
		selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CLASSNAME, PhpElementConciliator.concile(selectedNode));
	}

	@Test
	public void concileInterface() {
		setFileContent("<?php namespace my\name; interface iTemplate{public function setVariable($name, $var);}?>");

		Program program = createProgram(file);

		assertNotNull(program);

		// select the class name.
		int start = 25;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CLASSNAME, PhpElementConciliator.concile(selectedNode));

		// select the class declaration
		start = 36;
		selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CLASSNAME, PhpElementConciliator.concile(selectedNode));

	}

	@Test
	public void concileInterface1() {
		setFileContent(
				"<?php namespace my\name; interface iTemplate{public function setVariable($name, $var);} class Template implements iTemplate{  public function setVariable($name, $var){}}?>");

		Program program = createProgram(file);

		assertNotNull(program);

		// select the class name.
		int start = 25;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CLASSNAME, PhpElementConciliator.concile(selectedNode));

		// select the class declaration
		start = 115;
		selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CLASSNAME, PhpElementConciliator.concile(selectedNode));

	}

	@Test
	public void concileProgram() {
		setFileContent("<?php namespace my\name; class TestRenameClass{}?>");

		Program program = createProgram(file);

		assertNotNull(program);

		assertEquals(PhpElementConciliator.CONCILIATOR_PROGRAM, PhpElementConciliator.concile(program));
	}

	@Test
	public void concileGlobalVar() {
		setFileContent("<?php namespace my\name; $a = 1;  function test(){  global $a; echo $a;} ?>");

		Program program = createProgram(file);

		assertNotNull(program);

		// select the variable declaration.
		int start = 26;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_GLOBAL_VARIABLE, PhpElementConciliator.concile(selectedNode));

		// select the 'global $a'
		start = 60;
		selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_GLOBAL_VARIABLE, PhpElementConciliator.concile(selectedNode));

		// select the 'echo $a'
		start = 69;
		selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_GLOBAL_VARIABLE, PhpElementConciliator.concile(selectedNode));
	}

	@Test
	public void concileGlobalVar1() {
		setFileContent("<?php namespace my\name; $a = 1;  function test(){ echo $GLOBALS['a'];} ?>");

		Program program = createProgram(file);

		assertNotNull(program);

		int start = 67;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_GLOBAL_VARIABLE, PhpElementConciliator.concile(selectedNode));
	}

	@Test
	public void concileFunc() {
		setFileContent("<?php namespace my\name; $a = 1;  function test(){ echo $GLOBALS['a'];} ?>");

		Program program = createProgram(file);

		assertNotNull(program);

		// select the function declaration.
		int start = 35;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_FUNCTION, PhpElementConciliator.concile(selectedNode));

		// selection the function name.
		start = 44;
		selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_FUNCTION, PhpElementConciliator.concile(selectedNode));
	}

	@Test
	public void concileFunc1() {
		setFileContent("<?php namespace my\name; function a($n){return ($n * $n);}echo a(5);?>");

		Program program = createProgram(file);

		assertNotNull(program);

		// select the function declaration.
		int start = 62;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_FUNCTION, PhpElementConciliator.concile(selectedNode));
	}

	@Test
	public void concileMethod1() {
		setFileContent(
				"<?class foo {public static function bar(){return 'bar in a class called';}}$strFN2 = foo::bar;echo bar();?>");

		Program program = createProgram(file);

		assertNotNull(program);

		// select the function declaration.
		int start = 91;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CLASS_MEMBER, PhpElementConciliator.concile(selectedNode));
	}

	@Test
	public void concileMethod2() {
		setFileContent(
				"<? namespace my\name;class foo {public function bar(){return 'bar in a class called';}}$strFN2 = new foo(); $strFN2->bar()?>");

		Program program = createProgram(file);

		assertNotNull(program);

		// select the function declaration.
		int start = 118;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CLASS_MEMBER, PhpElementConciliator.concile(selectedNode));
	}

	@Test
	public void concileMethod3() {
		setFileContent(
				"<? namespace my\name;class foo {public function bar(){return 'bar in a class called';} public function f(){$this->bar();}}?>");

		Program program = createProgram(file);

		assertNotNull(program);

		// select the function declaration.
		int start = 115;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CLASS_MEMBER, PhpElementConciliator.concile(selectedNode));
	}

	@Test
	public void concileMethod4() {
		setFileContent(
				"<? namespace my\name;class foo {public function bar(){return 'bar in a class called';} public function f(){$this->bar();}}?>");

		Program program = createProgram(file);

		assertNotNull(program);

		// select the function declaration.
		int start = 50;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CLASS_MEMBER, PhpElementConciliator.concile(selectedNode));
	}

	@Test
	public void concileField1() {
		setFileContent("<? namespace my\name;class foo {var $f; public function f(){$this->$f;}}?>");

		Program program = createProgram(file);

		assertNotNull(program);

		//
		int start = 33;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CLASS_MEMBER, PhpElementConciliator.concile(selectedNode));

		start = 64;
		selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CLASS_MEMBER, PhpElementConciliator.concile(selectedNode));

	}

	@Test
	public void concileField2() {
		setFileContent(
				"<? namespace my\name;class foo {var $f; public function f(){$this->$f;}} $cls= new foo(); $cls->f;?>");

		Program program = createProgram(file);

		assertNotNull(program);

		//
		int start = 95;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CLASS_MEMBER, PhpElementConciliator.concile(selectedNode));
	}

	@Test
	public void concileStaticField1() {
		setFileContent(
				"<? namespace my\name;class foo {public static $my_static = 'foo';} echo Foo::$my_static; echo $foo->my_static?>");

		Program program = createProgram(file);

		assertNotNull(program);

		//
		int start = 107;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CLASS_MEMBER, PhpElementConciliator.concile(selectedNode));

		start = 78;
		selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CLASS_MEMBER, PhpElementConciliator.concile(selectedNode));

		start = 101;
		selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CLASS_MEMBER, PhpElementConciliator.concile(selectedNode));

	}

	@Test
	public void localVar() {
		setFileContent("<? namespace my\name; $x = 4; function assignx () {$x = 0; echo $x;} ?>");

		Program program = createProgram(file);

		assertNotNull(program);

		// select 'echo $x'
		int start = 65;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_LOCAL_VARIABLE, PhpElementConciliator.concile(selectedNode));

		// declaration
		start = 52;
		selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_LOCAL_VARIABLE, PhpElementConciliator.concile(selectedNode));
	}

	@Test
	public void concileConstant() {
		setFileContent("<?php namespace my\name; define('CONSTANT', 'Hello world.'); echo CONSTANT; echo Constant; ?>");

		Program program = createProgram(file);

		assertNotNull(program);

		// select the function declaration.
		int start = 25;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CONSTANT, PhpElementConciliator.concile(selectedNode));

		start = 34;
		selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CONSTANT, PhpElementConciliator.concile(selectedNode));

		start = 67;
		selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CONSTANT, PhpElementConciliator.concile(selectedNode));

		start = 82;
		selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CONSTANT, PhpElementConciliator.concile(selectedNode));

	}

	@Test
	public void concileConstant1() {
		setFileContent("<?php namespace my\name; define ('TEST', 1234); ?>");

		Program program = createProgram(file);

		assertNotNull(program);

		// select the function declaration.
		int start = 26;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CONSTANT, PhpElementConciliator.concile(selectedNode));
	}

	@Test
	public void concileConstant2() {
		setFileContent("<?php namespace my\name; define ('TEST', 1234); ?>");

		Program program = createProgram(file);

		assertNotNull(program);

		// select the function declaration.
		int start = 27;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CONSTANT, PhpElementConciliator.concile(selectedNode));
	}

	@Test
	public void concileConstant3() {
		setFileContent("<?php namespace NS;define(\"CONSTANT\", \"Hello world.\"); echo \\NS\\CONSTANT;?>");

		Program program = createProgram(file);

		assertNotNull(program);

		// select the function declaration.
		int start = 20;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CONSTANT, PhpElementConciliator.concile(selectedNode));

		start = 28;
		selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CONSTANT, PhpElementConciliator.concile(selectedNode));

		start = 51;
		selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CONSTANT, PhpElementConciliator.concile(selectedNode));

		start = 64;
		selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CONSTANT, PhpElementConciliator.concile(selectedNode));
	}

}
