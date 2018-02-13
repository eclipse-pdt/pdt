/*******************************************************************************
 * Copyright (c) 2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Dawid Pakuła - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.core.tests.text;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.eclipse.php.core.tests.TestSuiteWatcher;
import org.eclipse.php.internal.core.util.text.PHPTextSequenceUtilities;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

public class PHPTextSequenceUtilitiesTests {

	@ClassRule
	public static TestWatcher watcher = new TestSuiteWatcher();

	@Test
	public void emptyArgumentList() {
		String[] argNames = PHPTextSequenceUtilities.getArgNames(null, "()");
		assertEquals(0, argNames.length);
	}

	@Test
	public void singleArgument() {
		String[] argNames = PHPTextSequenceUtilities.getArgNames(null, "('MyArg')");
		assertEquals(1, argNames.length);
		assertEquals("MyArg", argNames[0]);
	}

	@Test
	public void singleArgumentDoubleQuote() {
		String[] argNames = PHPTextSequenceUtilities.getArgNames(null, "(\"MyArg\")");
		assertEquals(1, argNames.length);
		assertEquals("MyArg", argNames[0]);
	}

	@Test
	public void singleArgumentIgnore() {
		String[] argNames = PHPTextSequenceUtilities.getArgNames(null, "($anyElement)");
		assertEquals(1, argNames.length);
		assertNull(argNames[0]);
	}

	@Test
	public void singleArgumentIgnoreParenthesis() {
		String[] argNames = PHPTextSequenceUtilities.getArgNames(null, "'MyArg'");
		assertEquals(1, argNames.length);
		assertEquals("MyArg", argNames[0]);
	}

	@Test
	public void multiArgumentIgnore() {
		String[] argNames = PHPTextSequenceUtilities.getArgNames(null, "('MyString', $anyElement)");
		assertEquals(2, argNames.length);
		assertEquals("MyString", argNames[0]);
		assertNull(argNames[1]);
	}

	@Test
	public void multiArgumentIgnore2() {
		String[] argNames = PHPTextSequenceUtilities.getArgNames(null, "($anyElement, 'MyString')");
		assertEquals(2, argNames.length);
		assertEquals("MyString", argNames[1]);
		assertNull(argNames[0]);
	}

	@Test
	public void ignoreInternalCall() {
		String[] argNames = PHPTextSequenceUtilities.getArgNames(null, "(call('something'), 'MyString')");
		assertEquals(2, argNames.length);
		assertEquals("MyString", argNames[1]);
		assertNull(argNames[0]);
	}

	@Test
	public void nestedCall() {
		String[] argNames = PHPTextSequenceUtilities.getArgNames(null,
				"(call('something', $this->callMe(\"Another\")), 'MyString')");
		assertEquals(2, argNames.length);
		assertEquals("MyString", argNames[1]);
		assertNull(argNames[0]);
	}

	@Test
	public void suggestObjectOperator() {
		assertEquals(">", PHPTextSequenceUtilities.suggestObjectOperator("$x-"));
		assertEquals(">", PHPTextSequenceUtilities.suggestObjectOperator("$x -"));

		assertEquals("->", PHPTextSequenceUtilities.suggestObjectOperator("$x"));
		assertEquals("->", PHPTextSequenceUtilities.suggestObjectOperator("$x->field"));
		assertEquals("->", PHPTextSequenceUtilities.suggestObjectOperator("$x\n->call()\n"));
		assertEquals("->", PHPTextSequenceUtilities.suggestObjectOperator("X::$x"));

		assertEquals("->", PHPTextSequenceUtilities.suggestObjectOperator("$x[0]"));

	}

	@Test
	public void suggestStaticObjectOperator() {
		assertEquals(":", PHPTextSequenceUtilities.suggestObjectOperator("X:"));
		assertEquals("::", PHPTextSequenceUtilities.suggestObjectOperator("X"));
	}

	@Test
	public void ignoreObjecOperator() {
		assertNull(PHPTextSequenceUtilities.suggestObjectOperator("$x->"));
		assertNull(PHPTextSequenceUtilities.suggestObjectOperator("X::"));
	}
}
