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
package org.eclipse.php.core.libfolders;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.osgi.util.NLS;

/**
 * A refactoring change that updates the WTP Validation Framework when a library
 * folder is renamed or moved.
 * 
 * @author Kaloyan Raev
 */
public class RenameLibraryFolderChange extends Change {

	private IModelElement fElement;
	private IModelElement fNewElement;

	/**
	 * Creates the change.
	 * 
	 * @param element
	 *            the model element of the library folder to be renamed or moved
	 * @param newElement
	 *            the model element of the renamed/moved library folder
	 */
	public RenameLibraryFolderChange(IModelElement element,
			IModelElement newElement) {
		fElement = element;
		fNewElement = newElement;
	}

	@Override
	public Object getModifiedElement() {
		return fElement;
	}

	@Override
	public String getName() {
		return NLS.bind(Messages.RenameLibraryFolderChange_name,
				fNewElement.getElementName());
	}

	@Override
	public void initializeValidationData(IProgressMonitor monitor) {
	}

	@Override
	public RefactoringStatus isValid(IProgressMonitor monitor)
			throws CoreException, OperationCanceledException {
		return new RefactoringStatus();
	}

	@Override
	public Change perform(IProgressMonitor monitor) throws CoreException {
		// update the WTP Validation Framework for the renamed folder
		LibraryFolderManager lfm = LibraryFolderManager.getInstance();
		lfm.enableValidation(fElement);
		lfm.disableValidation(fNewElement);

		return new RenameLibraryFolderChange(fNewElement, fElement);
	}

}