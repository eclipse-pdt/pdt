package org.eclipse.php.internal.core.typeinference.evaluators;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.expressions.CallExpression;
import org.eclipse.dltk.ast.references.TypeReference;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.evaluation.types.SimpleType;
import org.eclipse.dltk.ti.IGoalEvaluatorFactory;
import org.eclipse.dltk.ti.goals.*;
import org.eclipse.php.internal.core.compiler.ast.nodes.*;
import org.eclipse.php.internal.core.typeinference.PHPClassType;

public class DefaultPHPGoalEvaluatorFactory implements IGoalEvaluatorFactory {

	public GoalEvaluator createEvaluator(IGoal goal) {

		if (goal instanceof ExpressionTypeGoal) {
			ExpressionTypeGoal exprGoal = (ExpressionTypeGoal) goal;
			return createExpressionEvaluator(exprGoal);
		}
		if (goal instanceof MethodReturnTypeGoal) {
			return new MethodReturnTypeEvaluator(goal);
		}

		return null;
	}

	private GoalEvaluator createExpressionEvaluator(ExpressionTypeGoal exprGoal) {

		ASTNode expression = exprGoal.getExpression();

		if (expression instanceof Assignment) {
			return new AssignmentEvaluator(exprGoal);
		}
		if (expression instanceof Scalar) {
			Scalar scalar = (Scalar) expression;
			return new ScalarEvaluator(exprGoal, scalar);
		}
		if (expression instanceof TypeReference) {
			TypeReference type = (TypeReference) expression;
			return new FixedAnswerEvaluator(exprGoal, new PHPClassType(type.getName()));
		}
		if (expression instanceof CallExpression) {
			return new MethodCallTypeEvaluator(exprGoal);
		}
		if (expression instanceof ClassInstanceCreation) {
			return new InstanceCreationEvaluator(exprGoal);
		}
		if (expression instanceof InfixExpression) {
			return new InfixExpressionEvaluator(exprGoal);
		}
		if (expression instanceof PrefixExpression) {
			return new PrefixExpressionEvaluator(exprGoal);
		}
		if (expression instanceof PostfixExpression) {
			return new PostfixExpressionEvaluator(exprGoal);
		}
		if (expression instanceof CastExpression) {
			return new CastEvaluator(exprGoal);
		}
		if (expression instanceof VariableReference) {
			return new VariableTypeEvaluator(exprGoal);
		}
		if (expression instanceof BackTickExpression) {
			return new FixedAnswerEvaluator(exprGoal, new SimpleType(SimpleType.TYPE_STRING));
		}
		if (expression instanceof CloneExpression) {
			return new CloneEvaluator(exprGoal);
		}
		if (expression instanceof InstanceOfExpression) {
			return new FixedAnswerEvaluator(exprGoal, new SimpleType(SimpleType.TYPE_BOOLEAN));
		}
		if (expression instanceof ConditionalExpression) {
			return new ConditionalExpressionEvaluator(exprGoal);
		}

		return null;
	}

}
