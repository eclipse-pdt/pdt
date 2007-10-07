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

package org.eclipse.php.internal.core.ti;

import org.eclipse.php.internal.core.ti.evaluators.IGoalEvaluator;
import org.eclipse.php.internal.core.ti.goals.IGoal;

public interface IEvaluationStatisticsListener {

	/**
	 * Called only once, when root goal were posted
	 *
	 * @param rootGoal
	 */
	void evaluationStarted(IGoal rootGoal);

	/**
	 * Called, when goal state are changed (for ex. pruned)
	 *
	 * @param goal
	 * @param state
	 */
	void goalStateChanged(IGoal goal, IGoal.State state, IGoal.State oldState);

	/**
	 * Called if goal were not pruned or considered recursive, and so evaluator
	 * were assigned to it
	 *
	 * @param goal
	 * @param evaluator
	 */
	void goalEvaluatorAssigned(IGoal goal, IGoalEvaluator evaluator);

	/**
	 * Called after init() call for some goal evaluator
	 *
	 * @param evaluator
	 * @param subgoals subgoals, that this evaluator posted
	 * @param time time, that evaluator spent in init() method
	 */
	void evaluatorInitialized(IGoalEvaluator evaluator, IGoal[] subgoals, long time);

	/**
	 * Called, when evaluator accepted subgoal result, i.e. subGoalDone called
	 *
	 * @param evaluator
	 * @param subgoals
	 * @param time
	 */
	void evaluatorReceivedResult(IGoalEvaluator evaluator, IGoal finishedGoal, IGoal[] newSubgoals, long time);

	/**
	 * Called, when evaluator finally produced a result
	 *
	 * @param evaluator
	 * @param result
	 * @param time
	 */
	void evaluatorProducedResult(IGoalEvaluator evaluator, Object result, long time);
}
