/*******************************************************************************
 * Copyright (c) 2019 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
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
import org.eclipse.php.core.ast.nodes.ArrayCreation;
import org.eclipse.php.core.ast.nodes.ArraySpreadElement;
import org.eclipse.php.core.ast.nodes.ArrowFunctionDeclaration;
import org.eclipse.php.core.ast.nodes.FieldsDeclaration;
import org.eclipse.php.core.ast.nodes.LambdaFunctionDeclaration;
import org.eclipse.php.core.ast.nodes.Scalar;
import org.eclipse.php.core.ast.nodes.Variable;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

/**
 * AST rewrite test which tests the ASTRewriteAnalyzer implementation.
 * 
 */
public class ASTRewriteTestsPHP74 extends ASTRewriteTestsPHP71 {

	@RunWith(org.junit.runners.Suite.class)
	@SuiteClasses({ ASTRewriteTestsPHP74.class, NodeDeletionTestsPHP74.class })
	public static class Suite {
	}

	@Override
	protected PHPVersion getPHPVersion() {
		return PHPVersion.PHP7_4;
	}

	@Test
	public void staticLambdaFunction() throws Exception {
		String str = "<?php $lambda = static function ($param) { $k = 'value'; }; ?>";
		initialize(str);

		List<LambdaFunctionDeclaration> lambdaFunctions = getAllOfType(program, LambdaFunctionDeclaration.class);
		assertTrue("Unexpected list size.", lambdaFunctions.size() == 1);
		lambdaFunctions.get(0).setStatic(false);

		rewrite();
		checkResult("<?php $lambda = function ($param) { $k = 'value'; }; ?>");
	}

	@Test
	public void staticLambdaFunction2() throws Exception {
		String str = "<?php $lambda = function ($param) { $k = 'value'; }; ?>";
		initialize(str);

		List<LambdaFunctionDeclaration> lambdaFunctions = getAllOfType(program, LambdaFunctionDeclaration.class);
		assertTrue("Unexpected list size.", lambdaFunctions.size() == 1);
		lambdaFunctions.get(0).setStatic(true);

		rewrite();
		checkResult("<?php $lambda = static function ($param) { $k = 'value'; }; ?>");
	}

	@Test
	public void staticLambdaFunction3() throws Exception {
		String str = "<?php $lambda = static function ($param) { $k = 'value'; }; ?>";
		initialize(str);

		List<LambdaFunctionDeclaration> lambdaFunctions = getAllOfType(program, LambdaFunctionDeclaration.class);
		assertTrue("Unexpected list size.", lambdaFunctions.size() == 1);
		lambdaFunctions.get(0).setStatic(true);

		rewrite();
		checkResult("<?php $lambda = static function ($param) { $k = 'value'; }; ?>");
	}

	@Test
	public void staticLambdaFunction4() throws Exception {
		String str = "<?php $lambda = function ($param) { $k = 'value'; }; ?>";
		initialize(str);

		List<LambdaFunctionDeclaration> lambdaFunctions = getAllOfType(program, LambdaFunctionDeclaration.class);
		assertTrue("Unexpected list size.", lambdaFunctions.size() == 1);
		lambdaFunctions.get(0).setStatic(false);

		rewrite();
		checkResult("<?php $lambda = function ($param) { $k = 'value'; }; ?>");
	}

	@Test
	public void staticLambdaFunction5() throws Exception {
		String str = "<?php $lambda = static function ($param) { $k = 'value'; }; ?>";
		initialize(str);

		List<LambdaFunctionDeclaration> lambdaFunctions = getAllOfType(program, LambdaFunctionDeclaration.class);
		assertTrue("Unexpected list size.", lambdaFunctions.size() == 1);
		lambdaFunctions.get(0).setStatic(true);
		lambdaFunctions.get(0).setIsReference(true);

		rewrite();
		checkResult("<?php $lambda = static function & ($param) { $k = 'value'; }; ?>");
	}

	@Test
	public void staticLambdaFunction5b() throws Exception {
		String str = "<?php $lambda = static function & ($param) { $k = 'value'; }; ?>";
		initialize(str);

		List<LambdaFunctionDeclaration> lambdaFunctions = getAllOfType(program, LambdaFunctionDeclaration.class);
		assertTrue("Unexpected list size.", lambdaFunctions.size() == 1);
		lambdaFunctions.get(0).setStatic(true);
		lambdaFunctions.get(0).setIsReference(true);

		rewrite();
		checkResult("<?php $lambda = static function & ($param) { $k = 'value'; }; ?>");
	}

	@Test
	public void staticLambdaFunction6() throws Exception {
		String str = "<?php $lambda = static function & ($param) { $k = 'value'; }; ?>";
		initialize(str);

		List<LambdaFunctionDeclaration> lambdaFunctions = getAllOfType(program, LambdaFunctionDeclaration.class);
		assertTrue("Unexpected list size.", lambdaFunctions.size() == 1);
		lambdaFunctions.get(0).setStatic(true);
		lambdaFunctions.get(0).setIsReference(false);

		rewrite();
		checkResult("<?php $lambda = static function ($param) { $k = 'value'; }; ?>");
	}

	public void staticLambdaFunction6b() throws Exception {
		String str = "<?php $lambda = static function ($param) { $k = 'value'; }; ?>";
		initialize(str);

		List<LambdaFunctionDeclaration> lambdaFunctions = getAllOfType(program, LambdaFunctionDeclaration.class);
		assertTrue("Unexpected list size.", lambdaFunctions.size() == 1);
		lambdaFunctions.get(0).setStatic(true);
		lambdaFunctions.get(0).setIsReference(false);

		rewrite();
		checkResult("<?php $lambda = static function ($param) { $k = 'value'; }; ?>");
	}

	@Test
	public void staticLambdaFunction7() throws Exception {
		String str = "<?php $lambda = function & ($param) { $k = 'value'; }; ?>";
		initialize(str);

		List<LambdaFunctionDeclaration> lambdaFunctions = getAllOfType(program, LambdaFunctionDeclaration.class);
		assertTrue("Unexpected list size.", lambdaFunctions.size() == 1);
		lambdaFunctions.get(0).setStatic(false);
		lambdaFunctions.get(0).setIsReference(false);

		rewrite();
		checkResult("<?php $lambda = function ($param) { $k = 'value'; }; ?>");
	}

	@Test
	public void staticLambdaFunction7b() throws Exception {
		String str = "<?php $lambda = function ($param) { $k = 'value'; }; ?>";
		initialize(str);

		List<LambdaFunctionDeclaration> lambdaFunctions = getAllOfType(program, LambdaFunctionDeclaration.class);
		assertTrue("Unexpected list size.", lambdaFunctions.size() == 1);
		lambdaFunctions.get(0).setStatic(false);
		lambdaFunctions.get(0).setIsReference(false);

		rewrite();
		checkResult("<?php $lambda = function ($param) { $k = 'value'; }; ?>");
	}

	@Test
	public void staticLambdaFunction8() throws Exception {
		String str = "<?php $lambda = function ($param) { $k = 'value'; }; ?>";
		initialize(str);

		List<LambdaFunctionDeclaration> lambdaFunctions = getAllOfType(program, LambdaFunctionDeclaration.class);
		assertTrue("Unexpected list size.", lambdaFunctions.size() == 1);
		lambdaFunctions.get(0).setStatic(false);
		lambdaFunctions.get(0).setIsReference(true);

		rewrite();
		checkResult("<?php $lambda = function & ($param) { $k = 'value'; }; ?>");
	}

	@Test
	public void staticLambdaFunction8b() throws Exception {
		String str = "<?php $lambda = function & ($param) { $k = 'value'; }; ?>";
		initialize(str);

		List<LambdaFunctionDeclaration> lambdaFunctions = getAllOfType(program, LambdaFunctionDeclaration.class);
		assertTrue("Unexpected list size.", lambdaFunctions.size() == 1);
		lambdaFunctions.get(0).setStatic(false);
		lambdaFunctions.get(0).setIsReference(true);

		rewrite();
		checkResult("<?php $lambda = function & ($param) { $k = 'value'; }; ?>");
	}

	@Test
	public void staticArrowFunction() throws Exception {
		String str = "<?php $arrow = static fn ($param) => $k = 'value'; ?>";
		initialize(str);

		List<ArrowFunctionDeclaration> arrowFunctions = getAllOfType(program, ArrowFunctionDeclaration.class);
		assertTrue("Unexpected list size.", arrowFunctions.size() == 1);
		arrowFunctions.get(0).setStatic(false);

		rewrite();
		checkResult("<?php $arrow = fn ($param) => $k = 'value'; ?>");
	}

	@Test
	public void staticArrowFunction2() throws Exception {
		String str = "<?php $arrow = fn ($param) => $k = 'value'; ?>";
		initialize(str);

		List<ArrowFunctionDeclaration> arrowFunctions = getAllOfType(program, ArrowFunctionDeclaration.class);
		assertTrue("Unexpected list size.", arrowFunctions.size() == 1);
		arrowFunctions.get(0).setStatic(true);

		rewrite();
		checkResult("<?php $arrow = static fn ($param) => $k = 'value'; ?>");
	}

	@Test
	public void staticArrowFunction3() throws Exception {
		String str = "<?php $arrow = static fn ($param) => $k = 'value'; ?>";
		initialize(str);

		List<ArrowFunctionDeclaration> arrowFunctions = getAllOfType(program, ArrowFunctionDeclaration.class);
		assertTrue("Unexpected list size.", arrowFunctions.size() == 1);
		arrowFunctions.get(0).setStatic(true);

		rewrite();
		checkResult("<?php $arrow = static fn ($param) => $k = 'value'; ?>");
	}

	@Test
	public void staticArrowFunction4() throws Exception {
		String str = "<?php $arrow = fn ($param) => $k = 'value'; ?>";
		initialize(str);

		List<ArrowFunctionDeclaration> arrowFunctions = getAllOfType(program, ArrowFunctionDeclaration.class);
		assertTrue("Unexpected list size.", arrowFunctions.size() == 1);
		arrowFunctions.get(0).setStatic(false);

		rewrite();
		checkResult("<?php $arrow = fn ($param) => $k = 'value'; ?>");
	}

	@Test
	public void staticArrowFunction5() throws Exception {
		String str = "<?php $arrow = static fn ($param) => $k = 'value'; ?>";
		initialize(str);

		List<ArrowFunctionDeclaration> arrowFunctions = getAllOfType(program, ArrowFunctionDeclaration.class);
		assertTrue("Unexpected list size.", arrowFunctions.size() == 1);
		arrowFunctions.get(0).setStatic(true);
		arrowFunctions.get(0).setIsReference(true);

		rewrite();
		checkResult("<?php $arrow = static fn & ($param) => $k = 'value'; ?>");
	}

	@Test
	public void staticArrowFunction5b() throws Exception {
		String str = "<?php $arrow = static fn & ($param) => $k = 'value'; ?>";
		initialize(str);

		List<ArrowFunctionDeclaration> arrowFunctions = getAllOfType(program, ArrowFunctionDeclaration.class);
		assertTrue("Unexpected list size.", arrowFunctions.size() == 1);
		arrowFunctions.get(0).setStatic(true);
		arrowFunctions.get(0).setIsReference(true);

		rewrite();
		checkResult("<?php $arrow = static fn & ($param) => $k = 'value'; ?>");
	}

	@Test
	public void staticArrowFunction6() throws Exception {
		String str = "<?php $arrow = static fn & ($param) => $k = 'value'; ?>";
		initialize(str);

		List<ArrowFunctionDeclaration> arrowFunctions = getAllOfType(program, ArrowFunctionDeclaration.class);
		assertTrue("Unexpected list size.", arrowFunctions.size() == 1);
		arrowFunctions.get(0).setStatic(true);
		arrowFunctions.get(0).setIsReference(false);

		rewrite();
		checkResult("<?php $arrow = static fn ($param) => $k = 'value'; ?>");
	}

	public void staticArrowFunction6b() throws Exception {
		String str = "<?php $arrow = static fn ($param) => $k = 'value'; ?>";
		initialize(str);

		List<ArrowFunctionDeclaration> arrowFunctions = getAllOfType(program, ArrowFunctionDeclaration.class);
		assertTrue("Unexpected list size.", arrowFunctions.size() == 1);
		arrowFunctions.get(0).setStatic(true);
		arrowFunctions.get(0).setIsReference(false);

		rewrite();
		checkResult("<?php $arrow = static fn ($param) => $k = 'value'; ?>");
	}

	@Test
	public void staticArrowFunction7() throws Exception {
		String str = "<?php $arrow = fn & ($param) => $k = 'value'; ?>";
		initialize(str);

		List<ArrowFunctionDeclaration> arrowFunctions = getAllOfType(program, ArrowFunctionDeclaration.class);
		assertTrue("Unexpected list size.", arrowFunctions.size() == 1);
		arrowFunctions.get(0).setStatic(false);
		arrowFunctions.get(0).setIsReference(false);

		rewrite();
		checkResult("<?php $arrow = fn ($param) => $k = 'value'; ?>");
	}

	@Test
	public void staticArrowFunction7b() throws Exception {
		String str = "<?php $arrow = fn ($param) => $k = 'value'; ?>";
		initialize(str);

		List<ArrowFunctionDeclaration> arrowFunctions = getAllOfType(program, ArrowFunctionDeclaration.class);
		assertTrue("Unexpected list size.", arrowFunctions.size() == 1);
		arrowFunctions.get(0).setStatic(false);
		arrowFunctions.get(0).setIsReference(false);

		rewrite();
		checkResult("<?php $arrow = fn ($param) => $k = 'value'; ?>");
	}

	@Test
	public void staticArrowFunction8() throws Exception {
		String str = "<?php $arrow = fn ($param) => $k = 'value'; ?>";
		initialize(str);

		List<ArrowFunctionDeclaration> arrowFunctions = getAllOfType(program, ArrowFunctionDeclaration.class);
		assertTrue("Unexpected list size.", arrowFunctions.size() == 1);
		arrowFunctions.get(0).setStatic(false);
		arrowFunctions.get(0).setIsReference(true);

		rewrite();
		checkResult("<?php $arrow = fn & ($param) => $k = 'value'; ?>");
	}

	@Test
	public void staticArrowFunction8b() throws Exception {
		String str = "<?php $arrow = fn & ($param) => $k = 'value'; ?>";
		initialize(str);

		List<ArrowFunctionDeclaration> arrowFunctions = getAllOfType(program, ArrowFunctionDeclaration.class);
		assertTrue("Unexpected list size.", arrowFunctions.size() == 1);
		arrowFunctions.get(0).setStatic(false);
		arrowFunctions.get(0).setIsReference(true);

		rewrite();
		checkResult("<?php $arrow = fn & ($param) => $k = 'value'; ?>");
	}

	@Test
	public void arraySpreadElement() throws Exception {
		String str = "<?php [...$arr1]; ?>";
		initialize(str);

		List<ArraySpreadElement> arraySpreadElements = getAllOfType(program, ArraySpreadElement.class);
		assertTrue("Unexpected list size.", arraySpreadElements.size() == 1);
		((Variable) arraySpreadElements.get(0).getValue()).setName(ast.newIdentifier("b2"));

		rewrite();
		checkResult("<?php [...$b2]; ?>");
	}

	@Test
	public void arraySpreadElement2() throws Exception {
		String str = "<?php [...$arr1]; ?>";
		initialize(str);

		List<ArraySpreadElement> arraySpreadElements = getAllOfType(program, ArraySpreadElement.class);
		assertTrue("Unexpected list size.", arraySpreadElements.size() == 1);
		((Variable) arraySpreadElements.get(0).getValue()).setName(ast.newIdentifier("b2"));
		List<ArrayCreation> arrayCreations = getAllOfType(program, ArrayCreation.class);
		assertTrue("Unexpected list size.", arrayCreations.size() == 1);
		arrayCreations.get(0).elements().add(ast.newArrayElement(null, ast.newVariable("c2")));
		arrayCreations.get(0).elements()
				.add(ast.newArrayElement(null, ast.newArraySpreadElement(ast.newVariable("d2"))));
		arrayCreations.get(0).elements().add(ast.newArrayElement(null, ast.newVariable("e2")));

		rewrite();
		checkResult("<?php [...$b2, $c2, ...$d2, $e2]; ?>");
	}

	@Test
	public void arraySpreadElement3() throws Exception {
		String str = "<?php [...$b2, $c2, ...$d2, $e2]; ?>";
		initialize(str);

		List<ArrayCreation> arrayCreations = getAllOfType(program, ArrayCreation.class);
		assertTrue("Unexpected list size.", arrayCreations.size() == 1);
		assertTrue("Unexpected list size.", arrayCreations.get(0).elements().size() == 4);
		arrayCreations.get(0).elements().remove(2);
		arrayCreations.get(0).elements().remove(0);

		rewrite();
		checkResult("<?php [$c2, $e2]; ?>");
	}

	@Test
	public void fieldsDeclarations() throws Exception {
		String str = "<?php class A { private   ?int $prop1   =   5, $prop2   =   null, $prop3, $prop4   =   0; } ?>";
		initialize(str);

		List<FieldsDeclaration> fieldsDeclarations = getAllOfType(program, FieldsDeclaration.class);
		assertTrue("Unexpected list size.", fieldsDeclarations.size() == 1);
		fieldsDeclarations.get(0).setFieldsType(null);
		assertTrue("Unexpected list size.", fieldsDeclarations.get(0).fields().size() == 4);
		fieldsDeclarations.get(0).fields().remove(0);

		rewrite();
		checkResult("<?php class A { private $prop2   =   null, $prop3, $prop4   =   0; } ?>");
	}

	@Test
	public void fieldsDeclarations2() throws Exception {
		String str = "<?php class A { private   $prop1   =   5, $prop2   =   null, $prop3, $prop4   =   0; } ?>";
		initialize(str);

		List<FieldsDeclaration> fieldsDeclarations = getAllOfType(program, FieldsDeclaration.class);
		assertTrue("Unexpected list size.", fieldsDeclarations.size() == 1);
		fieldsDeclarations.get(0).setFieldsType(ast.newIdentifier("float"));
		assertTrue("Unexpected list size.", fieldsDeclarations.get(0).fields().size() == 4);
		fieldsDeclarations.get(0).fields().add(0,
				ast.newSingleFieldDeclaration(ast.newVariable("prop0"), ast.newScalar("10", Scalar.TYPE_INT)));

		rewrite();
		checkResult(
				"<?php class A { private float   $prop0 = 10, $prop1   =   5, $prop2   =   null, $prop3, $prop4   =   0; } ?>");
	}

	@Test
	public void fieldsDeclarations3() throws Exception {
		String str = "<?php class A { private   $prop1   =   5, $prop2   =   null, $prop3, $prop4   =   0; } ?>";
		initialize(str);

		List<FieldsDeclaration> fieldsDeclarations = getAllOfType(program, FieldsDeclaration.class);
		assertTrue("Unexpected list size.", fieldsDeclarations.size() == 1);
		fieldsDeclarations.get(0).setFieldsType(ast.newIdentifier("float"));
		assertTrue("Unexpected list size.", fieldsDeclarations.get(0).fields().size() == 4);
		fieldsDeclarations.get(0).fields().clear();
		fieldsDeclarations.get(0).fields()
				.add(ast.newSingleFieldDeclaration(ast.newVariable("prop0"), ast.newScalar("10", Scalar.TYPE_INT)));

		rewrite();
		checkResult("<?php class A { private float   $prop0 = 10; } ?>");
	}

}
