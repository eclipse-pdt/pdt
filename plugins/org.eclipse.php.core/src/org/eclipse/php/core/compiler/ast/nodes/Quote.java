/*******************************************************************************
 * Copyright (c) 2009, 2018 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.core.compiler.ast.nodes;

import java.util.List;

import org.eclipse.dltk.annotations.NonNull;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.utils.CorePrinter;
import org.eclipse.php.internal.core.compiler.ast.visitor.ASTPrintVisitor;

/**
 * Represents complex quote(i.e. quote that includes string and variables). Also
 * represents heredoc/nowdoc.
 * 
 * <pre>
 * e.g.
 * 
 * "this is $a quote", "'single ${$complex->quote()}'" >>>Heredoc\n This is here
 * documents \nHeredoc;\n
 * 
 * Note: "This is".$not." a quote node", 'This is $not a quote too'
 * </pre>
 */
public class Quote extends Expression {
	public static final int QT_QUOTE = 0;
	public static final int QT_SINGLE = 1;
	public static final int QT_HEREDOC = 2;
	public static final int QT_NOWDOC = 3;

	private final List<? extends Expression> expressions;
	private final int quoteType;
	private final String innerIndentation;

	public Quote(int start, int end, List<? extends Expression> expressions, int type, String innerIndentation) {
		super(start, end);
		if (innerIndentation == null) {
			throw new IllegalArgumentException();
		}

		this.expressions = expressions;
		this.quoteType = type;
		this.innerIndentation = innerIndentation;
	}

	public Quote(int start, int end, List<? extends Expression> expressions, int type) {
		this(start, end, expressions, type, ""); //$NON-NLS-1$
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

	@Override
	public void traverse(ASTVisitor visitor) throws Exception {
		final boolean visit = visitor.visit(this);
		if (visit) {
			for (Expression expression : expressions) {
				expression.traverse(visitor);
			}
		}

		visitor.endvisit(this);
	}

	@Override
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
	 * Returns the (inner) indentation of a HEREDOC or a NOWDOC section, based
	 * on the indentation of the closing HEREDOC/NOWDOC marker. The returned
	 * value has no special meaning for any other <code>quoteType</code> value.
	 * 
	 * @return the HEREDOC/NOWDOC inner indentation, or an empty string
	 *         otherwise
	 * @since PHP 7.3
	 */
	@SuppressWarnings("null")
	@NonNull
	public String getInnerIndentation() {
		return innerIndentation;
	}

	/**
	 * We don't print anything - we use {@link ASTPrintVisitor} instead
	 */
	@Override
	public final void printNode(CorePrinter output) {
	}

	@Override
	public String toString() {
		return ASTPrintVisitor.toXMLString(this);
	}
}
