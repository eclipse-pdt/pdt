/*******************************************************************************
 * Copyright (c) 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.ui.util;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * A wizard dialog which can store its current size and location and restore it
 * with next run.
 */
public class PersistingSizeAndLocationWizardDialog extends WizardDialog {

	private IDialogSettings fDialogSettings;
	private int fPersistingStrategy;

	/**
	 * Creates a new wizard dialog for the given <code>wizard</code>. The dialog
	 * does not persist its location and size by default.
	 * 
	 * @param parentShell
	 *            the parent shell
	 * @param wizard
	 *            the wizard this dialog is working on
	 */
	public PersistingSizeAndLocationWizardDialog(Shell parentShell, IWizard wizard) {
		this(parentShell, wizard, null, 0);
	}

	/**
	 * Creates a new wizard dialog for the given <code>wizard</code>. The dialog
	 * persists its location and size by default.
	 * 
	 * @param parentShell
	 *            the parent shell
	 * @param wizard
	 *            the wizard this dialog is working on
	 * @param dialogSettings
	 *            the dialog settings used to store the dialog's location and/or
	 *            size, or <code>null</code> if the dialog's bounds should never
	 *            be stored
	 */
	public PersistingSizeAndLocationWizardDialog(Shell parentShell, IWizard wizard, IDialogSettings dialogSettings) {
		this(parentShell, wizard, dialogSettings, DIALOG_PERSISTLOCATION | DIALOG_PERSISTSIZE);
	}

	/**
	 * Creates a new wizard dialog for the given <code>wizard</code>.
	 * 
	 * @param parentShell
	 *            the parent shell
	 * @param wizard
	 *            the wizard this dialog is working on
	 * @param dialogSettings
	 *            the dialog settings used to store the dialog's location and/or
	 *            size, or <code>null</code> if the dialog's bounds should never
	 *            be stored
	 * @param persistingStrategy
	 *            the integer constant that describes the strategy for
	 *            persisting the dialog location size
	 * 
	 * @see Dialog#DIALOG_PERSISTLOCATION
	 * @see Dialog#DIALOG_PERSISTSIZE
	 * @see Dialog#getDialogBoundsSettings()
	 */
	public PersistingSizeAndLocationWizardDialog(Shell parentShell, IWizard wizard, IDialogSettings dialogSettings,
			int persistingStrategy) {
		super(parentShell, wizard);
		fDialogSettings = dialogSettings;
		fPersistingStrategy = persistingStrategy;
	}

	@Override
	protected IDialogSettings getDialogBoundsSettings() {
		return fDialogSettings;
	}

	@Override
	protected int getDialogBoundsStrategy() {
		return fPersistingStrategy;
	}

}
