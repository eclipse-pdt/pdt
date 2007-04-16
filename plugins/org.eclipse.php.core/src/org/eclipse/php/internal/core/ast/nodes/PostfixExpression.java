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
 * Represents a postfix expression
 * <pre>e.g.<pre> $a++,
 * foo()--
 */
public class PostfixExpression extends Expression {

	// '++'
	public static final int OP_INC = 0;
	// '--'
	public static final int OP_DEC = 1;

	private final VariableBase variable;
	private final int operator;

	public PostfixExpression(int start, int end, VariableBase variable, int operator) {
		super(start, end);

		assert variable != null;
		this.variable = variable;
		this.operator = operator;

		variable.setParent(this);
	}

	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	public void childrenAccept(Visitor visitor) {
		variable.accept(visitor);
	}

	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		variable.traverseTopDown(visitor);
	}

	public void traverseBottomUp(Visitor visitor) {
		variable.traverseBottomUp(visitor);
		accept(visitor);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<PostfixExpression");
		appendInterval(buffer);
		buffer.append(" operator='").append(getOperator(operator)).append("'>\n");
		variable.toString(buffer, TAB + tab);
		buffer.append("\n").append(tab).append("</PostfixExpression>");
	}

	public static String getOperator(int operator) {
		switch (operator) {
			case OP_DEC:
				return "--";
			case OP_INC:
				return "++";
			default:
				throw new IllegalArgumentException();
		}
	}

	public int getType() {
		return ASTNode.POSTFIX_EXPRESSION;
	}

	public int getOperator() {
		return operator;
	}

	public VariableBase getVariable() {
		return variable;
	}
}
