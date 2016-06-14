/*******************************************************************************
 * Copyright (c) 2014 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.libfolders;

import java.util.*;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;

/**
 * A resource change listener that automatically marks source folders as library
 * folders based on predefined set of rules.
 * 
 * @author Kaloyan Raev
 */
public class AutoDetectLibraryFolderListener implements IResourceChangeListener {

	private final Set<IProject> suspendedProjects = Collections.synchronizedSet(new HashSet<IProject>());
	private boolean suspendDetection = false;

	@Override
	public void resourceChanged(IResourceChangeEvent event) {
		if (suspendDetection)
			return;

		if (event.getType() != IResourceChangeEvent.POST_CHANGE)
			return;

		IResourceDelta delta = event.getDelta();

		// find all projects in the delta that may potentially have folders to
		// be marked as library folders
		IProject[] projects = new OpenedAndChangedProjectsFinder(delta).getFoundProjects();
		if (projects.length == 0) {
			return;
		}
		projects = filterSuspended(projects);
		if (projects.length == 0) {
			return;
		}
		// schedule a workspace job to process the projects
		new AutoDetectLibraryFolderJob(projects).schedule();
	}

	/**
	 * WARNING: This method should not be used by the clients.
	 * 
	 * Enables/disables detection for given project.
	 * 
	 * @param project
	 * @param suspend
	 */
	public void suspendDetection(IProject project, boolean suspend) {
		if (suspend)
			suspendedProjects.add(project);
		else
			suspendedProjects.remove(project);
	}

	/**
	 * WARNING: This method should not be used by the clients.
	 * 
	 * Enables/disables whole detection.
	 * 
	 * @param suspend
	 */
	public void suspendAllDetection(boolean suspend) {
		suspendDetection = suspend;
	}

	private IProject[] filterSuspended(IProject[] projects) {
		List<IProject> filtered = new ArrayList<IProject>();
		for (int i = 0; i < projects.length; i++) {
			if (!suspendedProjects.contains(projects[i])) {
				filtered.add(projects[i]);
			}
		}
		return filtered.toArray(new IProject[filtered.size()]);
	}

}
