/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.typeinference.evaluators;

import java.util.*;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.dltk.ti.goals.MethodReturnTypeGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.core.typeinference.PHPTypeInferenceUtils;
import org.eclipse.php.internal.core.typeinference.context.TypeContext;
import org.eclipse.php.internal.core.typeinference.goals.MethodElementReturnTypeGoal;

public class MethodReturnTypeEvaluator extends AbstractPHPGoalEvaluator {

	private final List<IEvaluatedType> evaluated = new LinkedList<IEvaluatedType>();

	public MethodReturnTypeEvaluator(IGoal goal) {
		super(goal);
	}

	private MethodReturnTypeGoal getTypedGoal() {
		return (MethodReturnTypeGoal) this.getGoal();
	}

	private TypeContext getTypedContext() {
		return (TypeContext) this.getGoal().getContext();
	}

	public Object produceResult() {
		return PHPTypeInferenceUtils.combineTypes(evaluated);
	}

	public IGoal[] init() {
		MethodReturnTypeGoal typedGoal = getTypedGoal();
		TypeContext typedContext = getTypedContext();

		final Set<IMethod> methods = new HashSet<IMethod>();

		String methodName = typedGoal.getMethodName();
		IType[] types = getTypes(typedContext.getInstanceType(), typedContext);

		if (types.length == 0) {
			IScriptProject scriptProject = typedContext.getSourceModule().getScriptProject();
			IDLTKSearchScope scope = SearchEngine.createSearchScope(scriptProject);
			IMethod[] functions = PHPTypeInferenceUtils.getFunctions(methodName, scope);
			for (IMethod function : functions) {
				methods.add(function);
			}
		} else {
			for (IType type : types) {
				try {
					IMethod method = type.getMethod(methodName);
					if (method.exists()) {
						methods.add(method);
					} else {
						IMethod[] elements = PHPModelUtils.getTypeHierarchyMethod(type, methodName, null);
						for (IMethod m : elements) {
							methods.add(m);
						}
					}
				} catch (CoreException e) {
					if (DLTKCore.DEBUG) {
						e.printStackTrace();
					}
				}
			}
		}

		final List<IGoal> subGoals = new ArrayList<IGoal>(methods.size());
		for (IMethod method : methods) {
			subGoals.add(new MethodElementReturnTypeGoal(typedContext, method));
		}
		return subGoals.toArray(new IGoal[subGoals.size()]);
	}

	public IGoal[] subGoalDone(IGoal subgoal, Object result, GoalState state) {
		if (state != GoalState.RECURSIVE && result != null) {
			evaluated.add((IEvaluatedType) result);
		}
		return IGoal.NO_GOALS;
	}

}
