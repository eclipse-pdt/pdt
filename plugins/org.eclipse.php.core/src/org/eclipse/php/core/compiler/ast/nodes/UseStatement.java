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

import java.util.Collection;
import java.util.List;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.utils.CorePrinter;
import org.eclipse.php.internal.core.compiler.ast.visitor.ASTPrintVisitor;

/**
 * Represent a 'use' statement.
 * 
 * e.g.
 * 
 * <pre>
 * use A;
 * use A as B;
 * use \A\B as C;
 * use function \A\B as C;
 * use A\B\ { C };
 * </pre>
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
	private FullyQualifiedReference namespace;

	public UseStatement(int start, int end, List<UsePart> parts) {
		this(start, end, parts, T_NONE);
	}

	public UseStatement(int start, int end, List<UsePart> parts, int statementType) {
		this(start, end, null, parts, statementType);
	}

	public UseStatement(int start, int end, FullyQualifiedReference namespace, List<UsePart> parts) {
		this(start, end, namespace, parts, UseStatement.T_NONE);
	}

	public UseStatement(int start, int end, FullyQualifiedReference namespace, List<UsePart> parts, int statementType) {
		super(start, end);

		assert parts != null;
		this.setNamespace(namespace);
		this.parts = parts;
		this.statementType = statementType;
	}

	@Override
	public void traverse(ASTVisitor visitor) throws Exception {
		if (visitor.visit(this)) {
			for (UsePart part : parts) {
				part.traverse(visitor);
			}
			visitor.endvisit(this);
		}
	}

	@Override
	public int getKind() {
		return ASTNodeKinds.USE_STATEMENT;
	}

	public Collection<UsePart> getParts() {
		return parts;
	}

	public void setStatementType(int statementType) {
		this.statementType = statementType;
	}

	/**
	 * <b>WARNING:</b> to get "full" use statement type, use
	 * UsePart#getStatementType().
	 * 
	 * @see UsePart#getStatementType()
	 */
	public int getStatementType() {
		return statementType;
	}

	public void setNamespace(FullyQualifiedReference namespace) {
		this.namespace = namespace;
		if (namespace != null) {
			namespace.removeGlobal();
		}
	}

	public FullyQualifiedReference getNamespace() {
		return namespace;
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
