/*******************************************************************************
 * Copyright (c) 2012, 2016, 2017 PDT Extension Group and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *     Kaloyan Raev - [501269] externalize strings
 *******************************************************************************/
package org.eclipse.php.composer.core.launch.environment;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IPreferencesService;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.php.composer.core.launch.ExecutableNotFoundException;
import org.eclipse.php.composer.core.launch.LaunchUtil;

public abstract class AbstractEnvironmentFactory implements EnvironmentFactory {

	@Override
	public Environment getEnvironment(IProject project) {

		IPreferencesService service = Platform.getPreferencesService();
		IScopeContext[] contexts = new IScopeContext[] { new ProjectScope(project), InstanceScope.INSTANCE,
				DefaultScope.INSTANCE };
		String executable = service.getString(getPluginId(), getExecutableKey(), null, contexts);
		String useProjectPhar = service.getString(getPluginId(), getUseProjectKey(), null, contexts);
		String systemPhar = service.getString(getPluginId(), getScriptKey(), null, contexts);

		if (executable == null || executable.isEmpty()) {
			// the user has not set any preference for PHP executable yet,
			// so try finding any PHP executable, e.g. contributed via the
			// phpExe extension point
			try {
				executable = LaunchUtil.getPHPExecutable();
			} catch (ExecutableNotFoundException e) {
				// no php exe found - executable will remain null
			}
		}

		if (executable != null && !executable.isEmpty()) {
			if (useProjectPhar != null && Boolean.parseBoolean(useProjectPhar)
					|| (systemPhar == null || systemPhar.length() == 0)) {
				return getProjectEnvironment(executable);
			}

			return new SysPhpSysPhar(executable, systemPhar);
		}

		return null;
	}

	/**
	 * Get the preference store of the plugin using the launcher.
	 * 
	 * @return
	 */
	protected abstract String getPreferenceQualifier();

	/**
	 * Get the Plugin ID of the plugin using the launcher.
	 * 
	 * @return
	 */
	protected abstract String getPluginId();

	/**
	 * 
	 * Get the {@link Environment} which uses a script inside the project for
	 * execution.
	 * 
	 * @param executable
	 * @return
	 */
	protected abstract PrjPharEnvironment getProjectEnvironment(String executable);

	/**
	 * Get the key for the php executable.
	 * 
	 * @return
	 */
	protected abstract String getExecutableKey();

	/**
	 * Get the key for the preference setting if the launcher should use a
	 * script per project or a global script for launching.
	 * 
	 * @return
	 */
	protected abstract String getUseProjectKey();

	/**
	 * Get the key for the preference setting for the location of the php script
	 * to execute (if the user selected a global script for every launcher)
	 * 
	 * @return
	 */
	protected abstract String getScriptKey();

}
