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
import org.eclipse.php.internal.core.compiler.ast.visitor.ASTPrintVisitor;

/**
 * Represents an indirect reference to a constant
 * 
 * <pre>
 * e.g.
 * 
 * CLASS::{$a} CLASS::$foo() CLASS::{foo()}
 * </pre>
 */
public class ReflectionConstantAccess extends StaticDispatch {

	private Expression constant;

	public ReflectionConstantAccess(int start, int end, Expression dispatcher, Expression constant) {
		super(start, end, dispatcher);
		this.constant = constant;
	}

	@Override
	public int getKind() {
		return ASTNodeKinds.REFLECTION_CONSTANT_ACCESS;
	}

	@Override
	public void traverse(ASTVisitor visitor) throws Exception {
		final boolean visit = visitor.visit(this);
		if (visit) {
			getDispatcher().traverse(visitor);
			constant.traverse(visitor);
		}
		visitor.endvisit(this);
	}

	public Expression getConstant() {
		return this.constant;
	}

	@Override
	public String toString() {
		return ASTPrintVisitor.toXMLString(this);
	}
}
