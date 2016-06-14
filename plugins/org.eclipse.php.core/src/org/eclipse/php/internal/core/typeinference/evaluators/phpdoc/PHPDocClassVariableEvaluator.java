/*******************************************************************************
 * Copyright (c) 2009, 2015, 2016 IBM Corporation and others.
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

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.Constants;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.typeinference.IModelAccessCache;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.core.typeinference.PHPTypeInferenceUtils;
import org.eclipse.php.internal.core.typeinference.context.TypeContext;
import org.eclipse.php.internal.core.typeinference.evaluators.AbstractPHPGoalEvaluator;
import org.eclipse.php.internal.core.typeinference.evaluators.PHPEvaluationUtils;
import org.eclipse.php.internal.core.typeinference.goals.phpdoc.PHPDocClassVariableGoal;

/**
 * This evaluator finds class field declaration either using "var" or in method
 * body using field access.
 */
public class PHPDocClassVariableEvaluator extends AbstractPHPGoalEvaluator {

	private List<IEvaluatedType> evaluated = new LinkedList<IEvaluatedType>();

	public PHPDocClassVariableEvaluator(IGoal goal) {
		super(goal);
	}

	@Override
	public IGoal[] init() {
		PHPDocClassVariableGoal typedGoal = (PHPDocClassVariableGoal) goal;
		TypeContext context = (TypeContext) typedGoal.getContext();
		String variableName = typedGoal.getVariableName();
		int offset = typedGoal.getOffset();

		IModelAccessCache cache = context.getCache();
		IType[] types = PHPTypeInferenceUtils.getModelElements(context.getInstanceType(), context, offset, cache);

		// remove array index from field name
		if (variableName.endsWith("]")) { //$NON-NLS-1$
			int index = variableName.indexOf("["); //$NON-NLS-1$
			if (index != -1) {
				variableName = variableName.substring(0, index);
			}
		}
		if (types == null) {
			return IGoal.NO_GOALS;
		}

		for (IType type : types) {
			try {
				// we look in whole hiearchy
				ITypeHierarchy superHierarchy;
				if (cache != null) {
					superHierarchy = cache.getSuperTypeHierarchy(type, null);
				} else {
					superHierarchy = type.newSupertypeHierarchy(null);
				}
				IType[] superTypes = superHierarchy.getAllTypes();
				for (IType superType : superTypes) {
					IField[] typeField = PHPModelUtils.getTypeField(superType, variableName, true);
					if (typeField.length > 0 && typeField[0].exists()) {
						addFieldType(typeField[0]);
					}
				}
			} catch (ModelException e) {
				Logger.logException(e);
			}
		}

		return IGoal.NO_GOALS;
	}

	private void addFieldType(IField typeField) throws ModelException {
		String fieldType = typeField.getType();
		if (fieldType == null) {
			return;
		}
		String[] typeNames = StringUtils.split(fieldType, Constants.TYPE_SEPERATOR_CHAR);
		IType currentNamespace = PHPModelUtils.getCurrentNamespace(typeField);
		IModelElement space = currentNamespace != null ? currentNamespace : typeField.getSourceModule();
		evaluated.addAll(Arrays.asList(
				PHPEvaluationUtils.evaluatePHPDocType(typeNames, space, typeField.getSourceRange().getOffset(), null)));
	}

	@Override
	public Object produceResult() {
		return PHPTypeInferenceUtils.combineTypes(evaluated);
	}

	@Override
	public IGoal[] subGoalDone(IGoal subgoal, Object result, GoalState state) {
		if (state != GoalState.RECURSIVE && result != null) {
			evaluated.add((IEvaluatedType) result);
		}
		return IGoal.NO_GOALS;
	}

}
