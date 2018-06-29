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
