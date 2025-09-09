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
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.compiler.problem.IProblemIdentifier;
import org.eclipse.dltk.utils.CorePrinter;
import org.eclipse.php.core.ast.visitor.Visitor;
import org.eclipse.php.internal.core.compiler.ast.parser.PHPProblemIdentifier;
import org.eclipse.php.internal.core.compiler.ast.visitor.ASTPrintVisitor;

/**
 * Represents a parsing error
 * 
 * <pre>
 * e.g.
 * 
 * echo ; for () {}
 * </pre>
 */
public class ASTError extends Statement {

	private IProblemIdentifier problemIdentifier;

	public ASTError(int start, int end) {
		super(start, end);
		this.problemIdentifier = PHPProblemIdentifier.SYNTAX;
	}

	public ASTError(int start, int end, IProblemIdentifier problemIdentifier) {
		super(start, end);
		this.problemIdentifier = problemIdentifier;
	}

	public void childrenAccept(Visitor visitor) {
	}

	public void traverseBottomUp(Visitor visitor) {
	}

	public void traverseTopDown(Visitor visitor) {
	}

	@Override
	public void traverse(ASTVisitor visitor) throws Exception {
		if (visitor.visit(this)) {
			visitor.endvisit(this);
		}
	}

	@Override
	public int getKind() {
		return ASTNodeKinds.AST_ERROR;
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

	public IProblemIdentifier getProblem() {
		return this.problemIdentifier;
	}
}
