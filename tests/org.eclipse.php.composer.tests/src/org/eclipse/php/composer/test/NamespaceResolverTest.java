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
package org.eclipse.php.composer.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.php.composer.core.ComposerPlugin;
import org.eclipse.php.composer.core.facet.FacetManager;
import org.eclipse.php.composer.core.resources.IComposerProject;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.tests.TestSuiteWatcher;
import org.eclipse.php.core.tests.TestUtils;
import org.eclipse.php.internal.core.facet.PHPFacets;
import org.eclipse.php.internal.core.project.PHPNature;
import org.junit.After;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

public class NamespaceResolverTest {

	private IProject project;

	@ClassRule
	public static TestWatcher watcher = new TestSuiteWatcher();

	@Test
	public void testNamespaceResolver() throws CoreException, IOException {
		project = TestUtils.createProject("namespace-resolver"); //$NON-NLS-1$

		assertNotNull(project);

		ComposerCoreTestPlugin.copyProjectFiles(project);

		TestUtils.setProjectPHPVersion(project, PHPVersion.PHP5_3);

		PHPFacets.setFacetedVersion(project, PHPVersion.PHP5_3);
		FacetManager.installFacets(project, PHPVersion.PHP5_3, new NullProgressMonitor());

		project.refreshLocal(IResource.DEPTH_INFINITE, null);

		IComposerProject project = ComposerPlugin.getDefault().getComposerProject(this.project);

		IFile composerJson = project.getComposerJson();
		assertNotNull(composerJson);

		assertTrue(project.getProject().hasNature(PHPNature.ID));
		assertTrue(FacetManager.hasComposerFacet(this.project));

		assertEquals("Foo\\Bar", project.getNamespace(new Path("src/Foo/Bar"))); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals("Foo\\Bar\\Baz", project.getNamespace(new Path("src/Foo/Bar/Baz"))); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals("Hello\\World", project.getNamespace(new Path("src/HelloWorld"))); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals("", project.getNamespace(new Path("src/Null/"))); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals("Wurst", project.getNamespace(new Path("src/Null/Wurst"))); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals("Blut\\Wurst", project.getNamespace(new Path("src/Null/Blut/Wurst"))); //$NON-NLS-1$ //$NON-NLS-2$

	}

	@After
	public void cleanup() throws CoreException {
		if (project != null) {
			project.delete(true, null);
		}
	}
}
