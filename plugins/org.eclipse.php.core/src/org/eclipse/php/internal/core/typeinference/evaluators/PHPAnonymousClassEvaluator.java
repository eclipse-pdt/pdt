/*******************************************************************************
 * Copyright (c) 2009, 2019 IBM Corporation and others.
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
 *     Dawid Pakuła
 *******************************************************************************/
package org.eclipse.php.internal.core.typeinference.evaluators;

import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.ISourceModuleContext;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.php.core.compiler.ast.nodes.AnonymousClassDeclaration;
import org.eclipse.php.internal.core.typeinference.AnonymousClassInstanceType;

/**
 * Light evaluator for class declaration
 */
public class PHPAnonymousClassEvaluator extends AbstractPHPGoalEvaluator {

	private AnonymousClassInstanceType result = null;

	public PHPAnonymousClassEvaluator(IGoal goal, AnonymousClassDeclaration declare) {
		super(goal);
		if (goal.getContext() instanceof ISourceModuleContext) {
			result = new AnonymousClassInstanceType(((ISourceModuleContext) goal.getContext()).getSourceModule(),
					declare);
		}

	}

	@Override
	public IGoal[] init() {
		return null;
	}

	@Override
	public Object produceResult() {
		return result;
	}

	@Override
	public IGoal[] subGoalDone(IGoal subgoal, Object result, GoalState state) {
		return null;
	}

}
