package org.eclipse.php.internal.core.typeinference.goals;

import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.ti.InstanceContext;
import org.eclipse.dltk.ti.goals.AbstractTypeGoal;
import org.eclipse.dltk.ti.goals.IGoal;

public class ClassVariableDeclarationGoal extends AbstractTypeGoal implements IGoal, IWeightedGoal {

	private SimpleReference field;

	public ClassVariableDeclarationGoal(InstanceContext context, SimpleReference field) {
		super(context);
		this.field = field;
	}

	public SimpleReference getField() {
		return field;
	}

	public int getWeight() {
		return IWeightedGoal.HEAVY;
	}
}
