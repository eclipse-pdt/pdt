/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.refactor.participants;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.internal.core.BuildpathEntry;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.php.internal.core.includepath.IncludePath;
import org.eclipse.php.internal.core.includepath.IncludePathManager;

/**
 * Breakpoint participant for project rename.
 * 
 * @since 3.2
 */
public class IncludepathRenameIParentParticipant extends
		IncludepathRenameParticipant {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jdt.internal.debug.core.refactoring.BreakpointRenameParticipant
	 * #accepts(org.eclipse.jdt.core.IModelElement)
	 */
	protected boolean accepts(IModelElement element) {
		return element instanceof IParent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jdt.internal.debug.core.refactoring.BreakpointRenameParticipant
	 * #gatherChanges(org.eclipse.core.resources.IMarker[], java.util.List,
	 * java.lang.String)
	 */
	protected void gatherChanges(IResource resource, List changes,
			String destProjectName) throws CoreException,
			OperationCanceledException {

		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot()
				.getProjects();
		for (int i = 0; i < projects.length; i++) {
			if (!projects[i].isAccessible()) {
				continue;
			}

			List<IncludePath> newIncludePathEntryList = new ArrayList<IncludePath>();
			Set<IBuildpathEntry> newBuildPathEntryList = new HashSet<IBuildpathEntry>();
			getNewIncludePaths(projects[i], newIncludePathEntryList,
					newBuildPathEntryList, resource, destProjectName);
			IProject newProject = projects[i];
			if (projects[i].equals(resource)) {
				newProject = ResourcesPlugin.getWorkspace().getRoot()
						.getProject(destProjectName);
			}
			changes.add(new IncludepathChange(projects[i], newProject,
					newIncludePathEntryList
							.toArray(new IncludePath[newIncludePathEntryList
									.size()]), newBuildPathEntryList
							.toArray(new IBuildpathEntry[newBuildPathEntryList
									.size()])));
		}
	}

	protected void getNewIncludePaths(IProject project,
			List<IncludePath> newIncludePathEntryList,
			Set<IBuildpathEntry> newBuildPathEntryList, IResource resource,
			String destProjectName) {
		IncludePath[] includePathEntries = IncludePathManager.getInstance()
				.getIncludePaths(project);
		try {
			IBuildpathEntry[] oldBuildpathEntries = DLTKCore.create(project)
					.getRawBuildpath();
			for (int i = 0; i < oldBuildpathEntries.length; i++) {
				newBuildPathEntryList.add(oldBuildpathEntries[i]);
			}
		} catch (ModelException e) {
			if (DLTKCore.DEBUG) {
				e.printStackTrace();
			}
		}
		for (int i = 0; i < includePathEntries.length; i++) {
			newIncludePathEntryList.add(getNewIncludePath(
					includePathEntries[i], resource, destProjectName,
					newBuildPathEntryList));
		}
	}

	protected IncludePath getNewIncludePath(IncludePath includePath,
			IResource resource, String destProjectName,
			Set<IBuildpathEntry> newBuildPathEntryList) {
		if (acceptKind(includePath)) {
			IPath renamedPath = resource.getFullPath();
			// IPath oldPath = null;
			if (includePath.isBuildpath()) {
				IBuildpathEntry entry = (IBuildpathEntry) includePath
						.getEntry();
				if (renamedPath.isPrefixOf(entry.getPath())) {
					entry = replaceBuildpath(destProjectName,
							newBuildPathEntryList, renamedPath, entry);

					includePath = new IncludePath(entry,
							includePath.getProject());
				}

			} else if (includePath.getEntry() instanceof IResource) {
				IResource oldRes = (IResource) includePath.getEntry();

				if (renamedPath.isPrefixOf(oldRes.getFullPath())) {
					renamedPath = renamedPath
							.removeLastSegments(1)
							.append(destProjectName)
							.append(oldRes.getFullPath().removeFirstSegments(
									renamedPath.segmentCount()));
					IResource newRes = null;
					if (oldRes.getType() == IResource.FILE) {
						newRes = ResourcesPlugin.getWorkspace().getRoot()
								.getFile(renamedPath);
					} else if (oldRes.getType() == IResource.FOLDER) {
						newRes = ResourcesPlugin.getWorkspace().getRoot()
								.getFolder(renamedPath);
					} else if (oldRes.getType() == IResource.PROJECT) {
						newRes = ResourcesPlugin.getWorkspace().getRoot()
								.getProject(renamedPath.toString());
					}
					includePath = new IncludePath(newRes,
							includePath.getProject());
				}
			}
		}
		return includePath;
	}

	protected IBuildpathEntry replaceBuildpath(String destProjectName,
			Set<IBuildpathEntry> newBuildPathEntryList, IPath renamedPath,
			IBuildpathEntry entry) {
		renamedPath = renamedPath
				.removeLastSegments(1)
				.append(destProjectName)
				.append(entry.getPath().removeFirstSegments(
						renamedPath.segmentCount()));
		// remove the old entry
		newBuildPathEntryList.remove(entry);
		entry = new BuildpathEntry(entry.getContentKind(),
				entry.getEntryKind(), renamedPath, entry.isExported(),
				entry.getInclusionPatterns(), entry.getExclusionPatterns(),
				entry.getAccessRules(), entry.combineAccessRules(),
				entry.getExtraAttributes(), entry.isExternal());
		// add the new entry
		newBuildPathEntryList.add(entry);
		return entry;
	}

	protected boolean isPrefix(IPath fullPath, IncludePath includePath) {
		return false;
	}

	protected boolean acceptKind(IncludePath includePath) {

		return true;
	}

	static class IncludepathChange extends Change {
		IProject project;
		IProject newProject;
		IncludePath[] oldIncludePathEntries;
		IncludePath[] newIncludePathEntries;
		IBuildpathEntry[] oldBuildpathEntries;
		IBuildpathEntry[] newBuildpathEntries;

		public IncludepathChange(IProject project, IProject newProject,
				IncludePath[] newIncludePathEntries,
				IBuildpathEntry[] newBuildpathEntries) {
			this.project = project;
			this.newProject = newProject;
			this.oldIncludePathEntries = IncludePathManager.getInstance()
					.getIncludePaths(project);
			this.newIncludePathEntries = newIncludePathEntries;
			try {
				oldBuildpathEntries = DLTKCore.create(project)
						.getRawBuildpath();
			} catch (ModelException e) {
				if (DLTKCore.DEBUG) {
					e.printStackTrace();
				}
			}
			this.newBuildpathEntries = newBuildpathEntries;
		}

		public IncludepathChange(IProject project, IProject newProject,
				IncludePath[] oldIncludePathEntries,
				IncludePath[] newIncludePathEntries,
				IBuildpathEntry[] oldBuildpathEntries,
				IBuildpathEntry[] newBuildpathEntries) {
			this.project = project;
			this.newProject = newProject;
			this.oldIncludePathEntries = oldIncludePathEntries;
			this.newIncludePathEntries = newIncludePathEntries;
			this.oldBuildpathEntries = oldBuildpathEntries;
			this.newBuildpathEntries = newBuildpathEntries;
		}

		@Override
		public String getName() {
			return Messages.IncludepathRenameIParentParticipant_0;
		}

		@Override
		public void initializeValidationData(IProgressMonitor pm) {

		}

		@Override
		public RefactoringStatus isValid(IProgressMonitor pm)
				throws CoreException, OperationCanceledException {
			return new RefactoringStatus();
		}

		@Override
		public Change perform(IProgressMonitor pm) throws CoreException {
			IncludePathManager.getInstance().setIncludePath(newProject,
					newIncludePathEntries);
			// try {
			// DLTKCore.create(project).setRawBuildpath(newBuildpathEntries,
			// new NullProgressMonitor());
			// } catch (ModelException e) {
			// if (DLTKCore.DEBUG) {
			// e.printStackTrace();
			// }
			// }
			return new IncludepathChange(newProject, project,
					newIncludePathEntries, oldIncludePathEntries,
					newBuildpathEntries, oldBuildpathEntries);
		}

		@Override
		public Object getModifiedElement() {
			return Messages.IncludepathRenameIParentParticipant_0;
		}

	}
}
