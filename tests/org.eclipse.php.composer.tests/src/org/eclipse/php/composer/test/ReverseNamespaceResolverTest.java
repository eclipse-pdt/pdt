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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.php.composer.core.facet.FacetManager;
import org.eclipse.php.composer.core.model.ModelAccess;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.tests.TestSuiteWatcher;
import org.eclipse.php.core.tests.TestUtils;
import org.eclipse.php.internal.core.facet.PHPFacets;
import org.eclipse.php.internal.core.project.PHPNature;
import org.junit.After;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

public class ReverseNamespaceResolverTest {
	private IProject project;

	@ClassRule
	public static TestWatcher watcher = new TestSuiteWatcher();

	@Test
	public void testNamespaceResolver() throws CoreException, IOException {

		project = TestUtils.createProject("testproject2"); //$NON-NLS-1$
		assertNotNull(project);
		ComposerCoreTestPlugin.copyProjectFiles(project);

		TestUtils.setProjectPHPVersion(project, PHPVersion.PHP5_3);

		PHPFacets.setFacetedVersion(project, PHPVersion.PHP5_3);
		FacetManager.installFacets(project, PHPVersion.PHP5_3, new NullProgressMonitor());

		project.refreshLocal(IResource.DEPTH_INFINITE, null);

		IFile file = project.getFile("composer.json"); //$NON-NLS-1$
		assertNotNull(file);

		assertTrue(project.hasNature(PHPNature.ID));
		assertTrue(FacetManager.hasComposerFacet(project));

		String namespace = "Foobar\\Sub"; //$NON-NLS-1$

		IPath resolvedPath = ModelAccess.getInstance().reverseResolve(project, namespace);
		assertNotNull(resolvedPath);
		assertTrue(project.getFolder(resolvedPath).exists());
	}

	@After
	public void cleanup() throws CoreException {
		if (project != null) {
			project.delete(true, null);
		}
	}

}
