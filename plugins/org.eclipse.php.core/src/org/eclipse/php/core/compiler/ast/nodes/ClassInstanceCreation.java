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
package org.eclipse.php.core.compiler.ast.nodes;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.expressions.CallArgumentsList;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.utils.CorePrinter;
import org.eclipse.php.internal.core.compiler.ast.visitor.ASTPrintVisitor;

/**
 * Represents a class instanciation. This class holds the class name as an
 * expression and array of constructor parameters
 * 
 * e.g.
 * 
 * <pre>
 * new MyClass(), 
 * new $a('start'), 
 * new foo()(1, $a)
 * </pre>
 */
public class ClassInstanceCreation extends Expression {

	private final Expression className;
	private final CallArgumentsList ctorParams;
	private final AnonymousClassDeclaration anonymousClassDeclaration;

	public ClassInstanceCreation(int start, int end, Expression className, CallArgumentsList ctorParams) {
		this(start, end, className, ctorParams, null);
	}

	public ClassInstanceCreation(int start, int end, Expression className, CallArgumentsList ctorParams,
			AnonymousClassDeclaration anonymousClass) {
		super(start, end);

		assert className != null && ctorParams != null;

		this.className = className;
		this.ctorParams = ctorParams;
		this.anonymousClassDeclaration = anonymousClass;
	}

	public void traverse(ASTVisitor visitor) throws Exception {
		final boolean visit = visitor.visit(this);
		if (visit) {
			if (className != null) {
				className.traverse(visitor);
			}
			ctorParams.traverse(visitor);
			if (anonymousClassDeclaration != null) {
				anonymousClassDeclaration.traverse(visitor);
			}
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

	public AnonymousClassDeclaration getAnonymousClassDeclaration() {
		return anonymousClassDeclaration;
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
