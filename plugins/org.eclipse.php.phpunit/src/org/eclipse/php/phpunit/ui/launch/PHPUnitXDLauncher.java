/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.phpunit.ui.launch;

import java.io.File;
import java.util.*;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.*;
import org.eclipse.debug.core.*;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.debug.core.debugger.parameters.IDebugParametersKeys;
import org.eclipse.php.internal.debug.core.IPHPDebugConstants;
import org.eclipse.php.internal.debug.core.Logger;
import org.eclipse.php.internal.debug.core.PHPDebugCoreMessages;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.launching.PHPLaunchUtilities;
import org.eclipse.php.internal.debug.core.pathmapper.PathMapperRegistry;
import org.eclipse.php.internal.debug.core.phpIni.PHPINIUtil;
import org.eclipse.php.internal.debug.core.preferences.PHPProjectPreferences;
import org.eclipse.php.internal.debug.core.preferences.PHPexeItem;
import org.eclipse.php.internal.debug.core.preferences.PHPexes;
import org.eclipse.php.internal.debug.core.xdebug.IDELayerFactory;
import org.eclipse.php.internal.debug.core.xdebug.XDebugPreferenceMgr;
import org.eclipse.php.internal.debug.core.xdebug.communication.XDebugCommunicationDaemon;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.DBGpBreakpointFacade;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.DBGpProxyHandler;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.DBGpProxyHandlersManager;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.XDebugDebuggerSettingsUtil;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.model.DBGpTarget;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.session.DBGpSessionHandler;
import org.eclipse.php.internal.debug.core.zend.debugger.ProcessCrashDetector;

public class PHPUnitXDLauncher extends PHPUnitBasicLauncher {

	public PHPUnitXDLauncher(ILaunchConfiguration configuration, ILaunch launch, PHPUnitOptionsList optionsList) {
		super(configuration, launch, optionsList);
	}

	@Override
	protected void launchRunMode(String fileName, File workingDir, String phpExeString, IProject project,
			Map<String, String> envVariables, IProgressMonitor monitor) throws CoreException {
		doLaunch(configuration, ILaunchManager.RUN_MODE, project, workingDir, launch, envVariables, monitor);
	}

	@Override
	protected void launchDebugMode(String fileName, File workingDir, String phpExeString, IProject project,
			Map<String, String> envVariables, IProgressMonitor monitor) throws CoreException {
		doLaunch(configuration, ILaunchManager.DEBUG_MODE, project, workingDir, launch, envVariables, monitor);
	}

	@Override
	protected void launchProfileMode(String fileName, File workingDir, String phpExeString, IProject project,
			Map<String, String> envVariables, IProgressMonitor monitor) throws CoreException {
		doLaunch(configuration, ILaunchManager.PROFILE_MODE, project, workingDir, launch, envVariables, monitor);
	}

	private void doLaunch(ILaunchConfiguration configuration, String mode, IProject project, File workingDirectory,
			ILaunch launch, Map<String, String> envVariables, IProgressMonitor monitor) throws CoreException {
		if (monitor.isCanceled()) {
			DebugPlugin.getDefault().getLaunchManager().removeLaunch(launch);
			return;
		}

		// get the launch info: php exe, php ini
		final String phpExeString = configuration.getAttribute(IPHPDebugConstants.ATTR_EXECUTABLE_LOCATION,
				(String) null);
		final String phpIniString = configuration.getAttribute(IPHPDebugConstants.ATTR_INI_LOCATION, (String) null);
		final String phpScriptString = configuration.getAttribute(IPHPDebugConstants.ATTR_FILE, (String) null);
		if (phpScriptString == null || phpScriptString.trim().length() == 0) {
			DebugPlugin.getDefault().getLaunchManager().removeLaunch(launch);
			displayErrorMessage(PHPDebugCoreMessages.XDebug_ExeLaunchConfigurationDelegate_0);
			return;
		}
		if (monitor.isCanceled()) {
			DebugPlugin.getDefault().getLaunchManager().removeLaunch(launch);
			return;
		}

		// locate the project from the php script
		final IPath filePath = new Path(phpScriptString);

		// resolve php exe location
		final IPath phpExe = new Path(phpExeString);

		// Set Project Name as this is required by the source lookup computer
		// delegate
		ILaunchConfigurationWorkingCopy wc = null;
		if (configuration.isWorkingCopy()) {
			wc = (ILaunchConfigurationWorkingCopy) configuration;
		} else {
			wc = configuration.getWorkingCopy();
		}
		wc.setAttribute(IPHPDebugConstants.PHP_Project, project.getFullPath().toString());
		wc.setAttribute(IDebugParametersKeys.TRANSFER_ENCODING, PHPProjectPreferences.getTransferEncoding(project));
		wc.setAttribute(IDebugParametersKeys.OUTPUT_ENCODING, PHPProjectPreferences.getOutputEncoding(project));
		wc.doSave();

		if (monitor.isCanceled()) {
			DebugPlugin.getDefault().getLaunchManager().removeLaunch(launch);
			return;
		}

		IPath projectLocation = project.getRawLocation();
		if (projectLocation == null) {
			projectLocation = project.getLocation();
		}

		// resolve the script location, but not relative to anything
		IPath phpFile = filePath;

		if (monitor.isCanceled()) {
			DebugPlugin.getDefault().getLaunchManager().removeLaunch(launch);
			return;
		}

		// Resolve the PHP ini location
		// Locate the php ini by using the attribute. If the attribute was null,
		// try to locate an ini that exists next to the executable.
		File phpIni = (phpIniString != null && new File(phpIniString).exists()) ? new File(phpIniString)
				: PHPINIUtil.findPHPIni(phpExeString);
		File tempIni = PHPINIUtil.prepareBeforeLaunch(phpIni, phpExeString, project);
		launch.setAttribute(IDebugParametersKeys.PHP_INI_LOCATION, tempIni.getAbsolutePath());

		// add process type to process attributes, basically the name of the exe
		// that was launched
		final Map<String, String> processAttributes = new HashMap<>();
		String programName = phpExe.lastSegment();
		final String extension = phpExe.getFileExtension();
		if (extension != null) {
			programName = programName.substring(0, programName.length() - (extension.length() + 1));
		}
		programName = programName.toLowerCase();

		// used by the console colorer extension to determine what class to use
		// should allow the console color providers and line trackers to work
		// process.setAttribute(IProcess.ATTR_PROCESS_TYPE,
		// IPHPConstants.PHPProcessType);

		processAttributes.put(IProcess.ATTR_PROCESS_TYPE, programName);
		// used by the Console to give that console a name
		processAttributes.put(IProcess.ATTR_CMDLINE, phpScriptString);

		if (monitor.isCanceled()) {
			DebugPlugin.getDefault().getLaunchManager().removeLaunch(launch);
			return;
		}

		// determine the environment variables
		String[] envVarString = null;
		DBGpTarget target = null;
		if (mode.equals(ILaunchManager.DEBUG_MODE)) {
			// check the launch for stop at first line, if not there go to
			// project specifics
			boolean stopAtFirstLine = PHPProjectPreferences.getStopAtFirstLine(project);
			stopAtFirstLine = configuration.getAttribute(IDebugParametersKeys.FIRST_LINE_BREAKPOINT, stopAtFirstLine);
			String sessionID = DBGpSessionHandler.getInstance().generateSessionId();
			String ideKey = String.valueOf((String) null);
			PHPexeItem phpExeItem = PHPLaunchUtilities.getPHPExe(configuration);
			int debugPort = getDebugPort(phpExeItem);
			// Check that the debug daemon is running
			if (!PHPLaunchUtilities.isDebugDaemonActive(debugPort, XDebugCommunicationDaemon.XDEBUG_DEBUGGER_ID)) {
				PHPLaunchUtilities
						.showLaunchErrorMessage(NLS.bind(PHPDebugCoreMessages.ExeLaunchConfigurationDelegate_PortInUse,
								debugPort, phpExeItem.getName()));
				monitor.setCanceled(true);
				monitor.done();
				return;
			}
			if (phpExeItem != null) {
				DBGpProxyHandler proxyHandler = DBGpProxyHandlersManager.INSTANCE.getHandler(phpExeItem.getUniqueId());
				if (proxyHandler.useProxy()) {
					ideKey = proxyHandler.getCurrentIdeKey();
					if (proxyHandler.registerWithProxy() == false) {
						displayErrorMessage(PHPDebugCoreMessages.XDebug_ExeLaunchConfigurationDelegate_2
								+ proxyHandler.getErrorMsg());
						DebugPlugin.getDefault().getLaunchManager().removeLaunch(launch);
						return;
					}
				}
			} else {
				ideKey = DBGpSessionHandler.getInstance().getIDEKey();
			}
			target = new DBGpTarget(launch, phpFile.lastSegment(), ideKey, sessionID, stopAtFirstLine);
			target.setPathMapper(PathMapperRegistry.getByLaunchConfiguration(configuration));
			DBGpSessionHandler.getInstance().addSessionListener(target);
			envVarString = createDebugLaunchEnvironment(configuration, sessionID, ideKey, phpExe);
		} else {
			envVarString = PHPLaunchUtilities.getEnvironment(configuration, new String[] { getLibraryPath(phpExe) });
		}

		IProgressMonitor subMonitor = SubMonitor.convert(monitor, 30);
		subMonitor.beginTask(PHPDebugCoreMessages.XDebug_ExeLaunchConfigurationDelegate_3, 10);

		// Detect PHP SAPI type and thus where we need arguments
		File phpExeFile = new File(phpExeString);
		String sapiType = null;
		PHPexeItem[] items = PHPexes.getInstance().getAllItems();
		for (PHPexeItem item : items) {
			if (item.getExecutable().equals(phpExeFile)) {
				sapiType = item.getSapiType();
				break;
			}
		}
		String[] args = null;
		if (PHPexeItem.SAPI_CLI.equals(sapiType)) {
			args = PHPLaunchUtilities.getProgramArguments(launch.getLaunchConfiguration());
		}

		// define the command line for launching
		String[] cmdLine = getCommandLine(project, phpExe.toOSString(), tempIni.toString(), phpFile.toOSString(), args);

		List<String> allArgs = new ArrayList<>();
		allArgs.addAll(Arrays.asList(cmdLine));
		allArgs.addAll(optionsList.getList());
		cmdLine = allArgs.toArray(new String[0]);

		// Launch the process
		final Process phpExeProcess = DebugPlugin.exec(cmdLine, workingDirectory, envVarString);
		// Attach a crash detector
		new Thread(new ProcessCrashDetector(launch, phpExeProcess)).start();

		IProcess eclipseProcessWrapper = null;
		if (phpExeProcess != null) {
			subMonitor.worked(10);
			eclipseProcessWrapper = DebugPlugin.newProcess(launch, phpExeProcess, phpExe.toOSString(),
					processAttributes);
			if (eclipseProcessWrapper == null) {

				// another error so we stop everything somehow
				phpExeProcess.destroy();
				subMonitor.done();
				DebugPlugin.getDefault().getLaunchManager().removeLaunch(launch);
				throw new CoreException(new Status(IStatus.ERROR, PHPDebugPlugin.ID, 0, null, null));
			}

			// if launching in debug mode, create the debug infrastructure and
			// link it with the launched process
			if (mode.equals(ILaunchManager.DEBUG_MODE) && target != null) {
				target.setProcess(eclipseProcessWrapper);
				launch.addDebugTarget(target);
				subMonitor.subTask(PHPDebugCoreMessages.XDebug_ExeLaunchConfigurationDelegate_4);
				target.waitForInitialSession((DBGpBreakpointFacade) IDELayerFactory.getIDELayer(),
						XDebugPreferenceMgr.createSessionPreferences(), monitor);
			}

		} else {
			// we did not launch
			if (mode.equals(ILaunchManager.DEBUG_MODE)) {
				DBGpSessionHandler.getInstance().removeSessionListener(target);
			}
			DebugPlugin.getDefault().getLaunchManager().removeLaunch(launch);
		}
		subMonitor.done();
	}

	/**
	 * create any environment variables that may be required
	 * 
	 * @param configuration
	 *            launch configuration
	 * @param sessionID
	 *            the DBGp Session Id
	 * @param ideKey
	 *            the DBGp ide key
	 * @return string array containing the environment
	 * @throws CoreException
	 *             rethrown exception
	 */
	public String[] createDebugLaunchEnvironment(ILaunchConfiguration configuration, String sessionID, String ideKey,
			IPath phpExe) throws CoreException {
		// create XDebug required environment variables, need the
		// session handler to start listening and generate a session id

		String configEnv = "XDEBUG_CONFIG=remote_enable=1 idekey=" + ideKey; //$NON-NLS-1$
		String extraDBGpEnv = "DBGP_IDEKEY=" + ideKey; //$NON-NLS-1$
		String sessEnv = "DBGP_COOKIE=" + sessionID; //$NON-NLS-1$

		Logger.debugMSG("env=" + configEnv + ", Cookie=" + sessEnv); //$NON-NLS-1$ //$NON-NLS-2$

		return PHPLaunchUtilities.getEnvironment(configuration,
				new String[] { configEnv, extraDBGpEnv, sessEnv, getLibraryPath(phpExe) });
	}

	/**
	 * @param phpExe
	 * @return debug port for given phpExe
	 * @throws CoreException
	 */
	protected int getDebugPort(PHPexeItem phpExe) throws CoreException {
		int customRequestPort = XDebugDebuggerSettingsUtil.getDebugPort(phpExe.getUniqueId());
		if (customRequestPort != -1) {
			return customRequestPort;
		}
		return PHPDebugPlugin.getDebugPort(XDebugCommunicationDaemon.XDEBUG_DEBUGGER_ID);
	}

	/**
	 * create the LD_LIBRARY_PATH information
	 * 
	 * @param exePath
	 *            the path to put into LD_LIBRARY_PATH
	 * @return environment string
	 */
	private String getLibraryPath(IPath exePath) {
		return PHPLaunchUtilities.getLibrarySearchPathEnv(new File(exePath.removeLastSegments(1).toOSString()));
	}

}
