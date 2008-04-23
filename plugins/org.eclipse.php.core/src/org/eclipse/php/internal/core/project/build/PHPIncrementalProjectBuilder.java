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
package org.eclipse.php.internal.core.project.build;

import java.util.Map;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.php.core.project.build.IPHPBuilderExtension;
import org.eclipse.php.internal.core.CoreMessages;
import org.eclipse.php.internal.core.Logger;

/**
 * This is a composite PHP builder which hold references to all extensions contributed to it,
 * and delegates all the responsibility to them.
 * 
 * @author michael
 */
public class PHPIncrementalProjectBuilder extends IncrementalProjectBuilder {

	private IPHPBuilderExtension[] extensions;

	public PHPIncrementalProjectBuilder() {
		extensions = PHPBuilderExtensionsRegistry.getInstance().getExtensions();
	}

	/**
	 * We do not support returning projects from this method, <code>null</code> will be returned always.
	 * @see IncrementalProjectBuilder#build(int, Map, IProgressMonitor)
	 * @return <code>null</code>
	 */
	protected IProject[] build(final int kind, final Map args, IProgressMonitor monitor) throws CoreException {
		final IProject project = getProject();
		final IResourceDelta delta = getDelta(project);
		if (kind == IncrementalProjectBuilder.AUTO_BUILD) {
			RSEFolderReporter visitor = new RSEFolderReporter();
			try {
				delta.accept(visitor);
			} catch (CoreException e) {
				Logger.logException(e);
			}
			if (visitor.isNewJobNeeded()) {
				WorkspaceJob j = new WorkspaceJob("Building PHP projects") {

					@Override
					public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
						internalBuild(project, delta, kind, args, monitor);
						return Status.OK_STATUS;
					}

				};
				j.setPriority(Job.LONG);
				j.setUser(false);
				j.schedule();
				return null;
			}
		}
		return internalBuild(project, delta, kind, args, monitor);
	}

	private IProject[] internalBuild(IProject project, IResourceDelta delta, int kind, Map args, IProgressMonitor monitor) throws CoreException {
		monitor.beginTask(CoreMessages.getString("PHPIncrementalProjectBuilder_0"), extensions.length);
		int numOfFiles = 1;
		if (delta != null) {
			FileCounter fc = new FileCounter();
			delta.accept(fc);
			numOfFiles = fc.numOfFiles;
		}
		for (int i = 0; i < extensions.length; ++i) {
			if (extensions[i].isEnabled()) {
				IProgressMonitor subMonitor = new SubProgressMonitor(monitor, 1);
				subMonitor.beginTask(project.getName(), numOfFiles);
				extensions[i].build(project, delta, kind, args, subMonitor);
			}
		}
		return null;
	}

	class FileCounter implements IResourceDeltaVisitor {

		public int numOfFiles = 0;

		public boolean visit(IResourceDelta delta) throws CoreException {
			if (delta.getResource().getType() == IResource.FILE) {
				numOfFiles++;
				return false;
			}
			return true;
		}

	}

	protected void clean(IProgressMonitor monitor) throws CoreException {
		for (int i = 0; i < extensions.length; ++i) {
			if (extensions[i].isEnabled()) {
				extensions[i].clean(getProject(), monitor);
			}
		}
	}

	protected void startupOnInitialize() {
		super.startupOnInitialize();

		for (int i = 0; i < extensions.length; ++i) {
			extensions[i].startupOnInitialize(this);
		}
	}
}
