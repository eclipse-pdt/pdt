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
package org.eclipse.php.internal.ui.corext.template.php;

import org.eclipse.dltk.core.IType;
import org.eclipse.jface.text.templates.TemplateVariableType;
import org.eclipse.php.internal.ui.text.template.contentassist.MultiVariable;

public final class PhpVariable extends MultiVariable {
	private String fParamType;

	public PhpVariable(TemplateVariableType type, String name, int[] offsets) {
		super(type, name, offsets);
	}

	/*
	 * @see
	 * org.eclipse.jdt.internal.ui.text.template.contentassist.MultiVariable
	 * #toString(java.lang.Object)
	 * 
	 * @since 3.3
	 */
	public String toString(Object object) {
		if (object instanceof IType)
			return ((IType) object).getElementName();
		return super.toString(object);
	}

	/**
	 * Returns the type given as parameter to this variable.
	 * 
	 * @return the type given as parameter to this variable
	 */
	public String getParamType() {
		return fParamType;
	}

	/**
	 * @param paramType
	 *            the paramType
	 * @since 3.3
	 */
	public void setParamType(String paramType) {
		fParamType = paramType;
	}
}
