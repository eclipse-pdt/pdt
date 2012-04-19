package org.eclipse.php.ui.explorer;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.core.IScriptFolder;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

public class DotFileFilter extends ViewerFilter {

	public DotFileFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Returns the result of this filter, when applied to the given inputs.
	 * 
	 * @return Returns true if element should be included in filtered set
	 */
	public boolean select(Viewer viewer, Object parent, Object element) {
		if (element instanceof IScriptFolder) {
			String name = ((IScriptFolder) element).getElementName();
			Path path = new Path(name);
			for (int i = 0; i < path.segmentCount(); i++) {
				String segment = path.segment(i);
				if (segment.startsWith(".")) { //$NON-NLS-1$
					return false;
				}
			}
		} else if (element instanceof ISourceModule) {
			if (((ISourceModule) element).getElementName().startsWith(".")) { //$NON-NLS-1$
				return false;
			}
		} else if (element instanceof IResource) {
			String lastSegment = ((IResource) element).getFullPath()
					.lastSegment();
			if (lastSegment.startsWith(".") && !lastSegment.equals(".htaccess")) { //$NON-NLS-1$
				return false;
			}
		}
		return true;
	}
}
