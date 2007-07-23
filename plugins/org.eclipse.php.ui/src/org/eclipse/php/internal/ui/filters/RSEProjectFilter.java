package org.eclipse.php.internal.ui.filters;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.php.internal.ui.PHPUiConstants;

public class RSEProjectFilter extends ViewerFilter {

	public RSEProjectFilter() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		//This was added in order to hide the RSE Temp project from the PHP explorer view
		if (element instanceof IProject) {
			IProject proj = (IProject) element;
			try {
				//check if an RSE nature (project must be open) OR simply compare its name
				if ((proj.isOpen() && proj.hasNature(PHPUiConstants.RSE_TEMP_PROJECT_NATURE_ID)) || proj.getName().equals(PHPUiConstants.RSE_TEMP_PROJECT_NAME)) {
					return false;
				}
			} catch (CoreException ce) {
				Logger.logException(ce);
				return false;
			}
		}
		return true;
	}

}
