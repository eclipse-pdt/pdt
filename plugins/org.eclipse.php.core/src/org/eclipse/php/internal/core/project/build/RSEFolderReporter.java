package org.eclipse.php.internal.core.project.build;

import java.net.URI;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.runtime.CoreException;

class RSEFolderReporter implements IResourceDeltaVisitor {

	private boolean newJobNeeded = false;

	public boolean visit(IResourceDelta delta) throws CoreException {
		if (newJobNeeded) {
			return false;
		}
		switch (delta.getResource().getType()) {
			//only process files with PHP content type
			case IResource.FILE:
				return false;
			case IResource.PROJECT:
				return true;
			case IResource.FOLDER:
				IResource resource = delta.getResource();
				URI locationURI = resource.getLocationURI();
				if (locationURI.getScheme().equals("rse") && delta.getKind() == IResourceDelta.ADDED) {
					newJobNeeded = true;
					return false;
				}
			default:
				return true;
		}
	}

	boolean isNewJobNeeded() {
		return newJobNeeded;
	}

}
