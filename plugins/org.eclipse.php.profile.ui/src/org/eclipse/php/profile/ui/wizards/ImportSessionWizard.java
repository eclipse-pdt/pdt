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

import java.io.FileInputStream;
import java.io.IOException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.profile.core.engine.ProfileSessionsManager;
import org.eclipse.php.profile.core.engine.ProfilerDB;
import org.eclipse.php.profile.core.engine.ProfilerDataSerializationUtil;
import org.eclipse.php.profile.ui.PHPProfileUIMessages;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;

/**
 * Import session wizard.
 */
public class ImportSessionWizard extends Wizard implements IImportWizard {

	private ImportSessionWizardFirstPage page1;

	public ImportSessionWizard() {
	}

	public boolean performFinish() {
		Job importJob = new Job(PHPProfileUIMessages
				.getString("ImportSessionWizard.0")) { //$NON-NLS-1$
			protected IStatus run(IProgressMonitor monitor) {
				try {
					ProfilerDB[] profilerDBs = ProfilerDataSerializationUtil
							.deserialize(new FileInputStream(page1
									.getSourceFile()));
					if (profilerDBs != null && profilerDBs.length > 0) {
						for (int i = 0; i < profilerDBs.length; ++i) {
							ProfileSessionsManager.addSession(profilerDBs[i]);
						}
					} else {
						final Display display = Display.getDefault();
						display.asyncExec(new Runnable() {
							public void run() {
								MessageDialog
										.openError(
												display.getActiveShell(),
												PHPProfileUIMessages
														.getString("ImportSessionWizard.1"), NLS.bind(PHPProfileUIMessages.getString("ImportSessionWizard.2"), page1.getSourceFile())); //$NON-NLS-1$ //$NON-NLS-2$
							}
						});
					}
				} catch (final IOException e) {
					final Display display = Display.getDefault();
					display.asyncExec(new Runnable() {
						public void run() {
							MessageDialog
									.openError(
											display.getActiveShell(),
											PHPProfileUIMessages
													.getString("ImportSessionWizard.3"), PHPProfileUIMessages.getString("ImportSessionWizard.4") + e.getMessage()); //$NON-NLS-1$ //$NON-NLS-2$
						}
					});
				}
				return Status.OK_STATUS;
			}
		};
		importJob.setUser(true);
		importJob.schedule();
		return true;
	}

	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setWindowTitle(PHPProfileUIMessages.getString("ImportSessionWizard.5")); //$NON-NLS-1$
	}

	public void addPages() {
		page1 = new ImportSessionWizardFirstPage();
		addPage(page1);
	}
}