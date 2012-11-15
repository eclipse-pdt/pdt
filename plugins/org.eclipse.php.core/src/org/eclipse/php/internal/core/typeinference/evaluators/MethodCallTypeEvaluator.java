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

import java.util.Arrays;
import java.util.List;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.expressions.CallArgumentsList;
import org.eclipse.dltk.ast.expressions.CallExpression;
import org.eclipse.dltk.evaluation.types.UnknownType;
import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.goals.ExpressionTypeGoal;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.compiler.ast.nodes.Scalar;
import org.eclipse.php.internal.core.compiler.ast.parser.ASTUtils;
import org.eclipse.php.internal.core.typeinference.PHPTypeInferenceUtils;
import org.eclipse.php.internal.core.typeinference.goals.MethodElementReturnTypeGoal;
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

	private IGoal produceNextSubgoal(IGoal previousGoal,
			IEvaluatedType previousResult, GoalState goalState) {

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
			previousResult = null;
			if (receiverType == null) {
				return null;
			}
			state = STATE_GOT_RECEIVER;
		}

		// we've evaluated receiver, lets evaluate the method return type now
		// (using PHP Doc first):
		if (state == STATE_GOT_RECEIVER) {
			state = STATE_WAITING_METHOD_PHPDOC;
			return new PHPDocMethodReturnTypeGoal(typedGoal.getContext(),
					receiverType, expression.getName());
		}

		// PHPDoc logic is done, start evaluating 'return' statements here:
		if (state == STATE_WAITING_METHOD_PHPDOC) {
			if (goalState != GoalState.PRUNED && previousResult != null
					&& previousResult != UnknownType.INSTANCE) {
				result = previousResult;
				previousResult = null;
			}
			state = STATE_WAITING_METHOD;
			CallArgumentsList args = expression.getArgs();
			String[] argNames = null;
			if (args != null && args.getChilds() != null) {
				List childs = args.getChilds();
				int i = 0;
				argNames = new String[childs.size()];
				for (Object o : childs) {
					if (o instanceof Scalar) {
						Scalar arg = (Scalar) o;
						argNames[i] = ASTUtils.stripQuotes(arg.getValue());
					}
					i++;
				}
			}
			return new MethodElementReturnTypeGoal(typedGoal.getContext(),
					receiverType, expression.getName(), argNames);
		}

		if (state == STATE_WAITING_METHOD) {
			if (goalState != GoalState.PRUNED && previousResult != null
					&& previousResult != UnknownType.INSTANCE) {
				if (result != null) {
					result = PHPTypeInferenceUtils.combineTypes(Arrays
							.asList(new IEvaluatedType[] { result,
									previousResult }));
				} else {
					result = previousResult;
					previousResult = null;
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
