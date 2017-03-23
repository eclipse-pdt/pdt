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

import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.php.composer.core.facet.FacetManager;
import org.eclipse.php.composer.core.model.ModelAccess;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.project.ProjectOptions;
import org.eclipse.php.internal.core.facet.PHPFacets;
import org.eclipse.php.internal.core.project.PHPNature;
import org.junit.Test;

public class ReverseNamespaceResolverTest extends ComposerModelTests {

	public ReverseNamespaceResolverTest() {
		super("Reverse Namespace Resolver tests");
	}

	@Test
	public void testNamespaceResolver() throws CoreException, IOException {

		IScriptProject scriptProject = ensureScriptProject("testproject2");

		assertNotNull(scriptProject);

		IProjectDescription desc = scriptProject.getProject().getDescription();
		desc.setNatureIds(new String[] { PHPNature.ID });
		scriptProject.getProject().setDescription(desc, null);

		ProjectOptions.setPHPVersion(PHPVersion.PHP5_3, scriptProject.getProject());

		PHPFacets.setFacetedVersion(scriptProject.getProject(), PHPVersion.PHP5_3);
		FacetManager.installFacets(scriptProject.getProject(), PHPVersion.PHP5_3, new NullProgressMonitor());

		scriptProject.getProject().refreshLocal(IResource.DEPTH_INFINITE, null);
		scriptProject.getProject().build(IncrementalProjectBuilder.FULL_BUILD, null);

		ComposerCoreTestPlugin.waitForIndexer();
		ComposerCoreTestPlugin.waitForAutoBuild();

		IFile file = scriptProject.getProject().getFile("composer.json");
		assertNotNull(file);

		assertTrue(scriptProject.getProject().hasNature(PHPNature.ID));
		assertTrue(FacetManager.hasComposerFacet(scriptProject.getProject()));

		String namespace = "Foobar\\Sub";

		IPath resolvedPath = ModelAccess.getInstance().reverseResolve(scriptProject.getProject(), namespace);
		assertNotNull(resolvedPath);
		assertTrue(scriptProject.getProject().getFolder(resolvedPath).exists());

	}
}
