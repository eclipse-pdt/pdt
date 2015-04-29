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

import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.evaluation.types.MultiTypeType;
import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.compiler.ast.nodes.NamespaceReference;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocBlock;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocTag;
import org.eclipse.php.internal.core.compiler.ast.nodes.UsePart;
import org.eclipse.php.internal.core.typeinference.IModelAccessCache;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.core.typeinference.PHPTypeInferenceUtils;
import org.eclipse.php.internal.core.typeinference.context.TypeContext;
import org.eclipse.php.internal.core.typeinference.evaluators.AbstractPHPGoalEvaluator;
import org.eclipse.php.internal.core.typeinference.evaluators.PHPEvaluationUtils;
import org.eclipse.php.internal.core.typeinference.goals.phpdoc.PHPDocClassVariableGoal;

/**
 * This evaluator finds class field declartion either using "var" or in method
 * body using field access.
 */
public class PHPDocClassVariableEvaluator extends AbstractPHPGoalEvaluator {

	public static final String BRACKETS = PHPEvaluationUtils.BRACKETS;

	public final static Pattern ARRAY_TYPE_PATTERN = PHPEvaluationUtils.ARRAY_TYPE_PATTERN;

	private static final String SPLASH = "\\"; //$NON-NLS-1$

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
		IType[] types = PHPTypeInferenceUtils.getModelElements(
				context.getInstanceType(), context, offset, cache);
		Map<PHPDocBlock, IField> docs = new HashMap<PHPDocBlock, IField>();
		if (types != null) {
			// remove array index from field name
			if (variableName.endsWith("]")) { //$NON-NLS-1$
				int index = variableName.indexOf("["); //$NON-NLS-1$
				if (index != -1) {
					variableName = variableName.substring(0, index);
				}
			}
			for (IType type : types) {
				try {
					// we look in whole hiearchy
					ITypeHierarchy superHierarchy;
					if (cache != null) {
						superHierarchy = cache
								.getSuperTypeHierarchy(type, null);
					} else {
						superHierarchy = type.newSupertypeHierarchy(null);
					}
					IType[] superTypes = superHierarchy.getAllTypes();
					for (IType superType : superTypes) {
						IField[] typeField = PHPModelUtils.getTypeField(
								superType, variableName, true);
						if (typeField.length > 0) {
							PHPDocBlock docBlock = PHPModelUtils
									.getDocBlock(typeField[0]);
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
			IType currentNamespace = PHPModelUtils
					.getCurrentNamespace(typeField);

			for (PHPDocTag tag : doc.getTags()) {
				if (tag.getTagKind() == PHPDocTag.VAR) {
					SimpleReference[] references = tag.getReferences();
					for (SimpleReference ref : references) {
						String typeName = ref.getName();

						IEvaluatedType evaluatedType = PHPEvaluationUtils
								.extractArrayType(typeName, currentNamespace,
										tag.sourceStart());
						if (evaluatedType != null) {
							evaluated.add(evaluatedType);
						} else {
							if (currentNamespace != null) {
								ModuleDeclaration moduleDeclaration = SourceParserUtil
										.getModuleDeclaration(currentNamespace
												.getSourceModule());
								if (typeName.indexOf(SPLASH) > 0) {
									// check if the first part is an
									// alias,then get the full name
									String prefix = typeName.substring(0,
											typeName.indexOf(SPLASH));
									final Map<String, UsePart> result = PHPModelUtils
											.getAliasToNSMap(prefix,
													moduleDeclaration,
													doc.sourceStart(),
													currentNamespace, true);
									if (result.containsKey(prefix)) {
										String fullName = result.get(prefix)
												.getNamespace()
												.getFullyQualifiedName();
										typeName = typeName.replace(prefix,
												fullName);
										if (typeName.charAt(0) != NamespaceReference.NAMESPACE_SEPARATOR) {
											typeName = NamespaceReference.NAMESPACE_SEPARATOR
													+ typeName;
										}
									}
								} else if (typeName.indexOf(SPLASH) < 0) {
									String prefix = typeName;
									final Map<String, UsePart> result = PHPModelUtils
											.getAliasToNSMap(prefix,
													moduleDeclaration,
													doc.sourceStart(),
													currentNamespace, true);
									if (result.containsKey(prefix)) {
										String fullName = result.get(prefix)
												.getNamespace()
												.getFullyQualifiedName();
										typeName = fullName;
										if (typeName.charAt(0) != NamespaceReference.NAMESPACE_SEPARATOR) {
											typeName = NamespaceReference.NAMESPACE_SEPARATOR
													+ typeName;
										}
									}
								}
							}
							IEvaluatedType type = PHPEvaluationUtils
									.getEvaluatedType(typeName,
											currentNamespace);
							if (type != null) {
								evaluated.add(type);
							}
						}
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

	/**
	 * 
	 * @deprecated will be removed in Mars
	 */
	public static MultiTypeType getArrayType(String type,
			IType currentNamespace, int offset) {
		return PHPEvaluationUtils.getArrayType(type, currentNamespace, offset);
	}

}
