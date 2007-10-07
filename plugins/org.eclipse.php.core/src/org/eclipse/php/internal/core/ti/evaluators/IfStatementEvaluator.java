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

import org.eclipse.core.runtime.Assert;
import org.eclipse.php.internal.core.ast.nodes.ASTNode;
import org.eclipse.php.internal.core.ast.nodes.IfStatement;
import org.eclipse.php.internal.core.ti.TIUtils;
import org.eclipse.php.internal.core.ti.goals.ExpressionTypeGoal;
import org.eclipse.php.internal.core.ti.goals.IGoal;
import org.eclipse.php.internal.core.ti.types.IEvaluatedType;

public class IfStatementEvaluator extends AbstractGoalEvaluator {

	private final static int STATE_TRY_THEN = 0;
	private final static int STATE_WAITING_THEN = 1;
	private final static int STATE_TRY_ELSE = 2;
	private final static int STATE_WAITING_ELSE = 3;
	private final static int STATE_DONE = -2;

	private int state = STATE_TRY_THEN;
	private IEvaluatedType[] evaluatedTypes = new IEvaluatedType[2];
	private int index = STATE_TRY_THEN;

	public IfStatementEvaluator(ExpressionTypeGoal goal) {
		super(goal);
	}

	private IGoal produceNextSubgoal(IGoal previousGoal, Object previousResult) {
		if (state == STATE_TRY_THEN) {
			ExpressionTypeGoal typedGoal = (ExpressionTypeGoal) goal;
			IfStatement expression = (IfStatement) typedGoal.getExpression();
			ASTNode clause = expression.getTrueStatement(); // then
			if (clause == null) {
				state = STATE_TRY_ELSE;
			} else {
				state = STATE_WAITING_THEN;
				return new ExpressionTypeGoal(goal.getContext(), clause);
			}
		}
		if (state == STATE_WAITING_THEN || state == STATE_WAITING_ELSE) {
			if (previousResult != null) {
				evaluatedTypes[index++] = (IEvaluatedType) previousResult;
			}
			state = (state == STATE_WAITING_THEN ? STATE_TRY_ELSE : STATE_DONE);
		} else {
			Assert.isTrue(previousGoal == null);
		}
		if (state == STATE_TRY_ELSE) {
			ExpressionTypeGoal typedGoal = (ExpressionTypeGoal) goal;
			IfStatement expression = (IfStatement) typedGoal.getExpression();
			ASTNode clause = expression.getFalseStatement(); // else
			if (clause == null) {
				state = STATE_DONE;
			} else {
				state = STATE_WAITING_ELSE;
				return new ExpressionTypeGoal(goal.getContext(), clause);
			}
		}
		return null;
	}

	public Object produceResult() {
		return TIUtils.combineTypes(evaluatedTypes);
	}

	public IGoal[] init() {
		IGoal goal = produceNextSubgoal(null, null);
		if (goal != null) {
			return new IGoal[] { goal };
		}
		return IGoal.NO_GOALS;
	}

	public IGoal[] subGoalDone(IGoal subgoal, Object result, IGoal.State state) {
		IGoal goal = produceNextSubgoal(subgoal, result);
		if (goal != null) {
			return new IGoal[] { goal };
		}
		return IGoal.NO_GOALS;
	}
}
