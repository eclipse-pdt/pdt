/*******************************************************************************
 * Copyright (c) 2012, 2019 PDT Extension Group and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *     Kaloyan Raev - [501269] externalize strings
 *     Dawid Pakuła - Separate job
 *******************************************************************************/
package org.eclipse.php.composer.ui.job;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.php.composer.core.ComposerPlugin;
import org.eclipse.php.composer.core.buildpath.BuildPathManager;
import org.eclipse.php.composer.core.log.Logger;
import org.eclipse.php.composer.core.resources.IComposerProject;
import org.eclipse.php.composer.ui.commands.Messages;

public class UpdateBuildPathJob extends Job {

	private IProject project;

	public UpdateBuildPathJob(IProject project) {
		super(Messages.UpdateBuildPathCommand_JobName);
		this.project = project;
		setRule(ResourcesPlugin.getWorkspace().getRuleFactory().refreshRule(ResourcesPlugin.getWorkspace().getRoot()));
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		IComposerProject composerProject = ComposerPlugin.getDefault().getComposerProject(project);
		final BuildPathManager bpManager = new BuildPathManager(composerProject);
		monitor.beginTask(getName(), IProgressMonitor.UNKNOWN);
		try {
			bpManager.update();
		} catch (CoreException e) {
			Logger.logException(e);
		} finally {
			monitor.done();
		}
		try {
			project.build(IncrementalProjectBuilder.INCREMENTAL_BUILD, SubMonitor.convert(monitor));
		} catch (CoreException e) {
			Logger.logException(e);
		}
		return Status.OK_STATUS;
	}

}
