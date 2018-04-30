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
package org.eclipse.php.profile.ui;

/**
 * Profiler UI constants.
 */
public class ProfilerUIConstants {

	public static final String PROFILE_PERSPECTIVE = "org.eclipse.php.profile.ui.perspective"; //$NON-NLS-1$
	public static final String EXECUTION_STATISTICS_VIEW = "org.eclipse.php.profile.ui.views.ExecutionStatisticsView"; //$NON-NLS-1$
	public static final String PROFILING_MONITOR_VIEW = "org.eclipse.php.profile.ui.views.ProfilingMonitorView"; //$NON-NLS-1$
	public static final String PROFILER_INFO_VIEW = "org.eclipse.php.profile.ui.views.ProfilerInformationView"; //$NON-NLS-1$
	public static final String EXECUTION_FLOW_VIEW = "org.eclipse.php.profile.ui.views.ExecutionFlowView"; //$NON-NLS-1$
	public static final String FUNCTION_INVOCATION_STATISTICS_VIEW = "org.eclipse.php.profile.ui.views.FunctionInvocationStatisticsView"; //$NON-NLS-1$
	public static final String CODE_COVERAGE_SUMMARY_VIEW = "org.eclipse.php.profile.ui.views.CodeCoverageSummaryView"; //$NON-NLS-1$
	public static final String CODE_COVERAGE_VIEW = "org.eclipse.php.debug.ui.views.CodeCoverageView"; //$NON-NLS-1$

	public static int SORT_NONE = 0;
	public static int SORT_ASCENDING = 1;
	public static int SORT_DESCENDING = 2;

	public static int GROUP_BY_FILE = 0;
	public static int GROUP_BY_CLASS = 1;
	public static int GROUP_BY_FUNCTION = 2;

	public static final int ECLIPSE_TYPE = 0;
	public static final int XDEBUG_TYPE = 1;
}
