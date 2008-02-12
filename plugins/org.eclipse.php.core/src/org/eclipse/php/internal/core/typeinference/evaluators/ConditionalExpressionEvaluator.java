package org.eclipse.php.internal.core.typeinference.evaluators;

import java.util.*;

import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.goals.ExpressionTypeGoal;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.compiler.ast.nodes.ConditionalExpression;
import org.eclipse.php.internal.core.typeinference.PHPTypeInferenceUtils;

public class ConditionalExpressionEvaluator extends GoalEvaluator {

	private final List<IEvaluatedType> evaluated = new LinkedList<IEvaluatedType>();

	public ConditionalExpressionEvaluator(IGoal goal) {
		super(goal);
	}

	public Object produceResult() {
		if (!evaluated.isEmpty()) {
			return PHPTypeInferenceUtils.combineTypes(evaluated);
		}
		return null;
	}

	public IGoal[] init() {
		ExpressionTypeGoal typedGoal = (ExpressionTypeGoal) goal;
		ConditionalExpression conditionalExpression = (ConditionalExpression) typedGoal.getExpression();
		return new IGoal[] { new ExpressionTypeGoal(goal.getContext(), conditionalExpression.getIfTrue()), new ExpressionTypeGoal(goal.getContext(), conditionalExpression.getIfFalse()) };
	}

	public IGoal[] subGoalDone(IGoal subgoal, Object result, GoalState state) {
		if (result != null) {
			evaluated.add((IEvaluatedType) result);
		}
		return IGoal.NO_GOALS;
	}

}
