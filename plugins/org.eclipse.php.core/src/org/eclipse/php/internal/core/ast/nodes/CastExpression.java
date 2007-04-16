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
 * Represents a type casting expression
 * <pre>e.g.<pre> (int) $a,
 * (string) $b->foo()
 */
public class CastExpression extends Expression {

	// 'int'
	public static final int TYPE_INT = 0;
	// 'real'
	public static final int TYPE_REAL = 1;
	// 'string'
	public static final int TYPE_STRING = 2;
	// 'array'	
	public static final int TYPE_ARRAY = 3;
	// 'object'	
	public static final int TYPE_OBJECT = 4;
	// 'bool'	
	public static final int TYPE_BOOL = 5;
	// 'unset'	
	public static final int TYPE_UNSET = 6;

	private final Expression expr;
	private final int castType;

	public CastExpression(int start, int end, Expression expr, int castType) {
		super(start, end);

		assert expr != null;
		this.expr = expr;
		this.castType = castType;

		expr.setParent(this);
	}

	public static String getCastType(int type) {
		switch (type) {
			case TYPE_INT:
				return "int";
			case TYPE_REAL:
				return "real";
			case TYPE_STRING:
				return "string";
			case TYPE_ARRAY:
				return "array";
			case TYPE_OBJECT:
				return "object";
			case TYPE_BOOL:
				return "bool";
			case TYPE_UNSET:
				return "unset";
			default:
				throw new IllegalArgumentException();
		}
	}

	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	public void childrenAccept(Visitor visitor) {
		expr.accept(visitor);
	}

	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		expr.traverseTopDown(visitor);
	}

	public void traverseBottomUp(Visitor visitor) {
		expr.traverseBottomUp(visitor);
		accept(visitor);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<CastExpression");
		appendInterval(buffer);
		buffer.append(" castType='").append(getCastType(castType)).append("'>\n");
		expr.toString(buffer, TAB + tab);
		buffer.append("\n").append(tab).append("</CastExpression>");
	}

	public int getType() {
		return ASTNode.CAST_EXPRESSION;
	}

	public int getCastType() {
		return castType;
	}

	public Expression getExpr() {
		return expr;
	}
}
