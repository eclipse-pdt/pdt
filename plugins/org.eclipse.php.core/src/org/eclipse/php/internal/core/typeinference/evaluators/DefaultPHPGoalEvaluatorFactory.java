package org.eclipse.php.internal.core.typeinference.evaluators;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.references.TypeReference;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.evaluation.types.SimpleType;
import org.eclipse.dltk.ti.IGoalEvaluatorFactory;
import org.eclipse.dltk.ti.goals.*;
import org.eclipse.php.internal.core.compiler.ast.nodes.*;
import org.eclipse.php.internal.core.typeinference.PHPClassType;
import org.eclipse.php.internal.core.typeinference.goals.GlobalVariableReferencesGoal;
import org.eclipse.php.internal.core.typeinference.goals.VariableTypeGoal;

public class DefaultPHPGoalEvaluatorFactory implements IGoalEvaluatorFactory {

	public GoalEvaluator createEvaluator(IGoal goal) {

		if (goal instanceof ExpressionTypeGoal) {
			ExpressionTypeGoal exprGoal = (ExpressionTypeGoal) goal;
			return createExpressionEvaluator(exprGoal);
		}
		if (goal instanceof MethodReturnTypeGoal) {
			return new MethodReturnTypeEvaluator(goal);
		}
		if (goal instanceof VariableTypeGoal) {
			return new VariableTypeEvaluator(goal);
		}
		if (goal instanceof GlobalVariableReferencesGoal) {
			return new GlobalVariableReferencesEvaluator(goal);
		}

		return null;
	}

	private GoalEvaluator createExpressionEvaluator(ExpressionTypeGoal exprGoal) {

		ASTNode expression = exprGoal.getExpression();
		Class<?> expressionClass = expression.getClass();

		if (expressionClass == Assignment.class) {
			return new AssignmentEvaluator(exprGoal);
		}
		if (expressionClass == Scalar.class) {
			Scalar scalar = (Scalar) expression;
			return new ScalarEvaluator(exprGoal, scalar);
		}
		if (expressionClass == TypeReference.class) {
			TypeReference type = (TypeReference) expression;
			return new FixedAnswerEvaluator(exprGoal, new PHPClassType(type.getName()));
		}
		if (expressionClass == PHPCallExpression.class) {
			return new MethodCallTypeEvaluator(exprGoal);
		}
		if (expressionClass == ClassInstanceCreation.class) {
			return new InstanceCreationEvaluator(exprGoal);
		}
		if (expressionClass == InfixExpression.class) {
			return new InfixExpressionEvaluator(exprGoal);
		}
		if (expressionClass == PrefixExpression.class) {
			return new PrefixExpressionEvaluator(exprGoal);
		}
		if (expressionClass == PostfixExpression.class) {
			return new PostfixExpressionEvaluator(exprGoal);
		}
		if (expressionClass == CastExpression.class) {
			return new CastEvaluator(exprGoal);
		}
		if (expressionClass == VariableReference.class) {
			return new VariableReferenceEvaluator(exprGoal);
		}
		if (expressionClass == BackTickExpression.class) {
			return new FixedAnswerEvaluator(exprGoal, new SimpleType(SimpleType.TYPE_STRING));
		}
		if (expressionClass == CloneExpression.class) {
			return new CloneEvaluator(exprGoal);
		}
		if (expressionClass == InstanceOfExpression.class) {
			return new FixedAnswerEvaluator(exprGoal, new SimpleType(SimpleType.TYPE_BOOLEAN));
		}
		if (expressionClass == ConditionalExpression.class) {
			return new ConditionalExpressionEvaluator(exprGoal);
		}
		if (expressionClass == ArrayCreation.class) {
			return new ArrayCreationEvaluator(exprGoal);
		}
		if (expressionClass == ArrayVariableReference.class) {
			return new ArrayVariableReferenceEvaluator(exprGoal);
		}

		return null;
	}

}
