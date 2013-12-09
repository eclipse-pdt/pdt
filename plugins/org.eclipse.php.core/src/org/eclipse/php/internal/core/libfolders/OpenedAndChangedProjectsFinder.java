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

import java.util.Collection;
import java.util.HashSet;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.project.PHPNature;

/**
 * A resource delta visitor that finds in a resource delta all project that are
 * opened or have a new folder added.
 * 
 * @author Kaloyan Raev
 */
public class OpenedAndChangedProjectsFinder implements IResourceDeltaVisitor {

	private IResourceDelta fDelta;

	private Collection<IProject> fFoundProjects;

	/**
	 * Creates a new finder for the given resource delta.
	 * 
	 * @param delta
	 *            a resource delta
	 */
	public OpenedAndChangedProjectsFinder(IResourceDelta delta) {
		fDelta = delta;
	}

	public boolean visit(IResourceDelta delta) throws CoreException {
		IResource resource = delta.getResource();
		if (resource == null)
			return false;

		switch (resource.getType()) {
		case IResource.ROOT:
			return visitWorkspaceRoot(delta);
		case IResource.PROJECT:
			return visitProject(delta);
		case IResource.FOLDER:
			return visitFolder(delta);
		default:
			return false;
		}
	}

	/**
	 * Returns all found projects in the resource delta.
	 * 
	 * @return an array of projects
	 */
	public IProject[] getFoundProjects() {
		if (fFoundProjects == null) {
			fFoundProjects = new HashSet<IProject>();
			try {
				fDelta.accept(this);
			} catch (CoreException e) {
				PHPCorePlugin.log(e);
			}
		}

		return fFoundProjects.toArray(new IProject[fFoundProjects.size()]);
	}

	private boolean visitWorkspaceRoot(IResourceDelta delta) {
		return delta.getKind() == IResourceDelta.CHANGED;
	}

	private boolean visitProject(IResourceDelta delta) throws CoreException {
		if ((delta.getFlags() & (IResourceDelta.OPEN | IResourceDelta.DESCRIPTION)) == 0)
			// visit project folders
			return true;

		IProject project = (IProject) delta.getResource();
		if (!project.isOpen())
			// the project is closed
			return false;

		if (!project.hasNature(PHPNature.ID))
			// not a PHP project
			return false;

		// add the project to found projects
		fFoundProjects.add(project);

		// no need to visit project folders
		return false;
	}

	private boolean visitFolder(IResourceDelta delta) {
		if (delta.getKind() == IResourceDelta.ADDED) {
			// add folder's project to found projects
			IProject project = delta.getResource().getProject();
			fFoundProjects.add(project);

			// no need to visit subfolders
			return false;
		}

		// visit subfolders
		return true;
	}

}
