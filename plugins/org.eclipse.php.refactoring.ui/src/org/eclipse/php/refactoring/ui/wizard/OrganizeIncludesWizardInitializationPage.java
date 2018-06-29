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

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.ltk.ui.refactoring.UserInputWizardPage;
import org.eclipse.php.refactoring.ui.PHPRefactoringUIMessages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

/**
 * @author seva
 */
public class OrganizeIncludesWizardInitializationPage extends UserInputWizardPage {

	protected OrganizeIncludesWizardInitializationPage() {
		super(PHPRefactoringUIMessages.getString("OrganizeIncludesWizardInitializationPage.0")); //$NON-NLS-1$
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createControl(Composite parent) {
		initializeDialogUnits(parent);
		Composite result = new Composite(parent, SWT.NONE);
		setControl(result);
		result.setLayout(new GridLayout());
		// TODO add controls
		Dialog.applyDialogFont(result);
	}

}
