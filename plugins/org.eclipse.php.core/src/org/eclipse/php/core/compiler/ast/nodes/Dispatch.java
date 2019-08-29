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

import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.utils.CorePrinter;
import org.eclipse.php.internal.core.compiler.ast.visitor.ASTPrintVisitor;

/**
 * Represents a base class for method invocation and field access
 * 
 * <pre>
 * e.g.
 * 
 * $a->$b, foo()->bar(), $myClass->foo()->bar(), A::$a->foo()
 * </pre>
 */
public abstract class Dispatch extends Expression {

	private Expression dispatcher;

	public Dispatch(int start, int end, Expression dispatcher) {
		super(start, end);

		assert dispatcher != null;

		this.dispatcher = dispatcher;
	}

	public Expression getDispatcher() {
		return dispatcher;
	}

	public void setDispatcher(Expression dispatcher) {
		this.dispatcher = dispatcher;
	}

	/**
	 * We don't print anything - we use {@link ASTPrintVisitor} instead
	 */
	@Override
	public final void printNode(CorePrinter output) {
	}

	@Override
	public String toString() {
		return ASTPrintVisitor.toXMLString(this);
	}
}
