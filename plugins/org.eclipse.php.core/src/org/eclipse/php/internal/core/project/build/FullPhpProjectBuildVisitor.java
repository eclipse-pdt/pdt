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
package org.eclipse.php.internal.core.project.build;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.internal.core.documentModel.validate.PHPProblemsValidator;
import org.eclipse.php.internal.core.phpModel.PHPModelUtil;
import org.eclipse.php.internal.core.phpModel.parser.PHPWorkspaceModelManager;

public class FullPhpProjectBuildVisitor implements IResourceVisitor {

	private IProgressMonitor monitor;
	private PHPProblemsValidator validator = PHPProblemsValidator.getInstance();

	public FullPhpProjectBuildVisitor(IProgressMonitor monitor) {
		this.monitor = monitor;
	}

	public boolean visit(IResource resource) {
		if(monitor.isCanceled()){
			return false;
		}
		// parse each PHP file with the parserFacade which adds it to
		// the model
		if (resource.getType() == IResource.FILE) {
			handle((IFile) resource);
			return false;
		}

		if (resource.getType() == IResource.PROJECT) {
			return handle((IProject) resource);
		}

		return true;
	}

	private boolean handle(IProject project) {
		//check if the project contains PHP
		if (PHPWorkspaceModelManager.getInstance().getModelForProject(project, true) == null) {
			return false;
		}

		return true;
	}

	private void handle(IFile file) {
		if (monitor.isCanceled()) {
			return;
		}
		if (!PHPModelUtil.isPhpFile(file)) {
			return;
		}
		monitor.subTask(NLS.bind("Parsing: {0} ...", file.getFullPath().toPortableString()));

		PHPWorkspaceModelManager.getInstance().addFileToModel(file);		
		validator.validateFile(file);

	}
}
