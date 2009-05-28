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
package org.eclipse.php.internal.core.typeinference.evaluators.phpdoc;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocBlock;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocTag;
import org.eclipse.php.internal.core.typeinference.PHPClassType;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.core.typeinference.PHPTypeInferenceUtils;
import org.eclipse.php.internal.core.typeinference.context.TypeContext;
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
		TypeContext context = (TypeContext) typedGoal.getContext();
		String variableName = typedGoal.getVariableName();

		IType[] types = PHPTypeInferenceUtils.getModelElements(context.getInstanceType(), context);
		Set<PHPDocBlock> docs = new HashSet<PHPDocBlock>();
		
		if (types != null) {
			for (IType type : types) {
				try {
					// we look in whole hiearchy
					ITypeHierarchy superHierarchy = type.newSupertypeHierarchy(null);
					IType[] superTypes = superHierarchy.getAllTypes();
					for (IType superType : superTypes) {
						IField typeField = PHPModelUtils.getTypeField(superType, variableName);
						PHPDocBlock docBlock = PHPModelUtils.getDocBlock(typeField);
						if (docBlock != null) {
							docs.add(docBlock);
						}
					}
				} catch (ModelException e) {
					if (DLTKCore.DEBUG) {
						e.printStackTrace();
					}
				}
			}
		}

		for (PHPDocBlock doc : docs) {
			for (PHPDocTag tag : doc.getTags()) {
				if (tag.getTagKind() == PHPDocTag.VAR) {
					SimpleReference[] references = tag.getReferences();
					for (SimpleReference ref : references) {
						IEvaluatedType type = PHPClassType.fromSimpleReference(ref);
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
