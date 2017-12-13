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
import org.eclipse.php.core.ast.nodes.UseStatement;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;

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
	private FullyQualifiedReference groupNamespace;
	private SimpleReference alias;
	private int statementType;

	public UsePart(FullyQualifiedReference namespace, SimpleReference alias) {
		this(null, namespace, alias, UseStatement.T_NONE);
	}

	public UsePart(FullyQualifiedReference groupNamespace, FullyQualifiedReference namespace, SimpleReference alias) {
		this(groupNamespace, namespace, alias, UseStatement.T_NONE);
	}

	public UsePart(FullyQualifiedReference namespace, SimpleReference alias, int statementType) {
		this(null, namespace, alias, statementType);
	}

	public UsePart(FullyQualifiedReference groupNamespace, FullyQualifiedReference namespace, SimpleReference alias,
			int statementType) {
		this.setGroupNamespace(groupNamespace);
		this.setNamespace(namespace);
		this.setAlias(alias);
		this.statementType = statementType;
	}

	@Override
	public String toString() {
		StringBuilder buf = new StringBuilder("[USE: ") //$NON-NLS-1$
				.append(getFullUseStatementName());
		if (getAlias() != null) {
			buf.append(" AS ").append(getAlias().getName()); //$NON-NLS-1$
		}
		buf.append("]"); //$NON-NLS-1$
		return buf.toString();
	}

	@Override
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

	public void setGroupNamespace(FullyQualifiedReference groupNamespace) {
		this.groupNamespace = groupNamespace;
	}

	public FullyQualifiedReference getGroupNamespace() {
		return groupNamespace;
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

	/**
	 * Returns the fully qualified name (without leading '\') that is the
	 * concatenation of the group use statement name (if any) and this use
	 * statement part name. Supports normal use statements and grouped use
	 * statements. <b>Returned name will not be null, will not be empty and will
	 * have no leading '\'.</b>
	 * 
	 * @return full use statement name
	 */
	public String getFullUseStatementName() {
		if (getGroupNamespace() == null) {
			return getNamespace().getFullyQualifiedName();
		}
		return PHPModelUtils.concatFullyQualifiedNames(getGroupNamespace().getFullyQualifiedName(),
				getNamespace().getFullyQualifiedName());
	}
}