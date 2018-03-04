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
package org.eclipse.php.internal.server.ui.wizard;

import org.eclipse.php.internal.server.ui.ServerTypeCompositeFragment;
import org.eclipse.php.internal.server.ui.ServerTypeWizardFragment;
import org.eclipse.php.internal.ui.wizards.CompositeFragment;
import org.eclipse.php.internal.ui.wizards.IControlHandler;
import org.eclipse.php.internal.ui.wizards.WizardFragment;
import org.eclipse.php.ui.wizards.ICompositeFragmentFactory;
import org.eclipse.swt.widgets.Composite;

/**
 * Composite fragment factory for server types.
 */
public class ServerTypeCompositeFragmentFactory implements ICompositeFragmentFactory {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.server.apache.ui.wizard.
	 * ICompositeFragmentFactory #createWizardFragment()
	 */
	@Override
	public WizardFragment createWizardFragment() {
		return new ServerTypeWizardFragment();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.server.apache.ui.wizard.
	 * ICompositeFragmentFactory #createComposite(org.eclipse.swt.widgets.Composite,
	 * org.eclipse.php.internal.server.apache.ui.IControlHandler)
	 */
	@Override
	public CompositeFragment createComposite(Composite parent, IControlHandler controlHandler) {
		return new ServerTypeCompositeFragment(parent, controlHandler, true);
	}

}