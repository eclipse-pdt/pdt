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
import org.eclipse.php.profile.core.engine.cachegrind.CacheGrindModelParser;
import org.eclipse.php.profile.ui.PHPProfileUIMessages;
import org.eclipse.php.profile.ui.ProfilerUIConstants;
import org.eclipse.php.profile.ui.ProfilerUIImages;
import org.eclipse.swt.graphics.Image;
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

	@Override
	public boolean performFinish() {
		Job importJob = new Job(PHPProfileUIMessages.getString("ImportSessionWizard.0")) { //$NON-NLS-1$
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {
					FileInputStream stream = new FileInputStream(page1.getSourceFile());
					ProfilerDB[] profilerDBs;
					if (page1.getSourceType() == ProfilerUIConstants.XDEBUG_TYPE) {
						profilerDBs = CacheGrindModelParser.build(stream);
					} else {
						profilerDBs = ProfilerDataSerializationUtil.deserialize(stream);
					}

					if (profilerDBs != null && profilerDBs.length > 0) {
						for (int i = 0; i < profilerDBs.length; ++i) {
							ProfileSessionsManager.addSession(profilerDBs[i]);
						}
					} else {
						final Display display = Display.getDefault();
						display.asyncExec(new Runnable() {
							@Override
							public void run() {
								MessageDialog.openError(display.getActiveShell(),
										PHPProfileUIMessages.getString("ImportSessionWizard.1"), //$NON-NLS-1$
										NLS.bind(PHPProfileUIMessages.getString("ImportSessionWizard.2"), //$NON-NLS-1$
												page1.getSourceFile()));
							}
						});
					}
				} catch (final IOException e) {
					final Display display = Display.getDefault();
					display.asyncExec(new Runnable() {
						@Override
						public void run() {
							MessageDialog.openError(display.getActiveShell(),
									PHPProfileUIMessages.getString("ImportSessionWizard.3"), //$NON-NLS-1$
									PHPProfileUIMessages.getString("ImportSessionWizard.4") + e.getMessage()); //$NON-NLS-1$
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

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setWindowTitle(PHPProfileUIMessages.getString("ImportSessionWizard.5")); //$NON-NLS-1$
	}

	@Override
	public void addPages() {
		page1 = new ImportSessionWizardFirstPage();
		addPage(page1);
	}

	@Override
	public Image getDefaultPageImage() {
		return ProfilerUIImages.get(ProfilerUIImages.IMG_WIZBAN_IMPORT_PROFILE_SESSIONS);
	}

}