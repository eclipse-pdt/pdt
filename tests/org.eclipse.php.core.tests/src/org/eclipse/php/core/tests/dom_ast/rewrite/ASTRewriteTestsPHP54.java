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

import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.ast.nodes.ArrayElement;
import org.eclipse.php.core.ast.nodes.ClassDeclaration;
import org.eclipse.php.core.ast.nodes.FieldsDeclaration;
import org.eclipse.php.core.ast.nodes.FunctionDeclaration;
import org.eclipse.php.core.ast.nodes.FunctionInvocation;
import org.eclipse.php.core.ast.nodes.InterfaceDeclaration;
import org.eclipse.php.core.ast.nodes.LambdaFunctionDeclaration;
import org.eclipse.php.core.ast.nodes.MethodDeclaration;
import org.eclipse.php.core.ast.nodes.NamespaceName;
import org.eclipse.php.core.ast.nodes.Scalar;
import org.eclipse.php.core.ast.nodes.StaticConstantAccess;
import org.eclipse.php.core.ast.nodes.TraitAliasStatement;
import org.eclipse.php.core.ast.nodes.TraitDeclaration;
import org.eclipse.php.core.ast.nodes.TraitPrecedenceStatement;
import org.eclipse.php.core.ast.nodes.TraitUseStatement;
import org.eclipse.php.core.ast.nodes.Variable;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

/**
 * AST rewrite test which tests the ASTRewriteAnalyzer implementation.
 * 
 * @author shalom
 */
public class ASTRewriteTestsPHP54 extends ASTRewriteTests {

	@RunWith(org.junit.runners.Suite.class)
	@SuiteClasses({ ASTRewriteTestsPHP54.class, NodeDeletionTests.class, ASTRewriteTestsPHP54.class })
	public static class Suite {

	}

	@Override
	protected PHPVersion getPHPVersion() {
		return PHPVersion.PHP5_4;
	}

	@Test
	public void classConstant() throws Exception {
		String str = "<?php $a = MyClass::MY_CONST; ?>";
		initialize(str);

		List<StaticConstantAccess> staticConstants = getAllOfType(program, StaticConstantAccess.class);
		assertTrue("Unexpected list size.", staticConstants.size() == 1);
		((NamespaceName) staticConstants.get(0).getClassName()).segments().get(0).setName("Foo");
		staticConstants.get(0).setConstant(ast.newIdentifier("BAR_CONST"));
		rewrite();
		checkResult("<?php $a = Foo::BAR_CONST; ?>");
	}

	@Test
	public void classDeclarationReplaceSuper() throws Exception {
		String str = "<?php class MyClass extends Foo { } ?> ";
		initialize(str);

		List<ClassDeclaration> declarations = getAllOfType(program, ClassDeclaration.class);
		assertTrue("Unexpected list size.", declarations.size() == 1);
		((NamespaceName) declarations.get(0).getSuperClass()).segments().get(0).setName("Bar");
		rewrite();
		checkResult("<?php class MyClass extends Bar { } ?> ");
	}

	@Test
	public void classDeclarationRenameInterface() throws Exception {
		String str = "<?php class MyClass extends AAA implements Foo,Bar{ } ?> ";
		initialize(str);

		List<ClassDeclaration> declarations = getAllOfType(program, ClassDeclaration.class);
		assertTrue("Unexpected list size.", declarations.size() == 1);
		((NamespaceName) declarations.get(0).interfaces().get(1)).segments().get(0).setName("BooBo");
		rewrite();
		checkResult("<?php class MyClass extends AAA implements Foo,BooBo{ } ?> ");
	}

	@Test
	public void interfaceDeclarationRenameExtend() throws Exception {
		String str = "<?php interface MyInterface extends Foo, Bar{ const MY_CONSTANT = 3; public function myFunction($a); } ?> ";
		initialize(str);

		List<InterfaceDeclaration> declarations = getAllOfType(program, InterfaceDeclaration.class);
		assertTrue("Unexpected list size.", declarations.size() == 1);
		((NamespaceName) declarations.get(0).interfaces().get(0)).segments().get(0).setName("Boobo");
		rewrite();
		checkResult(
				"<?php interface MyInterface extends Boobo, Bar{ const MY_CONSTANT = 3; public function myFunction($a); } ?> ");
	}

	// FIXME should fix this case,the scalar's end is wrong!
	// @Test public void emptyHeredoc() throws Exception {
	// String str = "<?php <<<Heredoc\nabc\nefg\nHeredoc;\n?>";
	// initialize(str);
	//
	// List<Quote> quotes = getAllOfType(program, Quote.class);
	// assertTrue("Unexpected list size.", quotes.size() == 1);
	// quotes.get(0).expressions().clear();
	// quotes.get(0).expressions().add(ast.newScalar("Hello World\n"));
	// rewrite();
	// checkResult("<?php <<<Heredoc\nHello World\nefg\nHeredoc;\n?>");
	// }

	@Test
	public void emptyHeredoc() throws Exception {
		String str = "<?php <<<Heredoc\nHeredoc;\n?>";
		initialize(str);
		checkResult("<?php <<<Heredoc\nHeredoc;\n?>");

	}

	@Test
	public void traitDeclarationSimple() throws Exception {
		String str = "<?php trait MyTrait { } ?> ";
		initialize(str);

		List<TraitDeclaration> declarations = getAllOfType(program, TraitDeclaration.class);
		assertTrue("Unexpected list size.", declarations.size() == 1);
		declarations.get(0).setName(ast.newIdentifier("Foo"));
		rewrite();
		checkResult("<?php trait Foo { } ?> ");
	}

	@Test
	public void traitFieldDeclaration() throws Exception {
		String str = "<?php trait A { public $a = 3; final private static $var; }?>";
		initialize(str);

		List<FieldsDeclaration> declarations = getAllOfType(program, FieldsDeclaration.class);
		assertTrue("Unexpected list size.", declarations.size() == 2);
		declarations.get(0).fields().add(ast.newSingleFieldDeclaration(ast.newVariable("b"), ast.newScalar("4")));
		declarations.get(0).fields().get(0).getValue().delete();
		declarations.get(1).setModifier(Modifiers.AccProtected | Modifiers.AccFinal);
		rewrite();
		checkResult("<?php trait A { public $a, $b = 4; protected final $var; }?>");
	}

	@Test
	public void traitFunctionDeclaration() throws Exception {
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
	public void functionDeclarationWithCallableParam2() throws Exception {
		String str = "<?php function foo($a, callable $b) {} ?> ";
		initialize(str);

		List<FunctionDeclaration> declarations = getAllOfType(program, FunctionDeclaration.class);
		assertTrue("Unexpected list size.", declarations.size() == 1);
		declarations.get(0).formalParameters().get(1).setParameterType(ast.newIdentifier("string"));
		rewrite();
		checkResult("<?php function foo($a, string $b) {} ?> ");
	}

	@Test
	public void traitMethodDeclaration() throws Exception {
		String str = "<?php trait A { public function foo(int $a){} }?> ";
		initialize(str);

		List<MethodDeclaration> declarations = getAllOfType(program, MethodDeclaration.class);
		assertTrue("Unexpected list size.", declarations.size() == 1);
		declarations.get(0).setModifier(Modifiers.AccProtected | Modifiers.AccAbstract);
		declarations.get(0).getFunction().setFunctionName(ast.newIdentifier("bar"));
		rewrite();
		checkResult("<?php trait A { protected abstract function bar(int $a){} }?> ");
	}

	@Test
	public void arrayInitializer() throws Exception {
		String str = "<?php $f = [new Human('Gonzalo'), 'hello']; ?>";
		initialize(str);

		List<ArrayElement> arrayAccess = getAllOfType(program, ArrayElement.class);
		assertTrue("Unexpected list size.", arrayAccess.size() == 2);
		((Scalar) arrayAccess.get(1).getValue()).setStringValue("'world'");
		rewrite();
		checkResult("<?php $f = [new Human('Gonzalo'), 'world']; ?>");
	}

	@Test
	public void classInitializer() throws Exception {
		String str = "<?php (new Human('Gonzalo'))->hello(); ?>";
		initialize(str);

		List<FunctionInvocation> arrayAccess = getAllOfType(program, FunctionInvocation.class);
		assertTrue("Unexpected list size.", arrayAccess.size() == 1);
		Variable name = ast.newVariable("world");
		name.setIsDollared(false);
		((FunctionInvocation) arrayAccess.get(0)).getFunctionName().setName(name);
		rewrite();
		checkResult("<?php (new Human('Gonzalo'))->world(); ?>");
	}

	@Test
	public void expresionFunctionInvocation() throws Exception {
		String str = "<?php $human->{'hello'}(); ?>";
		initialize(str);

		List<FunctionInvocation> arrayAccess = getAllOfType(program, FunctionInvocation.class);
		assertTrue("Unexpected list size.", arrayAccess.size() == 1);
		((Variable) arrayAccess.get(0).getFunctionName().getName()).setName(ast.newScalar("'world'"));
		rewrite();
		checkResult("<?php $human->{'world'}(); ?>");
	}

	@Test
	public void staticLambdaFunction() throws Exception {
		String str = "<?php $lambda = static function () { }; ?>";
		initialize(str);

		List<LambdaFunctionDeclaration> arrayAccess = getAllOfType(program, LambdaFunctionDeclaration.class);
		assertTrue("Unexpected list size.", arrayAccess.size() == 1);
	}

	@Test
	public void useTrait1() throws Exception {
		String str = "<?php class Test { use Hello, World; } ?>";
		initialize(str);

		List<TraitUseStatement> arrayAccess = getAllOfType(program, TraitUseStatement.class);
		assertTrue("Unexpected list size.", arrayAccess.size() == 1);
		arrayAccess.get(0).getTraitList().get(0).segments().get(0).setName("Hi");
		rewrite();
		checkResult("<?php class Test { use Hi, World; } ?>");
	}

	@Test
	public void useTrait2() throws Exception {
		String str = "<?php class Aliased_Talker { use A, B {B::smallTalk insteadof A;\nA::bigTalk insteadof B;\nB::bigTalk as talk;\n}\n } ?>";
		initialize(str);

		List<TraitPrecedenceStatement> arrayAccess = getAllOfType(program, TraitPrecedenceStatement.class);
		assertTrue("Unexpected list size.", arrayAccess.size() == 2);
		arrayAccess.get(0).getPrecedence().getMethodReference().getClassName().segments().get(0).setName("B1");
		arrayAccess.get(0).getPrecedence().getMethodReference().setFunctionName(ast.newIdentifier("bigTalk"));

		List<TraitAliasStatement> aliasStatement = getAllOfType(program, TraitAliasStatement.class);
		assertTrue("Unexpected list size.", aliasStatement.size() == 1);
		aliasStatement.get(0).getAlias().setFunctionName(ast.newIdentifier("talking"));

		rewrite();
		checkResult(
				"<?php class Aliased_Talker { use A, B {B1::bigTalk insteadof A;\nA::bigTalk insteadof B;\nB::bigTalk as talking;\n}\n } ?>");
	}

	@Test
	public void useTrait3() throws Exception {
		String str = "<?php class Test { use HelloWorld { sayHello as protected; } } ?>";
		initialize(str);

		List<TraitAliasStatement> arrayAccess = getAllOfType(program, TraitAliasStatement.class);
		assertTrue("Unexpected list size.", arrayAccess.size() == 1);
		arrayAccess.get(0).getAlias().setModifier(Modifiers.AccPublic);
		rewrite();
		checkResult("<?php class Test { use HelloWorld { sayHello as public; } } ?>");
	}

	@Test
	public void useTrait4() throws Exception {
		String str = "<?php class Test { use HelloWorld { sayHello as private myPrivateHello; } } ?>";
		initialize(str);

		List<TraitAliasStatement> arrayAccess = getAllOfType(program, TraitAliasStatement.class);
		assertTrue("Unexpected list size.", arrayAccess.size() == 1);
		arrayAccess.get(0).getAlias().setModifier(Modifiers.AccPublic);
		arrayAccess.get(0).getAlias().setFunctionName(ast.newIdentifier("myPublicHello"));
		rewrite();
		checkResult("<?php class Test { use HelloWorld { sayHello as public myPublicHello; } } ?>");
	}

}
