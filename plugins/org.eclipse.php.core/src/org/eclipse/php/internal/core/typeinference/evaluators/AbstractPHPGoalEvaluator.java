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

import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.ti.ISourceModuleContext;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.typeinference.PHPTypeInferenceUtils;

public abstract class AbstractPHPGoalEvaluator extends GoalEvaluator {

	public AbstractPHPGoalEvaluator(IGoal goal) {
		super(goal);
	}

	/**
	 * Returns all model elements for the given class type. If current file is not <code>null</code> this method tries to find
	 * only type declared in the very file.
	 * @param instanceType Evaluated type of the class
	 * @param currentModule Current file module
	 * @return
	 */
	protected IType[] getTypes(IEvaluatedType instanceType, ISourceModuleContext context) {
		IType[] types = PHPTypeInferenceUtils.getModelElements(instanceType, context, 0);

		ISourceModule currentModule = context.getSourceModule();
		if (currentModule != null) {
			IType typeFromSameFile = null;
			for (IType type : types) {
				if (type.getSourceModule().equals(currentModule)) {
					typeFromSameFile = type;
					break;
				}
			}
			// If type from the same file was found  - use it
			if (typeFromSameFile != null) {
				return new IType[] { typeFromSameFile };
			}
		}
		return types;
	}
}
