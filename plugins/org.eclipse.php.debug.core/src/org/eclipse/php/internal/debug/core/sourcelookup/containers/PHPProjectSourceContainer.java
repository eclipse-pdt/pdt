package org.eclipse.php.internal.debug.core.sourcelookup.containers;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.sourcelookup.containers.ProjectSourceContainer;

public class PHPProjectSourceContainer extends ProjectSourceContainer {

	public PHPProjectSourceContainer(IProject project, boolean referenced) {
		super(project, referenced);
	}

	public Object[] findSourceElements(String name) throws CoreException {
		IResource resource = ResourcesPlugin.getWorkspace().getRoot().findMember(name);
		if (resource instanceof IFile) {
			return new Object[] { (IFile)resource };
		}
		return super.findSourceElements(name);
	}
}
