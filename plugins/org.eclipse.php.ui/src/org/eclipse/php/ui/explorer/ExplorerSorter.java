package org.eclipse.php.ui.explorer;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.php.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.php.core.phpModel.phpElementData.PHPIncludeFileData;

public class ExplorerSorter extends ViewerSorter {

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ViewerComparator#category(java.lang.Object)
	 */
	public int category(Object element) {
		if (element instanceof IContainer) {
			return 1;
		}
		if (element instanceof IFile) {
			return 2;
		}
		if (element instanceof PHPIncludeFileData) {
			return 3;
		}
		return 4;
	}
}
