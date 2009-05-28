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

import org.eclipse.dltk.ast.DLTKToken;
import org.eclipse.dltk.ast.references.TypeReference;

/**
 * This is a reference to a namespace or to a namespace alias.
 * @author michael
 */
public class NamespaceReference extends TypeReference {
	
	public final static char NAMESPACE_SEPARATOR = '\\';
	private boolean global;
	private boolean local; 

	public NamespaceReference(DLTKToken token) {
		super(token);
	}

	public NamespaceReference(int start, int end, String name) {
		super(start, end, name);
	}
	
	public NamespaceReference(int start, int end, String name, boolean global, boolean local) {
		super(start, end, name);
	}

	/**
	 * Returns whether the namespace name is prefixed with a '\' character
	 * @return
	 */
	public boolean isGlobal() {
		return global;
	}

	/**
	 * Sets whether the namespace name is prefixed with a '\' character
	 * @param global
	 */
	public void setGlobal(boolean global) {
		this.global = global;
	}

	/**
	 * Returns whether the namespace name is prefixed with a 'namespace' keyword
	 * @return
	 */
	public boolean isLocal() {
		return local;
	}

	/**
	 * Sets whether the namespace name is prefixed with a 'namespace' keyword
	 * @param local
	 */
	public void setLocal(boolean local) {
		this.local = local;
	}

	public String getName() {
		StringBuilder buf = new StringBuilder();
		if (global) {
			buf.append(NAMESPACE_SEPARATOR);
		}
		if (local) {
			buf.append("namespace\\");
		}
		buf.append(super.getName());
		return buf.toString();
	}
}
