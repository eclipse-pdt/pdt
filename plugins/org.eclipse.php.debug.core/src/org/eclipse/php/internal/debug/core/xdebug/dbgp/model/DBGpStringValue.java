/*******************************************************************************
 * Copyright (c) 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.xdebug.dbgp.model;

import org.eclipse.debug.core.DebugException;
import org.w3c.dom.Node;

public class DBGpStringValue extends DBGpValue {

	public DBGpStringValue(DBGpVariable owningVariable, Node property) {
		super(owningVariable);
		setModifiable(true);
		simpleParseNode(property);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.php.xdebug.core.dbgp.model.DBGpValue#getReferenceTypeName()
	 */
	public String getReferenceTypeName() throws DebugException {
		return DBGpVariable.PHP_STRING;
	}

	void genValueString(String data) {
		if (data != null && data.trim().length() > 0) {
			setValueString(data);
		} else {
			// if we have no data, then we have an empty string. Could confirm this
			// with the length.
			setValueString("");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.php.xdebug.core.dbgp.model.DBGpValue#setValue(java.lang.String)
	 */
	public void setValue(String expression) throws DebugException {
		if (expression != null) {
			setValueString(expression.trim());
		} else {
			setValueString(IDBGpModelConstants.INVALID_VAR_CONTENT);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.php.xdebug.core.dbgp.model.DBGpValue#verifyValue(java.lang.String)
	 */
	boolean verifyValue(String expression) throws DebugException {
		// any string is ok
		return true;
	}
}
