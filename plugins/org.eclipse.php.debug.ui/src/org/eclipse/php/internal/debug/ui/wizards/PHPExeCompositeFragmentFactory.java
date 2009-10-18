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
package org.eclipse.php.internal.debug.ui.wizards;

import org.eclipse.php.internal.ui.wizards.CompositeFragment;
import org.eclipse.php.internal.ui.wizards.IControlHandler;
import org.eclipse.php.internal.ui.wizards.WizardFragment;
import org.eclipse.php.ui.wizards.ICompositeFragmentFactory;
import org.eclipse.swt.widgets.Composite;

public class PHPExeCompositeFragmentFactory implements
		ICompositeFragmentFactory {

	public CompositeFragment createComposite(Composite parent,
			IControlHandler controlHandler) {
		return new PHPExeCompositeFragment(parent, controlHandler, true);
	}

	public WizardFragment createWizardFragment() {
		return new PHPExeWizardFragment();
	}
}
