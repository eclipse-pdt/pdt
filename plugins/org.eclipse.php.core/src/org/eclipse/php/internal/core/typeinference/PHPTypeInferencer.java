/*******************************************************************************
 * Copyright (c) 2009, 2018 IBM Corporation and others.
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
 *     Michele Locati
 *******************************************************************************/
package org.eclipse.php.internal.core.typeinference;

import org.eclipse.dltk.ti.DefaultTypeInferencer;
import org.eclipse.dltk.ti.EvaluatorStatistics;
import org.eclipse.dltk.ti.TimelimitPruner;
import org.eclipse.dltk.ti.goals.AbstractTypeGoal;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.typeinference.evaluators.PHPGoalEvaluatorFactory;
import org.eclipse.php.internal.core.typeinference.goals.ClassVariableDeclarationGoal;
import org.eclipse.php.internal.core.typeinference.goals.FactoryMethodMethodReturnTypeGoal;
import org.eclipse.php.internal.core.typeinference.goals.MethodElementReturnTypeGoal;
import org.eclipse.php.internal.core.typeinference.goals.phpdoc.PHPDocClassVariableGoal;
import org.eclipse.php.internal.core.typeinference.goals.phpdoc.PHPDocMethodReturnTypeGoal;

@SuppressWarnings("deprecation")
public class PHPTypeInferencer extends DefaultTypeInferencer implements IPHPTypeInferencer {

	public PHPTypeInferencer() {
		super(new PHPGoalEvaluatorFactory());
	}

	/**
	 * Evaluates Factory Method goal
	 * 
	 * @param goal
	 * @return evaluated type
	 */
	@Override
	public IEvaluatedType evaluateTypeFactoryMethod(AbstractTypeGoal goal, int timeout) {
		return super.evaluateType(goal, new HeavyGoalsPruner(timeout));
	}

	/**
	 * Evaluates Factory Method goal with default timeout (3000 ms)
	 * 
	 * @param goal
	 * @return evaluated type
	 */
	@Override
	public IEvaluatedType evaluateTypeFactoryMethod(AbstractTypeGoal goal) {
		return evaluateTypeFactoryMethod(goal, 3000);
	}

	/**
	 * Evaluates PHP Doc goal
	 * 
	 * @param goal
	 * @return evaluated type
	 */
	@Override
	public IEvaluatedType evaluateTypePHPDoc(AbstractTypeGoal goal, int timeout) {
		return super.evaluateType(goal, new HeavyGoalsPruner(timeout));
	}

	/**
	 * Evaluates PHP Doc goal with default timeout (3000 ms)
	 * 
	 * @param goal
	 * @return evaluated type
	 */
	@Override
	public IEvaluatedType evaluateTypePHPDoc(AbstractTypeGoal goal) {
		return evaluateTypePHPDoc(goal, 3000);
	}

	@Override
	public IEvaluatedType evaluateTypeHeavy(AbstractTypeGoal goal, int timeout) {
		return super.evaluateType(goal, new PHPDocGoalsPruner(timeout));
	}

	/**
	 * This class prunes all PHP goals except for FactoryMethod/PHPDoc based goals
	 */
	class HeavyGoalsPruner extends TimelimitPruner {

		public HeavyGoalsPruner(long timeLimit) {
			super(timeLimit);
		}

		@Override
		public boolean prune(IGoal goal, EvaluatorStatistics stat) {
			// here are heavy goals pruned
			if (goal instanceof MethodElementReturnTypeGoal || goal instanceof ClassVariableDeclarationGoal) {
				return true;
			}
			return super.prune(goal, stat);
		}
	}

	/**
	 * This class prunes all FactoryMethod based goals
	 */
	class FactoryMethodGoalsPruner extends TimelimitPruner {

		public FactoryMethodGoalsPruner(long timeLimit) {
			super(timeLimit);
		}

		@Override
		public boolean prune(IGoal goal, EvaluatorStatistics stat) {
			// here are FactoryMethod (liteweight) goals pruned
			if (goal instanceof FactoryMethodMethodReturnTypeGoal) {
				return true;
			}
			return super.prune(goal, stat);
		}
	}

	/**
	 * This class prunes all PHPDoc based goals
	 */
	class PHPDocGoalsPruner extends TimelimitPruner {

		public PHPDocGoalsPruner(long timeLimit) {
			super(timeLimit);
		}

		@Override
		public boolean prune(IGoal goal, EvaluatorStatistics stat) {
			// here are PHPDoc (liteweight) goals pruned
			if (goal instanceof PHPDocMethodReturnTypeGoal || goal instanceof PHPDocClassVariableGoal) {
				return true;
			}
			return super.prune(goal, stat);
		}
	}
}
