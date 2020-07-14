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
import org.eclipse.dltk.ast.references.TypeReference;
import org.eclipse.dltk.evaluation.types.UnknownType;
import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.ISourceModuleContext;
import org.eclipse.dltk.ti.goals.ExpressionTypeGoal;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.core.compiler.ast.nodes.StaticConstantAccess;
import org.eclipse.php.internal.core.typeinference.PHPSimpleTypes;
import org.eclipse.php.internal.core.typeinference.context.TypeContext;
import org.eclipse.php.internal.core.typeinference.goals.ClassVariableDeclarationGoal;

public class StaticConstantAccessEvaluator extends GoalEvaluator {

	private IEvaluatedType evaluatedType;
	private String name;

	public StaticConstantAccessEvaluator(IGoal goal) {
		super(goal);
	}

	@Override
	public IGoal[] init() {
		ExpressionTypeGoal typedGoal = (ExpressionTypeGoal) goal;
		StaticConstantAccess expr = (StaticConstantAccess) typedGoal.getExpression();
		Expression dispatcher = expr.getDispatcher();
		if (dispatcher instanceof TypeReference) {
			TypeReference typeReference = (TypeReference) dispatcher;
			this.name = expr.getConstant().getName();
			return new IGoal[] { new ExpressionTypeGoal(typedGoal.getContext(), typeReference) };
		}
		return IGoal.NO_GOALS;
	}

	@Override
	public Object produceResult() {
		return evaluatedType;
	}

	@Override
	public IGoal[] subGoalDone(IGoal subgoal, Object result, GoalState state) {
		if (state == GoalState.PRUNED || result == null || result == UnknownType.INSTANCE) {
			evaluatedType = PHPSimpleTypes.MIXED;
			return IGoal.NO_GOALS;
		}
		if (subgoal instanceof ExpressionTypeGoal) {
			TypeContext typeContext = new TypeContext((ISourceModuleContext) goal.getContext(),
					(IEvaluatedType) result);
			return new IGoal[] { new ClassVariableDeclarationGoal(typeContext, name) };
		}
		if (result instanceof IEvaluatedType) {
			evaluatedType = (IEvaluatedType) result;
		}
		return IGoal.NO_GOALS;
	}

}
