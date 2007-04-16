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
 * Represent a break statement
 * <pre>e.g.<pre> throw $exceptionClass;
 */
public class ThrowStatement extends Statement {

	private final Expression expr;

	public ThrowStatement(int start, int end, Expression expr) {
		super(start, end);

		assert expr != null;
		this.expr = expr;

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
		buffer.append(tab).append("<ThrowStatement");
		appendInterval(buffer);
		buffer.append(">\n");
		expr.toString(buffer, TAB + tab);
		buffer.append("\n");
		buffer.append(tab).append("</ThrowStatement>");
	}

	public int getType() {
		return ASTNode.THROW_STATEMENT;
	}

	public Expression getExpr() {
		return expr;
	}
}
