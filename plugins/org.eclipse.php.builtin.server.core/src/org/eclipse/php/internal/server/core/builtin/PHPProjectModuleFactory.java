/*******************************************************************************
 * Copyright (c) 2017, 2018 Alex Xu and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Alex Xu - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.server.core.builtin;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.php.core.PHPToolkitUtil;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.project.ProjectOptions;
import org.eclipse.wst.server.core.IModule;
import org.eclipse.wst.server.core.model.ModuleDelegate;
import org.eclipse.wst.server.core.util.ProjectModuleFactoryDelegate;

public class PHPProjectModuleFactory extends ProjectModuleFactoryDelegate {

	private static final IModule[] EMPTY_MODULE = new IModule[0];

	@Override
	public ModuleDelegate getModuleDelegate(IModule module) {
		return new PHPProjectModule(module.getProject());
	}

	@Override
	protected IModule[] createModules(IProject project) {
		try {
			if (!PHPToolkitUtil.isPHPProject(project)) {
				return EMPTY_MODULE;
			}
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
		if ( phpVersion == null) {
			return null;
		}
		return phpVersion.getAlias().substring(3);
	}

	@Override
	protected IPath[] getListenerPaths() {
		return new IPath[] { Path.EMPTY };
	}

}
