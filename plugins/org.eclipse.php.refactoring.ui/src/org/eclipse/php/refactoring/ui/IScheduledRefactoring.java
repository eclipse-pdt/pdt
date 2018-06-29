/*******************************************************************************
 * Copyright (c) 2009, 2015 Zend Technologies and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
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
