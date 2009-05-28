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

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.DLTKToken;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.utils.CorePrinter;
import org.eclipse.php.internal.core.compiler.ast.visitor.ASTPrintVisitor;

/**
 * Represents an indirect reference to a variable.
 * <pre>e.g.<pre> $$a
 * $$foo()
 * ${foo()}
 */
public class ReflectionVariableReference extends Expression {

	private Expression expression;

	public ReflectionVariableReference(int start, int end, Expression name) {
		super(start, end);
		this.expression = name;
	}

	public ReflectionVariableReference(DLTKToken token) {
		super(token);
	}

	public int getKind() {
		return ASTNodeKinds.REFLECTION_VARIABLE;
	}

	public void traverse(ASTVisitor visitor) throws Exception {
		boolean visit = visitor.visit(this);
		if (visit) {
			expression.traverse(visitor);
		}
		visitor.endvisit(this);
	}

	public Expression getExpression(){
		return this.expression;
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
