/*******************************************************************************
 * Copyright (c) 2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *     Dawid Pakuła - cached inferencer
 *******************************************************************************/
package org.eclipse.php.internal.core.typeinference;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.dltk.ti.*;
import org.eclipse.dltk.ti.goals.*;
import org.eclipse.dltk.ti.statistics.IEvaluationStatisticsRequestor;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.typeinference.evaluators.PHPGoalEvaluatorFactory;
import org.eclipse.php.internal.core.typeinference.goals.ClassVariableDeclarationGoal;
import org.eclipse.php.internal.core.typeinference.goals.MethodElementReturnTypeGoal;
import org.eclipse.php.internal.core.typeinference.goals.phpdoc.PHPDocClassVariableGoal;
import org.eclipse.php.internal.core.typeinference.goals.phpdoc.PHPDocMethodReturnTypeGoal;

/**
 * This class allow persist goal results between inference sessions
 * 
 * @author Dawid zulus Pakula <zulus@w3des.net>
 */
public class PHPCachedTypeInferencer implements IPHPTypeInferencer {
	final private GoalEngine engine;
	final private Map<IGoal, Result> cache = new ConcurrentHashMap<IGoal, Result>();
	final private IEvaluationStatisticsRequestor stat;

	private class Result {
		final Object result;

		public Result(Object result) {
			this.result = result;
		}
	}

	private class CachedEvaluatorFactory extends PHPGoalEvaluatorFactory {
		@Override
		public GoalEvaluator createEvaluator(IGoal goal) {
			if (cache.containsKey(goal)) {
				return new FakeGoalEvaluator(goal, cache.get(goal));
			}

			GoalEvaluator eval = super.createEvaluator(goal);
			if (eval == null) { // std evaluator
				if (goal.getClass() == FieldReferencesGoal.class) {
					return new FieldReferencesGoalEvaluator(goal);
				} else if (goal.getClass() == MethodCallsGoal.class) {
					return new MethodCallsGoalEvaluator(goal);
				}

				return new NullGoalEvaluator(goal);
			} else {
				return eval;
			}
		}
	}

	private class FakeGoalEvaluator extends GoalEvaluator {
		final Result result;

		public FakeGoalEvaluator(IGoal goal, Result result) {
			super(goal);
			this.result = result;
		}

		@Override
		public IGoal[] init() {
			return IGoal.NO_GOALS;
		}

		@Override
		public IGoal[] subGoalDone(IGoal subgoal, Object result, GoalState state) {
			return IGoal.NO_GOALS;
		}

		@Override
		public Object produceResult() {
			return result.result;
		}
	}

	private class StatRequestor implements IEvaluationStatisticsRequestor {

		@Override
		public void evaluationStarted(IGoal rootGoal) {

		}

		@Override
		public void goalStateChanged(IGoal goal, GoalState state, GoalState oldState) {

		}

		@Override
		public void goalEvaluatorAssigned(IGoal goal, GoalEvaluator evaluator) {
		}

		@Override
		public void evaluatorInitialized(GoalEvaluator evaluator, IGoal[] subgoals, long time) {
		}

		@Override
		public void evaluatorReceivedResult(GoalEvaluator evaluator, IGoal finishedGoal, IGoal[] newSubgoals,
				long time) {
		}

		@Override
		public void evaluatorProducedResult(GoalEvaluator evaluator, Object result, long time) {
			if (!(evaluator instanceof FakeGoalEvaluator)) {
				cache.put(evaluator.getGoal(), new Result(result));
			}
		}

	}

	public PHPCachedTypeInferencer() {
		engine = new GoalEngine(new CachedEvaluatorFactory());
		stat = new StatRequestor();
	}

	@Override
	public IEvaluatedType evaluateTypePHPDoc(AbstractTypeGoal goal, int timeout) {
		return evaluateType(goal, new PHPDocGoalsPruner(timeout));
	}

	@Override
	public IEvaluatedType evaluateTypePHPDoc(AbstractTypeGoal goal) {
		return evaluateTypePHPDoc(goal, 3000);
	}

	@Override
	public IEvaluatedType evaluateTypeHeavy(AbstractTypeGoal goal, int timeout) {
		return evaluateType(goal, new PHPDocGoalsPruner(timeout));
	}

	@Override
	public IEvaluatedType evaluateType(AbstractTypeGoal goal) {
		return evaluateType(goal, null);
	}

	@Override
	public IEvaluatedType evaluateType(AbstractTypeGoal goal, int timeLimit) {
		return evaluateType(goal, new TimelimitPruner(timeLimit));
	}

	@Override
	public IEvaluatedType evaluateType(AbstractTypeGoal goal, IPruner pruner) {
		if (!cache.containsKey(goal)) {
			synchronized (engine) {
				cache.put(goal, new Result(engine.evaluateGoal(goal, pruner, stat)));
			}
		}

		return (IEvaluatedType) cache.get(goal).result;
	}

	/**
	 * This class prunes all PHP goals except for PHPDoc based goals
	 */
	static class HeavyGoalsPruner extends TimelimitPruner {

		public HeavyGoalsPruner(long timeLimit) {
			super(timeLimit);
		}

		public boolean prune(IGoal goal, EvaluatorStatistics stat) {
			// here are heavy goals pruned
			if (goal instanceof MethodElementReturnTypeGoal || goal instanceof ClassVariableDeclarationGoal) {
				return true;
			}
			return super.prune(goal, stat);
		}
	}

	/**
	 * This class prunes all PHPDoc based goals
	 */
	static class PHPDocGoalsPruner extends TimelimitPruner {

		public PHPDocGoalsPruner(long timeLimit) {
			super(timeLimit);
		}

		public boolean prune(IGoal goal, EvaluatorStatistics stat) {
			// here are PHPDoc (liteweight) goals pruned
			if (goal instanceof PHPDocMethodReturnTypeGoal || goal instanceof PHPDocClassVariableGoal) {
				return true;
			}
			return super.prune(goal, stat);
		}
	}

}
