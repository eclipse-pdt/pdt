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

package org.eclipse.php.internal.core.ti.goals;

import org.eclipse.php.internal.core.ti.IContext;

public interface IGoal {
	
	public enum State {
		DONE("Done"),
		WAITING("Waiting"),
		PRUNED("Pruned"),
		RECURSIVE("Recursive"),
		;
		
		private String name;
		
		State(String name) {
			this.name = name;
		}
		
		public String toString() {
			return name;
		}
	}
	
	public static final IGoal[] NO_GOALS = new IGoal[0];

	/**
	 * Returns context, in which this goal should be considered.
	 * Context contains, for example, instance of class the method is called on,
	 * pre-calculated scope or something like that.
	 *
	 * @return The context of this goal, or <code>null</code> is there is none.
	 */
	IContext getContext();
}
