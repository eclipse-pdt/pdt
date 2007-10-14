/**
 * Copyright (c) 2007 Zend Technologies
 * 
 */
package org.eclipse.php.internal.ui.filters;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.php.internal.core.phpModel.PHPModelUtil;

/**
 * @author seva
 *
 */
public class SystemFilesFilter extends ViewerFilter {

	/** (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (element instanceof IFile) {
			IFile file = (IFile) element;
			if (!PHPModelUtil.isPhpFile(file)) {
				final String name = file.getName();
				if (name.startsWith(".") && !".htaccess".equals(name)) { //$NON-NLS-1$ //$NON-NLS-2$
					return false;
				}
			}
		}
		if (element instanceof IFolder) {
			IFolder folder = (IFolder) element;
			if (folder.getName().startsWith(".")) { //$NON-NLS-1$
				return false;
			}
		}

		return true;
	}
}
