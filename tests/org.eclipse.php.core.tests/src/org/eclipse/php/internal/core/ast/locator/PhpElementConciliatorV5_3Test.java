package org.eclipse.php.internal.core.ast.locator;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.php.core.tests.PHPCoreTests;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.ast.nodes.ASTNode;
import org.eclipse.php.internal.core.ast.nodes.Program;

public class PhpElementConciliatorV5_3Test extends AbstraceConciliatorTest {

	private IProject project1;

	@Override
	protected void setUp() throws Exception {
		System.setProperty("disableStartupRunner", "true");
		PHPCoreTests.waitForIndexer();
		PHPCoreTests.waitForAutoBuild();

		project1 = createProject("project53", getPHPVersion());

	}

	protected PHPVersion getPHPVersion() {
		return PHPVersion.PHP5_3;
	}

	private IFile setFileContent(String content) throws CoreException {
		IFile file = project1.getFile("test1.php");
		InputStream source = new ByteArrayInputStream(content.getBytes());

		if (!file.exists()) {
			file.create(source, true, new NullProgressMonitor());
		} else {
			file.setContents(source, IFile.FORCE, new NullProgressMonitor());
		}

		PHPCoreTests.waitForIndexer();
		PHPCoreTests.waitForAutoBuild();
		return file;
	}

	public void testConcileClassName() {
		IFile file = null;
		try {
			file = setFileContent("<?php namespace my\name; class TestRenameClass{} new TestRenameClass(); ?>");
		} catch (CoreException e) {
			fail(e.getMessage());
		}

		assertNotNull(file);

		Program program = createProgram(file);

		assertNotNull(program);

		// select the class name.
		int start = 54;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CLASSNAME,
				PhpElementConciliator.concile(selectedNode));

		// select the class declaration
		start = 26;
		selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CLASSNAME,
				PhpElementConciliator.concile(selectedNode));
	}

	public void testConcileClassName1() {
		IFile file = null;
		try {
			file = setFileContent("<?php namespace my\name; class TestRenameClass{} class TestExtendedClass extends TestRenameClass{}?>");
		} catch (CoreException e) {
			fail(e.getMessage());
		}

		assertNotNull(file);

		Program program = createProgram(file);

		assertNotNull(program);

		// select the class name.
		int start = 82;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CLASSNAME,
				PhpElementConciliator.concile(selectedNode));
	}

	public void testConcileClassName2() {
		IFile file = null;
		try {
			file = setFileContent("<?php namespace my\name; class A{function foo(){}} $a = new A();$a->foo(); my\\name\\A::foo();?>");
		} catch (CoreException e) {
			fail(e.getMessage());
		}

		assertNotNull(file);

		Program program = createProgram(file);

		assertNotNull(program);

		// select the class name.
		int start = 59;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CLASSNAME,
				PhpElementConciliator.concile(selectedNode));

		start = 82;
		selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CLASSNAME,
				PhpElementConciliator.concile(selectedNode));
	}

	public void testConcileInterface() {
		IFile file = null;
		try {
			file = setFileContent("<?php namespace my\name; interface iTemplate{public function setVariable($name, $var);}?>");
		} catch (CoreException e) {
			fail(e.getMessage());
		}

		assertNotNull(file);

		Program program = createProgram(file);

		assertNotNull(program);

		// select the class name.
		int start = 25;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CLASSNAME,
				PhpElementConciliator.concile(selectedNode));

		// select the class declaration
		start = 36;
		selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CLASSNAME,
				PhpElementConciliator.concile(selectedNode));

	}

	public void testConcileInterface1() {
		IFile file = null;
		try {
			file = setFileContent("<?php namespace my\name; interface iTemplate{public function setVariable($name, $var);} class Template implements iTemplate{  public function setVariable($name, $var){}}?>");
		} catch (CoreException e) {
			fail(e.getMessage());
		}

		assertNotNull(file);

		Program program = createProgram(file);

		assertNotNull(program);

		// select the class name.
		int start = 25;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CLASSNAME,
				PhpElementConciliator.concile(selectedNode));

		// select the class declaration
		start = 115;
		selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CLASSNAME,
				PhpElementConciliator.concile(selectedNode));

	}

	public void testConcileProgram() {
		IFile file = null;
		try {
			file = setFileContent("<?php namespace my\name; class TestRenameClass{}?>");
		} catch (CoreException e) {
			fail(e.getMessage());
		}

		assertNotNull(file);

		Program program = createProgram(file);

		assertNotNull(program);

		assertEquals(PhpElementConciliator.CONCILIATOR_PROGRAM,
				PhpElementConciliator.concile(program));
	}

	public void testConcileGlobalVar() {
		IFile file = null;
		try {
			file = setFileContent("<?php namespace my\name; $a = 1;  function test(){  global $a; echo $a;} ?>");
		} catch (CoreException e) {
			fail(e.getMessage());
		}

		assertNotNull(file);

		Program program = createProgram(file);

		assertNotNull(program);

		// select the variable declaration.
		int start = 26;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_GLOBAL_VARIABLE,
				PhpElementConciliator.concile(selectedNode));

		// select the 'global $a'
		start = 60;
		selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_GLOBAL_VARIABLE,
				PhpElementConciliator.concile(selectedNode));

		// select the 'echo $a'
		start = 69;
		selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_GLOBAL_VARIABLE,
				PhpElementConciliator.concile(selectedNode));
	}

	public void testConcileGlobalVar1() {
		IFile file = null;
		try {
			file = setFileContent("<?php namespace my\name; $a = 1;  function test(){ echo $GLOBALS['a'];} ?>");
		} catch (CoreException e) {
			fail(e.getMessage());
		}

		assertNotNull(file);

		Program program = createProgram(file);

		assertNotNull(program);

		int start = 67;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_GLOBAL_VARIABLE,
				PhpElementConciliator.concile(selectedNode));
	}

	public void testConcileFunc() {
		IFile file = null;
		try {
			file = setFileContent("<?php namespace my\name; $a = 1;  function test(){ echo $GLOBALS['a'];} ?>");
		} catch (CoreException e) {
			fail(e.getMessage());
		}

		assertNotNull(file);

		Program program = createProgram(file);

		assertNotNull(program);

		// select the function declaration.
		int start = 35;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_FUNCTION,
				PhpElementConciliator.concile(selectedNode));

		// selection the function name.
		start = 44;
		selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_FUNCTION,
				PhpElementConciliator.concile(selectedNode));
	}

	public void testConcileFunc1() {
		IFile file = null;
		try {
			file = setFileContent("<?php namespace my\name; function a($n){return ($n * $n);}echo a(5);?>");
		} catch (CoreException e) {
			fail(e.getMessage());
		}

		assertNotNull(file);

		Program program = createProgram(file);

		assertNotNull(program);

		// select the function declaration.
		int start = 62;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_FUNCTION,
				PhpElementConciliator.concile(selectedNode));
	}

	public void testConcileMethod1() {
		IFile file = null;
		try {
			file = setFileContent("<?class foo {public static function bar(){return 'bar in a class called';}}$strFN2 = foo::bar;echo bar();?>");

		} catch (CoreException e) {
			fail(e.getMessage());
		}

		assertNotNull(file);

		Program program = createProgram(file);

		assertNotNull(program);

		// select the function declaration.
		int start = 91;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CLASS_MEMBER,
				PhpElementConciliator.concile(selectedNode));
	}

	public void testConcileMethod2() {
		IFile file = null;
		try {
			file = setFileContent("<? namespace my\name;class foo {public function bar(){return 'bar in a class called';}}$strFN2 = new foo(); $strFN2->bar()?>");

		} catch (CoreException e) {
			fail(e.getMessage());
		}

		assertNotNull(file);

		Program program = createProgram(file);

		assertNotNull(program);

		// select the function declaration.
		int start = 118;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CLASS_MEMBER,
				PhpElementConciliator.concile(selectedNode));
	}

	public void testConcileMethod3() {
		IFile file = null;
		try {
			file = setFileContent("<? namespace my\name;class foo {public function bar(){return 'bar in a class called';} public function f(){$this->bar();}}?>");

		} catch (CoreException e) {
			fail(e.getMessage());
		}

		assertNotNull(file);

		Program program = createProgram(file);

		assertNotNull(program);

		// select the function declaration.
		int start = 115;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CLASS_MEMBER,
				PhpElementConciliator.concile(selectedNode));
	}

	public void testConcileMethod4() {
		IFile file = null;
		try {
			file = setFileContent("<? namespace my\name;class foo {public function bar(){return 'bar in a class called';} public function f(){$this->bar();}}?>");

		} catch (CoreException e) {
			fail(e.getMessage());
		}

		assertNotNull(file);

		Program program = createProgram(file);

		assertNotNull(program);

		// select the function declaration.
		int start = 50;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CLASS_MEMBER,
				PhpElementConciliator.concile(selectedNode));
	}

	public void testConcileField1() {
		IFile file = null;
		try {
			file = setFileContent("<? namespace my\name;class foo {var $f; public function f(){$this->$f;}}?>");

		} catch (CoreException e) {
			fail(e.getMessage());
		}

		assertNotNull(file);

		Program program = createProgram(file);

		assertNotNull(program);

		//
		int start = 33;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CLASS_MEMBER,
				PhpElementConciliator.concile(selectedNode));

		start = 64;
		selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CLASS_MEMBER,
				PhpElementConciliator.concile(selectedNode));

	}

	public void testConcileField2() {
		IFile file = null;
		try {
			file = setFileContent("<? namespace my\name;class foo {var $f; public function f(){$this->$f;}} $cls= new foo(); $cls->f;?>");

		} catch (CoreException e) {
			fail(e.getMessage());
		}

		assertNotNull(file);

		Program program = createProgram(file);

		assertNotNull(program);

		//
		int start = 95;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CLASS_MEMBER,
				PhpElementConciliator.concile(selectedNode));
	}

	public void testConcileStaticField1() {
		IFile file = null;
		try {
			file = setFileContent("<? namespace my\name;class foo {public static $my_static = 'foo';} echo Foo::$my_static; echo $foo->my_static?>");

		} catch (CoreException e) {
			fail(e.getMessage());
		}

		assertNotNull(file);

		Program program = createProgram(file);

		assertNotNull(program);

		//
		int start = 107;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CLASS_MEMBER,
				PhpElementConciliator.concile(selectedNode));

		start = 78;
		selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CLASS_MEMBER,
				PhpElementConciliator.concile(selectedNode));

		start = 101;
		selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CLASS_MEMBER,
				PhpElementConciliator.concile(selectedNode));

	}

	public void testLocalVar() {
		IFile file = null;
		try {
			file = setFileContent("<? namespace my\name; $x = 4; function assignx () {$x = 0; echo $x;} ?>");

		} catch (CoreException e) {
			fail(e.getMessage());
		}

		assertNotNull(file);

		Program program = createProgram(file);

		assertNotNull(program);

		// select 'echo $x'
		int start = 65;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_LOCAL_VARIABLE,
				PhpElementConciliator.concile(selectedNode));

		// declaration
		start = 52;
		selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_LOCAL_VARIABLE,
				PhpElementConciliator.concile(selectedNode));
	}

	public void testConcileConstant() {
		IFile file = null;
		try {
			file = setFileContent("<?php namespace my\name; define('CONSTANT', 'Hello world.'); echo CONSTANT; echo Constant; ?>");
		} catch (CoreException e) {
			fail(e.getMessage());
		}

		assertNotNull(file);

		Program program = createProgram(file);

		assertNotNull(program);

		// select the function declaration.
		int start = 25;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CONSTANT,
				PhpElementConciliator.concile(selectedNode));

		start = 34;
		selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CONSTANT,
				PhpElementConciliator.concile(selectedNode));

		start = 67;
		selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CONSTANT,
				PhpElementConciliator.concile(selectedNode));

		start = 82;
		selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CONSTANT,
				PhpElementConciliator.concile(selectedNode));

	}

	public void testConcileConstant1() {
		IFile file = null;
		try {
			file = setFileContent("<?php namespace my\name; define ('TEST', 1234); ?>");
		} catch (CoreException e) {
			fail(e.getMessage());
		}

		assertNotNull(file);

		Program program = createProgram(file);

		assertNotNull(program);

		// select the function declaration.
		int start = 26;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CONSTANT,
				PhpElementConciliator.concile(selectedNode));
	}

	public void testConcileConstant2() {
		IFile file = null;
		try {
			file = setFileContent("<?php namespace my\name; define ('TEST', 1234); ?>");
		} catch (CoreException e) {
			fail(e.getMessage());
		}

		assertNotNull(file);

		Program program = createProgram(file);

		assertNotNull(program);

		// select the function declaration.
		int start = 27;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CONSTANT,
				PhpElementConciliator.concile(selectedNode));
	}

	public void testConcileConstant3() {
		IFile file = null;
		try {
			file = setFileContent("<?php namespace NS;define(\"CONSTANT\", \"Hello world.\"); echo \\NS\\CONSTANT;?>");
		} catch (CoreException e) {
			fail(e.getMessage());
		}

		assertNotNull(file);

		Program program = createProgram(file);

		assertNotNull(program);

		// select the function declaration.
		int start = 20;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CONSTANT,
				PhpElementConciliator.concile(selectedNode));

		start = 28;
		selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CONSTANT,
				PhpElementConciliator.concile(selectedNode));

		start = 51;
		selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CONSTANT,
				PhpElementConciliator.concile(selectedNode));

		start = 64;
		selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CONSTANT,
				PhpElementConciliator.concile(selectedNode));
	}

}
