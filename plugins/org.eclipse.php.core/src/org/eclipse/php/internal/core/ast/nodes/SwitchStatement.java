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
 * Represents a switch statement.
 * <pre>e.g.<pre> 
 * switch ($i) {
 *   case 0:
 *     echo "i equals 0";
 *     break;
 *   case 1:
 *     echo "i equals 1";
 *     break;
 *   default:
 *     echo "i not equals 0 or 1";
 *     break;
 * }
 */
public class SwitchStatement extends Statement {

	private final Expression expr;
	private final Block statement;

	public SwitchStatement(int start, int end, Expression expr, Block statement) {
		super(start, end);

		assert expr != null && statement != null;
		this.expr = expr;
		this.statement = statement;

		expr.setParent(this);
		statement.setParent(this);
	}

	public void accept(Visitor visitor) {
		final boolean visit = visitor.visit(this);
		if (visit) {
			childrenAccept(visitor);
		}
		visitor.endVisit(this);
	}	

	public void childrenAccept(Visitor visitor) {
		expr.accept(visitor);
		statement.accept(visitor);
	}

	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		expr.traverseTopDown(visitor);
		statement.traverseTopDown(visitor);
	}

	public void traverseBottomUp(Visitor visitor) {
		expr.traverseBottomUp(visitor);
		statement.traverseBottomUp(visitor);
		accept(visitor);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<SwitchStatement"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(">\n"); //$NON-NLS-1$
		buffer.append(TAB).append(tab).append("<Expression>\n"); //$NON-NLS-1$
		expr.toString(buffer, TAB + TAB + tab);
		buffer.append("\n"); //$NON-NLS-1$
		buffer.append(TAB).append(tab).append("</Expression>\n"); //$NON-NLS-1$
		statement.toString(buffer, TAB + tab);
		buffer.append("\n").append(tab).append("</SwitchStatement>"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public int getType() {
		return ASTNode.SWITCH_STATEMENT;
	}

	public Expression getExpr() {
		return expr;
	}

	public Block getStatement() {
		return statement;
	}
	
	/* 
	 * Method declared on ASTNode.
	 */
	public boolean subtreeMatch(ASTMatcher matcher, Object other) {
		// dispatch to correct overloaded match method
		return matcher.match(this, other);
	}
}
