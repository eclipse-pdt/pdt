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
 * Represents a parsing error
 * <pre>e.g.<pre> echo ;
 * for () {}
 */
public class ASTError extends Statement {

	public ASTError(int start, int end) {
		super(start, end);
	}

	public void childrenAccept(Visitor visitor) {
	}

	public void traverseBottomUp(Visitor visitor) {
	}

	public void traverseTopDown(Visitor visitor) {
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<AstError"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append("/>"); //$NON-NLS-1$
	}

	public void accept(Visitor visitor) {
		final boolean visit = visitor.visit(this);
		if (visit) {
			childrenAccept(visitor);
		}
		visitor.endVisit(this);
	}	
	
	public int getType() {
		return ASTNode.AST_ERROR;
	}
}
