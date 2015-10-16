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

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
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

	private IFolder fFolder;
	private IFolder fNewFolder;

	/**
	 * Creates the change.
	 * 
	 * @param folder
	 *            the library folder to be renamed or moved
	 * @param newFolder
	 *            the renamed/moved library folder
	 */
	public RenameLibraryFolderChange(IFolder folder, IFolder newFolder) {
		fFolder = folder;
		fNewFolder = newFolder;
	}

	@Override
	public Object getModifiedElement() {
		return fFolder;
	}

	@Override
	public String getName() {
		return NLS.bind(Messages.RenameLibraryFolderChange_name, fNewFolder.getFullPath());
	}

	@Override
	public void initializeValidationData(IProgressMonitor monitor) {
	}

	@Override
	public RefactoringStatus isValid(IProgressMonitor monitor) throws CoreException, OperationCanceledException {
		return new RefactoringStatus();
	}

	@Override
	public Change perform(IProgressMonitor monitor) throws CoreException {
		// update the WTP Validation Framework for the renamed folder
		LibraryFolderManager lfm = LibraryFolderManager.getInstance();
		lfm.enableValidation(fFolder);
		lfm.disableValidation(fNewFolder);

		return new RenameLibraryFolderChange(fNewFolder, fFolder);
	}

}