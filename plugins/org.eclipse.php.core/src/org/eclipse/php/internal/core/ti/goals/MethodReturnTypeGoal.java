/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/

package org.eclipse.php.internal.core.ti.goals;

import org.eclipse.php.internal.core.ti.IContext;
import org.eclipse.php.internal.core.ti.types.IEvaluatedType;

public class MethodReturnTypeGoal extends AbstractGoal {

	private final String methodName;
	private final IEvaluatedType[] arguments;

	public String getMethodName() {
		return methodName;
	}

	public IEvaluatedType[] getArguments() {
		return arguments;
	}

	public MethodReturnTypeGoal(IContext context, String methodName, IEvaluatedType[] arguments) {
		super(context);
		this.methodName = methodName;
		this.arguments = arguments;
	}
}
