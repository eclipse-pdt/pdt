/*******************************************************************************
 * Copyright (c) 2012, 2016, 2017 PDT Extension Group and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *     Kaloyan Raev - [501269] externalize strings
 *******************************************************************************/
package org.eclipse.php.composer.core.builder;

import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.php.composer.core.ComposerPlugin;
import org.eclipse.php.composer.core.buildpath.BuildPathManager;
import org.eclipse.php.composer.core.facet.FacetManager;
import org.eclipse.php.composer.core.log.Logger;
import org.eclipse.php.composer.core.resources.IComposerProject;

/**
 * This builder is checking for changes inside the `vendor` directory and
 * adjusts the buildpath of the project accordingly.
 * 
 */
public class ComposerBuildPathManagementBuilder extends IncrementalProjectBuilder {

	public static final String ID = "org.eclipse.php.composer.core.builder.buildPathManagementBuilder"; //$NON-NLS-1$

	@Override
	protected IProject[] build(int kind, Map<String, String> args, IProgressMonitor monitor) throws CoreException {

		IProject project = getProject();

		if (!FacetManager.hasComposerFacet(project)) {
			return null;
		}

		try {
			// return when no composer.json present
			IComposerProject composerProject = ComposerPlugin.getDefault().getComposerProject(project);
			IFile composerJson = composerProject.getComposerJson();
			if (composerJson == null || composerJson.exists() == false) {
				return null;
			}

			boolean changed = false;
			IResourceDelta delta = getDelta(project);
			BuildPathManager buildPathManager = new BuildPathManager(composerProject);

			if (delta == null) {
				buildPathManager.update(monitor);

				return null;
			}

			String vendor = composerProject.getVendorDir();

			for (IResourceDelta affected : delta.getAffectedChildren()) {
				String path = affected.getProjectRelativePath().toOSString();

				if (path.equals("composer.lock") || path.equals(vendor)) { //$NON-NLS-1$
					changed = true;
				}
			}

			// nothing to do
			if (!changed) {
				return null;
			}

			buildPathManager.update(monitor);
		} catch (Exception e) {
			Logger.logException(e);
		}

		return null;
	}
}
