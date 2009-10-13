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
package org.eclipse.php.internal.debug.core.sourcelookup.containers;

import java.io.File;
import java.util.ArrayList;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.sourcelookup.ISourceContainer;
import org.eclipse.debug.core.sourcelookup.ISourceContainerType;
import org.eclipse.debug.core.sourcelookup.containers.CompositeSourceContainer;
import org.eclipse.debug.core.sourcelookup.containers.DirectorySourceContainer;
import org.eclipse.debug.core.sourcelookup.containers.ExternalArchiveSourceContainer;
import org.eclipse.debug.core.sourcelookup.containers.ProjectSourceContainer;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IBuildpathEntry;
import org.eclipse.dltk.core.IProjectFragment;

public class PHPCompositeSourceContainer extends CompositeSourceContainer {

	private IProject project;

	public PHPCompositeSourceContainer(IProject project,
			ILaunchConfiguration configuration) {
		// DBGpTarget passes null for the ILaunchConfiguration here.
		this.project = project;
	}

	protected ISourceContainer[] createSourceContainers() throws CoreException {
		ArrayList<ISourceContainer> containers = new ArrayList<ISourceContainer>();

		ISourceContainer projectContainer = new ProjectSourceContainer(project,
				false);
		containers.add(projectContainer);
		IBuildpathEntry[] entries = DLTKCore.create(project).getRawBuildpath();
		if (entries != null) {
			for (IBuildpathEntry element : entries) {
				if (element.getEntryKind() == IBuildpathEntry.BPE_LIBRARY) {
					IPath path = element.getPath();
					File file = new File(path.toOSString());
					if (element.getContentKind() == IProjectFragment.K_BINARY) {
						containers.add(new ExternalArchiveSourceContainer(file
								.getAbsolutePath(), false));
					} else {
						containers
								.add(new DirectorySourceContainer(file, false));
					}
				} else if (element.getEntryKind() == IBuildpathEntry.BPE_PROJECT) {
					IResource resource = ResourcesPlugin.getWorkspace()
							.getRoot().findMember(
									element.getPath().lastSegment());
					if (resource instanceof IProject) {
						IProject includeProject = (IProject) resource;
						containers.add(new ProjectSourceContainer(
								includeProject, false));
					}
				} else if (element.getEntryKind() == IBuildpathEntry.BPE_VARIABLE) {
					IPath path = element.getPath();
					containers.add(new PHPVariableSourceContainer(path));
				}
			}
		}

		ISourceContainer[] scontainers = new ISourceContainer[containers.size()];
		containers.toArray(scontainers);
		return scontainers;
	}

	public Object[] findSourceElements(String name) throws CoreException {
		IResource resource = ResourcesPlugin.getWorkspace().getRoot()
				.findMember(name);
		if (resource != null) {
			return new Object[] { resource };
		}
		Object[] objs = super.findSourceElements(name);
		return objs;
	}

	public String getName() {
		return null;
	}

	public ISourceContainerType getType() {
		return null;
	}
}
