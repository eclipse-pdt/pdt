/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
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

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.utils.CorePrinter;
import org.eclipse.php.internal.core.compiler.ast.visitor.ASTPrintVisitor;

/**
 * Represents conditional expression Holds the condition, if true expression and
 * if false expression each on e can be any expression
 * 
 * <pre>
 * e.g.
 * 
 * (bool) $a ? 3 : 4, $a > 0 ? $a : -$a, $a ?? 0;
 * </pre>
 */
public class ConditionalExpression extends Expression {

	// ?:
	public static final int OP_TERNARY = 0;
	// ??
	public static final int OP_COALESCE = 1;

	private final Expression condition;
	private final Expression ifTrue;
	private final Expression ifFalse;
	private final int operatorType;

	/**
	 * Constructor for Ternary Operator
	 */
	public ConditionalExpression(int start, int end, Expression condition, Expression ifTrue, Expression ifFalse) {
		super(start, end);

		assert condition != null && ifFalse != null;
		this.condition = condition;
		this.ifTrue = ifTrue;
		this.ifFalse = ifFalse;
		this.operatorType = OP_TERNARY;
	}

	/**
	 * Constructor for Null Coalesce Operator
	 */
	public ConditionalExpression(int start, int end, Expression condition, Expression ifNull) {
		super(start, end);

		assert condition != null && ifNull != null;
		this.condition = condition;
		this.ifTrue = ifNull;
		this.ifFalse = null;
		this.operatorType = OP_COALESCE;
	}

	@Override
	public void traverse(ASTVisitor visitor) throws Exception {
		final boolean visit = visitor.visit(this);
		if (visit) {
			condition.traverse(visitor);
			if (ifTrue != null) {
				ifTrue.traverse(visitor);
			}
			if (ifFalse != null) {
				ifFalse.traverse(visitor);
			}
		}
		visitor.endvisit(this);
	}

	@Override
	public int getKind() {
		return ASTNodeKinds.CONDITIONAL_EXPRESSION;
	}

	public Expression getCondition() {
		return condition;
	}

	public Expression getIfFalse() {
		if (getOperatorType() == OP_COALESCE) {
			return condition;
		}
		return ifFalse;
	}

	public Expression getIfTrue() {
		return ifTrue;
	}

	public int getOperatorType() {
		return operatorType;
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
