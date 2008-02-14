package org.eclipse.php.internal.core.typeinference.evaluators;

import java.util.List;

import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.evaluation.types.AmbiguousType;
import org.eclipse.dltk.evaluation.types.MultiTypeType;
import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.goals.ExpressionTypeGoal;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.compiler.ast.nodes.ArrayVariableReference;
import org.eclipse.php.internal.core.typeinference.goals.VariableTypeGoal;

public class ArrayVariableReferenceEvaluator extends GoalEvaluator {

	private IEvaluatedType result;

	public ArrayVariableReferenceEvaluator(IGoal goal) {
		super(goal);
	}

	public IGoal[] init() {
		ExpressionTypeGoal typedGoal = (ExpressionTypeGoal) goal;
		ArrayVariableReference reference = (ArrayVariableReference) typedGoal.getExpression();
		return new IGoal[] { new VariableTypeGoal(goal.getContext(), new VariableReference(reference.sourceStart(), reference.sourceEnd(), reference.getName())) };
	}

	public Object produceResult() {
		return result;
	}

	@SuppressWarnings("unchecked")
	public IGoal[] subGoalDone(IGoal subgoal, Object result, GoalState state) {
		if (result instanceof MultiTypeType) {
			MultiTypeType multiTypeType = (MultiTypeType) result;
			List<IEvaluatedType> types = multiTypeType.getTypes();
			result = new AmbiguousType(types.toArray(new IEvaluatedType[types.size()]));
		}
		this.result = (IEvaluatedType) result;
		return IGoal.NO_GOALS;
	}

}
