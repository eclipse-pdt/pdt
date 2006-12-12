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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.php.core.phpModel.PHPModelUtil;
import org.eclipse.php.core.phpModel.parser.PHPProjectModel;
import org.eclipse.php.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.core.project.PHPNature;

/**
 * Filters out all non-PHP elements.
 */
public class NonPHPElementFilter extends ViewerFilter {

	/**
	 * Returns the result of this filter, when applied to the
	 * given inputs.
	 *
	 * @return Returns true if element should be included in filtered set
	 */
	public boolean select(Viewer viewer, Object parent, Object element) {
		if (element instanceof PHPCodeData || element instanceof PHPProjectModel)
			return true;

		if (element instanceof IFile) {
			return PHPModelUtil.isPhpFile((IFile)element);
		}

		if (element instanceof IProject) {
			IProject project = (IProject) element;
			try {
				if (!project.hasNature(PHPNature.ID))
					return false;
			} catch (CoreException e) {
			}
			return project.isOpen();
		}

		// Exclude all IStorage elements which are neither Java elements nor resources
		if (element instanceof IStorage)
			return false;

		return true;
	}
}
