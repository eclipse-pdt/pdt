/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Dawid Pakuła - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.ui.preferences;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.swt.widgets.Composite;

public interface IPHPPreferencePageBlockExtension extends IAdaptable {
	/**
	 * Build content inside parent
	 */
	public void createContents(Composite parent);

	public void setScopeContext(IScopeContext scope);

	/**
	 * Perform any operation needed when a OK is pressed.
	 * 
	 * @return True, if successful; False otherwise.
	 */
	public boolean performOK(boolean isProjectSpecific);

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
}
