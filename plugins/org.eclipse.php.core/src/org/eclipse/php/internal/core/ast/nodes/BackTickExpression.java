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

import java.util.List;

import org.eclipse.php.internal.core.ast.visitor.Visitor;

/**
 * Represents back tick expression
 * <pre>e.g.<pre> 
 * `.\exec.sh`
 */
public class BackTickExpression extends Expression {

	private final Expression[] expressions;

	public BackTickExpression(int start, int end, Expression[] expressions) {
		super(start, end);

		assert expressions != null;
		this.expressions = expressions;

		// set the child nodes' parent
		for (int i = 0; i < expressions.length; i++) {
			expressions[i].setParent(this);
		}
	}

	public BackTickExpression(int start, int end, List expressions) {
		this(start, end, expressions == null ? null : (Expression[]) expressions.toArray(new Expression[expressions.size()]));
	}

	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	public void childrenAccept(Visitor visitor) {
		for (int i = 0; i < expressions.length; i++) {
			expressions[i].accept(visitor);
		}
	}

	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		for (int i = 0; i < expressions.length; i++) {
			expressions[i].traverseTopDown(visitor);
		}
	}

	public void traverseBottomUp(Visitor visitor) {
		for (int i = 0; i < expressions.length; i++) {
			expressions[i].traverseBottomUp(visitor);
		}
		accept(visitor);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<BackTickExpression");
		appendInterval(buffer);
		buffer.append(">\n");
		for (int i = 0; i < expressions.length; i++) {
			expressions[i].toString(buffer, TAB + tab);
			buffer.append("\n");
		}
		buffer.append(tab).append("</BackTickExpression>");
	}

	public int getType() {
		return ASTNode.BACK_TICK_EXPRESSION;
	}

	public Expression[] getExpressions() {
		return expressions;
	}
}
