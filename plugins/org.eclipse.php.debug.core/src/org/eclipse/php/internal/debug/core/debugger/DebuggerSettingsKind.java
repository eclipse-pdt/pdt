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

/**
 * Simple constants that indicates debugger settings owner kind.
 * 
 * @author Bartlomiej Laczkowski
 */
public enum DebuggerSettingsKind {

	/**
	 * PHP executable kind.
	 */
	PHP_EXE,
	/**
	 * PHP server kind.
	 */
	PHP_SERVER,
	/**
	 * Unknown kind.
	 */
	UNKNOWN;

}
