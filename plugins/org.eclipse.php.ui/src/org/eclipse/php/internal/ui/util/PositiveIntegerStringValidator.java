/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.util;

public class PositiveIntegerStringValidator extends IntegerStringValidator implements IStringValidator {
	private String notPositiveError;

	public PositiveIntegerStringValidator(String zeroLengthError, String notIntegerError, String notPositiveError) {
		super(zeroLengthError, notIntegerError);
		this.notPositiveError = notPositiveError;
	}

	public ValidationStatus validate(String str) {
		ValidationStatus status = super.validate(str);
		if (status.isOK()) {
			if (Integer.parseInt(str) < 0) {
				status.setError(notPositiveError);
			}
		}
		return status;
	}
}
