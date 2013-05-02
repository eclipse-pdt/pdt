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
package org.eclipse.php.internal.core.project;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.preferences.CorePreferenceConstants.Keys;
import org.eclipse.php.internal.core.preferences.CorePreferencesSupport;

public class ProjectOptions {

	private ProjectOptions() {
	}

	private static IProject getProject(IModelElement modelElement) {
		IScriptProject scriptProject = modelElement.getScriptProject();
		if (scriptProject != null) {
			return scriptProject.getProject();
		}
		return null;
	}

	public static PHPVersion getPhpVersion(IModelElement modelElement) {
		return getPhpVersion(getProject(modelElement));
	}

	public static PHPVersion getPhpVersion(IProject project) {
		if (project != null) {
			return PHPVersion.byAlias(CorePreferencesSupport.getInstance()
					.getPreferencesValue(Keys.PHP_VERSION, null, project));
		}
		return getDefaultPhpVersion();
	}

	public static PHPVersion getPhpVersion(IFile file) {
		PHPVersion phpVersion = ProjectOptions.getDefaultPhpVersion();
		IProject project = file.getProject();
		if (project != null && project.isAccessible()) {
			phpVersion = ProjectOptions.getPhpVersion(project);
		}
		return phpVersion;
	}

	public static PHPVersion getPhpVersion(String fileName) {
		PHPVersion phpVersion = ProjectOptions.getDefaultPhpVersion();
		IResource resource = ResourcesPlugin.getWorkspace().getRoot()
				.findMember(fileName);
		if (resource != null) {
			IProject project = resource.getProject();
			if (project.isAccessible()) {
				phpVersion = ProjectOptions.getPhpVersion(project);
			}
		}
		return phpVersion;
	}

	public static PHPVersion getDefaultPhpVersion() {
		return PHPVersion.byAlias(CorePreferencesSupport.getInstance()
				.getWorkspacePreferencesValue(Keys.PHP_VERSION));
	}

	public static boolean setPhpVersion(PHPVersion version, IProject project) {
		return CorePreferencesSupport.getInstance()
				.setProjectSpecificPreferencesValue(Keys.PHP_VERSION,
						version.getAlias(), project);
	}

	public static final boolean isSupportingAspTags(IProject project) {
		return project == null ? false : Boolean.valueOf(
				CorePreferencesSupport.getInstance().getPreferencesValue(
						Keys.EDITOR_USE_ASP_TAGS, null, project))
				.booleanValue();
	}

	public static boolean useShortTags(IProject project) {
		String useShortTags = CorePreferencesSupport.getInstance()
				.getPreferencesValue(Keys.EDITOR_USE_SHORT_TAGS, null, project);
		return "true".equals(useShortTags); //$NON-NLS-1$
	}

	public static boolean useShortTags(IModelElement modelElement) {
		return useShortTags(getProject(modelElement));
	}

	public static final boolean isSupportingAspTags(IFile file) {
		return isSupportingAspTags(file.getProject());
	}

	public static final boolean setSupportingAspTags(boolean value,
			IProject project) {
		return CorePreferencesSupport.getInstance()
				.setProjectSpecificPreferencesValue(Keys.EDITOR_USE_ASP_TAGS,
						Boolean.toString(value), project);
	}
}
