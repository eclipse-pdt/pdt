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
 *  Represents an reference to a variable or class instanciation.
 *  <pre>e.g.<pre> &$a,
 *  &new MyClass()
 *  &foo()
 */
public class Reference extends Expression {

	/**
	 *  the expressions can be either variable or class instanciation 
	 *  note that other expressions can not be assigned to this field
	 */
	private final Expression expression;

	private Reference(int start, int end, Expression expression) {
		super(start, end);

		assert expression != null;
		this.expression = expression;

		expression.setParent(this);
	}

	public Reference(int start, int end, VariableBase variable) {
		this(start, end, (Expression) variable);
	}

	public Reference(int start, int end, ClassInstanceCreation classInstanciation) {
		this(start, end, (Expression) classInstanciation);
	}

	public void accept(Visitor visitor) {
		final boolean visit = visitor.visit(this);
		if (visit) {
			childrenAccept(visitor);
		}
		visitor.endVisit(this);
	}	

	public void childrenAccept(Visitor visitor) {
		expression.accept(visitor);
	}

	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		expression.traverseTopDown(visitor);
	}

	public void traverseBottomUp(Visitor visitor) {
		expression.traverseBottomUp(visitor);
		accept(visitor);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<Reference"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(">\n"); //$NON-NLS-1$
		expression.toString(buffer, TAB + tab);
		buffer.append("\n").append(tab).append("</Reference>"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public int getType() {
		return ASTNode.REFERENCE;
	}

	public Expression getExpression() {
		return expression;
	}
}
