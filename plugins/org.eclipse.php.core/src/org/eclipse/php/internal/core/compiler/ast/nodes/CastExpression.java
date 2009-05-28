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
 * Represents a type casting expression
 * <pre>e.g.<pre> (int) $a,
 * (string) $b->foo()
 */
public class CastExpression extends Expression {

	// 'int'
	public static final int TYPE_INT = 0;
	// 'real'
	public static final int TYPE_REAL = 1;
	// 'string'
	public static final int TYPE_STRING = 2;
	// 'array'
	public static final int TYPE_ARRAY = 3;
	// 'object'
	public static final int TYPE_OBJECT = 4;
	// 'bool'
	public static final int TYPE_BOOL = 5;
	// 'unset'
	public static final int TYPE_UNSET = 6;

	private final Expression expr;
	private final int castType;

	public CastExpression(int start, int end, Expression expr, int castType) {
		super(start, end);

		assert expr != null;
		this.expr = expr;
		this.castType = castType;
	}

	public void traverse(ASTVisitor visitor) throws Exception {
		final boolean visit = visitor.visit(this);
		if (visit) {
			expr.traverse(visitor);
		}
		visitor.endvisit(this);
	}
	public static String getCastType(int type) {
		switch (type) {
			case TYPE_INT:
				return "int"; //$NON-NLS-1$
			case TYPE_REAL:
				return "real"; //$NON-NLS-1$
			case TYPE_STRING:
				return "string"; //$NON-NLS-1$
			case TYPE_ARRAY:
				return "array"; //$NON-NLS-1$
			case TYPE_OBJECT:
				return "object"; //$NON-NLS-1$
			case TYPE_BOOL:
				return "bool"; //$NON-NLS-1$
			case TYPE_UNSET:
				return "unset"; //$NON-NLS-1$
			default:
				throw new IllegalArgumentException();
		}
	}

	public int getKind() {
		return ASTNodeKinds.CAST_EXPRESSION;
	}

	public int getCastType() {
		return castType;
	}

	public Expression getExpr() {
		return expr;
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
