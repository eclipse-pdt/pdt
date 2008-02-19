package org.eclipse.php.internal.core.typeinference.goals;

import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.ti.IContext;
import org.eclipse.dltk.ti.goals.AbstractGoal;

/**
 * Type of a local or global variables
 */
public class VariableTypeGoal extends AbstractGoal {

	private final VariableReference variableReference;

	public VariableTypeGoal(IContext context, VariableReference variableReference) {
		super(context);
		this.variableReference = variableReference;
	}

	public VariableReference getVariableReference() {
		return variableReference;
	}
}
