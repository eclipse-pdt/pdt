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
package org.eclipse.php.profile.ui;

import org.eclipse.php.internal.debug.ui.views.DebugBrowserView;
import org.eclipse.php.internal.debug.ui.views.DebugOutputView;
import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

/**
 * PHP profile perspective factory.
 */
public class PHPProfilePerspectiveFactory implements IPerspectiveFactory {

	public static final String ID_EXECUTION_VIEW = "org.eclipse.php.profile.ui.executionView"; //$NON-NLS-1$
	public static final String ID_PROFILE_MONITOR_VIEW = "org.eclipse.php.profile.ui.profilingMonitorView"; //$NON-NLS-1$
	public static final String ID_DEBUGGING_VIEW = "org.eclipse.php.profile.ui.debuggingView"; //$NON-NLS-1$

	@Override
	public void createInitialLayout(IPageLayout layout) {

		String editorArea = layout.getEditorArea();

		IFolderLayout executionFolder = layout.createFolder(ID_EXECUTION_VIEW, IPageLayout.TOP, 0.70f, editorArea);
		executionFolder.addView(ProfilerUIConstants.PROFILER_INFO_VIEW);
		executionFolder.addView(ProfilerUIConstants.EXECUTION_STATISTICS_VIEW);
		executionFolder.addView(ProfilerUIConstants.EXECUTION_FLOW_VIEW);
		// executionFolder.addView(ProfilerUIConstants.CODE_COVERAGE_SUMMARY_VIEW);
		executionFolder.addPlaceholder(ProfilerUIConstants.FUNCTION_INVOCATION_STATISTICS_VIEW + ":*"); //$NON-NLS-1$
		// executionFolder.addPlaceholder(ProfilerUIConstants.CODE_COVERAGE_VIEW
		// + ":*"); //$NON-NLS-1$

		IFolderLayout outlineFolder = layout.createFolder(ID_DEBUGGING_VIEW, IPageLayout.RIGHT, 0.5f, editorArea);
		outlineFolder.addPlaceholder(DebugOutputView.ID_PHPDebugOutput);
		outlineFolder.addPlaceholder(DebugBrowserView.ID_PHPBrowserOutput);
		outlineFolder.addPlaceholder("org.eclipse.debug.ui.ConsoleView"); //$NON-NLS-1$

		IFolderLayout navFolder = layout.createFolder(ID_PROFILE_MONITOR_VIEW, IPageLayout.LEFT, 0.25f,
				ID_EXECUTION_VIEW);
		navFolder.addView(ProfilerUIConstants.PROFILING_MONITOR_VIEW);

		layout.addPerspectiveShortcut("org.eclipse.php.perspective"); //$NON-NLS-1$
		layout.addPerspectiveShortcut("org.eclipse.debug.ui.DebugPerspective"); //$NON-NLS-1$
		layout.addPerspectiveShortcut("org.eclipse.php.profile.ui.perspective"); //$NON-NLS-1$

		layout.addShowViewShortcut(ProfilerUIConstants.PROFILER_INFO_VIEW);
		layout.addShowViewShortcut(ProfilerUIConstants.EXECUTION_STATISTICS_VIEW);
		layout.addShowViewShortcut(ProfilerUIConstants.EXECUTION_FLOW_VIEW);
		// layout.addShowViewShortcut(ProfilerUIConstants.CODE_COVERAGE_SUMMARY_VIEW);
		layout.addShowViewShortcut(ProfilerUIConstants.PROFILING_MONITOR_VIEW);
		layout.addShowViewShortcut(ProfilerUIConstants.FUNCTION_INVOCATION_STATISTICS_VIEW);

	}
}
