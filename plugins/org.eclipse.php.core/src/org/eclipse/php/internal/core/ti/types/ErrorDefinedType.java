/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/

package org.eclipse.php.internal.core.ti.types;

/**
 * This class represents error defined type for PHP element 
 */
public class ErrorDefinedType implements IEvaluatedType {

	public static final ErrorDefinedType INSTANCE = new ErrorDefinedType();
	
	private ErrorDefinedType() {
	}

	public String toString() {
		return "Error Defined"; //$NON-NLS-1$
	}
}
