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

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.project.ProjectOptions;
import org.eclipse.php.internal.core.project.PHPNature;
import org.eclipse.wst.server.core.IModule;
import org.eclipse.wst.server.core.model.ModuleDelegate;
import org.eclipse.wst.server.core.util.ProjectModuleFactoryDelegate;

@SuppressWarnings("restriction")
public class PHPProjectModuleFactory extends ProjectModuleFactoryDelegate {

	private static final IModule[] EMPTY_MODULE = new IModule[0];

	@Override
	public ModuleDelegate getModuleDelegate(IModule module) {
		return new PHPProjectModule(module.getProject());
	}

	@Override
	protected IModule[] createModules(IProject project) {
		try {
			IProjectNature nature = project.getNature(PHPNature.ID);
			if (nature == null)
				return EMPTY_MODULE;
		} catch (CoreException e) {
			return EMPTY_MODULE;
		}

		PHPVersion phpVersion = ProjectOptions.getPHPVersion(project);
		String moduleVersion = getModuleVersion(phpVersion);
		if (moduleVersion == null) {
			return EMPTY_MODULE;
		}
		String id = project.getName();
		String name = project.getName();
		IModule module = createModule(id, name, PHPProjectModule.PHP_MODULE_TYPE_ID, moduleVersion, project);
		return new IModule[] { module };

	}

	private String getModuleVersion(PHPVersion phpVersion) {
		switch (phpVersion) {
		case PHP5:
			return "5.0"; //$NON-NLS-1$
		case PHP5_3:
			return "5.3"; //$NON-NLS-1$
		case PHP5_4:
			return "5.4"; //$NON-NLS-1$
		case PHP5_5:
			return "5.5"; //$NON-NLS-1$
		case PHP5_6:
			return "5.6"; //$NON-NLS-1$
		case PHP7_0:
			return "7.0"; //$NON-NLS-1$
		case PHP7_1:
			return "7.1"; //$NON-NLS-1$
		default:
			return null;
		}
	}

}
