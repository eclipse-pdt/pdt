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

/**
 * This is a context for the PHP class or interface
 * @author michael
 */
public class TypeContext extends InstanceContext implements INamespaceContext {
	
	private String namespaceName;

	public TypeContext(ISourceModuleContext parent, IEvaluatedType instanceType) {
		super(parent, instanceType);
		
		if (parent instanceof INamespaceContext) {
			this.namespaceName = ((INamespaceContext)parent).getNamespace();
		}
	}

	public String getNamespace() {
		return namespaceName;
	}

}
