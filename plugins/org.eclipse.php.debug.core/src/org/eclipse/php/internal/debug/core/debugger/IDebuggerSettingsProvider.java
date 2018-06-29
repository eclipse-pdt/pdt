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

import java.util.List;

/**
 * Common interface for debugger settings providers.
 * 
 * @author Bartlomiej Laczkowski
 */
public interface IDebuggerSettingsProvider {

	/**
	 * Returns unique provider ID.
	 * 
	 * @return unique provider ID
	 */
	String getId();

	/**
	 * Returns debugger settings for given owner unique ID (e.g. PHP server or
	 * executable)
	 * 
	 * @param ownerId
	 * @return debugger settings for given owner
	 */
	IDebuggerSettings get(String ownerId);

	/**
	 * Returns all available settings from this provider.
	 * 
	 * @return all available settings.
	 */
	List<IDebuggerSettings> getAll();

	/**
	 * CreateS and returns settings working copy
	 * 
	 * @param settings
	 * @return settings working copy
	 */
	IDebuggerSettingsWorkingCopy createWorkingCopy(IDebuggerSettings settings);

	/**
	 * Save given settings.
	 * 
	 * @param settings
	 */
	void save(IDebuggerSettings settings);

	/**
	 * Deletes given settings.
	 * 
	 * @param settings
	 */
	void delete(IDebuggerSettings settings);

}
