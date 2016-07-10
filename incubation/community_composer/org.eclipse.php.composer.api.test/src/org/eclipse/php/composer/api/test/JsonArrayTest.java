/*******************************************************************************
 * Copyright (c) 2012, 2016 PDT Extension Group and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.api.test;

import org.eclipse.php.composer.api.collection.JsonArray;

import junit.framework.TestCase;

public class JsonArrayTest extends TestCase {

	public void testClear() {
		JsonArray array = new JsonArray();
		array.add("a");
		array.add("b");
		array.add("c");
		assertEquals(3, array.size());
		
		array.clear();
		assertEquals(0, array.size());
	}
	
	public void testEquals() {
		JsonArray a1, a2;
		
		a1 = new JsonArray();
		a1.add("a");
		a1.add("b");
		a1.add("c");
		
		a2 = new JsonArray();
		a2.add("a");
		a2.add("b");
		a2.add("c");
		
		assertTrue(a1.equals(a2));
	}
}
