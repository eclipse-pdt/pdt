package org.eclipse.php.internal.ui.filters;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.core.IScriptFolder;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

public class DotFileFilter extends ViewerFilter {

	private static final String DOT = "."; //$NON-NLS-1$
	private static final String HTACCESS_FILE = ".htaccess"; //$NON-NLS-1$

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
				if (segment.startsWith(DOT)) { 
					return false;
				}
			}
		} else if (element instanceof ISourceModule) {
			if (((ISourceModule) element).getElementName().startsWith(DOT)
					&& !((ISourceModule) element).getElementName().equals(
							HTACCESS_FILE)) { 
				return false;
			}
		} else if (element instanceof IResource) {

			IPath path = ((IResource) element).getFullPath();
			for (int i = 0; i < path.segmentCount(); i++) {
				String segment = path.segment(i);
				if (segment.startsWith(DOT) && !segment.equals(HTACCESS_FILE)) { 
					return false;
				}
			}
		}
		return true;
	}
}
