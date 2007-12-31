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

import org.eclipse.php.internal.core.ast.visitor.Visitor;

/**
 * Holds a function name. 
 * note that the function name can be expression, 
 * <pre>e.g.<pre> foo() - the name is foo 
 * $a() - the variable $a holds the function name
 */
public class FunctionName extends ASTNode {

	private final Expression functionName;

	public FunctionName(int start, int end, Expression functionName) {
		super(start, end);

		assert functionName != null;
		this.functionName = functionName;

		functionName.setParent(this);
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
	}

	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		functionName.traverseTopDown(visitor);
	}

	public void traverseBottomUp(Visitor visitor) {
		functionName.traverseBottomUp(visitor);
		accept(visitor);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<FunctionName"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(">\n"); //$NON-NLS-1$
		functionName.toString(buffer, TAB + tab);
		buffer.append("\n"); //$NON-NLS-1$
		buffer.append(tab).append("</FunctionName>"); //$NON-NLS-1$
	}

	public int getType() {
		return ASTNode.FUNCTION_NAME;
	}

	public Expression getFunctionName() {
		return functionName;
	}
}
