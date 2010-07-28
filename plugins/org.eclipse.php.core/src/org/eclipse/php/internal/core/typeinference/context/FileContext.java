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
package org.eclipse.php.internal.core.typeinference.context;

import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.ti.BasicContext;
import org.eclipse.dltk.ti.ISourceModuleContext;
import org.eclipse.php.internal.core.typeinference.IModelAccessCache;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;

/**
 * This is a context for PHP file (outside of classes or functions)
 * 
 * @author michael
 */
public class FileContext extends BasicContext implements INamespaceContext,
		IModelCacheContext {

	private String namespaceName;
	private IModelAccessCache cache;

	/**
	 * Creates file context where there's no declaring namespace
	 * 
	 * @param sourceModule
	 * @param rootNode
	 */
	public FileContext(ISourceModule sourceModule, ModuleDeclaration rootNode) {
		super(sourceModule, rootNode);
	}

	/**
	 * Creates a file context for the specific offset
	 * 
	 * @param sourceModule
	 * @param rootNode
	 * @param offset
	 */
	public FileContext(ISourceModule sourceModule, ModuleDeclaration rootNode,
			int offset) {
		super(sourceModule, rootNode);
		IType currentNamespace = PHPModelUtils.getCurrentNamespace(
				sourceModule, offset);
		if (currentNamespace != null) {
			this.namespaceName = currentNamespace.getElementName();
		}
	}

	public FileContext(ISourceModuleContext parent) {
		super(parent);
		if (parent instanceof INamespaceContext) {
			namespaceName = ((INamespaceContext) parent).getNamespace();
		}
	}

	public String getNamespace() {
		return namespaceName;
	}

	/**
	 * Sets the current namespace name
	 * 
	 * @param namespaceName
	 */
	public void setNamespace(String namespaceName) {
		this.namespaceName = namespaceName;
	}

	public IModelAccessCache getCache() {
		return cache;
	}

	public void setCache(IModelAccessCache cache) {
		this.cache = cache;
	}

	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((namespaceName == null) ? 0 : namespaceName.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		FileContext other = (FileContext) obj;
		if (namespaceName == null) {
			if (other.namespaceName != null)
				return false;
		} else if (!namespaceName.equals(other.namespaceName))
			return false;
		return true;
	}
}
