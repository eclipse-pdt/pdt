/*******************************************************************************
 * Copyright (c) 2019 Dawid Pakuła and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Dawid Pakuła - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.ui.job;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.php.composer.core.log.Logger;

public class AfterInstallJob extends Job {

	private IProject project;

	public AfterInstallJob(IProject project) {
		super("After composer install"); //$NON-NLS-1$
		this.project = project;
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		try {
			project.refreshLocal(IResource.DEPTH_INFINITE, SubMonitor.convert(monitor));
			new UpdateBuildPathJob(project).schedule();

		} catch (CoreException e) {
			Logger.logException(e);
		}

		return Status.OK_STATUS;
	}

}
