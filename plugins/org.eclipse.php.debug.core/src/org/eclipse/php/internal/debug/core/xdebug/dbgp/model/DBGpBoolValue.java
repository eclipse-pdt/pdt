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

/**
 * DBGp boolean value.
 * 
 * @author Bartlomiej Laczkowski
 */
public class DBGpBoolValue extends AbstractDBGpValue {

	private static final String[] fAllowedValues = { "false", "true" }; //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * Creates new DBGp boolean value.
	 * 
	 * @param owner
	 */
	public DBGpBoolValue(DBGpVariable owner) {
		super(owner);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.xdebug.core.dbgp.model.DBGpValue#verifyValue(java.lang
	 * .String)
	 */
	protected boolean verifyValue(String expression) {
		for (String allowed : fAllowedValues) {
			if (expression.equals(allowed)) {
				return true;
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.debug.core.xdebug.dbgp.model.AbstractDBGpValue#
	 * createValueString()
	 */
	@Override
	protected String createValueString(DBGpValueData data) {
		String valueString = data.getValueString();
		if (valueString != null) {
			try {
				int bool = Integer.parseInt(valueString);
				if (1 == bool) {
					return fAllowedValues[1];
				} else if (0 == bool) {
					return fAllowedValues[0];
				}
			} catch (NumberFormatException nfe) {
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.debug.core.xdebug.dbgp.model.AbstractDBGpValue#
	 * supportsValueModification()
	 */
	@Override
	protected boolean supportsValueModification() {
		return true;
	}

}
