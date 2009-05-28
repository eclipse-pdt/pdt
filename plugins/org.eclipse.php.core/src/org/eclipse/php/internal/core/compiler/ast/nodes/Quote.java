/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.core.compiler.ast.nodes;

import java.util.List;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.utils.CorePrinter;
import org.eclipse.php.internal.core.compiler.ast.visitor.ASTPrintVisitor;

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
	public static final int QT_NOWDOC = 3;

	private final List<? extends Expression> expressions;
	private final int quoteType;

	public Quote(int start, int end, List<? extends Expression> expressions, int type) {
		super(start, end);

		this.expressions = expressions;
		this.quoteType = type;
	}

	public static String getType(int type) {
		switch (type) {
			case QT_QUOTE:
				return "quote"; //$NON-NLS-1$
			case QT_SINGLE:
				return "single"; //$NON-NLS-1$
			case QT_HEREDOC:
				return "heredoc"; //$NON-NLS-1$
			case QT_NOWDOC:
				return "nowdoc"; //$NON-NLS-1$
			default:
				throw new IllegalArgumentException();
		}
	}

	public void traverse(ASTVisitor visitor) throws Exception {
		final boolean visit = visitor.visit(this);
		if (visit) {
			for (Expression expression : expressions) {
				expression.traverse(visitor);
			}
		}
		visitor.endvisit(this);
	}

	public int getKind() {
		return ASTNodeKinds.QUOTE;
	}

	public List<? extends Expression> getExpressions() {
		return expressions;
	}

	public int getQuoteType() {
		return quoteType;
	}

	/**
	 * We don't print anything - we use {@link ASTPrintVisitor} instead
	 */
	public final void printNode(CorePrinter output) {
	}

	public String toString() {
		return ASTPrintVisitor.toXMLString(this);
	}
}
