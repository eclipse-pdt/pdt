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

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.RenameParticipant;
import org.eclipse.php.core.libfolders.LibraryFolderManager;
import org.eclipse.php.core.libfolders.RenameLibraryFolderChange;

/**
 * A rename participant that watches if an explicitly disabled library folder is
 * renamed.
 * 
 * @author Kaloyan Raev
 */
public class LibraryFolderRenameParticipant extends RenameParticipant {

	/**
	 * The renamed library folder.
	 */
	private IFolder fFolder;

	@Override
	public RefactoringStatus checkConditions(IProgressMonitor monitor, CheckConditionsContext context)
			throws OperationCanceledException {
		return new RefactoringStatus();
	}

	@Override
	public Change createChange(IProgressMonitor monitor) throws CoreException, OperationCanceledException {
		IProject project = fFolder.getProject();

		String newName = getArguments().getNewName();
		IFolder newFolder = project.getFolder(newName);

		return new RenameLibraryFolderChange(fFolder, newFolder);
	}

	@Override
	public String getName() {
		// not displayed anywhere
		return ""; //$NON-NLS-1$
	}

	@Override
	protected boolean initialize(Object element) {
		fFolder = getFolder(element);
		if (fFolder == null)
			return false;

		LibraryFolderManager lfm = LibraryFolderManager.getInstance();

		// the participant takes place only if the folder is explicitly disabled
		// in the WTP Validation Framework
		return lfm.isExplicitlyDisabled(fFolder);
	}

	/**
	 * Returns the folder, represented by the given element, to be refactored.
	 * 
	 * @param element
	 *            the element to be refactored
	 * 
	 * @return the folder to be refactored or <code>null</code> if no folder can
	 *         be determined by the given element.
	 */
	private IFolder getFolder(Object element) {
		if (element instanceof IFolder) {
			return (IFolder) element;
		}

		if (element instanceof IModelElement) {
			IModelElement modelElement = (IModelElement) element;
			IResource resource = modelElement.getResource();

			if (resource == null)
				return null;

			if (resource.getType() == IResource.FOLDER)
				return (IFolder) resource;
		}

		return null;
	}

}
