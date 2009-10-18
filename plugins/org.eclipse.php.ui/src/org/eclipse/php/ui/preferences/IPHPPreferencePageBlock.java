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
package org.eclipse.php.ui.preferences;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.widgets.Composite;

/**
 * A PHP debug preferences page addon definition interface for any extension
 * point implementation that wishes to contribute a Composite widget to the PHP
 * | Debug preferences page.
 */
public interface IPHPPreferencePageBlock {

	/**
	 * Set the addon by adding widgets to the given parent composite.
	 * 
	 * @param parent
	 *            A Composite parent.
	 */
	public void setCompositeAddon(Composite parent);

	/**
	 * Initialize the addon values and fields.
	 * 
	 * @param propertyPage
	 *            The PropertyPage that this addon connected to.
	 */
	public void initializeValues(PreferencePage propertyPage);

	/**
	 * Perform any operation needed when a OK is pressed.
	 * 
	 * @return True, if successful; False otherwise.
	 */
	public boolean performOK(boolean isProjectSpecific);

	/**
	 * Perform any operation needed when a Apply is pressed.
	 */
	public void performApply(boolean isProjectSpecific);

	/**
	 * Perform any operation needed when a Cancel is pressed.
	 * 
	 * @return True, if successful; False otherwise.
	 */
	public boolean performCancel();

	/**
	 * Restore the addon values to the defaults.
	 */
	public void performDefaults();

	/**
	 * Returns the addon comparable name in order to determine its page
	 * location.
	 * 
	 * @return The defined addon Id.
	 * @see #setComparableName(String)
	 */
	public String getComparableName();

	/**
	 * Sets the addon comparable name. This is mainly used when determine the
	 * order of the addons in the preferences page.
	 * 
	 * @param id
	 *            The addon Id.
	 * @see #getComparableName()
	 */
	public void setComparableName(String name);

}
