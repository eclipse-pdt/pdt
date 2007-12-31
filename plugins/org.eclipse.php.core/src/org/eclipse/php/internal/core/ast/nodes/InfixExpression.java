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
 * Represents an infix expression
 * <pre>e.g.<pre> $a + 1,
 * 3 - 2,
 * foo() * $a->bar(),
 * 'string'.$c
 */
public class InfixExpression extends Expression {

	// '==='
	public static final int OP_IS_IDENTICAL = 0;
	// '!=='
	public static final int OP_IS_NOT_IDENTICAL = 1;
	// '=='
	public static final int OP_IS_EQUAL = 2;
	// '!='	
	public static final int OP_IS_NOT_EQUAL = 3;
	// '<'
	public static final int OP_RGREATER = 4;
	// '<='
	public static final int OP_IS_SMALLER_OR_EQUAL = 5;
	// '>'
	public static final int OP_LGREATER = 6;
	// '>='	
	public static final int OP_IS_GREATER_OR_EQUAL = 7;
	// '||'
	public static final int OP_BOOL_OR = 8;
	// '&&'
	public static final int OP_BOOL_AND = 9;
	// 'or'
	public static final int OP_STRING_OR = 10;
	// 'and'	
	public static final int OP_STRING_AND = 11;
	// 'xor'
	public static final int OP_STRING_XOR = 12;
	// '|'
	public static final int OP_OR = 13;
	// '&'
	public static final int OP_AND = 14;
	// '^'	
	public static final int OP_XOR = 15;
	// '.'
	public static final int OP_CONCAT = 16;
	// '+'
	public static final int OP_PLUS = 17;
	// '-'
	public static final int OP_MINUS = 18;
	// '*'	
	public static final int OP_MUL = 19;
	// '/'
	public static final int OP_DIV = 20;
	// '%'
	public static final int OP_MOD = 21;
	// '<<'
	public static final int OP_SL = 22;
	// '>>'	
	public static final int OP_SR = 23;

	private final Expression right;
	private final int operator;
	private final Expression left;

	public InfixExpression(int start, int end, Expression left, int operator, Expression right) {
		super(start, end);

		assert right != null && left != null;
		this.left = left;
		this.operator = operator;
		this.right = right;

		left.setParent(this);
		right.setParent(this);
	}

	public static String getOperator(int operator) {
		switch (operator) {
			case OP_IS_IDENTICAL:
				return "==="; //$NON-NLS-1$
			case OP_IS_NOT_IDENTICAL:
				return "!=="; //$NON-NLS-1$
			case OP_IS_EQUAL:
				return "=="; //$NON-NLS-1$
			case OP_IS_NOT_EQUAL:
				return "!="; //$NON-NLS-1$
			case OP_RGREATER:
				return "<"; //$NON-NLS-1$
			case OP_IS_SMALLER_OR_EQUAL:
				return "<="; //$NON-NLS-1$
			case OP_LGREATER:
				return ">"; //$NON-NLS-1$
			case OP_IS_GREATER_OR_EQUAL:
				return ">="; //$NON-NLS-1$
			case OP_BOOL_OR:
				return "||"; //$NON-NLS-1$
			case OP_BOOL_AND:
				return "&&"; //$NON-NLS-1$
			case OP_STRING_OR:
				return "or"; //$NON-NLS-1$
			case OP_STRING_AND:
				return "and"; //$NON-NLS-1$
			case OP_STRING_XOR:
				return "xor"; //$NON-NLS-1$
			case OP_OR:
				return "|"; //$NON-NLS-1$
			case OP_AND:
				return "&"; //$NON-NLS-1$
			case OP_XOR:
				return "^"; //$NON-NLS-1$
			case OP_CONCAT:
				return "."; //$NON-NLS-1$
			case OP_PLUS:
				return "+"; //$NON-NLS-1$
			case OP_MINUS:
				return "-"; //$NON-NLS-1$
			case OP_MUL:
				return "*"; //$NON-NLS-1$
			case OP_DIV:
				return "/"; //$NON-NLS-1$
			case OP_MOD:
				return "%"; //$NON-NLS-1$
			case OP_SL:
				return "<<"; //$NON-NLS-1$
			case OP_SR:
				return ">>"; //$NON-NLS-1$
			default:
				throw new IllegalArgumentException();
		}
	}

	public void accept(Visitor visitor) {
		final boolean visit = visitor.visit(this);
		if (visit) {
			childrenAccept(visitor);
		}
		visitor.endVisit(this);
	}	

	public void childrenAccept(Visitor visitor) {
		left.accept(visitor);
		right.accept(visitor);
	}

	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		left.traverseTopDown(visitor);
		right.traverseTopDown(visitor);
	}

	public void traverseBottomUp(Visitor visitor) {
		left.traverseBottomUp(visitor);
		right.traverseBottomUp(visitor);
		accept(visitor);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<InfixExpression"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(" operator='").append(getXmlStringValue(getOperator(operator))).append("'>\n"); //$NON-NLS-1$ //$NON-NLS-2$
		left.toString(buffer, TAB + tab);
		buffer.append("\n"); //$NON-NLS-1$
		right.toString(buffer, TAB + tab);
		buffer.append("\n").append(tab).append("</InfixExpression>"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public int getType() {
		return ASTNode.INFIX_EXPRESSION;
	}

	public Expression getLeft() {
		return left;
	}

	public int getOperator() {
		return operator;
	}

	public Expression getRight() {
		return right;
	}
}
