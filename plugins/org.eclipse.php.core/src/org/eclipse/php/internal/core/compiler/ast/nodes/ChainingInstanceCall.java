/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.php.internal.core.compiler.ast.nodes;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.utils.CorePrinter;
import org.eclipse.php.internal.core.compiler.ast.visitor.ASTPrintVisitor;

public class ChainingInstanceCall extends Expression {

	private ChainingMethodPropertyList chaining_method_or_property;

	public ChainingInstanceCall(int start, int end,
			ChainingMethodPropertyList chaining_method_or_property) {
		super(start, end);

		this.chaining_method_or_property = chaining_method_or_property;
	}

	public ChainingInstanceCall(
			ChainingMethodPropertyList chaining_method_or_property) {
		this.chaining_method_or_property = chaining_method_or_property;
	}

	public void traverse(ASTVisitor visitor) throws Exception {
		if (visitor.visit(this)) {
			if (chaining_method_or_property != null) {
				for (Object method_or_property : chaining_method_or_property
						.getChilds()) {
					((Expression) method_or_property).traverse(visitor);
				}
			}
			visitor.endvisit(this);
		}
	}

	public int getKind() {
		return ASTNodeKinds.USE_STATEMENT;
	}

	public ChainingMethodPropertyList getChainingMethodPropertyList() {
		return chaining_method_or_property;
	}

	/**
	 * We don't print anything - we use {@link ASTPrintVisitor} instead
	 */
	public final void printNode(CorePrinter output) {
	}

	public String toString() {
		return ASTPrintVisitor.toXMLString(this);
	}
}
