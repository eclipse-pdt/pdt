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
package org.eclipse.php.internal.core.typeinference.evaluators;

import org.eclipse.dltk.ast.declarations.TypeDeclaration;
import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.php.internal.core.compiler.ast.nodes.TraitDeclaration;
import org.eclipse.php.internal.core.typeinference.PHPClassType;

/**
 * Light evaluator for class declaration
 */
public class PHPClassEvaluator extends AbstractPHPGoalEvaluator {

	private PHPClassType result;

	public PHPClassEvaluator(IGoal goal, TypeDeclaration declare) {
		super(goal);
		Class<?> expressionClass = declare.getClass();
		if (expressionClass == TraitDeclaration.class) {
			result = new PHPTraitType(declare.getName());
		} else {
			result = new PHPClassType(declare.getName());
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