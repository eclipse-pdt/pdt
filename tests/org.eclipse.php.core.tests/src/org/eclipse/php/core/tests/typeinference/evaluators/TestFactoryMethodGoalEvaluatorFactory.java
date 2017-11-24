/*******************************************************************************
 * Copyright (c) 2017 Michele Locati and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Michele Locati <michele@locati.it> - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.core.tests.typeinference.evaluators;

import org.eclipse.dltk.ti.IGoalEvaluatorFactory;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;

public class TestFactoryMethodGoalEvaluatorFactory implements IGoalEvaluatorFactory {

	public GoalEvaluator createEvaluator(IGoal goal) {
		return new TestFactoryMethodReturnTypeEvaluator(goal);
	}

}
