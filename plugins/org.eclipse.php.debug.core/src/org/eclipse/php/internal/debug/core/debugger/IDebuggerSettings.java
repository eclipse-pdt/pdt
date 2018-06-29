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

import java.util.Map;

/**
 * Common interface for implementors of debugger owner settings.
 * 
 * @author Bartlomiej Laczkowski
 */
public interface IDebuggerSettings {

	/**
	 * Returns corresponding debugger ID.
	 * 
	 * @return corresponding debugger ID
	 */
	String getDebuggerId();

	/**
	 * Returns debugger settings owner (e.g. PHP executable or server).
	 * 
	 * @return debugger settings owner
	 */
	String getOwnerId();

	/**
	 * Returns settings kind (see {@link DebuggerSettingsKind} enum)
	 * 
	 * @return settings kind
	 */
	DebuggerSettingsKind getKind();

	/**
	 * Returns settings attribute value for given key.
	 * 
	 * @param key
	 * @return attribute value for given key
	 */
	String getAttribute(String key);

	/**
	 * Returns all attributes map.
	 * 
	 * @return all attributes map
	 */
	Map<String, String> getAttributes();

}
