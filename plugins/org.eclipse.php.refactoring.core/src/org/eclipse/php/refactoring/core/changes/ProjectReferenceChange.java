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

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.*;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IBuildpathEntry;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.internal.core.includepath.IncludePath;
import org.eclipse.php.internal.core.includepath.IncludePathManager;
import org.eclipse.php.refactoring.core.rename.Messages;

public class ProjectReferenceChange extends Change {

	private String oldName;
	private String newName;
	private IProject[] referencing;
	private IBuildpathEntry newEntry;

	public ProjectReferenceChange(String oldName, String newName,
			IProject[] referencing) {
		this.oldName = oldName;
		this.newName = newName;
		this.referencing = referencing;
	}

	@Override
	public Object getModifiedElement() {
		return referencing;
	}

	@Override
	public String getName() {
		return NLS.bind(Messages.ProjectReferenceChange_0, new String[] {
				oldName, newName });
	}

	@Override
	public void initializeValidationData(IProgressMonitor pm) {

	}

	@Override
	public RefactoringStatus isValid(IProgressMonitor pm) throws CoreException,
			OperationCanceledException {
		return new RefactoringStatus();
	}

	@Override
	public Change perform(IProgressMonitor pm) throws CoreException {

		try {
			pm.beginTask(getName(), 1);

			pm.beginTask(Messages.ProjectReferenceChange_1, referencing.length);
			for (int i = 0; i < referencing.length; i++) {
				IScriptProject jp = DLTKCore.create(referencing[i]);
				if (jp != null && jp.exists()) {
					modifyBuildpath(jp, new SubProgressMonitor(pm, 1));
				} else {
					pm.worked(1);
				}
			}
		} finally {
			pm.done();
		}

		return new ProjectReferenceChange(newName, oldName, referencing);
	}

	private void modifyBuildpath(IScriptProject referencingProject,
			IProgressMonitor pm) throws ModelException {
		pm.beginTask("", 1); //$NON-NLS-1$

		IProject project = referencingProject.getProject();

		IBuildpathEntry[] oldEntries = referencingProject.getRawBuildpath();
		IBuildpathEntry[] newEntries = new IBuildpathEntry[oldEntries.length];
		for (int i = 0; i < newEntries.length; i++) {
			if (isOurEntry(oldEntries[i])) {
				newEntries[i] = createModifiedEntry(oldEntries[i]);
			} else {
				newEntries[i] = oldEntries[i];
			}
		}

		IncludePath[] includes = IncludePathManager.getInstance()
				.getIncludePaths(project);

		IncludePath[] newInclude = new IncludePath[includes.length];

		for (int i = 0; i < includes.length; i++) {
			if (isOurEntry(includes[i])) {
				newInclude[i] = createModifiedIncludePath(includes[i], project);
			} else {
				newInclude[i] = includes[i];
			}
		}

		referencingProject.setRawBuildpath(newEntries, pm);

		IncludePathManager.getInstance().setIncludePath(project, newInclude);

		IncludePathManager.getInstance().refresh(project);

		pm.done();

	}

	private boolean isOurEntry(IncludePath includePath) {
		Object entry = includePath.getEntry();
		if (entry instanceof IProject) {
			return ((IProject) entry).getName().equals(oldName);
		}
		if (entry instanceof IBuildpathEntry) {
			return isOurEntry((IBuildpathEntry) entry);
		}
		return false;
	}

	private IncludePath createModifiedIncludePath(IncludePath includePath,
			IProject project) {

		Object entry = includePath.getEntry();
		if (entry instanceof IProject) {

		}
		if (entry instanceof IBuildpathEntry) {
			newEntry = createModifiedEntry((IBuildpathEntry) entry);
			return new IncludePath(newEntry, project);
		}

		return null;
	}

	private boolean isOurEntry(IBuildpathEntry cpe) {
		if (cpe.getEntryKind() != IBuildpathEntry.BPE_PROJECT) {
			return false;
		}
		if (!cpe.getPath().equals(new Path("/" + oldName))) {
			return false;
		}
		return true;
	}

	private IBuildpathEntry createModifiedEntry(IBuildpathEntry oldEntry) {
		return DLTKCore.newProjectEntry(createNewPath(),
				oldEntry.getAccessRules(), oldEntry.combineAccessRules(),
				oldEntry.getExtraAttributes(), oldEntry.isExported());
	}

	protected IPath createNewPath() {
		return new Path("/" + newName); //$NON-NLS-1$
	}
}
