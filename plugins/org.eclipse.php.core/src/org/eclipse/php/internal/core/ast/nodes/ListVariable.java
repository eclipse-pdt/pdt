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
 * Represents a list expression.
 * The list contains variables and/or other lists.
 *  
 * <pre>e.g.<pre> list($a,$b) = array (1,2),
 * list($a, list($b, $c))
 */
public class ListVariable extends VariableBase {

	private final VariableBase[] variables;

	private ListVariable(int start, int end, VariableBase[] variables) {
		super(start, end);

		assert variables != null;
		this.variables = variables;

		for (int i = 0; i < variables.length; i++) {
			variables[i].setParent(this);
		}
	}

	public ListVariable(int start, int end, List variables) {
		this(start, end, variables == null ? null : (VariableBase[]) variables.toArray(new VariableBase[variables.size()]));
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
		buffer.append(tab).append("<List"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(">\n"); //$NON-NLS-1$
		for (int i = 0; i < variables.length; i++) {
			variables[i].toString(buffer, TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
		}
		buffer.append(tab).append("</List>"); //$NON-NLS-1$
	}

	public int getType() {
		return ASTNode.LIST_VARIABLE;
	}

	public VariableBase[] getVariables() {
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
