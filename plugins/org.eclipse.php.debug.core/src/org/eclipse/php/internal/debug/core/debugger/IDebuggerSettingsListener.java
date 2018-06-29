/*******************************************************************************
 * Copyright (c) 2015 Zend Technologies and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.debugger;

import org.eclipse.jface.util.PropertyChangeEvent;

/**
 * Common interface for debugger settings listeners.
 * 
 * @author Bartlomiej Laczkowski
 */
public interface IDebuggerSettingsListener {

	/**
	 * Notifies that given settings have been added.
	 * 
	 * @param settings
	 */
	void settingsAdded(IDebuggerSettings settings);

	/**
	 * Notifies that given settings have been removed.
	 * 
	 * @param settings
	 */
	void settingsRemoved(IDebuggerSettings settings);

	/**
	 * Passes an event with information about settings changes
	 * 
	 * @param events
	 */
	void settingsChanged(PropertyChangeEvent[] events);

}
