package org.eclipse.php.internal.core.typeinference.evaluators;

import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.goals.ExpressionTypeGoal;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.compiler.ast.nodes.CatchClause;
import org.eclipse.php.internal.core.typeinference.PHPClassType;

public class CatchClauseEvaluator extends GoalEvaluator {

	private IEvaluatedType result;

	public CatchClauseEvaluator(IGoal goal) {
		super(goal);
	}

	public IGoal[] init() {
		ExpressionTypeGoal typedGoal = (ExpressionTypeGoal) goal;
		CatchClause catchClause = (CatchClause) typedGoal.getExpression();

		SimpleReference type = catchClause.getClassName();
		if (type != null) {
			result = new PHPClassType(type.getName());
		}
		return IGoal.NO_GOALS;
	}

	public Object produceResult() {
		return result;
	}

	public IGoal[] subGoalDone(IGoal subgoal, Object result, GoalState state) {
		return IGoal.NO_GOALS;
	}
}
