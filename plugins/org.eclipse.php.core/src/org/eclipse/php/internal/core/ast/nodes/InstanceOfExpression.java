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
 * Represent instanceof expression
 * <pre>e.g.<pre> $a instanceof MyClass,
 * foo() instanceof $myClass,
 * $a instanceof $b->$myClass
 */
public class InstanceOfExpression extends Expression {

	private final Expression expr;
	private final ClassName className;

	public InstanceOfExpression(int start, int end, Expression expr, ClassName type) {
		super(start, end);

		assert expr != null && type != null;
		this.expr = expr;
		this.className = type;

		expr.setParent(this);
		type.setParent(this);
	}

	public void accept(Visitor visitor) {
		final boolean visit = visitor.visit(this);
		if (visit) {
			childrenAccept(visitor);
		}
		visitor.endVisit(this);
	}	

	public void childrenAccept(Visitor visitor) {
		expr.accept(visitor);
		className.accept(visitor);
	}

	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		expr.traverseTopDown(visitor);
		className.traverseTopDown(visitor);
	}

	public void traverseBottomUp(Visitor visitor) {
		expr.traverseBottomUp(visitor);
		className.traverseBottomUp(visitor);
		accept(visitor);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<InstanceofExpression"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(">\n"); //$NON-NLS-1$
		expr.toString(buffer, TAB + tab);
		buffer.append("\n"); //$NON-NLS-1$
		className.toString(buffer, TAB + tab);
		buffer.append("\n").append(tab).append("</InstanceofExpression>"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public int getType() {
		return ASTNode.INSTANCE_OF_EXPRESSION;
	}

	public ClassName getClassName() {
		return className;
	}

	public Expression getExpr() {
		return expr;
	}
	
	/* 
	 * Method declared on ASTNode.
	 */
	public boolean subtreeMatch(ASTMatcher matcher, Object other) {
		// dispatch to correct overloaded match method
		return matcher.match(this, other);
	}
}
