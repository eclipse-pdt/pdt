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
 * Represents an unary operation expression
 * <pre>e.g.<pre> +$a,
 * -3,
 * -foo(),
 * +-+-$a
 */
public class UnaryOperation extends Expression {

	// '+'
	public static final int OP_PLUS = 0;
	// '-'
	public static final int OP_MINUS = 1;
	// '!'
	public static final int OP_NOT = 2;
	// '~'	
	public static final int OP_TILDA = 3;

	private final Expression expr;
	private final int operator;

	public UnaryOperation(int start, int end, Expression expr, int operator) {
		super(start, end);

		assert expr != null;
		this.expr = expr;
		this.operator = operator;

		expr.setParent(this);
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
		buffer.append(tab).append("<UnaryOperation"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(" operator='").append(getOperator(operator)).append("'>\n"); //$NON-NLS-1$ //$NON-NLS-2$
		expr.toString(buffer, TAB + tab);
		buffer.append("\n").append(tab).append("</UnaryOperation>"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public static String getOperator(int operator) {
		switch (operator) {
			case OP_PLUS:
				return "+"; //$NON-NLS-1$
			case OP_MINUS:
				return "-"; //$NON-NLS-1$
			case OP_NOT:
				return "!"; //$NON-NLS-1$
			case OP_TILDA:
				return "~"; //$NON-NLS-1$
			default:
				throw new IllegalArgumentException();
		}
	}

	public int getType() {
		return ASTNode.UNARY_OPERATION;
	}

	public Expression getExpr() {
		return expr;
	}

	public int getOperator() {
		return operator;
	}
}
