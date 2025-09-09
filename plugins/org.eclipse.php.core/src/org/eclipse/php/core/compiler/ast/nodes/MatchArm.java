/*******************************************************************************
 * Copyright (c) 2023 Dawid Pakuła and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Dawid Pakuła - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.core.compiler.ast.nodes;

import java.util.Collection;
import java.util.List;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.utils.CorePrinter;
import org.eclipse.php.internal.core.compiler.ast.visitor.ASTPrintVisitor;

/**
 * Match arm: conditions => value default => value
 */
public class MatchArm extends Statement {

	private final List<? extends Expression> conditions;
	private final Expression value;

	public MatchArm(int start, int end, List<? extends Expression> conditions, Expression value) {
		super(start, end);

		assert value != null;
		this.conditions = conditions;
		this.value = value;
	}

	public MatchArm(int start, int end, Expression value) {
		this(start, end, null, value);
	}

	@Override
	public void traverse(ASTVisitor visitor) throws Exception {
		if (visitor.visit(this)) {
			if (conditions != null) {
				for (Expression action : conditions) {
					action.traverse(visitor);
				}
			}

			value.traverse(visitor);
			visitor.endvisit(this);
		}
	}

	@Override
	public int getKind() {
		return ASTNodeKinds.MATCH_EXPRESSION;
	}

	public Collection<? extends Expression> getConditions() {
		return conditions;
	}

	public boolean isDefault() {
		return conditions == null;
	}

	public Expression getValue() {
		return value;
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
