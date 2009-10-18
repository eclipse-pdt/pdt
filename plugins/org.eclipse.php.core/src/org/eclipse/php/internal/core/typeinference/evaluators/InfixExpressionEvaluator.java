/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.core.typeinference.evaluators;

import org.eclipse.dltk.evaluation.types.SimpleType;
import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.goals.ExpressionTypeGoal;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.compiler.ast.nodes.InfixExpression;

public class InfixExpressionEvaluator extends GoalEvaluator {

	private IEvaluatedType result;

	public InfixExpressionEvaluator(IGoal goal) {
		super(goal);
	}

	public IGoal[] init() {
		ExpressionTypeGoal typedGoal = (ExpressionTypeGoal) goal;
		InfixExpression infixExpression = (InfixExpression) typedGoal
				.getExpression();
		int operator = infixExpression.getOperatorType();
		switch (operator) {
		case InfixExpression.OP_IS_IDENTICAL:
		case InfixExpression.OP_IS_NOT_IDENTICAL:
		case InfixExpression.OP_IS_EQUAL:
		case InfixExpression.OP_IS_NOT_EQUAL:
		case InfixExpression.OP_RGREATER:
		case InfixExpression.OP_IS_SMALLER_OR_EQUAL:
		case InfixExpression.OP_LGREATER:
		case InfixExpression.OP_IS_GREATER_OR_EQUAL:
		case InfixExpression.OP_BOOL_OR:
		case InfixExpression.OP_BOOL_AND:
		case InfixExpression.OP_STRING_OR:
		case InfixExpression.OP_STRING_AND:
		case InfixExpression.OP_STRING_XOR:
		case InfixExpression.OP_OR:
		case InfixExpression.OP_AND:
		case InfixExpression.OP_XOR:
			result = new SimpleType(SimpleType.TYPE_BOOLEAN);
			break;
		case InfixExpression.OP_CONCAT:
			result = new SimpleType(SimpleType.TYPE_STRING);
			break;
		case InfixExpression.OP_PLUS:
		case InfixExpression.OP_MINUS:
		case InfixExpression.OP_MUL:
		case InfixExpression.OP_DIV:
		case InfixExpression.OP_MOD:
		case InfixExpression.OP_SL:
		case InfixExpression.OP_SR:
			result = new SimpleType(SimpleType.TYPE_NUMBER);
			break;
		default:
			throw new IllegalArgumentException();
		}
		return IGoal.NO_GOALS;
	}

	public IGoal[] subGoalDone(IGoal subgoal, Object result, GoalState state) {
		return IGoal.NO_GOALS;
	}

	public Object produceResult() {
		return result;
	}
}
