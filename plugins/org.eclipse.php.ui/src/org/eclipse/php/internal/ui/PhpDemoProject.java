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
package org.eclipse.php.internal.ui;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.php.internal.core.project.PHPNature;

/**
 * This is a factory project class for the PHP Demo use -pd for gettingthis
 */
public class PhpDemoProject {

	private static final String SRC_FOLDER = "src/";	 //$NON-NLS-1$
	private static final String FILES_PATH = "/resources/phpdemo/"; 	 //$NON-NLS-1$
	private static final String TEST_PROJECT_NAME = "PHPDemo"; 	 //$NON-NLS-1$
	private static final String FILE_NAME1 = "01-Visibility1.php"; //$NON-NLS-1$
	private static final String FILE_NAME2 = "02-Visibility2.php"; //$NON-NLS-1$
	private static final String FILE_NAME3 = "03-html.php"; //$NON-NLS-1$
	private static final String FILE_NAME5 = "05-Inherit.php"; //$NON-NLS-1$
	private static final String FILE_NAME6 = "06-FunctionParameter.php"; //$NON-NLS-1$
	private static final String FILE_NAME7 = "07-ClassExtension.php"; //$NON-NLS-1$
	private static final String FILE_NAME8 = "08-MemberReference.php"; //$NON-NLS-1$
	private static final String FILE_NAME9 = "09-Including.php"; //$NON-NLS-1$
	private static final String FILE_NAME10 = "Employee.php"; //$NON-NLS-1$
	private static final String FILE_NAME11 = "Manager.php"; //$NON-NLS-1$
	private static final String FILE_NAME12 = "Person.php"; //$NON-NLS-1$
	private static final String SRC_FILE_NAME1 = "01-included.php"; //$NON-NLS-1$

	public PhpDemoProject() {
	}

	public static void run() {

		final IProject testProject = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(TEST_PROJECT_NAME);

		if (testProject.exists()) {
			return;
		}
		try {
			// create Example project
			testProject.create(null);
			testProject.open(null);
			providePhpNatureToProject(testProject);

			final IFile file = createFile(testProject, FILES_PATH, FILE_NAME1);
			createFile(testProject, FILES_PATH, FILE_NAME2);
			createFile(testProject, FILES_PATH, FILE_NAME3);
			createFile(testProject, FILES_PATH, FILE_NAME5);
			createFile(testProject, FILES_PATH, FILE_NAME6);
			createFile(testProject, FILES_PATH, FILE_NAME7);
			createFile(testProject, FILES_PATH, FILE_NAME8);
			createFile(testProject, FILES_PATH, FILE_NAME9);
			createFile(testProject, FILES_PATH, FILE_NAME10);
			createFile(testProject, FILES_PATH, FILE_NAME11);
			createFile(testProject, FILES_PATH, FILE_NAME12);

			createFolder(testProject, FILES_PATH, SRC_FOLDER);
			createFile(testProject, FILES_PATH, SRC_FOLDER + SRC_FILE_NAME1);
		} catch (Exception e) {
			Logger.logException(e);
		}
	}

	private static IFile createFile(final IProject testProject,
			String filePath, String fileName) throws IOException, CoreException {
		URL demoFileURL = FileLocator.find(Platform.getBundle(PHPUiPlugin
				.getPluginId()), new Path(filePath + fileName), null);
		demoFileURL = FileLocator.resolve(demoFileURL);
		IPath p = testProject.getFullPath();
		p = p.append(fileName);
		final IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(p);
		InputStream inputStream = (InputStream) demoFileURL.getContent();
		file.create(inputStream, true, null);
		return file;
	}

	private static IFolder createFolder(final IProject testProject,
			String filePath, String folderName) throws IOException,
			CoreException {
		URL demoFileURL = FileLocator.find(Platform.getBundle(PHPUiPlugin
				.getPluginId()), new Path(filePath + folderName), null);
		demoFileURL = FileLocator.resolve(demoFileURL);
		IPath p = testProject.getFullPath();
		p = p.append(folderName);
		final IFolder folder = ResourcesPlugin.getWorkspace().getRoot()
				.getFolder(p);
		folder.create(true, true, null);
		return folder;
	}

	// this method provides a PHP nature to the created project that is given
	private static void providePhpNatureToProject(IProject project)
			throws ExecutionException {
		try {
			IProjectDescription desc = null;

			// configure .project
			String[] natureIds = new String[] { PHPNature.ID };
			if (null != natureIds) {
				desc = project.getDescription();
				desc.setNatureIds(natureIds);
				project.setDescription(desc, null);
			}

		} catch (CoreException e) {
			Logger.logException(e);
		}
	}
}
