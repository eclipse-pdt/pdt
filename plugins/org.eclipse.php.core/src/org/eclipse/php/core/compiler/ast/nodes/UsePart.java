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

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.references.SimpleReference;

/**
 * Represent a 'use' part statement. e.g.
 * 
 * <pre>
 * A as B;
 * \A\B as C;
 * const \A\B as C;
 * </pre>
 */
public class UsePart extends ASTNode {

	private FullyQualifiedReference namespace;
	private SimpleReference alias;
	private int statementType;

	public UsePart(FullyQualifiedReference namespace, SimpleReference alias) {
		this(namespace, alias, UseStatement.T_NONE);
	}

	public UsePart(FullyQualifiedReference namespace, SimpleReference alias, int statementType) {
		this.setNamespace(namespace);
		this.setAlias(alias);
		this.statementType = statementType;
	}

	public String toString() {
		StringBuilder buf = new StringBuilder("[USE: ") //$NON-NLS-1$
				.append(getNamespace().getFullyQualifiedName());
		if (getAlias() != null) {
			buf.append(" AS ").append(getAlias().getName()); //$NON-NLS-1$
		}
		buf.append("]"); //$NON-NLS-1$
		return buf.toString();
	}

	public void traverse(ASTVisitor visitor) throws Exception {
		if (visitor.visit(this)) {
			getNamespace().traverse(visitor);
			if (getAlias() != null) {
				getAlias().traverse(visitor);
			}
			visitor.endvisit(this);
		}
	}

	public void setAlias(SimpleReference alias) {
		this.alias = alias;
	}

	public SimpleReference getAlias() {
		return alias;
	}

	public void setNamespace(FullyQualifiedReference namespace) {
		this.namespace = namespace;
	}

	public FullyQualifiedReference getNamespace() {
		return namespace;
	}

	public void setStatementType(int statementType) {
		this.statementType = statementType;
	}

	public int getStatementType() {
		return statementType;
	}
}