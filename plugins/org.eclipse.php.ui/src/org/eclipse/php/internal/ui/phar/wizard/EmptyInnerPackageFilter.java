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
package org.eclipse.php.internal.ui.phar.wizard;

import org.eclipse.dltk.core.IScriptFolder;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.php.internal.ui.PHPUiPlugin;

public class EmptyInnerPackageFilter extends ViewerFilter {

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (element instanceof IScriptFolder) {
			IScriptFolder pkg = (IScriptFolder) element;

			try {
				if (pkg.isRootFolder())
					return pkg.hasChildren();
				return pkg.hasSubfolders() || pkg.hasChildren() || (pkg.getForeignResources().length > 0);
			} catch (ModelException e) {
				PHPUiPlugin.log(e);
			}
		}

		return true;
	}

}
