/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
/**
 * 
 */
package org.eclipse.php.internal.debug.core.launching;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.*;
import org.eclipse.debug.core.model.ILaunchConfigurationDelegate2;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.php.internal.debug.core.IPHPDebugConstants;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.debugger.IDebuggerConfiguration;
import org.eclipse.php.internal.debug.core.preferences.PHPDebugCorePreferenceNames;
import org.eclipse.php.internal.debug.core.preferences.PHPDebuggersRegistry;
import org.eclipse.php.internal.debug.core.preferences.PHPexeItem;
import org.eclipse.php.internal.server.core.Server;
import org.eclipse.php.internal.server.core.manager.ServersManager;

/**
 * The PHP launch delegate proxy is designed to supply flexibility in delegating
 * launches to different types of launch configuration delegates. Using the
 * proxy model can allow a runtime determination of the right delegate to
 * launch.
 * 
 * @author Shalom Gibly
 * 
 */
@SuppressWarnings("restriction")
public class PHPLaunchDelegateProxy implements ILaunchConfigurationDelegate2 {

	protected ILaunchConfigurationDelegate2 launchConfigurationDelegate;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.core.model.ILaunchConfigurationDelegate2#buildForLaunch
	 * (org.eclipse.debug.core.ILaunchConfiguration, java.lang.String,
	 * org.eclipse.core.runtime.IProgressMonitor)
	 */
	public boolean buildForLaunch(ILaunchConfiguration configuration, String mode, IProgressMonitor monitor)
			throws CoreException {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.ILaunchConfigurationDelegate2#
	 * finalLaunchCheck (org.eclipse.debug.core.ILaunchConfiguration,
	 * java.lang.String, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public boolean finalLaunchCheck(ILaunchConfiguration configuration, String mode, IProgressMonitor monitor)
			throws CoreException {
		return getConfigurationDelegate(configuration).finalLaunchCheck(configuration, mode, monitor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.core.model.ILaunchConfigurationDelegate2#getLaunch(
	 * org.eclipse.debug.core.ILaunchConfiguration, java.lang.String)
	 */
	public ILaunch getLaunch(ILaunchConfiguration configuration, String mode) throws CoreException {
		ILaunchManager lm = DebugPlugin.getDefault().getLaunchManager();
		ILaunchConfigurationType exeType = lm.getLaunchConfigurationType(IPHPDebugConstants.PHPEXELaunchType);
		if (configuration.getType().equals(exeType)) {
			configuration = updatePHPExeAttributes(configuration, mode);
		}
		ILaunchConfigurationType serverType = lm.getLaunchConfigurationType(IPHPDebugConstants.PHPServerLaunchType);
		if (configuration.getType().equals(serverType)) {
			configuration = updatePHPServerAttributes(configuration, mode);
		}
		return getConfigurationDelegate(configuration).getLaunch(configuration, mode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.core.model.ILaunchConfigurationDelegate2#preLaunchCheck
	 * (org.eclipse.debug.core.ILaunchConfiguration, java.lang.String,
	 * org.eclipse.core.runtime.IProgressMonitor)
	 */
	public boolean preLaunchCheck(ILaunchConfiguration configuration, String mode, IProgressMonitor monitor)
			throws CoreException {
		return getConfigurationDelegate(configuration).preLaunchCheck(configuration, mode, monitor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.core.model.ILaunchConfigurationDelegate#launch(org.
	 * eclipse.debug.core.ILaunchConfiguration, java.lang.String,
	 * org.eclipse.debug.core.ILaunch,
	 * org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void launch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor)
			throws CoreException {
		// Launch
		try {
			getConfigurationDelegate(configuration).launch(configuration, mode, launch, monitor);
		} finally {
			// Clear the launch configuration delegate.
			launchConfigurationDelegate = null;
		}
	}

	/**
	 * Create and return a launch configuration delegate. In case the delegate
	 * was already created, return the cached delegate. Note that in order to
	 * allow class instanciation from non-dependent plug-in, there is a need to
	 * define the plug-in as Eclipse-RegisterBuddy: org.eclipse.php.debug.core
	 * 
	 * @param configuration
	 *            An {@link ILaunchConfiguration}
	 */
	protected ILaunchConfigurationDelegate2 getConfigurationDelegate(ILaunchConfiguration configuration)
			throws CoreException {
		String className = configuration.getAttribute(PHPDebugCorePreferenceNames.CONFIGURATION_DELEGATE_CLASS, ""); //$NON-NLS-1$
		if (className.length() == 0) {
			throw new IllegalArgumentException();
		}
		if (launchConfigurationDelegate == null
				|| !launchConfigurationDelegate.getClass().getCanonicalName().equals(className)) {
			try {
				launchConfigurationDelegate = (ILaunchConfigurationDelegate2) Class.forName(className).newInstance();
			} catch (Throwable t) {
				throw new CoreException(new Status(IStatus.ERROR, PHPDebugPlugin.ID, 0,
						"Launch configuration delegate loading error.", t)); //$NON-NLS-1$
			}
		}
		return launchConfigurationDelegate;
	}

	private ILaunchConfiguration updatePHPExeAttributes(ILaunchConfiguration configuration, String mode)
			throws CoreException {
		PHPexeItem item = PHPLaunchUtilities.getPHPExe(configuration);
		if (item != null) {
			String debuggerId;
			// 'Run' mode should be always launched without debugger
			if (mode.equals(ILaunchManager.RUN_MODE)) {
				debuggerId = PHPDebuggersRegistry.NONE_DEBUGGER_ID;
			} else {
				debuggerId = item.getDebuggerID();
			}
			ILaunchConfigurationWorkingCopy wc = configuration.getWorkingCopy();
			wc.setAttribute(IPHPDebugConstants.ATTR_EXECUTABLE_LOCATION, item.getExecutable().toString());
			wc.setAttribute(PHPDebugCorePreferenceNames.PHP_DEBUGGER_ID, debuggerId);
			IDebuggerConfiguration debuggerConfiguration = PHPDebuggersRegistry.getDebuggerConfiguration(debuggerId);
			wc.setAttribute(PHPDebugCorePreferenceNames.CONFIGURATION_DELEGATE_CLASS,
					debuggerConfiguration.getScriptLaunchDelegateClass());
			if ((mode.equals(ILaunchManager.DEBUG_MODE) || mode.equals(ILaunchManager.PROFILE_MODE))
					&& debuggerConfiguration.getDebuggerId().equals(PHPDebuggersRegistry.NONE_DEBUGGER_ID)) {
				wc.setAttribute(IDebugUIConstants.ATTR_PRIVATE, true);
			}
			if (item.getINILocation() != null) {
				wc.setAttribute(IPHPDebugConstants.ATTR_INI_LOCATION, item.getINILocation().toString());
			} else {
				wc.setAttribute(IPHPDebugConstants.ATTR_INI_LOCATION, (String) null);
			}
			configuration = wc.doSave();
		}
		return configuration;
	}

	private ILaunchConfiguration updatePHPServerAttributes(ILaunchConfiguration configuration, String mode)
			throws CoreException {
		ILaunchConfigurationWorkingCopy wc = configuration.getWorkingCopy();
		Server server = ServersManager.getServer(configuration.getAttribute(Server.NAME, "")); //$NON-NLS-1$
		if (server != null) {
			String debuggerId;
			// 'Run' mode should be always launched without debugger
			if (mode.equals(ILaunchManager.RUN_MODE)) {
				debuggerId = PHPDebuggersRegistry.NONE_DEBUGGER_ID;
			} else {
				debuggerId = server.getDebuggerId();
			}
			wc.setAttribute(PHPDebugCorePreferenceNames.PHP_DEBUGGER_ID, debuggerId);
			IDebuggerConfiguration debuggerConfiguration = PHPDebuggersRegistry.getDebuggerConfiguration(debuggerId);
			wc.setAttribute(PHPDebugCorePreferenceNames.CONFIGURATION_DELEGATE_CLASS,
					debuggerConfiguration.getWebLaunchDelegateClass());
			if ((mode.equals(ILaunchManager.DEBUG_MODE) || mode.equals(ILaunchManager.PROFILE_MODE))
					&& debuggerConfiguration.getDebuggerId().equals(PHPDebuggersRegistry.NONE_DEBUGGER_ID)) {
				wc.setAttribute(IDebugUIConstants.ATTR_PRIVATE, true);
			}
			configuration = wc.doSave();
		}
		return configuration;
	}

}
