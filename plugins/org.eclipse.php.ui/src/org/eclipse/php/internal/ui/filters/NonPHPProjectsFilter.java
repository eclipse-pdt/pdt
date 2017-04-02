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
import org.eclipse.dltk.core.DLTKLanguageManager;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.php.internal.core.PHPLanguageToolkit;

public class NonPHPProjectsFilter extends ViewerFilter {
	/*
	 * @see ViewerFilter
	 */
	@Override
	public boolean select(Viewer viewer, Object parent, Object element) {
		if (element instanceof IScriptProject) {
			IScriptProject sp = (IScriptProject) element;
			if (DLTKLanguageManager.getLanguageToolkit(sp) instanceof PHPLanguageToolkit) {
				return true;
			} else {
				return false;
			}

		} else if (element instanceof IProject)
			return !((IProject) element).isOpen();

		return true;
	}
}
