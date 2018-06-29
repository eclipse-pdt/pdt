/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.core.tests.includepath;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IBuildpathEntry;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.environment.EnvironmentPathUtils;
import org.eclipse.dltk.core.internal.environment.LocalEnvironment;
import org.eclipse.php.core.tests.TestSuiteWatcher;
import org.eclipse.php.core.tests.TestUtils;
import org.eclipse.php.internal.core.includepath.IIncludepathListener;
import org.eclipse.php.internal.core.includepath.IncludePath;
import org.eclipse.php.internal.core.includepath.IncludePathManager;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

public class IncludePathManagerTests {

	@ClassRule
	public static TestWatcher watcher = new TestSuiteWatcher();

	protected IProject project;

	@Before
	public void setUp() throws Exception {
		// Initialize include path manager:
		IncludePathManager.getInstance();
		project = TestUtils.createProject("IncludePathManagerTests");
	}

	@After
	public void tearDown() throws Exception {
		TestUtils.deleteProject(project);
	}

	@Test
	public void includePathGet() throws Exception {
		IScriptProject scriptProject = DLTKCore.create(project);
		scriptProject.setRawBuildpath(new IBuildpathEntry[0], null);

		IncludePath[] includePath = IncludePathManager.getInstance().getIncludePaths(project);
		assertTrue(includePath.length == 0);
	}

	// This test checks how buildpath changes are not propogated to the include
	// path:
	@Test
	public void includePathGetAfterBPChange1() throws Exception {
		IFolder folder = project.getFolder("a");
		folder.create(true, true, null);

		IScriptProject scriptProject = DLTKCore.create(project);
		scriptProject.setRawBuildpath(new IBuildpathEntry[] { DLTKCore.newSourceEntry(folder.getFullPath()) }, null);

		IncludePath[] includePath = IncludePathManager.getInstance().getIncludePaths(project);
		assertTrue(includePath.length == 0);
	}

	// This test checks how buildpath changes are propogated to the include
	// path:
	@Test
	public void includePathGetAfterBPChange2() throws Exception {
		File extLibrary = new File(new File(ResourcesPlugin.getWorkspace().getRoot().getLocationURI()), "MyLibrary");
		extLibrary.mkdir();
		String libraryPath = extLibrary.getAbsolutePath();

		IScriptProject scriptProject = DLTKCore.create(project);
		scriptProject.setRawBuildpath(
				new IBuildpathEntry[] { DLTKCore.newExtLibraryEntry(
						EnvironmentPathUtils.getFullPath(LocalEnvironment.getInstance(), new Path(libraryPath))) },
				null);

		IncludePath[] includePath = IncludePathManager.getInstance().getIncludePaths(project);

		assertTrue(includePath.length == 1);
		assertTrue(includePath[0].isBuildpath());
		assertEquals(
				EnvironmentPathUtils.getLocalPath(((IBuildpathEntry) includePath[0].getEntry()).getPath()).toOSString(),
				libraryPath);
		extLibrary.delete();
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
		System.arraycopy(includePath, 0, includePath = new IncludePath[count + 1], 0, count);
		includePath[count] = new IncludePath(folder, project);

		setIncludePath(manager, includePath);
		includePath = manager.getIncludePaths(project);

		assertTrue(includePath.length == 1);
		assertFalse(includePath[0].isBuildpath());
		assertEquals((includePath[0].getEntry()), folder);
	}

	private void setIncludePath(IncludePathManager manager, IncludePath[] includePath) {
		IncludePathWaiter waiter = new IncludePathWaiter();
		manager.registerIncludepathListener(waiter);
		manager.setIncludePath(project, includePath);
		waiter.run();

		manager.unregisterIncludepathListener(waiter);
	}

	class IncludePathWaiter implements IIncludepathListener, Runnable {

		private boolean refreshed;

		@Override
		public void refresh(IProject project) {
			this.refreshed = true;
		}

		@Override
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
