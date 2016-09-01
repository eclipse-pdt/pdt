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
package org.eclipse.php.composer.ui.explorer;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.dltk.core.IBuildpathEntry;
import org.eclipse.dltk.core.IProjectFragment;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.internal.core.ExternalProjectFragment;
import org.eclipse.dltk.internal.ui.navigator.ScriptExplorerContentProvider;
import org.eclipse.php.composer.core.ComposerNature;
import org.eclipse.php.composer.core.ComposerPlugin;
import org.eclipse.php.composer.core.log.Logger;
import org.eclipse.php.composer.core.model.PackagePath;

@SuppressWarnings("restriction")
public class PackageTreeContentProvider extends ScriptExplorerContentProvider {
	public PackageTreeContentProvider() {
		super(true);
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if (ComposerPlugin.getDefault().isBuildpathContainerEnabled() == false) {
			return null;
		}

		if (parentElement instanceof PackagePath) {

			PackagePath pPath = (PackagePath) parentElement;
			IScriptProject scriptProject = pPath.getProject();
			IBuildpathEntry entry = pPath.getEntry();

			try {

				IProjectFragment[] allProjectFragments;
				allProjectFragments = scriptProject.getAllProjectFragments();
				for (IProjectFragment fragment : allProjectFragments) {
					if (fragment instanceof ExternalProjectFragment) {
						ExternalProjectFragment external = (ExternalProjectFragment) fragment;
						if (external.getBuildpathEntry().equals(entry)) {
							return super.getChildren(external);
						}
					}
				}
			} catch (ModelException e) {
				Logger.logException(e);
			}
		} else if (parentElement instanceof ComposerBuildpathContainer) {
			ComposerBuildpathContainer container = (ComposerBuildpathContainer) parentElement;

			IAdaptable[] children = container.getChildren();

			if (children == null || children.length == 0) {
				return NO_CHILDREN;
			}

			return children;
		} else if (parentElement instanceof IScriptProject) {
			try {
				IProject project = ((IScriptProject) parentElement)
						.getProject();
				if (project.hasNature(ComposerNature.NATURE_ID)) {
					return new Object[] { new ComposerBuildpathContainer(
							(IScriptProject) parentElement) };
				}
			} catch (Exception e) {
				Logger.logException(e);
			}
		}
		return null;
	}
}
