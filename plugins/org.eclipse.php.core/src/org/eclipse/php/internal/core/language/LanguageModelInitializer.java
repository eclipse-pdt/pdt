/*******************************************************************************
 * Copyright (c) 2009, 2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *     Dawid Pakuła [456902]
 *******************************************************************************/
package org.eclipse.php.internal.core.language;

import java.io.File;
import java.util.*;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileSystem;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.*;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.core.environment.EnvironmentPathUtils;
import org.eclipse.dltk.core.internal.environment.LocalEnvironment;
import org.eclipse.dltk.internal.core.search.ProjectIndexerManager;
import org.eclipse.php.core.PHPToolkitUtil;
import org.eclipse.php.core.language.ILanguageModelProvider;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.preferences.IPreferencesPropagatorListener;
import org.eclipse.php.internal.core.preferences.PreferencesPropagatorEvent;
import org.eclipse.php.internal.core.preferences.TaskPatternsProvider;
import org.eclipse.php.internal.core.project.PHPNature;
import org.eclipse.php.internal.core.project.PhpVersionChangedHandler;
import org.eclipse.php.internal.core.util.project.observer.IProjectClosedObserver;
import org.eclipse.php.internal.core.util.project.observer.ProjectRemovedObserversAttacher;

public class LanguageModelInitializer extends BuildpathContainerInitializer {

	public static final String PHP_LANGUAGE_LIBRARY = "PHP Language Library"; //$NON-NLS-1$

	/**
	 * Path of the language model for php projects
	 */
	public static final String CONTAINER_PATH = PHPCorePlugin.ID + ".LANGUAGE"; //$NON-NLS-1$
	public static final Path LANGUAGE_CONTAINER_PATH = new Path(LanguageModelInitializer.CONTAINER_PATH);

	private static final String LANGUAGE_PREFIX = "__language__"; //$NON-NLS-1$

	/**
	 * Listeners for PHP version change map (per project)
	 */
	private Map<IProject, IPreferencesPropagatorListener> project2PhpVerListener = new HashMap<IProject, IPreferencesPropagatorListener>();

	/**
	 * Language model paths initializers
	 */
	private static ILanguageModelProvider[] providers;

	/**
	 * Holds nice names for the language model paths
	 */
	private static Map<IPath, String> pathToName = Collections.synchronizedMap(new HashMap<IPath, String>());

	static void addPathName(IPath path, String name) {
		pathToName.put(path, name);
	}

	/**
	 * Returns nice name for this language model path provided by the
	 * {@link ILanguageModelProvider}. If the path doesn't refer to the language
	 * model path - <code>null</code> is returned.
	 * 
	 * @return
	 */
	public static String getPathName(IPath path) {
		return pathToName.get(path);
	}

	/**
	 * Initialize version change listener for the given project
	 * 
	 * @param containerPath
	 * @param scriptProject
	 */
	private void initializeListener(final IPath containerPath, final IScriptProject scriptProject) {
		final IProject project = scriptProject.getProject();
		if (project2PhpVerListener.containsKey(project)) {
			return;
		}
		IPreferencesPropagatorListener versionChangeListener = new IPreferencesPropagatorListener() {
			public void preferencesEventOccured(PreferencesPropagatorEvent event) {
				try {
					// Re-initialize when PHP version changes
					initialize(containerPath, scriptProject);
				} catch (CoreException e) {
					Logger.logException(e);
				}
			}

			public IProject getProject() {
				return project;
			}
		};

		project2PhpVerListener.put(project, versionChangeListener);
		PhpVersionChangedHandler.getInstance().addPhpVersionChangedListener(versionChangeListener);

		ProjectRemovedObserversAttacher.getInstance().addProjectClosedObserver(project, new IProjectClosedObserver() {
			public void closed() {
				PhpVersionChangedHandler.getInstance()
						.removePhpVersionChangedListener(project2PhpVerListener.get(project));
				TaskPatternsProvider.unregisterProject(project);
				project2PhpVerListener.remove(project);
			}
		});
	}

	@Override
	public void initialize(IPath containerPath, IScriptProject scriptProject) throws CoreException {
		if (containerPath.segmentCount() > 0 && containerPath.segment(0).equals(CONTAINER_PATH)) {
			try {
				if (isPHPProject(scriptProject)) {
					DLTKCore.setBuildpathContainer(containerPath, new IScriptProject[] { scriptProject },
							new IBuildpathContainer[] { new LanguageModelContainer(containerPath, scriptProject) },
							null);
					initializeListener(containerPath, scriptProject);
				}
			} catch (Exception e) {
				Logger.logException(e);
			}
		}
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
		if (element != null) {
			IProjectFragment fragment = (IProjectFragment) element.getAncestor(IModelElement.PROJECT_FRAGMENT);
			if (fragment != null && fragment.isExternal()) {
				IPath path = fragment.getPath();

				// see getTargetLocation() below for description:
				if (path.segmentCount() > 2) {
					return LANGUAGE_PREFIX.equals(path.segment(path.segmentCount() - 2));
				}
			}
		}
		return false;
	}

	/**
	 * Modifies PHP project buildpath so it will contain path to the language
	 * model library
	 * 
	 * @param project
	 *            Project handle
	 * @throws ModelException
	 */
	public static void enableLanguageModelFor(IScriptProject project) throws ModelException {
		if (!isPHPProject(project)) {
			return;
		}

		boolean found = false;
		IBuildpathEntry[] rawBuildpath = project.getRawBuildpath();
		for (IBuildpathEntry entry : rawBuildpath) {
			if (entry.getEntryKind() == IBuildpathEntry.BPE_CONTAINER
					&& entry.getPath().equals(LANGUAGE_CONTAINER_PATH)) {
				found = true;
				break;
			}
		}

		if (!found) {
			IBuildpathEntry containerEntry = DLTKCore.newContainerEntry(LANGUAGE_CONTAINER_PATH);
			int newSize = rawBuildpath.length + 1;
			List<IBuildpathEntry> newRawBuildpath = new ArrayList<IBuildpathEntry>(newSize);
			newRawBuildpath.addAll(Arrays.asList(rawBuildpath));
			newRawBuildpath.add(containerEntry);
			project.setRawBuildpath(newRawBuildpath.toArray(new IBuildpathEntry[newSize]), null);
		}
	}

	static ILanguageModelProvider[] getContributedProviders() {
		if (LanguageModelInitializer.providers == null) {
			List<ILanguageModelProvider> providers = new LinkedList<ILanguageModelProvider>();
			providers.add(new DefaultLanguageModelProvider()); // add default

			IConfigurationElement[] elements = Platform.getExtensionRegistry()
					.getConfigurationElementsFor("org.eclipse.php.core.languageModelProviders"); //$NON-NLS-1$
			for (IConfigurationElement element : elements) {
				if (element.getName().equals("provider")) { //$NON-NLS-1$
					try {
						providers.add((ILanguageModelProvider) element.createExecutableExtension("class")); //$NON-NLS-1$
					} catch (CoreException e) {
						PHPCorePlugin.log(e);
					}
				}
			}
			LanguageModelInitializer.providers = (ILanguageModelProvider[]) providers
					.toArray(new ILanguageModelProvider[providers.size()]);
		}
		return LanguageModelInitializer.providers;
	}

	static IPath getTargetLocation(ILanguageModelProvider provider, IPath sourcePath, IScriptProject project) {

		return provider.getPlugin().getStateLocation().append(LANGUAGE_PREFIX)
				.append(Integer.toHexString(sourcePath.toOSString().hashCode()));
	}

	/**
	 * Drop unsused patches
	 * 
	 * @see http://eclip.se/456902
	 * @param projects
	 * @throws CoreException
	 */
	public static void cleanup(IProgressMonitor monitor) throws CoreException {
		final IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		Set<IPath> inUse = new HashSet<IPath>();
		Set<IPath> toDrop = new HashSet<IPath>();
		for (IProject project : projects) {
			if (!PHPToolkitUtil.isPHPProject(project)) {
				continue;
			}
			IBuildpathContainer container = DLTKCore.getBuildpathContainer(Path.fromPortableString(CONTAINER_PATH),
					DLTKCore.create(project));
			if (container == null) {
				continue;
			}
			for (IBuildpathEntry entry : container.getBuildpathEntries()) {
				inUse.add(entry.getPath());
			}
		}

		for (ILanguageModelProvider provider : getContributedProviders()) {
			File dir = provider.getPlugin().getStateLocation().append(LANGUAGE_PREFIX).toFile();
			if (!dir.exists() || !dir.isDirectory()) {
				continue;
			}
			for (File lib : dir.listFiles()) {
				IPath compare = EnvironmentPathUtils.getFullPath(LocalEnvironment.ENVIRONMENT_ID,
						Path.fromOSString(lib.getPath()));
				if (inUse.contains(compare)) {
					continue;
				}

				toDrop.add(compare);
			}
		}

		final IFileSystem efs = EFS.getLocalFileSystem();

		for (IPath path : toDrop) {
			ProjectIndexerManager.removeProject(path);
			efs.getStore(EnvironmentPathUtils.getLocalPath(path)).delete(EFS.NONE, monitor);
		}
	}
}
