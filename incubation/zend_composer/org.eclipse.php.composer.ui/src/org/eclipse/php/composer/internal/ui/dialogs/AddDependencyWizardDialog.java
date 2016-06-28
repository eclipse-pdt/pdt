/*******************************************************************************
 * Copyright (c) 2014, 2016 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.internal.ui.dialogs;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.operation.ModalContext;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.ProgressMonitorPart;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

/**
 * Class extends standard WizardDialog and removes UI disabling during task
 * progress. Added for AddDependencyPage purposes.
 * 
 * @author Michal Niewrzal, 2014
 * 
 */
public class AddDependencyWizardDialog extends WizardDialog {

	private boolean lockUI = false;

	public AddDependencyWizardDialog(Shell parentShell, IWizard newWizard) {
		super(parentShell, newWizard);
	}

	@Override
	public void run(boolean fork, boolean cancelable,
			IRunnableWithProgress runnable) throws InvocationTargetException,
			InterruptedException {
		try {
			if (getWizard().needsProgressMonitor()) {
				if (cancelable && fork) {
					Button cancelButton = getButton(IDialogConstants.CANCEL_ID);
					((ProgressMonitorPart) getProgressMonitor())
							.attachToCancelComponent(cancelButton);
				}
				((ProgressMonitorPart) getProgressMonitor()).setVisible(true);
			}

			lockUI = true;
			ModalContext.run(runnable, fork, getProgressMonitor(), getShell()
					.getDisplay());
		} finally {
			// explicitly invoke done() on our progress monitor so
			// that its
			// label does not spill over to the next invocation, see
			// bug 271530
			if (getProgressMonitor() != null
					&& !((ProgressMonitorPart) getProgressMonitor())
							.isDisposed()) {
				getProgressMonitor().done();
			}
			lockUI = false;
		}
	}

	@Override
	protected ProgressMonitorPart createProgressMonitorPart(
			Composite composite, GridLayout pmlayout) {
		ProgressMonitorPart part = new ProgressMonitorPart(composite, pmlayout) {
			@Override
			public boolean setFocus() {
				return false;
			}
		};
		return part;
	}

	@Override
	public boolean close() {
		if (lockUI) {
			return false;
		}
		return super.close();
	}

}
