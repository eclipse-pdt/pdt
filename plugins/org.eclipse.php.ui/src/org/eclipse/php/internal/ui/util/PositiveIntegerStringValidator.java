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
package org.eclipse.php.internal.ui.util;

public class PositiveIntegerStringValidator extends IntegerStringValidator implements IStringValidator {
	private String notPositiveError;

	public PositiveIntegerStringValidator(String zeroLengthError, String notIntegerError, String notPositiveError) {
		super(zeroLengthError, notIntegerError);
		this.notPositiveError = notPositiveError;
	}

	@Override
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
