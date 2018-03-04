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
import org.eclipse.php.profile.ui.ProfilerUIConstants;
import org.eclipse.php.profile.ui.ProfilerUiPlugin;
import org.eclipse.ui.PartInitException;

/**
 * Profiling monitor element.
 */
public class ProfilingMonitorElement {

	private ProfilerDB fProfilerDB;
	private ProfilingMonitorViewElement[] fChildren;

	public ProfilingMonitorElement(ProfilerDB profilerDB) {
		fProfilerDB = profilerDB;

		try {
			fChildren = new ProfilingMonitorViewElement[] {
					new ProfilingMonitorViewElement(this, ProfilerUIConstants.PROFILER_INFO_VIEW),
					new ProfilingMonitorViewElement(this, ProfilerUIConstants.EXECUTION_STATISTICS_VIEW),
					new ProfilingMonitorViewElement(this, ProfilerUIConstants.EXECUTION_FLOW_VIEW),
					// new ProfilingMonitorViewElement(this,
					// ProfilerUIConstants.CODE_COVERAGE_SUMMARY_VIEW),
			};
		} catch (PartInitException e) {
			ProfilerUiPlugin.log(e);
		}
	}

	public ProfilerDB getProfilerDB() {
		return fProfilerDB;
	}

	public ProfilingMonitorViewElement[] getChildren() {
		return fChildren;
	}
}
