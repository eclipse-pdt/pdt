/*******************************************************************************
 * Copyright (c) 2018 Michele Locati and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
