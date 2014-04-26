/*******************************************************************************
 * Copyright (c) 2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Dawid Pakuła - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.typeinference;

import org.eclipse.dltk.ti.IPruner;
import org.eclipse.dltk.ti.goals.AbstractTypeGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;

public interface IPHPTypeInferencer {
	public IEvaluatedType evaluateTypePHPDoc(AbstractTypeGoal goal, int timeout);

	public IEvaluatedType evaluateTypePHPDoc(AbstractTypeGoal goal);

	public IEvaluatedType evaluateTypeHeavy(AbstractTypeGoal goal, int timeout);

	public IEvaluatedType evaluateType(AbstractTypeGoal goal);

	public IEvaluatedType evaluateType(AbstractTypeGoal goal, int timeLimit);

	public IEvaluatedType evaluateType(AbstractTypeGoal goal, IPruner pruner);
}
