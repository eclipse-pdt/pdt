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
package org.eclipse.php.composer.core.launch;

import java.util.HashMap;

import javax.inject.Inject;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.php.composer.core.ComposerPlugin;
import org.eclipse.php.composer.core.launch.environment.Environment;
import org.eclipse.php.composer.core.launch.environment.EnvironmentFactory;
import org.eclipse.php.composer.core.log.Logger;

/**
 * 
 * @Inject the {@link ScriptLauncherManager} into your service to retrieve a
 *         {@link ScriptLauncherInterface} for executing PHP scripts.
 * 
 *         See composer exmaple implementation
 */
@Creatable
public class ScriptLauncherManager implements ScriptLauncherInterface {

	private static final String LAUNCHER_ID = ComposerPlugin.ID + ".executableLauncher"; //$NON-NLS-1$
	private final HashMap<String, EnvironmentFactory> factories = new HashMap<String, EnvironmentFactory>();

	@Inject
	public ScriptLauncherManager(IExtensionRegistry registry) {
		evaluate(registry);
	}

	private void evaluate(IExtensionRegistry registry) {
		try {
			IConfigurationElement[] config = registry.getConfigurationElementsFor(LAUNCHER_ID);
			for (IConfigurationElement e : config) {
				final EnvironmentFactory factory = (EnvironmentFactory) e.createExecutableExtension("class"); //$NON-NLS-1$
				if (factory != null) {
					factories.put(e.getAttribute("id"), factory); //$NON-NLS-1$
				}
			}
		} catch (Exception e) {
			Logger.logException(e);
		}
	}

	private Environment getEnvironment(String factoryId, IProject project) throws ExecutableNotFoundException {

		if (!factories.containsKey(factoryId)) {
			return null;
		}

		return factories.get(factoryId).getEnvironment(project);
	}

	@Override
	public ScriptLauncher getLauncher(String factoryId, IProject project)
			throws ScriptNotFoundException, ExecutableNotFoundException {
		Environment env = getEnvironment(factoryId, project);
		if (env == null) {
			throw new ExecutableNotFoundException(Messages.ScriptLauncherManager_CannotFindExe);
		}

		return new ScriptLauncher(env, project);
	}

	@Override
	public void resetEnvironment() {
		// TODO: ?
		/*
		 * if (env != null) { synchronized (env) { env = null; } }
		 */
	}
}
