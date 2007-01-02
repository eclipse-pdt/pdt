/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.debug.ui.launching;

import java.util.Vector;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.eclipse.ui.ide.IDE;

/**
 * @author Adam Peller (IBM)
 *
 * Dialog to select a Project from the Workspace
 */
public class ProjectSelectionDialog extends ElementListSelectionDialog {

	protected String[] requiredNatures;

	/**
	 * @param parent
	 * @param renderer
	 */
	public ProjectSelectionDialog(Shell parent, String[] requiredNatures, String title, String message) {
		super(parent, new ProjectLabelProvider());
		this.requiredNatures = requiredNatures;
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		if (requiredNatures != null && requiredNatures.length > 0) {
			Vector filteredProjects = new Vector();
			int numProjects = projects == null ? 0 : projects.length;
			for (int i = 0; i < numProjects; i++) {
				try {
					if (projectHasRequiredNatures(projects[i]))
						filteredProjects.add(projects[i]);
				} catch (CoreException ce) {
					// Project does not exist or is not open, so skip it
				}
			}
			setElements(filteredProjects.toArray());
		} else {
			setElements(projects);
		}

		setTitle(title); //$NON-NLS-1$
		setMessage(message); //$NON-NLS-1$

	}

	private boolean projectHasRequiredNatures(IProject project) throws CoreException {
		if (requiredNatures != null) {
			for (int i = 0; i < requiredNatures.length; i++) {
				if (!project.hasNature(requiredNatures[i]))
					return false;
			}
			return true;
		}
		return true;
	}
}

class ProjectLabelProvider extends LabelProvider {

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ILabelProvider#getImage(java.lang.Object)
	 */
	public Image getImage(Object element) {
		if (element instanceof IProject) {
			IProject proj = (IProject) element;
			String imgDesc = proj.isOpen() ? IDE.SharedImages.IMG_OBJ_PROJECT : IDE.SharedImages.IMG_OBJ_PROJECT_CLOSED;
			return PlatformUI.getWorkbench().getSharedImages().getImage(imgDesc);
		}
		return super.getImage(element);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object)
	 */
	public String getText(Object element) {
		if (element instanceof IProject) {
			IProject proj = (IProject) element;
			return proj.getName();
		}
		return super.getText(element);
	}

}
