package org.eclipse.php.internal.core.typeinference.goals;

import org.eclipse.dltk.ti.InstanceContext;
import org.eclipse.dltk.ti.goals.AbstractTypeGoal;
import org.eclipse.dltk.ti.goals.IGoal;

public class ClassVariableDeclarationGoal extends AbstractTypeGoal implements IGoal {

	private String variableName;

	public ClassVariableDeclarationGoal(InstanceContext context, String variableName) {
		super(context);
		this.variableName = variableName;
	}

	public String getVariableName() {
		return variableName;
	}
}
