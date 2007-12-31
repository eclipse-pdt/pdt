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
 * Represents include, include_once, require and require_once expressions
 * <pre>e.g.<pre> include('myFile.php'),
 * include_once($myFile),
 * require($myClass->getFileName()),
 * require_once(A::FILE_NAME)
 */
public class Include extends Expression {

	public static final int IT_REQUIRE = 0;
	public static final int IT_REQUIRE_ONCE = 1;
	public static final int IT_INCLUDE = 2;
	public static final int IT_INCLUDE_ONCE = 3;

	private final Expression expr;
	private final int includeType;

	public Include(int start, int end, Expression expr, int type) {
		super(start, end);

		assert expr != null;
		this.expr = expr;
		this.includeType = type;

		expr.setParent(this);
	}

	public static String getType(int type) {
		switch (type) {
			case IT_REQUIRE:
				return "require"; //$NON-NLS-1$
			case IT_REQUIRE_ONCE:
				return "require_once"; //$NON-NLS-1$
			case IT_INCLUDE:
				return "include"; //$NON-NLS-1$
			case IT_INCLUDE_ONCE:
				return "include_once"; //$NON-NLS-1$
			default:
				throw new IllegalArgumentException();
		}
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
	}

	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		expr.traverseTopDown(visitor);
	}

	public void traverseBottomUp(Visitor visitor) {
		expr.traverseBottomUp(visitor);
		accept(visitor);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<Include"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(" kind='").append(getType(includeType)).append("'>\n"); //$NON-NLS-1$ //$NON-NLS-2$
		expr.toString(buffer, TAB + tab);
		buffer.append("\n").append(tab).append("</Include>"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public int getType() {
		return ASTNode.INCLUDE;
	}

	public Expression getExpr() {
		return expr;
	}

	public int getIncludeType() {
		return includeType;
	}

}
