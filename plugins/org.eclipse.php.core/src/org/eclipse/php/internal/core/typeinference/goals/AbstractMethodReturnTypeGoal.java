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

import org.eclipse.core.runtime.Assert;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.ti.IContext;
import org.eclipse.dltk.ti.ISourceModuleContext;
import org.eclipse.dltk.ti.goals.AbstractTypeGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.typeinference.PHPTypeInferenceUtils;

public abstract class AbstractMethodReturnTypeGoal extends AbstractTypeGoal {

	private final String methodName;
	private IType[] types;
	private IEvaluatedType evaluatedType;
	private String[] argNames;

	public AbstractMethodReturnTypeGoal(IContext context,
			IEvaluatedType evaluatedType, String methodName) {
		super(context);
		Assert.isNotNull(methodName);
		this.methodName = methodName;
		this.evaluatedType = evaluatedType;
	}

	public AbstractMethodReturnTypeGoal(IContext context, IType[] types,
			String methodName) {
		super(context);
		Assert.isNotNull(methodName);
		this.methodName = methodName;
		this.types = types;
	}

	public AbstractMethodReturnTypeGoal(IContext context,
			IEvaluatedType evaluatedType, String methodName, String[] argNames) {
		this(context, evaluatedType, methodName);
		this.argNames = argNames;
	}

	public String[] getArgNames() {
		return argNames;
	}

	public String getMethodName() {
		return methodName;
	}

	public IType[] getTypes() {
		if (types == null) {
			if (evaluatedType != null) {
				types = PHPTypeInferenceUtils.getModelElements(evaluatedType,
						(ISourceModuleContext) context);
			}
		}
		return types;
	}

	public IEvaluatedType getEvaluatedType() {
		return this.evaluatedType;
	}

	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((evaluatedType == null) ? 0 : evaluatedType.hashCode());
		result = prime * result
				+ ((methodName == null) ? 0 : methodName.hashCode());
		result = prime * result + Arrays.hashCode(types);
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractMethodReturnTypeGoal other = (AbstractMethodReturnTypeGoal) obj;
		if (evaluatedType == null) {
			if (other.evaluatedType != null)
				return false;
		} else if (!evaluatedType.equals(other.evaluatedType))
			return false;
		if (methodName == null) {
			if (other.methodName != null)
				return false;
		} else if (!methodName.equals(other.methodName))
			return false;
		if (!Arrays.equals(types, other.types))
			return false;
		return true;
	}
}
