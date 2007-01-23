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
package org.eclipse.php.internal.ui.filters;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.php.internal.core.phpModel.parser.PHPProjectModel;
import org.eclipse.php.internal.core.project.PHPNature;


/**
 * Filters non-java projects
 */
public class NonPHPProjectsFilter extends ViewerFilter {

	/*
	 * @see ViewerFilter
	 */
	public boolean select(Viewer viewer, Object parent, Object element) {
		if (element instanceof IResource)
			element = ((IResource) element).getProject();
		if (element instanceof PHPProjectModel)
			return true;
		else if (element instanceof IProject) {
			IProject project = (IProject) element;
			try {
				if (!project.hasNature(PHPNature.ID))
					return false;
			} catch (CoreException e) {
			}
			return project.isOpen();

		}

		return true;
	}
}
