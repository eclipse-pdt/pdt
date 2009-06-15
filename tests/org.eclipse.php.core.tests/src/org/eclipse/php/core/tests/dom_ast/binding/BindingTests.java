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

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

import junit.framework.Assert;
import junit.framework.TestSuite;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.tests.model.SuiteOfTestCases;
import org.eclipse.php.core.tests.PHPCoreTests;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.ast.nodes.ASTParser;
import org.eclipse.php.internal.core.ast.nodes.Assignment;
import org.eclipse.php.internal.core.ast.nodes.ClassDeclaration;
import org.eclipse.php.internal.core.ast.nodes.ClassInstanceCreation;
import org.eclipse.php.internal.core.ast.nodes.Expression;
import org.eclipse.php.internal.core.ast.nodes.ExpressionStatement;
import org.eclipse.php.internal.core.ast.nodes.FieldAccess;
import org.eclipse.php.internal.core.ast.nodes.FunctionDeclaration;
import org.eclipse.php.internal.core.ast.nodes.FunctionInvocation;
import org.eclipse.php.internal.core.ast.nodes.IBinding;
import org.eclipse.php.internal.core.ast.nodes.IFunctionBinding;
import org.eclipse.php.internal.core.ast.nodes.IMethodBinding;
import org.eclipse.php.internal.core.ast.nodes.ITypeBinding;
import org.eclipse.php.internal.core.ast.nodes.IVariableBinding;
import org.eclipse.php.internal.core.ast.nodes.Identifier;
import org.eclipse.php.internal.core.ast.nodes.Include;
import org.eclipse.php.internal.core.ast.nodes.InfixExpression;
import org.eclipse.php.internal.core.ast.nodes.InterfaceDeclaration;
import org.eclipse.php.internal.core.ast.nodes.MethodDeclaration;
import org.eclipse.php.internal.core.ast.nodes.MethodInvocation;
import org.eclipse.php.internal.core.ast.nodes.Program;
import org.eclipse.php.internal.core.ast.nodes.StaticConstantAccess;
import org.eclipse.php.internal.core.ast.nodes.StaticFieldAccess;
import org.eclipse.php.internal.core.ast.nodes.StaticMethodInvocation;
import org.eclipse.php.internal.core.ast.nodes.Variable;
import org.eclipse.php.internal.core.project.PHPNature;
import org.eclipse.php.internal.core.project.ProjectOptions;
import org.eclipse.php.internal.core.typeinference.FakeField;

public class BindingTests extends SuiteOfTestCases {

	protected IProject project;
	private IFile testFile;
	private int counter;

	public BindingTests(String name) {
		super(name);
	}

	public static TestSuite suite() {
		return new Suite(BindingTests.class);
	}

	public void setUpSuite() throws Exception {
		project = ResourcesPlugin.getWorkspace().getRoot().getProject("BindingTests");
		if (project.exists()) {
			return;
		}

		project.create(null);
		project.open(null);

		// configure nature
		IProjectDescription desc = project.getDescription();
		desc.setNatureIds(new String[] { PHPNature.ID });
		project.setDescription(desc, null);
		
		super.setUpSuite();
	}

	public void tearDownSuite() throws Exception {
		project.delete(true, null);
		super.tearDownSuite();
	}

	protected void setUp() throws Exception {
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
		testFile = project.getFile("test" + (++counter) + ".php");
		testFile.create(new ByteArrayInputStream(code.getBytes()), true, null);
		project.refreshLocal(IResource.DEPTH_INFINITE, null);
		project.build(IncrementalProjectBuilder.FULL_BUILD, null);

		PHPCoreTests.waitForIndexer();
		PHPCoreTests.waitForAutoBuild();

		PHPVersion version = ProjectOptions.getDefaultPhpVersion();
		ISourceModule sourceModule = null;
		sourceModule = DLTKCore.createSourceModuleFrom(testFile);
		ASTParser parser = ASTParser.newParser(new InputStreamReader(testFile.getContents()), version, false, sourceModule);
		return parser.createAST(new NullProgressMonitor());
	}

	/**
	 * Get the ITypeBinding for the marked content in he given string.
	 * 
	 * @param content
	 * @param fromIndex - The index to start searching for the content marker from
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
	 * Get the ITypeBinding for the marked content in he given string.
	 * Same as calling getTypeBinding(content, 0).
	 * @param content
	 * @return
	 * @throws CoreException
	 * @throws Exception
	 */
	protected ITypeBinding getTypeBinding(String content) throws CoreException, Exception {
		return getTypeBinding(content, 0);
	}

	protected void tearDown() throws Exception {
		testFile.delete(true, new NullProgressMonitor());
	}

	public void testBasicExpression() throws Exception {
		// create a file and get the program's root
		String content = "<? class A { function foo() { $a = 4; $b =  /**/$a; } } ?>";
		ITypeBinding type = getTypeBinding(content);

		// check that it is a number
		Assert.assertEquals(type.getName(), "number");
	}

	public void testBasicType() throws Exception {
		// create a file and get the program's root
		String content = "<? class A {  } ; class B extends /**/A { } ?>";
		ITypeBinding type = getTypeBinding(content);

		// check that it is a number
		Assert.assertEquals(type.getName(), "A");
	}

	public void testIsArray() throws Exception {
		// create a file and get the program's root
		String content = "<? interface B {} class A implements B { function foo() { $a = array(1, 2, 3); $b = /**/$a; } } ?>";
		ITypeBinding type = getTypeBinding(content);
		Assert.assertTrue(type.isArray());
	}

	public void testIsClass() throws Exception {
		// create a file and get the program's root
		String content = "<? interface B {} class A implements B { function foo() { $a = new A(); $b = /**/$a; } } ?>";
		ITypeBinding type = getTypeBinding(content);
		Assert.assertTrue(type.isClass());
	}

	public void testIsInterface() throws Exception {
		// create a file and get the program's root
		String content = "<? interface B {} class A implements /**/B { function foo() { $a = new A(); $b = $a; } } ?>";
		ITypeBinding type = getTypeBinding(content);
		Assert.assertTrue(type.isInterface());
	}

	public void testIsNullType() throws Exception {
		// create a file and get the program's root
		String content = "<? interface B {} class A implements B { function foo() { $a = null; $b = /**/$a; } } ?>";
		ITypeBinding type = getTypeBinding(content);
		Assert.assertTrue(type.isNullType());
	}

	public void testIsPrimitive() throws Exception {
		// create a file and get the program's root
		String content = "<? interface B {} class A implements B { function foo() { $a = true; $b = /**/$a; } } ?>";
		ITypeBinding type = getTypeBinding(content);
		Assert.assertTrue(type.isPrimitive());
	}

	public void testIsSubTypeCompatibleTrue() throws Exception {
		// create a file and get the program's root
		String content = "<? interface B {} class A implements /**/B { function foo() { $a = true; $b = $a; } } class C extends A{} /**/$c = new C()?>";
		int indexOf = locateElement(content, 0);
		ITypeBinding type = getTypeBinding(content);
		ITypeBinding otherType = getTypeBinding(content, indexOf + 4);
		Assert.assertTrue("Should be sub-type compatible", otherType.isSubTypeCompatible(type));
	}

	public void testIsSubTypeCompatibleFalse() throws Exception {
		// create a file and get the program's root
		String content = "<? interface B {} class A implements /**/B { function foo() { $a = true; $b = $a; } } class C extends A{} /**/$c = new C()?>";
		int indexOf = locateElement(content, 0);
		ITypeBinding type = getTypeBinding(content);
		ITypeBinding otherType = getTypeBinding(content, indexOf + 4);
		Assert.assertFalse("Should NOT be sub-type compatible", type.isSubTypeCompatible(otherType));
	}

	public void testBinaryName() throws Exception {
		// create a file and get the program's root
		String content = "<? interface B {} class A implements B { function foo() { $a = new A(); $b = /**/$a; } } ?>";
		ITypeBinding type = getTypeBinding(content);
		String binaryName = type.getBinaryName();
		Assert.assertNotNull("Binary name was null", binaryName);
	}

	public void testArraysGetComponentType() throws Exception {
		// create a file and get the program's root
		String content = "<? interface B {} class A implements B { function foo() { $a = array(1, 2, 3); $b = /**/$a; } } ?>";
		ITypeBinding type = getTypeBinding(content);
		// TODO - Right now, this test will always fail until we implement this functionality.
		Assert.assertTrue("getComponentType() should return a non-null value for arrays", type.isArray() && type.getComponentType() != null);
	}

	public void testGetDeclaredFields() throws Exception {
		// create a file and get the program's root
		String content = "<? interface B {} class A implements B { public $value; private $pValue; function foo() { $a = new A(); $b = /**/$a; } } ?>";
		ITypeBinding type = getTypeBinding(content);
		IVariableBinding[] declaredFields = type.getDeclaredFields();
		declaredFields[0].getDeclaringClass();
		//		declaredFields[0].getDeclaringFunction();
		// TODO - complete the implementation of VariableBinding
	}

	public void testGetDeclaredMethods() throws Exception {
		// create a file and get the program's root
		String content = "<? interface B {} class A implements B { public $value; private $pValue; function foo() { $a = new A(); $b = /**/$a; } } ?>";
		ITypeBinding type = getTypeBinding(content);
		IMethodBinding[] declaredMethods = type.getDeclaredMethods();
		Assert.assertTrue("The declaring type name is wrong", declaredMethods[0].getDeclaringClass().getName().equals("A"));
		//		declaredFields[0].getDeclaringFunction();
		// TODO - complete the implementation of MethodBinding
	}

	public void testOverrides() throws Exception {
		// create a file and get the program's root
		String content = "<? interface B { function foo(); } class A implements /**/B { public $value; private $pValue; function foo() { $a = new A(); $b = /**/$a; } function boo() {} function foo($g) {} } ?>";
		int index = locateElement(content);
		ITypeBinding type = getTypeBinding(content);
		IMethodBinding[] interfaceBMethods = type.getDeclaredMethods();

		type = getTypeBinding(content, index + 4);
		IMethodBinding[] classAMethods = type.getDeclaredMethods();
		Assert.assertTrue("Should override", classAMethods[0].overrides(interfaceBMethods[0]));
		Assert.assertFalse("Should NOT override", classAMethods[1].overrides(interfaceBMethods[0]));
		Assert.assertTrue("Should override", classAMethods[2].overrides(interfaceBMethods[0]));
	}

	public void testConstantExpressionBinding() throws Exception {
		String str = "<?php class A{ const MY_CONSTANT = 3;} function foo() { echo /**/MY_CONSTANT; }  ?>";
		Program program = createAndParse(str);

		// locate the expression to test
		int indexOf = locateElement(str);
		Expression expr = (Expression) program.getElementAt(indexOf);
		Object resolveConstantExpressionValue = expr.resolveConstantExpressionValue();
		Assert.assertNotNull(resolveConstantExpressionValue);
	}

	public void testConstructorBinding() throws Exception {
		String str = "<?php $a = new MyClass(); class MyClass { } ?>";
		Program program = createAndParse(str);

		ExpressionStatement statement = (ExpressionStatement) program.statements().get(0);
		Assignment assignment = (Assignment) statement.getExpression();
		ClassInstanceCreation instanceCreation = (ClassInstanceCreation) assignment.getRightHandSide();

		IMethodBinding constructorBinding = instanceCreation.resolveConstructorBinding();

		Assert.assertNotNull(constructorBinding);
		Assert.assertTrue(constructorBinding.isConstructor() == true);
		Assert.assertTrue(constructorBinding.getName().equals("MyClass"));
		Assert.assertTrue(constructorBinding.getKind() == IBinding.METHOD);
	}

	public void testExpressionBinding() throws Exception {
		String str = "<?php $a = 5+5 ?>";
		Program program = createAndParse(str);

		ExpressionStatement statement = (ExpressionStatement) program.statements().get(0);
		Assignment assignment = (Assignment) statement.getExpression();
		InfixExpression infixExpression = (InfixExpression) assignment.getRightHandSide();

		ITypeBinding expressionBinding = infixExpression.resolveTypeBinding();

		Assert.assertTrue(expressionBinding.getKind() == IBinding.TYPE);
		// TODO
	}

	public void testFieldAccessBinding() throws Exception {
		String str = "<?php class MyClass { var $anotherOne; }; $a = new MyClass(); $b = $a->anotherOne ?>";
		Program program = createAndParse(str);

		ExpressionStatement statement = (ExpressionStatement) program.statements().get(3);
		Assignment assignment = (Assignment) statement.getExpression();
		FieldAccess fieldAccess = (FieldAccess) assignment.getRightHandSide();

		IVariableBinding variableBinding = fieldAccess.resolveFieldBinding();

		Assert.assertTrue(variableBinding.isField() == true);
		Assert.assertTrue(variableBinding.getName().equals("$anotherOne"));
		Assert.assertTrue(variableBinding.getKind() == IBinding.VARIABLE);
	}

	public void testStaticFieldAccessBinding() throws Exception {
		String str = "<?php class MyClass { public static $a = 4; } ; /**/MyClass::$a;?>";
		Program program = createAndParse(str);

		final ExpressionStatement statement = (ExpressionStatement) program.statements().get(2);
		final StaticFieldAccess staticFieldAcces = (StaticFieldAccess) statement.getExpression();
		IVariableBinding fieldBinding = staticFieldAcces.resolveFieldBinding();

		Assert.assertTrue(fieldBinding.isField() == true);
		Assert.assertTrue(fieldBinding.getName().equals("$a"));
		Assert.assertTrue(fieldBinding.getKind() == IBinding.VARIABLE);
	}

	public void testStaticConstantAccessBinding() throws Exception {
		String str = "<?php class MyClass { const A = 4; } ; /**/MyClass::A;?>";
		Program program = createAndParse(str);

		final ExpressionStatement statement = (ExpressionStatement) program.statements().get(2);
		final StaticConstantAccess constantAccess = (StaticConstantAccess) statement.getExpression();
		IVariableBinding fieldBinding = (IVariableBinding) constantAccess.resolveFieldBinding();

		Assert.assertNotNull(fieldBinding);
		Assert.assertTrue(fieldBinding.isField());
	}

	public void testIncludeBinding() throws Exception {
		String str = "<?php include('myFile.php');?>";
		Program program = createAndParse(str);

		IFile myFile = project.getFile("myFile.php");
		myFile.create(new ByteArrayInputStream(new byte[] {}), true, new NullProgressMonitor());
		try {

			ExpressionStatement statement = (ExpressionStatement) program.statements().get(0);
			Include include = (Include) statement.getExpression();

			IBinding sourceBinding = include.resolveBinding();

			Assert.assertTrue(sourceBinding.getName().equals("myFile.php") == true);
			Assert.assertTrue(sourceBinding.getPHPElement().getElementType() == IModelElement.SOURCE_MODULE);
			Assert.assertTrue(sourceBinding.getKind() == IBinding.INCLUDE);

		} finally {
			myFile.delete(true, new NullProgressMonitor());
		}
	}

	public void testFunctionDeclarationBinding() throws Exception {
		String str = "<?php function foo() {} ?> ";
		Program program = createAndParse(str);

		FunctionDeclaration functionDeclaration = (FunctionDeclaration) program.statements().get(0);

		IFunctionBinding functionBinding = functionDeclaration.resolveFunctionBinding();

		Assert.assertNotNull(functionBinding);
		Assert.assertTrue(functionBinding.getName().equals("foo"));
		//Assert.assertTrue(functionBinding.getReturnType());
	}

	public void testMethodDeclarationBinding() throws Exception {
		String str = "<?php class MyClass { function foo(){} } ?>";
		Program program = createAndParse(str);

		ClassDeclaration classDeclaration = (ClassDeclaration) program.statements().get(0);
		MethodDeclaration methodDeclaration = (MethodDeclaration) classDeclaration.getBody().statements().get(0);

		IMethodBinding methodBinding = methodDeclaration.resolveMethodBinding();
		Assert.assertNotNull(methodBinding);
		Assert.assertTrue(methodBinding.getName().equals("foo"));
		Assert.assertNotNull(methodBinding.getDeclaringClass());
		Assert.assertTrue(methodBinding.getDeclaringClass().getName().equals("MyClass"));
		Assert.assertTrue(methodBinding.isConstructor() == false);

		ITypeBinding[] parameterTypes = methodBinding.getParameterTypes();
		Assert.assertNotNull(parameterTypes);
		Assert.assertTrue(parameterTypes.length == 0);
	}

	public void testFunctionInvocationBinding() throws Exception {
		String str = "<?php function foo(){} foo(); ?>";
		Program program = createAndParse(str);

		ExpressionStatement statement = (ExpressionStatement) program.statements().get(1);
		FunctionInvocation functionInvocation = (FunctionInvocation) statement.getExpression();

		IFunctionBinding functionBinding = functionInvocation.resolveFunctionBinding();
		Assert.assertNotNull(functionBinding);
		Assert.assertTrue(functionBinding.getName().equals("foo"));
		Assert.assertTrue(functionBinding.isVarargs() == false);
		Assert.assertTrue(functionBinding.getParameterTypes().length == 0);
	}

	public void testMethodInvocationBinding() throws Exception {
		String str = "<?php class MyClass { function foo(){} } $a = new MyClass(); $a->foo(); ?>";
		Program program = createAndParse(str);

		ExpressionStatement statement = (ExpressionStatement) program.statements().get(2);
		MethodInvocation methodInvocation = (MethodInvocation) statement.getExpression();

		IMethodBinding methodBinding = methodInvocation.resolveMethodBinding();
		Assert.assertNotNull(methodBinding);
		Assert.assertTrue(methodBinding.getName().equals("foo"));

		ITypeBinding declaringClass = methodBinding.getDeclaringClass();
		Assert.assertNotNull(declaringClass);
		Assert.assertTrue(declaringClass.getName().equals("MyClass"));

		Assert.assertTrue(methodBinding.isConstructor() == false);

		ITypeBinding[] parameterTypes = methodBinding.getParameterTypes();
		Assert.assertNotNull(parameterTypes);
		Assert.assertTrue(parameterTypes.length == 0);
	}

	public void testStaticMethodInvocationBinding() throws Exception {
		String str = "<?php MyClass::foo($a); ?>";
		Program program = createAndParse(str);

		ExpressionStatement statement = (ExpressionStatement) program.statements().get(0);
		StaticMethodInvocation staticMethodInvocation = (StaticMethodInvocation) statement.getExpression();

		IMethodBinding methodBinding = staticMethodInvocation.resolveMethodBinding();

		Assert.assertNotNull(methodBinding);
		Assert.assertTrue(methodBinding.isConstructor() == false);
		Assert.assertTrue(methodBinding.getName().equals("foo"));

		ITypeBinding declaringClass = methodBinding.getDeclaringClass();
		Assert.assertNotNull(declaringClass);
		Assert.assertTrue(declaringClass.getName().equals("MyClass"));
		Assert.assertTrue(methodBinding.getKind() == IBinding.METHOD);

	}

	public void testIdentifierBinding() throws Exception {
		String str = "<?php $a = 5; ?>";
		Program program = createAndParse(str);

		ExpressionStatement statement = (ExpressionStatement) program.statements().get(0);
		Assignment assignment = (Assignment) statement.getExpression();
		Variable variable = (Variable) assignment.getLeftHandSide();
		Identifier identifier = (Identifier) variable.getName();

		IBinding binding = identifier.resolveBinding();

		Assert.assertNotNull(binding);
		
		String name = binding.getName();
		Assert.assertNotNull(name);
		Assert.assertTrue(name.equals("a"));
		Assert.assertTrue(binding.getKind() == IBinding.VARIABLE);
	}

	public void testVariableIntBinding1() throws Exception {
		String str = "<?php $a = 0; ?>";
		Program program = createAndParse(str);

		ExpressionStatement statement = (ExpressionStatement) program.statements().get(0);
		Assignment assignment = (Assignment) statement.getExpression();
		Variable var = (Variable) assignment.getLeftHandSide();
		IVariableBinding binding = var.resolveVariableBinding();

		Assert.assertNotNull(binding);
		Assert.assertTrue(binding.getDeclaringClass() == null);
		Assert.assertTrue(binding.getName().equals("a"));
		Assert.assertTrue(binding.getConstantValue() == null);
		Assert.assertTrue(binding.getKey() != null);
		Assert.assertTrue(binding.getDeclaringFunction() == null);
		Assert.assertTrue(binding.getKind() == IBinding.VARIABLE);
		Assert.assertTrue(binding.getPHPElement() instanceof FakeField);
		Assert.assertTrue(binding.getVariableId() == 0);
		Assert.assertTrue(binding.getType().getName().equals("integer")); // TODO ensure that the type is integer
	}

	public void testVariableIntBinding2() throws Exception {
		String str = "<?php $a = 0; $b = 3?>";
		Program program = createAndParse(str);

		ExpressionStatement statement = (ExpressionStatement) program.statements().get(1);
		Assignment assignment = (Assignment) statement.getExpression();
		Variable var = (Variable) assignment.getLeftHandSide();
		IVariableBinding binding = var.resolveVariableBinding();

		Assert.assertNotNull(binding);
		Assert.assertTrue(binding.getName().equals("b"));
		Assert.assertTrue(binding.getKind() == IBinding.VARIABLE);
		Assert.assertTrue(binding.getType().getName().equals("integer")); // TODO ensure that the type is integer
		Assert.assertTrue(binding.getVariableId() == 1);
	}

	public void testVariableStrBinding() throws Exception {
		String str = "<?php $a = 'test'?>";
		Program program = createAndParse(str);

		ExpressionStatement statement = (ExpressionStatement) program.statements().get(0);
		Assignment assignment = (Assignment) statement.getExpression();
		Variable var = (Variable) assignment.getLeftHandSide();
		IVariableBinding binding = var.resolveVariableBinding();

		Assert.assertNotNull(binding);
		Assert.assertTrue(binding.getName().equals("test"));
		Assert.assertTrue(binding.getKind() == IBinding.VARIABLE);
		Assert.assertTrue(binding.getType().getName().equals("string")); // TODO ensure that the type is String
	}

	public void testClassDeclarationBinding() throws Exception {
		String str = "<?php class A {} ?>";
		Program program = createAndParse(str);

		ClassDeclaration classDeclaration = (ClassDeclaration) program.statements().get(0);
		ITypeBinding binding = classDeclaration.resolveTypeBinding();

		Assert.assertNotNull(binding);
		Assert.assertTrue(binding.getName().equals("A"));
		Assert.assertTrue(binding.getKind() == IBinding.TYPE);
		Assert.assertTrue(binding.isClass());
	}

	public void testInterfaceDeclarationBinding() throws Exception {
		String str = "<?php interface A {} ?>";
		Program program = createAndParse(str);

		InterfaceDeclaration interfaceDeclaration = (InterfaceDeclaration) program.statements().get(0);
		ITypeBinding binding = interfaceDeclaration.resolveTypeBinding();

		Assert.assertNotNull(binding);
		Assert.assertTrue(binding.getName().equals("A"));
		Assert.assertTrue(binding.getKind() == IBinding.TYPE);
		Assert.assertTrue(binding.isInterface());
	}
}
