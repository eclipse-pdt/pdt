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
package org.eclipse.php.internal.core.typeinference.goals;

import java.util.Arrays;

import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.ti.IContext;
import org.eclipse.dltk.ti.goals.AbstractTypeGoal;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.php.internal.core.typeinference.context.TypeContext;

public class ClassVariableDeclarationGoal extends AbstractTypeGoal implements
		IGoal {

	private IType[] types;
	private String variableName;

	public ClassVariableDeclarationGoal(TypeContext context, String variableName) {
		super(context);
		this.variableName = variableName;
	}

	/**
	 * Use this constructor when containing classes are already known
	 * 
	 * @param context
	 * @param types
	 * @param variableName
	 */
	public ClassVariableDeclarationGoal(IContext context, IType[] types,
			String variableName) {
		super(context);
		this.types = types;
		this.variableName = variableName;
	}

	public IType[] getTypes() {
		return types;
	}

	public String getVariableName() {
		return variableName;
	}

	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Arrays.hashCode(types);
		result = prime * result
				+ ((variableName == null) ? 0 : variableName.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClassVariableDeclarationGoal other = (ClassVariableDeclarationGoal) obj;
		if (!Arrays.equals(types, other.types))
			return false;
		if (variableName == null) {
			if (other.variableName != null)
				return false;
		} else if (!variableName.equals(other.variableName))
			return false;
		return true;
	}
}
