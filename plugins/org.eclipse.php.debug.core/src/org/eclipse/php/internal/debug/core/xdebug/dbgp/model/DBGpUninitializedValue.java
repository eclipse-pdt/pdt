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

import static org.eclipse.php.internal.debug.core.model.IPHPDataType.DataType.PHP_UNINITIALIZED;

/**
 * DBGp uninitialized value.
 * 
 * @author Bartlomiej Laczkowski
 */
public class DBGpUninitializedValue extends AbstractDBGpValue {

	public DBGpUninitializedValue(DBGpVariable owner) {
		super(owner);
	}

	@Override
	protected String createValueString(DBGpValueData valueData) {
		return PHP_UNINITIALIZED.getText();
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