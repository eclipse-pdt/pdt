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
