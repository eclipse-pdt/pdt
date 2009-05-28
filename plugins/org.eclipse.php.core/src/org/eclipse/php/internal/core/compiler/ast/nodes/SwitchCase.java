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

import java.util.Collection;
import java.util.List;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.utils.CorePrinter;
import org.eclipse.php.internal.core.compiler.ast.visitor.ASTPrintVisitor;

/**
 * Represents a case statement.
 * A case statement is part of switch statement
 * <pre>e.g.<pre>
 * case expr:
 *   statement1;
 *   break;,
 *
 * default:
 *   statement2;
 */
public class SwitchCase extends Statement {

	private final Expression value;
	private final List<? extends Expression> actions;
	private final boolean isDefault;

	public SwitchCase(int start, int end, Expression value, List<? extends Expression> actions, boolean isDefault) {
		super(start, end);

		assert actions != null;
		this.value = value;
		this.actions = actions;
		this.isDefault = isDefault;
	}

	public void traverse(ASTVisitor visitor) throws Exception {
		final boolean visit = visitor.visit(this);
		if (visit) {
			if (value != null) {
				value.traverse(visitor);
			}
			for (Statement action : actions) {
				action.traverse(visitor);
			}
		}
		visitor.endvisit(this);
	}

	public int getKind() {
		return ASTNodeKinds.SWITCH_CASE;
	}

	public Collection<? extends Expression> getActions() {
		return actions;
	}

	public boolean isDefault() {
		return isDefault;
	}

	public Expression getValue() {
		return value;
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
