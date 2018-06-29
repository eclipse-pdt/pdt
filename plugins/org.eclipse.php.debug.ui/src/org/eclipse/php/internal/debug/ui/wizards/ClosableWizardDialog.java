/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.debug.ui.wizards;

import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;

public class ClosableWizardDialog extends WizardDialog {
	/**
	 * Constructor for ClosableWizardDialog.
	 * 
	 * @param shell
	 * @param wizard
	 */
	public ClosableWizardDialog(Shell shell, IWizard wizard) {
		super(shell, wizard);
	}

	/**
	 * The Finish button has been pressed.
	 */
	@Override
	public void finishPressed() {
		super.finishPressed();
	}
}