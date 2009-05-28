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
package org.eclipse.php.internal.debug.core.xdebug.dbgp.model;

import org.eclipse.debug.core.DebugException;

public class DBGpUnInitValue extends DBGpValue {

	public DBGpUnInitValue(DBGpVariable owningVariable) {
		super(owningVariable);
		setModifiable(false); // will never be modifiable, unknown type
		genValueString(IDBGpModelConstants.UNINITIALIZED_VAR_CONTENT);
	}

	/**
	 * 
	 */
	public String getReferenceTypeName() throws DebugException {
		return DBGpVariable.PHP_UNINIT;
	}

	void genValueString(String data) {
		setValueString(data);
	}
}
