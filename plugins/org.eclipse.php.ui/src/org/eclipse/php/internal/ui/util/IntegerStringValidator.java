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
package org.eclipse.php.internal.ui.util;

public class IntegerStringValidator {
	private String zeroLengthError;
	private String notIntegerError;

	public IntegerStringValidator(String zeroLengthError, String notIntegerError) {
		this.zeroLengthError = zeroLengthError;
		this.notIntegerError = notIntegerError;
	}

	public ValidationStatus validate(String str) {
		ValidationStatus status = new ValidationStatus();
		if (str.length() == 0) {
			status.setError(zeroLengthError);
		} else {
			try {
				Integer.parseInt(str);
			} catch (NumberFormatException exception) {
				status.setError(notIntegerError);
			}
		}
		return status;
	}
}
