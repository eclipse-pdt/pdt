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

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.references.SimpleReference;

/**
 * Represent a 'use' part statement.
 * <pre>e.g.<pre>
 * A as B;
 * \A\B as C;
 */
public class UsePart extends ASTNode {
	
	private FullyQualifiedReference namespace;
	private SimpleReference alias;
	
	public UsePart(FullyQualifiedReference namespace, SimpleReference alias) {
		this.setNamespace(namespace);
		this.setAlias(alias);
	}

	public String toString() {
		StringBuilder buf = new StringBuilder("[USE: ").append(getNamespace().getFullyQualifiedName()); //$NON-NLS-1$
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
}