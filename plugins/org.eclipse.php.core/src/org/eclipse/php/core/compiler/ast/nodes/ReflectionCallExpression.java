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
import org.eclipse.dltk.ast.expressions.CallArgumentsList;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.utils.CorePrinter;
import org.eclipse.php.internal.core.compiler.ast.visitor.ASTPrintVisitor;

public class ReflectionCallExpression extends Expression implements Dereferencable {

	private Expression receiver;
	private Expression name;
	private CallArgumentsList args;
	private boolean nullSafe;

	public ReflectionCallExpression(int start, int end, Expression receiver, Expression name, CallArgumentsList args) {
		this(start, end, receiver, false, name, args);

	}

	public ReflectionCallExpression(int start, int end, Expression receiver, boolean nullSafe, Expression name,
			CallArgumentsList args) {
		super(start, end);

		assert name != null;

		if (args == null) {
			args = new CallArgumentsList();
		}

		this.receiver = receiver;
		this.name = name;
		this.args = args;
		this.nullSafe = nullSafe;
	}

	@Override
	public int getKind() {
		return ASTNodeKinds.REFLECTION_CALL_EXPRESSION;
	}

	@Override
	public void traverse(ASTVisitor visitor) throws Exception {
		boolean visit = visitor.visit(this);
		if (visit) {
			if (receiver != null) {
				receiver.traverse(visitor);
			}
			name.traverse(visitor);
			args.traverse(visitor);
		}
		visitor.endvisit(this);
	}

	public Expression getName() {
		return name;
	}

	public CallArgumentsList getArguments() {
		return args;
	}

	public Expression getReceiver() {
		return receiver;
	}

	public void setReceiver(Expression receiver) {
		assert receiver != null;
		this.receiver = receiver;
		setStart(receiver.sourceStart());
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

	public boolean isNullSafe() {
		return nullSafe;
	}
}
