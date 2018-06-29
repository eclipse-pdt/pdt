/*******************************************************************************
 * Copyright (c) 2018 Michele Locati and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Michele Locati
 *******************************************************************************/
package org.eclipse.php.core.tests.typeinference.evaluators;

import org.eclipse.dltk.ti.IGoalEvaluatorFactory;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.php.internal.core.typeinference.goals.FactoryMethodMethodReturnTypeGoal;

public class TestGoalEvaluatorFactory implements IGoalEvaluatorFactory {

	@Override
	public GoalEvaluator createEvaluator(IGoal goal) {
		if (goal instanceof FactoryMethodMethodReturnTypeGoal) {
			return new TestFactoryMethodMethodReturnTypeEvaluator(goal);
		}

		return null;
	}

}
