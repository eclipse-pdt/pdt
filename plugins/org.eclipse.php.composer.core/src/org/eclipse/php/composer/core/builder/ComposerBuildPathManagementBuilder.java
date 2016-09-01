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
package org.eclipse.php.composer.core.builder;

import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.php.composer.core.ComposerNature;
import org.eclipse.php.composer.core.ComposerPlugin;
import org.eclipse.php.composer.core.buildpath.BuildPathManager;
import org.eclipse.php.composer.core.log.Logger;
import org.eclipse.php.composer.core.resources.IComposerProject;

/**
 * This builder is checking for changes inside the `vendor` directory and
 * adjusts the buildpath of the project accordingly.
 * 
 */
public class ComposerBuildPathManagementBuilder extends
		IncrementalProjectBuilder {

	public static final String ID = "org.eclipse.php.composer.core.builder.buildPathManagementBuilder";

	@Override
	protected IProject[] build(int kind, Map<String, String> args,
			IProgressMonitor monitor) throws CoreException {

		IProject project = getProject();

		if (project.hasNature(ComposerNature.NATURE_ID) == false) {
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
				if (project.hasNature(ComposerNature.NATURE_ID)) {
					buildPathManager.update(monitor);
				}
				
				return null;
			}

			String vendor = composerProject.getVendorDir();

			for (IResourceDelta affected : delta.getAffectedChildren()) {
				String path = affected.getProjectRelativePath().toOSString();
				
				if (path.equals("composer.lock") || path.equals(vendor)) {
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
