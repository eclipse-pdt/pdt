/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.typeinference.evaluators;

import java.util.Arrays;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.expressions.CallExpression;
import org.eclipse.dltk.evaluation.types.UnknownType;
import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.ISourceModuleContext;
import org.eclipse.dltk.ti.InstanceContext;
import org.eclipse.dltk.ti.goals.ExpressionTypeGoal;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.dltk.ti.goals.MethodReturnTypeGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.typeinference.PHPTypeInferenceUtils;
import org.eclipse.php.internal.core.typeinference.goals.phpdoc.PHPDocMethodReturnTypeGoal;

public class MethodCallTypeEvaluator extends GoalEvaluator {

	private final static int STATE_INIT = 0;
	private final static int STATE_WAITING_RECEIVER = 1;
	private final static int STATE_GOT_RECEIVER = 2;
	private final static int STATE_WAITING_METHOD_PHPDOC = 3;
	private final static int STATE_WAITING_METHOD = 4;

	private int state = STATE_INIT;
	private IEvaluatedType receiverType;
	private IEvaluatedType result;

	public MethodCallTypeEvaluator(ExpressionTypeGoal goal) {
		super(goal);
	}

	private IGoal produceNextSubgoal(IGoal previousGoal, IEvaluatedType previousResult, GoalState goalState) {

		ExpressionTypeGoal typedGoal = (ExpressionTypeGoal) goal;
		CallExpression expression = (CallExpression) typedGoal.getExpression();

		// just starting to evaluate method, evaluate method receiver first:
		if (state == STATE_INIT) {
			ASTNode receiver = expression.getReceiver();
			if (receiver == null) {
				state = STATE_GOT_RECEIVER;
			} else {
				state = STATE_WAITING_RECEIVER;
				return new ExpressionTypeGoal(goal.getContext(), receiver);
			}
		}

		// receiver must been evaluated now, lets evaluate method return type:
		if (state == STATE_WAITING_RECEIVER) {
			receiverType = previousResult;
			if (receiverType == null) {
				return null;
			}
			state = STATE_GOT_RECEIVER;
		}

		// we've evaluated receiver, lets evaluate the method return type now (using PHP Doc first):
		if (state == STATE_GOT_RECEIVER) {
			state = STATE_WAITING_METHOD_PHPDOC;
			return new PHPDocMethodReturnTypeGoal(new InstanceContext((ISourceModuleContext) goal.getContext(), receiverType), expression.getName());
		}
		if (state == STATE_WAITING_METHOD_PHPDOC) {
			if (goalState != GoalState.PRUNED && previousResult != null && previousResult != UnknownType.INSTANCE) {
				result = previousResult;
			}
			state = STATE_WAITING_METHOD;
			return new MethodReturnTypeGoal(new InstanceContext((ISourceModuleContext) goal.getContext(), receiverType), expression.getName(), new IEvaluatedType[0] /* arguments are not interesting us */);
		}
		if (state == STATE_WAITING_METHOD) {
			if (goalState != GoalState.PRUNED && previousResult != null && previousResult != UnknownType.INSTANCE) {
				if (result != null) {
					result = PHPTypeInferenceUtils.combineTypes(Arrays.asList(new IEvaluatedType[] {result, previousResult}));
				} else {
					result = previousResult;
				}
			}
		}
		return null;
	}

	public Object produceResult() {
		return result;
	}

	public IGoal[] init() {
		IGoal goal = produceNextSubgoal(null, null, null);
		if (goal != null) {
			return new IGoal[] { goal };
		}
		return IGoal.NO_GOALS;
	}

	public IGoal[] subGoalDone(IGoal subgoal, Object result, GoalState state) {
		IGoal goal = produceNextSubgoal(subgoal, (IEvaluatedType) result, state);
		if (goal != null) {
			return new IGoal[] { goal };
		}
		return IGoal.NO_GOALS;
	}

}
