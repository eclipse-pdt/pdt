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

import org.eclipse.dltk.ti.ISourceModuleContext;
import org.eclipse.dltk.ti.InstanceContext;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.typeinference.IModelAccessCache;

/**
 * This is a context for the PHP class or interface
 * 
 * @author michael
 */
public class TypeContext extends InstanceContext implements INamespaceContext,
		IModelCacheContext {

	private String namespaceName;
	private IModelAccessCache cache;

	public TypeContext(ISourceModuleContext parent, IEvaluatedType instanceType) {
		super(parent, instanceType);

		if (parent instanceof INamespaceContext) {
			this.namespaceName = ((INamespaceContext) parent).getNamespace();
		}
	}

	public String getNamespace() {
		return namespaceName;
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
		TypeContext other = (TypeContext) obj;
		if (namespaceName == null) {
			if (other.namespaceName != null)
				return false;
		} else if (!namespaceName.equals(other.namespaceName))
			return false;
		return true;
	}
}
