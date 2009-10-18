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
package org.eclipse.php.internal.core.util.project.observer;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.*;

/**
 * The class is a utility class for attaching observers 2 the event of project
 * closed.
 * 
 * After a project is deleted and all the observers r notified, the observers r
 * removed, because the project cannont be closed again. Currently, there is no
 * option 2 attach an observer 2 a closed project. If an observer need 2 be
 * attached, upon opening the project, attach the observer.
 */
public class ProjectRemovedObserversAttacher {

	private static ProjectRemovedObserversAttacher instance = new ProjectRemovedObserversAttacher();

	private Map project2CompositeProjectChangeObserver = new HashMap();
	private IResourceChangeListener resourceChangeListener;

	public static ProjectRemovedObserversAttacher getInstance() {
		return instance;
	}

	private ProjectRemovedObserversAttacher() {
	}

	/**
	 * There is no need for a remove method because oll the observer
	 * automatically removed when a project is closed
	 * 
	 * @param project
	 * @param projectChangeObserver
	 */
	public boolean addProjectClosedObserver(IProject project,
			IProjectClosedObserver projectChangeObserver) {
		if (resourceChangeListener == null) {
			resourceChangeListener = new IResourceChangeListener() {
				public void resourceChanged(IResourceChangeEvent event) {
					IResourceDelta resourceDelta = event.getDelta();
					if (resourceDelta == null) {
						return;
					}
					// always the workspace so the children must be projects
					IResourceDelta[] affectedChildren = resourceDelta
							.getAffectedChildren(IResourceDelta.CHANGED);
					if (affectedChildren.length > 0) {
						handleClosedProjects(affectedChildren);
					} else {
						affectedChildren = resourceDelta
								.getAffectedChildren(IResourceDelta.REMOVED);
						handleRemovedProjects(affectedChildren);
					}
				}
			};
		}
		ResourcesPlugin.getWorkspace().addResourceChangeListener(
				resourceChangeListener);

		if (project == null || !project.isAccessible()) {
			return false;
		}
		CompositeProjectChangeObserver compositeProjectChangeObserver = getCompositeProjectChangeObserver(project);
		compositeProjectChangeObserver.add(projectChangeObserver);
		return true;
	}

	private void handleRemovedProjects(IResourceDelta[] affectedChildren) {
		for (int i = 0; i < affectedChildren.length; i++) {
			IResourceDelta resourceDelta = affectedChildren[i];
			IResource resource = resourceDelta.getResource();
			IProject project = (IProject) resource;
			this.notifyProjectClosed(project);
		}
	}

	private void notifyProjectClosed(IProject project) {
		getCompositeProjectChangeObserver(project).closed();
		removeCompositeProjectChangeObserver(project);
	}

	private void handleClosedProjects(IResourceDelta[] affectedChildren) {
		for (int i = 0; i < affectedChildren.length; i++) {
			IResourceDelta resourceDelta = affectedChildren[i];
			IResource resource = resourceDelta.getResource();
			IProject project = (IProject) resource;
			int eventFlags = resourceDelta.getFlags();
			if ((eventFlags & IResourceDelta.OPEN) != 0) {
				// could be an OPEN or CLOSE
				if (!project.isOpen()) {
					this.notifyProjectClosed(project);
				}
			}
		}
	}

	private void removeCompositeProjectChangeObserver(IProject project) {
		CompositeProjectChangeObserver compositeProjectChangeObserver = (CompositeProjectChangeObserver) project2CompositeProjectChangeObserver
				.remove(project);
		compositeProjectChangeObserver.clear();
		if (project2CompositeProjectChangeObserver.isEmpty()) {
			ResourcesPlugin.getWorkspace().removeResourceChangeListener(
					resourceChangeListener);
		}
	}

	private CompositeProjectChangeObserver getCompositeProjectChangeObserver(
			IProject project) {
		Object object = project2CompositeProjectChangeObserver.get(project);
		if (object == null) {
			CompositeProjectChangeObserver compositeProjectChangeObserver = new CompositeProjectChangeObserver();
			project2CompositeProjectChangeObserver.put(project,
					compositeProjectChangeObserver);
			return compositeProjectChangeObserver;
		}

		return (CompositeProjectChangeObserver) object;
	}
}
