package org.eclipse.php.internal.ui.phar.wizard;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.internal.core.Model;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.php.internal.core.project.PHPNature;

public class ContainerFilter extends ViewerFilter {

	private boolean fFilterContainers;

	public static boolean FILTER_CONTAINERS = true;
	public static boolean FILTER_NON_CONTAINERS = false;

	public ContainerFilter(boolean filterContainers) {
		fFilterContainers = filterContainers;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers
	 * .Viewer, java.lang.Object, java.lang.Object)
	 */
	public boolean select(Viewer viewer, Object parent, Object element) {
		boolean isContainer = element instanceof IContainer;
		if (!isContainer && element instanceof IModelElement) {
			int type = ((IModelElement) element).getElementType();
			isContainer = type == IModelElement.PROJECT_FRAGMENT
					|| type == IModelElement.SCRIPT_FOLDER
					|| type == IModelElement.SCRIPT_PROJECT
			/* || type == IModelElement.SOURCE_MODULE */;
		}

		if (parent instanceof Model) {
			IProject project = null;
			if (element instanceof IScriptProject) {
				project = ((IScriptProject) element).getProject();
			} else if (element instanceof IProject) {
				project = (IProject) element;
			}
			if (project != null) {
				try {
					if (project.isAccessible()
							&& project.hasNature(PHPNature.ID)) {
						return true;
					} else {
						return false;
					}
				} catch (CoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return (fFilterContainers && !isContainer)
				|| (!fFilterContainers && isContainer);
	}
}
