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

import java.util.*;
import java.util.Map.Entry;

import org.eclipse.dltk.core.*;
import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocBlock;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocTag;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocTag.TagKind;
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

	public IGoal[] init() {
		PHPDocClassVariableGoal typedGoal = (PHPDocClassVariableGoal) goal;
		TypeContext context = (TypeContext) typedGoal.getContext();
		String variableName = typedGoal.getVariableName();
		int offset = typedGoal.getOffset();

		IModelAccessCache cache = context.getCache();
		IType[] types = PHPTypeInferenceUtils.getModelElements(context.getInstanceType(), context, offset, cache);
		Map<PHPDocBlock, IField> docs = new HashMap<PHPDocBlock, IField>();
		// remove array index from field name
		if (variableName.endsWith("]")) { //$NON-NLS-1$
			int index = variableName.indexOf("["); //$NON-NLS-1$
			if (index != -1) {
				variableName = variableName.substring(0, index);
			}
		}
		if (types != null) {
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
						if (typeField.length > 0) {
							PHPDocBlock docBlock = PHPModelUtils.getDocBlock(typeField[0]);
							if (docBlock != null) {
								docs.put(docBlock, typeField[0]);
							}
						}
					}
				} catch (ModelException e) {
					if (DLTKCore.DEBUG) {
						e.printStackTrace();
					}
				}
			}
		}

		for (Entry<PHPDocBlock, IField> entry : docs.entrySet()) {
			PHPDocBlock doc = entry.getKey();
			IField typeField = entry.getValue();
			IType currentNamespace = PHPModelUtils.getCurrentNamespace(typeField);

			IModelElement space = currentNamespace != null ? currentNamespace : typeField.getSourceModule();

			for (PHPDocTag tag : doc.getTags(TagKind.VAR)) {
				// do it like for
				// PHPDocumentationContentAccess#handleBlockTags(List tags):
				// variable name can be optional, but if present keep only
				// the good ones
				if (tag.getVariableReference() != null && !tag.getVariableReference().getName().equals(variableName)) {
					continue;
				}

				evaluated.addAll(Arrays.asList(PHPEvaluationUtils.evaluatePHPDocType(tag.getTypeReferences(), space,
						tag.sourceStart(), null)));
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
