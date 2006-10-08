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
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.php.core.documentModel.validate.PHPProblemsValidator;
import org.eclipse.php.core.phpModel.parser.PHPWorkspaceModelManager;

public class FullPhpProjectBuildVisitor implements IResourceVisitor {

	private PHPProblemsValidator validator = new PHPProblemsValidator();

	public boolean visit(IResource resource) {
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
		PHPWorkspaceModelManager.getInstance().addFileToModel(file);
		validator.validateFile(file);
	}
}
