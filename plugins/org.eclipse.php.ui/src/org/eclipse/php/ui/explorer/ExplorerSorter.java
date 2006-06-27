package org.eclipse.php.ui.explorer;

import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.php.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.php.core.phpModel.phpElementData.PHPIncludeFileData;

public class ExplorerSorter extends ViewerSorter {

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ViewerComparator#category(java.lang.Object)
	 */
	public int category(Object element) {
		if (element instanceof PHPFileData) {
			return 1;
		}
		if (element instanceof PHPIncludeFileData) {
			return 2;
		}
		return 2;
	}
}
