/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui;

import java.util.ArrayList;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.php.internal.core.phpModel.parser.PHPProjectModel;
import org.eclipse.php.internal.core.phpModel.parser.PHPWorkspaceModelManager;

/**
 * This class is the content provider that displays only containers.
 * The displayed elements are the porjects and folders in the workspace. No files are displayed
 * 
 * @author Eden K., 2007
 *
 */
public class ContainerContentProvider extends StandardPHPElementContentProvider {

	static final String[] FILTERS = {".settings", ".project", ".projectOptions", ".cache", ".classpath" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$

	protected Object[] getChildrenInternal(Object parentElement) {
		if (!exists(parentElement))
			return NO_CHILDREN;

		if (parentElement instanceof PHPWorkspaceModelManager)
			return getPHPProjects((PHPWorkspaceModelManager) parentElement);

		if (parentElement instanceof IProject)
			return getProjectChildren((IProject) parentElement, FILTERS);

		if (parentElement instanceof PHPProjectModel)
			return getProjectChildren((PHPProjectModel) parentElement, FILTERS);

		if (parentElement instanceof IFolder)
			return getFolderChildren((IContainer) parentElement, FILTERS);

		return NO_CHILDREN;
	}

	protected Object[] getFolderChildren(IContainer folder, String[] filterNames) {
		try {
			IResource[] members = folder.members();
			ArrayList folderList = new ArrayList();

			for (int i = 0; i < members.length; i++) {
				IResource member = members[i];

				boolean filterOut = false;
				if (filterNames != null)
					for (int j = 0; j < filterNames.length; j++) {
						if (filterNames[j].equals(member.getName())) {
							filterOut = true;
							break;
						}
					}
				if (filterOut)
					continue;

				if (member instanceof IFolder)
					folderList.add(member);

			}
			return folderList.toArray();
		} catch (CoreException e) {
			return NO_CHILDREN;
		}
	}

}
