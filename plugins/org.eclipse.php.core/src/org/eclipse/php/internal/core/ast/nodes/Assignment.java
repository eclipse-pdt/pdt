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
 * Represents an assignment statement.
 * <pre>e.g.<pre> $a = 5,
 * $a += 5,
 * $a .= $b,
 */
public class Assignment extends Expression {

	// '='
	public static final int OP_EQUAL = 0;
	// '+='
	public static final int OP_PLUS_EQUAL = 1;
	// '-='
	public static final int OP_MINUS_EQUAL = 2;
	// '*='	
	public static final int OP_MUL_EQUAL = 3;
	// '/='	
	public static final int OP_DIV_EQUAL = 4;
	// '.='	
	public static final int OP_CONCAT_EQUAL = 5;
	// '%='	
	public static final int OP_MOD_EQUAL = 6;
	// '&='	
	public static final int OP_AND_EQUAL = 7;
	// '|='	
	public static final int OP_OR_EQUAL = 8;
	// '^='	
	public static final int OP_XOR_EQUAL = 9;
	// '<<='	
	public static final int OP_SL_EQUAL = 10;
	// '>>='	
	public static final int OP_SR_EQUAL = 11;

	private final VariableBase variable;
	private final int operator;
	private final Expression value;

	public Assignment(int start, int end, VariableBase variable, int operator, Expression value) {
		super(start, end);

		assert variable != null && value != null;

		this.variable = variable;
		this.operator = operator;
		this.value = value;

		variable.setParent(this);
		value.setParent(this);
	}

	public static String getOperator(int operator) {
		switch (operator) {
			case OP_EQUAL:
				return "="; //$NON-NLS-1$
			case OP_PLUS_EQUAL:
				return "+="; //$NON-NLS-1$
			case OP_MINUS_EQUAL:
				return "-="; //$NON-NLS-1$
			case OP_MUL_EQUAL:
				return "*="; //$NON-NLS-1$
			case OP_DIV_EQUAL:
				return "/="; //$NON-NLS-1$
			case OP_MOD_EQUAL:
				return "%="; //$NON-NLS-1$
			case OP_CONCAT_EQUAL:
				return ".="; //$NON-NLS-1$
			case OP_AND_EQUAL:
				return "&="; //$NON-NLS-1$
			case OP_OR_EQUAL:
				return "|="; //$NON-NLS-1$
			case OP_XOR_EQUAL:
				return "^="; //$NON-NLS-1$
			case OP_SL_EQUAL:
				return "<<="; //$NON-NLS-1$
			case OP_SR_EQUAL:
				return ">>="; //$NON-NLS-1$
			default:
				throw new IllegalArgumentException();
		}
	}

	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	public void childrenAccept(Visitor visitor) {
		variable.accept(visitor);
		value.accept(visitor);
	}

	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		variable.traverseTopDown(visitor);
		value.traverseTopDown(visitor);
	}

	public void traverseBottomUp(Visitor visitor) {
		variable.traverseBottomUp(visitor);
		value.traverseBottomUp(visitor);
		accept(visitor);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<Assignment"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(" operator='").append(getXmlStringValue(getOperator(operator))).append("'>\n"); //$NON-NLS-1$ //$NON-NLS-2$
		variable.toString(buffer, TAB + tab);
		buffer.append("\n").append(TAB).append(tab).append("<Value>\n"); //$NON-NLS-1$ //$NON-NLS-2$
		value.toString(buffer, TAB + TAB + tab);
		buffer.append("\n").append(TAB).append(tab).append("</Value>\n"); //$NON-NLS-1$ //$NON-NLS-2$
		buffer.append(tab).append("</Assignment>"); //$NON-NLS-1$
	}

	public int getType() {
		return ASTNode.ASSIGNMENT;
	}

	public int getOperator() {
		return operator;
	}

	public Expression getValue() {
		return value;
	}

	public VariableBase getVariable() {
		return variable;
	}
}
