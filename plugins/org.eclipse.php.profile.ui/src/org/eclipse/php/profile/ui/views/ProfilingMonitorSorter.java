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

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

/**
 * Profiling monitor sorter.
 */
public class ProfilingMonitorSorter extends ViewerSorter {

	public static final int BY_DATE = 0;
	public static final int BY_FILENAME = 1;

	private int fMode = BY_DATE;

	public ProfilingMonitorSorter() {
	}

	public ProfilingMonitorSorter(int mode) {
		fMode = mode;
	}

	public void setMode(int mode) {
		fMode = mode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.ViewerSorter#compare(org.eclipse.jface.viewers
	 * .Viewer, java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		if (e1 instanceof ProfilingMonitorElement && e2 instanceof ProfilingMonitorElement) {
			ProfilingMonitorElement element1 = (ProfilingMonitorElement) e1;
			ProfilingMonitorElement element2 = (ProfilingMonitorElement) e2;

			if (fMode == BY_FILENAME) {
				return super.compare(viewer, element1.getProfilerDB().getGlobalData().getURI(),
						element2.getProfilerDB().getGlobalData().getURI());
			} else if (fMode == BY_DATE) {
				return element1.getProfilerDB().getProfileDate().compareTo(element2.getProfilerDB().getProfileDate());
			}
		}
		return 0; // super.compare(viewer, e1, e2);
	}
}
