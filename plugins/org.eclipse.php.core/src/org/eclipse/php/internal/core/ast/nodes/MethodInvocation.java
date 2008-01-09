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

import org.eclipse.php.internal.core.ast.match.ASTMatcher;
import org.eclipse.php.internal.core.ast.visitor.Visitor;

/**
 * Represents a dispaching expression
 * <pre>e.g.<pre> foo()->bar(),
 * $myClass->foo()->bar(),
 * A::$a->foo()
 */
public class MethodInvocation extends Dispatch {

	private final FunctionInvocation method;

	public MethodInvocation(int start, int end, VariableBase dispatcher, FunctionInvocation method) {
		super(start, end, dispatcher);

		assert method != null;
		this.method = method;

		method.setParent(this);
	}

	public void accept(Visitor visitor) {
		final boolean visit = visitor.visit(this);
		if (visit) {
			childrenAccept(visitor);
		}
		visitor.endVisit(this);
	}	

	public void childrenAccept(Visitor visitor) {
		getDispatcher().accept(visitor);
		method.accept(visitor);
	}

	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		getDispatcher().accept(visitor);
		method.traverseTopDown(visitor);
	}

	public void traverseBottomUp(Visitor visitor) {
		getDispatcher().traverseBottomUp(visitor);
		method.traverseBottomUp(visitor);
		accept(visitor);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<MethodInvocation"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(">\n"); //$NON-NLS-1$
		buffer.append(TAB).append(tab).append("<Dispatcher>\n"); //$NON-NLS-1$
		getDispatcher().toString(buffer, TAB + TAB + tab);
		buffer.append("\n").append(TAB).append(tab).append("</Dispatcher>\n"); //$NON-NLS-1$ //$NON-NLS-2$
		buffer.append(TAB).append(tab).append("<Property>\n"); //$NON-NLS-1$
		method.toString(buffer, TAB + TAB + tab);
		buffer.append("\n").append(TAB).append(tab).append("</Property>\n"); //$NON-NLS-1$ //$NON-NLS-2$
		buffer.append(tab).append("</MethodInvocation>"); //$NON-NLS-1$
	}

	public int getType() {
		return ASTNode.METHOD_INVOCATION;
	}

	public FunctionInvocation getMethod() {
		return method;
	}

	public VariableBase getMember() {
		return getMethod();
	}
	
	/* 
	 * Method declared on ASTNode.
	 */
	public boolean subtreeMatch(ASTMatcher matcher, Object other) {
		// dispatch to correct overloaded match method
		return matcher.match(this, other);
	}
}
