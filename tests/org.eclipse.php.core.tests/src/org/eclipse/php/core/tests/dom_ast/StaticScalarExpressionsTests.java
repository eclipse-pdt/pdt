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
package org.eclipse.php.core.tests.dom_ast;

import java.io.StringReader;

import junit.framework.Assert;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.ast.locator.Locator;
import org.eclipse.php.internal.core.ast.nodes.ASTNode;
import org.eclipse.php.internal.core.ast.nodes.ASTParser;
import org.eclipse.php.internal.core.ast.nodes.Expression;
import org.eclipse.php.internal.core.ast.nodes.Program;

public class StaticScalarExpressionsTests extends TestCase {

	public StaticScalarExpressionsTests(String name) {
		super(name);
	}

	public static TestSuite suite() {
		TestSuite suite = new TestSuite("Static Scalar Expressions Tests");
		for (Object[] d : DATA) {

			final String desc = (String) d[0];
			final String str = (String) d[1];
			final int offset = (Integer) d[2];
			final boolean expectedStaticScalar = (Boolean) d[3];
			
			suite.addTest(new StaticScalarExpressionsTests(desc) {
				
				/**
				 * Checks if the expression that is located in the given offset is static scalar
				 */
				protected void runTest() throws Exception {
					StringReader reader = new StringReader(str);
					Program program = ASTParser.newParser(reader, PHPVersion.PHP5).createAST(new NullProgressMonitor());

					final ASTNode locateNode = Locator.locateNode(program, offset);
					Assert.assertTrue(desc + " test fails. offset should locate an expression node was " + locateNode.getClass().getName(), locateNode instanceof Expression);

					Expression expression = (Expression) locateNode;
					final boolean actualStaticScalar = expression.isStaticScalar();

					Assert.assertTrue(desc + " test fails. Expression" + locateNode.toString() + " should " + (!expectedStaticScalar ? "not" : "") + "be static scalar", actualStaticScalar == expectedStaticScalar);
				}
			});
		}
		return suite;
	}

	public static Object[][] DATA = new Object[][] {
		{ "class constant", "<?php class A { const A = 5; const B = 'dsafsfd'; } ", 27, true },
		{ "declare expression", "<?php declare ( ticks = 1) {  }", 24, true },
		{ "formal parameter", "<?php function foo($a = 5) { }", 24, true },
		{ "static statement", "<?php function foo($a = 5) { static $a = 5, $b = array(); }", 41, true },
		{ "unary operation", "<?php function foo($a = +-+5) { }", 25, true },
		{ "empty array ", "<?php function foo($a = array()) { }", 25, true },
		{ "not empty array ", "<?php function foo($a = array(4,5)) { }", 26, true },
		{ "hashmap array ", "<?php function foo($a = array(4 => 6)) { }", 26, true },
		{ "twice hashmap 1", "<?php function foo($a = array(4 => 6, 7 => 4)) { }", 26, true },
		{ "twice hashmap 2", "<?php function foo($a = array(4 => 6, 7 => 4)) { }", 29, true },
		{ "twice hashmap 3", "<?php function foo($a = array(4 => 6, 7 => 4)) { }", 28, true },
		{ "twice hashmap 4", "<?php function foo($a = array(4 => 6, 7 => 4)) { }", 29, true },
		{ "twice hashmap 5", "<?php function foo($a = array(4 => 6, 7 => 4)) { }", 30, true },
		{ "twice hashmap 6", "<?php function foo($a = array(4 => 6, 7 => 4)) { }", 31, true },
		{ "nested hashmap 1", "<?php function foo($a = array(4 => array(), 7 => 4)) { }", 36, true },
		{ "array element", "<?php function foo($a = array(4 => array(4, 6), 7 => 4)) { }", 45, true },
		{ "nested unary operation", "<?php function foo($a = +-+5) { }", 24, true },
		{ "simple scalar 1", "<?php 5; ", 6, false },
		{ "simple scalar 2", "<?php 'dsafsfd'; ", 6, false },
		{ "simple scalar 3", "<?php $a = 5;", 7, false },
		{ "simple scalar 4", "<?php $a = 5;", 11, false },
		{ "simple scalar 5", "<?php $a = 5 + 5;", 13, false },
		{ "simple array 1", "<?php $a = array();", 13, false },
		{ "simple array 2", "<?php $a = array( 4 , 6 );", 20, false },
		{ "simple array 3", "<?php $a = array( 4 , 6 );", 24, false }
	};
}
