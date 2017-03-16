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
package org.eclipse.php.internal.core.typeinference.evaluators;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.ast.references.TypeReference;
import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.evaluation.types.MultiTypeType;
import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.IContext;
import org.eclipse.dltk.ti.goals.ExpressionTypeGoal;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.core.compiler.ast.nodes.*;
import org.eclipse.php.core.compiler.ast.nodes.PHPDocTag.TagKind;
import org.eclipse.php.internal.core.typeinference.PHPClassType;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.core.typeinference.PHPSimpleTypes;
import org.eclipse.php.internal.core.typeinference.context.MethodContext;

public class FormalParameterEvaluator extends GoalEvaluator {

	public static final String ELLIPSIS = "..."; //$NON-NLS-1$

	private IEvaluatedType result;

	public FormalParameterEvaluator(IGoal goal) {
		super(goal);
	}

	public IGoal[] init() {
		ExpressionTypeGoal typedGoal = (ExpressionTypeGoal) goal;
		FormalParameter parameter = (FormalParameter) typedGoal.getExpression();

		SimpleReference type = parameter.getParameterType();
		IContext context = typedGoal.getContext();
		if (type != null && "array".equals(type.getName()) == false) { //$NON-NLS-1$
			if (context instanceof MethodContext) {
				MethodContext methodContext = (MethodContext) context;

				String typeName = type.getName();
				String namespace = null;
				if (type instanceof FullyQualifiedReference) {
					FullyQualifiedReference fqn = (FullyQualifiedReference) type;
					if (fqn.getNamespace() != null) {
						namespace = fqn.getNamespace().getName();
					}
				}
				if (namespace == null) {
					String fullName = PHPModelUtils.getFullName(typeName, methodContext.getSourceModule(),
							parameter.start());
					typeName = PHPModelUtils.extractElementName(fullName);
					namespace = PHPModelUtils.extractNameSpaceName(fullName);
				}
				if (namespace != null) {
					result = new PHPClassType(namespace, typeName);
				} else {
					result = new PHPClassType(typeName);
				}
				if (parameter.isVariadic()) {
					// a variadic parameter is an array of a single type of data
					MultiTypeType multiType = new MultiTypeType();
					multiType.addType(result);
					result = multiType;
				}
			} else {
				result = PHPClassType.fromSimpleReference(type);
			}
		} else {
			if (context instanceof MethodContext) {
				MethodContext methodContext = (MethodContext) context;
				PHPMethodDeclaration methodDeclaration = (PHPMethodDeclaration) methodContext.getMethodNode();
				ISourceModule sourceModule = methodContext.getSourceModule();
				PHPDocBlock[] docBlocks = new PHPDocBlock[0];
				try {
					IModelElement element = sourceModule.getElementAt(methodDeclaration.getNameStart());
					if (element instanceof IMethod) {
						IMethod method = (IMethod) element;
						if (method.getDeclaringType() != null) {
							docBlocks = PHPModelUtils.getTypeHierarchyMethodDoc(method.getDeclaringType(),
									methodContext.getCache() != null ? methodContext.getCache()
											.getSuperTypeHierarchy(method.getDeclaringType(), null) : null,
									method.getElementName(), true, null);
						} else {
							docBlocks = new PHPDocBlock[] { methodDeclaration.getPHPDoc() };
						}
					} else {
						docBlocks = new PHPDocBlock[] { methodDeclaration.getPHPDoc() };
					}

				} catch (CoreException e) {
				}
				for (PHPDocBlock docBlock : docBlocks) {
					if (result != null) {
						break;
					}
					if (docBlock != null) {
						for (PHPDocTag tag : docBlock.getTags(TagKind.PARAM)) {
							if (!tag.isValidParamTag()) {
								continue;
							}
							String parameterName = parameter.getName();
							if (parameter.isVariadic()) {
								parameterName = ELLIPSIS + parameterName;
							}
							if (tag.getVariableReference().getName().equals(parameterName)) {
								MultiTypeType multiType = new MultiTypeType();
								for (TypeReference paramType : tag.getTypeReferences()) {
									String typeName = paramType.getName();

									typeName = PHPEvaluationUtils.extractArrayType(typeName);

									multiType.addType(
											PHPClassType.fromTypeName(typeName, sourceModule, paramType.sourceStart()));
								}
								// when it is not true multi type
								if (multiType.size() == 1) {
									result = multiType.get(0);
								} else if (multiType.size() > 1) {
									result = multiType;
								}

								break;
							}
						}
					}
				}
				if (result == null && parameter.getInitialization() instanceof Scalar) {
					Scalar scalar = (Scalar) parameter.getInitialization();
					result = PHPSimpleTypes.fromString(scalar.getType());
					if (result == null) {
						result = new PHPClassType(scalar.getType());
					}
				}
			}
		}
		return IGoal.NO_GOALS;
	}

	public Object produceResult() {
		return result;
	}

	public IGoal[] subGoalDone(IGoal subgoal, Object result, GoalState state) {
		return IGoal.NO_GOALS;
	}

}
