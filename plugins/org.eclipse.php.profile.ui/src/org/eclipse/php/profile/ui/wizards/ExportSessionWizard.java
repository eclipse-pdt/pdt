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

import java.io.FileOutputStream;
import java.io.IOException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.profile.core.engine.ProfilerDB;
import org.eclipse.php.profile.core.engine.ProfilerDataSerializationUtil;
import org.eclipse.php.profile.ui.PHPProfileUIMessages;
import org.eclipse.php.profile.ui.ProfilerUIImages;
import org.eclipse.php.profile.ui.ProfilerUiPlugin;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;

/**
 * Export session wizard.
 */
public class ExportSessionWizard extends AbstractSessionWizard implements IExportWizard {

	private ExportSessionWizardFirstPage page1;

	public ExportSessionWizard() {
	}

	@Override
	public boolean performFinish() {
		final ProfilerDB[] sessions = page1.getSessions();
		final String fileName = page1.getTargetFile();

		Job saveJob = new Job(PHPProfileUIMessages.getString("ExportSessionWizard.0")) { //$NON-NLS-1$
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {
					ProfilerDataSerializationUtil.serialize(sessions, new FileOutputStream(fileName));
				} catch (IOException e) {
					Display.getDefault().asyncExec(new Runnable() {
						@Override
						public void run() {
							MessageDialog.openError(Display.getDefault().getActiveShell(),
									PHPProfileUIMessages.getString("ExportSessionWizard.2"), //$NON-NLS-1$
									NLS.bind(PHPProfileUIMessages.getString("ExportSessionWizard.3"), fileName)); //$NON-NLS-1$
						}
					});
					ProfilerUiPlugin.log(e);
				}
				return Status.OK_STATUS;
			}
		};
		saveJob.setUser(true);
		saveJob.schedule();

		return true;
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setWindowTitle(PHPProfileUIMessages.getString("ExportSessionWizard.1")); //$NON-NLS-1$
		initSessionFromSelection(workbench);
	}

	@Override
	public void addPages() {
		page1 = new ExportSessionWizardFirstPage(getSession());
		addPage(page1);
	}

	@Override
	public Image getDefaultPageImage() {
		return ProfilerUIImages.get(ProfilerUIImages.IMG_WIZBAN_EXPORT_PROFILE_SESSIONS);
	}

}
