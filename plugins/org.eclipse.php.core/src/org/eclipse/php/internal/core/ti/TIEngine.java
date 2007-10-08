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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.Assert;
import org.eclipse.php.internal.core.ti.evaluators.DefaultGoalEvaluatorFactory;
import org.eclipse.php.internal.core.ti.evaluators.IGoalEvaluator;
import org.eclipse.php.internal.core.ti.evaluators.IGoalEvaluatorFactory;
import org.eclipse.php.internal.core.ti.goals.IGoal;
import org.eclipse.php.internal.core.ti.goals.IGoal.State;

/**
 * This is a main class for performing type inference, by evaluating goals
 * and managing their dependencies of subgoals. Type inference operation supports
 * pruning of subgoals by calling specified pruner on current goal.
 * This class isn't thread safe.
 */
public class TIEngine {

	private final LinkedList<WorkingPair> workingQueue = new LinkedList<WorkingPair>();
	private final Map<IGoal, GoalEvaluationState> goalStates = new HashMap<IGoal, GoalEvaluationState>();
	private final Map<IGoalEvaluator, EvaluatorState> evaluatorStates = new HashMap<IGoalEvaluator, EvaluatorState>();

	private final IGoalEvaluatorFactory evaluatorFactory;
	private IEvaluationStatisticsListener statisticsListener;

	public TIEngine() {
		this(new DefaultGoalEvaluatorFactory()); // default goal evaluator for PHP
	}
	
	public TIEngine(IGoalEvaluatorFactory evaluatorFactory) {
		this.evaluatorFactory = evaluatorFactory;
	}

	class EvaluatorState {
		public long timeCreated;
		public int totalSubgoals;
		public int successfulSubgoals;
		public int subgoalsLeft;
		public List<IGoal> subgoals = new ArrayList<IGoal>();

		public EvaluatorState(int subgoalsLeft) {
			this.subgoalsLeft = subgoalsLeft;
			this.timeCreated = System.currentTimeMillis();
			totalSubgoals = subgoalsLeft;
		}
	}

	class WorkingPair {
		IGoal goal;
		IGoalEvaluator creator;

		public WorkingPair(IGoal goal, IGoalEvaluator parent) {
			this.goal = goal;
			this.creator = parent;
		}
	}

	class GoalEvaluationState {
		public IGoalEvaluator creator;
		public IGoal.State state;
		public Object result;
	}

	class DefaultEvaluatorStatisticsListener implements IEvaluationStatisticsListener {
		public void evaluationStarted(IGoal rootGoal) {
		}

		public void evaluatorInitialized(IGoalEvaluator evaluator, IGoal[] subgoals, long time) {
		}

		public void evaluatorProducedResult(IGoalEvaluator evaluator, Object result, long time) {
		}

		public void evaluatorReceivedResult(IGoalEvaluator evaluator, IGoal finishedGoal, IGoal[] newSubgoals, long time) {
		}

		public void goalEvaluatorAssigned(IGoal goal, IGoalEvaluator evaluator) {
		}

		public void goalStateChanged(IGoal goal, State state, State oldState) {
		}
	}

	private void notifyEvaluator(IGoalEvaluator evaluator, IGoal subGoal) {
		long t = 0;

		GoalEvaluationState subGoalState = (GoalEvaluationState) goalStates.get(subGoal);
		Object result = subGoalState.result;
		IGoal.State state = subGoalState.state;

		if (state == IGoal.State.WAITING) {
			state = IGoal.State.RECURSIVE;
		}

		t = System.currentTimeMillis();
		IGoal[] newGoals = evaluator.subGoalDone(subGoal, result, state);
		statisticsListener.evaluatorReceivedResult(evaluator, subGoal, newGoals, System.currentTimeMillis() - t);
		if (newGoals == null) {
			newGoals = IGoal.NO_GOALS;
		}
		for (int i = 0; i < newGoals.length; i++) {
			workingQueue.add(new WorkingPair(newGoals[i], evaluator));
		}
		
		EvaluatorState ev = evaluatorStates.get(evaluator);
		ev.subgoalsLeft--;
		ev.subgoalsLeft += newGoals.length;
		ev.totalSubgoals += newGoals.length;
		ev.subgoals.addAll(Arrays.asList(newGoals));
		if (state == IGoal.State.DONE && result != null) {
			ev.successfulSubgoals++;
		}
		if (ev.subgoalsLeft == 0) {
			t = System.currentTimeMillis();
			Object newRes = evaluator.produceResult();
			statisticsListener.evaluatorProducedResult(evaluator, result, System.currentTimeMillis() - t);
			GoalEvaluationState st = (GoalEvaluationState) goalStates.get(evaluator.getGoal());
			Assert.isNotNull(st);
			st.state = IGoal.State.DONE;
			st.result = newRes;
			if (st.creator != null) {
				notifyEvaluator(st.creator, evaluator.getGoal());
			}
		}
	}

	public Object evaluateGoal(IGoal rootGoal, IPruner pruner, IEvaluationStatisticsListener statisticsListener) {
		long time = 0;
		if (statisticsListener == null) {
			statisticsListener = new DefaultEvaluatorStatisticsListener();
		}
		this.statisticsListener = statisticsListener;

		reset();
		if (pruner != null) {
			pruner.init();
		}
		workingQueue.add(new WorkingPair(rootGoal, null));
		statisticsListener.evaluationStarted(rootGoal);
		
		while (!workingQueue.isEmpty()) {
			
			WorkingPair pair = (WorkingPair) workingQueue.getFirst();
			workingQueue.removeFirst();
			GoalEvaluationState state = (GoalEvaluationState) goalStates.get(pair.goal);
			
			if (state != null && pair.creator != null) {
				notifyEvaluator(pair.creator, pair.goal);
			} else {
				boolean prune = false;
				if (pruner != null && pair.creator != null) {
					prune = pruner.prune(pair.goal);
				}
				
				if (prune) {
					storeGoal(pair.goal, IGoal.State.PRUNED, null, pair.creator);
					notifyEvaluator(pair.creator, pair.goal);
				} else {
					IGoalEvaluator evaluator = evaluatorFactory.createEvaluator(pair.goal);
					Assert.isNotNull(evaluator);
					statisticsListener.goalEvaluatorAssigned(pair.goal, evaluator);
					time = System.currentTimeMillis();
					IGoal[] newGoals = evaluator.init();
					if (newGoals == null) {
						newGoals = IGoal.NO_GOALS;
					}
					statisticsListener.evaluatorInitialized(evaluator, newGoals, System.currentTimeMillis() - time);
					
					if (newGoals.length > 0) {
						for (int i = 0; i < newGoals.length; i++) {
							workingQueue.add(new WorkingPair(newGoals[i], evaluator));
						}
						EvaluatorState evaluatorState = new EvaluatorState(newGoals.length);
						evaluatorState.subgoals.addAll(Arrays.asList(newGoals));
						evaluatorStates.put(evaluator, evaluatorState);
						storeGoal(pair.goal, IGoal.State.WAITING, null, pair.creator);
					} else {
						time = System.currentTimeMillis();
						Object result = evaluator.produceResult();
						statisticsListener.evaluatorProducedResult(evaluator, result, System.currentTimeMillis() - time);
						storeGoal(pair.goal, IGoal.State.DONE, result, pair.creator);
						if (pair.creator != null) {
							notifyEvaluator(pair.creator, pair.goal);
						}
					}
				}
			}
		}
		GoalEvaluationState s = (GoalEvaluationState) goalStates.get(rootGoal);

		Assert.isTrue(s.state == IGoal.State.DONE);
		return s.result;
	}

	private void storeGoal(IGoal goal, IGoal.State state, Object result, IGoalEvaluator creator) {
		GoalEvaluationState es = new GoalEvaluationState();
		es.result = result;
		es.state = state;
		es.creator = creator;
		goalStates.put(goal, es);

		statisticsListener.goalStateChanged(goal, state, null);
	}

	private void reset() {
		workingQueue.clear();
		goalStates.clear();
		evaluatorStates.clear();
	}
}
