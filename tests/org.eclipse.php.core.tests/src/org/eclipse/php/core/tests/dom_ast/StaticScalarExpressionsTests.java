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
package org.eclipse.php.core.tests.dom_ast;

import static org.junit.Assert.assertTrue;

import java.io.StringReader;
import java.util.Arrays;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.php.core.tests.TestSuiteWatcher;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.ast.nodes.ASTNode;
import org.eclipse.php.core.ast.nodes.ASTParser;
import org.eclipse.php.core.ast.nodes.Expression;
import org.eclipse.php.core.ast.nodes.Program;
import org.eclipse.php.internal.core.ast.locator.Locator;
import org.eclipse.php.core.project.ProjectOptions;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class StaticScalarExpressionsTests {

	@ClassRule
	public static TestWatcher watcher = new TestSuiteWatcher();

	@Parameter
	public String desc;

	@Parameter(1)
	public String fileContent;

	@Parameter(2)
	public int offset;

	@Parameter(3)
	public boolean expectedStaticScalar;

	@Test
	public void test() throws Exception {
		StringReader reader = new StringReader(fileContent);
		Program program = ASTParser.newParser(reader, PHPVersion.PHP5, ProjectOptions.useShortTags((IProject) null))
				.createAST(new NullProgressMonitor());

		final ASTNode locateNode = Locator.locateNode(program, offset);
		assertTrue(desc + " test fails. offset should locate an expression node was " + locateNode.getClass().getName(),
				locateNode instanceof Expression);

		Expression expression = (Expression) locateNode;
		final boolean actualStaticScalar = expression.isStaticScalar();

		assertTrue(
				desc + " test fails. Expression" + locateNode.toString() + " should "
						+ (!expectedStaticScalar ? "not" : "") + "be static scalar",
				actualStaticScalar == expectedStaticScalar);
	}

	public static Object[][] DATA = new Object[][] {
			{ "class constant", "<?php class A { const A = 5; const B = 'dsafsfd'; } ", 26, true },
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
			{ "nested hashmap 1", "<?php function foo($a = array(4 => array(), 7 => 4)) { }", 36, true },
			{ "array element", "<?php function foo($a = array(4 => array(4, 6), 7 => 4)) { }", 45, true },
			{ "nested unary operation", "<?php function foo($a = +-+5) { }", 24, true },
			{ "simple scalar 1", "<?php 5; ", 6, false }, { "simple scalar 2", "<?php 'dsafsfd'; ", 6, false },
			{ "simple scalar 3", "<?php $a = 5;", 7, false }, { "simple scalar 4", "<?php $a = 5;", 11, false },
			{ "simple scalar 5", "<?php $a = 5 + 5;", 13, false },
			{ "simple array 1", "<?php $a = array();", 13, false },
			{ "simple array 2", "<?php $a = array( 4 , 6 );", 20, false },
			{ "simple array 3", "<?php $a = array( 4 , 6 );", 24, false } };

	@Parameters(name = "{0}")
	public static Iterable<Object[]> data() {
		return Arrays.asList(DATA);
	}
}
