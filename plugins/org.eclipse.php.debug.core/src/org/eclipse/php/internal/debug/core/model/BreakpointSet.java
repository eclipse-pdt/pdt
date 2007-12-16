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
package org.eclipse.php.internal.debug.core.model;

import java.io.File;
import java.util.ArrayList;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.IPath;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.php.internal.core.phpModel.parser.PHPWorkspaceModelManager;
import org.eclipse.php.internal.core.project.IIncludePathEntry;
import org.eclipse.php.internal.core.project.options.PHPProjectOptions;
import org.eclipse.php.internal.debug.core.IPHPConstants;

public class BreakpointSet {

	private IProject fProject;
	private ArrayList<String> fDirectories;
	private ArrayList<IProject> fProjects;

	public BreakpointSet(IProject project, boolean isPHPCGI) {

		fProject = project;
		fDirectories = new ArrayList<String>();
		fProjects = new ArrayList<IProject>();

		if (project != null) {
			PHPProjectOptions options = PHPProjectOptions.forProject(project);
			if (options != null) {
				IIncludePathEntry[] entries = options.readRawIncludePath();

				if (entries != null) {
					for (IIncludePathEntry element : entries) {
						if (element.getEntryKind() == IIncludePathEntry.IPE_LIBRARY) {
							IPath path = element.getPath();
							File file = new File(path.toOSString());
							fDirectories.add(file.getAbsolutePath());
						} else if (element.getEntryKind() == IIncludePathEntry.IPE_PROJECT) {
							IResource includeResource = element.getResource();
							if (includeResource instanceof IContainer) {
								fProjects.add(includeResource.getProject());
							}
						} else if (element.getEntryKind() == IIncludePathEntry.IPE_VARIABLE) {
							IPath path = element.getPath();
							String variableName = path.toOSString();
							File file = getVariableFile(variableName);
							if (file != null) {
								if (file.isDirectory()) {
									fDirectories.add(file.getAbsolutePath());
								}
							}
						}
					}
				}
			}
		}
	}

	public boolean supportsBreakpoint(IBreakpoint breakpoint) {

		// If no project, assume everything in the workspace
		if (fProject == null)
			return true;

		PHPLineBreakpoint bp = (PHPLineBreakpoint) breakpoint;
		IMarker marker = bp.getMarker();
		IResource resource = null;
		if (breakpoint instanceof PHPRunToLineBreakpoint) {
			return true;
		} else {
			resource = marker.getResource();
		}

		if (resource instanceof IWorkspaceRoot) {
			String storageType = marker.getAttribute(IPHPConstants.STORAGE_TYPE, "");

			if (storageType.equals(IPHPConstants.STORAGE_TYPE_INCLUDE)) {
				String includeBasedir = marker.getAttribute(IPHPConstants.STORAGE_INC_BASEDIR, "");
				if (!"".equals(includeBasedir)) {
					Object[] dirs = fDirectories.toArray();
					for (Object element : dirs) {
						if (includeBasedir.equals(element))
							return true;
					}
					return false;
				}
			}
			return true;
		} else {
			IProject project = resource.getProject();
			if (fProject.equals(project) || fProject.equals(PHPWorkspaceModelManager.getDefaultPHPProjectModel().getProject())) {
				return true;
			}
			return fProjects.contains(project);
		}
	}

	private File getVariableFile(String variableName) {
		int index = variableName.indexOf('/');
		String extention = ""; //$NON-NLS-1$
		if (index != -1) {
			if (index + 1 < variableName.length()) {
				extention = variableName.substring(index + 1);
			}
			variableName = variableName.substring(0, index);
		}
		IPath path = PHPProjectOptions.getIncludePathVariable(variableName);
		if (path == null) {
			return null;
		}
		path = path.append(extention);
		return path.toFile();
	}

}
