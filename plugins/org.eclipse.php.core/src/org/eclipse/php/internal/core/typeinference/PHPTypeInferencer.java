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
package org.eclipse.php.internal.core.typeinference;

import org.eclipse.dltk.ti.DefaultTypeInferencer;
import org.eclipse.dltk.ti.EvaluatorStatistics;
import org.eclipse.dltk.ti.TimelimitPruner;
import org.eclipse.dltk.ti.goals.AbstractTypeGoal;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.typeinference.evaluators.PHPGoalEvaluatorFactory;
import org.eclipse.php.internal.core.typeinference.goals.ClassVariableDeclarationGoal;
import org.eclipse.php.internal.core.typeinference.goals.MethodElementReturnTypeGoal;
import org.eclipse.php.internal.core.typeinference.goals.phpdoc.PHPDocClassVariableGoal;
import org.eclipse.php.internal.core.typeinference.goals.phpdoc.PHPDocMethodReturnTypeGoal;

@SuppressWarnings("deprecation")
public class PHPTypeInferencer extends DefaultTypeInferencer {

	public PHPTypeInferencer() {
		super(new PHPGoalEvaluatorFactory());
	}

	/**
	 * Evaluates PHP Doc goal
	 * 
	 * @param goal
	 * @return evaluated type
	 */
	public IEvaluatedType evaluateTypePHPDoc(AbstractTypeGoal goal, int timeout) {
		return super.evaluateType(goal, new HeavyGoalsPruner(timeout));
	}

	/**
	 * Evaluates PHP Doc goal with default timeout (3000 ms)
	 * 
	 * @param goal
	 * @return evaluated type
	 */
	public IEvaluatedType evaluateTypePHPDoc(AbstractTypeGoal goal) {
		return evaluateTypePHPDoc(goal, 3000);
	}

	public IEvaluatedType evaluateTypeHeavy(AbstractTypeGoal goal, int timeout) {
		return super.evaluateType(goal, new PHPDocGoalsPruner(timeout));
	}

	/**
	 * This class prunes all PHP goals except for PHPDoc based goals
	 */
	class HeavyGoalsPruner extends TimelimitPruner {

		public HeavyGoalsPruner(long timeLimit) {
			super(timeLimit);
		}

		public boolean prune(IGoal goal, EvaluatorStatistics stat) {
			// here are heavy goals pruned
			if (goal instanceof MethodElementReturnTypeGoal
					|| goal instanceof ClassVariableDeclarationGoal) {
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

		public boolean prune(IGoal goal, EvaluatorStatistics stat) {
			// here are PHPDoc (liteweight) goals pruned
			if (goal instanceof PHPDocMethodReturnTypeGoal
					|| goal instanceof PHPDocClassVariableGoal) {
				return true;
			}
			return super.prune(goal, stat);
		}
	}
}
