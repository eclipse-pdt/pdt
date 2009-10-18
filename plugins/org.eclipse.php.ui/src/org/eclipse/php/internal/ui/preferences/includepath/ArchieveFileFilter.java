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
package org.eclipse.php.internal.ui.preferences.includepath;

import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.php.internal.ui.PHPUiPlugin;

public class ArchieveFileFilter extends ViewerFilter {

	private List fExcludes;
	private boolean fRecursive;
	private static String[] fFilterExtensions = { "*.zip", "*.jar" }; //defaults  //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * @param excludedFiles
	 *            Excluded files will not pass the filter. <code>null</code> is
	 *            allowed if no files should be excluded.
	 * @param recusive
	 *            Folders are only shown if, searched recursively, contain an
	 *            archive
	 */
	public ArchieveFileFilter(IFile[] excludedFiles, boolean recusive,
			String[] filterExtensions) {
		if (excludedFiles != null) {
			fExcludes = Arrays.asList(excludedFiles);
		} else {
			fExcludes = null;
		}
		fRecursive = recusive;
		fFilterExtensions = filterExtensions;
	}

	public ArchieveFileFilter(List excludedFiles, boolean recusive,
			String[] filterExtensions) {
		fExcludes = excludedFiles;
		fRecursive = recusive;
		fFilterExtensions = filterExtensions;
	}

	/*
	 * @see ViewerFilter#select
	 */
	public boolean select(Viewer viewer, Object parent, Object element) {
		if (element instanceof IFile) {
			if (fExcludes != null && fExcludes.contains(element)) {
				return false;
			}
			return isZipPath(((IFile) element).getFullPath());
		} else if (element instanceof IContainer) { // IProject, IFolder
			if (!fRecursive) {
				return true;
			}
			try {
				IResource[] resources = ((IContainer) element).members();
				for (int i = 0; i < resources.length; i++) {
					// recursive! Only show containers that contain an archive
					if (select(viewer, parent, resources[i])) {
						return true;
					}
				}
			} catch (CoreException e) {
				PHPUiPlugin.log(e.getStatus());
			}
		}
		return false;
	}

	public static boolean isZipPath(IPath path) {
		String ext = path.getFileExtension();
		if (ext != null && ext.length() != 0) {
			return isArchiveFileExtension(ext);
		}
		return false;
	}

	public static boolean isArchiveFileExtension(String ext) {
		for (int i = 0; i < fFilterExtensions.length; i++) {
			if (ext.equalsIgnoreCase(fFilterExtensions[i])) {
				return true;
			}
		}
		return false;
	}

}
