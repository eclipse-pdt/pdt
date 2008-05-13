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
