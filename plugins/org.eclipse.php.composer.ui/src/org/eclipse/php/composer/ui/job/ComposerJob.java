/*******************************************************************************
 * Copyright (c) 2012, 2016, 2017 PDT Extension Group and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *     Kaloyan Raev - [501269] externalize strings
 *******************************************************************************/
package org.eclipse.php.composer.ui.job;

import java.io.IOException;

import javax.inject.Inject;

import org.apache.commons.exec.ExecuteException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.php.composer.core.ComposerPlugin;
import org.eclipse.php.composer.core.launch.ExecutableNotFoundException;
import org.eclipse.php.composer.core.launch.ScriptLauncher;
import org.eclipse.php.composer.core.launch.ScriptLauncherManager;
import org.eclipse.php.composer.core.launch.ScriptNotFoundException;
import org.eclipse.php.composer.core.launch.environment.ComposerEnvironmentFactory;
import org.eclipse.php.composer.core.launch.execution.ExecutionResponseAdapter;
import org.eclipse.php.composer.core.log.Logger;
import org.eclipse.php.composer.ui.ComposerUIPlugin;
import org.eclipse.php.composer.ui.handler.ConsoleResponseHandler;
import org.eclipse.php.composer.ui.job.runner.ComposerFailureMessageRunner;
import org.eclipse.php.composer.ui.job.runner.MissingExecutableRunner;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

abstract public class ComposerJob extends Job {

	private IProject project;
	private IProgressMonitor monitor;
	private boolean cancelling = false;
	private ScriptLauncher launcher;

	@Inject
	public ScriptLauncherManager manager;

	protected static final IStatus ERROR_STATUS = new Status(Status.ERROR, ComposerPlugin.ID,
			Messages.ComposerJob_ErrorMessage);

	public ComposerJob(String name) {
		super(name);

		ContextInjectionFactory.inject(this, ComposerUIPlugin.getDefault().getEclipseContext());
	}

	public ComposerJob(IProject project, String name) {
		this(name);
		this.setProject(project);
	}

	@Override
	protected void canceling() {

		if (cancelling || launcher == null || !monitor.isCanceled()) {
			return;
		}

		launcher.abort();
		monitor.done();
		cancelling = true;
	}

	@Override
	protected IStatus run(final IProgressMonitor monitor) {
		boolean callDoOnLauncherRunException = false;
		try {

			this.monitor = monitor;

			try {
				launcher = manager.getLauncher(ComposerEnvironmentFactory.FACTORY_ID, getProject());
			} catch (ExecutableNotFoundException e) {
				callDoOnLauncherRunException = true;
				doOnLauncherRunException(e);
				// inform the user of the missing executable
				Display.getDefault().asyncExec(new MissingExecutableRunner());
				return Status.OK_STATUS;
			} catch (ScriptNotFoundException e) {
				callDoOnLauncherRunException = true;
				doOnLauncherRunException(e);
				// run the downloader
				Display.getDefault().asyncExec(new DownloadRunner());
				return Status.OK_STATUS;
			}

			launcher.addResponseListener(new ConsoleResponseHandler());
			launcher.addResponseListener(new ExecutionResponseAdapter() {
				public void executionFailed(final String response, final Exception exception) {
					Display.getDefault().asyncExec(new ComposerFailureMessageRunner(response, monitor));
				}

				@Override
				public void executionMessage(String message) {
					if (monitor != null && message != null) {
						monitor.subTask(message);
						monitor.worked(1);
					}
				}
			});

			monitor.beginTask(getName(), IProgressMonitor.UNKNOWN);
			monitor.worked(1);
			launch(launcher);
			monitor.worked(1);

			// refresh project
			if (getProject() != null) {
				getProject().refreshLocal(IProject.DEPTH_INFINITE, null);
				monitor.worked(1);
			}
		} catch (Exception e) {
			if (!callDoOnLauncherRunException) {
				callDoOnLauncherRunException = true;
				doOnLauncherRunException(e);
			}
			Logger.logException(e);
			return ERROR_STATUS;
		} finally {
			monitor.done();
		}

		return Status.OK_STATUS;
	}

	protected void doOnLauncherRunException(Exception e) {
	}

	abstract protected void launch(ScriptLauncher launcher) throws ExecuteException, IOException, InterruptedException;

	public IProject getProject() {
		return project;
	}

	public void setProject(IProject project) {
		this.project = project;
	}

	private class DownloadRunner implements Runnable {

		@Override
		public void run() {

			Shell shell = Display.getCurrent().getActiveShell();

			if (shell == null) {
				Logger.debug("Unable to get shell for message dialog."); //$NON-NLS-1$
				return;
			}

			if (MessageDialog.openConfirm(shell, Messages.ComposerJob_DownloadDialogTitle,
					Messages.ComposerJob_DownloadDialogMessage)) {
				DownloadJob job = new DownloadJob(getProject());
				job.addJobChangeListener(new JobChangeAdapter() {
					@Override
					public void done(IJobChangeEvent event) {
						// re-schedule the original job
						ComposerJob.this.schedule();
					}
				});
				job.setUser(true);
				job.schedule();
			}
		}
	}
}
