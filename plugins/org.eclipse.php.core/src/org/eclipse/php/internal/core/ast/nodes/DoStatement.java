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
 * Represent do while statement.
 * <pre>e.g.<pre>
 * do {
 *   echo $i;
 * } while ($i > 0);
 */
public class DoStatement extends Statement {

	private final Expression condition;
	private final Statement action;

	public DoStatement(int start, int end, Expression condition, Statement action) {
		super(start, end);

		assert condition != null && action != null;
		this.condition = condition;
		this.action = action;

		condition.setParent(this);
		action.setParent(this);
	}

	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	public void childrenAccept(Visitor visitor) {
		condition.accept(visitor);
		action.accept(visitor);
	}

	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		condition.traverseTopDown(visitor);
		action.traverseTopDown(visitor);
	}

	public void traverseBottomUp(Visitor visitor) {
		condition.traverseBottomUp(visitor);
		action.traverseBottomUp(visitor);
		accept(visitor);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<DoStatement"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(">\n"); //$NON-NLS-1$
		action.toString(buffer, TAB + tab);
		buffer.append("\n"); //$NON-NLS-1$
		buffer.append(TAB).append(tab).append("<Condition>\n"); //$NON-NLS-1$
		condition.toString(buffer, TAB + TAB + tab);
		buffer.append("\n"); //$NON-NLS-1$
		buffer.append(TAB).append(tab).append("</Condition>\n"); //$NON-NLS-1$
		buffer.append(tab).append("</DoStatement>"); //$NON-NLS-1$
	}

	public int getType() {
		return ASTNode.DO_STATEMENT;
	}

	public Statement getAction() {
		return action;
	}

	public Expression getCondition() {
		return condition;
	}
}
