package org.eclipse.php.internal.core.filenetwork;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.core.IFileHierarchyInfo;
import org.eclipse.dltk.core.IFileHierarchyResolver;
import org.eclipse.dltk.core.ISourceModule;

public class PHPFileHierarchyResolver implements IFileHierarchyResolver {

	public PHPFileHierarchyResolver() {
	}

	public IFileHierarchyInfo resolveDown(ISourceModule file, IProgressMonitor monitor) {
		return FileNetworkUtility.buildReferencingFilesTree(file, monitor);
	}

	public IFileHierarchyInfo resolveUp(ISourceModule file, IProgressMonitor monitor) {
		return FileNetworkUtility.buildReferencedFilesTree(file, monitor);
	}

}
