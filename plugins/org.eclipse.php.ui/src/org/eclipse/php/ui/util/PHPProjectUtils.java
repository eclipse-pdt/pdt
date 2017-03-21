/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
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

	public static void createProjectAt(IProject project, URI locationURI, IProgressMonitor monitor)
			throws CoreException {
		createProjectAt(project, locationURI, null, monitor);
	}

	public static void createProjectAt(IProject project, URI locationURI, String natureId, IProgressMonitor monitor)
			throws CoreException {
		if (monitor == null) {
			monitor = new NullProgressMonitor();
		}
		monitor.beginTask(NewWizardMessages.BuildPathsBlock_operationdesc_project, 10);
		// create the project
		try {
			if (!project.exists()) {
				IProjectDescription desc = project.getWorkspace().newProjectDescription(project.getName());
				if (locationURI != null
						&& ResourcesPlugin.getWorkspace().getRoot().getLocationURI().equals(locationURI)) {
					locationURI = null;
				}
				desc.setLocationURI(locationURI);

				if (natureId != null) {
					desc.setNatureIds(new String[] { natureId });
				}

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
