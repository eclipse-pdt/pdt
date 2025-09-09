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
 * Represents an indirect reference to a variable.
 * 
 * <pre>
 * e.g.
 * 
 * $$a $$foo() ${foo()}
 * </pre>
 */
public class ReflectionVariableReference extends Expression {

	private Expression expression;

	public ReflectionVariableReference(int start, int end, Expression name) {
		super(start, end);
		this.expression = name;
	}

	@Override
	public int getKind() {
		return ASTNodeKinds.REFLECTION_VARIABLE;
	}

	@Override
	public void traverse(ASTVisitor visitor) throws Exception {
		if (visitor.visit(this)) {
			expression.traverse(visitor);
			visitor.endvisit(this);
		}
	}

	public Expression getExpression() {
		return this.expression;
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
