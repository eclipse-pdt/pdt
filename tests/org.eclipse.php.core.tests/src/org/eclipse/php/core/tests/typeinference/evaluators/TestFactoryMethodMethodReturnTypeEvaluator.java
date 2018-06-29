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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.dltk.evaluation.types.UnknownType;
import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.php.core.compiler.ast.nodes.NamespaceReference;
import org.eclipse.php.internal.core.typeinference.PHPClassType;
import org.eclipse.php.internal.core.typeinference.evaluators.AbstractMethodReturnTypeEvaluator;
import org.eclipse.php.internal.core.typeinference.goals.FactoryMethodMethodReturnTypeGoal;

/**
 * This Evaluator process the type/method/arguments to determine its returned
 * type(s)
 */
public class TestFactoryMethodMethodReturnTypeEvaluator extends AbstractMethodReturnTypeEvaluator {

	public TestFactoryMethodMethodReturnTypeEvaluator(IGoal goal) {
		super(goal);
	}

	@Override
	public IGoal[] init() {
		return IGoal.NO_GOALS;
	}

	@Override
	public Object produceResult() {
		FactoryMethodMethodReturnTypeGoal goal = (FactoryMethodMethodReturnTypeGoal) this.goal;
		Pattern pattern = Pattern.compile("^factoryMethodName(?<argumentIndex>\\d*)"); //$NON-NLS-1$
		Matcher matcher = pattern.matcher(goal.getMethodName());
		if (matcher.find()) {
			int argumentIndex;
			if (matcher.group("argumentIndex").length() == 0) { //$NON-NLS-1$
				argumentIndex = 0;
			} else {
				argumentIndex = Integer.parseInt(matcher.group("argumentIndex")); //$NON-NLS-1$
			}
			String[] arguments = goal.getArgNames();
			if (arguments != null && arguments.length > argumentIndex && arguments[argumentIndex] != null
					&& arguments[argumentIndex].length() > 0) {
				switch (arguments[argumentIndex]) {
				default:
					return new PHPClassType(NamespaceReference.NAMESPACE_DELIMITER.concat(arguments[argumentIndex]));
				}
			}
		}

		return UnknownType.INSTANCE;
	}

	@Override
	public IGoal[] subGoalDone(IGoal subgoal, Object result, GoalState state) {
		return IGoal.NO_GOALS;
	}

}
