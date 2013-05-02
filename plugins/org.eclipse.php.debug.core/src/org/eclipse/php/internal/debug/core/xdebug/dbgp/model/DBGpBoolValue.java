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

public class DBGpBoolValue extends DBGpValue {

	String[] allowedValues = { "false", "true" }; //$NON-NLS-1$ //$NON-NLS-2$

	public DBGpBoolValue(DBGpVariable owningVariable, Node property) {
		super(owningVariable, property);
		setModifiable(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.xdebug.core.dbgp.model.DBGpValue#getReferenceTypeName()
	 */
	public String getReferenceTypeName() throws DebugException {
		return DBGpVariable.PHP_BOOL;
	}

	/**
	 * 
	 */
	void genValueString(String data) {
		setValueString(IDBGpModelConstants.INVALID_VAR_CONTENT);
		if (data != null) {
			try {
				int bool = Integer.parseInt(data);
				if (1 == bool) {
					setValueString("true"); //$NON-NLS-1$
				} else if (0 == bool) {
					setValueString("false"); //$NON-NLS-1$
				}
			} catch (NumberFormatException nfe) {

			}
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
		String data;
		if (expression.equals("true")) { //$NON-NLS-1$
			data = "1"; //$NON-NLS-1$
		} else if (expression.equals("false")) { //$NON-NLS-1$
			data = "0"; //$NON-NLS-1$
		} else {
			data = expression;
		}
		genValueString(data);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.xdebug.core.dbgp.model.DBGpValue#verifyValue(java.lang
	 * .String)
	 */
	boolean verifyValue(String expression) throws DebugException {
		boolean allowed = false;
		for (int i = 0; i < allowedValues.length && false == allowed; i++) {
			if (expression.equals(allowedValues[i])) {
				allowed = true;
			}
		}
		return allowed;
	}
}
