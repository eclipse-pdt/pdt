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

import org.junit.Test;

import org.eclipse.php.composer.api.ComposerPackage;
import org.eclipse.php.composer.api.json.ParseException;

public class JsonWriterTest extends ComposertTestCase {

	private ComposerPackage phpPackage;
	private String json;

	/*
	 * would be better in a @BeforeClass as so, this does not work for me
	 */
	@Override
	public void setUp() {
		phpPackage = createDummyPackage();
		json = phpPackage.toJson();
	}
	
	@Test
	public void testToJson() {
		assertNotNull(json);
	}
	
	@Test
	public void testEmptyPackage() {
		ComposerPackage pkg = new ComposerPackage();
		assertEquals("{}", pkg.toJson());
	}
	
	@Test
	public void testKeywords() {
		ComposerPackage pkg = new ComposerPackage();

		pkg.getKeywords().add("bla");
		assertNotSame("{\n\t\"keywords\" : \"bla\"\n}", pkg.toJson());
		
		pkg.getKeywords().add("blubb");
		assertEquals("{\n\t\"keywords\" : [\n\t\t\"bla\",\n\t\t\"blubb\"\n\t]\n}", pkg.toJson());
	}
	
	@Test
	public void testLicense() {
		ComposerPackage pkg = new ComposerPackage();

		pkg.getLicense().add("MIT");
		assertEquals("{\n\t\"license\" : \"MIT\"\n}", pkg.toJson());
		
		pkg.getLicense().add("EPL");
		assertEquals("{\n\t\"license\" : [\n\t\t\"MIT\",\n\t\t\"EPL\"\n\t]\n}", pkg.toJson());
	}
	
	@Test
	public void testComposerPackage() {
		try {
			doTestComposerPackage(new ComposerPackage(json));
		} catch (ParseException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testDependencies() {
		try {
			doTestDependencies(new ComposerPackage(json));
		} catch (ParseException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testAutoload() {
		try {
			doTestAutoload(new ComposerPackage(json));
		} catch (ParseException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testConfig() {
		try {
			doTestConfig(new ComposerPackage(json));
		} catch (ParseException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testScripts() {
		try {
			doTestScripts(new ComposerPackage(json));
		} catch (ParseException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testSupport() {
		try {
			doTestSupport(new ComposerPackage(json));
		} catch (ParseException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testRepositories() {
		try {
			doTestRepositories(new ComposerPackage(json));
		} catch (ParseException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test 
	public void testSimplePackage() {
		ComposerPackage pkg = new ComposerPackage();
		pkg.setName("test/package");
		pkg.setType("");
		
//		System.out.println(pkg.toJson());
	}
}
