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
import org.eclipse.php.internal.core.compiler.ast.nodes.UnaryOperation;

public class UnaryOperationEvaluator extends GoalEvaluator {

	private IEvaluatedType result;

	public UnaryOperationEvaluator(IGoal goal) {
		super(goal);
	}

	public IGoal[] init() {
		ExpressionTypeGoal typedGoal = (ExpressionTypeGoal) goal;
		UnaryOperation unaryOp = (UnaryOperation) typedGoal.getExpression();
		switch (unaryOp.getOperatorType()) {
		case UnaryOperation.OP_MINUS:
		case UnaryOperation.OP_PLUS:
			result = new SimpleType(SimpleType.TYPE_NUMBER);
			break;
		case UnaryOperation.OP_TILDA:
			return new IGoal[] { new ExpressionTypeGoal(goal.getContext(),
					unaryOp.getExpr()) };
		case UnaryOperation.OP_NOT:
			result = new SimpleType(SimpleType.TYPE_BOOLEAN);
			break;
		}
		return IGoal.NO_GOALS;
	}

	public IGoal[] subGoalDone(IGoal subgoal, Object result, GoalState state) {
		this.result = (IEvaluatedType) result;
		return IGoal.NO_GOALS;
	}

	public Object produceResult() {
		return result;
	}
}
