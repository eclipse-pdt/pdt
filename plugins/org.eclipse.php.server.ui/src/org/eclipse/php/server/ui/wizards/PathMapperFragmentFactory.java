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
package org.eclipse.php.server.ui.wizards;

import org.eclipse.php.internal.server.ui.PathMapperCompositeFragment;
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

	@Override
	public CompositeFragment createComposite(Composite parent, IControlHandler controlHandler) {
		// HELP
		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent, IPHPHelpContextIds.ADDING_A_SERVER_PATH_MAP);
		return new PathMapperCompositeFragment(parent, controlHandler, true);
	}

	@Override
	public WizardFragment createWizardFragment() {
		return new PathMapperWizardFragment();
	}
}
