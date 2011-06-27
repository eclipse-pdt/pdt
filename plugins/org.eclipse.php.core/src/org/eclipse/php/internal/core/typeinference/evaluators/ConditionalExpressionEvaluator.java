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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.goals.ExpressionTypeGoal;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.compiler.ast.nodes.ConditionalExpression;
import org.eclipse.php.internal.core.typeinference.PHPTypeInferenceUtils;

public class ConditionalExpressionEvaluator extends GoalEvaluator {

	private final List<IEvaluatedType> evaluated = new LinkedList<IEvaluatedType>();

	public ConditionalExpressionEvaluator(IGoal goal) {
		super(goal);
	}

	public Object produceResult() {
		if (!evaluated.isEmpty()) {
			return PHPTypeInferenceUtils.combineTypes(evaluated);
		}
		return null;
	}

	public IGoal[] init() {
		ExpressionTypeGoal typedGoal = (ExpressionTypeGoal) goal;
		ConditionalExpression conditionalExpression = (ConditionalExpression) typedGoal
				.getExpression();
		// https://bugs.eclipse.org/bugs/show_bug.cgi?id=339405
		List<IGoal> result = new ArrayList<IGoal>();
		if (conditionalExpression.getIfTrue() != null) {
			result.add(new ExpressionTypeGoal(goal.getContext(),
					conditionalExpression.getIfTrue()));
		}
		if (conditionalExpression.getIfFalse() != null) {
			result.add(new ExpressionTypeGoal(goal.getContext(),
					conditionalExpression.getIfFalse()));
		}
		return result.toArray(new IGoal[result.size()]);
	}

	public IGoal[] subGoalDone(IGoal subgoal, Object result, GoalState state) {
		if (state != GoalState.RECURSIVE && result != null) {
			evaluated.add((IEvaluatedType) result);
		}
		return IGoal.NO_GOALS;
	}

}
