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
package org.eclipse.php.debug.ui.wizards;

import org.eclipse.php.internal.debug.ui.wizards.DebuggerCompositeFragment;
import org.eclipse.php.internal.debug.ui.wizards.DebuggerWizardFragment;
import org.eclipse.php.internal.ui.wizards.CompositeFragment;
import org.eclipse.php.internal.ui.wizards.IControlHandler;
import org.eclipse.php.internal.ui.wizards.WizardFragment;
import org.eclipse.php.ui.wizards.ICompositeFragmentFactory;
import org.eclipse.swt.widgets.Composite;

/**
 * Debugger composite fragment factory.
 * 
 * @author Bartlomiej Laczkowski
 */
public class DebuggerCompositeFragmentFactory implements ICompositeFragmentFactory {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.ui.wizards.ICompositeFragmentFactory#createWizardFragment ()
	 */
	@Override
	public WizardFragment createWizardFragment() {
		return new DebuggerWizardFragment();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.ui.wizards.ICompositeFragmentFactory#createComposite(
	 * org.eclipse.swt.widgets.Composite,
	 * org.eclipse.php.internal.ui.wizards.IControlHandler)
	 */
	@Override
	public CompositeFragment createComposite(Composite parent, IControlHandler controlHandler) {
		return new DebuggerCompositeFragment(parent, controlHandler, true);
	}

}
