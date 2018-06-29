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
package org.eclipse.php.profile.ui.preferences;

import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.php.profile.ui.ProfilerUiPlugin;

/**
 * Profiler UI preference keys.
 */
public class PreferenceKeys {

	public static final String OPEN_PROFILE_PERSPECTIVE_ON_SESSION_DATA = "org.eclipse.php.profile.ui.openProfilePerspectiveOnSessionData"; //$NON-NLS-1$

	public static final String EXECUTION_FLOW_SORT_ORDER = "executionFlowSortOrder"; //$NON-NLS-1$
	public static final String EXECUTION_VIEW_SHOW_AS_PERCENTAGE = "executionViewShowAsPercentage"; //$NON-NLS-1$
	public static final String EXECUTION_VIEW_GROUP_BY = "executionViewGroupBy"; //$NON-NLS-1$
	public static final String PROFILING_MONITOR_SORT_ORDER = "profilingMonitorSortOrder"; //$NON-NLS-1$
	public static final String EXECUTION_STATISTICS_VIEW_FILTERS = "executionStatisticsViewFilters"; //$NON-NLS-1$
	public static final String EXECUTION_STATISTICS_SELECTED_FILTER = "executionStatisticsSelectedFilter"; //$NON-NLS-1$
	public static final String IMPORT_TYPE = "importType"; //$NON-NLS-1$

	public static IPreferenceStore getPreferenceStore() {
		return ProfilerUiPlugin.getDefault().getPreferenceStore();
	}

	public static void initializeDefaultValues() {
		getPreferenceStore().setDefault(OPEN_PROFILE_PERSPECTIVE_ON_SESSION_DATA, MessageDialogWithToggle.PROMPT);
	}

	private PreferenceKeys() {
		// don't instantiate
	}
}
