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
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.utils.CorePrinter;
import org.eclipse.php.internal.core.compiler.ast.visitor.ASTPrintVisitor;

/**
 * Represents if statement
 * <pre>e.g.<pre>
 * if ($a > $b) {
 *   echo "a is bigger than b";
 * } elseif ($a == $b) {
 *   echo "a is equal to b";
 * } else {
 *   echo "a is smaller than b";
 * },
 *
 * if ($a):
 *   echo "a is bigger than b";
 *   echo "a is NOT bigger than b";
 * endif;
 */
public class IfStatement extends Statement {

	private final Expression condition;
	private final Statement trueStatement;
	private final Statement falseStatement;

	public IfStatement(int start, int end, Expression condition, Statement trueStatement, Statement falseStatement) {
		super(start, end);

		assert condition != null && trueStatement != null;
		this.condition = condition;
		this.trueStatement = trueStatement;
		this.falseStatement = falseStatement;
	}

	public void traverse(ASTVisitor visitor) throws Exception {
		final boolean visit = visitor.visit(this);
		if (visit) {
			condition.traverse(visitor);
			trueStatement.traverse(visitor);
			if (falseStatement != null) {
				falseStatement.traverse(visitor);
			}
		}
		visitor.endvisit(this);
	}

	public int getKind() {
		return ASTNodeKinds.IF_STATEMENT;
	}

	public Expression getCondition() {
		return condition;
	}

	public Statement getFalseStatement() {
		return falseStatement;
	}

	public Statement getTrueStatement() {
		return trueStatement;
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
