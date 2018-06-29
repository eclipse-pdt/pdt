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

import java.io.IOException;
import java.net.URISyntaxException;

import org.eclipse.php.composer.api.ComposerPackage;
import org.eclipse.php.composer.api.collection.Psr;
import org.eclipse.php.composer.api.json.ParseException;
import org.eclipse.php.composer.api.objects.Autoload;
import org.eclipse.php.composer.api.objects.Namespace;
import org.junit.Test;

public class AutoloadTest extends ComposertTestCase {

	@Test
	public void testNamespace() {
		Namespace ns = new Namespace();
		ns.setNamespace("bla");
		assertEquals("bla", ns.getNamespace());
	}

	@Test
	public void testPsr0() throws IOException, URISyntaxException {
		ComposerPackage composerPackage = new ComposerPackage();
		composerPackage.getAutoload().getPsr0().clear();
		Namespace ns = new Namespace();
		ns.setNamespace("foo");
		ns.add("bar");
		composerPackage.getAutoload().getPsr0().add(ns);
		assertEquals(1, composerPackage.getAutoload().getPsr0().size());
		Namespace namespace = composerPackage.getAutoload().getPsr0().get("foo");
		assertNotNull(namespace);
		assertEquals("bar", namespace.getPaths().get(0));
	}

	@Test
	public void testPsr0Escaping() throws ParseException {

		Psr psr0 = new Psr();
		Namespace ns = new Namespace("Symfony\\Component\\Yaml", "symfony/yaml");
		psr0.add(ns);

		String json = psr0.toJson();
		psr0 = new Psr(json);
		assertEquals(1, psr0.getNamespaces().size());

	}

	@Test
	public void testParse() throws IOException, ParseException, URISyntaxException {
		ComposerPackage composerPackage = new ComposerPackage(loadFile("autoload.json"));
		Autoload autoload = composerPackage.getAutoload();

		Psr psr0 = autoload.getPsr0();
		assertNotNull(psr0);
		assertEquals(4, psr0.size());

		assertTrue(psr0.has(""));
		assertTrue(psr0.has("gossi"));
		assertTrue(psr0.has("Monolog"));
		assertTrue(psr0.has("UniqueGlobalClass"));

		assertEquals(2, psr0.get("Monolog").size());
		assertEquals("etc/", psr0.get("").getFirst());

		assertEquals(3, autoload.getClassMap().size());

		assertEquals(1, autoload.getFiles().size());
	}

	@Test
	public void testParseDev() throws IOException, ParseException, URISyntaxException {
		ComposerPackage composerPackage = new ComposerPackage(loadFile("autoload.json"));
		Autoload autoload = composerPackage.getAutoloadDev();

		Psr psr0 = autoload.getPsr0();
		assertNotNull(psr0);
		assertEquals(4, psr0.size());

		assertTrue(psr0.has(""));
		assertTrue(psr0.has("gossi"));
		assertTrue(psr0.has("Monolog"));
		assertTrue(psr0.has("UniqueGlobalClass"));

		assertEquals(2, psr0.get("Monolog").size());
		assertEquals("etc/", psr0.get("").getFirst());

		assertEquals(3, autoload.getClassMap().size());

		assertEquals(1, autoload.getFiles().size());
	}

	@Test
	public void testFromString() throws ParseException {
		Psr psr0 = new Psr("{ \"Foo\" : \"Bar\", \"What\" : \"Ever\"}");
		assertEquals(2, psr0.size());
		assertEquals("Foo", psr0.getFirst().getNamespace());
	}

	@Test
	public void testSearch() throws IOException, ParseException, URISyntaxException {
		ComposerPackage composerPackage = new ComposerPackage(loadFile("autoload.json"));
		Autoload autoload = composerPackage.getAutoload();
		Psr psr0 = autoload.getPsr0();

		assertTrue(psr0.hasPath("src/gossi"));

		Namespace ns = psr0.getNamespaceForPath("src/gossi");
		assertNotNull(ns);
		assertEquals("gossi", ns.getNamespace());
	}

	@Test
	public void testNamespaceEquals() {
		Namespace n1, n2;

		// positives
		n1 = new Namespace();
		n2 = n1;
		assertTrue(n1.equals(n2));

		n1.setNamespace("test");
		n2 = n1.clone();
		assertTrue(n1.equals(n2));

		n1 = new Namespace();
		n1.add("bla");
		n2 = n1.clone();
		assertTrue(n1.equals(n2));

		n1.add("boink");
		n2 = n1.clone();
		assertTrue(n1.equals(n2));

		// negatives
		n1 = new Namespace();
		n1.setNamespace("test");
		n2 = new Namespace();
		n2.setNamespace("wurst");
		assertFalse(n1.equals(n2));

		n1 = new Namespace();
		n1.add("bla");
		n2 = new Namespace();
		n2.add("boink");
		assertFalse(n1.equals(n2));

		n1 = new Namespace();
		n1.setNamespace("test");
		n2 = new Namespace();
		n2.add("boink");
		assertFalse(n1.equals(n2));

		n1 = new Namespace();
		n1.add("boink");
		n2 = new Namespace();
		n2.setNamespace("test");
		assertFalse(n1.equals(n2));

		n1 = new Namespace();
		n1.setNamespace("test");
		n1.add("bla");
		n2 = new Namespace();
		n2.setNamespace("test");
		n2.add("boink");
		assertFalse(n1.equals(n2));
	}
}
