/*******************************************************************************
 * Copyright (c) 2012, 2016 PDT Extension Group and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.api.json;

public class ParseException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4252451904249471375L;

	public static final int ERROR_UNEXPECTED_MALFORMED = 0;
	public static final int ERROR_UNEXPECTED_IO = 2;
	public static final int ERROR_UNEXPECTED_EXCEPTION = 4;

	private int errorType;

	public ParseException(String message) {
		super(message);
	}

	public int getErrorType() {
		return errorType;
	}

	public void setErrorType(int errorType) {
		this.errorType = errorType;
	}

}
