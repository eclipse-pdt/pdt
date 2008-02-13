package org.eclipse.php.internal.core.typeinference.evaluators;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.goals.ExpressionTypeGoal;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.compiler.ast.nodes.ArrayCreation;
import org.eclipse.php.internal.core.compiler.ast.nodes.ArrayElement;
import org.eclipse.php.internal.core.typeinference.PHPTypeInferenceUtils;

public class ArrayCreationEvaluator extends GoalEvaluator {

	private List<IEvaluatedType> evaluated = new LinkedList<IEvaluatedType>();

	public ArrayCreationEvaluator(IGoal goal) {
		super(goal);
	}

	public IGoal[] init() {
		ExpressionTypeGoal typedGoal = (ExpressionTypeGoal) goal;
		ArrayCreation arrayCreation = (ArrayCreation) typedGoal.getExpression();

		List<IGoal> subGoals = new LinkedList<IGoal>();
		for (ArrayElement arrayElement : arrayCreation.getElements()) {
			subGoals.add(new ExpressionTypeGoal(typedGoal.getContext(), arrayElement.getValue()));
		}
		return subGoals.toArray(new IGoal[subGoals.size()]);
	}

	public Object produceResult() {
		return PHPTypeInferenceUtils.combineMultiType(evaluated);
	}

	public IGoal[] subGoalDone(IGoal subgoal, Object result, GoalState state) {
		evaluated.add((IEvaluatedType)result);
		return IGoal.NO_GOALS;
	}
}
