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
}
