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
package org.eclipse.php.internal.core.ast.binding;

/**
 * Represents an attribute type     
 */
public class AttributeType {

	/**
	 * Represents a zval container 
	 */
	public static final AttributeType VARIABLE_ATTRIBUTE = new AttributeType("variable");

	/**
	 * Represents a constant 
	 */
	public static final AttributeType CONSTANT_ATTRIBUTE = new AttributeType("constant");

	/**
	 * Represents a function 
	 */
	public static final AttributeType FUNCTION_ATTRIBUTE = new AttributeType("function");

	/**
	 * Represents a function 
	 */
	public static final AttributeType CLASS_ATTRIBUTE = new AttributeType("class");

	/**
	 * Represents multiple attributes
	 */
	public static final AttributeType COMPOSITE_ATTRIBUTE = new AttributeType("multiple");

	/**
	 * Represents array attributes
	 */
	public static final AttributeType ARRAY_ATTRIBUTE = new AttributeType("array");

	/**
	 * Represents null attribute
	 */
	public static final AttributeType NULL_ATTRIBUTE = new AttributeType("null");

	/**
	 * Represents int attribute
	 */
	public static final AttributeType INT_ATTRIBUTE = new AttributeType("int");

	/**
	 * Represents string attribute
	 */
	public static final AttributeType STRING_ATTRIBUTE = new AttributeType("string");

	/**
	 * Represents bool attribute
	 */
	public static final AttributeType BOOL_ATTRIBUTE = new AttributeType("bool");

	/**
	 * Represents real attribute
	 */
	public static final AttributeType REAL_ATTRIBUTE = new AttributeType("real");

	private final String toString;

	private AttributeType(String toString) {
		this.toString = toString;
	}

	public String toString() {
		return toString;
	}

}
