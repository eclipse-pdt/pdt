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
package org.eclipse.php.internal.debug.core.xdebug.dbgp.model;

/**
 * DBGp resource value.
 * 
 * @author Bartlomiej Laczkowski
 */
public class DBGpResourceValue extends AbstractDBGpValue {

	/**
	 * Creates new DBGp resource value.
	 * 
	 * @param owner
	 */
	public DBGpResourceValue(DBGpVariable owner) {
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
		if (valueString != null && valueString.trim().length() > 0) {
			return valueString;
		}
		return ""; //$NON-NLS-1$
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.core.xdebug.dbgp.model.AbstractDBGpValue#
	 * supportsValueModification()
	 */
	@Override
	protected boolean supportsValueModification() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.core.xdebug.dbgp.model.AbstractDBGpValue#
	 * verifyValue(java.lang.String)
	 */
	@Override
	protected boolean verifyValue(String expression) {
		return false;
	}

}
