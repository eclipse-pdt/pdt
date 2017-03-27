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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.model.LaunchConfigurationDelegate;
import org.eclipse.php.internal.debug.core.IPHPDebugConstants;
import org.eclipse.wst.server.core.IServer;
import org.eclipse.wst.server.core.ServerCore;
import org.eclipse.wst.server.core.ServerUtil;

@SuppressWarnings("restriction")
public class PHPServerLaunchConfigurationDelegate extends LaunchConfigurationDelegate {

	@Override
	public void launch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor)
			throws CoreException {
		IServer server = ServerUtil.getServer(configuration);
		if (server == null) {
			Trace.trace(Trace.FINEST, "Launch configuration could not find server"); //$NON-NLS-1$
			return;
		}

		PHPServerBehaviour phpServer = (PHPServerBehaviour) server.loadAdapter(PHPServerBehaviour.class, null);

		if (server.shouldPublish() && ServerCore.isAutoPublishing())
			server.publish(IServer.PUBLISH_INCREMENTAL, monitor);

		// Determine PHP configuration file location:
		String workingDir = phpServer.getServerDeployDirectory().toOSString();
		int port = phpServer.getPHPServerConfiguration().getMainPort().getPort();
		String phpExeLocation = configuration.getAttribute(IPHPDebugConstants.ATTR_EXECUTABLE_LOCATION, ""); //$NON-NLS-1$
		String phpIniLocation = configuration.getAttribute(IPHPDebugConstants.ATTR_INI_LOCATION, ""); //$NON-NLS-1$

		PHPServerRunnerConfiguration runConfig = new PHPServerRunnerConfiguration(phpExeLocation, port);
		runConfig.setWorkingDirectory(workingDir);
		runConfig.setIniFilePath(phpIniLocation);

		IPHPServerRunner runner = null;

		if (ILaunchManager.RUN_MODE.equals(mode)) {
			runner = new DefaultPHPServerRunner();
		} else if (ILaunchManager.DEBUG_MODE.equals(mode)) {
			runner = new DefaultPHPServerDebugger();
		} else {
			return;
		}

		// Launch the configuration
		phpServer.setupLaunch(launch, mode, monitor);
		phpServer.setPHPServerRunner(runner);
		try {
			runner.run(runConfig, launch, monitor);
			phpServer.addProcessListener(launch.getProcesses()[0]);
		} catch (Exception e) {
			// Ensure we don't continue to think the server is starting
			phpServer.stopImpl();
		}
	}

}
