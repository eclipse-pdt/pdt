/*******************************************************************************
 * Copyright (c) 2011, 2015 Zend Technologies and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.core.changes;

import java.net.URI;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

public class PHPProjectMoveChange extends Change {

	private URI fNewLocation;
	private String fProjectName;
	private IProject fProject;

	public PHPProjectMoveChange(URI newLocation, String projectName) {
		this.fNewLocation = newLocation;
		this.fProjectName = projectName;
		fProject = ResourcesPlugin.getWorkspace().getRoot().getProject(fProjectName);
	}

	@Override
	public Object getModifiedElement() {
		return fProject;
	}

	@Override
	public String getName() {
		return Messages.PHPProjectMoveChange_0;
	}

	@Override
	public void initializeValidationData(IProgressMonitor arg0) {

	}

	@Override
	public RefactoringStatus isValid(IProgressMonitor arg0) throws CoreException, OperationCanceledException {
		return new RefactoringStatus();
	}

	@Override
	public Change perform(IProgressMonitor monitor) throws CoreException {

		monitor.setTaskName("Moving project..."); //$NON-NLS-1$

		IProjectDescription description = fProject.getDescription();
		// Record the original path so this can be undone
		URI newDestinationURI = description.getLocationURI();
		// Set the new location into the project's description
		// URIUtil.toURI(fNewLocation)
		description.setLocationURI(fNewLocation);

		fProject.move(description, IResource.FORCE | IResource.SHALLOW, monitor);

		return new PHPProjectMoveChange(newDestinationURI, fProjectName);
	}
}