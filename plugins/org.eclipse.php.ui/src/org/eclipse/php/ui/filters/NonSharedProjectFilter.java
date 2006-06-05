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
package org.eclipse.php.ui.filters;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.php.core.phpModel.parser.PHPProjectModel;
import org.eclipse.php.core.phpModel.parser.PHPWorkspaceModelManager;
import org.eclipse.team.core.RepositoryProvider;


/**
 * Filters non-shared projects and PHP projects. Non-shared projects are
 * projects that are not controlled by a team provider.
 * 
 * @since 2.1
 */
public class NonSharedProjectFilter extends ViewerFilter {

	/*
	 * @see ViewerFilter
	 */
	public boolean select(Viewer viewer, Object parent, Object element) {
		if (element instanceof IProject)
			return isSharedProject((IProject) element);

		if (element instanceof PHPProjectModel) {
			IProject project = PHPWorkspaceModelManager.getInstance().getProjectForModel((PHPProjectModel) element);
			return isSharedProject(project);
		}

		return true;
	}

	private boolean isSharedProject(IProject project) {
		return !project.isAccessible() || RepositoryProvider.isShared(project);
	}
}
