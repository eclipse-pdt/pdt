/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.core.typeinference.evaluators;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.TypeDeclaration;
import org.eclipse.dltk.ast.references.TypeReference;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.ti.IGoalEvaluatorFactory;
import org.eclipse.dltk.ti.goals.ExpressionTypeGoal;
import org.eclipse.dltk.ti.goals.FixedAnswerEvaluator;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.php.core.compiler.ast.nodes.*;
import org.eclipse.php.internal.core.typeinference.PHPSimpleTypes;
import org.eclipse.php.internal.core.typeinference.evaluators.phpdoc.PHPDocClassVariableEvaluator;
import org.eclipse.php.internal.core.typeinference.evaluators.phpdoc.PHPDocMethodReturnTypeEvaluator;
import org.eclipse.php.internal.core.typeinference.goals.*;
import org.eclipse.php.internal.core.typeinference.goals.phpdoc.PHPDocClassVariableGoal;
import org.eclipse.php.internal.core.typeinference.goals.phpdoc.PHPDocMethodReturnTypeGoal;

public class DefaultPHPGoalEvaluatorFactory implements IGoalEvaluatorFactory {

	@Override
	public GoalEvaluator createEvaluator(IGoal goal) {

		Class<?> goalClass = goal.getClass();
		if (goalClass == ExpressionTypeGoal.class) {
			ExpressionTypeGoal exprGoal = (ExpressionTypeGoal) goal;
			return createExpressionEvaluator(exprGoal);
		}
		if (goalClass == MethodElementReturnTypeGoal.class) {
			return new MethodReturnTypeEvaluator(goal);
		}
		// if (goalClass == FactoryMethodMethodReturnTypeGoal.class) {
		// return new FactoryMethodMethodReturnTypeEvaluator(goal);
		// }
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
		if (goalClass == ConstantDeclarationGoal.class) {
			return new ConstantDeclarationEvaluator(goal);
		}
		if (goalClass == ForeachStatementGoal.class) {
			return new ForeachStatementEvaluator(goal);
		}
		if (goalClass == ArrayDeclarationGoal.class) {
			return new ArrayDeclarationGoalEvaluator(goal);
		}
		if (goalClass == IteratorTypeGoal.class) {
			return new IteratorTypeGoalEvaluator(goal);
		}
		return null;
	}

	private GoalEvaluator createExpressionEvaluator(ExpressionTypeGoal exprGoal) {

		ASTNode expression = exprGoal.getExpression();
		Class<?> expressionClass = expression.getClass();

		if (expressionClass == InterfaceDeclaration.class || expressionClass == ClassDeclaration.class
				|| expressionClass == TraitDeclaration.class || expressionClass == EnumDeclaration.class) {
			return new PHPClassEvaluator(exprGoal, (TypeDeclaration) expression);
		}
		if (expressionClass == Assignment.class) {
			return new AssignmentEvaluator(exprGoal);
		}
		if (expressionClass == Scalar.class) {
			Scalar scalar = (Scalar) expression;
			return new ScalarEvaluator(exprGoal, scalar);
		}
		if (expressionClass == TypeReference.class || expressionClass == FullyQualifiedReference.class) {
			TypeReference type = (TypeReference) expression;
			return new TypeReferenceEvaluator(exprGoal, type);
		}
		if (expressionClass == TraitAliasStatement.class) {
			TraitAliasStatement tas = (TraitAliasStatement) expression;
			if (tas.getAlias().getTraitMethod() instanceof FullyQualifiedTraitMethodReference) {
				FullyQualifiedTraitMethodReference reference = (FullyQualifiedTraitMethodReference) tas.getAlias()
						.getTraitMethod();
				return new TypeReferenceEvaluator(exprGoal, reference.getClassName());
			}
		}
		if (expressionClass == PHPCallExpression.class || expressionClass == StaticMethodInvocation.class) {
			return new MethodCallTypeEvaluator(exprGoal);
		}
		if (expressionClass == ClassInstanceCreation.class) {
			return new InstanceCreationEvaluator(exprGoal);
		}

		if (expressionClass == Attribute.class) {
			return new AttributeCreationEvaluator(exprGoal);
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
			return new FixedAnswerEvaluator(exprGoal, PHPSimpleTypes.STRING);
		}
		if (expressionClass == CloneExpression.class) {
			return new CloneEvaluator(exprGoal);
		}
		if (expressionClass == InstanceOfExpression.class) {
			return new FixedAnswerEvaluator(exprGoal, PHPSimpleTypes.BOOLEAN);
		}
		if (expressionClass == ConditionalExpression.class) {
			return new ConditionalExpressionEvaluator(exprGoal);
		}
		if (expressionClass == ArrayCreation.class) {
			return new ArrayCreationEvaluator(exprGoal);
		}
		if (expressionClass == ArrayVariableReference.class
				|| expressionClass == ReflectionArrayVariableReference.class) {
			return new ArrayVariableReferenceEvaluator(exprGoal);
		}
		if (expressionClass == FieldAccess.class || expressionClass == StaticFieldAccess.class) {
			return new FieldAccessEvaluator(exprGoal);
		}
		if (expressionClass == StaticConstantAccess.class) {
			return new StaticConstantAccessEvaluator(exprGoal);
		}
		if (expressionClass == FormalParameter.class || expressionClass == FormalParameterByReference.class) {
			return new FormalParameterEvaluator(exprGoal);
		}
		if (expressionClass == CatchClause.class) {
			return new CatchClauseEvaluator(exprGoal);
		}
		if (expressionClass == AnonymousClassDeclaration.class) {
			return new PHPAnonymousClassEvaluator(exprGoal, (AnonymousClassDeclaration) expression);
		}

		return null;
	}

}
