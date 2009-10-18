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
package org.eclipse.php.internal.server.ui;

import org.eclipse.php.internal.server.ui.wizard.PathMapperWizardFragment;
import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.php.internal.ui.wizards.CompositeFragment;
import org.eclipse.php.internal.ui.wizards.IControlHandler;
import org.eclipse.php.internal.ui.wizards.WizardFragment;
import org.eclipse.php.ui.wizards.ICompositeFragmentFactory;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;

/**
 * @author michael
 */
public class PathMapperFragmentFactory implements ICompositeFragmentFactory {

	public CompositeFragment createComposite(Composite parent,
			IControlHandler controlHandler) {
		// HELP
		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent,
				IPHPHelpContextIds.ADDING_A_SERVER_LOCATION_PATH_MAP);
		return new PathMapperCompositeFragment(parent, controlHandler, true);
	}

	public WizardFragment createWizardFragment() {
		return new PathMapperWizardFragment();
	}
}
