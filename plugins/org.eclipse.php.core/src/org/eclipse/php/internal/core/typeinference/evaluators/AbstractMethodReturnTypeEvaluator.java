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
import org.eclipse.dltk.ti.ISourceModuleContext;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.core.typeinference.goals.AbstractMethodReturnTypeGoal;

public abstract class AbstractMethodReturnTypeEvaluator extends AbstractPHPGoalEvaluator {

	public AbstractMethodReturnTypeEvaluator(IGoal goal) {
		super(goal);
	}

	protected IMethod[] getMethods() {
		AbstractMethodReturnTypeGoal typedGoal = (AbstractMethodReturnTypeGoal) goal;
		ISourceModule sourceModule = ((ISourceModuleContext) goal.getContext()).getSourceModule();
		IType[] types = typedGoal.getTypes();
		String methodName = typedGoal.getMethodName();

		List<IMethod> methods = new LinkedList<IMethod>();
		if (types == null) {
			try {
				methods.addAll(Arrays.asList(PHPModelUtils.getFunctions(methodName, sourceModule, 0)));
			} catch (ModelException e) {
				if (DLTKCore.DEBUG) {
					e.printStackTrace();
				}
			}
		} else {
			try {
				for (IType type : types) {
					IMethod method = PHPModelUtils.getTypeMethod(type, methodName);
					if (method == null) {
						IMethod[] hierarchyMethods = PHPModelUtils.getTypeHierarchyMethod(type, methodName, null);
						if (hierarchyMethods.length > 0) {
							method = hierarchyMethods[0];
						}
					}
					if (method != null) {
						methods.add(method);
					}
				}
			} catch (CoreException e) {
				if (DLTKCore.DEBUG) {
					e.printStackTrace();
				}
			}
		}

		return (IMethod[]) methods.toArray(new IMethod[methods.size()]);
	}
}
