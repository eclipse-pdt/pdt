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
package org.eclipse.php.profile.ui.wizards;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.php.profile.core.engine.ProfilerDB;
import org.eclipse.php.profile.ui.ProfilerUIConstants;
import org.eclipse.php.profile.ui.views.ProfilingMonitorElement;
import org.eclipse.php.profile.ui.views.ProfilingMonitorView;
import org.eclipse.php.profile.ui.views.ProfilingMonitorViewElement;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;

/**
 * Abstract session wizard.
 */
public abstract class AbstractSessionWizard extends Wizard {

	private ProfilerDB session;

	public ProfilerDB getSession() {
		return session;
	}

	public void setSession(ProfilerDB session) {
		this.session = session;
	}

	protected void initSessionFromSelection(IWorkbench workbench) {
		IWorkbenchPage page = workbench.getActiveWorkbenchWindow().getActivePage();
		if (page != null) {
			ProfilingMonitorView view = (ProfilingMonitorView) page
					.findView(ProfilerUIConstants.PROFILING_MONITOR_VIEW);
			if (view != null) {
				ISelection selection = view.getSite().getSelectionProvider().getSelection();
				if (selection instanceof IStructuredSelection) {
					IStructuredSelection sSelection = (IStructuredSelection) selection;
					Object element = sSelection.getFirstElement();
					ProfilerDB session = null;
					if (element instanceof ProfilingMonitorViewElement) {
						session = ((ProfilingMonitorViewElement) element).getParent().getProfilerDB();
					} else if (element instanceof ProfilingMonitorElement) {
						session = ((ProfilingMonitorElement) element).getProfilerDB();
					}
					if (session != null) {
						setSession(session);
					}
				}
			}
		}
	}
}
