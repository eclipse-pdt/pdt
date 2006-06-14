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
import org.eclipse.php.server.core.deploy.FileUtil;

/**
 * 
 */
//public class ServerLaunchConfigurationDelegate extends AbstractJavaLaunchConfigurationDelegate {
public class ServerLaunchConfigurationDelegate extends LaunchConfigurationDelegate {
	//
	//    private ILaunch getLaunchableAdapter(Server server) {
	//        //get the launchable adapter
	//        ILaunchableAdapter launchableAdapter = null;
	//        Object launchable = null;
	//        ILaunchableAdapter[] adapters = ServerPlugin.getLaunchableAdapters();
	//        if (adapters != null) {
	//            int size2 = adapters.length;
	//            for (int j = 0; j < size2; j++) {
	//                ILaunchableAdapter adapter = adapters[j];
	//                try {
	//                    Object launchable2 = adapter.getLaunchable(server, moduleArtifact);
	//                    if (launchable2 != null) {
	//                        launchableAdapter = adapter;
	//                        launchable = launchable2;
	//                    }
	//                } catch (Exception e) {
	//                    Logger.logException("Error in launchable adapter", e);
	//                }
	//            }
	//        }
	//
	//        return launchableAdapter;
	//    }

	//    private IClient[] getClients(IServer server, Object launchable, String launchMode) {
	//        ArrayList list = new ArrayList();
	//        IClient[] clients = ServerPlugin.getClients();
	//        if (clients != null) {
	//            int size = clients.length;
	//            for (int i = 0; i < size; i++) {
	//                if (clients[i].supports(server, launchable, launchMode))
	//                    list.add(clients[i]);
	//            }
	//        }
	//
	//        IClient[] clients2 = new IClient[list.size()];
	//        list.toArray(clients2);
	//        return clients2;
	//    }

	//    public IModuleArtifact getModuleArtifact(ILaunchConfiguration configuration) {
	//        IModuleArtifact moduleArtifact = null;
	//
	//        try {
	//            String fileName = configuration.getAttribute(Activator.FILE_NAME, "");
	//            IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
	//            IResource resource = root.findMember(fileName);
	//
	//            if (resource == null)
	//                return null;
	//
	//            moduleArtifact = ServerPlugin.getModuleArtifact(resource);
	//
	//        } catch (CoreException e) {
	//
	//        }
	//
	//        return moduleArtifact;
	//    }

	public void launch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor) throws CoreException {

		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] elements = registry.getConfigurationElementsFor(Activator.PLUGIN_ID, "httpServerLaunchDelegate");

		if (elements == null || !elements[0].getName().equals("launchDelegate")) {
			doLaunch(configuration, mode, launch, monitor);
			return;
		}

		IConfigurationElement element = elements[0];
		IHTTPServerLaunch serverLaunch = (IHTTPServerLaunch) element.createExecutableExtension("class");
		serverLaunch.setHTTPServerDelegate(this);

		serverLaunch.launch(configuration, mode, launch, monitor);
	}

	public void doLaunch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor) throws CoreException {

		Server server = null; //ServersManager.getServer(configuration.); // TODO - Get the server name
		if (server == null) {
			Logger.log(Logger.ERROR, "Launch configuration could not find server");
			//throw CoreException();
			return;
		}

		//        ApacheServerBehaviour apacheServerBehaviour = (ApacheServerBehaviour) server.loadAdapter(ApacheServerBehaviour.class, null);
		//        apacheServerBehaviour.setupLaunch(launch, mode, monitor);
		//
		//        IModuleArtifact moduleArtifact = getModuleArtifact(configuration);
		//        if (moduleArtifact == null)
		//            return;
		//
		//        IModule module = moduleArtifact.getModule();
		//        ILaunchableAdapter launchableAdapter = getLaunchableAdapter(server, moduleArtifact);
		//        Object launchable = launchableAdapter.getLaunchable(server, moduleArtifact);
		//        IClient client = getClient(launchable, server, mode);
		//
		//        if (client == null) {
		//            //EclipseUtil.openError(Messages.errorNoClient);
		//            Logger.log(Logger.WARNING, "No launchable clients!");
		//            return;
		//        }

		//        IModule[] modules = new IModule[] { module }; // TODO: get parent heirarchy correct

		boolean publish = configuration.getAttribute(Server.PUBLISH, false);

		if (publish) {
//			FileUtil.publish(null, configuration, monitor);
		}
		//        LaunchClientJob clientJob = new LaunchClientJob(server, modules, mode, moduleArtifact, launchableAdapter, client);
		//        clientJob.schedule();

	}

	protected void run(IWorkspaceRunnable wr) throws DebugException {
		try {
			ResourcesPlugin.getWorkspace().run(wr, null, 0, null);
		} catch (CoreException e) {
			throw new DebugException(e.getStatus());
		}
	}
}
