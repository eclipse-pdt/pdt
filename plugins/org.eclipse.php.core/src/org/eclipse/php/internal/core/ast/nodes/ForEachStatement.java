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
 * Represents a for each statement
 * <pre>e.g.<pre>
 * foreach (array_expression as $value)
 *   statement;
 *     
 * foreach (array_expression as $key => $value) 
 *   statement;
 * 
 * foreach (array_expression as $key => $value): 
 *   statement;
 *   ...
 * endforeach;
 */
public class ForEachStatement extends Statement {

	private final Expression expression;
	private final Expression key;
	private final Expression value;
	private final Statement statement;

	public ForEachStatement(int start, int end, Expression expression, Expression key, Expression value, Statement statement) {
		super(start, end);

		assert expression != null && value != null && statement != null;
		this.expression = expression;
		this.key = key;
		this.value = value;
		this.statement = statement;

		expression.setParent(this);
		if (key != null) {
			key.setParent(this);
		}
		value.setParent(this);
		statement.setParent(this);
	}

	public ForEachStatement(int start, int end, Expression expression, Expression value, Statement statement) {
		this(start, end, expression, null, value, statement);
	}

	public void accept(Visitor visitor) {
		final boolean visit = visitor.visit(this);
		if (visit) {
			childrenAccept(visitor);
		}
		visitor.endVisit(this);
	}	

	public void childrenAccept(Visitor visitor) {
		expression.accept(visitor);
		if (key != null) {
			key.accept(visitor);
		}
		value.accept(visitor);
		statement.accept(visitor);
	}

	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		expression.traverseTopDown(visitor);
		if (key != null) {
			key.traverseTopDown(visitor);
		}
		value.traverseTopDown(visitor);
		statement.traverseTopDown(visitor);
	}

	public void traverseBottomUp(Visitor visitor) {
		expression.traverseBottomUp(visitor);
		if (key != null) {
			key.traverseBottomUp(visitor);
		}
		value.traverseBottomUp(visitor);
		statement.traverseBottomUp(visitor);
		accept(visitor);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<ForEachStatement"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(">\n"); //$NON-NLS-1$
		buffer.append(TAB).append(tab).append("<Expression>\n"); //$NON-NLS-1$
		expression.toString(buffer, TAB + TAB + tab);
		buffer.append("\n").append(TAB).append(tab).append("</Expression>\n"); //$NON-NLS-1$ //$NON-NLS-2$
		buffer.append(TAB).append(tab).append("<Key>\n"); //$NON-NLS-1$
		if (key != null) {
			key.toString(buffer, TAB + TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
		}
		buffer.append(TAB).append(tab).append("</Key>\n"); //$NON-NLS-1$
		buffer.append(TAB).append(tab).append("<Value>\n"); //$NON-NLS-1$
		value.toString(buffer, TAB + TAB + tab);
		buffer.append("\n").append(TAB).append(tab).append("</Value>\n"); //$NON-NLS-1$ //$NON-NLS-2$
		statement.toString(buffer, TAB + tab);
		buffer.append("\n"); //$NON-NLS-1$
		buffer.append(tab).append("</ForEachStatement>"); //$NON-NLS-1$
	}

	public int getType() {
		return ASTNode.FOR_EACH_STATEMENT;
	}

	public Expression getExpression() {
		return expression;
	}

	public Expression getKey() {
		return key;
	}

	public Statement getStatement() {
		return statement;
	}

	public Expression getValue() {
		return value;
	}
	
	/* 
	 * Method declared on ASTNode.
	 */
	public boolean subtreeMatch(ASTMatcher matcher, Object other) {
		// dispatch to correct overloaded match method
		return matcher.match(this, other);
	}
}
