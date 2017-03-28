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
package org.eclipse.php.core.tests.dom_ast.rewrite;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.php.core.tests.TestUtils;
import org.eclipse.php.core.tests.TestSuiteWatcher;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.ast.nodes.AST;
import org.eclipse.php.core.ast.nodes.ASTNode;
import org.eclipse.php.core.ast.nodes.ASTParser;
import org.eclipse.php.core.ast.nodes.ArrayAccess;
import org.eclipse.php.core.ast.nodes.ArrayCreation;
import org.eclipse.php.core.ast.nodes.Assignment;
import org.eclipse.php.core.ast.nodes.Block;
import org.eclipse.php.core.ast.nodes.BreakStatement;
import org.eclipse.php.core.ast.nodes.CastExpression;
import org.eclipse.php.core.ast.nodes.ClassDeclaration;
import org.eclipse.php.core.ast.nodes.ClassInstanceCreation;
import org.eclipse.php.core.ast.nodes.CloneExpression;
import org.eclipse.php.core.ast.nodes.ConditionalExpression;
import org.eclipse.php.core.ast.nodes.ContinueStatement;
import org.eclipse.php.core.ast.nodes.DoStatement;
import org.eclipse.php.core.ast.nodes.EchoStatement;
import org.eclipse.php.core.ast.nodes.Expression;
import org.eclipse.php.core.ast.nodes.FieldAccess;
import org.eclipse.php.core.ast.nodes.FieldsDeclaration;
import org.eclipse.php.core.ast.nodes.ForEachStatement;
import org.eclipse.php.core.ast.nodes.ForStatement;
import org.eclipse.php.core.ast.nodes.FormalParameter;
import org.eclipse.php.core.ast.nodes.FunctionDeclaration;
import org.eclipse.php.core.ast.nodes.FunctionInvocation;
import org.eclipse.php.core.ast.nodes.FunctionName;
import org.eclipse.php.core.ast.nodes.GlobalStatement;
import org.eclipse.php.core.ast.nodes.Identifier;
import org.eclipse.php.core.ast.nodes.IfStatement;
import org.eclipse.php.core.ast.nodes.IgnoreError;
import org.eclipse.php.core.ast.nodes.InLineHtml;
import org.eclipse.php.core.ast.nodes.Include;
import org.eclipse.php.core.ast.nodes.InfixExpression;
import org.eclipse.php.core.ast.nodes.InstanceOfExpression;
import org.eclipse.php.core.ast.nodes.InterfaceDeclaration;
import org.eclipse.php.core.ast.nodes.ListVariable;
import org.eclipse.php.core.ast.nodes.MethodDeclaration;
import org.eclipse.php.core.ast.nodes.MethodInvocation;
import org.eclipse.php.core.ast.nodes.PostfixExpression;
import org.eclipse.php.core.ast.nodes.PrefixExpression;
import org.eclipse.php.core.ast.nodes.Program;
import org.eclipse.php.core.ast.nodes.Quote;
import org.eclipse.php.core.ast.nodes.Reference;
import org.eclipse.php.core.ast.nodes.ReflectionVariable;
import org.eclipse.php.core.ast.nodes.ReturnStatement;
import org.eclipse.php.core.ast.nodes.Scalar;
import org.eclipse.php.core.ast.nodes.Statement;
import org.eclipse.php.core.ast.nodes.StaticConstantAccess;
import org.eclipse.php.core.ast.nodes.StaticFieldAccess;
import org.eclipse.php.core.ast.nodes.StaticMethodInvocation;
import org.eclipse.php.core.ast.nodes.StaticStatement;
import org.eclipse.php.core.ast.nodes.SwitchCase;
import org.eclipse.php.core.ast.nodes.SwitchStatement;
import org.eclipse.php.core.ast.nodes.ThrowStatement;
import org.eclipse.php.core.ast.nodes.TryStatement;
import org.eclipse.php.core.ast.nodes.UnaryOperation;
import org.eclipse.php.core.ast.nodes.Variable;
import org.eclipse.php.core.ast.nodes.VariableBase;
import org.eclipse.php.core.ast.nodes.WhileStatement;
import org.eclipse.php.core.ast.visitor.ApplyAll;
import org.eclipse.php.internal.core.ast.rewrite.ASTRewrite;
import org.eclipse.php.internal.core.ast.rewrite.ListRewrite;
import org.eclipse.php.core.project.ProjectOptions;
import org.eclipse.text.edits.TextEdit;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

/**
 * AST rewrite test which s the ASTRewriteAnalyzer implementation.
 * 
 * @author shalom
 */
public class ASTRewriteTests {

	@ClassRule
	public static TestWatcher watcher = new TestSuiteWatcher();

	@RunWith(org.junit.runners.Suite.class)
	@SuiteClasses({ ASTRewriteTests.class, ASTRewriteTestsPHP54.Suite.class, ASTRewriteTestsPHP55.Suite.class,
			ASTRewriteTestsPHP56.Suite.class, ASTRewriteTestsPHP7.Suite.class })
	public static class Suite {

	}

	protected AST ast;
	protected IDocument document;
	protected Program program;

	// //////////////////////// s //////////////////////////
	@Test
	public void variable1() throws Exception {
		String str = "<?php $a; $A;?>";
		initialize(str);

		List<Variable> variables = getAllOfType(program, Variable.class);
		assertTrue("Unexpected list size.", variables.size() == 2);
		variables.get(1).setName(ast.newIdentifier("B1"));
		rewrite();
		checkResult("<?php $a; $B1;?>");
	}

	@Test
	public void variable2() throws Exception {
		String str = "<?php $AAA;?>";
		initialize(str);

		List<Variable> variables = getAllOfType(program, Variable.class);
		assertTrue("Unexpected list size.", variables.size() == 1);
		((Identifier) variables.get(0).getName()).setName("B");
		rewrite();
		checkResult("<?php $B;?>");
	}

	@Test
	public void functionName() throws Exception {
		String str = "<?php foo(); ?>";
		initialize(str);

		List<FunctionInvocation> invocations = getAllOfType(program, FunctionInvocation.class);
		assertTrue("Unexpected list size.", invocations.size() == 1);
		FunctionName functionName = invocations.get(0).getFunctionName();
		functionName.setName(ast.newIdentifier("boobo"));
		rewrite();
		checkResult("<?php boobo(); ?>");
	}

	@Test
	public void functionInvocationName() throws Exception {
		String str = "<?php foo(); ?>";
		initialize(str);

		List<FunctionInvocation> invocations = getAllOfType(program, FunctionInvocation.class);
		assertTrue("Unexpected list size.", invocations.size() == 1);
		FunctionInvocation functionInvocation = invocations.get(0);
		functionInvocation.setFunctionName(ast.newFunctionName(ast.newIdentifier("boobo")));
		rewrite();
		checkResult("<?php boobo(); ?>");
	}

	@Test
	public void functionInvocationAddParam() throws Exception {
		String str = "<?php foo( ); ?>";
		initialize(str);

		List<FunctionInvocation> invocations = getAllOfType(program, FunctionInvocation.class);
		assertTrue("Unexpected list size.", invocations.size() == 1);
		FunctionInvocation functionInvocation = invocations.get(0);
		functionInvocation.parameters().add(ast.newVariable("aaa"));
		rewrite();
		checkResult("<?php foo($aaa ); ?>");
	}

	@Test
	public void functionInvocationAddParams() throws Exception {
		String str = "<?php foo(); ?>";
		initialize(str);

		List<FunctionInvocation> invocations = getAllOfType(program, FunctionInvocation.class);
		assertTrue("Unexpected list size.", invocations.size() == 1);
		FunctionInvocation functionInvocation = invocations.get(0);
		functionInvocation.parameters().add(ast.newVariable("aaa"));
		functionInvocation.parameters().add(ast.newVariable("bbb"));
		functionInvocation.parameters().add(ast.newVariable("ccc"));
		rewrite();
		checkResult("<?php foo($aaa, $bbb, $ccc); ?>");
	}

	@Test
	public void functionInvocationRemoveParam1() throws Exception {
		String str = "<?php foo($aaa, $bbb); ?>";
		initialize(str);

		List<FunctionInvocation> invocations = getAllOfType(program, FunctionInvocation.class);
		assertTrue("Unexpected list size.", invocations.size() == 1);
		FunctionInvocation functionInvocation = invocations.get(0);
		functionInvocation.parameters().remove(1);
		rewrite();
		checkResult("<?php foo($aaa); ?>");
	}

	@Test
	public void functionInvocationRemoveParam2() throws Exception {
		String str = "<?php foo($aaa, $bbb); ?>";
		initialize(str);

		List<FunctionInvocation> invocations = getAllOfType(program, FunctionInvocation.class);
		assertTrue("Unexpected list size.", invocations.size() == 1);
		FunctionInvocation functionInvocation = invocations.get(0);
		functionInvocation.parameters().remove(0);
		rewrite();
		checkResult("<?php foo($bbb); ?>");
	}

	@Test
	public void functionInvocationRemoveParams() throws Exception {
		String str = "<?php foo($aaa, $bbb); ?>";
		initialize(str);

		List<FunctionInvocation> invocations = getAllOfType(program, FunctionInvocation.class);
		assertTrue("Unexpected list size.", invocations.size() == 1);
		FunctionInvocation functionInvocation = invocations.get(0);
		functionInvocation.parameters().clear();
		rewrite();
		checkResult("<?php foo(); ?>");
	}

	@Test
	public void variable3() throws Exception {
		String str = "<?php $myClass->bar();?>";
		initialize(str);

		List<Variable> variables = getAllOfType(program, Variable.class);
		assertTrue("Unexpected list size.", variables.size() == 2);
		variables.get(1).setName(ast.newIdentifier("foo"));
		variables.get(1).setIsDollared(true);
		variables.get(0).setIsDollared(false);
		rewrite();
		checkResult("<?php myClass->$foo();?>");
	}

	@Test
	public void staticFunctionInvocation() throws Exception {
		String str = "<?php A::foo($a); ?>";
		initialize(str);

		List<StaticMethodInvocation> staticInvocations = getAllOfType(program, StaticMethodInvocation.class);
		assertTrue("Unexpected list size.", staticInvocations.size() == 1);
		staticInvocations.get(0).setClassName(ast.newIdentifier("B"));
		ArrayList<Expression> parameters = new ArrayList<Expression>();
		parameters.add(ast.newScalar("b", Scalar.TYPE_STRING));
		parameters.add(ast.newVariable("c"));
		staticInvocations.get(0)
				.setMethod(ast.newFunctionInvocation(ast.newFunctionName(ast.newIdentifier("bar")), parameters));
		rewrite();
		checkResult("<?php B::bar(b,$c); ?>");
	}

	@Test
	public void arrayAccessWithoutIndex() throws Exception {
		String str = "<?php $a[]; ?>";
		initialize(str);

		List<ArrayAccess> arrayAccess = getAllOfType(program, ArrayAccess.class);
		assertTrue("Unexpected list size.", arrayAccess.size() == 1);
		arrayAccess.get(0).setName(ast.newVariable("b"));
		// FIXME - We need to fix the ArrayAccess implementation to reflect any
		// dollared property change
		// on the inner Variable. Also, this might need a fix to the initial
		// creation of the ArrayAccess by the parser.
		// arrayAccess.get(0).setIsDollared(false) should also work after that
		// fix.
		rewrite();
		checkResult("<?php $b[]; ?>");
	}

	@Test
	public void arrayAccess() throws Exception {
		String str = "<?php $a[$b]; ?>";
		initialize(str);

		List<ArrayAccess> arrayAccess = getAllOfType(program, ArrayAccess.class);
		assertTrue("Unexpected list size.", arrayAccess.size() == 1);
		arrayAccess.get(0).setIndex(ast.newScalar("1"));
		rewrite();
		checkResult("<?php $a[1]; ?>");
	}

	@Test
	public void arrayVariableMultiIndex() throws Exception {
		String str = "<?php $a[$b][5][3]; ?>";
		initialize(str);

		List<ArrayAccess> arrayAccess = getAllOfType(program, ArrayAccess.class);
		assertTrue("Unexpected list size.", arrayAccess.size() == 3);
		// TODO - The access to this ArrayAccess is reversed - Check if this is
		// the intended behavior.
		// TODO - Setting the name on an ArrayAccess that is not in the highest
		// index (the first) cuts the array access deep.
		arrayAccess.get(0).setIndex(ast.newVariable("foo"));
		arrayAccess.get(2).setName(ast.newVariable("boo"));
		rewrite();
		checkResult("<?php $boo[$b][5][$foo]; ?>");
	}

	@Test
	public void arrayAccessType1() throws Exception {
		String str = "<?php $a[$b]; ?>";
		initialize(str);

		List<ArrayAccess> arrayAccess = getAllOfType(program, ArrayAccess.class);
		assertTrue("Unexpected list size.", arrayAccess.size() == 1);
		arrayAccess.get(0).setArrayType(ArrayAccess.VARIABLE_HASHTABLE);
		rewrite();
		checkResult("<?php $a{$b}; ?>");
	}

	@Test
	public void arrayAccessType2() throws Exception {
		String str = "<?php $a{'name'}; ?>";
		initialize(str);

		List<ArrayAccess> arrayAccess = getAllOfType(program, ArrayAccess.class);
		assertTrue("Unexpected list size.", arrayAccess.size() == 1);
		arrayAccess.get(0).setArrayType(ArrayAccess.VARIABLE_ARRAY);
		arrayAccess.get(0).setIndex(ast.newScalar("333"));
		rewrite();
		checkResult("<?php $a[333]; ?>");
	}

	@Test
	public void listVariable1() throws Exception {
		String str = "<?php list($a,$b)=1; ?>";
		initialize(str);

		List<ListVariable> listVariables = getAllOfType(program, ListVariable.class);
		assertTrue("Unexpected list size.", listVariables.size() == 1);
		listVariables.get(0).variables().add(ast.newVariable("c"));
		listVariables.get(0).variables().remove(0);
		rewrite();
		checkResult("<?php list($b,$c)=1; ?>");
	}

	@Test
	public void listVariable2() throws Exception {
		String str = "<?php list($a,$b)=1; ?>";
		initialize(str);

		List<ListVariable> listVariables = getAllOfType(program, ListVariable.class);
		assertTrue("Unexpected list size.", listVariables.size() == 1);
		listVariables.get(0).variables().remove(1);
		rewrite();
		checkResult("<?php list($a)=1; ?>");
	}

	@Test
	public void nestedListVariable() throws Exception {
		String str = "<?php list($a, list($b,$c))=1;?>";
		initialize(str);

		List<ListVariable> listVariables = getAllOfType(program, ListVariable.class);
		assertTrue("Unexpected list size.", listVariables.size() == 2);
		ArrayList<VariableBase> variables = new ArrayList<VariableBase>();
		variables.add(ast.newVariable("foo"));
		variables.add(ast.newVariable("bar"));
		listVariables.get(1).variables().add(ast.newListVariable(variables));
		rewrite();
		checkResult("<?php list($a, list($b,$c,list($foo, $bar)))=1;?>");
	}

	@Test
	public void assignmentLeft() throws Exception {
		String str = "<?php $a = 1;?>";
		initialize(str);

		List<Assignment> assignments = getAllOfType(program, Assignment.class);
		assertTrue("Unexpected list size.", assignments.size() == 1);
		assignments.get(0).setLeftHandSide(ast.newVariable("foo"));
		rewrite();
		checkResult("<?php $foo = 1;?>");
	}

	@Test
	public void assignmentRight() throws Exception {
		String str = "<?php $a = 1;?>";
		initialize(str);

		List<Assignment> assignments = getAllOfType(program, Assignment.class);
		assertTrue("Unexpected list size.", assignments.size() == 1);
		assignments.get(0).setRightHandSide(ast.newScalar("12345"));
		rewrite();
		checkResult("<?php $a = 12345;?>");
	}

	@Test
	public void assignmentOperator() throws Exception {
		String str = "<?php $a = 1;?>";
		initialize(str);

		List<Assignment> assignments = getAllOfType(program, Assignment.class);
		assertTrue("Unexpected list size.", assignments.size() == 1);
		assignments.get(0).setOperator(Assignment.OP_MUL_EQUAL);
		rewrite();
		checkResult("<?php $a *= 1;?>");
	}

	@Test
	public void reflectionSimple() throws Exception {
		String str = "<?php $$a;?>";
		initialize(str);

		List<ReflectionVariable> reflectionVariables = getAllOfType(program, ReflectionVariable.class);
		assertTrue("Unexpected list size.", reflectionVariables.size() == 1);
		reflectionVariables.get(0).setName(ast.newVariable("b"));
		// FIXME ???? - We need to fix the ReflectionVariable implementation to
		// reflect any dollared property change.
		// (similar to the ArrayAccess fix).
		rewrite();
		checkResult("<?php $$b;?>");
	}

	// TODO - Add a test for nested ReflectionVariable once the upper bug is
	// fixed.

	@Test
	public void reflectionFunction() throws Exception {
		String str = "<?php $$$bar(); ?>";
		initialize(str);

		List<FunctionInvocation> functionInvocations = getAllOfType(program, FunctionInvocation.class);
		assertTrue("Unexpected list size.", functionInvocations.size() == 1);
		functionInvocations.get(0).setFunctionName(
				ast.newFunctionName(ast.newReflectionVariable(ast.newReflectionVariable(ast.newVariable("foo")))));
		rewrite();
		checkResult("<?php $$$foo(); ?>");
	}

	@Test
	public void reflectionComplex() throws Exception {
		String str = "<?php ${\"var\"};?>";
		initialize(str);

		List<ReflectionVariable> reflectionVariables = getAllOfType(program, ReflectionVariable.class);
		assertTrue("Unexpected list size.", reflectionVariables.size() == 1);
		reflectionVariables.get(0).setName(ast.newScalar("\"boobo\"", Scalar.TYPE_STRING));
		rewrite();
		checkResult("<?php ${\"boobo\"};?>");
	}

	@Test
	public void staticMemberSimple() throws Exception {
		String str = "<?php MyClass::$a;?>";
		initialize(str);

		List<StaticFieldAccess> fieldsAccess = getAllOfType(program, StaticFieldAccess.class);
		assertTrue("Unexpected list size.", fieldsAccess.size() == 1);
		fieldsAccess.get(0).setField(ast.newVariable("boobo"));
		fieldsAccess.get(0).setClassName(ast.newIdentifier("Foo"));
		rewrite();
		checkResult("<?php Foo::$boobo;?>");
	}

	@Test
	public void staticMemberWithArray() throws Exception {
		String str = "<?php MyClass::$$a[5];?>";
		initialize(str);

		List<StaticFieldAccess> fieldsAccess = getAllOfType(program, StaticFieldAccess.class);
		assertTrue("Unexpected list size.", fieldsAccess.size() == 1);
		fieldsAccess.get(0)
				.setField(ast.newReflectionVariable(ast.newArrayAccess(ast.newVariable("bar"), ast.newScalar("333"))));
		rewrite();
		checkResult("<?php MyClass::$$bar[333];?>");
	}

	@Test
	public void dispatchSimple() throws Exception {
		String str = "<?php $a->$b;?>";
		initialize(str);

		List<FieldAccess> fieldsAccess = getAllOfType(program, FieldAccess.class);
		assertTrue("Unexpected list size.", fieldsAccess.size() == 1);
		fieldsAccess.get(0).setDispatcher(ast.newVariable("boo"));
		fieldsAccess.get(0).setField(ast.newVariable("foo"));
		rewrite();
		checkResult("<?php $boo->$foo;?>");
	}

	@Test
	public void dispatchNested() throws Exception {
		String str = "<?php $myClass->foo()->bar(); ?>";
		initialize(str);

		List<MethodInvocation> methodInvocations = getAllOfType(program, MethodInvocation.class);
		assertTrue("Unexpected list size.", methodInvocations.size() == 2);
		methodInvocations.get(1).getMethod().setFunctionName(ast.newFunctionName(ast.newScalar("boobo")));
		rewrite();
		checkResult("<?php $myClass->boobo()->bar(); ?>");
	}

	@Test
	public void dispatchWithStaticCall() throws Exception {
		String str = "<?php MyClass::$a->foo(); ?>";
		initialize(str);

		List<FunctionInvocation> functionInvocations = getAllOfType(program, FunctionInvocation.class);
		assertTrue("Unexpected list size.", functionInvocations.size() == 1);
		functionInvocations.get(0).getFunctionName().setName(ast.newScalar("bar"));
		List<MethodInvocation> methodInvocations = getAllOfType(program, MethodInvocation.class);
		assertTrue("Unexpected list size.", methodInvocations.size() == 1);
		((StaticFieldAccess) methodInvocations.get(0).getDispatcher()).getField().setName(ast.newScalar("boobo"));
		rewrite();
		checkResult("<?php MyClass::$boobo->bar(); ?>");
	}

	@Test
	public void testClone() throws Exception {
		String str = "<?php clone $a; ?>";
		initialize(str);

		List<CloneExpression> cloneExpressions = getAllOfType(program, CloneExpression.class);
		assertTrue("Unexpected list size.", cloneExpressions.size() == 1);
		cloneExpressions.get(0).setExpression(ast.newVariable("bbb"));
		rewrite();
		checkResult("<?php clone $bbb; ?>");
	}

	@Test
	public void castOfVariable() throws Exception {
		String str = "<?php (int) $a; ?>";
		initialize(str);

		List<CastExpression> castExpressions = getAllOfType(program, CastExpression.class);
		assertTrue("Unexpected list size.", castExpressions.size() == 1);
		castExpressions.get(0).setExpression(ast.newVariable("b"));
		castExpressions.get(0).setCastingType(CastExpression.TYPE_STRING);
		rewrite();
		checkResult("<?php (string) $b; ?>");
	}

	@Test
	public void castOfDispatch() throws Exception {
		String str = "<?php (string) $b->foo(); ?>";
		initialize(str);

		List<CastExpression> castExpressions = getAllOfType(program, CastExpression.class);
		assertTrue("Unexpected list size.", castExpressions.size() == 1);
		castExpressions.get(0).setCastingType(CastExpression.TYPE_INT);
		rewrite();
		checkResult("<?php (int) $b->foo(); ?>");
	}

	@Test
	public void classConstant() throws Exception {
		String str = "<?php $a = MyClass::MY_CONST; ?>";
		initialize(str);

		List<StaticConstantAccess> staticConstants = getAllOfType(program, StaticConstantAccess.class);
		assertTrue("Unexpected list size.", staticConstants.size() == 1);
		((Identifier) staticConstants.get(0).getClassName()).setName("Foo");
		staticConstants.get(0).setConstant(ast.newIdentifier("BAR_CONST"));
		rewrite();
		checkResult("<?php $a = Foo::BAR_CONST; ?>");
	}

	@Test
	public void postfixSimple() throws Exception {
		String str = "<?php $a++;?>";
		initialize(str);

		List<PostfixExpression> postfixExp = getAllOfType(program, PostfixExpression.class);
		assertTrue("Unexpected list size.", postfixExp.size() == 1);
		postfixExp.get(0).setOperator(PostfixExpression.OP_DEC);
		postfixExp.get(0).setVariable(ast.newVariable("b"));
		rewrite();
		checkResult("<?php $b--;?>");
	}

	@Test
	public void postfixWithFunction() throws Exception {
		String str = "<?php foo()--;?>";
		initialize(str);

		List<PostfixExpression> postfixExp = getAllOfType(program, PostfixExpression.class);
		assertTrue("Unexpected list size.", postfixExp.size() == 1);
		postfixExp.get(0).setOperator(PostfixExpression.OP_INC);
		((FunctionInvocation) postfixExp.get(0).getVariable())
				.setFunctionName(ast.newFunctionName(ast.newScalar("bar")));
		rewrite();
		checkResult("<?php bar()++;?>");
	}

	@Test
	public void prefixSimple() throws Exception {
		String str = "<?php ++$a;?>";
		initialize(str);

		List<PrefixExpression> prefixExp = getAllOfType(program, PrefixExpression.class);
		assertTrue("Unexpected list size.", prefixExp.size() == 1);
		prefixExp.get(0).setOperator(PrefixExpression.OP_DEC);
		rewrite();
		checkResult("<?php --$a;?>");
	}

	@Test
	public void unaryOperationSimple() throws Exception {
		String str = "<?php +$a;?>";
		initialize(str);

		List<UnaryOperation> ops = getAllOfType(program, UnaryOperation.class);
		assertTrue("Unexpected list size.", ops.size() == 1);
		ops.get(0).setOperator(UnaryOperation.OP_TILDA);
		ops.get(0).setExpression(ast.newVariable("b"));
		rewrite();
		checkResult("<?php ~$b;?>");
	}

	@Test
	public void unaryOperationWithFunction() throws Exception {
		String str = "<?php -foo(); ?>";
		initialize(str);

		List<UnaryOperation> ops = getAllOfType(program, UnaryOperation.class);
		assertTrue("Unexpected list size.", ops.size() == 1);
		ops.get(0).setOperator(UnaryOperation.OP_NOT);
		rewrite();
		checkResult("<?php !foo(); ?>");
	}

	@Test
	public void unaryOperationComplex() throws Exception {
		String str = "<?php +-+-$b;?>";
		initialize(str);

		List<UnaryOperation> ops = getAllOfType(program, UnaryOperation.class);
		assertTrue("Unexpected list size.", ops.size() == 4);
		ops.get(2).setOperator(UnaryOperation.OP_TILDA);
		ops.get(0).setOperator(UnaryOperation.OP_NOT);
		rewrite();
		checkResult("<?php !-~-$b;?>");
	}

	@Test
	public void classInstanciationSimple() throws Exception {
		String str = "<?php new MyClass(); ?>";
		initialize(str);

		List<ClassInstanceCreation> instanciations = getAllOfType(program, ClassInstanceCreation.class);
		assertTrue("Unexpected list size.", instanciations.size() == 1);
		instanciations.get(0).getClassName().setClassName(ast.newScalar("Foo"));
		rewrite();
		checkResult("<?php new Foo(); ?>");
	}

	@Test
	public void classInstanciationAddParam() throws Exception {
		String str = "<?php new MyClass(); ?>";
		initialize(str);

		List<ClassInstanceCreation> instanciations = getAllOfType(program, ClassInstanceCreation.class);
		assertTrue("Unexpected list size.", instanciations.size() == 1);
		instanciations.get(0).ctorParams().add(ast.newVariable("foo"));
		instanciations.get(0).ctorParams().add(ast.newVariable("bar"));
		rewrite();
		checkResult("<?php new MyClass($foo, $bar); ?>");
	}

	@Test
	public void classInstanciationVariable() throws Exception {
		String str = "<?php new $a('start'); ?>";
		initialize(str);

		List<ClassInstanceCreation> instanciations = getAllOfType(program, ClassInstanceCreation.class);
		assertTrue("Unexpected list size.", instanciations.size() == 1);
		instanciations.get(0).ctorParams().add(ast.newScalar("'hello'"));
		instanciations.get(0).setClassName(ast.newClassName(ast.newVariable("b")));
		rewrite();
		checkResult("<?php new $b('start', 'hello'); ?>");
	}

	@Test
	public void classInstanciationVariableRemove() throws Exception {
		String str = "<?php new $a('start','end'); ?>";
		initialize(str);

		List<ClassInstanceCreation> instanciations = getAllOfType(program, ClassInstanceCreation.class);
		assertTrue("Unexpected list size.", instanciations.size() == 1);
		instanciations.get(0).ctorParams().remove(1);
		rewrite();
		checkResult("<?php new $a('start'); ?>");
	}

	@Test
	public void classInstanciationFunction() throws Exception {
		String str = "<?php new $a->$b(1, $a); ?>";
		initialize(str);

		List<ClassInstanceCreation> instanciations = getAllOfType(program, ClassInstanceCreation.class);
		assertTrue("Unexpected list size.", instanciations.size() == 1);
		instanciations.get(0).ctorParams().add(0, ast.newScalar("'Welcome'"));
		rewrite();
		checkResult("<?php new $a->$b('Welcome', 1, $a); ?>");
	}

	@Test
	public void refernceSimple() throws Exception {
		String str = "<?php $b = &$a;?>";
		initialize(str);

		List<Reference> references = getAllOfType(program, Reference.class);
		assertTrue("Unexpected list size.", references.size() == 1);
		references.get(0).setExpression(ast.newVariable("foo"));
		rewrite();
		checkResult("<?php $b = &$foo;?>");
	}

	@Test
	public void refernceWithFunction() throws Exception {
		String str = "<?php $g = &$foo(); ?>";
		initialize(str);

		List<Reference> references = getAllOfType(program, Reference.class);
		assertTrue("Unexpected list size.", references.size() == 1);
		references.get(0).setExpression(ast.newFunctionInvocation(ast.newFunctionName(ast.newVariable("bar")), null));
		rewrite();
		checkResult("<?php $g = &$bar(); ?>");
	}

	@Test
	public void refernceInstanciation() throws Exception {
		String str = "<?php $b = &new MyClass(); ?>";
		initialize(str);

		List<Reference> references = getAllOfType(program, Reference.class);
		assertTrue("Unexpected list size.", references.size() == 1);
		((ClassInstanceCreation) references.get(0).getExpression()).ctorParams().add(ast.newVariable("boobo"));
		rewrite();
		checkResult("<?php $b = &new MyClass($boobo); ?>");
	}

	@Test
	public void instanceofSimple() throws Exception {
		String str = "<?php $a instanceof MyClass;?>";
		initialize(str);

		List<InstanceOfExpression> instanceOf = getAllOfType(program, InstanceOfExpression.class);
		assertTrue("Unexpected list size.", instanceOf.size() == 1);
		instanceOf.get(0).getClassName().setClassName(ast.newScalar("Foo", Scalar.TYPE_STRING));
		instanceOf.get(0).setExpression(ast.newVariable("b"));
		rewrite();
		checkResult("<?php $b instanceof Foo;?>");
	}

	@Test
	public void ignoreError() throws Exception {
		String str = "<?php @$a->foo(); ?>";
		initialize(str);

		List<IgnoreError> ignoreErrors = getAllOfType(program, IgnoreError.class);
		assertTrue("Unexpected list size.", ignoreErrors.size() == 1);
		ignoreErrors.get(0).setExpression(ast.newFunctionInvocation(ast.newFunctionName(ast.newScalar("bar")), null));
		rewrite();
		checkResult("<?php @bar(); ?>");
	}

	@Test
	public void include() throws Exception {
		String str = "<?php include('myFile.php'); ?>";
		initialize(str);

		List<Include> includes = getAllOfType(program, Include.class);
		assertTrue("Unexpected list size.", includes.size() == 1);
		includes.get(0).setExpression(ast.newScalar("'newFile.php'"));
		includes.get(0).setIncludetype(Include.IT_INCLUDE_ONCE);
		rewrite();
		checkResult("<?php include_once 'newFile.php'; ?>");
	}

	@Test
	public void includeWithParenthesis1() throws Exception {
		String str = "<?php include('myFile.php'); ?>";
		initialize(str);

		List<Include> includes = getAllOfType(program, Include.class);
		assertTrue("Unexpected list size.", includes.size() == 1);
		includes.get(0).setExpression(ast.newParenthesisExpression(ast.newScalar("'newFile.php'")));
		includes.get(0).setIncludetype(Include.IT_REQUIRE);
		rewrite();
		checkResult("<?php require('newFile.php'); ?>");
	}

	@Test
	public void includeWithParenthesis2() throws Exception {
		String str = "<?php include 'myFile.php'; ?>";
		initialize(str);

		List<Include> includes = getAllOfType(program, Include.class);
		assertTrue("Unexpected list size.", includes.size() == 1);
		includes.get(0).setExpression(ast.newParenthesisExpression(ast.newScalar("'file.php'")));
		includes.get(0).setIncludetype(Include.IT_REQUIRE_ONCE);
		rewrite();
		checkResult("<?php require_once ('file.php'); ?>");
	}

	@Test
	public void includeOnce() throws Exception {
		String str = "<?php include_once($myFile); ?>";
		initialize(str);

		List<Include> includes = getAllOfType(program, Include.class);
		assertTrue("Unexpected list size.", includes.size() == 1);
		includes.get(0).setIncludetype(Include.IT_INCLUDE);
		rewrite();
		checkResult("<?php include($myFile); ?>");
	}

	@Test
	public void arrayCreation1() throws Exception {
		String str = "<?php array(1,2,3,); ?>";
		initialize(str);

		List<ArrayCreation> arrays = getAllOfType(program, ArrayCreation.class);
		assertTrue("Unexpected list size.", arrays.size() == 1);
		arrays.get(0).elements().add(ast.newArrayElement(ast.newScalar("'foo'"), ast.newScalar("'boo'")));
		rewrite();
		checkResult("<?php array(1,2,3, 'foo'=>'boo',); ?>");
	}

	@Test
	public void arrayCreation2() throws Exception {
		String str = "<?php array(1,2,3,); ?>";
		initialize(str);

		List<ArrayCreation> arrays = getAllOfType(program, ArrayCreation.class);
		assertTrue("Unexpected list size.", arrays.size() == 1);
		arrays.get(0).elements().add(ast.newArrayElement(null, ast.newScalar("4")));
		rewrite();
		checkResult("<?php array(1,2,3, 4,); ?>");
	}

	@Test
	public void arrayCreation3() throws Exception {
		String str = "<?php array(1,2,3,); ?>";
		initialize(str);

		List<ArrayCreation> arrays = getAllOfType(program, ArrayCreation.class);
		assertTrue("Unexpected list size.", arrays.size() == 1);
		arrays.get(0).elements().remove(1);
		rewrite();
		checkResult("<?php array(1,3,); ?>");
	}

	@Test
	public void functionDeclaration() throws Exception {
		String str = "<?php function foo() {} ?> ";
		initialize(str);

		List<FunctionDeclaration> declarations = getAllOfType(program, FunctionDeclaration.class);
		assertTrue("Unexpected list size.", declarations.size() == 1);
		declarations.get(0).setFunctionName(ast.newIdentifier("bar"));
		declarations.get(0).formalParameters()
				.add(ast.newFormalParameter(ast.newIdentifier("int"), ast.newVariable("a"), null, false));
		rewrite();
		checkResult("<?php function bar(int $a) {} ?> ");
	}

	@Test
	public void functionDeclarationWithParam1() throws Exception {
		String str = "<?php function foo( $a) {} ?> ";
		initialize(str);

		List<FunctionDeclaration> declarations = getAllOfType(program, FunctionDeclaration.class);
		assertTrue("Unexpected list size.", declarations.size() == 1);
		declarations.get(0).formalParameters().get(0).setParameterType(ast.newIdentifier("string"));
		rewrite();
		checkResult("<?php function foo( string $a) {} ?> ");
	}

	@Test
	public void functionDeclarationWithParam2() throws Exception {
		String str = "<?php function foo($a, boolean $b) {} ?> ";
		initialize(str);

		List<FunctionDeclaration> declarations = getAllOfType(program, FunctionDeclaration.class);
		assertTrue("Unexpected list size.", declarations.size() == 1);
		declarations.get(0).formalParameters().get(1).setParameterType(ast.newIdentifier("string"));
		rewrite();
		checkResult("<?php function foo($a, string $b) {} ?> ");
	}

	@Test
	public void functionDeclarationWithParam3() throws Exception {
		String str = "<?php function foo($a, $b) {} ?> ";
		initialize(str);

		List<FunctionDeclaration> declarations = getAllOfType(program, FunctionDeclaration.class);
		assertTrue("Unexpected list size.", declarations.size() == 1);
		declarations.get(0).formalParameters().get(1).setParameterType(ast.newIdentifier("string"));
		rewrite();
		checkResult("<?php function foo($a, string $b) {} ?> ");
	}

	@Test
	public void functionDeclarationDeleteType() throws Exception {
		String str = "<?php function foo(boolean $a) {} ?> ";
		initialize(str);

		List<FunctionDeclaration> declarations = getAllOfType(program, FunctionDeclaration.class);
		assertTrue("Unexpected list size.", declarations.size() == 1);
		declarations.get(0).formalParameters().get(0).getParameterType().delete();
		rewrite();
		checkResult("<?php function foo( $a) {} ?> ");
	}

	@Test
	public void functionDeclarationCreationPHP5() throws Exception {
		String str = "<?php ?> ";
		initialize(str);

		Identifier name = ast.newIdentifier("foo");
		List<FormalParameter> formalParameters = new ArrayList<FormalParameter>();
		formalParameters
				.add(ast.newFormalParameter(ast.newIdentifier("int"), ast.newVariable("a"), ast.newScalar("5"), false));
		formalParameters.add(ast.newFormalParameter(null, ast.newVariable("b"), ast.newScalar("'boobo'"), false));
		Block body = ast.newBlock();
		program.statements().add(0, ast.newFunctionDeclaration(name, formalParameters, body, true));
		rewrite();
		checkResult("<?php function &foo(int $a = 5,  $b = 'boobo') {\n}\n?> ");
	}

	@Test
	public void functionDeclarationChangeDefault1() throws Exception {
		String str = "<?php function foo(boolean $a = false) {} ?> ";
		initialize(str);

		List<FunctionDeclaration> declarations = getAllOfType(program, FunctionDeclaration.class);
		assertTrue("Unexpected list size.", declarations.size() == 1);
		((Scalar) declarations.get(0).formalParameters().get(0).getDefaultValue()).setStringValue("true");
		rewrite();
		checkResult("<?php function foo(boolean $a = true) {} ?> ");
	}

	@Test
	public void functionDeclarationDeleteDefault1() throws Exception {
		String str = "<?php function foo(string $a = 'foo') {} ?> ";
		initialize(str);

		List<FunctionDeclaration> declarations = getAllOfType(program, FunctionDeclaration.class);
		assertTrue("Unexpected list size.", declarations.size() == 1);
		declarations.get(0).formalParameters().get(0).setDefaultValue(null);
		rewrite();
		checkResult("<?php function foo(string $a) {} ?> ");
	}

	@Test
	public void functionDeclarationDeleteDefault2() throws Exception {
		String str = "<?php function foo(string $a = 'foo') {} ?> ";
		initialize(str);

		List<FunctionDeclaration> declarations = getAllOfType(program, FunctionDeclaration.class);
		assertTrue("Unexpected list size.", declarations.size() == 1);
		declarations.get(0).formalParameters().get(0).getDefaultValue().delete();
		rewrite();
		checkResult("<?php function foo(string $a) {} ?> ");
	}

	@Test
	public void classDeclarationSimple() throws Exception {
		String str = "<?php class MyClass { } ?> ";
		initialize(str);

		List<ClassDeclaration> declarations = getAllOfType(program, ClassDeclaration.class);
		assertTrue("Unexpected list size.", declarations.size() == 1);
		declarations.get(0).setName(ast.newIdentifier("Foo"));
		rewrite();
		checkResult("<?php class Foo { } ?> ");
	}

	@Test
	public void functionDeclarationRemoveReference() throws Exception {
		String str = "<?php function  &foo() {} ?> ";
		initialize(str);

		List<FunctionDeclaration> declarations = getAllOfType(program, FunctionDeclaration.class);
		assertTrue("Unexpected list size.", declarations.size() == 1);
		declarations.get(0).setIsReference(false);
		rewrite();
		checkResult("<?php function foo() {} ?> ");
	}

	@Test
	public void functionDeclarationAddReference() throws Exception {
		String str = "<?php function  foo() {} ?> ";
		initialize(str);

		List<FunctionDeclaration> declarations = getAllOfType(program, FunctionDeclaration.class);
		assertTrue("Unexpected list size.", declarations.size() == 1);
		declarations.get(0).setIsReference(true);
		rewrite();
		checkResult("<?php function &foo() {} ?> ");
	}

	@Test
	public void classDeclarationAddSuper() throws Exception {
		String str = "<?php class MyClass { } ?> ";
		initialize(str);

		List<ClassDeclaration> declarations = getAllOfType(program, ClassDeclaration.class);
		assertTrue("Unexpected list size.", declarations.size() == 1);
		declarations.get(0).setModifier(ClassDeclaration.MODIFIER_FINAL);
		declarations.get(0).setSuperClass(ast.newIdentifier("Boo"));
		rewrite();
		checkResult("<?php final class MyClass extends Boo { } ?> ");
	}

	@Test
	public void classDeclarationDeleteSuper() throws Exception {
		String str = "<?php class MyClass extends Foo { } ?> ";
		initialize(str);

		List<ClassDeclaration> declarations = getAllOfType(program, ClassDeclaration.class);
		assertTrue("Unexpected list size.", declarations.size() == 1);
		declarations.get(0).getSuperClass().delete();
		rewrite();
		checkResult("<?php class MyClass { } ?> ");
	}

	@Test
	public void classDeclarationDeleteSuperWithImplements() throws Exception {
		String str = "<?php class MyClass extends Foo implements Bar { } ?> ";
		initialize(str);

		List<ClassDeclaration> declarations = getAllOfType(program, ClassDeclaration.class);
		assertTrue("Unexpected list size.", declarations.size() == 1);
		declarations.get(0).getSuperClass().delete();
		rewrite();
		checkResult("<?php class MyClass implements Bar { } ?> ");
	}

	@Test
	public void classDeclarationReplaceSuper() throws Exception {
		String str = "<?php class MyClass extends Foo { } ?> ";
		initialize(str);

		List<ClassDeclaration> declarations = getAllOfType(program, ClassDeclaration.class);
		assertTrue("Unexpected list size.", declarations.size() == 1);
		((Identifier) declarations.get(0).getSuperClass()).setName("Bar");
		rewrite();
		checkResult("<?php class MyClass extends Bar { } ?> ");
	}

	@Test
	public void classDeclarationAddInterfaces() throws Exception {
		String str = "<?php class MyClass { } ?> ";
		initialize(str);

		List<ClassDeclaration> declarations = getAllOfType(program, ClassDeclaration.class);
		assertTrue("Unexpected list size.", declarations.size() == 1);
		declarations.get(0).interfaces().add(ast.newIdentifier("Foo"));
		declarations.get(0).interfaces().add(ast.newIdentifier("Bar"));
		rewrite();
		checkResult("<?php class MyClass implements Foo, Bar { } ?> ");
	}

	@Test
	public void classDeclarationRemoveInterface() throws Exception {
		String str = "<?php class MyClass extends AAA implements Foo,Bar{ } ?> ";
		initialize(str);

		List<ClassDeclaration> declarations = getAllOfType(program, ClassDeclaration.class);
		assertTrue("Unexpected list size.", declarations.size() == 1);
		declarations.get(0).interfaces().remove(0);
		rewrite();
		checkResult("<?php class MyClass extends AAA implements Bar{ } ?> ");
	}

	@Test
	public void classDeclarationRemoveAllInterfaces() throws Exception {
		String str = "<?php class MyClass extends AAA implements Foo,Bar{ } ?> ";
		initialize(str);

		List<ClassDeclaration> declarations = getAllOfType(program, ClassDeclaration.class);
		assertTrue("Unexpected list size.", declarations.size() == 1);
		declarations.get(0).interfaces().clear();
		rewrite();
		checkResult("<?php class MyClass extends AAA{ } ?> ");
	}

	@Test
	public void classDeclarationRenameInterface() throws Exception {
		String str = "<?php class MyClass extends AAA implements Foo,Bar{ } ?> ";
		initialize(str);

		List<ClassDeclaration> declarations = getAllOfType(program, ClassDeclaration.class);
		assertTrue("Unexpected list size.", declarations.size() == 1);
		declarations.get(0).interfaces().get(1).setName("BooBo");
		rewrite();
		checkResult("<?php class MyClass extends AAA implements Foo,BooBo{ } ?> ");
	}

	@Test
	public void interfaceDeclarationSimple() throws Exception {
		String str = "<?php interface MyInterface { } ?> ";
		initialize(str);

		List<InterfaceDeclaration> declarations = getAllOfType(program, InterfaceDeclaration.class);
		assertTrue("Unexpected list size.", declarations.size() == 1);
		declarations.get(0).setName(ast.newIdentifier("Foo"));
		rewrite();
		checkResult("<?php interface Foo { } ?> ");
	}

	@Test
	public void interfaceDeclarationAddExtends() throws Exception {
		String str = "<?php interface MyInterface { const MY_CONSTANT = 3; public function myFunction($a); } ?> ";
		initialize(str);

		List<InterfaceDeclaration> declarations = getAllOfType(program, InterfaceDeclaration.class);
		assertTrue("Unexpected list size.", declarations.size() == 1);
		declarations.get(0).interfaces().add(ast.newIdentifier("Foo"));
		declarations.get(0).interfaces().add(ast.newIdentifier("Bar"));
		rewrite();
		checkResult(
				"<?php interface MyInterface extends Foo, Bar { const MY_CONSTANT = 3; public function myFunction($a); } ?> ");
	}

	@Test
	public void interfaceDeclarationRemoveExtend() throws Exception {
		String str = "<?php interface MyInterface extends Foo, Bar{ const MY_CONSTANT = 3; public function myFunction($a); } ?> ";
		initialize(str);

		List<InterfaceDeclaration> declarations = getAllOfType(program, InterfaceDeclaration.class);
		assertTrue("Unexpected list size.", declarations.size() == 1);
		declarations.get(0).interfaces().remove(1);
		rewrite();
		checkResult(
				"<?php interface MyInterface extends Foo{ const MY_CONSTANT = 3; public function myFunction($a); } ?> ");
	}

	@Test
	public void interfaceDeclarationRemoveAllExtends() throws Exception {
		String str = "<?php interface MyInterface extends Foo, Bar { const MY_CONSTANT = 3; public function myFunction($a); } ?> ";
		initialize(str);

		List<InterfaceDeclaration> declarations = getAllOfType(program, InterfaceDeclaration.class);
		assertTrue("Unexpected list size.", declarations.size() == 1);
		declarations.get(0).interfaces().clear();
		rewrite();
		checkResult("<?php interface MyInterface { const MY_CONSTANT = 3; public function myFunction($a); } ?> ");
	}

	@Test
	public void interfaceDeclarationRenameExtend() throws Exception {
		String str = "<?php interface MyInterface extends Foo, Bar{ const MY_CONSTANT = 3; public function myFunction($a); } ?> ";
		initialize(str);

		List<InterfaceDeclaration> declarations = getAllOfType(program, InterfaceDeclaration.class);
		assertTrue("Unexpected list size.", declarations.size() == 1);
		declarations.get(0).interfaces().get(0).setName("Boobo");
		rewrite();
		checkResult(
				"<?php interface MyInterface extends Boobo, Bar{ const MY_CONSTANT = 3; public function myFunction($a); } ?> ");
	}

	@Test
	public void arrayKeyValueRemoveKey() throws Exception {
		String str = "<?php array('Dodo'=>'Golo','Dafna'=>'Dodidu'); ?>";
		initialize(str);

		List<ArrayCreation> arrays = getAllOfType(program, ArrayCreation.class);
		assertTrue("Unexpected list size.", arrays.size() == 1);
		arrays.get(0).elements().get(1).getKey().delete();
		rewrite();
		checkResult("<?php array('Dodo'=>'Golo','Dodidu'); ?>");
	}

	@Test
	public void arrayKeyValueAddKey() throws Exception {
		String str = "<?php array('Golo','Dafna'=>'Dodidu'); ?>";
		initialize(str);

		List<ArrayCreation> arrays = getAllOfType(program, ArrayCreation.class);
		assertTrue("Unexpected list size.", arrays.size() == 1);
		arrays.get(0).elements().get(0).setKey(ast.newScalar("'Dodo'"));
		rewrite();
		checkResult("<?php array('Dodo'=>'Golo','Dafna'=>'Dodidu'); ?>");
	}

	@Test
	public void arrayKeyValueChangeKey() throws Exception {
		String str = "<?php array('Golo','Dafna'=>'Dodidu'); ?>";
		initialize(str);

		List<ArrayCreation> arrays = getAllOfType(program, ArrayCreation.class);
		assertTrue("Unexpected list size.", arrays.size() == 1);
		((Scalar) arrays.get(0).elements().get(1).getKey()).setStringValue("'Boobo'");
		rewrite();
		checkResult("<?php array('Golo','Boobo'=>'Dodidu'); ?>");
	}

	@Test
	public void arrayKeyDelete() throws Exception {
		String str = "<?php array('Dudu'=>'Golo','Dafna'=>'Dodidu'); ?>";
		initialize(str);

		List<ArrayCreation> arrays = getAllOfType(program, ArrayCreation.class);
		assertTrue("Unexpected list size.", arrays.size() == 1);
		arrays.get(0).elements().get(1).delete();
		rewrite();
		checkResult("<?php array('Dudu'=>'Golo'); ?>");
	}

	@Test
	public void conditionalExpression() throws Exception {
		String str = "<?php (bool)$a ? 3 : 4;?>";
		initialize(str);

		List<ConditionalExpression> conditional = getAllOfType(program, ConditionalExpression.class);
		assertTrue("Unexpected list size.", conditional.size() == 1);
		((CastExpression) conditional.get(0).getCondition()).setExpression(ast.newVariable("b"));
		conditional.get(0).setIfFalse(ast.newScalar("100"));
		conditional.get(0).setIfTrue(ast.newScalar("200"));
		rewrite();
		checkResult("<?php (bool)$b ? 200 : 100;?>");
	}

	@Test
	public void infixExpression() throws Exception {
		String str = "<?php $a + 1;?>";
		initialize(str);

		List<InfixExpression> expressions = getAllOfType(program, InfixExpression.class);
		assertTrue("Unexpected list size.", expressions.size() == 1);
		expressions.get(0).setOperator(InfixExpression.OP_MOD);
		expressions.get(0).setRight(ast.newFunctionInvocation(ast.newFunctionName(ast.newScalar("foo")), null));
		rewrite();
		checkResult("<?php $a % foo();?>");
	}

	@Test
	public void concatOperation() throws Exception {
		String str = "<?php 'string'.$c;?>";
		initialize(str);

		List<InfixExpression> expressions = getAllOfType(program, InfixExpression.class);
		assertTrue("Unexpected list size.", expressions.size() == 1);
		expressions.get(0).setOperator(InfixExpression.OP_IS_NOT_EQUAL);
		rewrite();
		checkResult("<?php 'string'!=$c;?>");
	}

	@Test
	public void quoteHeredoc() throws Exception {
		String str = "<?php \"this\nis $a quote\";?>";
		initialize(str);

		List<Quote> quotes = getAllOfType(program, Quote.class);
		assertTrue("Unexpected list size.", quotes.size() == 1);
		quotes.get(0).setQuoteType(Quote.QT_HEREDOC);
		rewrite();
		checkResult("<?php <<<Heredoc\nthis\nis $a quote\nHeredoc;\n;?>");
	}

	@Test
	public void quoteSingle() throws Exception {
		String str = "<?php \"this is $a quote\";?>";
		initialize(str);

		List<Quote> quotes = getAllOfType(program, Quote.class);
		assertTrue("Unexpected list size.", quotes.size() == 1);
		quotes.get(0).setQuoteType(Quote.QT_SINGLE);
		rewrite();
		checkResult("<?php 'this is $a quote';?>");
	}

	@Test
	public void quoteDouble() throws Exception {
		String str = "<?php <<<Heredoc\r\nthis is $a quote\r\nHeredoc;\r\n?>";
		initialize(str);

		List<Quote> quotes = getAllOfType(program, Quote.class);
		assertTrue("Unexpected list size.", quotes.size() == 1);
		quotes.get(0).setQuoteType(Quote.QT_QUOTE);
		rewrite();
		checkResult("<?php \"this is $a quote\";\r\n?>");
	}

	@Test
	public void quoteWithCurly() throws Exception {
		String str = "<?php $text = <<<EOF\ntest{test}test\nEOF;\n?>";
		initialize(str);

		List<Quote> quotes = getAllOfType(program, Quote.class);
		assertTrue("Unexpected list size.", quotes.size() == 1);
		quotes.get(0).setQuoteType(Quote.QT_QUOTE);
		rewrite();
		checkResult("<?php $text = \"test{test}test\";\n?>");
	}

	@Test
	public void emptyHeredoc() throws Exception {
		String str = "<?php <<<Heredoc\nHeredoc;\n?>";
		initialize(str);

		List<Quote> quotes = getAllOfType(program, Quote.class);
		assertTrue("Unexpected list size.", quotes.size() == 1);
		quotes.get(0).expressions().add(ast.newScalar("Hello World"));
		rewrite();
		checkResult("<?php <<<Heredoc\nHello World\nHeredoc;\n?>");
	}

	@Test
	public void breakStatementChange() throws Exception {
		String str = "<?php break $a;?>";
		initialize(str);

		List<BreakStatement> breaks = getAllOfType(program, BreakStatement.class);
		assertTrue("Unexpected list size.", breaks.size() == 1);
		breaks.get(0).setExpression(ast.newVariable("b"));
		rewrite();
		checkResult("<?php break $b;?>");
	}

	@Test
	public void breakStatementRemove() throws Exception {
		String str = "<?php break $a;?>";
		initialize(str);

		List<BreakStatement> breaks = getAllOfType(program, BreakStatement.class);
		assertTrue("Unexpected list size.", breaks.size() == 1);
		breaks.get(0).getExpression().delete();
		rewrite();
		checkResult("<?php break;?>");
	}

	@Test
	public void breakStatementAdd() throws Exception {
		String str = "<?php break;?>";
		initialize(str);

		List<BreakStatement> breaks = getAllOfType(program, BreakStatement.class);
		assertTrue("Unexpected list size.", breaks.size() == 1);
		breaks.get(0).setExpression(ast.newVariable("b"));
		rewrite();
		checkResult("<?php break $b;?>");
	}

	@Test
	public void continueStatementAdd() throws Exception {
		String str = "<?php continue;?>";
		initialize(str);

		List<ContinueStatement> continueExp = getAllOfType(program, ContinueStatement.class);
		assertTrue("Unexpected list size.", continueExp.size() == 1);
		continueExp.get(0).setExpression(ast.newVariable("b"));
		rewrite();
		checkResult("<?php continue $b;?>");
	}

	@Test
	public void continueStatementRemove() throws Exception {
		String str = "<?php continue $a;?>";
		initialize(str);

		List<ContinueStatement> continueExp = getAllOfType(program, ContinueStatement.class);
		assertTrue("Unexpected list size.", continueExp.size() == 1);
		continueExp.get(0).getExpression().delete();
		rewrite();
		checkResult("<?php continue;?>");
	}

	@Test
	public void continueStatementChange() throws Exception {
		String str = "<?php continue $a;?>";
		initialize(str);

		List<ContinueStatement> continueExp = getAllOfType(program, ContinueStatement.class);
		assertTrue("Unexpected list size.", continueExp.size() == 1);
		continueExp.get(0).setExpression(ast.newVariable("b"));
		rewrite();
		checkResult("<?php continue $b;?>");
	}

	@Test
	public void returnStatementAdd() throws Exception {
		String str = "<?php return; ?>";
		initialize(str);

		List<ReturnStatement> returnStatements = getAllOfType(program, ReturnStatement.class);
		assertTrue("Unexpected list size.", returnStatements.size() == 1);
		returnStatements.get(0).setExpression(ast.newVariable("a"));
		rewrite();
		checkResult("<?php return $a; ?>");
	}

	@Test
	public void returnExprStatementChange() throws Exception {
		String str = "<?php return $a; ?>";
		initialize(str);

		List<ReturnStatement> returnStatements = getAllOfType(program, ReturnStatement.class);
		assertTrue("Unexpected list size.", returnStatements.size() == 1);
		((Variable) returnStatements.get(0).getExpression()).setName(ast.newScalar("b"));
		rewrite();
		checkResult("<?php return $b; ?>");
	}

	@Test
	public void returnExprStatementRemove() throws Exception {
		String str = "<?php return $c; ?>";
		initialize(str);

		List<ReturnStatement> returnStatements = getAllOfType(program, ReturnStatement.class);
		assertTrue("Unexpected list size.", returnStatements.size() == 1);
		returnStatements.get(0).getExpression().delete();
		rewrite();
		checkResult("<?php return; ?>");
	}

	@Test
	public void echoStatement() throws Exception {
		String str = "<?php echo \"hello \",$b;?>";
		initialize(str);

		List<EchoStatement> list = getAllOfType(program, EchoStatement.class);
		assertTrue("Unexpected list size.", list.size() == 1);
		list.get(0).expressions().set(0, ast.newScalar("\"replaced hello\""));
		list.get(0).expressions().add(ast.newScalar("'1111'"));
		rewrite();
		checkResult("<?php echo \"replaced hello\",$b, '1111';?>");
	}

	@Test
	public void newEchoStatement() throws Exception {
		String str = "<?php ?>";
		initialize(str);

		List<Expression> expressions = new ArrayList<Expression>(2);
		expressions.add(ast.newScalar("\"hello\""));
		expressions.add(ast.newVariable("b"));
		program.statements().add(0, ast.newEchoStatement(expressions));
		rewrite();
		checkResult("<?php echo \"hello\", $b;\n?>");
	}

	@Test
	public void switchStatementSetExpression() throws Exception {
		String str = "<?php switch ($i) { case 0:    echo 'i equals 0';    break; case 1:     echo 'i equals 1';     break; default:    echo 'i not equals 0,1';  }  ?>";
		initialize(str);

		List<SwitchStatement> statements = getAllOfType(program, SwitchStatement.class);
		assertTrue("Unexpected list size.", statements.size() == 1);
		statements.get(0).setExpression(ast.newVariable("a"));
		rewrite();
		checkResult(
				"<?php switch ($a) { case 0:    echo 'i equals 0';    break; case 1:     echo 'i equals 1';     break; default:    echo 'i not equals 0,1';  }  ?>");
	}

	@Test
	public void switchStatementChangeCaseAction() throws Exception {
		String str = "<?php switch ($i) { case 0:    echo 'i equals 0';    break; case 1:     echo 'i equals 1';     break; default:    echo 'i not equals 0,1';  }  ?>";
		initialize(str);

		List<SwitchCase> switchCases = getAllOfType(program, SwitchCase.class);
		assertTrue("Unexpected list size.", switchCases.size() == 3);
		switchCases.get(0).setValue(ast.newScalar("5"));
		switchCases.get(0).actions().set(0, ast.newEchoStatement(ast.newScalar("'i equals 5'")));
		rewrite();
		checkResult(
				"<?php switch ($i) { case 5:    echo 'i equals 5';\n    break; case 1:     echo 'i equals 1';     break; default:    echo 'i not equals 0,1';  }  ?>");
	}

	@Test
	public void switchStatementCaseDeletion() throws Exception {
		String str = "<?php switch ($i) { case 0:    echo 'i equals 0';    break; case 1:     echo 'i equals 1';     break; default:    echo 'i not equals 0,1';  }  ?>";
		initialize(str);

		List<SwitchCase> switchCases = getAllOfType(program, SwitchCase.class);
		assertTrue("Unexpected list size.", switchCases.size() == 3);
		switchCases.get(0).delete();
		rewrite();
		checkResult(
				"<?php switch ($i) { case 1:     echo 'i equals 1';     break; default:    echo 'i not equals 0,1';  }  ?>");
	}

	@Test
	public void switchStatementChangeAddCase() throws Exception {
		String newLine = System.getProperty("line.separator");
		String str = "<?php " + newLine + "switch ($i) { " + newLine + "	case 0:" + newLine
				+ "		echo 'i equals 0';" + newLine + "		break;" + newLine + "	case 1:" + newLine
				+ "		echo 'i equals 1';" + newLine + "		break;" + newLine + "	default:" + newLine
				+ "		echo 'i not equals 0,1';" + newLine + "}  ?>";
		initialize(str);

		List<Block> blocks = getAllOfType(program, Block.class);
		assertTrue("Unexpected list size.", blocks.size() == 1);
		List<Statement> actions = new ArrayList<Statement>(3);
		actions.add(ast.newEchoStatement(ast.newScalar("'a new case'")));
		actions.add(
				ast.newExpressionStatement(ast.newFunctionInvocation(ast.newFunctionName(ast.newScalar("foo")), null)));
		actions.add(ast.newBreakStatement());
		blocks.get(0).statements().add(2, ast.newSwitchCase(ast.newScalar("2"), actions, false));
		rewrite();
		checkResult(
				"<?php \nswitch ($i) { \n	case 0:\n		echo 'i equals 0';\n		break;\n	case 1:\n		echo 'i equals 1';\n		break;\n	case 2 :\n		echo 'a new case';\n		foo ();\n		break;\n\n	default:\n		echo 'i not equals 0,1';\n}  ?>");
	}

	@Test
	public void ifStatementCondition() throws Exception {
		String str = "<?php if ($a > $b) {   echo 'a is bigger than b';} elseif ($a == $b) {   echo 'a is equal to b';} else {   echo 'a is smaller than b';} ?>";
		initialize(str);

		List<IfStatement> statements = getAllOfType(program, IfStatement.class);
		assertTrue("Unexpected list size.", statements.size() == 2);
		statements.get(0).setCondition(
				ast.newInfixExpression(ast.newVariable("c"), InfixExpression.OP_BOOL_OR, ast.newVariable("d")));
		rewrite();
		checkResult(
				"<?php if ($c || $d) {   echo 'a is bigger than b';} elseif ($a == $b) {   echo 'a is equal to b';} else {   echo 'a is smaller than b';} ?>");
	}

	@Test
	public void ifStatementCurlyToAlternative() throws Exception {
		String str = "<?php if ($a > $b) {\n	echo 'a > b';\n	} else {\n	echo 'a <= b';\n} ?>";
		initialize(str);

		List<IfStatement> statements = getAllOfType(program, IfStatement.class);
		List<Block> blocks = getAllOfType(program, Block.class);
		assertTrue("Unexpected list size.", statements.size() == 1);
		// TODO - For now, we must set all to be non-curly. Maybe this should be
		// an IfStatement property.
		blocks.get(0).setIsCurly(false);
		blocks.get(1).setIsCurly(false);
		rewrite();
		checkResult("<?php if ($a > $b) :\n	echo 'a > b';\n	 else :\n	echo 'a <= b';\nendif; ?>");
	}

	@Test
	public void ifStatementAlternativeToCurly() throws Exception {
		String str = "<?php if ($a > $b) :\n	echo 'a > b';\n else :\n	echo 'a <= b';\nendif; ?>";
		initialize(str);

		List<IfStatement> statements = getAllOfType(program, IfStatement.class);
		List<Block> blocks = getAllOfType(program, Block.class);
		assertTrue("Unexpected list size.", statements.size() == 1);
		// TODO - For now, we must set all to be curly. Maybe this should be an
		// IfStatement property.
		blocks.get(0).setIsCurly(true);
		blocks.get(1).setIsCurly(true);
		rewrite();
		checkResult("<?php if ($a > $b) {\n	echo 'a > b';\n}\n else {\n	echo 'a <= b';\n}\n ?>");
	}

	@Test
	public void nestedIfStatementCurlyToAlternative1() throws Exception {
		String str = "<?php if ($a > $b) {\n	if ($a == $b) {\n	echo 'a > b';\n	}\n} else {\n	echo 'a <= b';\n} ?>";
		initialize(str);

		List<Block> blocks = getAllOfType(program, Block.class);
		// TODO - For now, we must set all to be non-curly. Maybe this should be
		// an IfStatement property.
		for (Block block : blocks) {
			block.setIsCurly(false);
		}
		rewrite();
		checkResult(
				"<?php if ($a > $b) :\n	if ($a == $b) :\n	echo 'a > b';\n\tendif;\n else :\n	echo 'a <= b';\nendif; ?>");
	}

	@Test
	public void nestedIfStatementCurlyToAlternative2() throws Exception {
		String str = "<?php if ($a > $b) {\n	if ($a == $b) {\n	echo 'a > b'; \n} elseif ($b == 1) {\n	echo 'boobo';\n} else {\n	echo 5;\n	}\n} else {\n	echo 'a <= b';\n}\n?>";
		initialize(str);

		List<Block> blocks = getAllOfType(program, Block.class);
		// TODO - For now, we must set all to be non-curly. Maybe this should be
		// an IfStatement property.
		for (Block block : blocks) {
			block.setIsCurly(false);
		}
		rewrite();
		checkResult(
				"<?php if ($a > $b) :\n\tif ($a == $b) :\n\techo 'a > b'; \n elseif ($b == 1) :\n\techo 'boobo';\n else :\n\techo 5;\n\tendif;\n else :\n\techo 'a <= b';\nendif;\n?>");
	}

	@Test
	public void whileStatementCurlyToAlternative() throws Exception {
		String str = "<?php while ($i <= 10){\n echo $i++;\n}\n?>";
		initialize(str);

		List<Block> blocks = getAllOfType(program, Block.class);
		blocks.get(0).setIsCurly(false);
		rewrite();
		checkResult("<?php while ($i <= 10):\n echo $i++;\nendwhile;\n?>");
	}

	@Test
	public void whileStatementAlternativeToCurly() throws Exception {
		String str = "<?php while ($i <= 10):  echo $i;   $i++; endwhile; ?>";
		initialize(str);

		List<Block> blocks = getAllOfType(program, Block.class);
		blocks.get(0).setIsCurly(true);
		rewrite();
		checkResult("<?php while ($i <= 10){  echo $i;   $i++;\n}  ?>");
	}

	@Test
	public void forStatementCurlyToAlternative() throws Exception {
		String str = "<?php for ($i = 1; $i <= 10; $i++) {  echo $i; } ?>";
		initialize(str);

		List<Block> blocks = getAllOfType(program, Block.class);
		blocks.get(0).setIsCurly(false);
		rewrite();
		checkResult("<?php for ($i = 1; $i <= 10; $i++) :  echo $i; endfor; ?>");
	}

	@Test
	public void forStatementAlternativeToCurly() throws Exception {
		String str = "<?php for ($i = 1; $i <= 10; $i++) :  echo $i; endfor; ?>";
		initialize(str);

		List<Block> blocks = getAllOfType(program, Block.class);
		blocks.get(0).setIsCurly(true);
		rewrite();
		checkResult("<?php for ($i = 1; $i <= 10; $i++) {  echo $i;\n}  ?>");
	}

	@Test
	public void forEachStatementCurlyToAlternative() throws Exception {
		String str = "<?php foreach ($arr as &$value) { $value = $value * 2; } ?>";
		initialize(str);

		List<Block> blocks = getAllOfType(program, Block.class);
		blocks.get(0).setIsCurly(false);
		rewrite();
		checkResult("<?php foreach ($arr as &$value) : $value = $value * 2; endforeach; ?>");
	}

	@Test
	public void forEachStatementAlternativeToCurly() throws Exception {
		String str = "<?php foreach ($arr as &$value) : $value = $value * 2; endforeach; ?>";
		initialize(str);

		List<Block> blocks = getAllOfType(program, Block.class);
		blocks.get(0).setIsCurly(true);
		rewrite();
		checkResult("<?php foreach ($arr as &$value) { $value = $value * 2;\n}  ?>");
	}

	@Test
	public void switchStatementCurlyToAlternative() throws Exception {
		String str = "<?php switch ($i) { case 0:    echo 'i equals 0';    break; case 1:     echo 'i equals 1';     break; default:    echo 'i not equals 0,1';  }  ?>";
		initialize(str);

		List<Block> blocks = getAllOfType(program, Block.class);
		blocks.get(0).setIsCurly(false);
		rewrite();
		checkResult(
				"<?php switch ($i) : case 0:    echo 'i equals 0';    break; case 1:     echo 'i equals 1';     break; default:    echo 'i not equals 0,1';  endswitch;  ?>");
	}

	@Test
	public void switchStatementAlternativeToCurly() throws Exception {
		String str = "<?php switch ($i) : case 0:    echo 'i equals 0';    break; case 1:     echo 'i equals 1';     break; default:    echo 'i not equals 0,1';  endswitch;  ?>";
		initialize(str);

		List<Block> blocks = getAllOfType(program, Block.class);
		blocks.get(0).setIsCurly(true);
		rewrite();
		checkResult(
				"<?php switch ($i) { case 0:    echo 'i equals 0';    break; case 1:     echo 'i equals 1';     break; default:    echo 'i not equals 0,1';  \n}  ?>");
	}

	@Test
	public void whileStatement() throws Exception {
		String str = "<?php while ($i <= 10) echo $i++; ?>";
		initialize(str);

		List<WhileStatement> statements = getAllOfType(program, WhileStatement.class);
		assertTrue("Unexpected list size.", statements.size() == 1);
		statements.get(0).setCondition(
				ast.newInfixExpression(ast.newVariable("a"), InfixExpression.OP_BOOL_OR, ast.newVariable("b")));
		statements.get(0).setBody(ast.newEchoStatement(ast.newScalar("'Hello!!'")));
		rewrite();
		checkResult("<?php while ($a || $b)echo 'Hello!!';\n ?>");
	}

	@Test
	public void doWhileStatement() throws Exception {
		String str = "<?php do { echo $i;} while ($i > 0); ?>";
		initialize(str);

		List<DoStatement> statements = getAllOfType(program, DoStatement.class);
		assertTrue("Unexpected list size.", statements.size() == 1);
		statements.get(0).setCondition(
				ast.newInfixExpression(ast.newVariable("a"), InfixExpression.OP_IS_NOT_EQUAL, ast.newVariable("b")));

		List<Statement> newStatements = new ArrayList<Statement>(2);
		newStatements.add(ast.newEchoStatement(ast.newScalar("'Hello!!'")));
		newStatements.add(ast.newEchoStatement(ast.newScalar("'Goodbye!!'")));
		statements.get(0).setBody(ast.newBlock(newStatements));
		rewrite();
		checkResult("<?php do {\n	echo 'Hello!!';\n	echo 'Goodbye!!';\n}\nwhile ($a != $b); ?>");
	}

	@Test
	public void curlyBlockCreation() throws Exception {
		String str = "<?php ?>";
		initialize(str);

		List<Statement> trueStatement = new ArrayList<Statement>(1);
		trueStatement.add(ast.newEchoStatement(ast.newScalar("'Boobo'")));
		Block block = ast.newBlock(trueStatement);
		block.setIsCurly(true);
		program.statements().add(0, ast.newIfStatement(ast.newVariable("a"), block, null));
		rewrite();
		checkResult("<?php if ($a) {\n	echo 'Boobo';\n}\n?>");
	}

	@Test
	public void alternativeBlockCreation() throws Exception {
		String str = "<?php ?>";
		initialize(str);

		List<Statement> trueStatement = new ArrayList<Statement>(1);
		trueStatement.add(ast.newEchoStatement(ast.newScalar("'Boobo'")));
		Block block = ast.newBlock(trueStatement);
		block.setIsCurly(false);
		program.statements().add(0, ast.newIfStatement(ast.newVariable("a"), block, null));
		rewrite();
		checkResult("<?php if ($a) :\n	echo 'Boobo';\n\nendif;\n?>");
	}

	@Test
	public void forStatement() throws Exception {
		String str = "<?php for ($i = 1; $i <= 10; $i++) {  echo $i; } ?>";
		initialize(str);

		List<ForStatement> statements = getAllOfType(program, ForStatement.class);
		assertTrue("Unexpected list size.", statements.size() == 1);
		statements.get(0).initializers()
				.add(ast.newAssignment(ast.newVariable("j"), Assignment.OP_EQUAL, ast.newScalar("5")));
		statements.get(0).conditions()
				.add(ast.newInfixExpression(ast.newVariable("j"), InfixExpression.OP_LGREATER, ast.newScalar("20")));
		statements.get(0).updaters().add(ast.newPrefixExpression(ast.newVariable("j"), PrefixExpression.OP_INC));
		rewrite();
		checkResult("<?php for ($i = 1, $j=5; $i <= 10, $j > 20; $i++, $j++) {  echo $i; } ?>");
	}

	@Test
	public void forStatementRemoveInitialization() throws Exception {
		String str = "<?php for ($i = 1; $i <= 10; $i++) {  echo $i; } ?>";
		initialize(str);

		List<ForStatement> statements = getAllOfType(program, ForStatement.class);
		assertTrue("Unexpected list size.", statements.size() == 1);
		statements.get(0).initializers().get(0).delete();
		rewrite();
		checkResult("<?php for (; $i <= 10; $i++) {  echo $i; } ?>");
	}

	@Test
	public void forStatementRemoveCondition() throws Exception {
		String str = "<?php for ($i = 1; $i <= 10; $i++) {  echo $i; } ?>";
		initialize(str);

		List<ForStatement> statements = getAllOfType(program, ForStatement.class);
		assertTrue("Unexpected list size.", statements.size() == 1);
		statements.get(0).conditions().clear();
		rewrite();
		checkResult("<?php for ($i = 1;; $i++) {  echo $i; } ?>");
	}

	@Test
	public void forStatementRemoveUpdaters() throws Exception {
		String str = "<?php for ($i = 1; $i <= 10; $i++) {  echo $i; } ?>";
		initialize(str);

		List<ForStatement> statements = getAllOfType(program, ForStatement.class);
		assertTrue("Unexpected list size.", statements.size() == 1);
		statements.get(0).updaters().get(0).delete();
		rewrite();
		checkResult("<?php for ($i = 1; $i <= 10;) {  echo $i; } ?>");
	}

	@Test
	public void forEachStatementAddKey() throws Exception {
		String str = "<?php foreach ($arr as &$value) { $value = $value * 2; } ?>";
		initialize(str);

		List<ForEachStatement> statements = getAllOfType(program, ForEachStatement.class);
		assertTrue("Unexpected list size.", statements.size() == 1);
		statements.get(0).setExpression(ast.newVariable("foo"));
		statements.get(0).setKey(ast.newVariable("bar"));
		rewrite();
		checkResult("<?php foreach ($foo as $bar=>&$value) { $value = $value * 2; } ?>");
	}

	@Test
	public void forEachStatementChangeKeyAndValue() throws Exception {
		String str = "<?php foreach ($arr as $foo=>&$value) { $value = $value * 2; } ?>";
		initialize(str);

		List<ForEachStatement> statements = getAllOfType(program, ForEachStatement.class);
		assertTrue("Unexpected list size.", statements.size() == 1);

		statements.get(0).setKey(ast.newVariable("bar"));
		Reference ref = ast.newReference(ast.newVariable("val"));
		statements.get(0).setValue(ref);
		rewrite();
		checkResult("<?php foreach ($arr as $bar=>&$val) { $value = $value * 2; } ?>");
	}

	@Test
	public void forEachStatementRemoveKey() throws Exception {
		String str = "<?php foreach ($foo as $bar=>&$value) { $value = $value * 2; } ?>";
		initialize(str);

		List<ForEachStatement> statements = getAllOfType(program, ForEachStatement.class);
		assertTrue("Unexpected list size.", statements.size() == 1);
		statements.get(0).getKey().delete();
		rewrite();
		checkResult("<?php foreach ($foo as &$value) { $value = $value * 2; } ?>");
	}

	@Test
	public void tryCatchStatement() throws Exception {
		String str = "<?php try { $error = 'Always throw this error'; } catch (Exception $e) { echo ''; }  ?>";
		initialize(str);

		List<TryStatement> statements = getAllOfType(program, TryStatement.class);
		assertTrue("Unexpected list size.", statements.size() == 1);
		Block newBlock = ast.newBlock();
		newBlock.statements().add(ast.newEchoStatement(ast.newScalar("'Hello'")));
		statements.get(0).catchClauses()
				.add(ast.newCatchClause(ast.newIdentifier("Boobo"), ast.newVariable("b"), newBlock));
		rewrite();
		checkResult(
				"<?php try { $error = 'Always throw this error'; } catch (Exception $e) { echo ''; }catch (Boobo $b) {\necho 'Hello';\n}\n  ?>");
	}

	@Test
	public void tryMultiCatchStatement() throws Exception {
		String str = "<?php try { $error = 'Always throw this error'; } catch (Exception $e) { echo ''; } catch (AnotherException $ea) { echo ''; }  ?>";
		initialize(str);

		List<TryStatement> statements = getAllOfType(program, TryStatement.class);
		assertTrue("Unexpected list size.", statements.size() == 1);
		statements.get(0).catchClauses().get(1).delete();
		rewrite();
		checkResult("<?php try { $error = 'Always throw this error'; } catch (Exception $e) { echo ''; }  ?>");
	}

	@Test
	public void globalStatementSimple() throws Exception {
		String str = "<?php global $a; ?>";
		initialize(str);

		List<GlobalStatement> statements = getAllOfType(program, GlobalStatement.class);
		statements.get(0).variables().add(ast.newVariable("b"));
		rewrite();
		checkResult("<?php global $a, $b; ?>");
	}

	@Test
	public void globalStatementReflection() throws Exception {
		String str = "<?php global $$a; ?>";
		initialize(str);

		List<GlobalStatement> statements = getAllOfType(program, GlobalStatement.class);
		statements.get(0).variables().add(ast.newReflectionVariable(ast.newVariable("b")));
		rewrite();
		checkResult("<?php global $$a, $$b; ?>");
	}

	@Test
	public void staticSimple() throws Exception {
		String str = "<?php static $a;?>";
		initialize(str);

		List<StaticStatement> statements = getAllOfType(program, StaticStatement.class);
		statements.get(0).expressions()
				.add(ast.newAssignment(ast.newVariable("b"), Assignment.OP_EQUAL, ast.newScalar("8")));
		rewrite();
		checkResult("<?php static $a, $b=8;?>");
	}

	@Test
	public void inLineHtml() throws Exception {
		String str = "<html> </html>";
		initialize(str);

		List<InLineHtml> inline = getAllOfType(program, InLineHtml.class);
		assertTrue("Unexpected list size.", inline.size() == 1);
		rewrite();
		checkResult("<html> </html>");
	}

	@Test
	public void fieldDeclaration() throws Exception {
		String str = "<?php class A { public $a = 3; final private static $var; }?>";
		initialize(str);

		List<FieldsDeclaration> declarations = getAllOfType(program, FieldsDeclaration.class);
		assertTrue("Unexpected list size.", declarations.size() == 2);
		declarations.get(0).fields().add(ast.newSingleFieldDeclaration(ast.newVariable("b"), ast.newScalar("4")));
		declarations.get(0).fields().get(0).getValue().delete();
		declarations.get(1).setModifier(Modifiers.AccProtected | Modifiers.AccFinal);
		rewrite();
		checkResult("<?php class A { public $a, $b = 4; protected final $var; }?>");
	}

	@Test
	public void methodDeclaration() throws Exception {
		String str = "<?php class A { public function foo(int $a){} }?> ";
		initialize(str);

		List<MethodDeclaration> declarations = getAllOfType(program, MethodDeclaration.class);
		assertTrue("Unexpected list size.", declarations.size() == 1);
		declarations.get(0).setModifier(Modifiers.AccProtected | Modifiers.AccAbstract);
		declarations.get(0).getFunction().setFunctionName(ast.newIdentifier("bar"));
		rewrite();
		checkResult("<?php class A { protected abstract function bar(int $a){} }?> ");
	}

	@Test
	public void comment() throws Exception {
		String str = "<?php\n class A { \n\tpublic function foo(int $a){}\n }?> ";
		initialize(str);

		List<Block> blocks = getAllOfType(program, Block.class);
		assertTrue("Unexpected list size.", blocks.size() == 2);
		AST astRoot = program.getAST();
		ASTRewrite rewrite = ASTRewrite.create(astRoot);
		Block block = blocks.get(0);
		ListRewrite listRewrite = rewrite.getListRewrite(block, Block.STATEMENTS_PROPERTY);
		ASTNode placeHolder = rewrite.createStringPlaceholder("//mycomment", ASTNode.COMMENT);
		listRewrite.insertFirst(placeHolder, null);

		TextEdit textEdits = rewrite.rewriteAST(document, null);
		textEdits.apply(document);
		checkResult("<?php\n class A { \n	//mycomment\n	public function foo(int $a){}\n }?> ");
	}

	@Test
	public void exception() throws Exception {
		String str = "<?php\n function A() { throw new Exception(); }";
		initialize(str);
		List<ThrowStatement> allthrows = getAllOfType(program, ThrowStatement.class);
		ASTRewrite rewrite = ASTRewrite.create(program.getAST());

		rewrite.replace(allthrows.get(0),
				rewrite.createGroupNode(new ASTNode[] { allthrows.get(0), program.getAST().newBreakStatement() }),
				null);
		rewrite.rewriteAST(document, null).apply(document);

		checkResult("<?php\n function A() { throw new Exception(); break; }");

	}

	// //////////////////////// Utility methods //////////////////////////
	/**
	 * Set the content into the document and initialize the parser, the program
	 * and the ast.
	 */
	private void initialize(String content, PHPVersion phpVersion) throws Exception {
		document = new Document(content);

		ASTParser parser = ASTParser.newParser(phpVersion, ProjectOptions.useShortTags((IProject) null));
		parser.setSource(document.get().toCharArray());
		program = parser.createAST(new NullProgressMonitor());
		ast = program.getAST();

		program.recordModifications();
	}

	protected final void initialize(String content) throws Exception {
		initialize(content, getPHPVersion());
	}

	protected PHPVersion getPHPVersion() {
		return PHPVersion.PHP5;
	}

	protected final void rewrite() throws Exception {
		TextEdit edits = program.rewrite(document, null);
		edits.apply(document);
	}

	protected final void checkResult(String expected) {
		String actual = document.get();
		String diff = TestUtils.compareContentsIgnoreWhitespace(expected, actual);
		if (diff != null) {
			fail(diff);
		}
	}

	public <T extends ASTNode> List<T> getAllOfType(Program program, final String className) {
		final List<T> list = new ArrayList<T>();
		program.accept(new ApplyAll() {
			@SuppressWarnings("unchecked")
			protected boolean apply(ASTNode node) {
				if (node.getClass().getName().equals(className)) {
					list.add((T) node);
				}
				return true;
			}
		});
		return list;
	}

	public <T extends ASTNode> List<T> getAllOfType(Program program, final Class<T> nodeClass) {
		final List<T> list = new ArrayList<T>();
		program.accept(new ApplyAll() {
			@SuppressWarnings("unchecked")
			protected boolean apply(ASTNode node) {
				if (node.getClass() == nodeClass) {
					list.add((T) node);
				}
				return true;
			}
		});
		return list;
	}

}
