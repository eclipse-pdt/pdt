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
import org.w3c.dom.Node;

public class DBGpNullValue extends DBGpValue {

	public DBGpNullValue(DBGpVariable owningVariable, Node property) {
		super(owningVariable, property);
		setModifiable(false); // will never be modifiable, unknown type
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.xdebug.core.dbgp.model.DBGpValue#getReferenceTypeName()
	 */
	public String getReferenceTypeName() throws DebugException {
		return DBGpVariable.PHP_NULL;
	}

	void genValueString(String data) {
		if (data != null) {
			setValueString(data.trim());
		} else {
			setValueString(IDBGpModelConstants.UNINITIALIZED_VAR_CONTENT);
		}
	}

}
