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

public class ValidationStatus {
	public final static int OK = 0;
	public final static int FAILURE = 1;

	protected String error;
	protected int status;

	public ValidationStatus() {
		this.status = OK;
	}

	public ValidationStatus(String error) {
		this.status = FAILURE;
		this.error = error;
	}

	public void setError(String error) {
		if (error == null) {
			this.status = OK;
			this.error = null;
		} else {
			this.status = FAILURE;
			this.error = error;
		}
	}

	public String getError() {
		return error;
	}

	public boolean isOK() {
		return status == OK;
	}
}
