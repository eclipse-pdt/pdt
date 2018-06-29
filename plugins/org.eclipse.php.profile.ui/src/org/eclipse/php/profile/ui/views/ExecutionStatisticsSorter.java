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
import org.eclipse.php.profile.core.data.ProfilerFunctionData;
import org.eclipse.php.profile.ui.ProfilerUiPlugin;
import org.eclipse.php.profile.ui.preferences.PreferenceKeys;

/**
 * Execution statistics sorter.
 */
public class ExecutionStatisticsSorter extends AbstractTableSorter {

	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		TreeElement i1 = (TreeElement) e1;
		TreeElement i2 = (TreeElement) e2;
		Object d1 = i1.getData();
		Object d2 = i2.getData();

		boolean showAsPercentage = ProfilerUiPlugin.getDefault().getPreferenceStore()
				.getBoolean(PreferenceKeys.EXECUTION_VIEW_SHOW_AS_PERCENTAGE);

		if (d1 instanceof ProfilerFunctionData && d2 instanceof ProfilerFunctionData) {
			ProfilerFunctionData f1 = (ProfilerFunctionData) d1;
			ProfilerFunctionData f2 = (ProfilerFunctionData) d2;
			switch (getColumn()) {
			case 1:
				return compare(f1.getCallsCount(), f2.getCallsCount());
			case 2:
				double averageOwnTime1 = f1.getOwnTime();
				double averageOwnTime2 = f2.getOwnTime();
				if (f1.getCallsCount() > 0) {
					averageOwnTime1 = f1.getOwnTime() / f1.getCallsCount() / f1.getTotalTime();
				}

				if (f2.getCallsCount() > 0) {
					averageOwnTime2 = f2.getOwnTime() / f2.getCallsCount() / f2.getTotalTime();
				}
				return compare(averageOwnTime1, averageOwnTime2);
			case 3:
				double ownTime1 = f1.getOwnTime();
				double ownTime2 = f2.getOwnTime();
				if (showAsPercentage) {
					if (ownTime1 > 0) {
						ownTime1 = ownTime1 / f1.getTotalTime();
					}
					if (ownTime2 > 0) {
						ownTime2 = ownTime2 / f2.getTotalTime();
					}
				}
				return compare(ownTime1, ownTime2);
			case 4:
				double othersTime1 = f1.getTotalTime() - f1.getOwnTime();
				double othersTime2 = f2.getTotalTime() - f2.getOwnTime();
				if (showAsPercentage) {
					if (othersTime1 > 0) {
						othersTime1 = othersTime1 / f1.getTotalTime();
					}
					if (othersTime2 > 0) {
						othersTime2 = othersTime2 / f2.getTotalTime();
					}
				}
				return compare(othersTime1, othersTime2);

			case 5:
				return compare(f1.getTotalTime(), f2.getTotalTime());
			}
		}
		return super.compare(viewer, d1, d2);
	}
}
