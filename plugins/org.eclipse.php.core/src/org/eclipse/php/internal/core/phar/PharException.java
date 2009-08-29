/*******************************************************************************
 * Copyright (c) 2009 Zhao and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Zhao - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.phar;

public class PharException extends Exception {

	/**
	 * Generated serial version UID for this class.
	 */
	private static final long serialVersionUID = 2949726295921101652L;

	/**
	 * Constructs a PharException without a detail string.
	 */
	public PharException() {
		super();
	}

	/**
	 * Constructs a PharException with the specified detail string.
	 * 
	 * @param s
	 *            the detail string
	 */
	public PharException(String s) {
		super(s);
	}

	/**
	 * Constructs a PharException with the specified detail string.
	 * 
	 * @param s
	 *            the detail string
	 * @param cause
	 *            the cause
	 */
	public PharException(String s, Throwable cause) {
		super(s, cause);
	}
}
