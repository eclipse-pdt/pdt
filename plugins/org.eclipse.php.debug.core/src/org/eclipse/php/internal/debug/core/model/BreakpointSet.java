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
package org.eclipse.php.internal.debug.core.model;

import java.io.File;
import java.util.ArrayList;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.IPath;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IBuildpathEntry;
import org.eclipse.dltk.core.ModelException;

public class BreakpointSet {

	private IProject fProject;
	private ArrayList<String> fDirectories;
	private ArrayList<IProject> fProjects;

	public BreakpointSet(IProject project, boolean isPHPCGI) {

		fProject = project;
		fDirectories = new ArrayList<String>();
		fProjects = new ArrayList<IProject>();

		if (project != null) {
			IBuildpathEntry[] entries = null;
			try {
				entries = DLTKCore.create(project).getRawBuildpath();
			} catch (ModelException e) {
			}
			if (entries != null) {
				for (IBuildpathEntry element : entries) {
					if (element.getEntryKind() == IBuildpathEntry.BPE_LIBRARY) {
						IPath path = element.getPath();
						File file = new File(path.toOSString());
						fDirectories.add(file.getAbsolutePath());
					} else if (element.getEntryKind() == IBuildpathEntry.BPE_PROJECT) {
						IResource includeResource = ResourcesPlugin
								.getWorkspace().getRoot().findMember(
										element.getPath().lastSegment());
						if (includeResource instanceof IProject) {
							fProjects.add((IProject) includeResource);
						}
					} else if (element.getEntryKind() == IBuildpathEntry.BPE_VARIABLE) {
						IPath path = DLTKCore.getResolvedVariablePath(element
								.getPath());
						File file = path.toFile();
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
			return true;
		} else {
			IProject project = resource.getProject();
			return fProject.equals(project) || fProjects.contains(project);
		}
	}

}
