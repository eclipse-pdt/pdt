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
 * Represents function invocation.
 * Holds the function name and the invocation parameters.
 * <pre>e.g.<pre> foo(),
 * $a(),
 * foo($a, 'a', 12)
 */
public class FunctionInvocation extends VariableBase {

	private final FunctionName functionName;
	private final Expression[] parameters;

	private FunctionInvocation(int start, int end, FunctionName functionName, Expression[] parameters) {
		super(start, end);

		assert functionName != null && parameters != null;

		this.functionName = functionName;
		this.parameters = parameters;

		functionName.setParent(this);
		for (int i = 0; i < parameters.length; i++) {
			parameters[i].setParent(this);
		}
	}

	public FunctionInvocation(int start, int end, FunctionName functionName, List parameters) {
		this(start, end, functionName, parameters == null ? null : (Expression[]) parameters.toArray(new Expression[parameters.size()]));
	}

	public void accept(Visitor visitor) {
		final boolean visit = visitor.visit(this);
		if (visit) {
			childrenAccept(visitor);
		}
		visitor.endVisit(this);
	}	

	public void childrenAccept(Visitor visitor) {
		functionName.accept(visitor);
		for (int i = 0; i < parameters.length; i++) {
			parameters[i].accept(visitor);
		}
	}

	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		functionName.traverseTopDown(visitor);
		for (int i = 0; i < parameters.length; i++) {
			parameters[i].traverseTopDown(visitor);
		}
	}

	public void traverseBottomUp(Visitor visitor) {
		functionName.traverseBottomUp(visitor);
		for (int i = 0; i < parameters.length; i++) {
			parameters[i].traverseBottomUp(visitor);
		}
		accept(visitor);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<FunctionInvocation"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(">\n"); //$NON-NLS-1$
		functionName.toString(buffer, TAB + tab);
		buffer.append("\n"); //$NON-NLS-1$
		buffer.append(TAB).append(tab).append("<Parameters>\n"); //$NON-NLS-1$
		for (int i = 0; parameters != null && i < parameters.length; i++) {
			parameters[i].toString(buffer, TAB + TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
		}
		buffer.append(TAB).append(tab).append("</Parameters>\n"); //$NON-NLS-1$
		buffer.append(tab).append("</FunctionInvocation>"); //$NON-NLS-1$
	}

	public int getType() {
		return ASTNode.FUNCTION_INVOCATION;
	}

	public FunctionName getFunctionName() {
		return functionName;
	}

	public Expression[] getParameters() {
		return parameters;
	}
	
	/* 
	 * Method declared on ASTNode.
	 */
	public boolean subtreeMatch(ASTMatcher matcher, Object other) {
		// dispatch to correct overloaded match method
		return matcher.match(this, other);
	}
}
