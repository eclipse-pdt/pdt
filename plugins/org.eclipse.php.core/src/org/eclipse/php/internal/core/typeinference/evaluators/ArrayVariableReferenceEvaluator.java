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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.evaluation.types.AmbiguousType;
import org.eclipse.dltk.evaluation.types.MultiTypeType;
import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.goals.ExpressionTypeGoal;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.core.compiler.ast.nodes.ArrayVariableReference;
import org.eclipse.php.core.compiler.ast.nodes.ReflectionArrayVariableReference;
import org.eclipse.php.internal.core.typeinference.PHPSimpleTypes;

public class ArrayVariableReferenceEvaluator extends GoalEvaluator {

	private IEvaluatedType result;

	public ArrayVariableReferenceEvaluator(IGoal goal) {
		super(goal);
	}

	public IGoal[] init() {
		ExpressionTypeGoal typedGoal = (ExpressionTypeGoal) goal;

		ASTNode expr = typedGoal.getExpression();
		if (expr instanceof ReflectionArrayVariableReference) {
			return new IGoal[] { new ExpressionTypeGoal(goal.getContext(),
					((ReflectionArrayVariableReference) expr).getExpression()) };
		}
		return new IGoal[] { new ExpressionTypeGoal(goal.getContext(), new VariableReference(expr.sourceStart(),
				expr.sourceEnd(), ((ArrayVariableReference) expr).getName())) };
	}

	public Object produceResult() {
		return result;
	}

	public IGoal[] subGoalDone(IGoal subgoal, Object result, GoalState state) {
		if (result instanceof AmbiguousType) {
			IEvaluatedType[] possibleTypes = ((AmbiguousType) result).getPossibleTypes();
			List<IEvaluatedType> types = new ArrayList<IEvaluatedType>();
			for (IEvaluatedType type : possibleTypes) {
				if (type instanceof MultiTypeType) {
					types.addAll(((MultiTypeType) type).getTypes());
				}
			}
			result = new AmbiguousType(types.toArray(new IEvaluatedType[types.size()]));
		} else if (result instanceof MultiTypeType) {
			MultiTypeType multiTypeType = (MultiTypeType) result;
			List<IEvaluatedType> types = multiTypeType.getTypes();
			if (types.size() == 1) {
				result = types.get(0);
			} else if (types.size() == 0) {
				result = PHPSimpleTypes.MIXED;
			} else {
				result = new AmbiguousType(types.toArray(new IEvaluatedType[types.size()]));
			}
		}
		this.result = (IEvaluatedType) result;
		return IGoal.NO_GOALS;
	}

}
