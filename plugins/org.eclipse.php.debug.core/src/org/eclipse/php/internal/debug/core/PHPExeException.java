/*******************************************************************************
 * Copyright (c) 2014 Zend Technologies and others.
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
package org.eclipse.php.internal.debug.core;

/**
 * Exception specific for executing commands with the use of PHP executable.
 * 
 * @author Bartlomiej Laczkowski
 */
public class PHPExeException extends Exception {

	/**
	 * Generated serial version UID for this class.
	 */
	private static final long serialVersionUID = -6836803411271930035L;

	/**
	 * Constructs a PHPExeException with the specified detail string.
	 * 
	 * @param message
	 */
	public PHPExeException(String message) {
		super(message);
	}

	/**
	 * Constructs a PHPExeException with the specified detail string and throwable.
	 * 
	 * @param message
	 * @param throwable
	 */
	public PHPExeException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
