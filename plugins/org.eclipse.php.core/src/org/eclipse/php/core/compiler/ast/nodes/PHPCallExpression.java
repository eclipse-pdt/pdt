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

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.expressions.CallArgumentsList;
import org.eclipse.dltk.ast.expressions.CallExpression;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.utils.CorePrinter;
import org.eclipse.php.internal.core.compiler.ast.visitor.ASTPrintVisitor;

public class PHPCallExpression extends CallExpression implements Dereferencable {

	private boolean nullSafe = false;
	
	public PHPCallExpression(int start, int end, ASTNode receiver, String name, CallArgumentsList args) {
		super(start, end, receiver, name, args);
	}

	public PHPCallExpression(int start, int end, ASTNode receiver, SimpleReference name, CallArgumentsList args) {
		super(start, end, receiver, name, args);
	}

	public PHPCallExpression(ASTNode receiver, String name, CallArgumentsList args) {
		super(receiver, name, args);
	}
	
	public PHPCallExpression(int start, int end, ASTNode receiver, boolean nullSafe, String name, CallArgumentsList args) {
		super(start, end, receiver, name, args);
		this.nullSafe = nullSafe;
	}

	public PHPCallExpression(int start, int end, ASTNode receiver, boolean nullSafe, SimpleReference name, CallArgumentsList args) {
		super(start, end, receiver, name, args);
		this.nullSafe = nullSafe;
	}

	public PHPCallExpression(ASTNode receiver, String name, boolean nullSafe, CallArgumentsList args) {
		super(receiver, name, args);
		this.nullSafe = nullSafe;
	}

	public void setReceiver(ASTNode receiver) {
		this.receiver = receiver;
	}

	@Override
	public void traverse(ASTVisitor pVisitor) throws Exception {
		if (pVisitor.visit(this)) {
			if (receiver != null) {
				receiver.traverse(pVisitor);
			}
			getCallName().traverse(pVisitor);
			if (getArgs() != null) {
				getArgs().traverse(pVisitor);
			}
			pVisitor.endvisit(this);
		}
	}

	@Override
	public int getKind() {
		return ASTNodeKinds.METHOD_INVOCATION;
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
