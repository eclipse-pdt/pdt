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
package org.eclipse.php.composer.test;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IBuildpathEntry;
import org.eclipse.php.composer.core.facet.FacetManager;
import org.eclipse.php.core.PHPToolkitUtil;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.tests.TestUtils;
import org.eclipse.php.core.tests.TestUtils.ColliderType;
import org.eclipse.php.core.util.INamespaceResolver;
import org.eclipse.php.internal.core.facet.PHPFacets;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

@SuppressWarnings("nls")
public class CoreNamespaceResolverTests {

	private static IProject project;

	@BeforeClass
	public static void setup() throws CoreException {
		TestUtils.disableColliders(ColliderType.ALL);
		project = TestUtils.createProject("composer_namespace_resolver");
		TestUtils.createFolder(project, "src");
		TestUtils.createFolder(project, "tests");

		DLTKCore.create(project)
				.setRawBuildpath(
						new IBuildpathEntry[] { DLTKCore.newSourceEntry(project.getFullPath().append("src")),
								DLTKCore.newSourceEntry(project.getFullPath().append("tests")) },
						new NullProgressMonitor());

		IFile file = project.getFile("composer.json"); //$NON-NLS-1$
		file.create(new ByteArrayInputStream(
				"{\"autoload\": {\"psr-4\" : {\"App\\\\\" : \"src/\"} }, \"autoload-dev\": {\"psr-4\" : {\"App\\\\Tests\\\\\" : \"tests/\"} }   }"
						.getBytes()),
				true, null);

		TestUtils.setProjectPHPVersion(project, PHPVersion.PHP5_3);

		PHPFacets.setFacetedVersion(project, PHPVersion.PHP5_3);
		FacetManager.installFacets(project, PHPVersion.PHP5_3, new NullProgressMonitor());

	}

	@AfterClass
	public static void cleanup() {
		TestUtils.deleteProject(project);

		project = null;
		TestUtils.enableColliders(ColliderType.ALL);
	}

	@Test
	public void testSimpleResolveNamespace() {
		INamespaceResolver resolver = PHPToolkitUtil.getNamespaceResolver(project);

		assertEquals("App", resolver.resolveNamespace(project.getFullPath().append("src")));
		assertEquals("App\\Api", resolver.resolveNamespace(project.getFullPath().append("src").append("Api")));

		assertEquals("App\\Tests\\Api", resolver.resolveNamespace(project.getFullPath().append("tests").append("Api")));
	}

	@Test
	public void testSimpleResolveLocation() {
		INamespaceResolver resolver = PHPToolkitUtil.getNamespaceResolver(project);

		assertEquals(project.getFullPath().append("src"), resolver.resolveLocation(project.getFullPath(), "App"));
		assertEquals(project.getFullPath().append("src").append("Api"),
				resolver.resolveLocation(project.getFullPath(), "App\\Api"));
		assertEquals(project.getFullPath().append("tests").append("Something"),
				resolver.resolveLocation(project.getFullPath(), "App\\Tests\\Something"));
	}

}
