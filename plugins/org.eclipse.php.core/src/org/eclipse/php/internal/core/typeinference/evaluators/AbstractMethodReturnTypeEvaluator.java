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
package org.eclipse.php.internal.core.typeinference.evaluators;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.ti.IContext;
import org.eclipse.dltk.ti.ISourceModuleContext;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.php.internal.core.typeinference.IModelAccessCache;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.core.typeinference.context.IModelCacheContext;
import org.eclipse.php.internal.core.typeinference.goals.AbstractMethodReturnTypeGoal;

public abstract class AbstractMethodReturnTypeEvaluator extends
		AbstractPHPGoalEvaluator {
	protected class MethodsAndTypes {
		public IMethod[] methods;
		public IType[] types;
	}

	public AbstractMethodReturnTypeEvaluator(IGoal goal) {
		super(goal);
	}

	protected IMethod[] getMethods() {
		return getMethodsAndTypes().methods;
	}

	protected MethodsAndTypes getMethodsAndTypes() {
		AbstractMethodReturnTypeGoal typedGoal = (AbstractMethodReturnTypeGoal) goal;
		ISourceModule sourceModule = ((ISourceModuleContext) goal.getContext())
				.getSourceModule();
		IType[] types = typedGoal.getTypes();
		String methodName = typedGoal.getMethodName();

		IContext context = typedGoal.getContext();
		IModelAccessCache cache = null;
		if (context instanceof IModelCacheContext) {
			cache = ((IModelCacheContext) context).getCache();
		}

		List<IMethod> methods = new LinkedList<IMethod>();
		List<IType> methodTypes = new LinkedList<IType>();
		if (types == null) {
			try {
				methods.addAll(Arrays.asList(PHPModelUtils.getFunctions(
						methodName, sourceModule, 0, cache, null)));
				for (IMethod method : methods) {
					methodTypes.add(null);
				}
			} catch (ModelException e) {
				if (DLTKCore.DEBUG) {
					e.printStackTrace();
				}
			}
		} else {
			try {
				for (IType type : types) {
					IMethod[] typeMethods = PHPModelUtils.getTypeMethod(type,
							methodName, true);
					if (typeMethods.length == 0) {
						ITypeHierarchy hierarchy = null;
						if (cache != null) {
							hierarchy = cache.getSuperTypeHierarchy(type, null);
						}
						typeMethods = PHPModelUtils
								.getSuperTypeHierarchyMethod(type, hierarchy,
										methodName, true, null);
					}
					if (typeMethods.length > 0) {
						methods.add(typeMethods[0]);
						methodTypes.add(type);
					}
				}
			} catch (CoreException e) {
				if (DLTKCore.DEBUG) {
					e.printStackTrace();
				}
			}
		}
		MethodsAndTypes mat = new MethodsAndTypes();
		mat.methods = (IMethod[]) methods.toArray(new IMethod[methods.size()]);
		mat.types = (IType[]) methodTypes
				.toArray(new IType[methodTypes.size()]);

		return mat;
	}
}
