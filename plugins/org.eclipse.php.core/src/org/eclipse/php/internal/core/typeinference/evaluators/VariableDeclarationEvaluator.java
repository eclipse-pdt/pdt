/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.typeinference.evaluators;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.evaluation.types.UnknownType;
import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.IContext;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.compiler.ast.nodes.Assignment;
import org.eclipse.php.internal.core.typeinference.goals.VariableDeclarationGoal;
import org.eclipse.php.internal.core.typeinference.goals.VariableTypeGoal;
import org.eclipse.php.internal.core.typeinference.goals.phpdoc.VarCommentVariableGoal;

/**
 * Finds variable type from the given declaration by delegating to {@link VariableTypeGoal} or to {@link VarCommentVariableGoal}
 */
public class VariableDeclarationEvaluator extends GoalEvaluator {

	private IEvaluatedType result;

	public VariableDeclarationEvaluator(IGoal goal) {
		super(goal);
	}

	public IGoal[] init() {
		VariableDeclarationGoal typedGoal = (VariableDeclarationGoal) goal;
		IContext context = typedGoal.getContext();
		ASTNode declaration = typedGoal.getDeclaration();

		List<IGoal> subGoals = new LinkedList<IGoal>();
		if (declaration instanceof Assignment) {
			Assignment assignment = (Assignment) declaration;
			subGoals.add(new VarCommentVariableGoal(context, assignment.getVarComment(), assignment.getVariable()));
		}
		subGoals.add(new VariableTypeGoal(context, declaration));

		return subGoals.toArray(new IGoal[subGoals.size()]);
	}

	public Object produceResult() {
		return result;
	}

	public IGoal[] subGoalDone(IGoal subgoal, Object result, GoalState state) {
		if (state != GoalState.PRUNED && result != null && result != UnknownType.INSTANCE) {
			this.result = (IEvaluatedType) result;
		}
		return IGoal.NO_GOALS;
	}
}
