/*******************************************************************************
 * Copyright (c) 2015, 2016 IBM Corporation and others.
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

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.ast.references.TypeReference;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.evaluation.types.MultiTypeType;
import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.ISourceModuleContext;
import org.eclipse.dltk.ti.goals.ExpressionTypeGoal;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.core.compiler.ast.nodes.FormalParameter;
import org.eclipse.php.core.compiler.ast.nodes.PHPDocBlock;
import org.eclipse.php.core.compiler.ast.nodes.PHPDocTag;
import org.eclipse.php.core.compiler.ast.nodes.PHPMethodDeclaration;
import org.eclipse.php.core.compiler.ast.nodes.PHPDocTag.TagKind;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.typeinference.GeneratorClassType;
import org.eclipse.php.internal.core.typeinference.IModelAccessCache;
import org.eclipse.php.internal.core.typeinference.PHPClassType;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.core.typeinference.context.IModelCacheContext;
import org.eclipse.php.internal.core.typeinference.context.MethodContext;
import org.eclipse.php.internal.core.typeinference.goals.IteratorTypeGoal;
import org.eclipse.php.internal.core.typeinference.goals.MethodElementReturnTypeGoal;
import org.eclipse.php.internal.core.typeinference.goals.phpdoc.PHPDocMethodReturnTypeGoal;

public class IteratorTypeGoalEvaluator extends GoalEvaluator {

	private IEvaluatedType result;

	public IteratorTypeGoalEvaluator(IGoal goal) {
		super(goal);
	}

	public IGoal[] init() {
		IteratorTypeGoal typedGoal = (IteratorTypeGoal) goal;
		return new IGoal[] { new ExpressionTypeGoal(goal.getContext(), typedGoal.getExpression()) };
	}

	public Object produceResult() {
		return result;
	}

	public IGoal[] subGoalDone(IGoal subgoal, Object result, GoalState state) {
		IModelAccessCache cache = null;
		if (goal.getContext() instanceof IModelCacheContext) {
			cache = (IModelAccessCache) ((IModelCacheContext) goal.getContext()).getCache();
		}
		String variableName = null;
		IteratorTypeGoal iteratorTypeGoal = (IteratorTypeGoal) goal;
		if (iteratorTypeGoal.getExpression() instanceof VariableReference) {
			variableName = ((VariableReference) iteratorTypeGoal.getExpression()).getName();
		}
		if (state != GoalState.RECURSIVE) {
			if (result instanceof GeneratorClassType) {
				MultiTypeType type = new MultiTypeType();
				type.getTypes().addAll(((GeneratorClassType) result).getTypes());
				this.result = type;
				return IGoal.NO_GOALS;
			} else if (result instanceof PHPClassType) {
				if (subgoal instanceof ExpressionTypeGoal) {
					ISourceModule sourceModule = ((ISourceModuleContext) subgoal.getContext()).getSourceModule();
					PHPClassType classType = (PHPClassType) result;
					List<IGoal> subGoals = new LinkedList<IGoal>();
					try {
						// XXX: offset is 0 here but it should still work,
						// because classType already contains the namespace part
						IType[] types = PHPModelUtils.getTypes(classType.getTypeName(), sourceModule, 0, cache, null);
						for (IType type : types) {
							IType[] superTypes = PHPModelUtils.getSuperClasses(type,
									cache == null ? null : cache.getSuperTypeHierarchy(type, null));

							if (subgoal.getContext() instanceof MethodContext) {

								MethodContext methodContext = (MethodContext) subgoal.getContext();

								if (isArrayType(methodContext, variableName, type)) {
									MultiTypeType mType = new MultiTypeType();
									mType.addType((IEvaluatedType) result);
									this.result = mType;
									return IGoal.NO_GOALS;
								}
							}

							if (isImplementedIterator(superTypes)) {
								subGoals.add(new MethodElementReturnTypeGoal(subgoal.getContext(), new IType[] { type },
										"current", new String[0], type.getSourceRange().getOffset())); //$NON-NLS-1$
								subGoals.add(new PHPDocMethodReturnTypeGoal(subgoal.getContext(), new IType[] { type },
										"current", new String[0], type.getSourceRange().getOffset())); //$NON-NLS-1$
							}
						}
						if (subGoals.size() == 0) {
							MultiTypeType mType = new MultiTypeType();
							mType.addType((IEvaluatedType) result);
							this.result = mType;
							return IGoal.NO_GOALS;
						}
						return subGoals.toArray(new IGoal[subGoals.size()]);
					} catch (ModelException e) {
						if (DLTKCore.DEBUG) {
							e.printStackTrace();
						}
					}
				}
				MultiTypeType type = new MultiTypeType();
				type.addType((IEvaluatedType) result);
				this.result = type;
				return IGoal.NO_GOALS;
			}
			this.result = (IEvaluatedType) result;
		}
		return IGoal.NO_GOALS;
	}

	/**
	 * Check if IType is a typed array in the methodContext:
	 * 
	 * <pre>
	 *   /*
	 *    *  @param SomeClass[] $elements
	 *    *\/
	 *    public function foo(array $elements);
	 * </pre>
	 * 
	 * 
	 * @param methodContext
	 * @param variableName
	 * @param type
	 * @return boolean
	 */
	private boolean isArrayType(MethodContext methodContext, String variableName, IType type) {

		PHPMethodDeclaration methodDeclaration = (PHPMethodDeclaration) methodContext.getMethodNode();

		PHPDocBlock[] docBlocks = new PHPDocBlock[0];
		for (Object object : methodDeclaration.getArguments()) {
			if (object instanceof FormalParameter) {
				FormalParameter formalParameter = (FormalParameter) object;
				if (formalParameter.getName().equals(variableName) && formalParameter.isVariadic()) {
					return true;
				}
			}
		}
		try {
			IModelElement element = methodContext.getSourceModule().getElementAt(methodDeclaration.getNameStart());
			if (element instanceof IMethod) {
				IMethod method = (IMethod) element;
				if (method.getDeclaringType() != null) {
					docBlocks = PHPModelUtils
							.getTypeHierarchyMethodDoc(method.getDeclaringType(),
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
			Logger.logException(e);
		}

		if (docBlocks.length > 0) {
			for (int i = 0; i < docBlocks.length; i++) {
				if (docBlocks[i] == null) {
					continue;
				}
				PHPDocTag[] tags = docBlocks[i].getTags(TagKind.PARAM);
				for (int j = 0; j < tags.length; j++) {
					PHPDocTag tag = tags[j];
					if (tag.isValidParamTag() && tag.getVariableReference().getName().equals(variableName)) {
						for (TypeReference reference : tag.getTypeReferences()) {
							if (PHPEvaluationUtils.isArrayType(reference.getName())) {
								return true;
							}
						}
						break;
					}
				}
			}
		}

		return false;
	}

	private boolean isImplementedIterator(IType[] superClasses) {
		if (superClasses == null)
			return false;
		for (IType superClass : superClasses) {
			if (superClass.getFullyQualifiedName().equalsIgnoreCase("Iterator")) { //$NON-NLS-1$
				return true;
			}
		}
		return false;
	}

}
