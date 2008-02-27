package org.eclipse.php.internal.core.typeinference.evaluators;

import org.eclipse.dltk.ast.references.TypeReference;
import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.IContext;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.typeinference.MethodContext;
import org.eclipse.php.internal.core.typeinference.PHPClassType;

public class TypeReferenceEvaluator extends GoalEvaluator {

	private TypeReference typeReference;
	private IEvaluatedType result;

	public TypeReferenceEvaluator(IGoal goal, TypeReference typeReference) {
		super(goal);
		this.typeReference = typeReference;
	}

	public IGoal[] init() {
		IContext context = goal.getContext();
		String className = typeReference.getName();

		if ("self".equals(className)) { //$NON-NLS-1$
			if (context instanceof MethodContext) {
				MethodContext methodContext = (MethodContext) context;
				IEvaluatedType instanceType = methodContext.getInstanceType();
				if (instanceType instanceof PHPClassType) {
					className = instanceType.getTypeName();
				}
			}
		}
		result = new PHPClassType(className);

		return IGoal.NO_GOALS;
	}

	public Object produceResult() {
		return result;
	}

	public IGoal[] subGoalDone(IGoal subgoal, Object result, GoalState state) {
		return IGoal.NO_GOALS;
	}

}
