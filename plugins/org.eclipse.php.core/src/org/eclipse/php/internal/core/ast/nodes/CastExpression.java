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

	public void accept(Visitor visitor) {
		final boolean visit = visitor.visit(this);
		if (visit) {
			childrenAccept(visitor);
		}
		visitor.endVisit(this);
	}	
	public static String getCastType(int type) {
		switch (type) {
			case TYPE_INT:
				return "int"; //$NON-NLS-1$
			case TYPE_REAL:
				return "real"; //$NON-NLS-1$
			case TYPE_STRING:
				return "string"; //$NON-NLS-1$
			case TYPE_ARRAY:
				return "array"; //$NON-NLS-1$
			case TYPE_OBJECT:
				return "object"; //$NON-NLS-1$
			case TYPE_BOOL:
				return "bool"; //$NON-NLS-1$
			case TYPE_UNSET:
				return "unset"; //$NON-NLS-1$
			default:
				throw new IllegalArgumentException();
		}
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
		buffer.append(tab).append("<CastExpression"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(" castType='").append(getCastType(castType)).append("'>\n"); //$NON-NLS-1$ //$NON-NLS-2$
		expr.toString(buffer, TAB + tab);
		buffer.append("\n").append(tab).append("</CastExpression>"); //$NON-NLS-1$ //$NON-NLS-2$
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
