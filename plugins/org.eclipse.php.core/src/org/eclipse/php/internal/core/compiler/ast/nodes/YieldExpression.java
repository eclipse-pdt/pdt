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

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.utils.CorePrinter;
import org.eclipse.php.internal.core.compiler.ast.visitor.ASTPrintVisitor;

/**
 * Represent a yield statement
 * 
 * <pre>e.g.
 * 
 * <pre>
 * yield;
 * yield $a;
 * yield $k => $b;
 */
public class YieldExpression extends Expression {

	private final Expression key;
	private final Expression expr;

	public YieldExpression(int start, int end) {
		this(start, end, null);
	}

	public YieldExpression(int start, int end, Expression expr) {
		super(start, end);
		this.expr = expr;
		this.key = null;
	}

	public YieldExpression(int start, int end, Expression key, Expression expr) {
		super(start, end);
		this.expr = expr;
		this.key = key;
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

	/**
	 * We don't print anything - we use {@link ASTPrintVisitor} instead
	 */
	public final void printNode(CorePrinter output) {
	}

	public String toString() {
		return ASTPrintVisitor.toXMLString(this);
	}
}
