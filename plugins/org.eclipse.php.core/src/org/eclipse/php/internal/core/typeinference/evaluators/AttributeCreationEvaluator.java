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

import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.evaluation.types.UnknownType;
import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.goals.ExpressionTypeGoal;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.core.compiler.ast.nodes.Attribute;

public class AttributeCreationEvaluator extends GoalEvaluator {

	private IEvaluatedType result;

	public AttributeCreationEvaluator(IGoal goal) {
		super(goal);
	}

	@Override
	public IGoal[] init() {
		ExpressionTypeGoal typedGoal = (ExpressionTypeGoal) goal;
		Attribute expression = (Attribute) typedGoal.getExpression();

		Expression className = expression.getName();
		if (className != null) {
			return new IGoal[] { new ExpressionTypeGoal(goal.getContext(), className) };
		}
		result = UnknownType.INSTANCE;
		return IGoal.NO_GOALS;
	}

	@Override
	public Object produceResult() {
		return result;
	}

	@Override
	public IGoal[] subGoalDone(IGoal subgoal, Object result, GoalState state) {
		this.result = (IEvaluatedType) result;
		return IGoal.NO_GOALS;
	}

}
