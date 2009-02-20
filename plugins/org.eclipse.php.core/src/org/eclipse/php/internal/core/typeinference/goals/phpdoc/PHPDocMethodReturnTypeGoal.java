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
package org.eclipse.php.internal.core.typeinference.goals.phpdoc;

import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.ti.IContext;
import org.eclipse.dltk.ti.goals.AbstractTypeGoal;

public class PHPDocMethodReturnTypeGoal extends AbstractTypeGoal {

	private IMethod method;

	public PHPDocMethodReturnTypeGoal(IContext context, IMethod methodName) {
		super(context);
		this.method = methodName;
	}

	public IMethod getMethod() {
		return method;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((method == null) ? 0 : method.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		PHPDocMethodReturnTypeGoal other = (PHPDocMethodReturnTypeGoal) obj;
		if (method == null) {
			if (other.method != null) {
				return false;
			}
		} else if (!method.equals(other.method)) {
			return false;
		}
		return true;
	}
}
