/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.ast.nodes;

import java.util.List;

import org.eclipse.php.internal.core.ast.match.ASTMatcher;
import org.eclipse.php.internal.core.ast.visitor.Visitor;

/**
 * Represents a global statement 
 * <pre>e.g.<pre> global $a
 * global $a, $b
 * global ${foo()->bar()},
 * global $$a 
 */
public class GlobalStatement extends Statement {

	private final Variable[] variables;

	private GlobalStatement(int start, int end, Variable[] variables) {
		super(start, end);

		assert variables != null;
		this.variables = variables;

		for (int i = 0; i < variables.length; i++) {
			variables[i].setParent(this);
		}
	}

	public GlobalStatement(int start, int end, List variables) {
		this(start, end, variables == null ? null : (Variable[]) variables.toArray(new Variable[variables.size()]));
	}

	public void accept(Visitor visitor) {
		final boolean visit = visitor.visit(this);
		if (visit) {
			childrenAccept(visitor);
		}
		visitor.endVisit(this);
	}	

	public void childrenAccept(Visitor visitor) {
		for (int i = 0; i < variables.length; i++) {
			variables[i].accept(visitor);
		}
	}

	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		for (int i = 0; i < variables.length; i++) {
			variables[i].traverseTopDown(visitor);
		}
	}

	public void traverseBottomUp(Visitor visitor) {
		for (int i = 0; i < variables.length; i++) {
			variables[i].traverseBottomUp(visitor);
		}
		accept(visitor);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<GlobalStatement"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(">\n"); //$NON-NLS-1$
		for (int i = 0; i < variables.length; i++) {
			variables[i].toString(buffer, TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
		}
		buffer.append(tab).append("</GlobalStatement>"); //$NON-NLS-1$
	}

	public int getType() {
		return ASTNode.GLOBAL_STATEMENT;
	}

	public Variable[] getVariables() {
		return variables;
	}
	
	/* 
	 * Method declared on ASTNode.
	 */
	public boolean subtreeMatch(ASTMatcher matcher, Object other) {
		// dispatch to correct overloaded match method
		return matcher.match(this, other);
	}
}
