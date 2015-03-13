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
