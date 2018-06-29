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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;

import org.junit.Test;

import org.eclipse.php.composer.api.ComposerPackage;
import org.eclipse.php.composer.api.VersionedPackage;
import org.eclipse.php.composer.api.collection.Dependencies;
import org.eclipse.php.composer.api.collection.Persons;
import org.eclipse.php.composer.api.collection.Psr;
import org.eclipse.php.composer.api.objects.Autoload;
import org.eclipse.php.composer.api.objects.JsonObject;
import org.eclipse.php.composer.api.objects.Namespace;
import org.eclipse.php.composer.api.objects.Person;

public class ListenerTest extends ComposertTestCase {

	private ComposerPackage pkg;
	private String property;
	private Object oldValue;
	private Object newValue;
	private int changes = 0;
	
	private HashMap<String, Integer> listenerCounter;
	
	@Override
	protected void setUp() throws Exception {
		pkg = createDummyPackage();
		listenerCounter = new HashMap<>();
		changes = 0;
		pkg.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent e) {
				property = e.getPropertyName();
				oldValue = e.getOldValue();
				newValue = e.getNewValue();
				changes++;
				
				if (!listenerCounter.containsKey(property)) {
					listenerCounter.put(property, 0);
				}
				
				listenerCounter.put(property, listenerCounter.get(property) + 1);
				
				// debug output
//				System.out.println("Prop Change: " + e.getPropertyName() + ", old Value: " + e.getOldValue() + ", new Value: " + e.getNewValue());
			}
		});
	}
	
	@Test
	public void testName() {
		String name = "foo/bar";
		
		pkg.setName(name);
		assertEquals(1, changes);
		assertEquals("name", property);
		assertEquals(name, newValue);
		assertEquals(NAME, oldValue);
		assertFalse(oldValue.equals(newValue));
		
		changes = 0;
		pkg.remove("name");
		assertEquals(1, changes);
		assertEquals("name", property);
		assertEquals(null, newValue);
		assertEquals(name, oldValue);
		assertFalse(oldValue.equals(newValue));
		
		changes = 0;
		pkg.setName(NAME);
		assertEquals(1, changes);
		assertEquals("name", property);
		assertEquals(NAME, newValue);
		assertEquals(null, oldValue);
		assertFalse(newValue.equals(oldValue));
	}
	
	@Test
	public void testAuthors() {
		// set name of first author
		String name = "hans";
		Persons authors = pkg.getAuthors();
		Person p1 = authors.get(1);
		p1.setName("hans");
		assertEquals(1, authors.indexOf(p1));
		
		assertEquals(1, changes);
		assertEquals("authors.#1.name", property);
		assertEquals(name, newValue);
		assertEquals(PERSON2, oldValue);
		assertFalse(oldValue.equals(newValue));
		
		
		// add author
		changes = 0;
		Person p = new Person();
		p.setName(name);
		authors.add(p);
		
		assertEquals(1, changes);
		assertEquals("authors.#2", property);
		assertEquals(p, newValue);
		assertEquals(null, oldValue);
		assertFalse(newValue.equals(oldValue));
		
		
		// change property to same value
		changes = 0;
		p.setName(name);
		assertEquals(0, changes);
		
		
		// remove an author
		changes = 0;
		p = authors.get(1);
		authors.remove(p);
		
		assertEquals(1, changes);
		assertEquals("authors.#1", property);
		assertEquals(null, newValue);
		assertEquals(p, oldValue);
		assertFalse(oldValue.equals(newValue));
	}
	
	@Test
	public void testCustom() {
		String p = "prop";
		JsonObject o = new JsonObject();
		
		pkg.set(p, o);
		assertEquals(1, changes);
		assertEquals(p, property);
		assertEquals(o, newValue);
		assertEquals(null, oldValue);
		assertFalse(newValue.equals(oldValue));
		
		
		changes = 0;
		String p2 = "prop2";
		String v = "val";
		o.set(p2, v);
		
		assertEquals(1, changes);
		assertEquals(p+"."+p2, property);
		assertEquals(v, newValue);
		assertEquals(null, oldValue);
		assertFalse(newValue.equals(oldValue));
		
	}
	
	
	private int getCounter(String key) {
		if (!listenerCounter.containsKey(key)) {
			return 0;
		}
		
		return listenerCounter.get(key);
	}
	
	@Test
	public void testLicense() {
		pkg.getLicense().add("GPL");
		assertEquals(1, getCounter("license.#2"));
		
		pkg.getLicense().add("LGPL");
		assertEquals(1, getCounter("license.#3"));
		
		changes = 0;
		pkg.getLicense().add("MIT");
		assertEquals(0, changes);
		
		pkg.getLicense().clear();
		assertEquals(4, getCounter("license.#0"));
		
		
	}
	
	@Test
	public void testKeywords() {
		pkg.getKeywords().add("fool");

		assertEquals(1, getCounter("keywords.#2"));
		
		pkg.getKeywords().add("bar");
		
		assertEquals(1, getCounter("keywords.#3"));
	}
	
	@Test
	public void testAutoload() {
		Autoload al = pkg.getAutoload();
		Psr psr = al.getPsr0();
		
		// psr
		Namespace ns1 = new Namespace();
		ns1.setNamespace("test");
		psr.add(ns1);
		
		assertEquals(1, getCounter("autoload.psr-0.test"));
		
		ns1.add("new/path");
		
		assertEquals(1, getCounter("autoload.psr-0.test.#0"));
		
		// classmap
		al.getClassMap().add("file/to/path.php");
		
		assertEquals(1, getCounter("autoload.classmap.#3"));
		
		// files
		al.getFiles().add("another/file/to/path.php");
		
		assertEquals(1, getCounter("autoload.files.#1"));
	}
	
	@Test
	public void testDependencies() {
		Dependencies require = pkg.getRequireDev();
		
		VersionedPackage phpunit = require.get(PHPUNIT);
		phpunit.setVersion("1.2.3");
		
		assertEquals(1, changes);
		assertEquals("require-dev.phpunit/phpunit.version", property);
		assertEquals("1.2.3", newValue);
		assertEquals(PHPUNIT_VERSION, oldValue);
		assertFalse(oldValue.equals(newValue));
	}
}
