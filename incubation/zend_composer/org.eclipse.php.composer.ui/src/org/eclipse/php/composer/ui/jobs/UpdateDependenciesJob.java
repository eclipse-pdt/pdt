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
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.php.composer.core.ComposerService;
import org.eclipse.php.composer.internal.ui.ComposerUIPlugin;

/**
 * @author Wojciech Galanciak, 2013
 * 
 */
public class UpdateDependenciesJob extends WorkspaceJob {

	private ComposerService composer;

	public UpdateDependenciesJob(ComposerService composer) {
		super(Messages.UpdateDependenciesJob_Name);
		this.composer = composer;
		setUser(false);
	}

	@Override
	public IStatus runInWorkspace(IProgressMonitor monitor) {
		monitor.beginTask(Messages.UpdateDependenciesJob_Title, IProgressMonitor.UNKNOWN);
		composer.update(monitor);
		if (composer.getError() != null) {
			return new Status(IStatus.ERROR, ComposerUIPlugin.PLUGIN_ID, Messages.InstallDependenciesJob_ErrorTitle,
					composer.getError());

		} else {
			try {
				IContainer root = composer.getRoot();
				root.refreshLocal(IResource.DEPTH_ONE, monitor);
				ComposerService.getVendor(root).refreshLocal(IResource.DEPTH_INFINITE, monitor);
			} catch (CoreException e) {
				ComposerUIPlugin.logError(e);
			}
		}
		monitor.done();
		return Status.OK_STATUS;
	}
}
