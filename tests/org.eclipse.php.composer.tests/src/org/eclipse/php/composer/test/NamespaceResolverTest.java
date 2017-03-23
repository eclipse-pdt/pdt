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
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.php.composer.core.ComposerPlugin;
import org.eclipse.php.composer.core.facet.FacetManager;
import org.eclipse.php.composer.core.resources.IComposerProject;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.project.ProjectOptions;
import org.eclipse.php.internal.core.facet.PHPFacets;
import org.eclipse.php.internal.core.project.PHPNature;
import org.junit.Test;

public class NamespaceResolverTest extends ComposerModelTests {

	public NamespaceResolverTest() {
		super("Namespace Resolver tests");
	}

	@Test
	public void testNamespaceResolver() throws CoreException, IOException {

		IScriptProject scriptProject = ensureScriptProject("namespace-resolver");

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

		IComposerProject project = ComposerPlugin.getDefault().getComposerProject(scriptProject);

		IFile composerJson = project.getComposerJson();
		assertNotNull(composerJson);

		assertTrue(scriptProject.getProject().hasNature(PHPNature.ID));
		assertTrue(FacetManager.hasComposerFacet(scriptProject.getProject()));

		assertEquals("Foo\\Bar", project.getNamespace(new Path("src/Foo/Bar")));
		assertEquals("Foo\\Bar\\Baz", project.getNamespace(new Path("src/Foo/Bar/Baz")));
		assertEquals("Hello\\World", project.getNamespace(new Path("src/HelloWorld")));
		assertEquals("", project.getNamespace(new Path("src/Null/")));
		assertEquals("Wurst", project.getNamespace(new Path("src/Null/Wurst")));
		assertEquals("Blut\\Wurst", project.getNamespace(new Path("src/Null/Blut/Wurst")));

		// IResource resource = scriptProject.getProject().getFolder(new
		// Path("src/Foobar/Sub"));
		// IPath path = ModelAccess.getInstance().resolve(resource);
		// assertNotNull(path);
		// assertEquals("Foobar/Sub", path.toString());
	}
}
