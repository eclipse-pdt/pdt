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
 * Represents an assignment statement.
 * <pre>e.g.<pre> $a = 5,
 * $a += 5,
 * $a .= $b,
 */
public class Assignment extends Expression {

	// '='
	public static final int OP_EQUAL = 0;
	// '+='
	public static final int OP_PLUS_EQUAL = 1;
	// '-='
	public static final int OP_MINUS_EQUAL = 2;
	// '*='
	public static final int OP_MUL_EQUAL = 3;
	// '/='
	public static final int OP_DIV_EQUAL = 4;
	// '.='
	public static final int OP_CONCAT_EQUAL = 5;
	// '%='
	public static final int OP_MOD_EQUAL = 6;
	// '&='
	public static final int OP_AND_EQUAL = 7;
	// '|='
	public static final int OP_OR_EQUAL = 8;
	// '^='
	public static final int OP_XOR_EQUAL = 9;
	// '<<='
	public static final int OP_SL_EQUAL = 10;
	// '>>='
	public static final int OP_SR_EQUAL = 11;
	// '=&'
	public static final int OP_REF_EQUAL = 12;

	private final Expression variable;
	private final int operator;
	private final Expression value;

	public Assignment(int start, int end, Expression variable, int operator, Expression value) {
		super(start, end);

		assert variable != null && value != null;

		this.variable = variable;
		this.operator = operator;
		this.value = value;
	}

	public void traverse(ASTVisitor visitor) throws Exception {
		final boolean visit = visitor.visit(this);
		if (visit) {
			variable.traverse(visitor);
			value.traverse(visitor);
		}
		visitor.endvisit(this);
	}

	public String getOperator() {
		switch (getOperatorType()) {
			case OP_EQUAL:
				return "="; //$NON-NLS-1$
			case OP_PLUS_EQUAL:
				return "+="; //$NON-NLS-1$
			case OP_MINUS_EQUAL:
				return "-="; //$NON-NLS-1$
			case OP_MUL_EQUAL:
				return "*="; //$NON-NLS-1$
			case OP_DIV_EQUAL:
				return "/="; //$NON-NLS-1$
			case OP_MOD_EQUAL:
				return "%="; //$NON-NLS-1$
			case OP_CONCAT_EQUAL:
				return ".="; //$NON-NLS-1$
			case OP_AND_EQUAL:
				return "&="; //$NON-NLS-1$
			case OP_OR_EQUAL:
				return "|="; //$NON-NLS-1$
			case OP_XOR_EQUAL:
				return "^="; //$NON-NLS-1$
			case OP_SL_EQUAL:
				return "<<="; //$NON-NLS-1$
			case OP_SR_EQUAL:
				return ">>="; //$NON-NLS-1$
			case OP_REF_EQUAL:
				return "=&"; //$NON-NLS-1$
			default:
				throw new IllegalArgumentException();
		}
	}

	public int getKind() {
		return ASTNodeKinds.ASSIGNMENT;
	}

	public int getOperatorType() {
		return operator;
	}

	public Expression getValue() {
		return value;
	}

	public Expression getVariable() {
		return variable;
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
