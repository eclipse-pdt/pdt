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
package org.eclipse.php.core.ast.nodes;

import org.eclipse.php.core.ast.match.ASTMatcher;
import org.eclipse.php.core.ast.visitor.Visitor;

public class TraitAliasStatement extends TraitStatement {
	private TraitAlias alias;

	public TraitAliasStatement(int start, int end, AST ast, TraitAlias alias) {
		super(start, end, ast, alias);
		this.alias = alias;
	}

	public TraitAliasStatement(AST ast) {
		super(ast);
	}

	public TraitAlias getAlias() {
		return alias;
	}

	public void setAlias(TraitAlias alias) {
		super.setExp(alias);
		this.alias = alias;
	}

	@Override
	public boolean subtreeMatch(ASTMatcher matcher, Object other) {
		if (!(other instanceof TraitAliasStatement)) {
			return false;
		}
		return super.subtreeMatch(matcher, other);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<TraitAliasStatement"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(">\n"); //$NON-NLS-1$
		alias.toString(buffer, TAB + tab);
		buffer.append("\n"); //$NON-NLS-1$
		buffer.append(tab).append("</TraitAliasStatement>"); //$NON-NLS-1$
	}

	@Override
	ASTNode clone0(AST target) {
		TraitAlias alias = ASTNode.copySubtree(target, getAlias());
		final TraitAliasStatement result = new TraitAliasStatement(this.getStart(), this.getEnd(), target, alias);
		return result;
	}

	public void accept0(Visitor visitor) {
		final boolean visit = visitor.visit(this);
		if (visit) {
			childrenAccept(visitor);
		}
		visitor.endVisit(this);
	}
}
