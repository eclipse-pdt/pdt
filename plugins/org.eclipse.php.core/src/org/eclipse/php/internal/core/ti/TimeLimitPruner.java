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

package org.eclipse.php.internal.core.ti;

import org.eclipse.php.internal.core.ti.goals.IGoal;

/**
 * Pruner that decides whether to prune current goal depending
 * on whether the evaluation time exceeded the time limit or not.
 */
public class TimeLimitPruner implements IPruner {

	private long timeStart;
	private final long timeLimit;

	public TimeLimitPruner(long timeLimit) {
		this.timeLimit = timeLimit;
	}

	public void init() {
		this.timeStart = System.currentTimeMillis();
	}

	public boolean prune(IGoal goal) {
		if (timeLimit > 0 && System.currentTimeMillis() - timeStart > timeLimit) {
			return true;
		}
		return false;
	}
}
