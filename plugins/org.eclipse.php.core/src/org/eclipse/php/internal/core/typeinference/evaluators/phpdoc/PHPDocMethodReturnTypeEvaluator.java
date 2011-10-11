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
import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.core.SourceParserUtil;
import org.eclipse.dltk.evaluation.types.MultiTypeType;
import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.compiler.ast.nodes.*;
import org.eclipse.php.internal.core.index.IPHPDocAwareElement;
import org.eclipse.php.internal.core.typeinference.PHPClassType;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.core.typeinference.PHPSimpleTypes;
import org.eclipse.php.internal.core.typeinference.PHPTypeInferenceUtils;
import org.eclipse.php.internal.core.typeinference.evaluators.AbstractMethodReturnTypeEvaluator;
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

	private final static Pattern ARRAY_TYPE_PATTERN = Pattern
			.compile("array\\[.*\\]");

	private final static String SELF_RETURN_TYPE = "self";

	/**
	 * Used for splitting the data types list of the returned tag
	 */
	private final static Pattern PIPE_PATTERN = Pattern.compile("\\|");

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
				PHPDocTag[] tags = docBlock.getTags(PHPDocTagKinds.RETURN);
				if (tags != null && tags.length > 0) {
					for (PHPDocTag phpDocTag : tags) {
						if (phpDocTag.getReferences() != null
								&& phpDocTag.getReferences().length > 0) {
							// returnTypeList.add(phpDocTag.getReferences()[0]
							// .getStringRepresentation());
							String[] returnTypes = phpDocTag.getReferences()[0]
									.getStringRepresentation().split(",");
							for (String returnType : returnTypes) {
								returnTypeList.add(returnType);
							}
						}

					}
				}
				typeNames = returnTypeList.toArray(new String[returnTypeList
						.size()]);
			}
			if (typeNames != null) {
				for (String typeName : typeNames) {
					Matcher m = ARRAY_TYPE_PATTERN.matcher(typeName);
					if (m.find()) {
						int offset = 0;
						try {
							offset = method.getSourceRange().getOffset();
						} catch (ModelException e) {
						}
						evaluated.add(getArrayType(m.group(), currentNamespace,
								offset));
					} else {
						AbstractMethodReturnTypeGoal goal = (AbstractMethodReturnTypeGoal) getGoal();
						IType[] types = goal.getTypes();
						if (typeName.equals(SELF_RETURN_TYPE) && types != null) {
							for (IType t : types) {
								IEvaluatedType type = getEvaluatedType(
										t.getElementName(), currentNamespace);
								if (type != null) {
									evaluated.add(type);
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
									}
								}
							}
							IEvaluatedType type = getEvaluatedType(typeName,
									currentNamespace);
							if (type != null) {
								evaluated.add(type);
							}
						}

						IEvaluatedType type = getEvaluatedType(typeName,
								currentNamespace);
						if (type != null) {
							evaluated.add(type);
						}
					}
				}
			}
		}

		return IGoal.NO_GOALS;
	}

	private MultiTypeType getArrayType(String type, IType currentNamespace,
			int offset) {
		int beginIndex = type.indexOf("[") + 1;
		int endIndex = type.lastIndexOf("]");
		type = type.substring(beginIndex, endIndex);
		MultiTypeType arrayType = new MultiTypeType();
		Matcher m = ARRAY_TYPE_PATTERN.matcher(type);
		if (m.find()) {
			arrayType
					.addType(getArrayType(m.group(), currentNamespace, offset));
			type = m.replaceAll("");
		}
		String[] typeNames = type.split(",");
		for (String name : typeNames) {
			if (!"".equals(name)) {

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
					}
				}
				arrayType.addType(getEvaluatedType(name, currentNamespace));
			}
		}
		return arrayType;
	}

	private IEvaluatedType getEvaluatedType(String typeName,
			IType currentNamespace) {
		IEvaluatedType type = PHPSimpleTypes.fromString(typeName);
		if (type == null) {
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

	public Object produceResult() {
		return PHPTypeInferenceUtils.combineTypes(evaluated);
	}

	public IGoal[] subGoalDone(IGoal subgoal, Object result, GoalState state) {
		return IGoal.NO_GOALS;
	}

}
