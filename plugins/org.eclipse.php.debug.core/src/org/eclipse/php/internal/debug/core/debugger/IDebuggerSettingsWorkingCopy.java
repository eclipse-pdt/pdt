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

/**
 * Common interface for debugger settings working copies.
 * 
 * @author Bartlomiej Laczkowski
 */
public interface IDebuggerSettingsWorkingCopy extends IDebuggerSettings {

	/**
	 * Sets attribute value
	 * 
	 * @param key
	 * @param value
	 */
	void setAttribute(String key, String value);

	/**
	 * Returns original settings.
	 * 
	 * @return original settings.
	 */
	IDebuggerSettings getOriginal();

	/**
	 * Checks if given working copy is dirty.
	 * 
	 * @return <code>true</code> if working copy is dirty, <code>false</code>
	 *         otherwise
	 */
	boolean isDirty();

}
