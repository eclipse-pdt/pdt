/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
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
package org.eclipse.php.internal.core.typeinference.goals;

import org.eclipse.dltk.ti.IContext;
import org.eclipse.dltk.ti.goals.AbstractGoal;

public class GlobalVariableReferencesGoal extends AbstractGoal {

	private String variableName;

	public GlobalVariableReferencesGoal(IContext context, String variableName) {
		super(context);
		this.variableName = variableName;
	}

	public String getVariableName() {
		return variableName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((variableName == null) ? 0 : variableName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		GlobalVariableReferencesGoal other = (GlobalVariableReferencesGoal) obj;
		if (variableName == null) {
			if (other.variableName != null) {
				return false;
			}
		} else if (!variableName.equals(other.variableName)) {
			return false;
		}
		return true;
	}
}
