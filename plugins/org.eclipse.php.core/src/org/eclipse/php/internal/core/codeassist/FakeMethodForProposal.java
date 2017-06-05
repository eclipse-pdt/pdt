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
package org.eclipse.php.internal.core.codeassist;

import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.IParameter;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.typeinference.FakeMethod;

public class FakeMethodForProposal extends FakeMethod {

	private IMethod fMethod;

	public FakeMethodForProposal(ModelElement method) {
		super((ModelElement) method.getParent(), getName((IMethod) method));
		fMethod = (IMethod) method;
		try {
			setFlags(fMethod.getFlags());
			setConstructor(fMethod.isConstructor());
			setType(fMethod.getType());
		} catch (ModelException e) {
			PHPCorePlugin.log(e);
		}
	}

	public FakeMethodForProposal(IMethod method, IParameter[] parameters) {
		this((ModelElement) method);
		setParameters(parameters);
	}

	@Override
	public boolean exists() {
		return true;
	}

	public IMethod getMethod() {
		return fMethod;
	}

	private static String getName(IMethod method) {
		if (method.getParent() instanceof AliasType) {
			return ((AliasType) method.getParent()).getAlias();
		}
		return method.getElementName();
	}

	@Override
	public String getFullyQualifiedName() {
		return fMethod.getFullyQualifiedName();
	}

	@Override
	public String getFullyQualifiedName(String enclosingTypeSeparator) {
		return fMethod.getFullyQualifiedName(enclosingTypeSeparator);
	}

	@Override
	public boolean equals(Object o) {
		if (super.equals(o)) {
			if (!(o instanceof FakeMethodForProposal)) {
				return false;
			}
			IMethod method = (FakeMethodForProposal) o;
			try {
				return method.getParameters().length == this.getParameters().length;
			} catch (ModelException e) {
			}
		}
		return false;
	}

}
