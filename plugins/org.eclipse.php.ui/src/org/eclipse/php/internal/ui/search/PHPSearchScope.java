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
package org.eclipse.php.internal.ui.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.php.internal.core.phpModel.phpElementData.CodeData;

public class PHPSearchScope implements IPHPSearchScope {

	protected static final IPath[] EMPTY_PATHS = new IPath[0];
	protected HashSet fullProjects;
	protected HashSet partialProjects;
	protected HashMap paths;
	protected int searchFor;

	public PHPSearchScope(int searchFor) {
		this.searchFor = searchFor;
		fullProjects = new HashSet(5);
		partialProjects = new HashSet(5);
		paths = new HashMap(); // A map from project to a List of IPaths of resources.
	}

	public IProject[] getAllEnclosingProjects() {
		IProject[] allEnclosingProjects = new IProject[fullProjects.size()];
		IProject[] partialEnclosingProjects = new IProject[partialProjects.size()];
		fullProjects.toArray(allEnclosingProjects);
		partialProjects.toArray(partialEnclosingProjects);
		IProject[] projects = new IProject[allEnclosingProjects.length + partialEnclosingProjects.length];
		System.arraycopy(allEnclosingProjects, 0, projects, 0, allEnclosingProjects.length);
		System.arraycopy(partialEnclosingProjects, 0, projects, allEnclosingProjects.length, partialEnclosingProjects.length);
		return projects;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.ui.search.IPHPSearchScope#getFullScopeProjects()
	 */
	public IProject[] getFullScopeProjects() {
		IProject[] projects = new IProject[fullProjects.size()];
		fullProjects.toArray(projects);
		return projects;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.ui.search.IPHPSearchScope#getPartialScopeProjects()
	 */
	public IProject[] getPartialScopeProjects() {
		IProject[] projects = new IProject[partialProjects.size()];
		partialProjects.toArray(projects);
		return projects;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.ui.search.IPHPSearchScope#getPartialResourcesPaths()
	 */
	public IPath[] getPartialResourcesPaths(IProject project) {
		List allPaths = (List) paths.get(project);
		if (allPaths == null) {
			return EMPTY_PATHS;
		}
		IPath[] allPathsArr = new IPath[allPaths.size()];
		allPaths.toArray(allPathsArr);
		return allPathsArr;
	}

	public boolean isInScope(CodeData codeData) {
		IAdaptable adaptable = (IAdaptable) codeData;
		IFile file = (IFile) (adaptable.getAdapter(IResource.class));

		// First, check if the resource is contained in one of the added projects.
		Iterator iterator = fullProjects.iterator();
		while (iterator.hasNext()) {
			if (((IProject) iterator.next()).exists(file.getFullPath())) {
				return true;
			}
		}

		// Check if the resource is contained in one of the resources.
		Iterator pathsIterator = paths.entrySet().iterator();
		while (pathsIterator.hasNext()) {
			Map.Entry entry = (Map.Entry) pathsIterator.next();
			List values = (List) entry.getValue();
			if (values.contains(file.getFullPath())) {
				return true;
			}
		}
		return false;
	}

	public void add(IProject project) {
		if (project.isAccessible()) {
			fullProjects.add(project);
		}
		// Remove any reference from the partial projects since it is now in the full projects scope.
		// And also remove the mapping for the resources paths. 
		partialProjects.remove(project);
		paths.remove(project);
	}

	public void add(IResource resource) {
		IProject project = resource.getProject();
		if (!fullProjects.contains(project)) {
			partialProjects.add(project);
		}
		List list = (List) paths.get(project);
		if (list == null) {
			list = new ArrayList(5);
			paths.put(project, list);
		}
		list.add(resource.getFullPath());
	}

	public int getSearchFor() {
		return searchFor;
	}

}
