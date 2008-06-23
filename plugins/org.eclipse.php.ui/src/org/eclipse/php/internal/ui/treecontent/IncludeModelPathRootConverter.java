/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
/**
 * 
 */
package org.eclipse.php.internal.ui.treecontent;

import java.io.File;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.php.internal.core.phpModel.parser.IPhpModel;
import org.eclipse.php.internal.core.phpModel.parser.PHPIncludePathModelManager;
import org.eclipse.php.internal.core.phpModel.parser.PHPProjectModel;
import org.eclipse.php.internal.core.phpModel.parser.PHPWorkspaceModelManager;

class IncludeModelPathRootConverter {
	static public String toString(IPhpModel model) {
		// TODO assert model
		return String.valueOf(model.getID().replace(IPath.SEPARATOR, '?').replace(File.separatorChar, '!').replace(IPath.DEVICE_SEPARATOR, ';'));
	}

	// TODO modifier
	// TODO be consistent toString and toPhpModel
	static IPhpModel toPhpModel(String pathRoot, IProject[] projectsToFindIn) {
		String id = pathRoot.replace('?', IPath.SEPARATOR).replace('!', File.separatorChar).replace(';', IPath.DEVICE_SEPARATOR);
		for (int i = 0; i < projectsToFindIn.length; ++i) {
			PHPProjectModel projectModel = PHPWorkspaceModelManager.getInstance().getModelForProject(projectsToFindIn[i]);
			if (projectModel == null) {
				continue;
			}
			PHPIncludePathModelManager includeModelManager = (PHPIncludePathModelManager) projectModel.getModel(PHPIncludePathModelManager.COMPOSITE_INCLUDE_PATH_MODEL_ID);
			if (includeModelManager == null) {
				continue;
			}
			IPhpModel model = includeModelManager.getModel(id);
			if (model != null) {
				return model;
			}
		}
		return null;
	}

	// TODO toPhpModel
	static public IPhpModel from(String pathRoot, IProject project) {
		return toPhpModel(pathRoot, new IProject[] { project });
	}

	// TODO toPhpModel		
	static public IPhpModel from(String pathRoot) {
		return toPhpModel(pathRoot, ResourcesPlugin.getWorkspace().getRoot().getProjects());
	}
}