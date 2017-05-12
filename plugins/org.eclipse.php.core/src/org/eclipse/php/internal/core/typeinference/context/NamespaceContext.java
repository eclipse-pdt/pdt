/*******************************************************************************
 * Copyright (c) 2017 Alex Xu and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Alex Xu - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.typeinference.context;

import org.eclipse.dltk.ti.ISourceModuleContext;
import org.eclipse.dltk.ti.InstanceContext;
import org.eclipse.php.internal.core.typeinference.IModelAccessCache;
import org.eclipse.php.internal.core.typeinference.PHPNamespaceType;

/**
 * This is a context for the PHP namespace
 * 
 */
public class NamespaceContext extends InstanceContext implements INamespaceContext, IModelCacheContext {

	private String namespaceName;
	private IModelAccessCache cache;

	public NamespaceContext(ISourceModuleContext parent, PHPNamespaceType instanceType) {
		super(parent, instanceType);
		this.namespaceName = instanceType.getTypeName();
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
		result = prime * result + ((namespaceName == null) ? 0 : namespaceName.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		NamespaceContext other = (NamespaceContext) obj;
		if (namespaceName == null) {
			if (other.namespaceName != null)
				return false;
		} else if (!namespaceName.equals(other.namespaceName))
			return false;
		return true;
	}
}
