/*******************************************************************************
 * Copyright (c) 2005, 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.core.test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.php.core.tests.PHPCoreTests;
import org.eclipse.php.internal.core.project.PHPNature;

public class TestProject {
	IProject project;

	public TestProject() {
		this("TestProject");
	}

	public TestProject(String name) {
		createProject(name);
	}

	private void createProject(String name) {
		project = ResourcesPlugin.getWorkspace().getRoot().getProject(name);
		if (project.exists()) {
			return;
		}

		try {
			project.create(null);
			project.open(IResource.BACKGROUND_REFRESH, new NullProgressMonitor());
			IProjectDescription desc = project.getDescription();
			desc.setNatureIds(new String[] { PHPNature.ID });
			project.setDescription(desc, null);

			project.refreshLocal(IResource.DEPTH_INFINITE, null);
			project.build(IncrementalProjectBuilder.FULL_BUILD, null);

			PHPCoreTests.waitForIndexer();
			PHPCoreTests.waitForAutoBuild();

		} catch (CoreException e) {
			e.printStackTrace();
		}

	}

	public boolean exists() {
		return project != null && project.exists();
	}

	public IProject getProject() {
		return project;
	}

	public void delete() throws Exception {
		if (project != null && project.exists())
			project.delete(true, new NullProgressMonitor());
	}

	public IFile createFile(String path, String content) throws CoreException {
		IPath filePath = new Path(path);
		if (filePath.segmentCount() > 1) {
			createFolder(filePath.removeLastSegments(1));
		}

		if (project != null) {
			InputStream source = new ByteArrayInputStream(content.getBytes());
			IFile file = project.getFile(path);
			if (!file.exists()) {
				file.create(source, true, new NullProgressMonitor());
			} else {
				file.setContents(source, IFile.FORCE, new NullProgressMonitor());
			}
			return file;
		}
		return null;
	}

	private IFolder createFolder(IPath path) throws CoreException {
		if (project != null) {
			IFolder folder = project.getFolder(path);
			if (!folder.exists()) {
				folder.create(true, true, new NullProgressMonitor());
			}
			return folder;
		}
		return null;
	}

	public IFile findFile(String path) {
		if (project != null) {
			return project.getFile(path);
		}
		return null;
	}

	public IFolder createFolder(String path) throws CoreException {
		if (project != null) {
			IFolder folder = project.getFolder(path);
			if (!folder.exists()) {
				folder.create(true, true, new NullProgressMonitor());
			}
			return folder;
		}
		return null;

	}
}
