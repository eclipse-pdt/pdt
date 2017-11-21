package org.eclipse.php.core.tests.typeinference.evaluators;

import org.eclipse.dltk.ti.IGoalEvaluatorFactory;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;

public class TestFactoryMethodGoalEvaluatorFactory implements IGoalEvaluatorFactory {

	public GoalEvaluator createEvaluator(IGoal goal) {
		return new TestFactoryMethodReturnTypeEvaluator(goal);
	}

}
