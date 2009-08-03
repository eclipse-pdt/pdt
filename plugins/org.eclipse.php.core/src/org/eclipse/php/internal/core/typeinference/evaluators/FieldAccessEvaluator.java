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
import org.eclipse.php.internal.core.compiler.ast.nodes.FieldAccess;
import org.eclipse.php.internal.core.compiler.ast.nodes.StaticFieldAccess;
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

	private IGoal[] produceNextSubgoal(IGoal previousGoal,
			IEvaluatedType previousResult, GoalState goalState) {

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
		if (field instanceof VariableReference) { // static access
			variableName = ((VariableReference) field).getName();
		} else if (field instanceof SimpleReference) { // object access
			variableName = '$' + ((SimpleReference) field).getName();
		} else {
			return null;
		}

		// just starting to evaluate method, evaluate method receiver first:
		if (state == STATE_INIT) {

			if (receiver == null) {
				state = STATE_GOT_RECEIVER;
			} else {
				state = STATE_WAITING_RECEIVER;
				return new IGoal[] { new ExpressionTypeGoal(goal.getContext(),
						receiver) };
			}
		}

		// receiver must been evaluated now:
		if (state == STATE_WAITING_RECEIVER) {
			receiverType = previousResult;
			if (receiverType == null) {
				return null;
			}
			state = STATE_GOT_RECEIVER;
		}

		// we've evaluated receiver, lets evaluate the method return type now
		// (using PHPDoc first):
		if (state == STATE_GOT_RECEIVER) {
			state = STATE_WAITING_FIELD_PHPDOC;
			return new IGoal[] { new PHPDocClassVariableGoal(new TypeContext(
					(ISourceModuleContext) goal.getContext(), receiverType),
					variableName) };
		}

		if (state == STATE_WAITING_FIELD_PHPDOC) {
			if (goalState != GoalState.PRUNED && previousResult != null
					&& previousResult != UnknownType.INSTANCE) {
				result = previousResult;
				return null;
			}
			state = STATE_WAITING_FIELD;
			return new IGoal[] { new ClassVariableDeclarationGoal(
					new TypeContext((ISourceModuleContext) goal.getContext(),
							receiverType), variableName) };
		}

		if (state == STATE_WAITING_FIELD) {
			if (goalState != GoalState.PRUNED && previousResult != null
					&& previousResult != UnknownType.INSTANCE) {
				result = previousResult;
			}
		}

		return null;
	}

	public Object produceResult() {
		return result;
	}

	public IGoal[] init() {
		IGoal[] goals = produceNextSubgoal(null, null, null);
		if (goals != null) {
			return goals;
		}
		return IGoal.NO_GOALS;
	}

	public IGoal[] subGoalDone(IGoal subgoal, Object result, GoalState state) {
		IGoal[] goals = produceNextSubgoal(subgoal, (IEvaluatedType) result,
				state);
		if (goals != null) {
			return goals;
		}
		return IGoal.NO_GOALS;
	}
}
