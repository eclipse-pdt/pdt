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
package org.eclipse.php.internal.core.ast.nodes;

import org.eclipse.php.internal.core.ast.visitor.Visitor;

/**
 * Represents a scalar
 * <pre>e.g.<pre> 'string',
 * 1,
 * 1.3,
 * __CLASS__
 */
public class Scalar extends Expression {

	// 'int'
	public static final int TYPE_INT = 0;
	// 'real'
	public static final int TYPE_REAL = 1;
	// 'string'
	public static final int TYPE_STRING = 2;
	// unknown scalar in quote expression
	public static final int TYPE_UNKNOWN = 3;
	// system scalars (__CLASS__ / ...)
	public static final int TYPE_SYSTEM = 4;

	private final String stringValue;
	private final int scalarType;

	public Scalar(int start, int end, String value, int type) {
		super(start, end);
		this.stringValue = value;
		this.scalarType = type;
	}

	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	public void childrenAccept(Visitor visitor) {
	}

	public void traverseBottomUp(Visitor visitor) {
	}

	public void traverseTopDown(Visitor visitor) {
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<Scalar");
		appendInterval(buffer);
		buffer.append(" type='").append(getType(scalarType)).append("'");
		if (stringValue != null) {
			buffer.append(" value='").append(getXmlStringValue(stringValue)).append("'");
		}
		buffer.append("/>");
	}

	public static String getType(int type) {
		switch (type) {
			case TYPE_INT:
				return "int";
			case TYPE_REAL:
				return "real";
			case TYPE_STRING:
				return "string";
			case TYPE_UNKNOWN:
				return "unknown";
			case TYPE_SYSTEM:
				return "system";
			default:
				throw new IllegalArgumentException();
		}
	}

	public int getType() {
		return ASTNode.SCALAR;
	}

	public int getScalarType() {
		return scalarType;
	}

	public String getStringValue() {
		return stringValue;
	}

}
