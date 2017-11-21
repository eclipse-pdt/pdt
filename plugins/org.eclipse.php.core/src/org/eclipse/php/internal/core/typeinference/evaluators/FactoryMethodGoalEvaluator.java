/*******************************************************************************
 * Copyright (c) 2015, 2016 IBM Corporation and others.
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

import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.IGoalEvaluatorFactory;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;

public class FactoryMethodGoalEvaluator extends GoalEvaluator {

	private final IGoalEvaluatorFactory[] factoryMethodFactories;
	private int currentFactoryIndex = -1;
	private GoalEvaluator currentEvaluator = null;
	private IEvaluatedType result = null;

	public FactoryMethodGoalEvaluator(IGoal goal, final IGoalEvaluatorFactory[] factoryMethodFactories) {
		super(goal);
		this.factoryMethodFactories = factoryMethodFactories;
	}

	@Override
	public IGoal[] init() {
		this.currentFactoryIndex = -1;
		this.currentEvaluator = null;
		this.result = null;
		return this.skipToNextFactory();
	}

	@Override
	public IGoal[] subGoalDone(IGoal subgoal, Object result, GoalState state) {
		IGoal[] evaluatorGoals = this.currentEvaluator.subGoalDone(subgoal, result, state);
		if (evaluatorGoals != null && evaluatorGoals.length > 0) {
			return evaluatorGoals;
		}
		if (!this.getResultFromCurrentEvaluator()) {
			return this.skipToNextFactory();
		}
		return IGoal.NO_GOALS;
	}

	@Override
	public Object produceResult() {
		return this.result;
	}

	private IGoal[] skipToNextFactory() {
		this.currentEvaluator = null;
		this.currentFactoryIndex++;
		if (this.currentFactoryIndex >= this.factoryMethodFactories.length) {
			return IGoal.NO_GOALS;
		}
		this.currentEvaluator = this.factoryMethodFactories[this.currentFactoryIndex].createEvaluator(this.goal);
		if (this.currentEvaluator == null) {
			return this.skipToNextFactory();
		}
		IGoal[] initGoals = this.currentEvaluator.init();
		if (initGoals != null && initGoals.length > 0) {
			return initGoals;
		}
		if (!this.getResultFromCurrentEvaluator()) {
			return this.skipToNextFactory();
		}
		return IGoal.NO_GOALS;
	}

	private boolean getResultFromCurrentEvaluator() {
		Object evaluatorResult = this.currentEvaluator.produceResult();
		if (!(evaluatorResult instanceof IEvaluatedType)) {
			return false;
		}
		this.result = (IEvaluatedType) evaluatorResult;
		return true;
	}
}
