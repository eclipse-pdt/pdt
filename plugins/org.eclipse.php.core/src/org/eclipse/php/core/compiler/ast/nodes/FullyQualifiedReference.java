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

import org.eclipse.dltk.annotations.Internal;
import org.eclipse.dltk.annotations.Nullable;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.references.TypeReference;

/**
 * This is a reference to the PHP element name which has a namespace prefix in
 * it.
 * 
 * @author michael
 */
public class FullyQualifiedReference extends TypeReference {

	public final static int T_TYPE = 1;
	public final static int T_FUNCTION = 2;
	public final static int T_CONSTANT = 3;

	private NamespaceReference namespace;
	private boolean nullable;
	private int elementType;

	public FullyQualifiedReference(int start, int end, String name, NamespaceReference namespace) {
		this(start, end, name, namespace, T_CONSTANT);
	}

	public FullyQualifiedReference(int start, int end, String name, NamespaceReference namespace, int type) {
		super(start, end, name);
		this.namespace = namespace;
		this.elementType = type;
	}

	public FullyQualifiedReference(int start, int end, String fqn, boolean global, boolean local) {
		this(start, end, fqn, global, local, T_CONSTANT);
	}

	public FullyQualifiedReference(int start, int end, String fqn, boolean global, boolean local, int elementType) {
		super(start, end, null);
		this.elementType = elementType;
		int pos = fqn.lastIndexOf(NamespaceReference.NAMESPACE_DELIMITER);
		if (pos == -1) {
			// ignore
			if (global) {
				setNamespace(new NamespaceReference(start, start + 1, NamespaceReference.EMPTY, global, local));
			} else if (local) {
				setNamespace(new NamespaceReference(start, start, NamespaceReference.EMPTY, global, local));
			}
		} else {
			setNamespace(new NamespaceReference(start, start + pos + (global ? 1 : 0), fqn.substring(0, pos), global,
					local));
		}
		setName(fqn.substring(pos + 1));
	}

	@Override
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
	@Nullable
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

	public boolean isNullable() {
		return nullable;
	}

	public void setNullable(boolean nullable) {
		this.nullable = nullable;
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
		if (name.length() == 0 || name.charAt(name.length() - 1) != NamespaceReference.NAMESPACE_SEPARATOR) {
			buf.append(NamespaceReference.NAMESPACE_SEPARATOR);
		}
		buf.append(getName());

		return buf.toString();
	}

	public int getElementType() {
		return elementType;
	}

	public void setElementType(int elementType) {
		this.elementType = elementType;
	}

	/**
	 * Used for useStatements
	 * 
	 * XXX: Remove this hack
	 */
	@Internal
	public void removeGlobal() {

		if (namespace != null && namespace.isGlobal()) {
			setStart(start() + 1);
			namespace.setGlobal(false);
			namespace.setStart(namespace.start() + 1);
			if (namespace.getName().length() == 0) {
				namespace = null;
			}

		}
	}
}
