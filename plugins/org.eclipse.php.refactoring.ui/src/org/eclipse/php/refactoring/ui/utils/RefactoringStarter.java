/*******************************************************************************
 * Copyright (c) 2007, 2015 Zend Technologies and others.
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
package org.eclipse.php.refactoring.ui.utils;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.ui.refactoring.RefactoringWizard;
import org.eclipse.ltk.ui.refactoring.RefactoringWizardOpenOperation;
import org.eclipse.php.internal.ui.dialogs.saveFiles.SaveFilesHandler;
import org.eclipse.php.internal.ui.dialogs.saveFiles.SaveFilesHandler.SaveFilesResult;
import org.eclipse.swt.widgets.Shell;

/**
 * Convienience class to be called on refactoring start. Some basic checks are
 * done and the refactoring wizard is launched
 * 
 * @author Eden K., 2007
 * 
 */
public class RefactoringStarter {

	private RefactoringStatus fStatus;

	/**
	 * Activates the refactoring. Beforehand checks if there are unsaved (dirty)
	 * editors in case the mustSaveEditors is on.
	 * 
	 * @param refactoring
	 * @param wizard
	 * @param parent
	 * @param dialogTitle
	 * @param mustSaveEditors
	 */
	public boolean activate(Refactoring refactoring, RefactoringWizard wizard, Shell parent, String dialogTitle,
			boolean mustSaveEditors) {
		if (!canActivate(mustSaveEditors, parent)) {
			return false;
		}

		try {
			RefactoringWizardOpenOperation op = new RefactoringWizardOpenOperation(wizard);
			int result = op.run(parent, dialogTitle);
			fStatus = op.getInitialConditionCheckingStatus();
			if (result == IDialogConstants.CANCEL_ID
					|| result == RefactoringWizardOpenOperation.INITIAL_CONDITION_CHECKING_FAILED) {
				// TODO ???
				// fSaveHelper.triggerBuild();
			} else if (result == IDialogConstants.OK_ID) {
				return true;
			}
		} catch (InterruptedException e) {
			// do nothing. User action got canceled
		}
		return false;
	}

	public RefactoringStatus getInitialConditionCheckingStatus() {
		return fStatus;
	}

	private boolean canActivate(boolean mustSaveEditors, Shell shell) {
		return !mustSaveEditors || checkUnsavedFiles();
	}

	/**
	 * Check if there are any unsaved files in the workspace and whether the user
	 * selected that he wants to save them before the refactoring
	 * 
	 * @return boolean value
	 * 
	 */
	private boolean checkUnsavedFiles() {
		boolean autoSave = false;

		SaveFilesResult result = SaveFilesHandler.handle(null, autoSave, false, new NullProgressMonitor());
		if (!result.isAccepted()) {
			return false;
		}

		return true;

	}
}
