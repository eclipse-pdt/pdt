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
 * Represents if statement
 * <pre>e.g.<pre> 
 * if ($a > $b) {
 *   echo "a is bigger than b";
 * } elseif ($a == $b) {
 *   echo "a is equal to b";
 * } else {
 *   echo "a is smaller than b";
 * },
 * 
 * if ($a):
 *   echo "a is bigger than b";
 *   echo "a is NOT bigger than b";
 * endif;
 */
public class IfStatement extends Statement {

	private final Expression condition;
	private final Statement trueStatement;
	private final Statement falseStatement;

	public IfStatement(int start, int end, Expression condition, Statement trueStatement, Statement falseStatement) {
		super(start, end);

		assert condition != null && trueStatement != null;
		this.condition = condition;
		this.trueStatement = trueStatement;
		this.falseStatement = falseStatement;

		condition.setParent(this);
		trueStatement.setParent(this);
		if (falseStatement != null) {
			falseStatement.setParent(this);
		}
	}

	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	public void childrenAccept(Visitor visitor) {
		condition.accept(visitor);
		trueStatement.accept(visitor);
		if (falseStatement != null) {
			falseStatement.accept(visitor);
		}
	}

	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		condition.traverseTopDown(visitor);
		trueStatement.traverseTopDown(visitor);
		if (falseStatement != null) {
			falseStatement.traverseTopDown(visitor);
		}
	}

	public void traverseBottomUp(Visitor visitor) {
		condition.traverseBottomUp(visitor);
		trueStatement.traverseBottomUp(visitor);
		if (falseStatement != null) {
			falseStatement.traverseBottomUp(visitor);
		}
		accept(visitor);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<IfStatement");
		appendInterval(buffer);
		buffer.append(">\n");
		buffer.append(TAB).append(tab).append("<Condition>\n");
		condition.toString(buffer, TAB + TAB + tab);
		buffer.append("\n");
		buffer.append(TAB).append(tab).append("</Condition>\n");
		buffer.append(TAB).append(tab).append("<TrueStatement>\n");
		trueStatement.toString(buffer, TAB + TAB + tab);
		buffer.append("\n");
		buffer.append(TAB).append(tab).append("</TrueStatement>\n");
		buffer.append(TAB).append(tab).append("<FalseStatement>\n");
		if (falseStatement != null) {
			falseStatement.toString(buffer, TAB + TAB + tab);
			buffer.append("\n");
		}
		buffer.append(TAB).append(tab).append("</FalseStatement>\n");
		buffer.append(tab).append("</IfStatement>");
	}

	public int getType() {
		return ASTNode.IF_STATEMENT;
	}

	public Expression getCondition() {
		return condition;
	}

	public Statement getFalseStatement() {
		return falseStatement;
	}

	public Statement getTrueStatement() {
		return trueStatement;
	}
}
