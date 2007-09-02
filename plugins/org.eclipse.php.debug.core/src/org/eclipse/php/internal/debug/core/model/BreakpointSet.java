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

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.IPath;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.php.internal.core.phpModel.parser.PHPProjectModel;
import org.eclipse.php.internal.core.phpModel.parser.PHPWorkspaceModelManager;
import org.eclipse.php.internal.core.project.IIncludePathEntry;
import org.eclipse.php.internal.core.project.options.PHPProjectOptions;
import org.eclipse.php.internal.debug.core.IPHPConstants;
import org.eclipse.php.internal.debug.core.preferences.PHPProjectPreferences;
import org.eclipse.php.internal.debug.core.zend.debugger.RemoteDebugger;
import org.eclipse.php.internal.ui.wizards.PHPProjectCreationWizard;

public class BreakpointSet {

	private IProject fProject;
	private ArrayList fDirectories;
	private ArrayList fProjects;

//	private boolean fIsPHPCGI;

	public BreakpointSet(IProject project, boolean isPHPCGI) {

		fProject = project;
//		fIsPHPCGI = isPHPCGI;
		fDirectories = new ArrayList();
		fProjects = new ArrayList();

		PHPProjectOptions options = PHPProjectOptions.forProject(project);
		if (options != null) {
			IIncludePathEntry[] entries = options.readRawIncludePath();

			if (entries != null) {
				for (int i = 0; i < entries.length; i++) {
					if (entries[i].getEntryKind() == IIncludePathEntry.IPE_LIBRARY) {
						IPath path = entries[i].getPath();
						File file = new File(path.toString());
						fDirectories.add(RemoteDebugger.convertToSystemIndependentFileName(file.getAbsolutePath()));
					} else if (entries[i].getEntryKind() == IIncludePathEntry.IPE_PROJECT) {
						IResource includeResource = entries[i].getResource();
						if (includeResource instanceof IProject) {
							fProjects.add(includeResource);
						}
					} else if (entries[i].getEntryKind() == IIncludePathEntry.IPE_VARIABLE) {
						IPath path = entries[i].getPath();
						String variableName = path.toString();
						File file = getVariableFile(variableName);
						if (file != null) {
							if (file.isDirectory()) {
								fDirectories.add(RemoteDebugger.convertToSystemIndependentFileName(file.getAbsolutePath()));
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
				String includeBasedir = (String) marker.getAttribute(IPHPConstants.STORAGE_INC_BASEDIR, "");
				if (!"".equals(includeBasedir)) {
					Object[] dirs = fDirectories.toArray();
					for (int i = 0; i < dirs.length; i++) {
						if (includeBasedir.equals((String) dirs[i]))
							return true;
					}
					return false;
				}
			}
			return true;
		} else {
			IProject project = resource.getProject();
			if (fProject.equals(project) || fProject.equals(PHPWorkspaceModelManager.getDefaultPHPProjectModel().getProject())){
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
