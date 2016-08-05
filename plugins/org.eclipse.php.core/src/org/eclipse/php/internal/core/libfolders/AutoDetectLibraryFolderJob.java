/*******************************************************************************
 * Copyright (c) 2014 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.libfolders;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.*;
import org.eclipse.php.core.libfolders.ILibraryFolderNameProvider;
import org.eclipse.php.core.libfolders.LibraryFolderManager;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.wst.validation.internal.PrefConstants;
import org.eclipse.wst.validation.internal.PreferencesWrapper;

/**
 * A workspace job for automatically detecting library folders in a given array
 * of projects.
 * 
 * <p>
 * The job consults the library folder name providers registered in the
 * <code>org.eclipse.php.core.libraryFolderNameProviders</code> extension point
 * for finding the source folders to be marked as library folders.
 * </p>
 * 
 * @author Kaloyan Raev
 */
public class AutoDetectLibraryFolderJob extends WorkspaceJob {

	private static final String EXTENSION_POINT_ID = "org.eclipse.php.core.libraryFolderNameProviders"; //$NON-NLS-1$
	private static final String CLASS_ATTR = "class"; //$NON-NLS-1$

	private IProject[] fProjects;

	/**
	 * Constructor for the job.
	 * 
	 * @param projects
	 *            an array of projects
	 */
	public AutoDetectLibraryFolderJob(IProject[] projects) {
		super(Messages.AutoDetectLibraryFolderJob_Name);
		fProjects = projects;
	}

	@Override
	public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {

		LibraryFolderManager lfm = LibraryFolderManager.getInstance();

		for (IProject project : fProjects) {
			// check if the user has already marked any folders as library
			// folders or source folders
			if (!hasDisabledPreference(project)) {
				// check the extension point for common library folder names
				String[] names = getCommonLibraryFolderNames(project);

				// find these folders in the project
				IFolder[] folders = getFoldersFromNames(project, names);

				// mark the folders as library folders
				try {
					lfm.useAsLibraryFolder(folders, monitor);
				} catch (Exception e) {
					return new Status(IStatus.ERROR, PHPCorePlugin.ID, e.getLocalizedMessage(), e);
				}
			}
		}

		return Status.OK_STATUS;
	}

	/**
	 * Returns whether the given project has the "disabled" preference available
	 * in its WTP Validation Framework preference file.
	 * 
	 * @param project
	 *            a project
	 * 
	 * @return <code>true</code> if the "disable" preference is available, and
	 *         <code>false</code> otherwise.
	 */
	private boolean hasDisabledPreference(IProject project) {
		PreferencesWrapper prefs = PreferencesWrapper.getPreferences(project, null);
		return prefs.get(PrefConstants.disabled, null) != null;
	}

	/**
	 * Returns the common library folder names by consulting the library folder
	 * name providers registered in the extension point.
	 * 
	 * @param project
	 *            a project
	 * 
	 * @return an array of folder names
	 */
	private String[] getCommonLibraryFolderNames(IProject project) {
		Collection<String> result = new HashSet<String>();

		IConfigurationElement[] elements = Platform.getExtensionRegistry()
				.getConfigurationElementsFor(EXTENSION_POINT_ID);

		for (IConfigurationElement element : elements) {
			try {
				Object o = element.createExecutableExtension(CLASS_ATTR);
				if (o instanceof ILibraryFolderNameProvider) {
					ILibraryFolderNameProvider extension = (ILibraryFolderNameProvider) o;
					String[] names = extension.getLibraryFolderNames(project);
					if (names != null) {
						result.addAll(Arrays.asList(names));
					}
				}
			} catch (Exception e) {
				PHPCorePlugin.log(e);
			}
		}

		return result.toArray(new String[result.size()]);
	}

	/**
	 * Converts the given array of folder names to an array of folders in the
	 * given project.
	 * 
	 * <p>
	 * A folder name is converted to a folder only if it exists in the given
	 * project.
	 * </p>
	 * 
	 * @param project
	 *            a project
	 * @param folderNames
	 *            an array of folder names
	 * 
	 * @return an array of folders
	 */
	private IFolder[] getFoldersFromNames(IProject project, String[] folderNames) {
		Collection<IFolder> result = new HashSet<IFolder>();

		for (String name : folderNames) {
			IFolder folder = project.getFolder(name);
			if (folder != null && folder.exists()) {
				result.add(folder);
			}
		}

		return result.toArray(new IFolder[result.size()]);
	}

}
