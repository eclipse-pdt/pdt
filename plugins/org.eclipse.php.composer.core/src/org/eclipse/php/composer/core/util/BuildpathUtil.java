/*******************************************************************************
 * Copyright (c) 2012, 2016, 2017 PDT Extension Group and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *     Kaloyan Raev - [501269] externalize strings
 *******************************************************************************/
package org.eclipse.php.composer.core.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.core.*;
import org.eclipse.php.internal.core.buildpath.BuildPathUtils;

@SuppressWarnings("restriction")
public class BuildpathUtil {

	public static void setupVendorBuildpath(IScriptProject scriptProject, IProgressMonitor progress)
			throws ModelException {
		IProject project = scriptProject.getProject();
		IPath composerPath = project.getFullPath().append("vendor"); //$NON-NLS-1$
		IBuildpathEntry[] rawBuildpath = scriptProject.getRawBuildpath();
		progress.setTaskName(Messages.BuildpathUtil_TaskName);

		for (IBuildpathEntry entry : rawBuildpath) {
			if (entry.getPath().equals(composerPath)) {
				BuildPathUtils.removeEntryFromBuildPath(scriptProject, entry);
			}
		}

		BuildPathUtils.addEntriesToBuildPath(scriptProject, getVendorEntries(composerPath));
		progress.worked(1);
	}

	protected static List<IBuildpathEntry> getVendorEntries(IPath composerPath) {

		IPath[] include = new IPath[] { new Path("composer/*") }; //$NON-NLS-1$
		IBuildpathAttribute[] attributes = new IBuildpathAttribute[0];
		IPath[] exclude = new IPath[0];
		IBuildpathEntry vendorEntry = DLTKCore.newBuiltinEntry(composerPath, new IAccessRule[0], attributes, include,
				exclude, false, false);
		List<IBuildpathEntry> vendorEntries = new ArrayList<IBuildpathEntry>();
		vendorEntries.add(vendorEntry);

		return vendorEntries;

	}
}
