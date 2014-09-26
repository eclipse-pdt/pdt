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

import java.util.Collection;
import java.util.List;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.utils.CorePrinter;
import org.eclipse.php.internal.core.compiler.ast.visitor.ASTPrintVisitor;

/**
 * Represent a 'use' statement.
 * <pre>e.g.<pre>
 * use A;
 * use A as B;
 * use \A\B as C;
 */
public class UseStatement extends Statement {

	// none
	public static final int T_NONE = 0;
	// 'function' keyword
	public static final int T_FUNCTION = 1;
	// 'const' keyword
	public static final int T_CONST = 2;

	private List<UsePart> parts;
	private int statementType;

	public UseStatement(int start, int end, List<UsePart> parts,
			int statementType) {
		super(start, end);

		assert parts != null;
		this.parts = parts;
		this.statementType = statementType;
	}

	public UseStatement(int start, int end, List<UsePart> parts) {
		this(start, end, parts, T_NONE);
	}

	public void traverse(ASTVisitor visitor) throws Exception {
		if (visitor.visit(this)) {
			for (UsePart part : parts) {
				part.traverse(visitor);
			}
			visitor.endvisit(this);
		}
	}

	public int getKind() {
		return ASTNodeKinds.USE_STATEMENT;
	}

	public Collection<UsePart> getParts() {
		return parts;
	}

	public int getStatementType() {
		return statementType;
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
