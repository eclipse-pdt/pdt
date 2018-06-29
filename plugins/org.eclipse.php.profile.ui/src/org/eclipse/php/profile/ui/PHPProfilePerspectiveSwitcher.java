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

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.internal.ui.viewers.AsynchronousSchedulingRuleFactory;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.php.profile.core.engine.IProfileSessionListener;
import org.eclipse.php.profile.core.engine.ProfilerDB;
import org.eclipse.php.profile.ui.preferences.PreferenceKeys;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.*;

/**
 * Class responsible for switching PHP Profile perspective in case of receiving
 * profiling session result data.
 * 
 * @author Bartlomiej Laczkowski
 */
public class PHPProfilePerspectiveSwitcher implements IProfileSessionListener {

	private abstract class SwitchJob extends Job {

		public SwitchJob() {
			super("Profile Perspective Switch Job"); //$NON-NLS-1$
			setSystem(true);
			setPriority(Job.INTERACTIVE);
			setRule(AsynchronousSchedulingRuleFactory.getDefault().newSerialPerObjectRule(this));
		}

		@Override
		protected IStatus run(final IProgressMonitor monitor) {
			if (monitor.isCanceled()) {
				return Status.CANCEL_STATUS;
			}
			Display asyncDisplay = PlatformUI.getWorkbench().getDisplay();
			if (asyncDisplay == null || asyncDisplay.isDisposed()) {
				return Status.CANCEL_STATUS;
			}
			asyncDisplay.asyncExec(new Runnable() {
				@Override
				public void run() {
					IStatus result = null;
					Throwable throwable = null;
					try {
						if (monitor.isCanceled()) {
							result = Status.CANCEL_STATUS;
						} else {
							result = runInUIThread(monitor);
						}
					} catch (Throwable t) {
						throwable = t;
					} finally {
						if (result == null) {
							result = new Status(IStatus.ERROR, PlatformUI.PLUGIN_ID, IStatus.ERROR, "Error", throwable); //$NON-NLS-1$
						}
						done(result);
					}
				}
			});
			return Job.ASYNC_FINISH;
		}

		public abstract IStatus runInUIThread(IProgressMonitor monitor);

	}

	@Override
	public void profileSessionAdded(ProfilerDB db) {
		final String shouldAsk = ProfilerUiPlugin.getDefault().getPreferenceStore()
				.getString(PreferenceKeys.OPEN_PROFILE_PERSPECTIVE_ON_SESSION_DATA);
		if (shouldAsk.equals(MessageDialogWithToggle.NEVER)) {
			return;
		}
		SwitchJob switchJob = new SwitchJob() {
			@Override
			public IStatus runInUIThread(IProgressMonitor monitor) {
				IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
				if (window != null) {
					Shell shell = window.getShell();
					if (shell != null) {
						shell.forceActive();
					}
				} else {
					return Status.CANCEL_STATUS;
				}
				if (!isProfilePerspectiveOpen(window)) {
					if (shouldAsk.equals(MessageDialogWithToggle.ALWAYS) || shouldOpenPerspective(window)) {
						switchToProfilePerspective(window);
						return Status.OK_STATUS;
					}
				}
				return Status.CANCEL_STATUS;
			};
		};
		switchJob.schedule();
	}

	@Override
	public void profileSessionRemoved(ProfilerDB db) {
		// ignore
	}

	@Override
	public void currentSessionChanged(ProfilerDB current) {
		// ignore
	}

	private boolean isProfilePerspectiveOpen(IWorkbenchWindow window) {
		boolean isCurrent = false;
		IWorkbenchPage page = window.getActivePage();
		if (page != null) {
			IPerspectiveDescriptor perspectiveDescriptor = page.getPerspective();
			if (perspectiveDescriptor != null) {
				isCurrent = ProfilerUIConstants.PROFILE_PERSPECTIVE.equals(perspectiveDescriptor.getId());
			}
		}
		return isCurrent;
	}

	private boolean shouldOpenPerspective(IWorkbenchWindow window) {
		// Activate the shell if necessary so the prompt is visible
		Shell shell = window.getShell();
		Shell modal = getModalDialogOpen(shell);
		if (shell.getMinimized()) {
			shell.setMinimized(false);
			if (modal != null) {
				modal.setFocus();
			}
		}
		MessageDialogWithToggle dialog = MessageDialogWithToggle.openYesNoQuestion(shell,
				PHPProfileUIMessages.getString("ProfilePerspectiveHandler_0"), //$NON-NLS-1$
				PHPProfileUIMessages.getString("ProfilePerspectiveHandler_1"), null, false, //$NON-NLS-1$
				ProfilerUiPlugin.getDefault().getPreferenceStore(),
				PreferenceKeys.OPEN_PROFILE_PERSPECTIVE_ON_SESSION_DATA);
		return (dialog.getReturnCode() == IDialogConstants.YES_ID);
	}

	private Shell getModalDialogOpen(Shell shell) {
		Shell[] shells = shell.getShells();
		for (int i = 0; i < shells.length; i++) {
			Shell dialog = shells[i];
			if ((dialog.getStyle() & (SWT.APPLICATION_MODAL | SWT.PRIMARY_MODAL | SWT.SYSTEM_MODAL)) > 0) {
				return dialog;
			}
		}
		return null;
	}

	private void switchToProfilePerspective(IWorkbenchWindow window) {
		try {
			Shell shell = window.getShell();
			Shell dialog = getModalDialogOpen(shell);
			window.getWorkbench().showPerspective(ProfilerUIConstants.PROFILE_PERSPECTIVE, window);
			if (dialog != null) {
				dialog.setFocus();
			}
		} catch (WorkbenchException e) {
			ProfilerUiPlugin.log(e);
		}
	}

}
