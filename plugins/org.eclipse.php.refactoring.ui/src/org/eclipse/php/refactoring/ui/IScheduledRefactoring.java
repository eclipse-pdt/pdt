/*******************************************************************************
 * Copyright (c) 2009, 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.ui;

import org.eclipse.core.runtime.jobs.ISchedulingRule;

public interface IScheduledRefactoring {

	/**
	 * The scheduling rule used to perform the refactoring.
	 * 
	 * @return {@link ISchedulingRule} not null
	 */
	public ISchedulingRule getSchedulingRule();

}
