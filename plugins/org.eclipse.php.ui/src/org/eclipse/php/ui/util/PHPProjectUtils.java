package org.eclipse.php.ui.util;

import java.net.URI;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.dltk.internal.ui.wizards.NewWizardMessages;

public class PHPProjectUtils {
	public static void createProjectAt(IProject project, URI locationURI,
			IProgressMonitor monitor) throws CoreException {
		if (monitor == null) {
			monitor = new NullProgressMonitor();
		}
		monitor.beginTask(
				NewWizardMessages.BuildPathsBlock_operationdesc_project, 10);
		// create the project
		try {
			if (!project.exists()) {
				IProjectDescription desc = project.getWorkspace()
						.newProjectDescription(project.getName());
				if (locationURI != null
						&& ResourcesPlugin.getWorkspace().getRoot()
								.getLocationURI().equals(locationURI)) {
					locationURI = null;
				}
				desc.setLocationURI(locationURI);
				project.create(desc, monitor);
			}
			if (!project.isOpen()) {
				project.open(monitor);
			}
		} finally {
			// not null
			monitor.done();
		}
	}
}
