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

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.php.composer.core.ComposerPlugin;
import org.eclipse.php.composer.core.buildpath.BuildPathParser;
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

public class BuildPathTest {
	private IProject project;

	@ClassRule
	public static TestWatcher watcher = new TestSuiteWatcher();

	@Test
	public void testBuildpathParser() throws CoreException, IOException, InterruptedException {
		project = TestUtils.createProject("buildpath"); //$NON-NLS-1$
		assertNotNull(project);
		ComposerCoreTestPlugin.copyProjectFiles(project);
		project.refreshLocal(IResource.DEPTH_INFINITE, null);

		TestUtils.setProjectPHPVersion(project, PHPVersion.PHP5_3);

		PHPFacets.setFacetedVersion(project, PHPVersion.PHP5_3);
		FacetManager.installFacets(project, PHPVersion.PHP5_3, new NullProgressMonitor());

		IFile file = project.getFile("composer.json"); //$NON-NLS-1$
		assertNotNull(file);

		assertTrue(project.hasNature(PHPNature.ID));
		assertTrue(FacetManager.hasComposerFacet(project));

		IComposerProject composerProject = ComposerPlugin.getDefault().getComposerProject(project);
		BuildPathParser parser = new BuildPathParser(composerProject);
		List<String> paths = parser.getPaths();
		List<String> expected = new ArrayList<>(Arrays.asList("mordor/composer", "mordor/gossi/ldap/src", //$NON-NLS-1$ //$NON-NLS-2$
				"mordor/phing/phing/classes/phing", "mordor/propel/propel1/generator/lib", //$NON-NLS-1$ //$NON-NLS-2$
				"mordor/propel/propel1/runtime/lib", "mordor/symfony/console", "nother", "src", "test")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
		assertArrayEquals(paths.toArray(), expected.toArray());

		// let indexing threads shutdown to avoid SWT thread access errors
	}

	@After
	public void cleanup() throws CoreException {
		if (project != null) {
			project.delete(true, null);
		}
	}

}
