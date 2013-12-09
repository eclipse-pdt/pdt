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

	public void resourceChanged(IResourceChangeEvent event) {
		if (event.getType() != IResourceChangeEvent.POST_CHANGE)
			return;

		IResourceDelta delta = event.getDelta();

		// find all projects in the delta that may potentially have folders to
		// be marked as library folders
		IProject[] projects = new OpenedAndChangedProjectsFinder(delta)
				.getFoundProjects();

		// schedule a workspace job to process the projects
		new AutoDetectLibraryFolderJob(projects).schedule();
	}

}
