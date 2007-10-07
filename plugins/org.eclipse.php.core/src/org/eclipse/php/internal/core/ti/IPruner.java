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
 * Pruner stops processing working queue by pruning some goals, depending on
 * time limits, goals count or more complex criteria. Pruners are created per
 * every evaluation.
 */
public interface IPruner {

	/**
	 * This method is called when evaluating were started.
	 */
	void init();

	/**
	 * Called every time before getting new goal from evaluating queue.
	 * @param goal Goal to prune
	 */
	boolean prune(IGoal goal);
}
