/*******************************************************************************
 * Copyright (c) 2013 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.php.internal.core.project.PHPNature;
import org.eclipse.php.internal.ui.util.LibraryFolderHelper;
import org.eclipse.wst.validation.internal.PrefConstants;
import org.eclipse.wst.validation.internal.PreferencesWrapper;

/**
 * A resource change listener that automatically marks source folders as library
 * folders based on predefined set of rules.
 * 
 * @author Kaloyan Raev
 */
public class AutoDetectLibraryFolderListener implements IResourceChangeListener {

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
				PHPUiPlugin.log(e);
				continue;
			}

			if (!hasDisabledPreference(project)) {
				// TODO read folders from preferences
				final IModelElement vendorFolder = DLTKCore.create(project
						.getFolder("vendor"));

				if (vendorFolder.exists()) {
					// TODO choose better job name and externalize it
					new WorkspaceJob("Auto-detect library folders") {
						@Override
						public IStatus runInWorkspace(IProgressMonitor monitor)
								throws CoreException {
							IModelElement[] folders = new IModelElement[] { vendorFolder };

							try {
								LibraryFolderHelper.useAsLibraryFolder(folders,
										monitor);
							} catch (OperationCanceledException e) {
								return errorStatus(e);
							} catch (InterruptedException e) {
								return errorStatus(e);
							}
							return Status.OK_STATUS;
						}
					}.schedule();
				}
			}
		}
	}

	@SuppressWarnings("restriction")
	private boolean hasDisabledPreference(IProject project) {
		PreferencesWrapper prefs = PreferencesWrapper.getPreferences(project,
				null);
		return prefs.get(PrefConstants.disabled, null) != null;
	}

	private IStatus errorStatus(Exception e) {
		// TODO status message
		return new Status(IStatus.ERROR, PHPUiPlugin.ID, "", e);
	}

}
