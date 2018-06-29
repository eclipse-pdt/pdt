/*******************************************************************************
 * Copyright (c) 2009, 2017 IBM Corporation and others.
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

import java.util.Arrays;

import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.evaluation.types.UnknownType;
import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.ISourceModuleContext;
import org.eclipse.dltk.ti.goals.ExpressionTypeGoal;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.core.compiler.ast.nodes.FieldAccess;
import org.eclipse.php.core.compiler.ast.nodes.StaticFieldAccess;
import org.eclipse.php.internal.core.typeinference.PHPTypeInferenceUtils;
import org.eclipse.php.internal.core.typeinference.context.IModelCacheContext;
import org.eclipse.php.internal.core.typeinference.context.TypeContext;
import org.eclipse.php.internal.core.typeinference.goals.ClassVariableDeclarationGoal;
import org.eclipse.php.internal.core.typeinference.goals.phpdoc.PHPDocClassVariableGoal;

public class FieldAccessEvaluator extends GoalEvaluator {

	private final static int STATE_INIT = 0;
	private final static int STATE_WAITING_RECEIVER = 1;
	private final static int STATE_GOT_RECEIVER = 2;
	private final static int STATE_WAITING_FIELD_PHPDOC = 3;
	private final static int STATE_WAITING_FIELD = 4;

	private int state = STATE_INIT;
	private IEvaluatedType receiverType;
	private IEvaluatedType result;

	public FieldAccessEvaluator(IGoal goal) {
		super(goal);
	}

	// See also method MethodCallTypeEvaluator#produceNextSubgoal()
	private IGoal[] produceNextSubgoal(IGoal previousGoal, IEvaluatedType previousResult, GoalState goalState) {

		ExpressionTypeGoal typedGoal = (ExpressionTypeGoal) goal;
		Expression expression = (Expression) typedGoal.getExpression();

		Expression receiver;
		Expression field;
		if (expression instanceof FieldAccess) {
			FieldAccess fieldAccess = (FieldAccess) expression;
			receiver = fieldAccess.getDispatcher();
			field = fieldAccess.getField();
		} else if (expression instanceof StaticFieldAccess) {
			StaticFieldAccess fieldAccess = (StaticFieldAccess) expression;
			receiver = fieldAccess.getDispatcher();
			field = fieldAccess.getField();
		} else {
			return null;
		}

		String variableName;
		int offset = 0;
		if (field instanceof VariableReference) { // static access
			variableName = ((VariableReference) field).getName();
			offset = ((VariableReference) field).sourceStart();
		} else if (field instanceof SimpleReference) { // object access
			variableName = '$' + ((SimpleReference) field).getName();
			offset = ((SimpleReference) field).sourceStart();
		} else {
			return null;
		}

		// just starting to evaluate method, evaluate method receiver first:
		if (state == STATE_INIT) {
			if (receiver == null) {
				state = STATE_GOT_RECEIVER;
			} else {
				state = STATE_WAITING_RECEIVER;
				return new IGoal[] { new ExpressionTypeGoal(goal.getContext(), receiver) };
			}
		}

		// receiver must been evaluated now:
		if (state == STATE_WAITING_RECEIVER) {
			receiverType = previousResult;
			previousResult = null;
			if (receiverType == null) {
				return null;
			}
			state = STATE_GOT_RECEIVER;
		}

		// we've evaluated receiver, lets evaluate the method return type now
		// (using PHPDoc first):
		if (state == STATE_GOT_RECEIVER) {
			state = STATE_WAITING_FIELD_PHPDOC;
			TypeContext typeContext = new TypeContext((ISourceModuleContext) goal.getContext(), receiverType);
			if (goal.getContext() instanceof IModelCacheContext) {
				typeContext.setCache(((IModelCacheContext) goal.getContext()).getCache());
			}
			return new IGoal[] { new PHPDocClassVariableGoal(typeContext, variableName, offset) };
		}

		if (state == STATE_WAITING_FIELD_PHPDOC) {
			if (goalState != GoalState.PRUNED && previousResult != null && previousResult != UnknownType.INSTANCE) {
				result = previousResult;
				previousResult = null;
				// BUGS 507522 & 525480, stop when it's not a "generic"
				// simple element
				if (!PHPTypeInferenceUtils.isGenericSimple(result)) {
					return null;
				}
			}
			state = STATE_WAITING_FIELD;
			TypeContext typeContext = new TypeContext((ISourceModuleContext) goal.getContext(), receiverType);
			if (goal.getContext() instanceof IModelCacheContext) {
				typeContext.setCache(((IModelCacheContext) goal.getContext()).getCache());
			}
			return new IGoal[] { new ClassVariableDeclarationGoal(typeContext, variableName) };
		}

		if (state == STATE_WAITING_FIELD) {
			if (goalState != GoalState.PRUNED && previousResult != null && previousResult != UnknownType.INSTANCE) {
				if (result != null) {
					result = PHPTypeInferenceUtils
							.combineTypes(Arrays.asList(new IEvaluatedType[] { result, previousResult }));
				} else {
					result = previousResult;
					previousResult = null;
				}
			}
		}
		return null;
	}

	@Override
	public Object produceResult() {
		return result;
	}

	@Override
	public IGoal[] init() {
		IGoal[] goals = produceNextSubgoal(null, null, null);
		if (goals != null) {
			return goals;
		}
		return IGoal.NO_GOALS;
	}

	@Override
	public IGoal[] subGoalDone(IGoal subgoal, Object result, GoalState state) {
		IGoal[] goals = produceNextSubgoal(subgoal, (IEvaluatedType) result, state);
		if (goals != null) {
			return goals;
		}
		return IGoal.NO_GOALS;
	}

}
