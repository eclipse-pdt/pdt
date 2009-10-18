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
package org.eclipse.php.internal.debug.ui.launching;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

public class ApplicationFileViewerFilter extends ViewerFilter {

	protected String[] validExtensions;
	protected String[] requiredNatures;

	public ApplicationFileViewerFilter(String[] requiredNatures,
			String[] validExtensions) {
		this.requiredNatures = requiredNatures;
		this.validExtensions = validExtensions;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers
	 * .Viewer, java.lang.Object, java.lang.Object)
	 */
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		return isValid(element);
	}

	public boolean isValid(Object element) {
		if (IFile.class.isInstance(element))
			return isValidFile((IFile) element);
		if (IContainer.class.isInstance(element))
			return isValidDirectory((IContainer) element);
		return false;
	}

	/**
	 * Returns boolean indicating whether the specified IFile is valid.
	 */
	public boolean isValidFile(IFile file) {
		String ext = file.getFileExtension();
		for (int i = 0; i < validExtensions.length; i++) {
			if (validExtensions[i].equalsIgnoreCase(ext)) {
				return true;
			}
		}
		return false;

	}

	/**
	 * Returns boolean indicating whether the specified IContainer is valid.
	 * 
	 * @param container
	 * @return
	 */
	public boolean isValidDirectory(IContainer container) {
		try {
			if (projectHasRequiredNatures(container.getProject())
					&& !container.getName().startsWith(".")) { //$NON-NLS-1$
				return true;
			}
			return false;
		} catch (CoreException e) {
			return false;
		}

	}

	private boolean projectHasRequiredNatures(IProject project)
			throws CoreException {
		if (requiredNatures != null) {
			for (int i = 0; i < requiredNatures.length; i++) {
				if (!project.hasNature(requiredNatures[i]))
					return false;
			}
		}
		return true;
	}
}
