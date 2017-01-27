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
package org.eclipse.php.composer.ui.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.php.composer.core.ComposerPlugin;
import org.eclipse.php.composer.core.buildpath.BuildPathManager;
import org.eclipse.php.composer.core.log.Logger;
import org.eclipse.php.composer.core.resources.IComposerProject;
import org.eclipse.ui.handlers.HandlerUtil;

public class UpdateBuildPathCommand extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = HandlerUtil.getActiveWorkbenchWindow(event).getActivePage().getSelection();

		if (selection instanceof IStructuredSelection) {
			Object item = ((IStructuredSelection) selection).getFirstElement();

			if (item instanceof IAdaptable) {
				IAdaptable adaptable = (IAdaptable) item;
				IProject project = ((IResource) adaptable.getAdapter(IResource.class)).getProject();
				IComposerProject composerProject = ComposerPlugin.getDefault().getComposerProject(project);

				final BuildPathManager bpManager = new BuildPathManager(composerProject);
				final String title = Messages.UpdateBuildPathCommand_JobName;
				Job job = new Job(title) {

					@Override
					protected IStatus run(IProgressMonitor monitor) {
						monitor.beginTask(title, IProgressMonitor.UNKNOWN);
						try {
							bpManager.update();
						} catch (CoreException e) {
							Logger.logException(e);
						} finally {
							monitor.done();
						}
						return Status.OK_STATUS;
					}
				};
				job.setUser(false);
				job.schedule();
			}
		}

		return null;
	}

}
