package org.eclipse.php.internal.core.typeinference.evaluators;

import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.goals.ExpressionTypeGoal;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.typeinference.goals.VariableTypeGoal;

/**
 * This evaluator simply delegates to {@link ExpressionTypeGoal}
 */
public class VariableTypeEvaluator extends GoalEvaluator {

	private IEvaluatedType result;

	public VariableTypeEvaluator(IGoal goal) {
		super(goal);
	}

	public IGoal[] init() {
		VariableTypeGoal typedGoal = (VariableTypeGoal) goal;
		return new IGoal[] { new ExpressionTypeGoal(goal.getContext(), typedGoal.getExpression()) };
	}

	public IGoal[] subGoalDone(IGoal subgoal, Object result, GoalState state) {
		this.result = (IEvaluatedType) result;
		return IGoal.NO_GOALS;
	}

	public Object produceResult() {
		return result;
	}
}
