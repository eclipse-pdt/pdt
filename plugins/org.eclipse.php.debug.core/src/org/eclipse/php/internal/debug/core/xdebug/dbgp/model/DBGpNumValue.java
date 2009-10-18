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

public class DBGpNumValue extends DBGpValue {

	private String numberType;

	public DBGpNumValue(DBGpVariable owningVariable, Node property,
			String numType) {
		super(owningVariable, property);
		setModifiable(true);
		numberType = numType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.xdebug.core.dbgp.model.DBGpValue#getReferenceTypeName()
	 */
	public String getReferenceTypeName() throws DebugException {
		return numberType;
	}

	void genValueString(String data) {
		if (data != null) {
			setValueString(data.trim());
		} else {
			setValueString(IDBGpModelConstants.INVALID_VAR_CONTENT);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.xdebug.core.dbgp.model.DBGpValue#setValue(java.lang.String
	 * )
	 */
	public void setValue(String expression) throws DebugException {
		genValueString(expression);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.xdebug.core.dbgp.model.DBGpValue#verifyValue(java.lang
	 * .String)
	 */
	boolean verifyValue(String expression) throws DebugException {
		try {
			Float.parseFloat(expression);
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}
}
