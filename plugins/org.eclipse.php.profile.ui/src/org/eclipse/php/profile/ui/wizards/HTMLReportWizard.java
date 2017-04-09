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
package org.eclipse.php.profile.ui.wizards;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.php.profile.core.engine.ProfilerDB;
import org.eclipse.php.profile.ui.PHPProfileUIMessages;
import org.eclipse.php.profile.ui.ProfilerUIConstants;
import org.eclipse.php.profile.ui.ProfilerUiPlugin;
import org.eclipse.php.profile.ui.report.HTMLReporter;
import org.eclipse.php.profile.ui.utils.ProfileUITools;
import org.eclipse.php.profile.ui.views.AbstractProfilerView;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.progress.WorkbenchJob;

/**
 * HTML report wizard.
 */
public class HTMLReportWizard extends AbstractSessionWizard implements IExportWizard {

	private HTMLReportWizardFirstPage page1;

	public HTMLReportWizard() {
	}

	private void openView(final String viewID, final ProfilerDB input) {
		IViewPart view = ProfileUITools.findExistingView(viewID);
		if (view instanceof AbstractProfilerView) {
			((AbstractProfilerView) view).setInput(input);
		}
	}

	public boolean performFinish() {
		WorkbenchJob reportJob = new WorkbenchJob(PHPProfileUIMessages.getString("HTMLReportWizard.0")) { //$NON-NLS-1$
			public IStatus runInUIThread(IProgressMonitor monitor) {
				try {
					String targetFile = page1.getTargetFile();
					ProfilerDB session = page1.getSession();

					List<String> viewIds = new ArrayList<String>();
					if (page1.isExportCommonInfo()) {
						openView(ProfilerUIConstants.PROFILER_INFO_VIEW, session);
						viewIds.add(ProfilerUIConstants.PROFILER_INFO_VIEW);
					}
					if (page1.isExportExecutionStatistics()) {
						openView(ProfilerUIConstants.EXECUTION_STATISTICS_VIEW, session);
						viewIds.add(ProfilerUIConstants.EXECUTION_STATISTICS_VIEW);
					}
					if (page1.isExportExecutionFlow()) {
						openView(ProfilerUIConstants.EXECUTION_FLOW_VIEW, session);
						viewIds.add(ProfilerUIConstants.EXECUTION_FLOW_VIEW);
					}

					PrintWriter out = new PrintWriter(new FileWriter(targetFile));
					HTMLReporter.generateReport(session, (String[]) viewIds.toArray(new String[viewIds.size()]), out);
					HTMLReporter.saveImages(new File(targetFile).getParent());
					out.close();

				} catch (IOException e) {
					ProfilerUiPlugin.log(e);
				}

				return Status.OK_STATUS;
			}
		};
		reportJob.setUser(true);
		reportJob.schedule();

		return true;
	}

	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setWindowTitle(PHPProfileUIMessages.getString("HTMLReportWizard.1")); //$NON-NLS-1$
		initSessionFromSelection(workbench);
	}

	public void addPages() {
		page1 = new HTMLReportWizardFirstPage(getSession());
		addPage(page1);
	}
}
