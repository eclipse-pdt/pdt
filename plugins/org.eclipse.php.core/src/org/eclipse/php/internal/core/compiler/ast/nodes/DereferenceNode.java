/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.core.compiler.ast.nodes;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.php.internal.core.compiler.ast.visitor.ASTPrintVisitor;

/**
 * Represent an array dereference "[]" part statement.
 * 
 * <pre>e.g.
 * 
 * <pre>
 * [0]
 * [$index]
 */
public class DereferenceNode extends ASTNode {

	// ASTNode parent;
	private Expression exp;

	public DereferenceNode(Expression exp, int start, int end) {
		super(start, end);
		// this.parent = parent;
		this.exp = exp;
	}

	public String toString() {
		return ASTPrintVisitor.toXMLString(this);
	}

	public void traverse(ASTVisitor visitor) throws Exception {
		if (visitor.visit(this)) {
			getExpression().traverse(visitor);
			visitor.endvisit(this);
		}
	}

	public void setExpression(Expression exp) {
		this.exp = exp;
	}

	public Expression getExpression() {
		return exp;
	}
}