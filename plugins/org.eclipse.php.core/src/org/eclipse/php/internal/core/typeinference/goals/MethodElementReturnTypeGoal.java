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

import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.ti.IContext;
import org.eclipse.dltk.ti.types.IEvaluatedType;

public class MethodElementReturnTypeGoal extends AbstractMethodReturnTypeGoal {

	public MethodElementReturnTypeGoal(IContext context,
			IEvaluatedType evaluatedType, String methodName, String[] argNames) {
		super(context, evaluatedType, methodName, argNames);
	}

	public MethodElementReturnTypeGoal(IContext context, IType[] types,
			String methodName) {
		super(context, types, methodName);
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof MethodElementReturnTypeGoal)) {
			return false;
		}
		return this == obj;
	}
}
