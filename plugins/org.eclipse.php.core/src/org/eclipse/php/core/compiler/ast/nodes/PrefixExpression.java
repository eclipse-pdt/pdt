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
 * Represents a prefix expression
 * 
 * <pre>
 * e.g.
 * 
 * --$a, --foo()
 * </pre>
 */
public class PrefixExpression extends Expression {

	// '++'
	public static final int OP_INC = 0;
	// '--'
	public static final int OP_DEC = 1;
	// '...'
	public static final int OP_UNPACK = 2;

	private final Expression variable;
	private final int operator;

	public PrefixExpression(int start, int end, Expression variable, int operator) {
		super(start, end);

		assert variable != null;

		this.variable = variable;
		this.operator = operator;
	}

	@Override
	public void traverse(ASTVisitor visitor) throws Exception {
		if (visitor.visit(this)) {
			variable.traverse(visitor);
			visitor.endvisit(this);
		}
	}

	@Override
	public String getOperator() {
		switch (getOperatorType()) {
		case OP_DEC:
			return "--"; //$NON-NLS-1$
		case OP_INC:
			return "++"; //$NON-NLS-1$
		case OP_UNPACK:
			return "..."; //$NON-NLS-1$
		default:
			throw new IllegalArgumentException();
		}
	}

	@Override
	public int getKind() {
		return ASTNodeKinds.PREFIX_EXPRESSION;
	}

	public int getOperatorType() {
		return operator;
	}

	public Expression getVariable() {
		return variable;
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
