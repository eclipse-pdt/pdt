package org.eclipse.php.internal.core.typeinference.goals;

import org.eclipse.dltk.ti.goals.AbstractGoal;

public class ScalarGoal extends AbstractGoal implements IWeightedGoal {

	private int scalarType;

	public ScalarGoal(int scalarType) {
		super(null);
	}

	public int getScalarType() {
		return scalarType;
	}

	public int getWeight() {
		return IWeightedGoal.LITE;
	}
}
