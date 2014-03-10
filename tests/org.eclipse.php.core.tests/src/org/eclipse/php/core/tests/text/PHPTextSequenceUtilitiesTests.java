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

import junit.framework.TestCase;

import org.eclipse.php.internal.core.util.text.PHPTextSequenceUtilities;

public class PHPTextSequenceUtilitiesTests extends TestCase {

	public void testEmptyArgumentList() {
		String[] argNames = PHPTextSequenceUtilities.getArgNames(null, "()");
		assertEquals(0, argNames.length);
	}

	public void testSingleArgument() {
		String[] argNames = PHPTextSequenceUtilities.getArgNames(null,
				"('MyArg')");
		assertEquals(1, argNames.length);
		assertEquals("'MyArg'", argNames[0]);
	}

	public void testSingleArgumentDoubleQuote() {
		String[] argNames = PHPTextSequenceUtilities.getArgNames(null,
				"(\"MyArg\")");
		assertEquals(1, argNames.length);
		assertEquals("\"MyArg\"", argNames[0]);
	}

	public void testSingleArgumentIgnore() {
		String[] argNames = PHPTextSequenceUtilities.getArgNames(null,
				"($anyElement)");
		assertEquals(1, argNames.length);
		assertNull(argNames[0]);
	}

	public void testSingleArgumentIgnoreParenthesis() {
		String[] argNames = PHPTextSequenceUtilities.getArgNames(null,
				"'MyArg'");
		assertEquals(1, argNames.length);
		assertEquals("'MyArg'", argNames[0]);
	}

	public void testMultiArgumentIgnore() {
		String[] argNames = PHPTextSequenceUtilities.getArgNames(null,
				"('MyString', $anyElement)");
		assertEquals(2, argNames.length);
		assertEquals("'MyString'", argNames[0]);
		assertNull(argNames[1]);
	}

	public void testMultiArgumentIgnore2() {
		String[] argNames = PHPTextSequenceUtilities.getArgNames(null,
				"($anyElement, 'MyString')");
		assertEquals(2, argNames.length);
		assertEquals("'MyString'", argNames[1]);
		assertNull(argNames[0]);
	}

	public void testIgnoreInternalCall() {
		String[] argNames = PHPTextSequenceUtilities.getArgNames(null,
				"(call('something'), 'MyString')");
		assertEquals(2, argNames.length);
		assertEquals("'MyString'", argNames[1]);
		assertNull(argNames[0]);
	}

	public void testNestedCall() {
		String[] argNames = PHPTextSequenceUtilities.getArgNames(null,
				"(call('something', $this->callMe(\"Another\")), 'MyString')");
		assertEquals(2, argNames.length);
		assertEquals("'MyString'", argNames[1]);
		assertNull(argNames[0]);
	}
}
