/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.debug.ui.preferences.phps;

/**
 * Used to provide a description for PHP exe selections in the installed PHP
 * exes block.
 */
public abstract class PHPexeDescriptor {

	/**
	 * Returns a description of the PHP exe setting.
	 * 
	 * @return description of the PHP exe setting
	 */
	public abstract String getDescription();

}
