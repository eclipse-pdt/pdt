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

import org.eclipse.dltk.ast.references.TypeReference;
import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.IContext;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocBlock;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocTag;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocTag.TagKind;
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
public class PHPDocMethodReturnTypeEvaluator extends AbstractMethodReturnTypeEvaluator {

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
				typeNames = returnTypeList.toArray(new String[returnTypeList.size()]);
			}
			if (typeNames != null) {
				PHPDocBlock docBlock = PHPModelUtils.getDocBlock(method);
				if (docBlock != null) {
					AbstractMethodReturnTypeGoal goal = (AbstractMethodReturnTypeGoal) getGoal();
					IModelElement space = currentNamespace != null ? currentNamespace : method.getSourceModule();
					evaluated.addAll(Arrays.asList(PHPEvaluationUtils.evaluatePHPDocType(typeNames, space,
							docBlock.sourceStart(), goal.getTypes())));
				}
			}
		}

		return IGoal.NO_GOALS;
	}

	private void evaluateReturnType(List<String> returnTypeList, PHPDocBlock docBlock, IMethod method) {
		PHPDocTag[] tags = docBlock.getTags(TagKind.RETURN);
		PHPDocTag[] inherit = docBlock.getTags(TagKind.INHERITDOC);

		if (inherit.length == 1) {
			IType type = method.getDeclaringType();

			if (type != null) {
				try {
					IContext context = goal.getContext();
					IModelAccessCache cache = null;
					if (context instanceof IModelCacheContext) {
						cache = ((IModelCacheContext) context).getCache();
					}
					IType[] superClasses = PHPModelUtils.getSuperClasses(type,
							cache == null ? null : cache.getSuperTypeHierarchy(type, null));

					for (IType superClass : superClasses) {
						IMethod superClassMethod = superClass.getMethod(method.getElementName());

						if (superClassMethod != null) {
							PHPDocBlock superDocBlock = PHPModelUtils.getDocBlock(superClassMethod);
							if (superDocBlock == null) {
								continue;
							}
							evaluateReturnType(returnTypeList, superDocBlock, superClassMethod);
						}
					}
				} catch (ModelException e) {
					Logger.logException(e);
				}
			}
		}

		for (PHPDocTag phpDocTag : tags) {
			for (TypeReference ref : phpDocTag.getTypeReferences()) {
				returnTypeList.add(ref.getName());
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
