/*******************************************************************************
 * Copyright (c) 2005, 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.core.changes;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IBuildpathEntry;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.internal.core.includepath.IncludePathManager;
import org.eclipse.php.refactoring.core.utils.RefactoringUtility;

public class RenameBuildAndIcludePathChange extends Change {
	private IPath fDest;
	private IPath fSource;
	private String fName;
	private String fNewName;
	private List<IBuildpathEntry> newBuildEntries;
	private List<IBuildpathEntry> newIncludePathEntries;
	private List<IBuildpathEntry> oldBuildEntries;
	private List<IBuildpathEntry> oldIncludeEntries;

	public RenameBuildAndIcludePathChange(IPath source, IPath dest, String resName, String newName,
			List<IBuildpathEntry> oldBiuldEntries, List<IBuildpathEntry> newBiuldEntries,
			List<IBuildpathEntry> oldIncludePath, List<IBuildpathEntry> newIncludePathEntries) {

		fSource = source;
		fDest = dest;
		fName = resName;
		fNewName = newName;
		this.oldBuildEntries = oldBiuldEntries;
		this.newBuildEntries = newBiuldEntries;
		this.oldIncludeEntries = oldIncludePath;
		this.newIncludePathEntries = newIncludePathEntries;
	}

	@Override
	public Object getModifiedElement() {
		return null;
	}

	@Override
	public String getName() {
		return NLS.bind(Messages.RenameBuildAndIcludePathChange_0, fName);
	}

	@Override
	public void initializeValidationData(IProgressMonitor pm) {

	}

	@Override
	public RefactoringStatus isValid(IProgressMonitor pm) throws CoreException, OperationCanceledException {
		return new RefactoringStatus();
	}

	@Override
	public Change perform(IProgressMonitor pm) throws CoreException {
		performChanges(pm);
		return new RenameBuildAndIcludePathChange(fDest, fSource, fNewName, fName, newBuildEntries, oldBuildEntries,
				newIncludePathEntries, oldIncludeEntries);
	}

	protected void performChanges(IProgressMonitor pm) throws CoreException {

		IResource newResource = RefactoringUtility.getResource(fDest.append(fNewName));
		IProject newProject = newResource.getProject();

		IScriptProject newScriptProject = DLTKCore.create(newProject);

		// Added new paths
		if (newBuildEntries.size() > 0) {
			// Remove all old paths
			newScriptProject.setRawBuildpath(null, pm);

			newScriptProject.setRawBuildpath(newBuildEntries.toArray(new IBuildpathEntry[newBuildEntries.size()]),
					null);
		}

		if (newIncludePathEntries.size() > 0) {
			IncludePathManager.getInstance().addEntriesToIncludePath(newProject, newIncludePathEntries);
		}

		IncludePathManager.getInstance().refresh(newProject);
	}

}
