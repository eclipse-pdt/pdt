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
package org.eclipse.php.core.tests.search;

import org.eclipse.dltk.core.tests.model.AbstractSingleProjectSearchTests;
import org.eclipse.dltk.core.tests.model.TestSearchResults;
import org.eclipse.php.core.tests.PHPCoreTests;

public class SearchTests extends AbstractSingleProjectSearchTests {

	public SearchTests(String name) {
		super(PHPCoreTests.PLUGIN_ID, name, "search");
	}

	public static Suite suite() {
		return new Suite(SearchTests.class);
	}

	public void testConstantDeclarations() throws Exception {
		final TestSearchResults results = search("TEST1", FIELD, DECLARATIONS);
		assertEquals(1, results.size());
	}

	public void testVariableDeclarations() throws Exception {
		final TestSearchResults results = search("$globalVar", FIELD, DECLARATIONS);
		assertEquals(1, results.size());
	}
	
	public void testVariableReferences() throws Exception {
		final TestSearchResults results = search("$globalVar", FIELD, REFERENCES);
		assertEquals(2, results.size()); // declaration is also reference
		results.assertSourceModule("search2.php");
	}
	
	public void testClassDeclarations() throws Exception {
		final TestSearchResults results = search("A", TYPE, DECLARATIONS);
		assertEquals(1, results.size());
		results.assertType("A");
	}

	public void testClassReferences() throws Exception {
		final TestSearchResults results = search("A", TYPE, REFERENCES);
		assertEquals(1, results.size());
		results.assertSourceModule("search1.php");
	}

	public void testClassAllOccurences() throws Exception {
		final TestSearchResults results = search("A", TYPE, ALL_OCCURRENCES);
		assertEquals(2, results.size());
		results.assertSourceModule("search1.php");
		results.assertType("A");
	}
	
	public void testInterfaceDeclarations() throws Exception {
		final TestSearchResults results = search("I", TYPE, DECLARATIONS);
		assertEquals(1, results.size());
		results.assertType("I");
	}
	
	public void testInterfaceReferences() throws Exception {
		final TestSearchResults results = search("I", TYPE, REFERENCES);
		assertEquals(1, results.size());
		results.assertSourceModule("search1.php");
	}
	
	public void testInterfaceAllOccurences() throws Exception {
		final TestSearchResults results = search("I", TYPE, ALL_OCCURRENCES);
		assertEquals(2, results.size());
		results.assertSourceModule("search1.php");
		results.assertType("I");
	}
}
