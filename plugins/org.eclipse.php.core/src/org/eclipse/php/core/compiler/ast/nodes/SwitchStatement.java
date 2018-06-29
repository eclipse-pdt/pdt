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
import org.eclipse.dltk.ast.statements.Block;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.utils.CorePrinter;
import org.eclipse.php.internal.core.compiler.ast.visitor.ASTPrintVisitor;

/**
 * Represents a switch statement.
 * 
 * <pre>
 * e.g.
 * 
 * <pre>
 * switch ($i) { case 0: echo "i equals 0"; break; case 1: echo "i equals 1";
 * break; default: echo "i not equals 0 or 1"; break; }
 */
public class SwitchStatement extends Statement {

	private final Expression expr;
	private final Block statement;

	public SwitchStatement(int start, int end, Expression expr, Block statement) {
		super(start, end);

		assert expr != null && statement != null;
		this.expr = expr;
		this.statement = statement;
	}

	@Override
	public void traverse(ASTVisitor visitor) throws Exception {
		final boolean visit = visitor.visit(this);
		if (visit) {
			expr.traverse(visitor);
			statement.traverse(visitor);
		}
		visitor.endvisit(this);
	}

	@Override
	public int getKind() {
		return ASTNodeKinds.SWITCH_STATEMENT;
	}

	public Expression getExpr() {
		return expr;
	}

	public Block getStatement() {
		return statement;
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
