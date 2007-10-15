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
 * Represents conditional expression
 * Holds the condition, if true expression and if false expression
 * each on e can be any expression
 * <pre>e.g.<pre> (bool) $a ? 3 : 4
 * $a > 0 ? $a : -$a
 */
public class ConditionalExpression extends Expression {

	private final Expression condition;
	private final Expression ifTrue;
	private final Expression ifFalse;

	public ConditionalExpression(int start, int end, Expression condition, Expression ifTrue, Expression ifFalse) {
		super(start, end);

		assert condition != null && ifTrue != null && ifFalse != null;
		this.condition = condition;
		this.ifTrue = ifTrue;
		this.ifFalse = ifFalse;

		condition.setParent(this);
		ifTrue.setParent(this);
		ifFalse.setParent(this);
	}

	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	public void childrenAccept(Visitor visitor) {
		condition.accept(visitor);
		ifTrue.accept(visitor);
		ifFalse.accept(visitor);
	}

	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		condition.traverseTopDown(visitor);
		ifTrue.traverseTopDown(visitor);
		ifFalse.traverseTopDown(visitor);
	}

	public void traverseBottomUp(Visitor visitor) {
		condition.traverseBottomUp(visitor);
		ifTrue.traverseBottomUp(visitor);
		ifFalse.traverseBottomUp(visitor);
		accept(visitor);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<ConditionalExpression"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(">\n"); //$NON-NLS-1$
		buffer.append(TAB).append(tab).append("<Condition>\n"); //$NON-NLS-1$
		condition.toString(buffer, TAB + TAB + tab);
		buffer.append("\n").append(TAB).append(tab).append("</Condition>\n"); //$NON-NLS-1$ //$NON-NLS-2$
		buffer.append(TAB).append(tab).append("<IfTrue>\n"); //$NON-NLS-1$
		ifTrue.toString(buffer, TAB + TAB + tab);
		buffer.append("\n").append(TAB).append(tab).append("</IfTrue>\n"); //$NON-NLS-1$ //$NON-NLS-2$
		buffer.append(TAB).append(tab).append("<IfFalse>\n"); //$NON-NLS-1$
		ifFalse.toString(buffer, TAB + TAB + tab);
		buffer.append("\n").append(TAB).append(tab).append("</IfFalse>\n"); //$NON-NLS-1$ //$NON-NLS-2$
		buffer.append(tab).append("</ConditionalExpression>"); //$NON-NLS-1$
	}

	public int getType() {
		return ASTNode.CONDITIONAL_EXPRESSION;
	}

	public Expression getCondition() {
		return condition;
	}

	public Expression getIfFalse() {
		return ifFalse;
	}

	public Expression getIfTrue() {
		return ifTrue;
	}
}
