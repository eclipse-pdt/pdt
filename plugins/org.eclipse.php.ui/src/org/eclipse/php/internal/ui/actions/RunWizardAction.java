/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.ui.actions;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWizard;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.wizards.IWizardDescriptor;
import org.eclipse.ui.wizards.IWizardRegistry;

public abstract class RunWizardAction extends Action {

	/**
	 * Return Wizard ID
	 * 
	 * @return ID
	 */
	public abstract String getWizardId();

	/**
	 * Return registry that holds this wizard (NewWizardRegistry,
	 * ImportWizardRegistry, etc..)
	 * 
	 * @return wizard registry
	 */
	public abstract IWizardRegistry getWizardRegistry();

	public void run() {
		try {
			IWizardDescriptor wizardDescriptor = getWizardRegistry()
					.findWizard(getWizardId());

			IWorkbenchWizard wizard = wizardDescriptor.createWizard();
			wizard.init(PlatformUI.getWorkbench(), StructuredSelection.EMPTY);

			if (wizardDescriptor.canFinishEarly()
					&& !wizardDescriptor.hasPages()) {
				wizard.performFinish();
				return;
			}

			Shell parent = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
					.getShell();
			WizardDialog dialog = new WizardDialog(parent, wizard);
			dialog.create();
			dialog.open();

		} catch (CoreException ex) {
			PHPCorePlugin.log(ex);
		}
	}
}
