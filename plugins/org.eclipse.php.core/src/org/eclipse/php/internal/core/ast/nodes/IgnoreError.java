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
 * Represents ignore error expression
 * <pre>e.g.<pre> '@$a->foo()' 
 */
public class IgnoreError extends Expression {

	private final Expression expr;

	public IgnoreError(int start, int end) {
		this(start, end, null);
	}

	public IgnoreError(int start, int end, Expression expr) {
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
		buffer.append(tab).append("<IgnoreError"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(">\n"); //$NON-NLS-1$
		expr.toString(buffer, TAB + tab);
		buffer.append("\n").append(tab).append("</IgnoreError>"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public int getType() {
		return ASTNode.IGNORE_ERROR;
	}

	public Expression getExpr() {
		return expr;
	}
}
