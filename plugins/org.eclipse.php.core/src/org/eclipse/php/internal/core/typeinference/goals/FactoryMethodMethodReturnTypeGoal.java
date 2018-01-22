/*******************************************************************************
 * Copyright (c) 2018 Michele Locati and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Michele Locati
 *******************************************************************************/
package org.eclipse.php.internal.core.typeinference.goals;

import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.ti.IContext;
import org.eclipse.dltk.ti.types.IEvaluatedType;

public class FactoryMethodMethodReturnTypeGoal extends AbstractMethodReturnTypeGoal {

	public FactoryMethodMethodReturnTypeGoal(IContext context, IEvaluatedType evaluatedType, String methodName,
			String[] argNames, int offset) {
		super(context, evaluatedType, methodName, argNames, offset);
	}

	public FactoryMethodMethodReturnTypeGoal(IContext context, IType[] types, String methodName, String[] argNames,
			int offset) {
		super(context, types, methodName, argNames, offset);
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof FactoryMethodMethodReturnTypeGoal)) {
			return false;
		}
		return super.equals(obj);
	}
}
