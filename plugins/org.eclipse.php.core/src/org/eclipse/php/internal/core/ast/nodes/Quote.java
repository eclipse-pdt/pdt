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

import org.eclipse.php.internal.core.ast.match.ASTMatcher;
import org.eclipse.php.internal.core.ast.visitor.Visitor;

/**
 * Represents complex qoute(i.e. qoute that includes string and variables).
 * Also represents heredoc
 * <pre>e.g.<pre> 
 * "this is $a quote",
 * "'single ${$complex->quote()}'"
 * >>>Heredoc\n  This is here documents \nHeredoc;\n 
 * 
 * Note: "This is".$not." a quote node",
 *       'This is $not a quote too'
 */
public class Quote extends Expression {

	public static final int QT_QUOTE = 0;
	public static final int QT_SINGLE = 1;
	public static final int QT_HEREDOC = 2;

	private final Expression[] expressions;
	private final int quoteType;

	public Quote(int start, int end, Expression[] expressions, int type) {
		super(start, end);

		this.expressions = expressions;
		this.quoteType = type;

		for (int i = 0; i < expressions.length; i++) {
			expressions[i].setParent(this);
		}
	}

	public Quote(int start, int end, List expressions, int type) {
		this(start, end, expressions == null ? null : (Expression[]) expressions.toArray(new Expression[expressions.size()]), type);
	}

	public static String getType(int type) {
		switch (type) {
			case QT_QUOTE:
				return "quote"; //$NON-NLS-1$
			case QT_SINGLE:
				return "single"; //$NON-NLS-1$
			case QT_HEREDOC:
				return "heredoc"; //$NON-NLS-1$
			default:
				throw new IllegalArgumentException();
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
		buffer.append(tab).append("<Quote"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(" type='").append(getType(quoteType)).append("'>\n"); //$NON-NLS-1$ //$NON-NLS-2$
		for (int i = 0; i < expressions.length; i++) {
			expressions[i].toString(buffer, TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
		}
		buffer.append(tab).append("</Quote>"); //$NON-NLS-1$
	}

	public int getType() {
		return ASTNode.QUOTE;
	}

	public Expression[] getExpressions() {
		return expressions;
	}

	public int getQuoteType() {
		return quoteType;
	}
	
	/* 
	 * Method declared on ASTNode.
	 */
	public boolean subtreeMatch(ASTMatcher matcher, Object other) {
		// dispatch to correct overloaded match method
		return matcher.match(this, other);
	}
}
