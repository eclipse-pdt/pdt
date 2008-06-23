/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.phpModel;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.core.BuildpathContainerInitializer;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.DLTKLanguageManager;
import org.eclipse.dltk.core.IBuildpathContainer;
import org.eclipse.dltk.core.IBuildpathEntry;
import org.eclipse.dltk.core.IBuiltinModuleProvider;
import org.eclipse.dltk.core.IDLTKLanguageToolkit;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IProjectFragment;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.core.environment.EnvironmentManager;
import org.eclipse.dltk.core.environment.EnvironmentPathUtils;
import org.eclipse.dltk.core.environment.IEnvironment;
import org.eclipse.dltk.internal.core.BuildpathEntry;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.phpModel.parser.PHPVersion;
import org.eclipse.php.internal.core.preferences.IPreferencesPropagatorListener;
import org.eclipse.php.internal.core.preferences.PreferencesPropagatorEvent;
import org.eclipse.php.internal.core.project.PHPNature;
import org.eclipse.php.internal.core.project.properties.handlers.PhpVersionChangedHandler;
import org.eclipse.php.internal.core.project.properties.handlers.PhpVersionProjectPropertyHandler;
import org.eclipse.php.internal.core.util.project.observer.IProjectClosedObserver;
import org.eclipse.php.internal.core.util.project.observer.ProjectRemovedObserversAttacher;

public class LanguageModelInitializer extends BuildpathContainerInitializer {

	public static final String CONTAINER_PATH = PHPCorePlugin.ID + ".LANGUAGE"; //$NON-NLS-1$
	private static final String LANGUAGE_LIBRARY_PATH = "Resources/language/php%d"; //$NON-NLS-1$
	private IPreferencesPropagatorListener phpVersionListener;
	private String phpVersion;
	private IBuildpathEntry[] buildPathEntries;

	public LanguageModelInitializer() {
	}

	private void initializeListener(final IPath containerPath, final IScriptProject project) {

		if (phpVersionListener != null) {
			return;
		}

		phpVersionListener = new IPreferencesPropagatorListener() {
			public void preferencesEventOccured(PreferencesPropagatorEvent event) {
				phpVersion = (String) event.getNewValue();
				buildPathEntries = null;
				try {
					initialize(containerPath, project);
				} catch (CoreException e) {
					Logger.logException(e);
				}
			}

			public IProject getProject() {
				return project.getProject();
			}
		};
		PhpVersionChangedHandler.getInstance().addPhpVersionChangedListener(phpVersionListener);

		ProjectRemovedObserversAttacher.getInstance().addProjectClosedObserver(project.getProject(), new IProjectClosedObserver() {
			public void closed() {
				PhpVersionChangedHandler.getInstance().removePhpVersionChangedListener(phpVersionListener);
				phpVersionListener = null;
			}
		});
	}

	public void initialize(IPath containerPath, IScriptProject project) throws CoreException {
		if (containerPath.segmentCount() > 0 && containerPath.segment(0).equals(CONTAINER_PATH)) {
			try {
				if (isPHPProject(project)) {

					phpVersion = PhpVersionProjectPropertyHandler.getVersion(project.getProject());

					DLTKCore.setBuildpathContainer(containerPath, new IScriptProject[] { project }, new IBuildpathContainer[] { new LanguageModelContainer(containerPath) }, null);

					initializeListener(containerPath, project);
				}
			} catch (Exception e) {
				Logger.logException(e);
			}
		}
	}

	private static IPath getContainerPath(IScriptProject project, String phpVersion) throws IOException {
		String libraryPath = getLanguageLibraryPath(project, phpVersion);

		URL url = FileLocator.find(PHPCorePlugin.getDefault().getBundle(), new Path(libraryPath), null);
		URL resolved = FileLocator.resolve(url);
		IPath path = Path.fromOSString(resolved.getFile());

		return path;
	}

	private static String getLanguageLibraryPath(IScriptProject project, String phpVersion) {
		return String.format(LANGUAGE_LIBRARY_PATH, PHPVersion.PHP4.equals(phpVersion) ? 4 : 5);
	}

	private static boolean isPHPProject(IScriptProject project) {
		String nature = getNatureFromProject(project);
		return PHPNature.ID.equals(nature);
	}

	private static String getNatureFromProject(IScriptProject project) {
		IDLTKLanguageToolkit languageToolkit = DLTKLanguageManager.getLanguageToolkit(project);
		if (languageToolkit != null) {
			return languageToolkit.getNatureId();
		}
		return null;
	}

	public static boolean isLanguageModelElement(IModelElement element) {
		IProjectFragment fragment = (IProjectFragment) element.getAncestor(IModelElement.PROJECT_FRAGMENT);
		if (fragment != null) {
		
			IScriptProject project = element.getScriptProject();
			if (project != null) {
			
				String phpVersion = PhpVersionProjectPropertyHandler.getVersion(project.getProject());
				try {
					IPath containerPath = getContainerPath(project, phpVersion);
					return EnvironmentPathUtils.getLocalPath(fragment.getPath()).equals(containerPath);
				} catch (IOException e) {
					Logger.logException(e);
				}
			}
		}
		return false;
	}

	/**
	 * Modifies PHP project buildpath so it will contain path to the language model library
	 * @param project Project handle
	 * @throws ModelException
	 */
	public static void enableLanguageModelFor(IScriptProject project) throws ModelException {
		if (!isPHPProject(project)) {
			return;
		}

		IPath containerPath = new Path(LanguageModelInitializer.CONTAINER_PATH);

		boolean found = false;

		IBuildpathEntry[] rawBuildpath = project.getRawBuildpath();
		for (IBuildpathEntry entry : rawBuildpath) {
			if (entry.isContainerEntry() && entry.getPath().equals(containerPath)) {
				found = true;
				break;
			}
		}

		if (!found) {
			IBuildpathEntry containerEntry = DLTKCore.newContainerEntry(containerPath);
			int newSize = rawBuildpath.length + 1;
			List<IBuildpathEntry> newRawBuildpath = new ArrayList<IBuildpathEntry>(newSize);
			newRawBuildpath.addAll(Arrays.asList(rawBuildpath));
			newRawBuildpath.add(containerEntry);
			project.setRawBuildpath(newRawBuildpath.toArray(new IBuildpathEntry[newSize]), null);
		}
	}

	class LanguageModelContainer implements IBuildpathContainer {

		private IPath containerPath;

		public LanguageModelContainer(IPath containerPath) {
			this.containerPath = containerPath;
		}

		public IBuildpathEntry[] getBuildpathEntries(IScriptProject project) {
			if (buildPathEntries == null) {
				IEnvironment environment = EnvironmentManager.getEnvironment(project);
				try {
					IPath path = getContainerPath(project, phpVersion);
					if (environment != null) {
						path = EnvironmentPathUtils.getFullPath(environment, path);
					}
					buildPathEntries = new IBuildpathEntry[] { DLTKCore.newLibraryEntry(path, BuildpathEntry.NO_ACCESS_RULES, BuildpathEntry.NO_EXTRA_ATTRIBUTES, BuildpathEntry.INCLUDE_ALL, BuildpathEntry.EXCLUDE_NONE, false, true) };
				} catch (Exception e) {
					Logger.logException(e);
				}
			}
			return buildPathEntries;
		}

		public IBuiltinModuleProvider getBuiltinProvider(IScriptProject project) {
			return null;
		}

		public String getDescription(IScriptProject project) {
			return "PHP Language Library";
		}

		public int getKind() {
			return K_SYSTEM;
		}

		public IPath getPath() {
			return containerPath;
		}
	}
}
