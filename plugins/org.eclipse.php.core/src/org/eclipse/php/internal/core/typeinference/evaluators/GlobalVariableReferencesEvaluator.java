package org.eclipse.php.internal.core.typeinference.evaluators;

import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;

public class GlobalVariableReferencesEvaluator extends GoalEvaluator {

	public GlobalVariableReferencesEvaluator(IGoal goal) {
		super(goal);
	}

	public IGoal[] init() {
		return IGoal.NO_GOALS;
	}

	public Object produceResult() {
		return null;
	}

	public IGoal[] subGoalDone(IGoal subgoal, Object result, GoalState state) {
		return IGoal.NO_GOALS;
	}
}
