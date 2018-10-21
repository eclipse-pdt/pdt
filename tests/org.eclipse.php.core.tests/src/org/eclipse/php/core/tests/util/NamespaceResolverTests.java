/*******************************************************************************
 * Copyright (c) 2018 Dawid Pakuła and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Dawid Pakuła - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.core.tests.util;

import static org.junit.Assert.assertEquals;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IBuildpathEntry;
import org.eclipse.php.core.PHPToolkitUtil;
import org.eclipse.php.core.tests.TestUtils;
import org.eclipse.php.core.util.INamespaceResolver;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class NamespaceResolverTests {

	private final static String SIMPLE = "namespace_resolver1";
	private final static String SIMPLE_BUILDPATHS = "namespace_resolver2";

	private static IProject simple;
	private static IProject simpleBuildpaths;

	@BeforeClass
	public static void setup() throws CoreException {
		simple = TestUtils.createProject(SIMPLE);
		TestUtils.createFolder(simple, "src");
		TestUtils.createFolder(simple, "tests");

		simpleBuildpaths = TestUtils.createProject(SIMPLE_BUILDPATHS);
		TestUtils.createFolder(simpleBuildpaths, "src");
		TestUtils.createFolder(simpleBuildpaths, "tests");

		DLTKCore.create(simpleBuildpaths).setRawBuildpath(
				new IBuildpathEntry[] { DLTKCore.newSourceEntry(simpleBuildpaths.getFullPath().append("src")),
						DLTKCore.newSourceEntry(simpleBuildpaths.getFullPath().append("tests")) },
				new NullProgressMonitor());
	}

	@AfterClass
	public static void cleanup() {
		TestUtils.deleteProject(simple);
		TestUtils.deleteProject(simpleBuildpaths);

		simple = null;
		simpleBuildpaths = null;
	}

	@Test
	public void testSimpleResolveNamespace() {
		INamespaceResolver resolver = PHPToolkitUtil.getNamespaceResolver(simple);

		assertEquals("src", resolver.resolveNamespace(simple.getFullPath().append("src")));
		assertEquals("src\\Api", resolver.resolveNamespace(simple.getFullPath().append("src").append("Api")));
		assertEquals("NotExisted\\Dir",
				resolver.resolveNamespace(simple.getFullPath().append("NotExisted").append("Dir")));
	}

	@Test
	public void testSimpleResolveLocation() {
		INamespaceResolver resolver = PHPToolkitUtil.getNamespaceResolver(simple);

		assertEquals(simple.getFullPath().append("src"), resolver.resolveLocation(simple.getFullPath(), "src"));
		assertEquals(simple.getFullPath().append("src").append("Api"),
				resolver.resolveLocation(simple.getFullPath(), "src\\Api"));
		assertEquals(simple.getFullPath().append("NotExisted").append("Dir"),
				resolver.resolveLocation(simple.getFullPath(), "NotExisted\\\\Dir"));
	}

	@Test
	public void testSimpleBuildpathResolveNamespace() {
		INamespaceResolver resolver = PHPToolkitUtil.getNamespaceResolver(simpleBuildpaths);

		assertEquals("", resolver.resolveNamespace(simpleBuildpaths.getFullPath().append("src")));
		assertEquals("Api", resolver.resolveNamespace(simpleBuildpaths.getFullPath().append("src").append("Api")));
		assertEquals("NotExisted\\Dir",
				resolver.resolveNamespace(simpleBuildpaths.getFullPath().append("NotExisted").append("Dir")));

		assertEquals("Command\\Something", resolver.resolveNamespace(
				simpleBuildpaths.getFullPath().append("tests").append("Command").append("Something")));
	}

	@Test
	public void testSimpleBuildpathResolveLocation() {
		INamespaceResolver resolver = PHPToolkitUtil.getNamespaceResolver(simpleBuildpaths);

		assertEquals(simpleBuildpaths.getFullPath().append("src").append("Api"),
				resolver.resolveLocation(simpleBuildpaths.getFullPath().append("src").append("inner"), "Api"));
		assertEquals(simpleBuildpaths.getFullPath().append("tests").append("Api").append("Something"),
				resolver.resolveLocation(simpleBuildpaths.getFullPath().append("tests"), "Api\\Something"));
		assertEquals(simpleBuildpaths.getFullPath(), resolver.resolveLocation(simpleBuildpaths.getFullPath(), ""));
	}

}
