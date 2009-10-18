/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.ui.filters;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.php.internal.ui.PHPUiConstants;

public class RSEProjectFilter extends ViewerFilter {

	public RSEProjectFilter() {
	}

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		// This was added in order to hide the RSE Temp project from the PHP
		// explorer view
		IProject proj = null;
		if (element instanceof IProject) {
			proj = (IProject) element;

		} else if (element instanceof IScriptProject) {
			proj = ((IScriptProject) element).getProject();
		}
		if (proj != null) {
			try {
				// check if an RSE nature (project must be open) OR simply
				// compare its name
				if ((proj.isOpen() && proj
						.hasNature(PHPUiConstants.RSE_TEMP_PROJECT_NATURE_ID))
						|| proj.getName().equals(
								PHPUiConstants.RSE_TEMP_PROJECT_NAME)) {
					return false;
				}
			} catch (CoreException ce) {
				Logger.logException(ce);
				return false;
			}
		}
		return true;
	}

}
