/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.core.compiler.ast.nodes;

import org.eclipse.dltk.ast.ASTVisitor;

public class TraitPrecedenceStatement extends TraitStatement {
	private TraitPrecedence precedence;

	public TraitPrecedenceStatement(int start, int end, TraitPrecedence precedence) {
		super(start, end);
		this.precedence = precedence;
	}

	@Override
	public void traverse(ASTVisitor visitor) throws Exception {
		if (visitor.visit(this)) {
			if (precedence != null) {
				precedence.traverse(visitor);
			}
			visitor.endvisit(this);
		}
	}

	public TraitPrecedence getPrecedence() {
		return precedence;
	}

	@Override
	public int getKind() {
		// TODO Auto-generated method stub
		return 0;
	}

}
