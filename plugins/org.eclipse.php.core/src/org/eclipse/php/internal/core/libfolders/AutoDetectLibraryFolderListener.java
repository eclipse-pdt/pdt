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

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.php.core.libfolders.ILibraryFolderNameProvider;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.project.PHPNature;
import org.eclipse.wst.validation.internal.PrefConstants;
import org.eclipse.wst.validation.internal.PreferencesWrapper;

/**
 * A resource change listener that automatically marks source folders as library
 * folders based on predefined set of rules.
 * 
 * @author Kaloyan Raev
 */
public class AutoDetectLibraryFolderListener implements IResourceChangeListener {

	private static final String EXTENSION_POINT_ID = "org.eclipse.php.core.libraryFolderNameProviders"; //$NON-NLS-1$
	private static final String CLASS_ATTR = "class"; //$NON-NLS-1$

	public void resourceChanged(IResourceChangeEvent event) {
		if (event.getType() != IResourceChangeEvent.POST_CHANGE)
			return;

		IResourceDelta rootDelta = event.getDelta();
		IResourceDelta[] projectDeltas = rootDelta.getAffectedChildren();
		for (IResourceDelta projectDelta : projectDeltas) {
			if ((projectDelta.getFlags() & (IResourceDelta.OPEN | IResourceDelta.DESCRIPTION)) == 0)
				continue;

			IResource resource = projectDelta.getResource();
			if (resource.getType() != IResource.PROJECT)
				// not a project
				continue;

			IProject project = (IProject) resource;
			if (!project.isOpen())
				// the project is closed
				continue;

			try {
				if (!project.hasNature(PHPNature.ID))
					// not a PHP project
					continue;
			} catch (CoreException e) {
				PHPCorePlugin.log(e);
				continue;
			}

			if (!hasDisabledPreference(project)) {
				String[] names = getCommonLibraryFolderNames(project);
				IModelElement[] folders = getFoldersFromNames(project, names);

				// mark the folders as library folder in a separate job
				new AutoDetectLibraryFolderJob(project, folders).schedule();
			}
		}
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
	@SuppressWarnings("restriction")
	private boolean hasDisabledPreference(IProject project) {
		PreferencesWrapper prefs = PreferencesWrapper.getPreferences(project,
				null);
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
	 * Converts the given array of folder names to an array of model elements
	 * representing them in the given project.
	 * 
	 * <p>
	 * A folder name is converted to a model element only if it represents an
	 * existing folder in the given project.
	 * </p>
	 * 
	 * @param project
	 *            a project
	 * @param folderNames
	 *            an array of folder names
	 * 
	 * @return an array of model elements
	 */
	private IModelElement[] getFoldersFromNames(IProject project,
			String[] folderNames) {
		Collection<IModelElement> result = new HashSet<IModelElement>();

		for (String name : folderNames) {
			IModelElement folder = DLTKCore.create(project.getFolder(name));
			if (folder.exists()) {
				result.add(folder);
			}
		}

		return result.toArray(new IModelElement[result.size()]);
	}

}
