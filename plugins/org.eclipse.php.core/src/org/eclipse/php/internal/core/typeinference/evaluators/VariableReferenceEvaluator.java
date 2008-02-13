package org.eclipse.php.internal.core.typeinference.evaluators;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.evaluation.types.SimpleType;
import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.IContext;
import org.eclipse.dltk.ti.goals.ExpressionTypeGoal;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.typeinference.MethodContext;
import org.eclipse.php.internal.core.typeinference.PHPClassType;
import org.eclipse.php.internal.core.typeinference.PHPTypeInferenceUtils;
import org.eclipse.php.internal.core.typeinference.goals.VariableTypeGoal;

public class VariableReferenceEvaluator extends GoalEvaluator {

	private List<IEvaluatedType> results = new ArrayList<IEvaluatedType>();

	public VariableReferenceEvaluator(IGoal goal) {
		super(goal);
	}

	public IGoal[] init() {
		VariableReference variableReference = (VariableReference) ((ExpressionTypeGoal) goal).getExpression();
		IContext context = goal.getContext();

		// Handle $this variable reference
		if (variableReference.getName().equals("$this")) {
			if (context instanceof MethodContext) {
				MethodContext methodContext = (MethodContext) context;
				IEvaluatedType instanceType = methodContext.getInstanceType();
				if (instanceType != null) {
					this.results.add(new PHPClassType(instanceType.getTypeName()));
				} else {
					this.results.add(new SimpleType(SimpleType.TYPE_NULL));
				}
				return IGoal.NO_GOALS;
			}
		}

		return new IGoal[] { new VariableTypeGoal(context, variableReference) };
	}

	public Object produceResult() {
		return PHPTypeInferenceUtils.combineTypes(results);
	}

	public IGoal[] subGoalDone(IGoal subgoal, Object result, GoalState state) {
		results.add((IEvaluatedType) result);
		return IGoal.NO_GOALS;
	}
}
