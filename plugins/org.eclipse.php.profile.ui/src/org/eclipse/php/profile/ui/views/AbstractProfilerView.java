/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.profile.ui.views;

import org.eclipse.php.profile.core.engine.ProfilerDB;
import org.eclipse.ui.part.ViewPart;

/**
 * Abstract profiler view.
 */
public abstract class AbstractProfilerView extends ViewPart {

	/**
	 * Sets profiler database associated with this view
	 * 
	 * @param profiler
	 *            database
	 */
	public abstract void setInput(ProfilerDB profilerDB);

	/**
	 * Returns profiler database associated with this view
	 * 
	 * @return profiler database
	 */
	public abstract ProfilerDB getInput();
}
