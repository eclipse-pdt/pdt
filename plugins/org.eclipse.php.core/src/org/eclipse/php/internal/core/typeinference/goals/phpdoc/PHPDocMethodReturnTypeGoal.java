package org.eclipse.php.internal.core.typeinference.goals.phpdoc;

import org.eclipse.dltk.ti.IContext;
import org.eclipse.dltk.ti.goals.AbstractGoal;

public class PHPDocMethodReturnTypeGoal extends AbstractGoal {

	private String methodName;

	public PHPDocMethodReturnTypeGoal(IContext context, String methodName) {
		super(context);
		this.methodName = methodName;
	}

	public String getMethodName() {
		return methodName;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((methodName == null) ? 0 : methodName.hashCode());
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
		if (methodName == null) {
			if (other.methodName != null) {
				return false;
			}
		} else if (!methodName.equals(other.methodName)) {
			return false;
		}
		return true;
	}
}
