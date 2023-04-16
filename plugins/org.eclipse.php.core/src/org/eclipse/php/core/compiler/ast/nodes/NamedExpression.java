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
 * Represents a named expression
 * 
 * <pre>
 * e.g.
 * 
 * --$a, --foo()
 * </pre>
 */
public class NamedExpression extends Expression {

	private final String name;
	private final Expression variable;

	public NamedExpression(int start, int end, String name, Expression variable) {
		super(start, end);

		assert variable != null;
		assert name != null;

		this.name = name;
		this.variable = variable;

	}

	@Override
	public void traverse(ASTVisitor visitor) throws Exception {
		final boolean visit = visitor.visit(this);
		if (visit) {
			variable.traverse(visitor);
		}
		visitor.endvisit(this);
	}

	@Override
	public int getKind() {
		return ASTNodeKinds.NAMED_EXPRESSION;
	}

	public Expression getVariable() {
		return variable;
	}

	public String getName() {
		return name;
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
