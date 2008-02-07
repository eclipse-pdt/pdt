package org.eclipse.php.internal.core.typeinference.evaluators;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.goals.ExpressionTypeGoal;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.compiler.ast.nodes.Assignment;

public class AssignmentEvaluator extends GoalEvaluator {

	private IEvaluatedType result;

	public AssignmentEvaluator(IGoal goal) {
		super(goal);
	}

	public IGoal[] init() {
		ExpressionTypeGoal typedGoal = (ExpressionTypeGoal) goal;
		ASTNode expression = (typedGoal).getExpression();
		if (!(expression instanceof Assignment)) {
			return IGoal.NO_GOALS;
		}
		Assignment expr = (Assignment) expression;
		return new IGoal[] { new ExpressionTypeGoal(typedGoal.getContext(), expr.getValue()) };
	}

	public IGoal[] subGoalDone(IGoal subgoal, Object result, GoalState state) {
		this.result = (IEvaluatedType) result;
		return IGoal.NO_GOALS;
	}

	public Object produceResult() {
		return result;
	}
}
