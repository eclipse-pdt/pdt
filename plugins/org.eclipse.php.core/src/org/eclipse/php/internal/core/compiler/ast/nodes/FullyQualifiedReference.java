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
import org.eclipse.dltk.ast.DLTKToken;
import org.eclipse.dltk.ast.references.TypeReference;

/**
 * This is a reference to the PHP element name which has a namespace prefix in
 * it.
 * 
 * @author michael
 */
public class FullyQualifiedReference extends TypeReference {

	private NamespaceReference namespace;

	public FullyQualifiedReference(DLTKToken token) {
		super(token);
	}

	public FullyQualifiedReference(int start, int end, String name,
			NamespaceReference namespace) {
		super(start, end, name);
		this.namespace = namespace;
	}

	public void traverse(ASTVisitor pVisitor) throws Exception {
		if (pVisitor.visit(this)) {
			if (namespace != null) {
				namespace.traverse(pVisitor);
			}
			pVisitor.endvisit(this);
		}
	}

	/**
	 * Returns namespace reference prefix
	 * 
	 * @return
	 */
	public NamespaceReference getNamespace() {
		return namespace;
	}

	/**
	 * Sets namespace reference prefix
	 * 
	 * @param namespace
	 */
	public void setNamespace(NamespaceReference namespace) {
		this.namespace = namespace;
	}

	/**
	 * Returns the full name including the namespace prefix
	 * 
	 * @return
	 */
	public String getFullyQualifiedName() {
		if (namespace == null) {
			return getName();
		}

		String name = namespace.getName();
		StringBuilder buf = new StringBuilder(name);
		if (name.length() == 0
				|| name.charAt(name.length() - 1) != NamespaceReference.NAMESPACE_SEPARATOR) {
			buf.append(NamespaceReference.NAMESPACE_SEPARATOR);
		}
		buf.append(getName());

		return buf.toString();
	}
}
