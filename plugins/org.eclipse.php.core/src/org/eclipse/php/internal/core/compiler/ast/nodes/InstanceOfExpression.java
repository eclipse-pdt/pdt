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
 * Represent instanceof expression
 * <pre>e.g.<pre> $a instanceof MyClass,
 * foo() instanceof $myClass,
 * $a instanceof $b->$myClass
 */
public class InstanceOfExpression extends Expression {

	private final Expression expr;
	private final Expression className;

	public InstanceOfExpression(int start, int end, Expression expr, Expression type) {
		super(start, end);

		assert expr != null && type != null;
		this.expr = expr;
		this.className = type;
	}

	public void traverse(ASTVisitor visitor) throws Exception {
		final boolean visit = visitor.visit(this);
		if (visit) {
			expr.traverse(visitor);
			className.traverse(visitor);
		}
		visitor.endvisit(this);
	}

	public int getKind() {
		return ASTNodeKinds.INSTANCE_OF_EXPRESSION;
	}

	public Expression getClassName() {
		return className;
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
