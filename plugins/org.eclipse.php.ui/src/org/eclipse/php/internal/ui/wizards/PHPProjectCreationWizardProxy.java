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
package org.eclipse.php.internal.ui.wizards;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.IWorkbenchWizard;

public class PHPProjectCreationWizardProxy {

	private static final String WIZARD_POINT = "org.eclipse.php.ui.phpProjectWizard"; //$NON-NLS-1$
	private static final String WIZARD = "wizard"; //$NON-NLS-1$
	private static final String CLASS_ATTR = "class"; //$NON-NLS-1$

	private static IConfigurationElement phpProjectWizardElement;
	private static IWorkbenchWizard phpProjectWizard;

	static {
		IConfigurationElement[] elements = Platform.getExtensionRegistry()
				.getConfigurationElementsFor(WIZARD_POINT);
		for (IConfigurationElement element : elements) {
			String name = element.getName();
			if (WIZARD.equals(name)) {
				phpProjectWizardElement = element;
			}
		}
	}

	public static IWorkbenchWizard getProjectWizard() {
		if (phpProjectWizard == null && phpProjectWizardElement != null) {
			try {
				phpProjectWizard = (IWorkbenchWizard) phpProjectWizardElement
						.createExecutableExtension(CLASS_ATTR);
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
		if (phpProjectWizard == null) {
			phpProjectWizard = new PHPProjectCreationWizard();
		}
		return phpProjectWizard;
	}

}