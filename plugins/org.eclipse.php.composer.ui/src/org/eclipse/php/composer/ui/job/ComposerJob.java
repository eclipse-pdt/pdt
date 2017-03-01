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
 *     Kaloyan Raev - [511744] Wizard freezes if no PHP executable is configured
 *******************************************************************************/
package org.eclipse.php.composer.ui.job;

import java.io.IOException;
import java.util.Objects;

import org.apache.commons.exec.ExecuteException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.window.Window;
import org.eclipse.php.composer.core.ComposerPlugin;
import org.eclipse.php.composer.core.launch.ExecutableNotFoundException;
import org.eclipse.php.composer.core.launch.ScriptLauncher;
import org.eclipse.php.composer.core.launch.ScriptNotFoundException;
import org.eclipse.php.composer.core.launch.environment.ComposerEnvironmentFactory;
import org.eclipse.php.composer.core.launch.environment.Environment;
import org.eclipse.php.composer.core.log.Logger;
import org.eclipse.php.composer.ui.ComposerUIPlugin;
import org.eclipse.php.composer.ui.job.runner.ComposerFailureMessageRunner;
import org.eclipse.php.composer.ui.job.runner.MissingExecutableRunner;
import org.eclipse.php.composer.ui.terminal.ComposerLauncher;
import org.eclipse.swt.widgets.Display;

abstract public class ComposerJob extends Job {

	private IProject project;
	private IProgressMonitor monitor;
	private boolean cancelling = false;
	private ScriptLauncher launcher;

	protected static final IStatus ERROR_STATUS = new Status(Status.ERROR, ComposerPlugin.ID,
			Messages.ComposerJob_ErrorMessage);

	public ComposerJob(String name) {
		super(name);
	}

	public ComposerJob(IProject project, String name) {
		this(name);
		this.setProject(project);
	}

	@Override
	public boolean belongsTo(Object family) {
		return Objects.equals(ComposerUIPlugin.FAMILY_COMPOSER, family) || Objects.equals(project, family)
				|| (project != null && Objects.equals(project.getName(), family));
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

			boolean tryAgain = false;
			do {
				try {
					Environment env = new ComposerEnvironmentFactory().getEnvironment(getProject());
					if (env == null) {
						throw new ExecutableNotFoundException(Messages.ComposerJob_CannotFindExe);
					}

					launcher = new ComposerLauncher(env, getProject());

					tryAgain = false;
				} catch (ExecutableNotFoundException e) {
					// inform the user of the missing executable
					MissingExecutableRunner runner = new MissingExecutableRunner();
					Display.getDefault().syncExec(runner);
					if (runner.getReturnCode() == Window.OK) {
						tryAgain = true;
					} else {
						callDoOnLauncherRunException = true;
						doOnLauncherRunException(e);
						return Status.OK_STATUS;
					}
				} catch (ScriptNotFoundException e) {
					callDoOnLauncherRunException = true;
					doOnLauncherRunException(e);
					if (tryAgain) {
						Display.getDefault().asyncExec(
								new ComposerFailureMessageRunner(Messages.ComposerJob_DownloadErrorMessage, monitor));
						return Status.OK_STATUS;
					} else {
						// run the downloader
						DownloadJob job = new DownloadJob(getProject());
						job.schedule();
						job.join();
						tryAgain = true;
					}
				}
			} while (tryAgain);

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

}
