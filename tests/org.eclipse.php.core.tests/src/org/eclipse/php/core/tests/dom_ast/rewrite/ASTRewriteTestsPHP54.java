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
package org.eclipse.php.core.tests.dom_ast.rewrite;

import java.util.List;

import junit.framework.Assert;
import junit.framework.TestSuite;

import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.ast.nodes.ArrayElement;
import org.eclipse.php.internal.core.ast.nodes.ClassDeclaration;
import org.eclipse.php.internal.core.ast.nodes.FieldsDeclaration;
import org.eclipse.php.internal.core.ast.nodes.FunctionDeclaration;
import org.eclipse.php.internal.core.ast.nodes.FunctionInvocation;
import org.eclipse.php.internal.core.ast.nodes.InterfaceDeclaration;
import org.eclipse.php.internal.core.ast.nodes.LambdaFunctionDeclaration;
import org.eclipse.php.internal.core.ast.nodes.MethodDeclaration;
import org.eclipse.php.internal.core.ast.nodes.NamespaceName;
import org.eclipse.php.internal.core.ast.nodes.Scalar;
import org.eclipse.php.internal.core.ast.nodes.StaticConstantAccess;
import org.eclipse.php.internal.core.ast.nodes.TraitAliasStatement;
import org.eclipse.php.internal.core.ast.nodes.TraitDeclaration;
import org.eclipse.php.internal.core.ast.nodes.TraitPrecedenceStatement;
import org.eclipse.php.internal.core.ast.nodes.TraitUseStatement;
import org.eclipse.php.internal.core.ast.nodes.Variable;

/**
 * AST rewrite test which tests the ASTRewriteAnalyzer implementation.
 * 
 * @author shalom
 */
public class ASTRewriteTestsPHP54 extends ASTRewriteTests {

	public ASTRewriteTestsPHP54(String name) {
		super(name);
	}

	public static TestSuite suite() {
		return new TestSuite(new Class[] { ASTRewriteTestsPHP54.class,
				NodeDeletionTestsPHP54.class, },
				ASTRewriteTestsPHP54.class.getName());
	}

	@Override
	protected PHPVersion getPHPVersion() {
		return PHPVersion.PHP5_4;
	}

	public void testClassConstant() throws Exception {
		String str = "<?php $a = MyClass::MY_CONST; ?>";
		initialize(str);

		List<StaticConstantAccess> staticConstants = getAllOfType(program,
				StaticConstantAccess.class);
		Assert.assertTrue("Unexpected list size.", staticConstants.size() == 1);
		((NamespaceName) staticConstants.get(0).getClassName()).segments()
				.get(0).setName("Foo");
		staticConstants.get(0).setConstant(ast.newIdentifier("BAR_CONST"));
		rewrite();
		checkResult("<?php $a = Foo::BAR_CONST; ?>");
	}

	public void testClassDeclarationReplaceSuper() throws Exception {
		String str = "<?php class MyClass extends Foo { } ?> ";
		initialize(str);

		List<ClassDeclaration> declarations = getAllOfType(program,
				ClassDeclaration.class);
		Assert.assertTrue("Unexpected list size.", declarations.size() == 1);
		((NamespaceName) declarations.get(0).getSuperClass()).segments().get(0)
				.setName("Bar");
		rewrite();
		checkResult("<?php class MyClass extends Bar { } ?> ");
	}

	public void testClassDeclarationRenameInterface() throws Exception {
		String str = "<?php class MyClass extends AAA implements Foo,Bar{ } ?> ";
		initialize(str);

		List<ClassDeclaration> declarations = getAllOfType(program,
				ClassDeclaration.class);
		Assert.assertTrue("Unexpected list size.", declarations.size() == 1);
		((NamespaceName) declarations.get(0).interfaces().get(1)).segments()
				.get(0).setName("BooBo");
		rewrite();
		checkResult("<?php class MyClass extends AAA implements Foo,BooBo{ } ?> ");
	}

	public void testInterfaceDeclarationRenameExtend() throws Exception {
		String str = "<?php interface MyInterface extends Foo, Bar{ const MY_CONSTANT = 3; public function myFunction($a); } ?> ";
		initialize(str);

		List<InterfaceDeclaration> declarations = getAllOfType(program,
				InterfaceDeclaration.class);
		Assert.assertTrue("Unexpected list size.", declarations.size() == 1);
		((NamespaceName) declarations.get(0).interfaces().get(0)).segments()
				.get(0).setName("Boobo");
		rewrite();
		checkResult("<?php interface MyInterface extends Boobo, Bar{ const MY_CONSTANT = 3; public function myFunction($a); } ?> ");
	}

	// FIXME should fix this case,the scalar's end is wrong!
	// public void testEmptyHeredoc() throws Exception {
	// String str = "<?php <<<Heredoc\nabc\nefg\nHeredoc;\n?>";
	// initialize(str);
	//
	// List<Quote> quotes = getAllOfType(program, Quote.class);
	// Assert.assertTrue("Unexpected list size.", quotes.size() == 1);
	// quotes.get(0).expressions().clear();
	// quotes.get(0).expressions().add(ast.newScalar("Hello World\n"));
	// rewrite();
	// checkResult("<?php <<<Heredoc\nHello World\nefg\nHeredoc;\n?>");
	// }

	public void testEmptyHeredoc() throws Exception {
		String str = "<?php <<<Heredoc\nHeredoc;\n?>";
		initialize(str);
		checkResult("<?php <<<Heredoc\nHeredoc;\n?>");

	}

	public void testTraitDeclarationSimple() throws Exception {
		String str = "<?php trait MyTrait { } ?> ";
		initialize(str);

		List<TraitDeclaration> declarations = getAllOfType(program,
				TraitDeclaration.class);
		Assert.assertTrue("Unexpected list size.", declarations.size() == 1);
		declarations.get(0).setName(ast.newIdentifier("Foo"));
		rewrite();
		checkResult("<?php trait Foo { } ?> ");
	}

	public void testTraitFieldDeclaration() throws Exception {
		String str = "<?php trait A { public $a = 3; final private static $var; }?>";
		initialize(str);

		List<FieldsDeclaration> declarations = getAllOfType(program,
				FieldsDeclaration.class);
		Assert.assertTrue("Unexpected list size.", declarations.size() == 2);
		declarations
				.get(0)
				.fields()
				.add(ast.newSingleFieldDeclaration(ast.newVariable("b"),
						ast.newScalar("4")));
		declarations.get(0).fields().get(0).getValue().delete();
		declarations.get(1).setModifier(
				Modifiers.AccProtected | Modifiers.AccFinal);
		rewrite();
		checkResult("<?php trait A { public $a, $b = 4; protected final $var; }?>");
	}

	public void testTraitFunctionDeclaration() throws Exception {
		String str = "<?php function foo() {} ?> ";
		initialize(str);

		List<FunctionDeclaration> declarations = getAllOfType(program,
				FunctionDeclaration.class);
		Assert.assertTrue("Unexpected list size.", declarations.size() == 1);
		declarations.get(0).setFunctionName(ast.newIdentifier("bar"));
		declarations
				.get(0)
				.formalParameters()
				.add(ast.newFormalParameter(ast.newIdentifier("int"),
						ast.newVariable("a"), null, false));
		rewrite();
		checkResult("<?php function bar(int $a) {} ?> ");
	}

	public void testFunctionDeclarationWithCallableParam2() throws Exception {
		String str = "<?php function foo($a, callable $b) {} ?> ";
		initialize(str);

		List<FunctionDeclaration> declarations = getAllOfType(program,
				FunctionDeclaration.class);
		Assert.assertTrue("Unexpected list size.", declarations.size() == 1);
		declarations.get(0).formalParameters().get(1)
				.setParameterType(ast.newIdentifier("string"));
		rewrite();
		checkResult("<?php function foo($a, string $b) {} ?> ");
	}

	public void testTraitMethodDeclaration() throws Exception {
		String str = "<?php trait A { public function foo(int $a){} }?> ";
		initialize(str);

		List<MethodDeclaration> declarations = getAllOfType(program,
				MethodDeclaration.class);
		Assert.assertTrue("Unexpected list size.", declarations.size() == 1);
		declarations.get(0).setModifier(
				Modifiers.AccProtected | Modifiers.AccAbstract);
		declarations.get(0).getFunction()
				.setFunctionName(ast.newIdentifier("bar"));
		rewrite();
		checkResult("<?php trait A { protected abstract function bar(int $a){} }?> ");
	}

	public void testArrayInitializer() throws Exception {
		String str = "<?php $f = [new Human('Gonzalo'), 'hello']; ?>";
		initialize(str);

		List<ArrayElement> arrayAccess = getAllOfType(program,
				ArrayElement.class);
		Assert.assertTrue("Unexpected list size.", arrayAccess.size() == 2);
		((Scalar) arrayAccess.get(1).getValue()).setStringValue("'world'");
		rewrite();
		checkResult("<?php $f = [new Human('Gonzalo'), 'world']; ?>");
	}

	public void testClassInitializer() throws Exception {
		String str = "<?php (new Human('Gonzalo'))->hello(); ?>";
		initialize(str);

		List<FunctionInvocation> arrayAccess = getAllOfType(program,
				FunctionInvocation.class);
		Assert.assertTrue("Unexpected list size.", arrayAccess.size() == 1);
		Variable name = ast.newVariable("world");
		name.setIsDollared(false);
		((FunctionInvocation) arrayAccess.get(0)).getFunctionName().setName(
				name);
		rewrite();
		checkResult("<?php (new Human('Gonzalo'))->world(); ?>");
	}

	public void testExpresionFunctionInvocation() throws Exception {
		String str = "<?php $human->{'hello'}(); ?>";
		initialize(str);

		List<FunctionInvocation> arrayAccess = getAllOfType(program,
				FunctionInvocation.class);
		Assert.assertTrue("Unexpected list size.", arrayAccess.size() == 1);
		((Variable) arrayAccess.get(0).getFunctionName().getName()).setName(ast
				.newScalar("'world'"));
		rewrite();
		checkResult("<?php $human->{'world'}(); ?>");
	}

	public void testStaticLambdaFunction() throws Exception {
		String str = "<?php $lambda = static function () { }; ?>";
		initialize(str);

		List<LambdaFunctionDeclaration> arrayAccess = getAllOfType(program,
				LambdaFunctionDeclaration.class);
		Assert.assertTrue("Unexpected list size.", arrayAccess.size() == 1);
	}

	public void testUseTrait1() throws Exception {
		String str = "<?php class Test { use Hello, World; } ?>";
		initialize(str);

		List<TraitUseStatement> arrayAccess = getAllOfType(program,
				TraitUseStatement.class);
		Assert.assertTrue("Unexpected list size.", arrayAccess.size() == 1);
		arrayAccess.get(0).getTraitList().get(0).segments().get(0)
				.setName("Hi");
		rewrite();
		checkResult("<?php class Test { use Hi, World; } ?>");
	}

	public void testUseTrait2() throws Exception {
		String str = "<?php class Aliased_Talker { use A, B {B::smallTalk insteadof A;\nA::bigTalk insteadof B;\nB::bigTalk as talk;\n}\n } ?>";
		initialize(str);

		List<TraitPrecedenceStatement> arrayAccess = getAllOfType(program,
				TraitPrecedenceStatement.class);
		Assert.assertTrue("Unexpected list size.", arrayAccess.size() == 2);
		arrayAccess.get(0).getPrecedence().getMethodReference().getClassName()
				.segments().get(0).setName("B1");
		arrayAccess.get(0).getPrecedence().getMethodReference()
				.setFunctionName(ast.newIdentifier("bigTalk"));

		List<TraitAliasStatement> aliasStatement = getAllOfType(program,
				TraitAliasStatement.class);
		Assert.assertTrue("Unexpected list size.", aliasStatement.size() == 1);
		aliasStatement.get(0).getAlias()
				.setFunctionName(ast.newIdentifier("talking"));

		rewrite();
		checkResult("<?php class Aliased_Talker { use A, B {B1::bigTalk insteadof A;\nA::bigTalk insteadof B;\nB::bigTalk as talking;\n}\n } ?>");
	}

	public void testUseTrait3() throws Exception {
		String str = "<?php class Test { use HelloWorld { sayHello as protected; } } ?>";
		initialize(str);

		List<TraitAliasStatement> arrayAccess = getAllOfType(program,
				TraitAliasStatement.class);
		Assert.assertTrue("Unexpected list size.", arrayAccess.size() == 1);
		arrayAccess.get(0).getAlias().setModifier(Modifiers.AccPublic);
		rewrite();
		checkResult("<?php class Test { use HelloWorld { sayHello as public; } } ?>");
	}

	public void testUseTrait4() throws Exception {
		String str = "<?php class Test { use HelloWorld { sayHello as private myPrivateHello; } } ?>";
		initialize(str);

		List<TraitAliasStatement> arrayAccess = getAllOfType(program,
				TraitAliasStatement.class);
		Assert.assertTrue("Unexpected list size.", arrayAccess.size() == 1);
		arrayAccess.get(0).getAlias().setModifier(Modifiers.AccPublic);
		arrayAccess.get(0).getAlias()
				.setFunctionName(ast.newIdentifier("myPublicHello"));
		rewrite();
		checkResult("<?php class Test { use HelloWorld { sayHello as public myPublicHello; } } ?>");
	}

}
