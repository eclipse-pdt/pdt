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
package org.eclipse.php.core.project.deployables;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.core.resources.IProject;
import org.eclipse.php.core.project.PHPNature;
import org.eclipse.wst.server.core.IModule;
import org.eclipse.wst.server.core.model.ModuleDelegate;
import org.eclipse.wst.server.core.util.ProjectModuleFactoryDelegate;

public class PHPDeployableFactory extends ProjectModuleFactoryDelegate {
	private static final String ID = "org.eclipse.php.deployable.php"; //$NON-NLS-1$
	protected ArrayList moduleDelegates = new ArrayList();

	/*
	 * @see DeployableProjectFactoryDelegate#getFactoryID()
	 */
	public String getFactoryId() {
		return ID;
	}

	/**
	 * Returns true if the project represents a deployable project of this type.
	 * 
	 * @param project
	 *            org.eclipse.core.resources.IProject
	 * @return boolean
	 */
	protected boolean isValidModule(IProject project) {
		return false;
	}

	/**
	 * Creates the deployable project for the given project.
	 * 
	 * @param project
	 *            org.eclipse.core.resources.IProject
	 * @return com.ibm.etools.server.core.model.IDeployableProject
	 */
	protected IModule createModule(IProject project) {
		try {
			IModule deployable = null;
			PHPDeployable projectModule = null;
			if (project.hasNature(PHPNature.ID)) {
				PHPNature nature = (PHPNature) project.getNature(PHPNature.ID);
				deployable = nature.getModule();
				if (deployable == null) {
					projectModule = new PHPDeployable(nature.getProject());
					deployable = createModule(projectModule.getId(), projectModule.getName(), projectModule.getType(), projectModule.getVersion(), projectModule.getProject());
					nature.setModule(deployable);
					projectModule.initialize(deployable);
					//deployable = projectModule.getModule();
				}
				moduleDelegates.add(projectModule);
				return deployable;
			}
		} catch (Exception e) {
			//Ignore
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.wst.server.core.model.ModuleFactoryDelegate#getModuleDelegate(org.eclipse.wst.server.core.IModule)
	 */
	public ModuleDelegate getModuleDelegate(IModule module) {
		for (Iterator iter = moduleDelegates.iterator(); iter.hasNext();) {
			ModuleDelegate element = (ModuleDelegate) iter.next();
			if (module == element.getModule())
				return element;
		}
		return null;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.wst.server.core.model.ModuleFactoryDelegate#getModules()
	 */
/*	public IModule[] getModules() {
		if (projects == null || projects.isEmpty())
			cacheModules();
		int i = 0;
		Iterator modules = projects.keySet().iterator();
		IModule[] modulesArray = new IModule[projects.size()];
		while (modules.hasNext()) {
			IModule[] module = null;
			IProject project = (IProject) modules.next();
			module = (IModule[]) projects.get(project);
			modulesArray[i++] = module[0];
		}
		return modulesArray;

	} */

	/* (non-Javadoc)
	 * @see org.eclipse.wst.server.core.util.ProjectModuleFactoryDelegate#createModules(org.eclipse.core.resources.IProject)
	 */
	protected IModule[] createModules(IProject project) {

		IModule mod = createModule(project);
		return (mod == null) ? null : new IModule[] { mod };
	}
}