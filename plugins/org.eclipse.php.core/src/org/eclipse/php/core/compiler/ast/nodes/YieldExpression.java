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
package org.eclipse.php.core.compiler.ast.nodes;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.utils.CorePrinter;
import org.eclipse.php.internal.core.compiler.ast.visitor.ASTPrintVisitor;

/**
 * Represent a yield statement
 * 
 * <pre>
 * e.g.
 * 
 * <pre>
 * yield;
 * yield $a;
 * yield $k => $b;
 * yield from $a;
 * </pre>
 */
public class YieldExpression extends Expression {

	// yield $a or yield $k => $a
	public static final int OP_NONE = 0;
	// yield from $k
	public static final int OP_FROM = 1;

	private final Expression key;
	private final Expression expr;
	private int operator;

	public YieldExpression(int start, int end) {
		this(start, end, null);
	}

	public YieldExpression(int start, int end, Expression expr) {
		this(start, end, null, expr);
	}

	public YieldExpression(int start, int end, Expression key, Expression expr) {
		super(start, end);
		this.expr = expr;
		this.key = key;
		this.operator = OP_NONE;
	}

	public YieldExpression(int start, int end, Expression expr, int operator) {
		this(start, end, expr, null);
		this.operator = operator;
	}

	public void traverse(ASTVisitor visitor) throws Exception {
		final boolean visit = visitor.visit(this);
		if (visit) {
			if (key != null) {
				key.traverse(visitor);
			}
			if (expr != null) {
				expr.traverse(visitor);
			}
		}
		visitor.endvisit(this);
	}

	public int getKind() {
		return ASTNodeKinds.YIELD_STATEMENT;
	}

	public Expression getExpr() {
		return expr;
	}

	public Expression getKey() {
		return key;
	}

	public int getOperatorType() {
		return operator;
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
