package org.eclipse.php.internal.core.ast.locator;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.php.core.tests.PHPCoreTests;
import org.eclipse.php.internal.core.ast.nodes.ASTNode;
import org.eclipse.php.internal.core.ast.nodes.FunctionDeclaration;
import org.eclipse.php.internal.core.ast.nodes.Program;
import org.eclipse.php.internal.core.ast.nodes.TypeDeclaration;

public class PhpElementConciliatorTest extends AbstraceConciliatorTest {

	private IProject project1;

	@Override
	protected void setUp() throws Exception {
		System.setProperty("disableStartupRunner", "true");
		PHPCoreTests.waitForIndexer();
		PHPCoreTests.waitForAutoBuild();

		project1 = createProject("project1");

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
			file = setFileContent("<?php class TestRenameClass{}?>");
		} catch (CoreException e) {
			fail(e.getMessage());
		}

		assertNotNull(file);

		Program program = createProgram(file);

		assertNotNull(program);

		// select the class name.
		int start = 13;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CLASSNAME,
				PhpElementConciliator.concile(selectedNode));

		// select the class declaration
		start = 7;
		selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CLASSNAME,
				PhpElementConciliator.concile(selectedNode));
	}

	public void testConcileClassName1() {
		IFile file = null;
		try {
			file = setFileContent("<?php class TestRenameClass{} class TestExtendedClass extends TestRenameClass{}?>");
		} catch (CoreException e) {
			fail(e.getMessage());
		}

		assertNotNull(file);

		Program program = createProgram(file);

		assertNotNull(program);

		// select the class name.
		int start = 63;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CLASSNAME,
				PhpElementConciliator.concile(selectedNode));
	}

	public void testConcileClassName2() {
		IFile file = null;
		try {
			file = setFileContent("<?php class A{function foo(){}} class B{function bar(){}} $a = new A();$a->foo(); A::foo(); $b = new B();$b->bar();B::bar();?>");
		} catch (CoreException e) {
			fail(e.getMessage());
		}

		assertNotNull(file);

		Program program = createProgram(file);

		assertNotNull(program);

		// select the class name.
		int start = 68;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CLASSNAME,
				PhpElementConciliator.concile(selectedNode));

		start = 83;
		selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CLASSNAME,
				PhpElementConciliator.concile(selectedNode));
	}

	public void testConcileInterface() {
		IFile file = null;
		try {
			file = setFileContent("<?php interface iTemplate{public function setVariable($name, $var);}?>");
		} catch (CoreException e) {
			fail(e.getMessage());
		}

		assertNotNull(file);

		Program program = createProgram(file);

		assertNotNull(program);

		// select the class name.
		int start = 6;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CLASSNAME,
				PhpElementConciliator.concile(selectedNode));

		// select the class declaration
		start = 17;
		selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CLASSNAME,
				PhpElementConciliator.concile(selectedNode));

	}

	public void testConcileInterface1() {
		IFile file = null;
		try {
			file = setFileContent("<?php interface iTemplate{public function setVariable($name, $var);} class Template implements iTemplate{  public function setVariable($name, $var){}}?>");
		} catch (CoreException e) {
			fail(e.getMessage());
		}

		assertNotNull(file);

		Program program = createProgram(file);

		assertNotNull(program);

		// select the class name.
		int start = 6;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CLASSNAME,
				PhpElementConciliator.concile(selectedNode));

		// select the class declaration
		start = 96;
		selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CLASSNAME,
				PhpElementConciliator.concile(selectedNode));

	}

	public void testConcileProgram() {
		IFile file = null;
		try {
			file = setFileContent("<?php class TestRenameClass{}?>");
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
			file = setFileContent("<?php $a = 1;  function test(){  global $a; echo $a;} ?>");
		} catch (CoreException e) {
			fail(e.getMessage());
		}

		assertNotNull(file);

		Program program = createProgram(file);

		assertNotNull(program);

		// select the variable declaration.
		int start = 7;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_GLOBAL_VARIABLE,
				PhpElementConciliator.concile(selectedNode));

		// select the 'global $a'
		start = 41;
		selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_GLOBAL_VARIABLE,
				PhpElementConciliator.concile(selectedNode));

		// select the 'echo $a'
		start = 50;
		selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_GLOBAL_VARIABLE,
				PhpElementConciliator.concile(selectedNode));
	}

	public void testConcileGlobalVar1() {
		IFile file = null;
		try {
			file = setFileContent("<?php $a = 1;  function test(){ echo $GLOBALS['a'];} ?>");
		} catch (CoreException e) {
			fail(e.getMessage());
		}

		assertNotNull(file);

		Program program = createProgram(file);

		assertNotNull(program);

		int start = 48;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_GLOBAL_VARIABLE,
				PhpElementConciliator.concile(selectedNode));
	}

	public void testConcileGlobalVar2() {
		IFile file = null;
		try {
			file = setFileContent("<?php $a = 1;  function test(){  global $a; echo $a;} ?>");
		} catch (CoreException e) {
			fail(e.getMessage());
		}

		assertNotNull(file);

		Program program = createProgram(file);

		assertNotNull(program);

		// select the variable declaration.
		int start = 41;

		ASTNode selectedNode = locateNode(program, start, 0);
		selectedNode = selectedNode.getParent();

		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_GLOBAL_VARIABLE,
				PhpElementConciliator.concile(selectedNode));
	}

	public void testConcileFunc() {
		IFile file = null;
		try {
			file = setFileContent("<?php $a = 1;  function test(){ echo $GLOBALS['a'];} ?>");
		} catch (CoreException e) {
			fail(e.getMessage());
		}

		assertNotNull(file);

		Program program = createProgram(file);

		assertNotNull(program);

		// select the function declaration.
		int start = 16;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_FUNCTION,
				PhpElementConciliator.concile(selectedNode));

		// selection the function name.
		start = 25;
		selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_FUNCTION,
				PhpElementConciliator.concile(selectedNode));
	}

	public void testConcileFunc1() {
		IFile file = null;
		try {
			file = setFileContent("<?php function a($n){return ($n * $n);}echo a(5);?>");
		} catch (CoreException e) {
			fail(e.getMessage());
		}

		assertNotNull(file);

		Program program = createProgram(file);

		assertNotNull(program);

		// select the function declaration.
		int start = 45;
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
			file = setFileContent("<?class foo {public function bar(){return 'bar in a class called';}}$strFN2 = new foo(); $strFN2->bar()?>");

		} catch (CoreException e) {
			fail(e.getMessage());
		}

		assertNotNull(file);

		Program program = createProgram(file);

		assertNotNull(program);

		// select the function declaration.
		int start = 99;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CLASS_MEMBER,
				PhpElementConciliator.concile(selectedNode));
	}

	public void testConcileMethod3() {
		IFile file = null;
		try {
			file = setFileContent("<?class foo {public function bar(){return 'bar in a class called';} public function f(){$this->bar();}}?>");

		} catch (CoreException e) {
			fail(e.getMessage());
		}

		assertNotNull(file);

		Program program = createProgram(file);

		assertNotNull(program);

		// select the function declaration.
		int start = 96;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CLASS_MEMBER,
				PhpElementConciliator.concile(selectedNode));
	}

	public void testConcileMethod4() {
		IFile file = null;
		try {
			file = setFileContent("<?class foo {public function bar(){return 'bar in a class called';} public function f(){$this->bar();}}?>");

		} catch (CoreException e) {
			fail(e.getMessage());
		}

		assertNotNull(file);

		Program program = createProgram(file);

		assertNotNull(program);

		// select the function declaration.
		int start = 30;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CLASS_MEMBER,
				PhpElementConciliator.concile(selectedNode));
	}

	public void testConcileField1() {
		IFile file = null;
		try {
			file = setFileContent("<?class foo {var $f; public function f(){$this->$f;}}?>");

		} catch (CoreException e) {
			fail(e.getMessage());
		}

		assertNotNull(file);

		Program program = createProgram(file);

		assertNotNull(program);

		// 
		int start = 14;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CLASS_MEMBER,
				PhpElementConciliator.concile(selectedNode));

		start = 45;
		selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CLASS_MEMBER,
				PhpElementConciliator.concile(selectedNode));

	}

	public void testConcileField2() {
		IFile file = null;
		try {
			file = setFileContent("<?class foo {var $f; public function f(){$this->$f;}} $cls= new foo(); $cls->f;?>");

		} catch (CoreException e) {
			fail(e.getMessage());
		}

		assertNotNull(file);

		Program program = createProgram(file);

		assertNotNull(program);

		//
		int start = 78;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CLASS_MEMBER,
				PhpElementConciliator.concile(selectedNode));
	}

	public void testConcileField3() {
		IFile file = null;
		try {
			file = setFileContent("<?class foo {var $f; public function f(){$this->$f;}} $cls= new foo(); $cls->f;?>");

		} catch (CoreException e) {
			fail(e.getMessage());
		}

		assertNotNull(file);

		Program program = createProgram(file);

		assertNotNull(program);

		//
		int start = 18;
		ASTNode selectedNode = locateNode(program, start, 0);
		selectedNode = selectedNode.getParent();
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CLASS_MEMBER,
				PhpElementConciliator.concile(selectedNode));
	}

	public void testConcileStaticField1() {
		IFile file = null;
		try {
			file = setFileContent("<?class foo {public static $my_static = 'foo';} echo Foo::$my_static; echo $foo->my_static?>");

		} catch (CoreException e) {
			fail(e.getMessage());
		}

		assertNotNull(file);

		Program program = createProgram(file);

		assertNotNull(program);

		//
		int start = 28;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CLASS_MEMBER,
				PhpElementConciliator.concile(selectedNode));

		start = 59;
		selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CLASS_MEMBER,
				PhpElementConciliator.concile(selectedNode));

		start = 82;
		selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CLASS_MEMBER,
				PhpElementConciliator.concile(selectedNode));

	}

	public void testLocalVar() {
		IFile file = null;
		try {
			file = setFileContent("<? $x = 4; function assignx () {$x = 0; echo $x;} ?>");

		} catch (CoreException e) {
			fail(e.getMessage());
		}

		assertNotNull(file);

		Program program = createProgram(file);

		assertNotNull(program);

		// select 'echo $x'
		int start = 46;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_LOCAL_VARIABLE,
				PhpElementConciliator.concile(selectedNode));

		// declaration
		start = 33;
		selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_LOCAL_VARIABLE,
				PhpElementConciliator.concile(selectedNode));
	}

	public void testLocalVar1() {
		IFile file = null;
		try {
			file = setFileContent("<? $x = 4; function assignx () {$x = 0; echo $x;} ?>");

		} catch (CoreException e) {
			fail(e.getMessage());
		}

		assertNotNull(file);

		Program program = createProgram(file);

		assertNotNull(program);

		// select 'echo $x'
		int start = 33;
		ASTNode selectedNode = locateNode(program, start, 0);
		selectedNode = selectedNode.getParent();
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_LOCAL_VARIABLE,
				PhpElementConciliator.concile(selectedNode));

	}

	public void testConcileConstant() {
		IFile file = null;
		try {
			file = setFileContent("<?php define(\"CONSTANT\", \"Hello world.\"); echo CONSTANT; echo Constant; ?>");
		} catch (CoreException e) {
			fail(e.getMessage());
		}

		assertNotNull(file);

		Program program = createProgram(file);

		assertNotNull(program);

		// select the function declaration.
		int start = 6;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CONSTANT,
				PhpElementConciliator.concile(selectedNode));

		start = 15;
		selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CONSTANT,
				PhpElementConciliator.concile(selectedNode));

		start = 48;
		selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CONSTANT,
				PhpElementConciliator.concile(selectedNode));

		start = 63;
		selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CONSTANT,
				PhpElementConciliator.concile(selectedNode));

	}

	public void testConcileConstant1() {
		IFile file = null;
		try {
			file = setFileContent("<?php define (\"TEST\", 1234);");
		} catch (CoreException e) {
			fail(e.getMessage());
		}

		assertNotNull(file);

		Program program = createProgram(file);

		assertNotNull(program);

		// select the function declaration.
		int start = 6;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CONSTANT,
				PhpElementConciliator.concile(selectedNode));
	}

	public void testConcileConstantExists() {
		IFile file = null;
		try {
			file = setFileContent("<?php define (\"TEST\", 1234);");
		} catch (CoreException e) {
			fail(e.getMessage());
		}

		assertNotNull(file);

		Program program = createProgram(file);

		assertNotNull(program);

		assertTrue(PhpElementConciliator.constantAlreadyExists(program, "TEST"));
	}

	public void testConcileClsMemberExists() {
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
		int start = 8;
		ASTNode selectedNode = locateNode(program, start, 0);
		selectedNode = selectedNode.getParent();
		assertNotNull(selectedNode);

		assertTrue(selectedNode instanceof TypeDeclaration);

		assertTrue(PhpElementConciliator.classMemeberAlreadyExists(
				(TypeDeclaration) selectedNode, "bar",
				ASTNode.FUNCTION_DECLARATION));
	}

	public void testConcileClassNameExists() {
		IFile file = null;
		try {
			file = setFileContent("<?php class TestRenameClass{}?>");
		} catch (CoreException e) {
			fail(e.getMessage());
		}

		assertNotNull(file);

		Program program = createProgram(file);

		assertNotNull(program);

		PhpElementConciliator
				.classNameAlreadyExists(program, "TestRenameClass");
	}

	public void testLocalVarExists() {
		IFile file = null;
		try {
			file = setFileContent("<? $x = 4; function assignx () {$x = 0; echo $x;} ?>");

		} catch (CoreException e) {
			fail(e.getMessage());
		}

		assertNotNull(file);

		Program program = createProgram(file);

		assertNotNull(program);

		int start = 21;
		ASTNode selectedNode = locateNode(program, start, 0);
		selectedNode = selectedNode.getParent();
		assertNotNull(selectedNode);

		PhpElementConciliator.localVariableAlreadyExists(
				(FunctionDeclaration) selectedNode, "x");
	}

	public void testFunExists1() {
		IFile file = null;
		try {
			file = setFileContent("<? $x = 4; function assignx () {$x = 0; echo $x;} ?>");

		} catch (CoreException e) {
			fail(e.getMessage());
		}

		assertNotNull(file);

		Program program = createProgram(file);

		assertNotNull(program);

		PhpElementConciliator.functionAlreadyExists(program, "assignx");
	}

	public void testConcileGlobalExists() {
		IFile file = null;
		try {
			file = setFileContent("<?php $a = 1;  function test(){ echo $GLOBALS['a'];} ?>");
		} catch (CoreException e) {
			fail(e.getMessage());
		}

		assertNotNull(file);

		Program program = createProgram(file);

		assertNotNull(program);

		assertTrue(PhpElementConciliator.globalVariableAlreadyExists(program,
				"a"));
	}

}
