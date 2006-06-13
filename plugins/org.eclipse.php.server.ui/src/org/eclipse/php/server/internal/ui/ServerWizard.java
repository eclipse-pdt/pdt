/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.server.internal.ui;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.php.server.ui.FragmentedWizard;
import org.eclipse.php.server.ui.WizardModel;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

/**
 * A Server wizard.
 * 
 * @author shalom
 */

public class ServerWizard extends FragmentedWizard implements INewWizard {

	public ServerWizard() {
		this("");
	}

	public ServerWizard(String title, WizardModel taskModel) {
		super(title, new ServerWizardFragment(), taskModel);
	}

	public ServerWizard(String title) {
		super(title, new ServerWizardFragment());
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench, org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		// Do nothing
	}
}
