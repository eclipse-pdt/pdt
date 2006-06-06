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
/*
 * Created on Feb 21, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.eclipse.php.core.project.deployables;

import java.util.Arrays;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.php.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.php.core.project.PHPNature;
import org.eclipse.wst.server.core.IModule;
import org.eclipse.wst.server.core.IModuleArtifact;
import org.eclipse.wst.server.core.ServerUtil;
import org.eclipse.wst.server.core.util.WebResource;



public class PHPDeployableObjectAdapterUtil {

	// private final static String[] extensionsToExclude = new String[]{}; //$NON-NLS-1$ //$NON-NLS-2$

	public static PHPNature getNature(IProject project) {
		if (project == null)
			return null;
		try {
			PHPNature nature = null;
			if (project.hasNature(PHPNature.ID))
				nature = (PHPNature) project.getNature(PHPNature.ID);
			return nature;
		} catch (CoreException e) {
			return null;
		}
	}

	public static IModuleArtifact getModuleObject(Object obj) {
		IResource resource = null;
		if (obj instanceof IResource)
			resource = (IResource) obj;
		else if (obj instanceof IAdaptable)
			resource = (IResource) ((IAdaptable) obj).getAdapter(IResource.class);
		else if(obj instanceof PHPFileData)
		{
			PHPFileData fileData = (PHPFileData)obj;
			String name = fileData.getName();
			resource = createResource(name);
		}
		if (resource == null)
			return null;

		// find deployable
		PHPNature phpNature = getNature(resource.getProject());
		if (phpNature == null || !(phpNature instanceof PHPNature))
			return null;

		if (resource instanceof IProject)
			return new WebResource(getModule(phpNature), resource.getFullPath()); //$NON-NLS-1$

		// determine path
		IPath resourcePath = resource.getProjectRelativePath();

		// return Web resource type
		return new WebResource(getModule(phpNature), resourcePath);

	}
	
	private static IResource createResource(String name)
	{
		StringTokenizer st = new StringTokenizer(name, "/");
		String projectName = st.nextToken();
		String fileName = st.nextToken();
		
		IWorkspaceRoot workspace = ResourcesPlugin.getWorkspace().getRoot();
		IProject proj = workspace.getProject(projectName);
		
		if(fileName == null)
			return null;
		
		return (IResource)proj.getFile(fileName);
	}

	protected static IModule getModule(PHPNature nature) {
		IModule deployable = nature.getModule();
		if (deployable != null)
			return deployable;

		IProject project = nature.getProject();
		Iterator iterator = Arrays.asList(ServerUtil.getModules(PHPNature.PROJECTTYPE_VALUE)).iterator(); //$NON-NLS-1$ //$NON-NLS-2$
		while (iterator.hasNext()) {
			deployable = (IModule) iterator.next();
			if (deployable != null) {
				if ((deployable).getProject().equals(project))
					return deployable;
			}
		}
		return null;
	}

}
