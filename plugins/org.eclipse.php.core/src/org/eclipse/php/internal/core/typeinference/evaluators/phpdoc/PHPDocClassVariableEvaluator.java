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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
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
import org.eclipse.php.internal.core.typeinference.*;
import org.eclipse.php.internal.core.typeinference.context.TypeContext;
import org.eclipse.php.internal.core.typeinference.evaluators.AbstractPHPGoalEvaluator;
import org.eclipse.php.internal.core.typeinference.goals.phpdoc.PHPDocClassVariableGoal;

/**
 * This evaluator finds class field declartion either using "var" or in method
 * body using field access.
 */
public class PHPDocClassVariableEvaluator extends AbstractPHPGoalEvaluator {

	private static final String SPLASH = "\\"; //$NON-NLS-1$

	public static final String BRACKETS = "[]"; //$NON-NLS-1$

	private List<IEvaluatedType> evaluated = new LinkedList<IEvaluatedType>();

	public final static Pattern ARRAY_TYPE_PATTERN = Pattern
			.compile("array\\[.*\\]"); //$NON-NLS-1$

	public PHPDocClassVariableEvaluator(IGoal goal) {
		super(goal);
	}

	public IGoal[] init() {
		PHPDocClassVariableGoal typedGoal = (PHPDocClassVariableGoal) goal;
		TypeContext context = (TypeContext) typedGoal.getContext();
		String variableName = typedGoal.getVariableName();

		IType[] types = PHPTypeInferenceUtils.getModelElements(
				context.getInstanceType(), context);
		Map<PHPDocBlock, IField> docs = new HashMap<PHPDocBlock, IField>();

		IModelAccessCache cache = context.getCache();

		if (types != null) {
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

		for (PHPDocBlock doc : docs.keySet()) {
			IField typeField = docs.get(doc);
			IType currentNamespace = PHPModelUtils
					.getCurrentNamespace(typeField);
			for (PHPDocTag tag : doc.getTags()) {
				if (tag.getTagKind() == PHPDocTag.VAR) {
					SimpleReference[] references = tag.getReferences();
					for (SimpleReference ref : references) {
						String typeName = ref.getName();
						Matcher m = ARRAY_TYPE_PATTERN.matcher(typeName);
						if (m.find()) {
							evaluated.add(getArrayType(m.group(),
									currentNamespace, doc.sourceStart()));
						} else if (typeName.endsWith(BRACKETS)
								&& typeName.length() > 2) {
							int offset = 0;
							try {
								offset = typeField.getSourceRange().getOffset();
							} catch (ModelException e) {
							}
							evaluated.add(getArrayType(typeName.substring(0,
									typeName.length() - 2), currentNamespace,
									offset));

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

							IEvaluatedType type = getEvaluatedType(typeName,
									currentNamespace);
							if (type != null) {
								evaluated.add(type);
							}
							// IEvaluatedType type = PHPClassType
							// .fromSimpleReference(ref);
							// evaluated.add(type);
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

	public static MultiTypeType getArrayType(String type,
			IType currentNamespace, int offset) {
		int beginIndex = type.indexOf("[") + 1; //$NON-NLS-1$
		int endIndex = type.lastIndexOf("]"); //$NON-NLS-1$
		if (endIndex != -1) {
			type = type.substring(beginIndex, endIndex);
		}
		MultiTypeType arrayType = new MultiTypeType();
		Matcher m = ARRAY_TYPE_PATTERN.matcher(type);
		if (m.find()) {
			arrayType
					.addType(getArrayType(m.group(), currentNamespace, offset));
			type = m.replaceAll(""); //$NON-NLS-1$
		}
		String[] typeNames = type.split(","); //$NON-NLS-1$
		for (String name : typeNames) {
			if (!"".equals(name)) { //$NON-NLS-1$

				if (name.indexOf(NamespaceReference.NAMESPACE_SEPARATOR) > 0
						&& currentNamespace != null) {
					// check if the first part is an
					// alias,then get the full name
					ModuleDeclaration moduleDeclaration = SourceParserUtil
							.getModuleDeclaration(currentNamespace
									.getSourceModule());
					String prefix = name.substring(0, name
							.indexOf(NamespaceReference.NAMESPACE_SEPARATOR));
					final Map<String, UsePart> result = PHPModelUtils
							.getAliasToNSMap(prefix, moduleDeclaration, offset,
									currentNamespace, true);
					if (result.containsKey(prefix)) {
						String fullName = result.get(prefix).getNamespace()
								.getFullyQualifiedName();
						name = name.replace(prefix, fullName);
						if (name.charAt(0) != NamespaceReference.NAMESPACE_SEPARATOR) {
							name = NamespaceReference.NAMESPACE_SEPARATOR
									+ name;
						}
					}
				}
				arrayType.addType(getEvaluatedType(name, currentNamespace));
			}
		}
		return arrayType;
	}

	public static IEvaluatedType getEvaluatedType(String typeName,
			IType currentNamespace) {
		IEvaluatedType type = PHPSimpleTypes.fromString(typeName);
		if (type == null) {
			if (typeName.indexOf(NamespaceReference.NAMESPACE_SEPARATOR) > 0
					&& currentNamespace != null) {
				typeName = NamespaceReference.NAMESPACE_SEPARATOR
						+ currentNamespace.getElementName()
						+ NamespaceReference.NAMESPACE_SEPARATOR + typeName;
			}
			if (typeName.indexOf(NamespaceReference.NAMESPACE_SEPARATOR) != -1
					|| currentNamespace == null) {
				type = new PHPClassType(typeName);
			} else if (currentNamespace != null) {
				type = new PHPClassType(currentNamespace.getElementName(),
						typeName);
			}
		}
		return type;
	}

}
