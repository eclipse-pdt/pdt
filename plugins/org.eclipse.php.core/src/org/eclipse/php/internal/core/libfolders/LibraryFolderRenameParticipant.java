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
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.RenameParticipant;
import org.eclipse.php.core.libfolders.LibraryFolderManager;
import org.eclipse.php.core.libfolders.RenameLibraryFolderChange;

/**
 * A rename participant that watches if a topmost library folder is renamed.
 * 
 * @author Kaloyan Raev
 */
public class LibraryFolderRenameParticipant extends RenameParticipant {

	/**
	 * The model element of the renamed library folder.
	 */
	private IModelElement fElement;

	@Override
	public RefactoringStatus checkConditions(IProgressMonitor monitor,
			CheckConditionsContext context) throws OperationCanceledException {
		return new RefactoringStatus();
	}

	@Override
	public Change createChange(IProgressMonitor monitor) throws CoreException,
			OperationCanceledException {
		IProject project = fElement.getScriptProject().getProject();

		String newName = getArguments().getNewName();
		IModelElement newElement = DLTKCore.create(project.getFolder(newName));

		return new RenameLibraryFolderChange(fElement, newElement);
	}

	@Override
	public String getName() {
		// not displayed anywhere
		return ""; //$NON-NLS-1$
	}

	@Override
	protected boolean initialize(Object element) {
		if (!(element instanceof IModelElement))
			return false;

		fElement = (IModelElement) element;
		LibraryFolderManager lfm = LibraryFolderManager.getInstance();

		// the participant takes place only if the model element is a topmost
		// library folder, i.e. it is explicitly disabled in the WTP Validation
		// Framework
		return lfm.isTopmostLibraryFolder(fElement);
	}

}
