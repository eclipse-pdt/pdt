/*******************************************************************************
 * Copyright (c) 2009, 2015 IBM Corporation and others.
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
package org.eclipse.php.internal.core.typeinference.goals.phpdoc;

import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.ti.IContext;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.typeinference.goals.AbstractMethodReturnTypeGoal;

public class PHPDocMethodReturnTypeGoal extends AbstractMethodReturnTypeGoal {

	public PHPDocMethodReturnTypeGoal(IContext context, IEvaluatedType evaluatedType, String methodName,
			String[] argNames, int offset) {
		super(context, evaluatedType, methodName, argNames, offset);
	}

	public PHPDocMethodReturnTypeGoal(IContext context, IType[] types, String methodName, int offset) {
		super(context, types, methodName, offset);
	}

	public PHPDocMethodReturnTypeGoal(IContext context, IType[] types, String methodName, String[] argNames,
			int offset) {
		super(context, types, methodName, argNames, offset);
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof PHPDocMethodReturnTypeGoal)) {
			return false;
		}
		return super.equals(obj);
	}
}
