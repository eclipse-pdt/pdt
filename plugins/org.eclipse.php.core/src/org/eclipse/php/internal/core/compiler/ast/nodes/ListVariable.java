/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.compiler.ast.nodes;

import java.util.List;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.utils.CorePrinter;
import org.eclipse.php.internal.core.compiler.ast.visitor.ASTPrintVisitor;

/**
 * Represents a list expression.
 * The list contains variables and/or other lists.
 *
 * <pre>e.g.<pre> list($a,$b) = array (1,2),
 * list($a, list($b, $c))
 */
public class ListVariable extends Expression {

	private final Expression[] variables;

	private ListVariable(int start, int end, Expression[] variables) {
		super(start, end);

		assert variables != null;
		this.variables = variables;
	}

	public ListVariable(int start, int end, List<? extends Expression> variables) {
		this(start, end, variables == null ? null : variables.toArray(new Expression[variables.size()]));
	}

	public void traverse(ASTVisitor visitor) throws Exception {
		final boolean visit = visitor.visit(this);
		if (visit) {
			for (Expression variable : variables) {
				variable.traverse(visitor);
			}
		}
		visitor.endvisit(this);
	}

	public int getKind() {
		return ASTNodeKinds.LIST_VARIABLE;
	}

	public Expression[] getVariables() {
		return variables;
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
