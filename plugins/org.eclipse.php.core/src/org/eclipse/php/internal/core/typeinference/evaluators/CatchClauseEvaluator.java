/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
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
 *******************************************************************************/
package org.eclipse.php.internal.core.typeinference.evaluators;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dltk.ast.references.TypeReference;
import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.goals.ExpressionTypeGoal;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.core.compiler.ast.nodes.CatchClause;
import org.eclipse.php.core.compiler.ast.nodes.FullyQualifiedReference;
import org.eclipse.php.internal.core.typeinference.PHPClassType;
import org.eclipse.php.internal.core.typeinference.PHPTypeInferenceUtils;

public class CatchClauseEvaluator extends GoalEvaluator {

	private List<IEvaluatedType> result = new ArrayList<>();

	public CatchClauseEvaluator(IGoal goal) {
		super(goal);
	}

	@Override
	public IGoal[] init() {
		ExpressionTypeGoal typedGoal = (ExpressionTypeGoal) goal;
		CatchClause catchClause = (CatchClause) typedGoal.getExpression();

		List<TypeReference> classNames = catchClause.getClassNames();
		for (TypeReference className : classNames) {
			if (className != null) {
				String typeName = className.getName();
				String namespace = null;
				if (className instanceof FullyQualifiedReference) {
					FullyQualifiedReference fqn = (FullyQualifiedReference) className;
					if (fqn.getNamespace() != null) {
						namespace = fqn.getNamespace().getName();
					}
				}
				if (namespace != null) {
					result.add(new PHPClassType(namespace, typeName));
				} else {
					result.add(new PHPClassType(typeName));
				}
			}
		}
		return IGoal.NO_GOALS;
	}

	@Override
	public Object produceResult() {
		return PHPTypeInferenceUtils.combineTypes(result);
	}

	@Override
	public IGoal[] subGoalDone(IGoal subgoal, Object result, GoalState state) {
		return IGoal.NO_GOALS;
	}
}
