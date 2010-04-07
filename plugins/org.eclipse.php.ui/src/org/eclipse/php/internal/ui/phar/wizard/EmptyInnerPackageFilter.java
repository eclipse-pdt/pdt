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
				return pkg.hasSubfolders() || pkg.hasChildren()
						|| (pkg.getForeignResources().length > 0);
			} catch (ModelException e) {
				PHPUiPlugin.log(e);
			}
		}

		return true;
	}

}
