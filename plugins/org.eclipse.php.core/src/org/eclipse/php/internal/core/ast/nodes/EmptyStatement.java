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
 * This class represents an empty statement.
 * <pre>e.g.<pre> ;
 * while(true); - the while statement contains empty statement
 */
public class EmptyStatement extends Statement {

	public EmptyStatement(int start, int end) {
		super(start, end);
	}

	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	public void childrenAccept(Visitor visitor) {
	}

	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
	}

	public void traverseBottomUp(Visitor visitor) {
		accept(visitor);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<EmptyStatement"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append("/>"); //$NON-NLS-1$
	}

	public int getType() {
		return ASTNode.EMPTY_STATEMENT;
	}
}
