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

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((variableName == null) ? 0 : variableName.hashCode());
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
		PHPDocClassVariableGoal other = (PHPDocClassVariableGoal) obj;
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
