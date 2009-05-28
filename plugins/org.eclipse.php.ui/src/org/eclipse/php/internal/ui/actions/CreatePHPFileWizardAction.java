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

import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.wizards.IWizardRegistry;

public class CreatePHPFileWizardAction extends RunWizardAction {

	public String getWizardId() {
		return "org.eclipse.php.ui.wizards.PHPFileCreationWizard"; //$NON-NLS-1$
	}

	public IWizardRegistry getWizardRegistry() {
		return PlatformUI.getWorkbench().getNewWizardRegistry();
	}
}