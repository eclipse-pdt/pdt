/*******************************************************************************
 * Copyright (c) 2012, 2016 PDT Extension Group and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.api.test;

import junit.framework.TestCase;

import org.eclipse.php.composer.api.objects.JsonObject;

public class JsonObjectTest extends TestCase {

	public void testProperties() {
		JsonObject obj = new JsonObject();
		obj.set("a", "val1");
		obj.set("b", "val2");
		obj.set("c", "val3");
		
		assertEquals(3, obj.size());
		
		assertTrue(obj.has("a"));
		assertTrue(obj.has("b"));
		assertTrue(obj.has("c"));
		
		assertEquals("val1", obj.getAsString("a"));
		assertEquals("val2", obj.getAsString("b"));
		assertEquals("val3", obj.getAsString("c"));
	}
	
	public void testClear() {
		JsonObject obj = new JsonObject();
		obj.set("a", "val");
		obj.set("b", "val");
		obj.set("c", "val");
		
		assertEquals(3, obj.size());
		
		obj.clear();
		
		assertEquals(0, obj.size());
	}
}
