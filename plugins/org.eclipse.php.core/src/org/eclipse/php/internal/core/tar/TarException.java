/*******************************************************************************
 * Copyright (c) 2009 Zhao and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Zhao - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.tar;

/**
 * Exception generated upon encountering corrupted tar files.
 */
public class TarException extends Exception {
	/**
	 * Generated serial version UID for this class.
	 */
	private static final long serialVersionUID = 2886671254518853528L;

	/**
	 * Constructs a TarException without a detail string.
	 */
	public TarException() {
		super();
	}

	/**
	 * Constructs a TarException with the specified detail string.
	 * 
	 * @param s
	 *            the detail string
	 */
	public TarException(String s) {
		super(s);
	}

	/**
	 * Constructs a TarException with the specified detail string.
	 * 
	 * @param s
	 *            the detail string
	 * @param cause
	 *            the cause
	 */
	public TarException(String s, Throwable cause) {
		super(s, cause);
	}
}
