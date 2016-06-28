/*******************************************************************************
 * Copyright (c) 2013, 2016 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.ui.jobs;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.*;
import org.eclipse.php.composer.core.ComposerService;
import org.eclipse.php.composer.internal.ui.ComposerUIPlugin;

/**
 * @author Wojciech Galanciak, 2013
 * 
 */
public class DumpAutoloadJob extends WorkspaceJob {

	private static final String AUTOLOAD_PHP = "autoload.php"; //$NON-NLS-1$
	private static final String COMPOSER_FOLDER = "composer"; //$NON-NLS-1$

	private ComposerService composer;

	public DumpAutoloadJob(ComposerService composer) {
		super(Messages.DumpAutoloadJob_JobName);
		this.composer = composer;
		setUser(false);
	}

	@Override
	public IStatus runInWorkspace(IProgressMonitor monitor) {
		monitor.beginTask(Messages.DumpAutoloadJob_JobTitle, IProgressMonitor.UNKNOWN);
		composer.dumpAutoload(monitor);
		if (composer.getError() != null) {
			return new Status(IStatus.ERROR, ComposerUIPlugin.PLUGIN_ID, Messages.InstallDependenciesJob_ErrorTitle,
					composer.getError());
		}
		try {
			IContainer root = composer.getRoot();
			root.refreshLocal(IResource.DEPTH_ONE, monitor);
			IContainer composerFolder = ComposerService.getVendor(root).getFolder(new Path(COMPOSER_FOLDER));
			if (composerFolder.exists()) {
				composerFolder.refreshLocal(IResource.DEPTH_ONE, monitor);
			}
			IFile autoloadFile = ComposerService.getVendor(root).getFile(new Path(AUTOLOAD_PHP));
			if (autoloadFile.exists()) {
				autoloadFile.refreshLocal(IResource.DEPTH_ZERO, monitor);
			}
		} catch (CoreException e) {
			return new Status(IStatus.ERROR, ComposerUIPlugin.PLUGIN_ID, Messages.DumpAutoloadJob_Error, e);
		}
		monitor.done();
		return Status.OK_STATUS;
	}

}
