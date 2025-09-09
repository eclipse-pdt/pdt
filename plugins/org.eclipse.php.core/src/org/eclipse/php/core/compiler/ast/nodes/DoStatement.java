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
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.statements.Statement;

public class DoStatement extends WhileStatement {

	public DoStatement(int start, int end, Expression condition, Statement action) {
		super(start, end, condition, action);
	}

	@Override
	public void traverse(ASTVisitor visitor) throws Exception {
		if (visitor.visit(this)) {
			getAction().traverse(visitor);
			getCondition().traverse(visitor);
			visitor.endvisit(this);
		}
	}

	@Override
	public int getKind() {
		return ASTNodeKinds.DO_STATEMENT;
	}
}
