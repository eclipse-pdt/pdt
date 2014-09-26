/*******************************************************************************
 * Copyright (c) 2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.core.codeassist;

import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceRange;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.dltk.internal.core.SourceMethod;

public class AliasMethod extends SourceMethod {

	private String alias;
	IMethod method;

	public AliasMethod(ModelElement sourceModule, String name, String alias) {
		super((ModelElement) sourceModule.getParent(), name);
		this.alias = alias;
		this.method = (IMethod) sourceModule;
	}

	@Override
	public ISourceRange getSourceRange() throws ModelException {
		return ((IMethod) method).getSourceRange();
	}

	@Override
	public int getFlags() throws ModelException {
		return ((IMethod) method).getFlags();
	}

	public String getAlias() {
		return alias;
	}

	public IModelElement getMethod() {
		return method;
	}

}
