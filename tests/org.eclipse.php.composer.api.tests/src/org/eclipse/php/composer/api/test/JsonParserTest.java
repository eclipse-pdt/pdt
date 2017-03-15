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

import java.io.BufferedReader;
import java.io.Reader;

import org.eclipse.php.composer.api.ComposerPackage;
import org.eclipse.php.composer.api.RepositoryPackage;
import org.eclipse.php.composer.api.json.ParseException;
import org.junit.Test;

public class JsonParserTest extends ComposertTestCase {

	@Test
	public void testException() {
		try {
			new ComposerPackage("{\n\"bla\":\'arg\n}");
			fail();
		} catch (ParseException e) {
			assertTrue(e.getMessage().startsWith("Unterminated string at line 3 column 2"));
		}
	}

	@Test
	public void testComposerPackage() {
		try {
			doTestComposerPackage(new ComposerPackage(loadFile("composer.json")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testDependencies() {
		try {
			doTestDependencies(new ComposerPackage(loadFile("dependencies.json")));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testSupport() {
		try {
			doTestSupport(new ComposerPackage(loadFile("support.json")));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testConfig() {
		try {
			doTestConfig(new ComposerPackage(loadFile("config.json")));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testScripts() {
		try {
			doTestScripts(new ComposerPackage(loadFile("scripts.json")));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	public void testRepositories() {
		try {
			doTestRepositories(new ComposerPackage(loadFile("repositories.json")));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testAutoload() {
		try {
			doTestAutoload(new ComposerPackage(loadFile("autoload.json")));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testEmptyJson() {
		try {
			ComposerPackage phpPackage = new ComposerPackage(loadFile("empty.json"));
			assertNotNull(phpPackage);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testParserOrder() {
		try {
			Reader core = loadFile("keeko-core.json");
			BufferedReader reader = new BufferedReader(loadFile("keeko-core.json"));
			StringBuilder out = new StringBuilder();
			String line = null;
			String ls = System.getProperty("line.separator");

			while ((line = reader.readLine()) != null) {
				out.append(line);
				out.append(ls);
			}
			String contents = out.toString().trim();

			ComposerPackage phpPackage = new ComposerPackage(core);
			assertEquals(contents, phpPackage.toJson());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testPackageJson() {

		try {
			RepositoryPackage phpPackage = RepositoryPackage.fromPackageRepository(loadFile("packagist.json"));
			assertNotNull(phpPackage);

			assertEquals("friendsofsymfony/user-bundle", phpPackage.getName());
			assertEquals("Symfony FOSUserBundle", phpPackage.getDescription());
			assertNotNull(phpPackage.getVersions());
			assertTrue(phpPackage.getVersions().size() > 0);

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testReactJson() {

		try {
			RepositoryPackage phpPackage = RepositoryPackage.fromPackageRepository(loadFile("react.json"));
			assertNotNull(phpPackage);

			assertEquals("react/react", phpPackage.getName());
			assertEquals("Nuclear Reactor written in PHP.", phpPackage.getDescription());
			assertNotNull(phpPackage.getVersions());
			assertTrue(phpPackage.getVersions().size() > 0);

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
