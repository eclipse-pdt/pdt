/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.typeinference.goals;

import org.eclipse.core.runtime.Assert;
import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.ti.goals.AbstractTypeGoal;

public class MethodElementReturnTypeGoal extends AbstractTypeGoal {
	
	private final IMethod methodElement;

	public IMethod getMethod() {
		return methodElement;
	}

	public MethodElementReturnTypeGoal(IMethod methodElement) {
		super(null);
		Assert.isNotNull(methodElement);
		this.methodElement = methodElement;
	}
}
