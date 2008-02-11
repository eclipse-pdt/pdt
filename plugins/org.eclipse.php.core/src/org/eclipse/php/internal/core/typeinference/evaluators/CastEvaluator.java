package org.eclipse.php.internal.core.typeinference.evaluators;

import org.eclipse.dltk.evaluation.types.SimpleType;
import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.goals.ExpressionTypeGoal;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.compiler.ast.nodes.CastExpression;
import org.eclipse.php.internal.core.typeinference.PHPClassType;

public class CastEvaluator extends GoalEvaluator {

	private IEvaluatedType result = null;

	public CastEvaluator(IGoal goal) {
		super(goal);
	}

	public IGoal[] init() {
		ExpressionTypeGoal typedGoal = (ExpressionTypeGoal) goal;
		CastExpression castExpression = (CastExpression) typedGoal.getExpression();
		int operator = castExpression.getCastType();
		switch (operator) {
			case CastExpression.TYPE_INT:
			case CastExpression.TYPE_REAL:
				result = new SimpleType(SimpleType.TYPE_NUMBER);
				break;
			case CastExpression.TYPE_STRING:
				result = new SimpleType(SimpleType.TYPE_STRING);
				break;
			case CastExpression.TYPE_ARRAY:
				result = new SimpleType(SimpleType.TYPE_ARRAY);
				break;
			case CastExpression.TYPE_OBJECT:
				return new IGoal[]{new ExpressionTypeGoal(typedGoal.getContext(), castExpression.getExpr())};
			case CastExpression.TYPE_BOOL:
				result = new SimpleType(SimpleType.TYPE_BOOLEAN);
				break;
			case CastExpression.TYPE_UNSET:
				result = new SimpleType(SimpleType.TYPE_NULL);
				break;
			default:
				throw new IllegalArgumentException();
		}
		return IGoal.NO_GOALS;
	}

	public IGoal[] subGoalDone(IGoal subgoal, Object result, GoalState state) {
		if(result instanceof PHPClassType){
			this.result = (PHPClassType) result;
		} else {
			this.result = new PHPClassType("StdClass");			
		}
		return IGoal.NO_GOALS;
	}

	public Object produceResult() {
		return result;
	}
}
