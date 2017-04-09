/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
