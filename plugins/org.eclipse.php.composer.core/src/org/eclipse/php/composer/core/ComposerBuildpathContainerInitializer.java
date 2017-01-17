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
package org.eclipse.php.composer.core;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.core.*;
import org.eclipse.php.composer.core.model.ComposerBuildpathContainer;
import org.eclipse.php.composer.core.model.ModelAccess;
import org.eclipse.php.composer.core.model.PackageManager;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.preferences.IPreferencesPropagatorListener;
import org.eclipse.php.internal.core.preferences.PreferencesPropagatorEvent;
import org.eclipse.php.internal.core.project.PHPNature;
import org.eclipse.php.internal.core.project.PhpVersionChangedHandler;
import org.eclipse.php.internal.core.util.project.observer.IProjectClosedObserver;
import org.eclipse.php.internal.core.util.project.observer.ProjectRemovedObserversAttacher;

/**
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class ComposerBuildpathContainerInitializer extends BuildpathContainerInitializer {

	public static final String CONTAINER = ComposerPlugin.ID + ".CONTAINER"; //$NON-NLS-1$

	private Map<IProject, IPreferencesPropagatorListener> project2PhpVerListener = new HashMap<IProject, IPreferencesPropagatorListener>();

	@Override
	public void initialize(IPath containerPath, IScriptProject scriptProject) throws CoreException {
		if (containerPath.segmentCount() > 0 && containerPath.segment(0).equals(CONTAINER)
				&& ComposerPlugin.getDefault().isBuildpathContainerEnabled()) {
			try {
				if (isPHPProject(scriptProject)) {
					DLTKCore.setBuildpathContainer(containerPath, new IScriptProject[] { scriptProject },
							new IBuildpathContainer[] { new ComposerBuildpathContainer(containerPath, scriptProject) },
							null);
					initializeListener(containerPath, scriptProject);
				}
			} catch (Exception e) {
				Logger.logException(e);
			}
		}
	}

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
				project2PhpVerListener.remove(project);
			}
		});
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

	public void requestBuildpathContainerUpdate(IPath containerPath, IScriptProject project,
			IBuildpathContainer containerSuggestion) {

		if (isComposerContainer(containerPath)) {
			String name = containerPath.segment(1);
			if (containerSuggestion != null) {

				PackageManager manager = ModelAccess.getInstance().getPackageManager();

				if (manager.getPackage(name) == null) {
					return;
				}

				manager.setPackage(name, containerSuggestion.getBuildpathEntries(),
						containerSuggestion.getKind() == IBuildpathContainer.K_SYSTEM);
			} else {
				ModelAccess.getInstance().getPackageManager().removePackage(name);
			}
		}

	}

	private boolean isComposerContainer(IPath path) {
		return path != null && CONTAINER.equals(path.segment(0));
	}

	@Override
	public boolean canUpdateBuildpathContainer(IPath containerPath, IScriptProject project) {
		return isComposerContainer(containerPath);
	}
}
