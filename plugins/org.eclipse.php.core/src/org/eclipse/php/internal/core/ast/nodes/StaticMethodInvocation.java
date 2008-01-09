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
 * Represents static function invocation.
 * Holds the function invocation and the class name.
 * <pre>e.g.<pre> 
 * MyClass::foo($a)
 */
public class StaticMethodInvocation extends StaticDispatch {

	private final FunctionInvocation method;

	public StaticMethodInvocation(int start, int end, Identifier className, FunctionInvocation method) {
		super(start, end, className);

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
		getClassName().accept(visitor);
		method.accept(visitor);
	}

	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		getClassName().traverseTopDown(visitor);
		method.traverseTopDown(visitor);
	}

	public void traverseBottomUp(Visitor visitor) {
		getClassName().traverseBottomUp(visitor);
		method.traverseBottomUp(visitor);
		accept(visitor);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<StaticMethodInvocation"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(">\n"); //$NON-NLS-1$
		buffer.append(TAB).append(tab).append("<ClassName>\n"); //$NON-NLS-1$
		getClassName().toString(buffer, TAB + TAB + tab);
		buffer.append("\n").append(TAB).append(tab).append("</ClassName>\n"); //$NON-NLS-1$ //$NON-NLS-2$
		method.toString(buffer, TAB + tab);
		buffer.append("\n"); //$NON-NLS-1$
		buffer.append(tab).append("</StaticMethodInvocation>"); //$NON-NLS-1$
	}

	public int getType() {
		return ASTNode.STATIC_METHOD_INVOCATION;
	}

	public FunctionInvocation getMethod() {
		return method;
	}

	public ASTNode getMember() {
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
