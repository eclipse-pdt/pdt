/*******************************************************************************
 * Copyright (c) 2009, 2015 IBM Corporation and others.
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
import org.eclipse.php.internal.core.typeinference.IModelAccessCache;
import org.eclipse.php.internal.core.typeinference.PHPTypeInferenceUtils;
import org.eclipse.php.internal.core.typeinference.context.IModelCacheContext;
import org.eclipse.php.internal.core.typeinference.context.MethodContext;

public abstract class AbstractMethodReturnTypeGoal extends AbstractTypeGoal {

	private final String methodName;
	private IType[] types;
	private IEvaluatedType evaluatedType;
	private String[] argNames;
	private int offset;

	public AbstractMethodReturnTypeGoal(IContext context, IEvaluatedType evaluatedType, String methodName, int offset) {
		super(context);
		Assert.isNotNull(methodName);
		this.methodName = methodName;
		this.evaluatedType = evaluatedType;
		this.offset = offset;
	}

	public AbstractMethodReturnTypeGoal(IContext context, IType[] types, String methodName, String[] argNames,
			int offset) {
		super(context);
		Assert.isNotNull(methodName);
		this.methodName = methodName;
		this.types = types;
		this.argNames = argNames;
		this.offset = offset;
	}

	public AbstractMethodReturnTypeGoal(IContext context, IType[] types, String methodName, int offset) {
		this(context, types, methodName, null, offset);
	}

	public AbstractMethodReturnTypeGoal(IContext context, IEvaluatedType evaluatedType, String methodName,
			String[] argNames, int offset) {
		this(context, evaluatedType, methodName, offset);
		this.argNames = argNames;
	}

	public String[] getArgNames() {
		return argNames;
	}

	public String getMethodName() {
		return methodName;
	}

	public int getOffset() {
		return offset;
	}

	public IType[] getTypes() {
		if (types == null) {
			if (evaluatedType != null) {
				final ISourceModuleContext cnt = (ISourceModuleContext) context;
				IModelAccessCache cache = null;
				if (context instanceof IModelCacheContext) {
					cache = ((IModelCacheContext) context).getCache();
				}
				types = PHPTypeInferenceUtils.getModelElements(evaluatedType, cnt, cnt instanceof MethodContext
						? ((MethodContext) cnt).getMethodNode().start() : cnt.getRootNode().end(), cache);
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

		result = prime * result + ((methodName == null) ? 0 : methodName.hashCode());
		result = prime * result + (this.evaluatedType != null ? this.evaluatedType.hashCode() : 0);
		result = prime * result + Arrays.hashCode(argNames);
		result = prime * result + this.offset;
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
		if (this.offset != other.offset) {
			return false;
		}
		if (argNames == null) {
			if (other.argNames != null)
				return false;
		} else if (!Arrays.equals(argNames, other.argNames))
			return false;
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
		return true;
	}
}
