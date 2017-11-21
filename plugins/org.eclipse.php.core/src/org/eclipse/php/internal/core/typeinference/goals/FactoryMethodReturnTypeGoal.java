/*******************************************************************************
 * Copyright (c) 2017 Michele Locati and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Michele Locati - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.typeinference.goals;

import java.util.Arrays;

import org.eclipse.dltk.ti.IContext;
import org.eclipse.dltk.ti.types.IEvaluatedType;

/**
 * Goal representing a prospective factory method. Resolve this goal by
 * inspecting getEvaluatedType() / getMethodName() / getStringArguments()
 */
public class FactoryMethodReturnTypeGoal extends AbstractMethodReturnTypeGoal {

	/**
	 * The list of string arguments. The size of the array is the same as the number
	 * of arguments of the method call. Every item can be a String if the method
	 * argument is a string, NULL otherwise.
	 */
	protected String[] stringArguments;

	public FactoryMethodReturnTypeGoal(IContext context, IEvaluatedType evaluatedType, String methodName, int offset,
			String[] stringArguments) {
		super(context, evaluatedType, methodName, offset);
		this.stringArguments = stringArguments;
	}

	/**
	 * @return The list of string arguments. The size of the array is the same as
	 *         the number of arguments of the method call. Every item can be a
	 *         String if the method argument is a string, NULL otherwise.
	 */
	public String[] getStringArguments() {
		return this.stringArguments;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof FactoryMethodReturnTypeGoal)) {
			return false;
		}
		FactoryMethodReturnTypeGoal typedObj = (FactoryMethodReturnTypeGoal) obj;
		if (!Arrays.equals(this.stringArguments, typedObj.stringArguments)) {
			return false;
		}
		return super.equals(obj);
	}

}
