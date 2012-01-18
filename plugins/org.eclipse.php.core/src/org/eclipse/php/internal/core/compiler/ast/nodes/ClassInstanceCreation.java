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
import org.eclipse.dltk.ast.expressions.CallArgumentsList;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.utils.CorePrinter;
import org.eclipse.php.internal.core.compiler.ast.visitor.ASTPrintVisitor;

/**
 * Represents a class instanciation. This class holds the class name as an
 * expression and array of constructor parameters
 * 
 * <pre>e.g.
 * 
 * <pre>
 * new MyClass(),
 * new $a('start'),
 * new foo()(1, $a)
 */
public class ClassInstanceCreation extends Expression {

	private final Expression className;
	private final CallArgumentsList ctorParams;
	private ChainingInstanceCall chainingInstanceCall;

	public ClassInstanceCreation(int start, int end, Expression className,
			CallArgumentsList ctorParams) {
		super(start, end);

		assert className != null && ctorParams != null;

		this.className = className;
		this.ctorParams = ctorParams;
	}

	public void traverse(ASTVisitor visitor) throws Exception {
		final boolean visit = visitor.visit(this);
		if (visit) {
			className.traverse(visitor);
			ctorParams.traverse(visitor);
			// if (chainingInstanceCall != null) {
			// chainingInstanceCall.traverse(visitor);
			// }
		}
		visitor.endvisit(this);
	}

	public int getKind() {
		return ASTNodeKinds.CLASS_INSTANCE_CREATION;
	}

	public Expression getClassName() {
		return className;
	}

	public CallArgumentsList getCtorParams() {
		return ctorParams;
	}

	public ChainingInstanceCall getChainingInstanceCall() {
		return chainingInstanceCall;
	}

	public void setChainingInstanceCall(
			ChainingInstanceCall chainingInstanceCall) {
		this.chainingInstanceCall = chainingInstanceCall;
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
