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

public class ReflectionCallExpression extends Expression implements
		Dereferencable {

	private Expression receiver;
	private Expression name;
	private CallArgumentsList args;
	private PHPArrayDereferenceList arrayDereferenceList;

	public ReflectionCallExpression(int start, int end, Expression receiver,
			Expression name, CallArgumentsList args) {
		super(start, end);

		assert name != null;

		if (args == null) {
			args = new CallArgumentsList();
		}

		this.receiver = receiver;
		this.name = name;
		this.args = args;
	}

	public int getKind() {
		return ASTNodeKinds.REFLECTION_CALL_EXPRESSION;
	}

	public void traverse(ASTVisitor visitor) throws Exception {
		boolean visit = visitor.visit(this);
		if (visit) {
			if (receiver != null) {
				receiver.traverse(visitor);
			}
			name.traverse(visitor);
			args.traverse(visitor);
			if (getArrayDereferenceList() != null) {
				getArrayDereferenceList().traverse(visitor);
			}
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

	public PHPArrayDereferenceList getArrayDereferenceList() {
		return arrayDereferenceList;
	}

	public void setArrayDereferenceList(
			PHPArrayDereferenceList arrayDereferenceList) {
		this.arrayDereferenceList = arrayDereferenceList;
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
