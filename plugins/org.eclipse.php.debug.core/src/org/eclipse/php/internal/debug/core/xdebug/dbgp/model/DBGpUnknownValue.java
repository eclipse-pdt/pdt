/*******************************************************************************
 * Copyright (c) 2016 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.xdebug.dbgp.model;

import static org.eclipse.php.internal.debug.core.model.IPHPDataType.DataType.UNKNOWN;

/**
 * DBGp unknown value.
 * 
 * @author Bartlomiej Laczkowski
 */
public class DBGpUnknownValue extends AbstractDBGpValue {

	public DBGpUnknownValue(DBGpVariable owner) {
		super(owner);
	}

	@Override
	protected String createValueString(DBGpValueData valueData) {
		return UNKNOWN.getText();
	}

	@Override
	protected boolean supportsValueModification() {
		return false;
	}

	@Override
	protected boolean verifyValue(String expression) {
		return false;
	}

}