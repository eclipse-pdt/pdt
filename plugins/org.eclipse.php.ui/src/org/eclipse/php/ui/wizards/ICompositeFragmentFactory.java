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
package org.eclipse.php.ui.wizards;

import org.eclipse.swt.widgets.Composite;

/**
 * A factory for the creation of WizardFragment or Composites.
 */
public interface ICompositeFragmentFactory {

	/**
	 * Creates a WizardFragment used for any Wizard control that is related to the PHP servers infrastructure.
	 * 
	 * @param serverContainer
	 * @return
	 */
	public WizardFragment createWizardFragment();

	/**
	 * Creates a CompositeFragment used for any composite that 
	 * @param parent
	 * @param controlHandler
	 * @return
	 */
	public CompositeFragment createComposite(Composite parent, IControlHandler controlHandler);

}
