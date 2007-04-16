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
 * Represents an attribute of a variable 
 */
public class VariableAttribute extends Attribute {

	public final String type;

	public VariableAttribute(final String type) {
		this.type = type;
	}

	public AttributeType getType() {
		return AttributeType.VARIABLE_ATTRIBUTE;
	}
}
