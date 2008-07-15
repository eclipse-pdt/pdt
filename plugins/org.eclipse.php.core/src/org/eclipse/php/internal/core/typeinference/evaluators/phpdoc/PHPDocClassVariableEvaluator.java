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
package org.eclipse.php.internal.core.typeinference.evaluators.phpdoc;

import java.util.*;

import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.InstanceContext;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocBlock;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocTag;
import org.eclipse.php.internal.core.mixin.PHPDocField;
import org.eclipse.php.internal.core.mixin.PHPMixinModel;
import org.eclipse.php.internal.core.typeinference.PHPClassType;
import org.eclipse.php.internal.core.typeinference.PHPSimpleTypes;
import org.eclipse.php.internal.core.typeinference.PHPTypeInferenceUtils;
import org.eclipse.php.internal.core.typeinference.evaluators.AbstractPHPGoalEvaluator;
import org.eclipse.php.internal.core.typeinference.goals.phpdoc.PHPDocClassVariableGoal;

/**
 * This evaluator finds class field declartion either using "var" or in method body using field access.
 */
public class PHPDocClassVariableEvaluator extends AbstractPHPGoalEvaluator {

	private List<IEvaluatedType> evaluated = new LinkedList<IEvaluatedType>();

	public PHPDocClassVariableEvaluator(IGoal goal) {
		super(goal);
	}

	public IGoal[] init() {
		PHPDocClassVariableGoal typedGoal = (PHPDocClassVariableGoal) goal;
		InstanceContext context = (InstanceContext) typedGoal.getContext();
		String variableName = typedGoal.getVariableName();

		IType[] types = getTypes(context.getInstanceType(), context.getSourceModule());

		Set<PHPDocField> docs = new HashSet<PHPDocField>();
		for (IType type : types) {
			IModelElement[] elements = PHPMixinModel.getInstance().getVariableDoc(variableName, null, type.getElementName());
			for (IModelElement e : elements) {
				docs.add((PHPDocField) e);
			}
		}

		for (PHPDocField doc : docs) {
			PHPDocBlock docBlock = doc.getDocBlock();
			for (PHPDocTag tag : docBlock.getTags()) {
				if (tag.getTagKind() == PHPDocTag.VAR) {
					SimpleReference[] references = tag.getReferences();
					if (references.length == 1) {
						IEvaluatedType type = PHPSimpleTypes.fromString(references[0].getName());
						if (type == null) {
							type = new PHPClassType(references[0].getName());
						}
						evaluated.add(type);
					}
				}
			}
		}

		return IGoal.NO_GOALS;
	}

	public Object produceResult() {
		return PHPTypeInferenceUtils.combineTypes(evaluated);
	}

	public IGoal[] subGoalDone(IGoal subgoal, Object result, GoalState state) {
		if (state != GoalState.RECURSIVE && result != null) {
			evaluated.add((IEvaluatedType) result);
		}
		return IGoal.NO_GOALS;
	}
}
