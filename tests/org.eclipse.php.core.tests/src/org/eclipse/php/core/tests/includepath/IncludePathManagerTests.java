/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.core.tests.includepath;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IBuildpathEntry;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.environment.EnvironmentPathUtils;
import org.eclipse.dltk.core.internal.environment.LocalEnvironment;
import org.eclipse.php.internal.core.includepath.IIncludepathListener;
import org.eclipse.php.internal.core.includepath.IncludePath;
import org.eclipse.php.internal.core.includepath.IncludePathManager;
import org.eclipse.php.internal.core.project.PHPNature;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class IncludePathManagerTests {

	protected IProject project;

	@Before
	public void setUp() throws Exception {
		// Initialize include path manager:
		IncludePathManager.getInstance();

		project = ResourcesPlugin.getWorkspace().getRoot()
				.getProject("IncludePathManagerTests");
		if (project.exists()) {
			return;
		}

		project.create(null);
		project.open(null);

		// configure nature
		IProjectDescription desc = project.getDescription();
		desc.setNatureIds(new String[] { PHPNature.ID });
		project.setDescription(desc, null);

	}

	@After
	public void tearDown() throws Exception {
		project.delete(true, null);
	}

	@Test
	public void includePathGet() throws Exception {
		IScriptProject scriptProject = DLTKCore.create(project);
		scriptProject.setRawBuildpath(new IBuildpathEntry[0], null);

		IncludePath[] includePath = IncludePathManager.getInstance()
				.getIncludePaths(project);
		assertTrue(includePath.length == 0);
	}

	// This test checks how buildpath changes are not propogated to the include
	// path:
	@Test
	public void includePathGetAfterBPChange1() throws Exception {
		IFolder folder = project.getFolder("a");
		folder.create(true, true, null);

		IScriptProject scriptProject = DLTKCore.create(project);
		scriptProject.setRawBuildpath(new IBuildpathEntry[] { DLTKCore
				.newSourceEntry(folder.getFullPath()) }, null);

		IncludePath[] includePath = IncludePathManager.getInstance()
				.getIncludePaths(project);
		assertTrue(includePath.length == 0);
	}

	// This test checks how buildpath changes are propogated to the include
	// path:
	@Test
	public void includePathGetAfterBPChange2() throws Exception {
		String libraryPath = Platform.OS_WIN32.equals(Platform.getOS()) ? "C:\\Projects\\MyLibrary"
				: "/var/www/MyLibrary";

		IScriptProject scriptProject = DLTKCore.create(project);
		scriptProject.setRawBuildpath(
				new IBuildpathEntry[] { DLTKCore
						.newExtLibraryEntry(EnvironmentPathUtils.getFullPath(
								LocalEnvironment.getInstance(), new Path(
										libraryPath))) }, null);

		IncludePath[] includePath = IncludePathManager.getInstance()
				.getIncludePaths(project);

		assertTrue(includePath.length == 1);
		assertTrue(includePath[0].isBuildpath());
		assertEquals(
				EnvironmentPathUtils
						.getLocalPath(
								((IBuildpathEntry) includePath[0].getEntry())
										.getPath()).toOSString(), libraryPath);
	}

	// This test checks how include path changes are saved:
	@Test
	public void includePathSet() throws Exception {
		IFolder folder = project.getFolder("a");
		folder.create(true, true, null);
		folder = folder.getFolder("b");
		folder.create(true, true, null);

		// Add new resource to the include path:
		IncludePathManager manager = IncludePathManager.getInstance();
		IncludePath[] includePath = manager.getIncludePaths(project);
		int count = includePath.length;
		System.arraycopy(includePath, 0,
				includePath = new IncludePath[count + 1], 0, count);
		includePath[count] = new IncludePath(folder, project);

		setIncludePath(manager, includePath);
		includePath = manager.getIncludePaths(project);

		assertTrue(includePath.length == 1);
		assertFalse(includePath[0].isBuildpath());
		assertEquals(((IResource) includePath[0].getEntry()), folder);
	}

	private void setIncludePath(IncludePathManager manager,
			IncludePath[] includePath) {
		IncludePathWaiter waiter = new IncludePathWaiter();
		manager.registerIncludepathListener(waiter);
		manager.setIncludePath(project, includePath);
		waiter.run();

		manager.unregisterIncludepathListener(waiter);
	}

	class IncludePathWaiter implements IIncludepathListener, Runnable {

		private boolean refreshed;

		public void refresh(IProject project) {
			this.refreshed = true;
		}

		public void run() {
			while (!refreshed) {
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
				}
			}
		}
	}
}
