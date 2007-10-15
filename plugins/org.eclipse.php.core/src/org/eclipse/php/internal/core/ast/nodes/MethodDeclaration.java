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
 * Represents a function declaration in a class
 * Holds the function modifier
 * @see {@link FunctionDeclaration}
 */
public class MethodDeclaration extends BodyDeclaration {

	private final FunctionDeclaration function;

	public MethodDeclaration(int start, int end, int modifier, FunctionDeclaration function, boolean shouldComplete) {
		super(start, end, modifier, shouldComplete);

		assert function != null;
		this.function = function;

		function.setParent(this);
	}

	public MethodDeclaration(int start, int end, int modifier, FunctionDeclaration function) {
		this(start, end, modifier, function, false);
	}

	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	public void childrenAccept(Visitor visitor) {
		function.accept(visitor);
	}

	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		function.traverseTopDown(visitor);
	}

	public void traverseBottomUp(Visitor visitor) {
		function.traverseBottomUp(visitor);
		accept(visitor);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<MethodDeclaration"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(" modifier='").append(getModifierString()).append("'>\n"); //$NON-NLS-1$ //$NON-NLS-2$
		function.toString(buffer, TAB + tab);
		buffer.append("\n"); //$NON-NLS-1$
		buffer.append(tab).append("</MethodDeclaration>"); //$NON-NLS-1$
	}

	public int getType() {
		return ASTNode.METHOD_DECLARATION;
	}

	public FunctionDeclaration getFunction() {
		return function;
	}
}
