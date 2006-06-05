/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.core.project.build;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.php.core.PHPCorePlugin;
import org.eclipse.php.core.phpModel.parser.PHPWorkspaceModelManager;
import org.eclipse.php.core.project.PHPNature;

public class PhpResourceDeltaBuildVisitor implements IResourceDeltaVisitor {

	private IProgressMonitor monitor;

	public PhpResourceDeltaBuildVisitor(IProgressMonitor monitor) {
		this.monitor = monitor;
	}

	
	public boolean visit(IResourceDelta delta) throws CoreException {
		switch (delta.getResource().getType()) {
			//only process files with PHP content type
			case IResource.FILE:
				processFileDelta(delta);
				return false;

			//only process projects with PHP nature
			case IResource.PROJECT:
				return processProjectDelta(delta);

			default:
				return true;
		}
	}

	private void processFileDelta(IResourceDelta fileDelta) {
		IFile file = (IFile) fileDelta.getResource();
		switch (fileDelta.getKind()) {
			case IResourceDelta.ADDED:
				PHPWorkspaceModelManager.getInstance().addFileToModel(file);
				return;
			case IResourceDelta.REMOVED:
				PHPWorkspaceModelManager.getInstance().removeFileFromModel(file);
				return;
			case IResourceDelta.CHANGED:
		}
	}

	private boolean processProjectDelta(IResourceDelta projectDelta) {
		IProject project = (IProject) projectDelta.getResource();
		try {
			if (!project.hasNature(PHPNature.ID)) {
				return false;
			}
		} catch (CoreException e) {
			PHPCorePlugin.log(e);
			return false;
		}

		return true;
	}
}
