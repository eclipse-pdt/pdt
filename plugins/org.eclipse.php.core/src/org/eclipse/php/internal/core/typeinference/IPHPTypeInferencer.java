/*******************************************************************************
 * Copyright (c) 2014, 2018 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Dawid Pakuła - initial API and implementation
 *     Michele Locati
 *******************************************************************************/
package org.eclipse.php.internal.core.typeinference;

import org.eclipse.dltk.ti.IPruner;
import org.eclipse.dltk.ti.goals.AbstractTypeGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;

public interface IPHPTypeInferencer {
	public IEvaluatedType evaluateTypeFactoryMethod(AbstractTypeGoal goal, int timeout);

	public IEvaluatedType evaluateTypeFactoryMethod(AbstractTypeGoal goal);

	public IEvaluatedType evaluateTypePHPDoc(AbstractTypeGoal goal, int timeout);

	public IEvaluatedType evaluateTypePHPDoc(AbstractTypeGoal goal);

	public IEvaluatedType evaluateTypeHeavy(AbstractTypeGoal goal, int timeout);

	public IEvaluatedType evaluateType(AbstractTypeGoal goal);

	public IEvaluatedType evaluateType(AbstractTypeGoal goal, int timeLimit);

	public IEvaluatedType evaluateType(AbstractTypeGoal goal, IPruner pruner);
}
