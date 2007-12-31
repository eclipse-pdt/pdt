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
 * Represent a echo statement.
 * <pre>e.g.<pre> echo "hello",
 * echo "hello", "world"
 */
public class EchoStatement extends Statement {

	private final Expression[] expressions;

	private EchoStatement(int start, int end, Expression[] expressions) {
		super(start, end);

		assert expressions != null;
		this.expressions = expressions;

		for (int i = 0; i < expressions.length; i++) {
			expressions[i].setParent(this);
		}
	}

	public EchoStatement(int start, int end, List expressions) {
		this(start, end, (Expression[]) expressions.toArray(new Expression[expressions.size()]));
	}
	
	public void accept(Visitor visitor) {
		final boolean visit = visitor.visit(this);
		if (visit) {
			childrenAccept(visitor);
		}
		visitor.endVisit(this);
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
		buffer.append(tab).append("<EchoStatement"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(">\n"); //$NON-NLS-1$
		for (int i = 0; i < expressions.length; i++) {
			expressions[i].toString(buffer, TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
		}
		buffer.append(tab).append("</EchoStatement>"); //$NON-NLS-1$
	}

	public int getType() {
		return ASTNode.ECHO_STATEMENT;
	}

	public Expression[] getExpressions() {
		return expressions;
	}
}
