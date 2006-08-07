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
package org.eclipse.php.server.core.launch;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.*;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.LaunchConfigurationDelegate;
import org.eclipse.php.server.core.Activator;
import org.eclipse.php.server.core.Logger;
import org.eclipse.php.server.core.Server;
import org.eclipse.php.server.core.deploy.DeployFilter;
import org.eclipse.php.server.core.deploy.FileUtil;
import org.eclipse.php.server.core.manager.ServersManager;

/**
 * Server launch configuration delegate.
 */
//public class ServerLaunchConfigurationDelegate extends AbstractJavaLaunchConfigurationDelegate {
public class ServerLaunchConfigurationDelegate extends LaunchConfigurationDelegate {

	private static final String PHP_IDE_PLUGIN_PREFIX = "org.eclipse.php.";

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.debug.core.model.ILaunchConfigurationDelegate#launch(org.eclipse.debug.core.ILaunchConfiguration, java.lang.String, org.eclipse.debug.core.ILaunch, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void launch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor) throws CoreException {

		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] elements = registry.getConfigurationElementsFor(Activator.PLUGIN_ID, "httpServerLaunchDelegate");
		if (elements == null || elements.length == 0 || !elements[0].getName().equals("launchDelegate")) {
			doLaunch(configuration, mode, launch, monitor);
			return;
		}

		// Get the best match configuration element
		IConfigurationElement element = null;
		for (int i = 0; i < elements.length; i++) {
			element = elements[i];
			// Stop the search at the first external plugin.
			if (!elements[i].getNamespaceIdentifier().startsWith(PHP_IDE_PLUGIN_PREFIX)) {
				break;
			}
		}
		IHTTPServerLaunch serverLaunch = (IHTTPServerLaunch) element.createExecutableExtension("class");
		serverLaunch.setHTTPServerDelegate(this);
		serverLaunch.launch(configuration, mode, launch, monitor);
	}

	public void doLaunch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor) throws CoreException {

		Server server = ServersManager.getServer(configuration.getAttribute(Server.NAME, ""));
		if (server == null) {
			Logger.log(Logger.ERROR, "Launch configuration could not find server");
			//throw CoreException();
			return;
		}
		String fileName = configuration.getAttribute(Server.FILE_NAME, (String) null);
		// Get the project from the file name
		IPath filePath = new Path(fileName);
		IProject proj = null;
		try {
			proj = ResourcesPlugin.getWorkspace().getRoot().getProject(filePath.segment(0));
		} catch (Throwable t) {
		}
		if (proj == null) {
			Logger.log(Logger.ERROR, "Could not launch (Project is null).");
			return;
		}

		boolean publish = configuration.getAttribute(Server.PUBLISH, false);
		if (publish) {
			if (!FileUtil.publish(server, proj, configuration, DeployFilter.getFilterMap(), monitor)) {
				// Return if the publish failed.
				return;
			}
		}
	}

	protected void run(IWorkspaceRunnable wr) throws DebugException {
		try {
			ResourcesPlugin.getWorkspace().run(wr, null, 0, null);
		} catch (CoreException e) {
			throw new DebugException(e.getStatus());
		}
	}
}
