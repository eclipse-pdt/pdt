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
import org.eclipse.php.composer.core.ComposerCLIParams;
import org.eclipse.php.composer.core.ComposerService;
import org.eclipse.php.composer.internal.ui.ComposerUIPlugin;

/**
 * @author Wojciech Galanciak, 2013
 * 
 */
public class InstallDependenciesJob extends WorkspaceJob {

	private ComposerService composer;
	private ComposerCLIParams[] extraOptions;

	public InstallDependenciesJob(ComposerService composer) {
		this(composer, new ComposerCLIParams[0]);
	}

	public InstallDependenciesJob(ComposerService composer, ComposerCLIParams... extraOptions) {
		super(Messages.ResolveDependenciesJob_Name);
		this.composer = composer;
		this.extraOptions = extraOptions;
		setUser(false);
	}

	public IStatus runInWorkspace(IProgressMonitor monitor) {
		monitor.beginTask(Messages.ResolveDependenciesJob_Title, IProgressMonitor.UNKNOWN);
		try {
			composer.resolve(monitor, ComposerCLIParams.toStringArray(extraOptions));
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
		} finally {
			monitor.done();
		}
		return Status.OK_STATUS;
	}

}
