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
 * Represents a class instanciation.
 * This class holds the class name as an expression and
 * array of constructor parameters
 * <pre>e.g.<pre> new MyClass(),
 * new $a('start'),
 * new foo()(1, $a)
 */
public class ClassInstanceCreation extends Expression {

	private final ClassName className;
	private final Expression[] ctorParams;

	public ClassInstanceCreation(int start, int end, ClassName className, Expression[] ctorParams) {
		super(start, end);

		assert className != null && ctorParams != null;

		this.className = className;
		this.ctorParams = ctorParams;

		className.setParent(this);
		for (int i = 0; i < ctorParams.length; i++) {
			ctorParams[i].setParent(this);
		}
	}

	public void accept(Visitor visitor) {
		final boolean visit = visitor.visit(this);
		if (visit) {
			childrenAccept(visitor);
		}
		visitor.endVisit(this);
	}	

	public ClassInstanceCreation(int start, int end, ClassName className, List ctorParams) {
		this(start, end, className, ctorParams == null ? null : (Expression[]) ctorParams.toArray(new Expression[ctorParams.size()]));
	}

	public void childrenAccept(Visitor visitor) {
		className.accept(visitor);
		for (int i = 0; i < ctorParams.length; i++) {
			ctorParams[i].accept(visitor);
		}
	}

	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		className.traverseTopDown(visitor);
		for (int i = 0; i < ctorParams.length; i++) {
			ctorParams[i].traverseTopDown(visitor);
		}
	}

	public void traverseBottomUp(Visitor visitor) {
		className.traverseBottomUp(visitor);
		for (int i = 0; i < ctorParams.length; i++) {
			ctorParams[i].traverseBottomUp(visitor);
		}
		accept(visitor);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<ClassInstanceCreation"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(">\n"); //$NON-NLS-1$
		className.toString(buffer, TAB + tab);
		buffer.append("\n").append(TAB).append(tab).append("<ConstructorParameters>\n"); //$NON-NLS-1$ //$NON-NLS-2$
		for (int i = 0; i < ctorParams.length; i++) {
			ctorParams[i].toString(buffer, TAB + TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
		}
		buffer.append(TAB).append(tab).append("</ConstructorParameters>\n"); //$NON-NLS-1$
		buffer.append(tab).append("</ClassInstanceCreation>"); //$NON-NLS-1$
	}

	public int getType() {
		return ASTNode.CLASS_INSTANCE_CREATION;
	}

	public ClassName getClassName() {
		return className;
	}

	public Expression[] getCtorParams() {
		return ctorParams;
	}
	
	/* 
	 * Method declared on ASTNode.
	 */
	public boolean subtreeMatch(ASTMatcher matcher, Object other) {
		// dispatch to correct overloaded match method
		return matcher.match(this, other);
	}
}
