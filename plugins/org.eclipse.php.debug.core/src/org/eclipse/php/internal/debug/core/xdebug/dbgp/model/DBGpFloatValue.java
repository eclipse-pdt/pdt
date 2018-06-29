/*******************************************************************************
 * Copyright (c) 2016 Zend Technologies and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.xdebug.dbgp.model;

/**
 * DBGp float value.
 * 
 * @author Bartlomiej Laczkowski
 */
public class DBGpFloatValue extends AbstractDBGpValue {

	/**
	 * Creates new DBGp float value.
	 * 
	 * @param owner
	 */
	public DBGpFloatValue(DBGpVariable owner) {
		super(owner);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.core.xdebug.dbgp.model.AbstractDBGpValue#
	 * createValueString(org.eclipse.php.internal.debug.core.xdebug.dbgp.model.
	 * AbstractDBGpValue.DBGpValueData)
	 */
	@Override
	protected String createValueString(DBGpValueData valueData) {
		String valueString = valueData.getValueString();
		if (valueString != null) {
			return valueString.trim();
		}
		return IDBGpModelConstants.INVALID_VAR_CONTENT;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.core.xdebug.dbgp.model.AbstractDBGpValue#
	 * supportsValueModification()
	 */
	@Override
	protected boolean supportsValueModification() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.core.xdebug.dbgp.model.AbstractDBGpValue#
	 * verifyValue(java.lang.String)
	 */
	@Override
	protected boolean verifyValue(String expression) {
		try {
			Float.parseFloat(expression);
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}

}
