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

import org.eclipse.php.internal.core.ti.goals.IGoal;


public interface IGoalEvaluator {
	
	/**
	 * Returns current goal
	 * @return goal
	 */
	public IGoal getGoal();

	/**
	 * Called first time to fetch primary subgoals.
	 *
	 * @return array of required subgoals or <code>IGoal.NO_GOALS</code>
	 */
	public IGoal[] init();

	/**
	 * Called when some subgoal are done.
	 *
	 * @param subgoal completed subgoal
	 * @param result result of that subgoal
	 * @param state final state of subgoal (DONE, WAITING, PRUNED or RECURSION)
	 * @return array of new required subgoals or <code>IGoal.NO_GOALS</code>
	 */
	public IGoal[] subGoalDone(IGoal subgoal, Object result, IGoal.State state);

	/**
	 * Called when all posted subgoals are done
	 *
	 * @return result of evaluation this goal
	 */
	public Object produceResult();

}
