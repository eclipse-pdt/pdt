/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.util;

public class ValidationStatus {
	public final static int OK = 0;
	public final static int FAILURE = 1;
	
	protected String error;
	protected int status;
	
	public ValidationStatus () {
		this.status = OK;
	}
	
	public ValidationStatus (String error) {
		this.status = FAILURE;
		this.error = error;
	}
	
	public void setError (String error) {
		if (error == null) {
			this.status = OK;
			this.error = null;
		} else {
			this.status = FAILURE;
			this.error = error;
		}
	}
	
	public String getError () {
		return error;
	}
	
	public boolean isOK() {
		return status == OK;
	}
}
