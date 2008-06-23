/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.phpModel.phpElementData;

public class PhpVariableTypeUtil {

	public static final String INT_TYPE = "int"; //$NON-NLS-1$
	public static final String DOUBLE_TYPE = "double"; //$NON-NLS-1$
	public static final String ARRAY_TYPE = "array"; //$NON-NLS-1$
	
	public static boolean isNumber(String variableType) {
		if (variableType == null) {
			return false;
		}
		return variableType.equals(INT_TYPE) || variableType.equals(DOUBLE_TYPE);
	}

	public static boolean isArray(String variableType) {
		if (variableType == null) {
			return false;
		}
		return variableType.equals(ARRAY_TYPE);
	}
}
