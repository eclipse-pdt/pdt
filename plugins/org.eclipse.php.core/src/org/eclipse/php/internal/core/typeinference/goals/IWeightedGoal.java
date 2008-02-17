package org.eclipse.php.internal.core.typeinference.goals;

public interface IWeightedGoal {

	public static final int LITE = 1;
	public static final int HEAVY = 2;

	public int getWeight();
}
