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
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.utils.CorePrinter;
import org.eclipse.php.internal.core.compiler.ast.visitor.ASTPrintVisitor;

/**
 * Represent a return statement
 * 
 * <pre>
 * e.g.
 * 
 * return; return $a;
 * </pre>
 */
public class ReturnStatement extends Statement {

	private final Expression expr;

	public ReturnStatement(int start, int end) {
		this(start, end, null);
	}

	public ReturnStatement(int start, int end, Expression expr) {
		super(start, end);
		this.expr = expr;
	}

	@Override
	public void traverse(ASTVisitor visitor) throws Exception {
		if (visitor.visit(this)) {
			if (expr != null) {
				expr.traverse(visitor);
			}
			visitor.endvisit(this);
		}
	}

	@Override
	public int getKind() {
		return ASTNodeKinds.RETURN_STATEMENT;
	}

	public Expression getExpr() {
		return expr;
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
