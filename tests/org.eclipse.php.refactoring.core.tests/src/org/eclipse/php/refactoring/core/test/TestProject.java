/*******************************************************************************
 * Copyright (c) 2005, 2015 Zend Technologies and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
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
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.php.core.tests.TestUtils;

public class TestProject {

	private static int fCounter = 0;

	private IProject fProject;

	public TestProject() {
		this("TestProject_" + fCounter++);
	}

	public TestProject(String name) {
		createProject(name);
	}

	private void createProject(String name) {
		fProject = TestUtils.createProject(name);
	}

	public IProject getProject() {
		return fProject;
	}

	public void delete() throws Exception {
		TestUtils.deleteProject(fProject);
	}

	public IFile createFile(String path, String content) throws CoreException {
		IPath filePath = new Path(path);
		if (filePath.segmentCount() > 1) {
			createFolder(filePath.removeLastSegments(1));
		}
		InputStream source = new ByteArrayInputStream(content.getBytes());
		IFile file = fProject.getFile(path);
		if (!file.exists()) {
			file.create(source, true, new NullProgressMonitor());
		} else {
			file.setContents(source, IFile.FORCE, new NullProgressMonitor());
		}
		return file;
	}

	private IFolder createFolder(IPath path) throws CoreException {
		IFolder folder = fProject.getFolder(path);
		if (!folder.exists()) {
			folder.create(true, true, new NullProgressMonitor());
		}
		return folder;
	}

	public IFile findFile(String path) {
		return fProject.getFile(path);
	}

	public IFolder createFolder(String path) throws CoreException {
		IFolder folder = fProject.getFolder(path);
		if (!folder.exists()) {
			folder.create(true, true, new NullProgressMonitor());
		}
		return folder;
	}

}
