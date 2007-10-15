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
 * Represents an attribute of a class
 * TODO : pool of class attributes 
 */
public class ClassAttribute extends Attribute {

	// the name of the class
	public final String name;

	public ClassAttribute(String name) {
		this.name = name;
	}

	public AttributeType getType() {
		return AttributeType.CLASS_ATTRIBUTE;
	}

	public String toString() {
		return AttributeType.CLASS_ATTRIBUTE.toString() + "(" + name + ")"; //$NON-NLS-1$ //$NON-NLS-2$

	}

}
