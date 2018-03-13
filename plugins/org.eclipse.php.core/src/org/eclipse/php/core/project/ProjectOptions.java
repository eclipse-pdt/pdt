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

import org.eclipse.core.internal.resources.Project;
import org.eclipse.core.internal.resources.Workspace;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
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

	// https://bugs.eclipse.org/bugs/show_bug.cgi?id=459395
	// XXX: the code formatter preview UI should always use latest php version to
	// render php code samples, independently from the workspace default php version
	// (or any project-specific php version).
	// Ideally we should be able to set the php version in classes PHPScriptRegion,
	// PHPSourceParser and PHPTokenizer (without defining an IProject), but let's do
	// it simple for now by defining a fake DUMMY_PREVIEW_PROJECT project that will
	// be used by class PHPPreview.
	public static final Project DUMMY_PREVIEW_PROJECT = new Project(new Path("/"), //$NON-NLS-1$
			(Workspace) ResourcesPlugin.getWorkspace()) {
	};

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
		if (project == DUMMY_PREVIEW_PROJECT) {
			return PHPVersion.getLatestVersion();
		}
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
		if (project == DUMMY_PREVIEW_PROJECT) {
			return PHPVersion.getLatestVersion();
		}
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

	public static boolean setPHPVersion(@NonNull PHPVersion version, @NonNull IProject project) {
		if (project == DUMMY_PREVIEW_PROJECT) {
			return true;
		}
		return CorePreferencesSupport.getInstance().setProjectSpecificPreferencesValue(Keys.PHP_VERSION,
				version.getAlias(), project);
	}

	public static final boolean isSupportingASPTags(@Nullable IProject project) {
		if (project == DUMMY_PREVIEW_PROJECT) {
			return false;
		}
		String useShortTags = CorePreferencesSupport.getInstance().getPreferencesValue(Keys.EDITOR_USE_ASP_TAGS,
				"false", //$NON-NLS-1$
				project);
		return "false".equals(useShortTags); //$NON-NLS-1$
	}

	public static boolean useShortTags(@Nullable IProject project) {
		if (project == DUMMY_PREVIEW_PROJECT) {
			return true;
		}
		String useShortTags = CorePreferencesSupport.getInstance().getPreferencesValue(Keys.EDITOR_USE_SHORT_TAGS,
				"true", //$NON-NLS-1$
				project);
		return "true".equals(useShortTags); //$NON-NLS-1$
	}

	public static boolean useShortTags(@NonNull IModelElement modelElement) {
		return useShortTags(getProject(modelElement));
	}

	public static final boolean isSupportingASPTags(@NonNull IFile file) {
		return isSupportingASPTags(file.getProject());
	}

	public static final boolean setSupportingASPTags(boolean value, @NonNull IProject project) {
		if (project == DUMMY_PREVIEW_PROJECT) {
			return true;
		}
		return CorePreferencesSupport.getInstance().setProjectSpecificPreferencesValue(Keys.EDITOR_USE_ASP_TAGS,
				Boolean.toString(value), project);
	}
}
