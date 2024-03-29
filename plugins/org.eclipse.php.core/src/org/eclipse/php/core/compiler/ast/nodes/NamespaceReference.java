/*******************************************************************************
 * Copyright (c) 2009, 2015 IBM Corporation and others.
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

import org.eclipse.dltk.annotations.NonNull;
import org.eclipse.dltk.ast.references.TypeReference;

/**
 * This is a reference to a namespace or to a namespace alias.
 * 
 * @author michael
 */
public class NamespaceReference extends TypeReference {

	public final static char NAMESPACE_SEPARATOR = '\\';
	public final static String NAMESPACE_DELIMITER = "\\"; //$NON-NLS-1$
	public final static String EMPTY = ""; //$NON-NLS-1$
	private boolean global;
	private boolean local;

	public NamespaceReference(int start, int end, String name) {
		super(start, end, name);
	}

	public NamespaceReference(int start, int end, String name, boolean global, boolean local) {
		super(start, end, name);
		this.global = global;
		this.local = local;
	}

	/**
	 * Returns whether the namespace name is prefixed with a '\' character. <br>
	 * <b>WARNING:</b> isGlobal() will always return false for
	 * NamespaceReferences used inside any kind of use statement declaration,
	 * because we never set this property for any kind of use statement
	 * declaration.
	 * 
	 * @return
	 */
	public boolean isGlobal() {
		return global;
	}

	/**
	 * Sets whether the namespace name is prefixed with a '\' character
	 * 
	 * @param global
	 */
	public void setGlobal(boolean global) {
		this.global = global;
	}

	/**
	 * Returns whether the namespace name is prefixed with a 'namespace'
	 * keyword. <br>
	 * <b>WARNING:</b> despite its name, method isLocal() is <b>not</b> related
	 * to method isGlobal(). <br>
	 * <b>WARNING:</b> isLocal() will always return false for
	 * NamespaceReferences used inside any kind of use statement declaration,
	 * because we never set this property for any kind of use statement
	 * declaration.
	 * 
	 * @return
	 */
	public boolean isLocal() {
		return local;
	}

	/**
	 * Sets whether the namespace name is prefixed with a 'namespace' keyword
	 * 
	 * @param local
	 */
	public void setLocal(boolean local) {
		this.local = local;
	}

	/**
	 * Returns namespace name, prefixed by "\" for global namespaces or
	 * "namespace\" for local namespaces. The returned value is never null and
	 * never empty.
	 */
	@SuppressWarnings("null")
	@Override
	@NonNull
	public String getName() {
		StringBuilder buf = new StringBuilder();
		if (global) {
			buf.append(NAMESPACE_SEPARATOR);
		}
		if (local) {
			buf.append("namespace\\"); //$NON-NLS-1$
		}
		buf.append(super.getName());
		String name = buf.toString();

		assert name.length() > 0;

		return name;
	}
}
