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
package org.eclipse.php.ui.workingset;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.php.core.phpModel.parser.PHPWorkspaceModelManager;
import org.eclipse.php.ui.StandardPHPElementContentProvider;

class PHPWorkingSetPageContentProvider extends StandardPHPElementContentProvider {

	public boolean hasChildrenInternal(Object element) {

		if (element instanceof IProject && !((IProject) element).isAccessible())
			return false;

		if (element instanceof IContainer) {
			IContainer container = (IContainer) element;
			try {
				//	if ( is external
				return container.members().length > 0;
			} catch (CoreException e) {
				return false;
			}
		}
		return super.hasChildrenInternal(element);
	}

	public Object[] getChildrenInternal(Object parentElement) {
		try {
			if (parentElement instanceof PHPWorkspaceModelManager)
				return ResourcesPlugin.getWorkspace().getRoot().getProjects();

			if (parentElement instanceof IProject)
				return ((IProject) parentElement).members();

			return super.getChildrenInternal(parentElement);
		} catch (CoreException e) {
			return NO_CHILDREN;
		}
	}

}
