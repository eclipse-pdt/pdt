/*******************************************************************************
 * Copyright (c) 2018 Michele Locati and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
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
