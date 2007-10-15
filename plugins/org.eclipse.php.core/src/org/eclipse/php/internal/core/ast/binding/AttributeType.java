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
	public static final AttributeType VARIABLE_ATTRIBUTE = new AttributeType("variable"); //$NON-NLS-1$

	/**
	 * Represents a constant 
	 */
	public static final AttributeType CONSTANT_ATTRIBUTE = new AttributeType("constant"); //$NON-NLS-1$

	/**
	 * Represents a function 
	 */
	public static final AttributeType FUNCTION_ATTRIBUTE = new AttributeType("function"); //$NON-NLS-1$

	/**
	 * Represents a function 
	 */
	public static final AttributeType CLASS_ATTRIBUTE = new AttributeType("class"); //$NON-NLS-1$

	/**
	 * Represents multiple attributes
	 */
	public static final AttributeType COMPOSITE_ATTRIBUTE = new AttributeType("multiple"); //$NON-NLS-1$

	/**
	 * Represents array attributes
	 */
	public static final AttributeType ARRAY_ATTRIBUTE = new AttributeType("array"); //$NON-NLS-1$

	/**
	 * Represents null attribute
	 */
	public static final AttributeType NULL_ATTRIBUTE = new AttributeType("null"); //$NON-NLS-1$

	/**
	 * Represents int attribute
	 */
	public static final AttributeType INT_ATTRIBUTE = new AttributeType("int"); //$NON-NLS-1$

	/**
	 * Represents string attribute
	 */
	public static final AttributeType STRING_ATTRIBUTE = new AttributeType("string"); //$NON-NLS-1$

	/**
	 * Represents bool attribute
	 */
	public static final AttributeType BOOL_ATTRIBUTE = new AttributeType("bool"); //$NON-NLS-1$

	/**
	 * Represents real attribute
	 */
	public static final AttributeType REAL_ATTRIBUTE = new AttributeType("real"); //$NON-NLS-1$

	private final String toString;

	private AttributeType(String toString) {
		this.toString = toString;
	}

	public String toString() {
		return toString;
	}

}
