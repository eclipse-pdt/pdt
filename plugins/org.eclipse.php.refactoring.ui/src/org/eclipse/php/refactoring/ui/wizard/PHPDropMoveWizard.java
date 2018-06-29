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
package org.eclipse.php.refactoring.ui.wizard;

import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.ui.refactoring.RefactoringWizard;
import org.eclipse.php.refactoring.ui.PHPRefactoringUIMessages;

/**
 * This class is responsible for the display of the move refactoring wizard and
 * it functionality.
 * 
 * @author Eden K., 2007
 * 
 */
public class PHPDropMoveWizard extends RefactoringWizard {

	public PHPDropMoveWizard(Refactoring refactoring) {
		super(refactoring, DIALOG_BASED_USER_INTERFACE | PREVIEW_EXPAND_FIRST_NODE);
		setDefaultPageTitle(PHPRefactoringUIMessages.getString("PHPMoveWizard.0")); //$NON-NLS-1$
	}

	@Override
	protected void addUserInputPages() {
		addPage(new RefactoringDropMoveWizardPage());
	}

}
