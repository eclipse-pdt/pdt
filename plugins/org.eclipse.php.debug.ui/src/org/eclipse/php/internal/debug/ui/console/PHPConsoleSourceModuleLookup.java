/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.ui.console;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.core.DLTKLanguageManager;
import org.eclipse.dltk.core.IDLTKLanguageToolkit;
import org.eclipse.dltk.core.IProjectFragment;
import org.eclipse.dltk.core.IScriptFolder;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.core.environment.EnvironmentPathUtils;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.dltk.internal.core.Model;
import org.eclipse.dltk.internal.core.ModelManager;
import org.eclipse.dltk.internal.core.ProjectFragment;
import org.eclipse.dltk.internal.core.util.Util;

public class PHPConsoleSourceModuleLookup {

	private final IDLTKSearchScope scope;
	private final Model model;

	public PHPConsoleSourceModuleLookup(IDLTKLanguageToolkit toolkit) {
		this.model = ModelManager.getModelManager().getModel();
		scope = SearchEngine.createWorkspaceScope(toolkit);
	}

	private IProject[] getAllProjects() {
		return ResourcesPlugin.getWorkspace().getRoot().getProjects();
	}

	private boolean checkScope(IProject project, IPath[] scopeProjectsAndZips) {
		final IPath location = project.getFullPath();
		for (int j = 0; j < scopeProjectsAndZips.length; j++) {
			if (scopeProjectsAndZips[j].equals(location)) {
				return true;
			}
		}
		return false;
	}

	public ISourceModule findSourceModuleByLocalPath(final IPath path) {
		final boolean isFullPath = EnvironmentPathUtils.isFull(path);
		final IProject[] projects = getAllProjects();
		final IPath[] enclosingProjectsAndZips = scope.enclosingProjectsAndZips();
		for (int i = 0, max = projects.length; i < max; i++) {
			try {
				final IProject project = projects[i];
				if (!checkScope(project, enclosingProjectsAndZips)) {
					continue;
				}
				if (!project.isAccessible() || !DLTKLanguageManager.hasScriptNature(project))
					continue;

				IScriptProject scriptProject = model.getScriptProject(project);
				final ISourceModule module = findInProject(scriptProject, path, isFullPath);
				if (module != null) {
					return module.exists() ? module : null;
				}

			} catch (CoreException e) {
				// CoreException from hasNature - should not happen since we
				// check that the project is accessible
				// ModelException from getProjectFragments - a problem occurred
				// while accessing project: nothing we can do, ignore
			}
		}
		return null;
	}

	private ISourceModule findInProject(IScriptProject scriptProject, IPath path, boolean isFullPath) throws ModelException {
		IProjectFragment[] roots = scriptProject.getProjectFragments();
		for (int j = 0, rootCount = roots.length; j < rootCount; j++) {
			final ProjectFragment root = (ProjectFragment) roots[j];
			IPath rootPath = root.getPath();
			if (!isFullPath) {
				rootPath = EnvironmentPathUtils.getLocalPath(rootPath);
			}
			if (rootPath.isPrefixOf(path) && !Util.isExcluded(path, root.fullInclusionPatternChars(), root.fullExclusionPatternChars(), false)) {
				IPath localPath = path.setDevice(null).removeFirstSegments(rootPath.segmentCount());
				if (localPath.segmentCount() >= 1) {
					final IScriptFolder folder;
					if (localPath.segmentCount() > 1) {
						folder = root.getScriptFolder(localPath.removeLastSegments(1));
					} else {
						folder = root.getScriptFolder(""); //$NON-NLS-1$
					}
					return folder.getSourceModule(localPath.lastSegment());
				}
			}
		}
		return null;
	}

}
