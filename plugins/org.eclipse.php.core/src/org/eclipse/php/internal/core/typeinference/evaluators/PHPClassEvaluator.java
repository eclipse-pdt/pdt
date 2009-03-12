/*******************************************************************************
 * Copyright (c) 2005, 2009 Zend Technologies.
 * All rights reserved. This program and the accompanying materials
 * are the copyright of Zend Technologies and is protected under
 * copyright laws of the United States.
 * You must not copy, adapt or redistribute this document for 
 * any use. 
 *
 *******************************************************************************/
package org.eclipse.php.internal.core.typeinference.evaluators;

import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.php.internal.core.typeinference.PHPClassType;

/**
 * Light evaluator for class declaration 
 */
public class PHPClassEvaluator extends AbstractPHPGoalEvaluator {

	private PHPClassType result;

	public PHPClassEvaluator(IGoal goal, org.eclipse.php.internal.core.compiler.ast.nodes.ClassDeclaration declare) {
		super(goal);
		result = new PHPClassType(declare.getName());

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