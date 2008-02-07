package org.eclipse.php.internal.core.typeinference.evaluators;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.evaluation.types.SimpleType;
import org.eclipse.dltk.ti.IGoalEvaluatorFactory;
import org.eclipse.dltk.ti.goals.*;
import org.eclipse.php.internal.core.compiler.ast.nodes.Assignment;
import org.eclipse.php.internal.core.compiler.ast.nodes.Scalar;

public class DefaultPHPGoalEvaluatorFactory implements IGoalEvaluatorFactory {

	public GoalEvaluator createEvaluator(IGoal goal) {
		
		if (goal instanceof ExpressionTypeGoal) {
			ExpressionTypeGoal exprGoal = (ExpressionTypeGoal) goal;
			return createExpressionEvaluator(exprGoal);
		}

		return null;	
	}

	private GoalEvaluator createExpressionEvaluator(ExpressionTypeGoal exprGoal) {
		ASTNode expression = exprGoal.getExpression();
		if(expression instanceof Assignment){
			return new AssignmentEvaluator(exprGoal);
		}
		if(expression instanceof Scalar){
			Scalar scalar = (Scalar)expression;
			int scalarType = scalar.getScalarType();
			int simpleType = SimpleType.TYPE_NONE;
			switch (scalarType) {
				case Scalar.TYPE_INT:
				case Scalar.TYPE_REAL:
					simpleType = SimpleType.TYPE_NUMBER;
					break;
				case Scalar.TYPE_STRING:
				case Scalar.TYPE_SYSTEM:
					simpleType = SimpleType.TYPE_STRING;
					break;
			}
			return new ScalarEvaluator(exprGoal, new SimpleType(simpleType));
		}
		
		return null;
	}

}
