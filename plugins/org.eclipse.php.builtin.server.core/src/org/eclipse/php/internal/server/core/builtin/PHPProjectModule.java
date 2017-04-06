/*******************************************************************************
 * Copyright (c) 2017 Alex Xu and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Alex Xu - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.server.core.builtin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IProjectFragment;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.wst.server.core.model.IModuleResource;
import org.eclipse.wst.server.core.util.ModuleFile;
import org.eclipse.wst.server.core.util.ModuleFolder;
import org.eclipse.wst.server.core.util.ProjectModule;

public class PHPProjectModule extends ProjectModule {

	public static final String PHP_MODULE_TYPE_ID = "php.web"; //$NON-NLS-1$

	private IScriptProject fScriptProject;

	/**
	 * Create a new project module in the given project.
	 * 
	 * @param project
	 *            the project containing the module
	 */
	public PHPProjectModule(IProject project) {
		super(project);
		fScriptProject = DLTKCore.create(project);
	}

	/**
	 * Return the module resources for a given path.
	 * 
	 * @param path
	 *            a path
	 * @param container
	 *            a container
	 * @return an array of module resources
	 * @throws CoreException
	 */
	@Override
	protected IModuleResource[] getModuleResources(IPath path, IContainer container) throws CoreException {
		return internalGetModuleResources(path, container);
	}

	private IModuleResource[] internalGetModuleResources(IPath path, IContainer container) throws CoreException {
		IResource[] resources = null;
		if (container instanceof IProject) {
			List<IResource> list = new ArrayList<IResource>();
			IProjectFragment[] projectFragments = fScriptProject.getAllProjectFragments();
			for (IProjectFragment fragment : projectFragments) {
				if (!fragment.isExternal()) {
					list.addAll(Arrays.asList(((IContainer) fragment.getResource()).members()));
				}
			}
			resources = list.toArray(new IResource[list.size()]);
		} else {
			resources = container.members();
		}
		if (resources != null) {
			int size = resources.length;
			List<IModuleResource> list = new ArrayList<IModuleResource>(size);
			for (int i = 0; i < size; i++) {
				IResource resource = resources[i];
				if (resource != null && resource.exists()) {
					String name = resource.getName();
					if (resource instanceof IContainer) {
						IContainer container2 = (IContainer) resource;
						ModuleFolder mf = new ModuleFolder(container2, name, path);
						mf.setMembers(internalGetModuleResources(path.append(name), container2));
						list.add(mf);
					} else if (resource instanceof IFile) {
						list.add(new ModuleFile((IFile) resource, name, path));
					}
				}
			}
			IModuleResource[] moduleResources = new IModuleResource[list.size()];
			list.toArray(moduleResources);
			return moduleResources;
		}
		return new IModuleResource[0];

	}

}
