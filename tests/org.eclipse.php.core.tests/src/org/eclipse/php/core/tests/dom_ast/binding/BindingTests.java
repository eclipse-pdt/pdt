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
package org.eclipse.php.core.tests.dom_ast.binding;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.ast.nodes.ASTParser;
import org.eclipse.php.core.ast.nodes.Assignment;
import org.eclipse.php.core.ast.nodes.ClassDeclaration;
import org.eclipse.php.core.ast.nodes.ClassInstanceCreation;
import org.eclipse.php.core.ast.nodes.Expression;
import org.eclipse.php.core.ast.nodes.ExpressionStatement;
import org.eclipse.php.core.ast.nodes.FieldAccess;
import org.eclipse.php.core.ast.nodes.FunctionDeclaration;
import org.eclipse.php.core.ast.nodes.FunctionInvocation;
import org.eclipse.php.core.ast.nodes.IBinding;
import org.eclipse.php.core.ast.nodes.IFunctionBinding;
import org.eclipse.php.core.ast.nodes.IMethodBinding;
import org.eclipse.php.core.ast.nodes.ITypeBinding;
import org.eclipse.php.core.ast.nodes.IVariableBinding;
import org.eclipse.php.core.ast.nodes.Include;
import org.eclipse.php.core.ast.nodes.InfixExpression;
import org.eclipse.php.core.ast.nodes.InterfaceDeclaration;
import org.eclipse.php.core.ast.nodes.MethodDeclaration;
import org.eclipse.php.core.ast.nodes.MethodInvocation;
import org.eclipse.php.core.ast.nodes.Program;
import org.eclipse.php.core.ast.nodes.StaticConstantAccess;
import org.eclipse.php.core.ast.nodes.StaticFieldAccess;
import org.eclipse.php.core.ast.nodes.StaticMethodInvocation;
import org.eclipse.php.core.project.ProjectOptions;
import org.eclipse.php.core.tests.TestSuiteWatcher;
import org.eclipse.php.core.tests.TestUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

public class BindingTests {

	@ClassRule
	public static TestWatcher watcher = new TestSuiteWatcher();

	protected static IProject project;
	private IFile testFile;

	@BeforeClass
	public static void setUpSuite() {
		project = TestUtils.createProject("BindingTests");
	}

	@AfterClass
	public static void tearDownSuite() {
		TestUtils.deleteProject(project);
	}

	@After
	public void tearDown() throws Exception {
		TestUtils.deleteFile(testFile);
		testFile = null;
	}

	/**
	 * Locate the index of the marker (comment block) in the given content.
	 */
	private int locateElement(String content, int fromIndex) {
		final int indexOf = content.indexOf("/**/", fromIndex);
		return indexOf + 4;
	}

	/**
	 * Locate the index of the marker (comment block) in the given content.
	 * (start the search from the zero index)
	 */
	private int locateElement(String content) {
		return locateElement(content, 0);
	}

	protected Program createAndParse(String code) throws Exception {
		if (testFile == null) {
			testFile = TestUtils.createFile(project, "test.php", code);
			// Wait for indexer...
			TestUtils.waitForIndexer();
		}
		PHPVersion version = ProjectOptions.getDefaultPHPVersion();
		ISourceModule sourceModule = null;
		sourceModule = DLTKCore.createSourceModuleFrom(testFile);
		ASTParser parser = ASTParser.newParser(new InputStreamReader(testFile.getContents()), version, false,
				sourceModule);
		return parser.createAST(new NullProgressMonitor());
	}

	/**
	 * Get the ITypeBinding for the marked content in he given string.
	 * 
	 * @param content
	 * @param fromIndex
	 *            - The index to start searching for the content marker from
	 * @return
	 * @throws CoreException
	 * @throws Exception
	 */
	protected ITypeBinding getTypeBinding(String content, int fromIndex) throws CoreException, Exception {
		Program program = createAndParse(content);

		// locate the expression to test
		int indexOf = locateElement(content, fromIndex);
		Expression expr = (Expression) program.getElementAt(indexOf);

		// resolve binding of the expression
		ITypeBinding type = expr.resolveTypeBinding();
		return type;
	}

	/**
	 * Get the ITypeBinding for the marked content in he given string. Same as
	 * calling getTypeBinding(content, 0).
	 * 
	 * @param content
	 * @return
	 * @throws CoreException
	 * @throws Exception
	 */
	protected ITypeBinding getTypeBinding(String content) throws CoreException, Exception {
		return getTypeBinding(content, 0);
	}

	@Test
	public void basicExpression() throws Exception {
		// create a file and get the program's root
		String content = "<? class A { function foo() { $a = 4; $b =  /**/$a; } } ?>";
		ITypeBinding type = getTypeBinding(content);

		// check that it is a number
		assertEquals(type.getName(), "number");
	}

	@Test
	public void basicType() throws Exception {
		// create a file and get the program's root
		String content = "<? class A {  } ; class B extends /**/A { } ?>";
		ITypeBinding type = getTypeBinding(content);

		// check that it is a number
		assertEquals("A", type.getName());
	}

	@Test
	public void isArray() throws Exception {
		// create a file and get the program's root
		String content = "<? interface B {} class A implements B { function foo() { $a = array(1, 2, 3); $b = /**/$a; } } ?>";
		ITypeBinding type = getTypeBinding(content);
		assertTrue(type.isArray());
	}

	@Test
	public void isClass() throws Exception {
		// create a file and get the program's root
		String content = "<? interface B {} class A implements B { function foo() { $a = new A(); $b = /**/$a; } } ?>";
		ITypeBinding type = getTypeBinding(content);
		assertTrue(type.isClass());
	}

	@Test
	public void isInterface() throws Exception {
		// create a file and get the program's root
		String content = "<? interface B {} class A implements /**/B { function foo() { $a = new A(); $b = $a; } } ?>";
		ITypeBinding type = getTypeBinding(content);
		assertTrue(type.isInterface());
	}

	@Test
	public void isNullType() throws Exception {
		// create a file and get the program's root
		String content = "<? interface B {} class A implements B { function foo() { $a = null; $b = /**/$a; } } ?>";
		ITypeBinding type = getTypeBinding(content);
		assertTrue(type.isNullType());
	}

	@Test
	public void isPrimitive() throws Exception {
		// create a file and get the program's root
		String content = "<? interface B {} class A implements B { function foo() { $a = true; $b = /**/$a; } } ?>";
		ITypeBinding type = getTypeBinding(content);
		assertTrue(type.isPrimitive());
	}

	@Test
	public void isSubTypeCompatibleTrue() throws Exception {
		// create a file and get the program's root
		String content = "<? interface B {} class A implements /**/B { function foo() { $a = true; $b = $a; } } class C extends A{} /**/$c = new C()?>";
		int indexOf = locateElement(content, 0);
		ITypeBinding type = getTypeBinding(content);
		ITypeBinding otherType = getTypeBinding(content, indexOf + 4);
		assertTrue("Should be sub-type compatible", //$NON-NLS-1$
				otherType.isSubTypeCompatible(type));
	}

	@Test
	public void isSubTypeCompatibleFalse() throws Exception {
		// create a file and get the program's root
		String content = "<? interface B {} class A implements /**/B { function foo() { $a = true; $b = $a; } } class C extends A{} /**/$c = new C()?>";
		int indexOf = locateElement(content, 0);
		ITypeBinding type = getTypeBinding(content);
		ITypeBinding otherType = getTypeBinding(content, indexOf + 4);
		assertFalse("Should NOT be sub-type compatible", type.isSubTypeCompatible(otherType));
	}

	@Test
	public void binaryName() throws Exception {
		// create a file and get the program's root
		String content = "<? interface B {} class A implements B { function foo() { $a = new A(); $b = /**/$a; } } ?>";
		ITypeBinding type = getTypeBinding(content);
		String binaryName = type.getBinaryName();
		assertNotNull("Binary name was null", binaryName);
	}

	// public void testArraysGetComponentType() throws Exception {
	// // create a file and get the program's root
	// String content =
	// "<? interface B {} class A implements B { function foo() { $a = array(1,
	// 2, 3); $b = /**/$a; } } ?>";
	// ITypeBinding type = getTypeBinding(content);
	// // TODO - Right now, this test will always fail until we implement this
	// // functionality.
	// assertTrue(
	// "getComponentType() should return a non-null value for arrays",
	// type.isArray() && type.getComponentType() != null);
	// }

	@Test
	public void getDeclaredFields() throws Exception {
		// create a file and get the program's root
		String content = "<? interface B {} class A implements B { public $value; private $pValue; function foo() { $a = new A(); $b = /**/$a; } } ?>";
		ITypeBinding type = getTypeBinding(content);
		IVariableBinding[] declaredFields = type.getDeclaredFields();
		declaredFields[0].getDeclaringClass();
		// declaredFields[0].getDeclaringFunction();
		// TODO - complete the implementation of VariableBinding
	}

	@Test
	public void getDeclaredMethods() throws Exception {
		// create a file and get the program's root
		String content = "<? interface B {} class A implements B { public $value; private $pValue; function foo() { $a = new A(); $b = /**/$a; } } ?>";
		ITypeBinding type = getTypeBinding(content);
		IMethodBinding[] declaredMethods = type.getDeclaredMethods();
		assertTrue("The declaring type name is wrong", declaredMethods[0].getDeclaringClass().getName().equals("A"));
		// declaredFields[0].getDeclaringFunction();
		// TODO - complete the implementation of MethodBinding
	}

	@Test
	public void overrides() throws Exception {
		// create a file and get the program's root
		String content = "<? interface B { function foo(); } class A implements /**/B { public $value; private $pValue; function foo() { $a = new A(); $b = /**/$a; } function boo() {} function foo($g) {} } ?>";
		int index = locateElement(content);
		ITypeBinding type = getTypeBinding(content);
		IMethodBinding[] interfaceBMethods = type.getDeclaredMethods();

		type = getTypeBinding(content, index + 4);
		IMethodBinding[] classAMethods = type.getDeclaredMethods();
		assertTrue("Should override", classAMethods[0].overrides(interfaceBMethods[0]));
		assertFalse("Should NOT override", classAMethods[1].overrides(interfaceBMethods[0]));
		assertTrue("Should override", classAMethods[2].overrides(interfaceBMethods[0]));
	}

	// public void testConstantExpressionBinding() throws Exception {
	// String str =
	// "<?php class A{ const MY_CONSTANT = 3;} function foo() { echo
	// /**/MY_CONSTANT; } ?>";
	// Program program = createAndParse(str);
	//
	// // locate the expression to test
	// int indexOf = locateElement(str);
	// Expression expr = (Expression) program.getElementAt(indexOf);
	// Object resolveConstantExpressionValue = expr
	// .resolveConstantExpressionValue();
	// assertNotNull(resolveConstantExpressionValue);
	// }

	@Test
	public void constructorBinding() throws Exception {
		String str = "<?php $a = new MyClass(); class MyClass { public function MyClass() {} } ?>";
		Program program = createAndParse(str);

		ExpressionStatement statement = (ExpressionStatement) program.statements().get(0);
		Assignment assignment = (Assignment) statement.getExpression();
		ClassInstanceCreation instanceCreation = (ClassInstanceCreation) assignment.getRightHandSide();

		IMethodBinding constructorBinding = instanceCreation.resolveConstructorBinding();

		assertNotNull(constructorBinding);
		assertTrue(constructorBinding.isConstructor() == true);
		assertTrue(constructorBinding.getName().equals("MyClass"));
		assertTrue(constructorBinding.getKind() == IBinding.METHOD);
	}

	@Test
	public void expressionBinding() throws Exception {
		String str = "<?php $a = 5+5 ?>";
		Program program = createAndParse(str);

		ExpressionStatement statement = (ExpressionStatement) program.statements().get(0);
		Assignment assignment = (Assignment) statement.getExpression();
		InfixExpression infixExpression = (InfixExpression) assignment.getRightHandSide();

		ITypeBinding expressionBinding = infixExpression.resolveTypeBinding();

		assertTrue(expressionBinding.getKind() == IBinding.TYPE);
		// TODO
	}

	@Test
	public void fieldAccessBinding() throws Exception {
		String str = "<?php class MyClass { var $anotherOne; }; $a = new MyClass(); $b = $a->anotherOne ?>";
		Program program = createAndParse(str);

		ExpressionStatement statement = (ExpressionStatement) program.statements().get(3);
		Assignment assignment = (Assignment) statement.getExpression();
		FieldAccess fieldAccess = (FieldAccess) assignment.getRightHandSide();

		IVariableBinding variableBinding = fieldAccess.resolveFieldBinding();

		assertTrue(variableBinding.isField() == true);
		assertTrue(variableBinding.getName().equals("$anotherOne"));
		assertTrue(variableBinding.getKind() == IBinding.VARIABLE);
	}

	@Test
	public void thisFieldAccessBinding() throws Exception {
		String str = "<?php class MyClass { public $myvar = \"test\"; public function mymethod(){ return $this->myvar; }} $a = new MyClass(); $a->mymethod();?>";
		Program program = createAndParse(str);

		ExpressionStatement statement = (ExpressionStatement) program.statements().get(2);
		MethodInvocation methodInvocation = (MethodInvocation) statement.getExpression();
		IMethodBinding methodBinding = methodInvocation.resolveMethodBinding();

		assertNotNull(methodBinding);
		assertTrue(methodBinding.getName().equals("mymethod"));
		assertNotNull(methodBinding.getDeclaringClass());
		assertTrue(methodBinding.getDeclaringClass().getName().equals("MyClass"));
		assertTrue(methodBinding.isConstructor() == false);
		assertTrue(methodBinding.getReturnType()[0].getName().equals("string"));
	}

	@Test
	public void staticFieldAccessBinding() throws Exception {
		String str = "<?php class MyClass { public static $a = 4; } ; /**/MyClass::$a;?>";
		Program program = createAndParse(str);

		final ExpressionStatement statement = (ExpressionStatement) program.statements().get(2);
		final StaticFieldAccess staticFieldAcces = (StaticFieldAccess) statement.getExpression();
		IVariableBinding fieldBinding = staticFieldAcces.resolveFieldBinding();

		assertTrue(fieldBinding.isField() == true);
		assertTrue(fieldBinding.getName().equals("$a"));
		assertTrue(fieldBinding.getKind() == IBinding.VARIABLE);
	}

	@Test
	public void staticConstantAccessBinding() throws Exception {
		String str = "<?php class MyClass { const A = 4; } ; /**/MyClass::A;?>";
		Program program = createAndParse(str);

		final ExpressionStatement statement = (ExpressionStatement) program.statements().get(2);
		final StaticConstantAccess constantAccess = (StaticConstantAccess) statement.getExpression();
		IVariableBinding fieldBinding = (IVariableBinding) constantAccess.resolveFieldBinding();

		assertNotNull(fieldBinding);
		assertTrue(fieldBinding.isField());
	}

	@Test
	public void includeBinding() throws Exception {
		String str = "<?php include('myFile.php');?>";
		Program program = createAndParse(str);

		IFile myFile = project.getFile("myFile.php");
		myFile.create(new ByteArrayInputStream(new byte[] {}), true, new NullProgressMonitor());
		try {

			ExpressionStatement statement = (ExpressionStatement) program.statements().get(0);
			Include include = (Include) statement.getExpression();

			IBinding sourceBinding = include.resolveBinding();

			assertTrue(sourceBinding.getName().equals("myFile.php") == true);
			assertTrue(sourceBinding.getPHPElement().getElementType() == IModelElement.SOURCE_MODULE);
			assertTrue(sourceBinding.getKind() == IBinding.INCLUDE);

		} finally {
			myFile.delete(true, new NullProgressMonitor());
		}
	}

	@Test
	public void functionDeclarationBinding() throws Exception {
		String str = "<?php function foo() { return new SoapClient(); } ?> ";
		Program program = createAndParse(str);

		FunctionDeclaration functionDeclaration = (FunctionDeclaration) program.statements().get(0);

		IFunctionBinding functionBinding = functionDeclaration.resolveFunctionBinding();

		assertNotNull(functionBinding);
		assertTrue(functionBinding.getName().equals("foo"));

		ITypeBinding[] returnTypes = functionBinding.getReturnType();
		assertTrue(returnTypes[0].getName().equals("SoapClient"));
	}

	@Test
	public void methodDeclarationBinding() throws Exception {
		String str = "<?php class MyClass { function foo(){ return new MyClass(); } } ?>";
		Program program = createAndParse(str);

		ClassDeclaration classDeclaration = (ClassDeclaration) program.statements().get(0);
		MethodDeclaration methodDeclaration = (MethodDeclaration) classDeclaration.getBody().statements().get(0);

		IMethodBinding methodBinding = methodDeclaration.resolveMethodBinding();
		assertNotNull(methodBinding);
		assertTrue(methodBinding.getName().equals("foo"));
		assertNotNull(methodBinding.getDeclaringClass());
		assertTrue(methodBinding.getDeclaringClass().getName().equals("MyClass"));
		assertTrue(methodBinding.isConstructor() == false);
		assertTrue(methodBinding.getReturnType()[0].getName().equals("MyClass"));

	}

	@Test
	public void methodDeclarationGeneratorBinding() throws Exception {
		String str = "<?php class MyClass { function foo(){ yield 1; } } ?>";
		Program program = createAndParse(str);

		ClassDeclaration classDeclaration = (ClassDeclaration) program.statements().get(0);
		MethodDeclaration methodDeclaration = (MethodDeclaration) classDeclaration.getBody().statements().get(0);

		IMethodBinding methodBinding = methodDeclaration.resolveMethodBinding();
		assertNotNull(methodBinding);
		assertTrue(methodBinding.getName().equals("foo"));
		assertNotNull(methodBinding.getDeclaringClass());
		assertTrue(methodBinding.getDeclaringClass().getName().equals("MyClass"));
		assertTrue(methodBinding.isConstructor() == false);
		assertTrue(methodBinding.getReturnType()[0].getName().equals("Generator"));

	}

	// public void testMethodParametersBinding() throws Exception {
	// String str =
	// "<?php class MyClass { function foo(MyClass $instance){} } ?>";
	// Program program = createAndParse(str);
	//
	// ClassDeclaration classDeclaration = (ClassDeclaration) program
	// .statements().get(0);
	// MethodDeclaration methodDeclaration = (MethodDeclaration)
	// classDeclaration
	// .getBody().statements().get(0);
	//
	// IMethodBinding methodBinding = methodDeclaration.resolveMethodBinding();
	//
	// ITypeBinding[] parameterTypes = methodBinding.getParameterTypes();
	// assertNotNull(parameterTypes);
	// assertTrue(parameterTypes.length == 1);
	// }

	@Test
	public void functionInvocationBinding() throws Exception {
		String str = "<?php function foo(){} foo(); ?>";
		Program program = createAndParse(str);

		ExpressionStatement statement = (ExpressionStatement) program.statements().get(1);
		FunctionInvocation functionInvocation = (FunctionInvocation) statement.getExpression();

		IFunctionBinding functionBinding = functionInvocation.resolveFunctionBinding();
		assertNotNull(functionBinding);
		assertTrue(functionBinding.getName().equals("foo"));
		assertTrue(functionBinding.isVarargs() == false);
	}

	// public void testFunctionInvocationParametersBinding() throws Exception {
	// String str = "<?php function foo(){} foo(); ?>";
	// Program program = createAndParse(str);
	//
	// ExpressionStatement statement = (ExpressionStatement) program
	// .statements().get(1);
	// FunctionInvocation functionInvocation = (FunctionInvocation) statement
	// .getExpression();
	//
	// IFunctionBinding functionBinding = functionInvocation
	// .resolveFunctionBinding();
	// ITypeBinding[] parameterTypes = functionBinding.getParameterTypes();
	// assertNotNull(parameterTypes);
	// assertTrue(parameterTypes.length == 0);
	// }

	@Test
	public void methodInvocationBinding() throws Exception {
		String str = "<?php class MyClass { function foo(){} } $a = new MyClass(); $a->foo(); ?>";
		Program program = createAndParse(str);

		ExpressionStatement statement = (ExpressionStatement) program.statements().get(2);
		MethodInvocation methodInvocation = (MethodInvocation) statement.getExpression();

		IMethodBinding methodBinding = methodInvocation.resolveMethodBinding();
		assertNotNull(methodBinding);
		assertTrue(methodBinding.getName().equals("foo"));

		ITypeBinding declaringClass = methodBinding.getDeclaringClass();
		assertNotNull(declaringClass);
		assertTrue(declaringClass.getName().equals("MyClass"));

		assertTrue(methodBinding.isConstructor() == false);
	}

	// public void testMethodInvocationParametersBinding() throws Exception {
	// String str =
	// "<?php class MyClass { function foo(MyClass $instance){} } $a = new
	// MyClass(); $a->foo(); ?>";
	// Program program = createAndParse(str);
	//
	// ExpressionStatement statement = (ExpressionStatement) program
	// .statements().get(2);
	// MethodInvocation methodInvocation = (MethodInvocation) statement
	// .getExpression();
	//
	// IMethodBinding methodBinding = methodInvocation.resolveMethodBinding();
	//
	// ITypeBinding[] parameterTypes = methodBinding.getParameterTypes();
	// assertNotNull(parameterTypes);
	// assertTrue(parameterTypes.length == 1);
	// }

	@Test
	public void staticMethodInvocationBinding() throws Exception {
		String str = "<?php class MyClass { static function foo(){} } MyClass::foo($a); ?>";
		Program program = createAndParse(str);

		ExpressionStatement statement = (ExpressionStatement) program.statements().get(1);
		StaticMethodInvocation staticMethodInvocation = (StaticMethodInvocation) statement.getExpression();

		IMethodBinding methodBinding = staticMethodInvocation.resolveMethodBinding();

		assertNotNull(methodBinding);
		assertTrue(methodBinding.isConstructor() == false);
		assertTrue(methodBinding.getName().equals("foo"));

		ITypeBinding declaringClass = methodBinding.getDeclaringClass();
		assertNotNull(declaringClass);
		assertTrue(declaringClass.getName().equals("MyClass"));
		assertTrue(methodBinding.getKind() == IBinding.METHOD);
	}

	// public void testIdentifierBinding() throws Exception {
	// String str = "<?php $a = 5; ?>";
	// Program program = createAndParse(str);
	//
	// ExpressionStatement statement = (ExpressionStatement) program
	// .statements().get(0);
	// Assignment assignment = (Assignment) statement.getExpression();
	// Variable variable = (Variable) assignment.getLeftHandSide();
	// Identifier identifier = (Identifier) variable.getName();
	//
	// IBinding binding = identifier.resolveBinding();
	//
	// assertNotNull(binding);
	//
	// String name = binding.getName();
	// assertNotNull(name);
	// assertTrue(name.equals("a"));
	// assertTrue(binding.getKind() == IBinding.VARIABLE);
	// }

	// public void testVariableIntBinding1() throws Exception {
	// String str = "<?php $a = 0; ?>";
	// Program program = createAndParse(str);
	//
	// ExpressionStatement statement = (ExpressionStatement) program
	// .statements().get(0);
	// Assignment assignment = (Assignment) statement.getExpression();
	// Variable var = (Variable) assignment.getLeftHandSide();
	// IVariableBinding binding = var.resolveVariableBinding();
	//
	// assertNotNull(binding);
	// assertTrue(binding.getDeclaringClass() == null);
	// assertTrue(binding.getName().equals("a"));
	// assertTrue(binding.getConstantValue() == null);
	// assertTrue(binding.getKey() != null);
	// assertTrue(binding.getDeclaringFunction() == null);
	// assertTrue(binding.getKind() == IBinding.VARIABLE);
	// assertTrue(binding.getPHPElement() instanceof FakeField);
	// assertTrue(binding.getVariableId() == 0);
	// assertTrue(binding.getType().getName().equals("integer")); // TODO
	// // ensure
	// // that
	// // the
	// // type
	// // is
	// // integer
	// }

	// public void testVariableIntBinding2() throws Exception {
	// String str = "<?php $a = 0; $b = 3?>";
	// Program program = createAndParse(str);
	//
	// ExpressionStatement statement = (ExpressionStatement) program
	// .statements().get(1);
	// Assignment assignment = (Assignment) statement.getExpression();
	// Variable var = (Variable) assignment.getLeftHandSide();
	// IVariableBinding binding = var.resolveVariableBinding();
	//
	// assertNotNull(binding);
	// assertTrue(binding.getName().equals("b"));
	// assertTrue(binding.getKind() == IBinding.VARIABLE);
	// assertTrue(binding.getType().getName().equals("integer")); // TODO
	// // ensure
	// // that
	// // the
	// // type
	// // is
	// // integer
	// assertTrue(binding.getVariableId() == 1);
	// }

	// public void testVariableStrBinding() throws Exception {
	// String str = "<?php $a = 'test'?>";
	// Program program = createAndParse(str);
	//
	// ExpressionStatement statement = (ExpressionStatement) program
	// .statements().get(0);
	// Assignment assignment = (Assignment) statement.getExpression();
	// Variable var = (Variable) assignment.getLeftHandSide();
	// IVariableBinding binding = var.resolveVariableBinding();
	//
	// assertNotNull(binding);
	// assertTrue(binding.getName().equals("test"));
	// assertTrue(binding.getKind() == IBinding.VARIABLE);
	// assertTrue(binding.getType().getName().equals("string")); // TODO
	// // ensure
	// // that
	// // the
	// // type
	// // is
	// // String
	// }

	@Test
	public void classDeclarationBinding() throws Exception {
		String str = "<?php class A {} ?>";
		Program program = createAndParse(str);

		ClassDeclaration classDeclaration = (ClassDeclaration) program.statements().get(0);
		ITypeBinding binding = classDeclaration.resolveTypeBinding();

		assertNotNull(binding);
		assertTrue(binding.getName().equals("A"));
		assertTrue(binding.getKind() == IBinding.TYPE);
		assertTrue(binding.isClass());
	}

	@Test
	public void interfaceDeclarationBinding() throws Exception {
		String str = "<?php interface A {} ?>";
		Program program = createAndParse(str);

		InterfaceDeclaration interfaceDeclaration = (InterfaceDeclaration) program.statements().get(0);
		ITypeBinding binding = interfaceDeclaration.resolveTypeBinding();

		assertNotNull(binding);
		assertTrue(binding.getName().equals("A"));
		assertTrue(binding.getKind() == IBinding.TYPE);
		assertTrue(binding.isInterface());
	}
}
