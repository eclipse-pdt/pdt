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
}
