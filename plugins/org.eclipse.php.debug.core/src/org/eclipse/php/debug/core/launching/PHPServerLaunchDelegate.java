package org.eclipse.php.debug.core.launching;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.php.debug.core.IPHPConstants;
import org.eclipse.php.debug.core.Logger;
import org.eclipse.php.debug.core.PHPDebugPlugin;
import org.eclipse.php.debug.core.model.PHPDebugTarget;
import org.eclipse.php.debug.core.preferences.PHPDebugCorePreferenceNames;
import org.eclipse.php.debug.core.preferences.PHPProjectPreferences;
import org.eclipse.php.server.apache.core.ApacheLaunchConfigurationDelegate;
import org.eclipse.php.server.apache.core.ApachePlugin;
import org.eclipse.php.server.apache.core.ApacheServerBehaviour;
import org.eclipse.php.server.apache.core.IHTTPServerLaunch;
import org.eclipse.wst.server.core.IModule;
import org.eclipse.wst.server.core.IModuleArtifact;
import org.eclipse.wst.server.core.IServer;
import org.eclipse.wst.server.core.ServerUtil;

import org.eclipse.php.debug.core.PHPDebugCoreMessages;


public class PHPServerLaunchDelegate implements IHTTPServerLaunch
{
	private ApacheLaunchConfigurationDelegate httpServerDelegate = null;
	
	public PHPServerLaunchDelegate()
	{
	}
	
	public void setHTTPServerDelegate(ApacheLaunchConfigurationDelegate delegate)
	{
		this.httpServerDelegate = delegate;
	}
	
	public void launch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor) throws CoreException
	{
		boolean runWithDebug = configuration.getAttribute("run_with_debug", true);
		
		if(mode.equals(ILaunchManager.RUN_MODE)&& !runWithDebug)
		{
			httpServerDelegate.doLaunch(configuration,mode,launch,monitor);
			return;
		}
		
		IServer server = ServerUtil.getServer(configuration);
		if (server == null) {
			Logger.log(Logger.ERROR, "Luanch configuration could not find server");
			// throw CoreException();
			return;
		}

		ApacheServerBehaviour apacheServerBehaviour = (ApacheServerBehaviour) server.loadAdapter(ApacheServerBehaviour.class, null);
		apacheServerBehaviour.setupLaunch(launch, mode, monitor);
		
		IModuleArtifact moduleArtifact = httpServerDelegate.getModuleArtifact(configuration);
		if(moduleArtifact == null)
			throw new CoreException(new Status(IStatus.ERROR, PHPDebugPlugin.getID(), IPHPConstants.INTERNAL_ERROR, PHPDebugCoreMessages.configurationError,null));
		
		IModule module = moduleArtifact.getModule();
		
		boolean publish = configuration.getAttribute(ApachePlugin.DEPLOYABLE, false);
		if(publish)
			apacheServerBehaviour.publish(module,monitor);
		
		ILaunchConfigurationWorkingCopy wc = configuration.getWorkingCopy();
		IProject proj = module.getProject();
		String project = proj.getFullPath().toString();
		
		wc.setAttribute(IPHPConstants.PHP_Project, project);
		wc.doSave();
		
		String URL = configuration.getAttribute(ApachePlugin.URL, "");
		String contextRoot = configuration.getAttribute(ApachePlugin.CONTEXT_ROOT, "");
		
		PHPDebugTarget target = null;
		if (mode.equals(ILaunchManager.DEBUG_MODE) || runWithDebug == true) {
            if (mode.equals(ILaunchManager.DEBUG_MODE))runWithDebug = false;
            boolean stopAtFirstLine = PHPProjectPreferences.getStopAtFirstLine(proj);
            int requestPort = PHPProjectPreferences.getDebugPort(proj);   
			PHPProcess process = new PHPProcess(launch, URL);
			apacheServerBehaviour.setProcess(process);
			target = new PHPDebugTarget(launch, URL, requestPort, process, contextRoot, runWithDebug, stopAtFirstLine, proj);
			launch.addDebugTarget(target);
		}
	}
}
