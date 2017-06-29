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

/**
 * Basic DBGp value builder.
 * 
 * @author Bartlomiej Laczkowski
 */
public class DBGpValueBuilder {

	/**
	 * Builds new DBGp value for provided variable.
	 * 
	 * @param variable
	 * @return new DBGp value
	 */
	public AbstractDBGpValue build(DBGpVariable variable) {
		AbstractDBGpValue value;
		switch (variable.getDataType()) {
		case PHP_ARRAY: {
			value = new DBGpArrayValue(variable);
			break;
		}
		case PHP_BOOL: {
			value = new DBGpBoolValue(variable);
			break;
		}
		case PHP_FLOAT: {
			value = new DBGpFloatValue(variable);
			break;
		}
		case PHP_INT: {
			value = new DBGpIntValue(variable);
			break;
		}
		case PHP_NULL: {
			value = new DBGpNullValue(variable);
			break;
		}
		case PHP_OBJECT: {
			value = new DBGpObjectValue(variable);
			break;
		}
		case PHP_RESOURCE: {
			value = new DBGpResourceValue(variable);
			break;
		}
		case PHP_STRING: {
			value = new DBGpStringValue(variable);
			break;
		}
		case PHP_UNINITIALIZED: {
			value = new DBGpUninitializedValue(variable);
			break;
		}
		default: {
			value = new DBGpUnknownValue(variable);
			break;
		}
		}
		// Initialize value
		value.update(variable.getDescriptor());
		return value;
	}

}
