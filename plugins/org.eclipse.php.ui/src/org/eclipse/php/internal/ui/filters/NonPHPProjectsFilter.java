/*******************************************************************************
 * Copyright (c) 2018 Dawid Pakuła and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
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
import org.eclipse.php.core.PHPToolkitUtil;
import org.eclipse.php.internal.ui.Logger;

public class NonPHPProjectsFilter extends ViewerFilter {

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (element instanceof IProject) {
			return testProject((IProject) element);
		} else if (element instanceof IScriptProject) {
			return testProject(((IScriptProject) element).getProject());
		}
		return true;
	}

	public boolean testProject(IProject project) {
		try {
			if (project != null && project.isAccessible() && !PHPToolkitUtil.isPHPProject(project)) {
				return false;
			}
		} catch (CoreException e) {
			Logger.logException(e);
		}
		return true;
	}

}
