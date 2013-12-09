/*******************************************************************************
 * Copyright (c) 2014 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.libfolders;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.core.libfolders.LibraryFolderManager;
import org.eclipse.php.internal.core.PHPCorePlugin;

/**
 * A workspace job for marking a list of source folder as library folders.
 * 
 * @author Kaloyan Raev
 */
public class AutoDetectLibraryFolderJob extends WorkspaceJob {

	private IModelElement[] fFolders;

	/**
	 * Constructor for the job.
	 * 
	 * @param project
	 *            a handy reference to the project, which contains the given
	 *            folders
	 * @param folders
	 *            an array of source folders
	 */
	public AutoDetectLibraryFolderJob(IProject project, IModelElement[] folders) {
		super(NLS.bind(Messages.AutoDetectLibraryFolderListener_JobName,
				project.getName()));
		fFolders = folders;
	}

	@Override
	public IStatus runInWorkspace(IProgressMonitor monitor)
			throws CoreException {
		LibraryFolderManager lfm = LibraryFolderManager.getInstance();

		try {
			lfm.useAsLibraryFolder(fFolders, monitor);
		} catch (Exception e) {
			return new Status(IStatus.ERROR, PHPCorePlugin.ID,
					e.getLocalizedMessage(), e);
		}
		return Status.OK_STATUS;
	}

}
