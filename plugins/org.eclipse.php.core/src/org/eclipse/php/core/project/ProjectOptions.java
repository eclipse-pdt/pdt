/*******************************************************************************
 * Copyright (c) 2009, 2016, 2018 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *     Dawid Pakuła - API Version
 *******************************************************************************/
package org.eclipse.php.core.project;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.dltk.annotations.NonNull;
import org.eclipse.dltk.annotations.Nullable;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.internal.core.preferences.CorePreferenceConstants.Keys;
import org.eclipse.php.internal.core.preferences.CorePreferencesSupport;

/**
 * @since 4.1
 */
public class ProjectOptions {

	private ProjectOptions() {
	}

	private static @Nullable IProject getProject(@NonNull IModelElement modelElement) {
		IScriptProject scriptProject = modelElement.getScriptProject();
		if (scriptProject != null) {
			return scriptProject.getProject();
		}
		return null;
	}

	public static PHPVersion getPHPVersion(@NonNull IModelElement modelElement) {
		return getPHPVersion(getProject(modelElement));
	}

	public static PHPVersion getPHPVersion(IProject project, PHPVersion defaultPHPVersion) {
		if (project != null) {
			PHPVersion version = PHPVersion
					.byAlias(CorePreferencesSupport.getInstance().getPreferencesValue(Keys.PHP_VERSION, null, project));
			if (version != null) {
				return version;
			}
		}
		return defaultPHPVersion;
	}

	public static PHPVersion getPHPVersion(IProject project) {
		if (project != null) {
			PHPVersion version = PHPVersion
					.byAlias(CorePreferencesSupport.getInstance().getPreferencesValue(Keys.PHP_VERSION, null, project));
			if (version != null) {
				return version;
			}
		}
		return getDefaultPHPVersion();
	}

	public static PHPVersion getPHPVersion(@NonNull IFile file) {
		PHPVersion phpVersion = ProjectOptions.getDefaultPHPVersion();
		IProject project = file.getProject();
		if (project != null && project.isAccessible()) {
			phpVersion = ProjectOptions.getPHPVersion(project);
		}
		return phpVersion;
	}

	public static PHPVersion getPHPVersion(@NonNull String fileName) {
		PHPVersion phpVersion = ProjectOptions.getDefaultPHPVersion();
		IResource resource = ResourcesPlugin.getWorkspace().getRoot().findMember(fileName);
		if (resource != null) {
			IProject project = resource.getProject();
			if (project.isAccessible()) {
				phpVersion = ProjectOptions.getPHPVersion(project);
			}
		}
		return phpVersion;
	}

	public static PHPVersion getDefaultPHPVersion() {
		return PHPVersion.byAlias(CorePreferencesSupport.getInstance().getWorkspacePreferencesValue(Keys.PHP_VERSION));
	}

	public static boolean getDefaultIsSupportingASPTags() {
		PHPVersion phpVersion = getDefaultPHPVersion();
		if (phpVersion.isGreaterThan(PHPVersion.PHP5_6)) {
			return false;
		}
		return "true".equalsIgnoreCase( //$NON-NLS-1$
				CorePreferencesSupport.getInstance().getWorkspacePreferencesValue(Keys.EDITOR_USE_ASP_TAGS));
	}

	public static boolean getDefaultUseShortTags() {
		return "true".equalsIgnoreCase( //$NON-NLS-1$
				CorePreferencesSupport.getInstance().getWorkspacePreferencesValue(Keys.EDITOR_USE_SHORT_TAGS));
	}

	public static boolean setPHPVersion(@NonNull PHPVersion version, @NonNull IProject project) {
		return CorePreferencesSupport.getInstance().setProjectSpecificPreferencesValue(Keys.PHP_VERSION,
				version.getAlias(), project);
	}

	public static final boolean isSupportingASPTags(@Nullable IProject project) {
		PHPVersion phpVersion = getPHPVersion(project);
		if (phpVersion.isGreaterThan(PHPVersion.PHP5_6)) {
			return false;
		}
		String isSupportingASPTags = project != null
				? CorePreferencesSupport.getInstance().getPreferencesValue(Keys.EDITOR_USE_ASP_TAGS, null, project)
				: null;
		if (isSupportingASPTags == null) {
			return getDefaultIsSupportingASPTags();
		}
		return "true".equalsIgnoreCase(isSupportingASPTags); //$NON-NLS-1$
	}

	public static boolean useShortTags(@Nullable IProject project) {
		String useShortTags = project != null
				? CorePreferencesSupport.getInstance().getPreferencesValue(Keys.EDITOR_USE_SHORT_TAGS, null, project)
				: null;
		if (useShortTags == null) {
			return getDefaultUseShortTags();
		}
		return "true".equalsIgnoreCase(useShortTags); //$NON-NLS-1$
	}

	public static boolean useShortTags(@NonNull IModelElement modelElement) {
		return useShortTags(getProject(modelElement));
	}

	public static final boolean setUseShortTags(boolean value, @NonNull IProject project) {
		return CorePreferencesSupport.getInstance().setProjectSpecificPreferencesValue(Keys.EDITOR_USE_SHORT_TAGS,
				Boolean.toString(value), project);
	}

	public static final boolean isSupportingASPTags(@NonNull IFile file) {
		return isSupportingASPTags(file.getProject());
	}

	public static boolean isSupportingASPTags(@NonNull IModelElement modelElement) {
		return isSupportingASPTags(getProject(modelElement));
	}

	public static final boolean setSupportingASPTags(boolean value, @NonNull IProject project) {
		return CorePreferencesSupport.getInstance().setProjectSpecificPreferencesValue(Keys.EDITOR_USE_ASP_TAGS,
				Boolean.toString(value), project);
	}
}
