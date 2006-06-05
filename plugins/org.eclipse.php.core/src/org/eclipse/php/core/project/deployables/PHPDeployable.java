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

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.php.core.project.PHPNature;
import org.eclipse.wst.server.core.IModule;
import org.eclipse.wst.server.core.IModuleType;
import org.eclipse.wst.server.core.util.ProjectModule;

public class PHPDeployable extends ProjectModule {
	
	private IProject project;

	public PHPDeployable(IProject project) {
		super(project);
		this.project = project;
//		setPHPNature(getPHPNature());
	}

//	private void setPHPNature(PHPNature nature) {
//		nature.setProject(getProject());
//	}

	// RSG - is this method needed?
	public String getFactoryId() {
		return "org.eclipse.php.deployable"; //$NON-NLS-1$
	}

	/**
	 * Returns true if this deployable currently exists, and false if it has been deleted or moved
	 * and is no longer represented by this deployable.
	 * 
	 * @return boolean
	 */
	public boolean exists() {
		if (getProject() == null || !getProject().exists())
			return false;
		try {
			return (this.project.hasNature(PHPNature.ID));
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return false;
	}

	protected PHPNature getPHPNature() {
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

	public String getType() {
		return PHPNature.PROJECTTYPE_VALUE; //$NON-NLS-1$
	}

	public String getVersion() {
		return "1.0"; //$NON-NLS-1$
	}

	/**
	 * Returns the root folder. The root folder is the project relative path
	 * that points to the root directory of the module. All resources contained
	 * within this folder belong to the module.
	 * 
	 * @return the root project-relative folder that contains the contents
	 *    of the module
	 */
	public IPath getRootFolder() {
		IPath root = project.getLocation();
		return root;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.wst.server.core.IModule#validate(org.eclipse.core.runtime.IProgressMonitor)
	 */
	public IStatus validate(IProgressMonitor monitor) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.wst.server.core.IModule#getModuleType()
	 */
	public IModuleType getModuleType() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.wst.server.core.IModule#getChildModules(org.eclipse.core.runtime.IProgressMonitor)
	 */
	public IModule[] getChildModules(IProgressMonitor monitor) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
	 */
	public Object getAdapter(Class adapter) {
		// TODO Auto-generated method stub
		return null;
	}
}