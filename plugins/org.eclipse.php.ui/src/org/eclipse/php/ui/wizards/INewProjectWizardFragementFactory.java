/*******************************************************************************
 * Copyright (c) 2012 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Martin Eisengardt <martin.eisengardt@fiducia.de>
 *******************************************************************************/
package org.eclipse.php.ui.wizards;

import org.eclipse.php.internal.ui.wizards.WizardFragment;

/**
 * Factory for creating fragments for the new project wizard.
 * 
 */
@Deprecated
public interface INewProjectWizardFragementFactory {

	/**
	 * Creates a WizardFragment used for the new project wizard.
	 * 
	 * @return the wizard fragment
	 */
	public WizardFragment createWizardFragment();

}
