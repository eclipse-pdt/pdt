/*******************************************************************************
 * Copyright (c) 2014 IBM Corporation and others.
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
		return method.getSourceRange();
	}

	@Override
	public int getFlags() throws ModelException {
		return method.getFlags();
	}

	public String getAlias() {
		return alias;
	}

	public IModelElement getMethod() {
		return method;
	}

}
