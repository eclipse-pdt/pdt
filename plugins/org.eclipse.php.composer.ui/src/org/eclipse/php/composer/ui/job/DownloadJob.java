/*******************************************************************************
 * Copyright (c) 2012, 2016 PDT Extension Group and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.ui.job;

import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.php.composer.api.packages.PharDownloader;
import org.eclipse.php.composer.core.ComposerPlugin;
import org.eclipse.php.composer.core.log.Logger;

public class DownloadJob extends Job {

	private IProject project;
	private PharDownloader downloader;
	private IProgressMonitor monitor;

	public DownloadJob(IProject project) {
		super("Downloading composer.phar");
		this.project = project;
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		try {

			this.monitor = monitor;

			monitor.beginTask("Downloading composer.phar from getcomposer.org", 3);

			downloader = new PharDownloader();
			InputStream resource = downloader.download();

			monitor.worked(1);
			IFile file = project.getFile("composer.phar");
			monitor.worked(1);

			file.create(resource, true, new NullProgressMonitor());
			file.refreshLocal(IResource.DEPTH_ZERO, new NullProgressMonitor());

			monitor.worked(1);

		} catch (Exception e) {
			Logger.logException(e);
			return new Status(Status.ERROR, ComposerPlugin.ID,
					"Error while downloading composer.phar. See {workspace}/.metadata/.log for details");
		} finally {
			monitor.done();
		}

		return Status.OK_STATUS;
	}

	@Override
	protected void canceling() {
		super.canceling();
		if (monitor != null && monitor.isCanceled() && downloader != null) {
			downloader.abort();
		}
	}
}
