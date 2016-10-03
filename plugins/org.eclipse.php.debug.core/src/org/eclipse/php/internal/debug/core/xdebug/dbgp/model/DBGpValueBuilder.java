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
		switch (variable.getDataType()) {
		case PHP_ARRAY:
			return new DBGpArrayValue(variable);
		case PHP_BOOL:
			return new DBGpBoolValue(variable);
		case PHP_FLOAT:
			return new DBGpFloatValue(variable);
		case PHP_INT:
			return new DBGpIntValue(variable);
		case PHP_NULL:
			return new DBGpNullValue(variable);
		case PHP_OBJECT:
			return new DBGpObjectValue(variable);
		case PHP_RESOURCE:
			return new DBGpResourceValue(variable);
		case PHP_STRING:
			return new DBGpStringValue(variable);
		case PHP_UNINITIALIZED:
			return new DBGpUninitializedValue(variable);
		default:
			break;
		}
		return new DBGpUnknownValue(variable);
	}

}
