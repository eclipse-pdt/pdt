/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/

package org.eclipse.php.internal.core.ti.evaluators;

import org.eclipse.php.internal.core.ti.goals.IGoal;


public abstract class AbstractGoalEvaluator implements IGoalEvaluator {

	protected final IGoal goal;

	public AbstractGoalEvaluator(IGoal goal) {
		this.goal = goal;
	}

	public IGoal getGoal() {
		return goal;
	}
}
