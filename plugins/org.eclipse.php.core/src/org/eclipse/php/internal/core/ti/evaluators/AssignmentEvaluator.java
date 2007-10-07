/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/

package org.eclipse.php.internal.core.ti.evaluators;

import org.eclipse.php.internal.core.ast.nodes.ASTNode;
import org.eclipse.php.internal.core.ast.nodes.Assignment;
import org.eclipse.php.internal.core.ti.goals.ExpressionTypeGoal;
import org.eclipse.php.internal.core.ti.goals.IGoal;
import org.eclipse.php.internal.core.ti.types.IEvaluatedType;

/**
 * Evaluator for assignment goal expression
 */
public class AssignmentEvaluator extends AbstractGoalEvaluator {

	private IEvaluatedType result;

	public AssignmentEvaluator(IGoal goal) {
		super(goal);
	}

	public IGoal[] init() {
		ExpressionTypeGoal typedGoal = (ExpressionTypeGoal) goal;
		ASTNode expression = (typedGoal).getExpression();
		if (!(expression instanceof Assignment)) {
			return IGoal.NO_GOALS;
		}
		Assignment expr = (Assignment) expression;
		return new IGoal[] { new ExpressionTypeGoal(typedGoal.getContext(), expr.getValue()) };
	}

	public IGoal[] subGoalDone(IGoal subgoal, Object result, IGoal.State state) {
		this.result = (IEvaluatedType) result;
		return IGoal.NO_GOALS;
	}

	public Object produceResult() {
		return result;
	}
}
