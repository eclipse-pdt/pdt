package org.eclipse.php.internal.core.typeinference.goals.phpdoc;

import org.eclipse.dltk.ti.InstanceContext;
import org.eclipse.dltk.ti.goals.AbstractGoal;

public class PHPDocClassVariableGoal extends AbstractGoal {

	private String variableName;

	public PHPDocClassVariableGoal(InstanceContext context, String variableName) {
		super(context);
		this.variableName = variableName;
	}

	public String getVariableName() {
		return variableName;
	}
}
