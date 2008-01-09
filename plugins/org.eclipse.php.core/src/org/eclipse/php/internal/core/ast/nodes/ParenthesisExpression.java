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

import org.eclipse.php.internal.core.ast.match.ASTMatcher;
import org.eclipse.php.internal.core.ast.visitor.Visitor;

/**
 * Represents a parenthesis expression
 * <pre>e.g.<pre> ( $a == 2 );
 * echo ($a);
 */
public class ParenthesisExpression extends Expression {

	private final Expression expr;

	public ParenthesisExpression(int start, int end) {
		this(start, end, null);
	}

	public ParenthesisExpression(int start, int end, Expression expr) {
		super(start, end);
		this.expr = expr;

		if (expr != null) {
			expr.setParent(this);
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
		if (expr != null) {
			expr.accept(visitor);
		}
	}

	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		if (expr != null) {
			expr.traverseTopDown(visitor);
		}
	}

	public void traverseBottomUp(Visitor visitor) {
		if (expr != null) {
			expr.traverseBottomUp(visitor);
		}
		accept(visitor);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<ParenthesisExpression"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(">\n"); //$NON-NLS-1$
		if (expr != null) {
			expr.toString(buffer, TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
		}
		buffer.append(tab).append("</ParenthesisExpression>"); //$NON-NLS-1$
	}

	public int getType() {
		return ASTNode.PARENTHESIS_EXPRESSION;
	}

	public Expression getExpr() {
		return expr;
	}
	
	/* 
	 * Method declared on ASTNode.
	 */
	public boolean subtreeMatch(ASTMatcher matcher, Object other) {
		// dispatch to correct overloaded match method
		return matcher.match(this, other);
	}
}
