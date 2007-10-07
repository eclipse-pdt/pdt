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
 * Class that represents simple type of PHP element:
 * integer, float, string, boolean, array.
 */
public class SimpleType implements IEvaluatedType {

	public final static SimpleType INTEGER = new SimpleType("integer");
	public final static SimpleType FLOAT = new SimpleType("float");
	public final static SimpleType STRING = new SimpleType("string");
	public final static SimpleType BOOLEAN = new SimpleType("boolean");
	public final static SimpleType ARRAY = new SimpleType("array");
	
	private String name;
	
	private SimpleType(String name) {
		this.name = name;
	}

	public String toString() {
		return name;
	}
}
