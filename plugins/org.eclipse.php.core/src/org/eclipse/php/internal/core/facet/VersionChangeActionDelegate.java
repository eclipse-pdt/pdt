package org.eclipse.php.internal.core.facet;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.wst.common.project.facet.core.IDelegate;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;

public class VersionChangeActionDelegate implements IDelegate {

	public void execute(IProject arg0, IProjectFacetVersion arg1, Object arg2,
			IProgressMonitor arg3) throws CoreException {
		// sync php version of the project
		// check if there is any change to prevent endless loops
		// (php options upgrade will invoke a version change and lead to this)
		// TODO
	}

}
