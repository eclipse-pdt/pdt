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

import org.eclipse.swt.widgets.Composite;

/**
 * A WizardFragment that always hold a Composite.
 * 
 */
public abstract class CompositeWizardFragment extends WizardFragment {

	/**
	 * Constructs a new CompositeWizardFragment.
	 * 
	 */
	public CompositeWizardFragment() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.wst.server.ui.wizard.WizardFragment#hasComposite()
	 */
	public boolean hasComposite() {
		return true;
	}

	/**
	 * Returns the composite that was last created by this fragment.
	 * 
	 * @return A Composite (Null if the createComposite was not called yet)
	 */
	public abstract Composite getComposite();
}