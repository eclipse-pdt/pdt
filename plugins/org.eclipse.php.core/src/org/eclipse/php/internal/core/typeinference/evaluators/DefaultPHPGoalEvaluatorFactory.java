package org.eclipse.php.internal.core.typeinference.evaluators;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.references.TypeReference;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.evaluation.types.SimpleType;
import org.eclipse.dltk.ti.IGoalEvaluatorFactory;
import org.eclipse.dltk.ti.goals.*;
import org.eclipse.php.internal.core.compiler.ast.nodes.*;
import org.eclipse.php.internal.core.typeinference.PHPClassType;
import org.eclipse.php.internal.core.typeinference.evaluators.phpdoc.PHPDocClassVariableEvaluator;
import org.eclipse.php.internal.core.typeinference.evaluators.phpdoc.PHPDocMethodReturnTypeEvaluator;
import org.eclipse.php.internal.core.typeinference.evaluators.phpdoc.VarCommentVariableEvaluator;
import org.eclipse.php.internal.core.typeinference.goals.*;
import org.eclipse.php.internal.core.typeinference.goals.phpdoc.PHPDocClassVariableGoal;
import org.eclipse.php.internal.core.typeinference.goals.phpdoc.PHPDocMethodReturnTypeGoal;
import org.eclipse.php.internal.core.typeinference.goals.phpdoc.VarCommentVariableGoal;

public class DefaultPHPGoalEvaluatorFactory implements IGoalEvaluatorFactory {

	public GoalEvaluator createEvaluator(IGoal goal) {

		Class<?> goalClass = goal.getClass();

		if (goalClass == ExpressionTypeGoal.class) {
			ExpressionTypeGoal exprGoal = (ExpressionTypeGoal) goal;
			return createExpressionEvaluator(exprGoal);
		}
		if (goalClass == VariableTypeGoal.class) {
			return new VariableTypeEvaluator(goal);
		}
		if (goalClass == VariableDeclarationGoal.class) {
			return new VariableDeclarationEvaluator(goal);
		}
		if (goalClass == MethodReturnTypeGoal.class) {
			return new MethodReturnTypeEvaluator(goal);
		}
		if (goalClass == PHPDocMethodReturnTypeGoal.class) {
			return new PHPDocMethodReturnTypeEvaluator(goal);
		}
		if (goalClass == GlobalVariableReferencesGoal.class) {
			return new GlobalVariableReferencesEvaluator(goal);
		}
		if (goalClass == ClassVariableDeclarationGoal.class) {
			return new ClassVariableDeclarationEvaluator(goal);
		}
		if (goalClass == PHPDocClassVariableGoal.class) {
			return new PHPDocClassVariableEvaluator(goal);
		}
		if (goalClass == ScalarGoal.class) {
			ScalarGoal scalarGoal = (ScalarGoal)goal;
			return new ScalarEvaluator(scalarGoal);
		}
		if (goalClass == ConstantDeclarationGoal.class) {
			return new ConstantDeclarationEvaluator(goal);
		}
		if (goalClass == VarCommentVariableGoal.class) {
			return new VarCommentVariableEvaluator(goal);
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
		if (expressionClass == PHPCallExpression.class || expressionClass == StaticMethodInvocation.class) {
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
		if (expressionClass == UnaryOperation.class) {
			return new UnaryOperationEvaluator(exprGoal);
		}
		if (expressionClass == CastExpression.class) {
			return new CastEvaluator(exprGoal);
		}
		if (expressionClass == VariableReference.class) {
			return new VariableReferenceEvaluator(exprGoal);
		}
		if (expressionClass == BackTickExpression.class || expressionClass == Quote.class) {
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
		if (expressionClass == FieldAccess.class) {
			return new FieldAccessEvaluator(exprGoal);
		}
		if (expressionClass == StaticConstantAccess.class) {
			return new StaticConstantAccessEvaluator(exprGoal);
		}
		if (expressionClass == FormalParameter.class) {
			return new FormalParameterEvaluator(exprGoal);
		}

		return null;
	}

}
