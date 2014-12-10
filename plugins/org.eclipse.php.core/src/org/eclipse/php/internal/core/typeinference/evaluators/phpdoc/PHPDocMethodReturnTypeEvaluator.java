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

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.core.SourceParserUtil;
import org.eclipse.dltk.evaluation.types.MultiTypeType;
import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.IContext;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.compiler.ast.nodes.*;
import org.eclipse.php.internal.core.index.IPHPDocAwareElement;
import org.eclipse.php.internal.core.typeinference.IModelAccessCache;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.core.typeinference.PHPTypeInferenceUtils;
import org.eclipse.php.internal.core.typeinference.context.IModelCacheContext;
import org.eclipse.php.internal.core.typeinference.evaluators.AbstractMethodReturnTypeEvaluator;
import org.eclipse.php.internal.core.typeinference.evaluators.PHPEvaluationUtils;
import org.eclipse.php.internal.core.typeinference.goals.AbstractMethodReturnTypeGoal;

/**
 * This Evaluator process the phpdoc of a method to determine its returned
 * type(s)
 * 
 * @see the PHPCodumentor spec at {@link http
 *      ://manual.phpdoc.org/HTMLSmartyConverter
 *      /HandS/phpDocumentor/tutorial_tags.return.pkg.html}
 */
public class PHPDocMethodReturnTypeEvaluator extends
		AbstractMethodReturnTypeEvaluator {

	private final static String SELF_RETURN_TYPE = "self"; //$NON-NLS-1$

	private final static Pattern MULTITYPE_PATTERN = Pattern.compile(
			"^multitype:(.+)$", Pattern.CASE_INSENSITIVE); //$NON-NLS-1$

	/**
	 * Holds the result of evaluated types that this evaluator resolved
	 */
	private final List<IEvaluatedType> evaluated = new LinkedList<IEvaluatedType>();

	public PHPDocMethodReturnTypeEvaluator(IGoal goal) {
		super(goal);
	}

	public IGoal[] init() {
		for (IMethod method : getMethods()) {
			IType currentNamespace = PHPModelUtils.getCurrentNamespace(method);
			String[] typeNames = null;
			if (method instanceof IPHPDocAwareElement) {
				typeNames = ((IPHPDocAwareElement) method).getReturnTypes();
			} else {
				List<String> returnTypeList = new LinkedList<String>();
				PHPDocBlock docBlock = PHPModelUtils.getDocBlock(method);
				if (docBlock == null) {
					return IGoal.NO_GOALS;
				}

				evaluateReturnType(returnTypeList, docBlock, method);
				typeNames = returnTypeList.toArray(new String[returnTypeList
						.size()]);
			}
			if (typeNames != null) {
				MultiTypeType evalMultiType = null;
				for (String typeName : typeNames) {
					if (typeName.trim().isEmpty()) {
						continue;
					}
					int offset = 0;
					try {
						offset = method.getSourceRange().getOffset();
					} catch (ModelException e) {
						PHPCorePlugin.log(e);
					}
					IEvaluatedType evaluatedType = PHPEvaluationUtils
							.extractArrayType(typeName, currentNamespace,
									offset);
					if (evaluatedType != null) {
						evaluated.add(evaluatedType);
					} else {
						boolean isMulti = false;
						Matcher multi = MULTITYPE_PATTERN.matcher(typeName);
						if (multi.find()) {
							if (evalMultiType == null) {
								evalMultiType = new MultiTypeType();
							}
							isMulti = true;
							typeName = multi.group(1);
						}
						AbstractMethodReturnTypeGoal goal = (AbstractMethodReturnTypeGoal) getGoal();
						IType[] types = goal.getTypes();
						if (typeName.equals(SELF_RETURN_TYPE) && types != null) {
							for (IType t : types) {
								IEvaluatedType type = PHPEvaluationUtils
										.getEvaluatedType(
												PHPModelUtils.getFullName(t),
												null);
								if (type != null) {
									if (isMulti) {
										evalMultiType.addType(type);
									} else {
										evaluated.add(type);
									}
								}
							}
						} else {
							if (currentNamespace != null) {
								PHPDocBlock docBlock = PHPModelUtils
										.getDocBlock(method);
								ModuleDeclaration moduleDeclaration = SourceParserUtil
										.getModuleDeclaration(currentNamespace
												.getSourceModule());
								if (typeName
										.indexOf(NamespaceReference.NAMESPACE_SEPARATOR) > 0) {
									// check if the first part
									// is an
									// alias,then get the full
									// name
									String prefix = typeName
											.substring(
													0,
													typeName.indexOf(NamespaceReference.NAMESPACE_SEPARATOR));
									final Map<String, UsePart> result = PHPModelUtils
											.getAliasToNSMap(prefix,
													moduleDeclaration,
													docBlock.sourceStart(),
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
								} else if (typeName
										.indexOf(NamespaceReference.NAMESPACE_SEPARATOR) < 0) {

									String prefix = typeName;
									final Map<String, UsePart> result = PHPModelUtils
											.getAliasToNSMap(prefix,
													moduleDeclaration,
													docBlock.sourceStart(),
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
								if (isMulti) {
									evalMultiType.addType(type);
								} else {
									evaluated.add(type);
								}
							}
						}
					}
				}
				if (evalMultiType != null) {
					evaluated.add(evalMultiType);
				}
			}
		}

		return IGoal.NO_GOALS;
	}

	private void evaluateReturnType(List<String> returnTypeList,
			PHPDocBlock docBlock, IMethod method) {
		PHPDocTag[] tags = docBlock.getTags(PHPDocTagKinds.RETURN);
		PHPDocTag[] inherit = docBlock.getTags(PHPDocTagKinds.INHERITDOC);

		if (inherit != null && inherit.length == 1) {
			IType type = method.getDeclaringType();

			if (type != null) {
				try {
					IContext context = goal.getContext();
					IModelAccessCache cache = null;
					if (context instanceof IModelCacheContext) {
						cache = ((IModelCacheContext) context).getCache();
					}
					IType[] superClasses = PHPModelUtils.getSuperClasses(
							type,
							cache == null ? null : cache.getSuperTypeHierarchy(
									type, null));

					for (IType superClass : superClasses) {
						IMethod superClassMethod = superClass.getMethod(method
								.getElementName());

						if (superClassMethod != null) {
							PHPDocBlock superDocBlock = PHPModelUtils
									.getDocBlock(superClassMethod);
							if (superDocBlock == null) {
								continue;
							}
							evaluateReturnType(returnTypeList, superDocBlock,
									superClassMethod);
						}
					}
				} catch (ModelException e) {
					Logger.logException(e);
				}
			}
		}

		if (tags != null && tags.length > 0) {
			for (PHPDocTag phpDocTag : tags) {
				if (phpDocTag.getReferences() != null
						&& phpDocTag.getReferences().length > 0) {
					for (SimpleReference ref : phpDocTag.getReferences()) {
						String type = ref.getName();
						if (type != null) {
							returnTypeList.add(type);
						}
					}
				}
			}
		}
	}

	public Object produceResult() {
		return PHPTypeInferenceUtils.combineTypes(evaluated);
	}

	public IGoal[] subGoalDone(IGoal subgoal, Object result, GoalState state) {
		return IGoal.NO_GOALS;
	}

}
