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
package org.eclipse.php.internal.debug.core.launching;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.*;
import org.eclipse.debug.core.*;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.LaunchConfigurationDelegate;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.php.debug.core.debugger.parameters.IDebugParametersKeys;
import org.eclipse.php.internal.debug.core.IPHPDebugConstants;
import org.eclipse.php.internal.debug.core.Logger;
import org.eclipse.php.internal.debug.core.PHPDebugCoreMessages;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.pathmapper.PathMapperRegistry;
import org.eclipse.php.internal.debug.core.phpIni.PHPINIUtil;
import org.eclipse.php.internal.debug.core.preferences.PHPProjectPreferences;
import org.eclipse.php.internal.debug.core.preferences.PHPexeItem;
import org.eclipse.php.internal.debug.core.preferences.PHPexes;
import org.eclipse.php.internal.debug.core.xdebug.IDELayerFactory;
import org.eclipse.php.internal.debug.core.xdebug.XDebugPreferenceMgr;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.DBGpBreakpointFacade;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.DBGpProxyHandler;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.model.DBGpTarget;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.session.DBGpSessionHandler;
import org.eclipse.php.internal.debug.core.zend.debugger.ProcessCrashDetector;
import org.eclipse.swt.widgets.Display;

public class XDebugExeLaunchConfigurationDelegate extends
		LaunchConfigurationDelegate {

	public void launch(ILaunchConfiguration configuration, String mode,
			ILaunch launch, IProgressMonitor monitor) throws CoreException {

		if (monitor.isCanceled()) {
			DebugPlugin.getDefault().getLaunchManager().removeLaunch(launch);
			return;
		}

		// get the launch info: php exe, php ini
		final String phpExeString = configuration.getAttribute(
				IPHPDebugConstants.ATTR_EXECUTABLE_LOCATION, (String) null);
		final String phpIniString = configuration.getAttribute(
				IPHPDebugConstants.ATTR_INI_LOCATION, (String) null);
		final String phpScriptString = configuration.getAttribute(
				IPHPDebugConstants.ATTR_FILE, (String) null);
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
		final IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace()
				.getRoot();
		final IPath filePath = new Path(phpScriptString);
		final IResource scriptRes = workspaceRoot.findMember(filePath);
		if (scriptRes == null) {
			DebugPlugin.getDefault().getLaunchManager().removeLaunch(launch);
			displayErrorMessage(PHPDebugCoreMessages.XDebug_ExeLaunchConfigurationDelegate_1);
			return;
		}

		// resolve php exe location
		final IPath phpExe = new Path(phpExeString);

		// resolve project directory
		IProject project = scriptRes.getProject();

		// Set Project Name as this is required by the source lookup computer
		// delegate
		final String projectString = project.getFullPath().toString();
		ILaunchConfigurationWorkingCopy wc = null;
		if (configuration.isWorkingCopy()) {
			wc = (ILaunchConfigurationWorkingCopy) configuration;
		} else {
			wc = configuration.getWorkingCopy();
		}
		wc.setAttribute(IPHPDebugConstants.PHP_Project, projectString);
		wc.setAttribute(IDebugParametersKeys.TRANSFER_ENCODING,
				PHPProjectPreferences.getTransferEncoding(project));
		wc.setAttribute(IDebugParametersKeys.OUTPUT_ENCODING,
				PHPProjectPreferences.getOutputEncoding(project));
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
		IPath phpFile = scriptRes.getLocation();

		if (monitor.isCanceled()) {
			DebugPlugin.getDefault().getLaunchManager().removeLaunch(launch);
			return;
		}

		// Resolve the PHP ini location
		// Locate the php ini by using the attribute. If the attribute was null,
		// try to locate an ini that exists next to the executable.
		File phpIni = (phpIniString != null && new File(phpIniString).exists()) ? new File(
				phpIniString) : PHPINIUtil.findPHPIni(phpExeString);
		File tempIni = PHPINIUtil.prepareBeforeDebug(phpIni, phpExeString,
				project);
		launch.setAttribute(IDebugParametersKeys.PHP_INI_LOCATION,
				tempIni.getAbsolutePath());

		// add process type to process attributes, basically the name of the exe
		// that was launched
		final Map<String, String> processAttributes = new HashMap<String, String>();
		String programName = phpExe.lastSegment();
		final String extension = phpExe.getFileExtension();
		if (extension != null) {
			programName = programName.substring(0, programName.length()
					- (extension.length() + 1));
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
			boolean stopAtFirstLine = PHPProjectPreferences
					.getStopAtFirstLine(project);
			stopAtFirstLine = configuration
					.getAttribute(IDebugParametersKeys.FIRST_LINE_BREAKPOINT,
							stopAtFirstLine);
			String sessionID = DBGpSessionHandler.getInstance()
					.generateSessionId();
			String ideKey = null;
			if (DBGpProxyHandler.instance.useProxy()) {
				ideKey = DBGpProxyHandler.instance.getCurrentIdeKey();
				if (DBGpProxyHandler.instance.registerWithProxy() == false) {
					displayErrorMessage(PHPDebugCoreMessages.XDebug_ExeLaunchConfigurationDelegate_2
							+ DBGpProxyHandler.instance.getErrorMsg()); 
					DebugPlugin.getDefault().getLaunchManager()
							.removeLaunch(launch);
					return;
				}
			} else {
				ideKey = DBGpSessionHandler.getInstance().getIDEKey();
			}
			target = new DBGpTarget(launch, phpFile.lastSegment(), ideKey,
					sessionID, stopAtFirstLine);
			target.setPathMapper(PathMapperRegistry
					.getByLaunchConfiguration(configuration));
			DBGpSessionHandler.getInstance().addSessionListener(target);
			envVarString = createDebugLaunchEnvironment(configuration,
					sessionID, ideKey, phpExe);
		} else {
			envVarString = PHPLaunchUtilities.getEnvironment(configuration,
					new String[] { getLibraryPath(phpExe) });
		}

		IProgressMonitor subMonitor = new SubProgressMonitor(monitor, 30);
		subMonitor.beginTask(
				PHPDebugCoreMessages.XDebug_ExeLaunchConfigurationDelegate_3,
				10); 

		// determine the working directory. default is the location of the
		// script
		IPath workingPath = phpFile.removeLastSegments(1);
		File workingDir = workingPath.makeAbsolute().toFile();

		boolean found = false;
		for (int i = 0; i < envVarString.length && !found; i++) {
			String envEntity = envVarString[i];
			String[] elements = envEntity.split("="); //$NON-NLS-1$
			if (elements.length > 0 && elements[0].equals("XDEBUG_WORKING_DIR")) { //$NON-NLS-1$
				found = true;
				workingPath = new Path(elements[1]);
				File temp = workingPath.makeAbsolute().toFile();
				if (temp.exists()) {
					workingDir = temp;
				}
			}
		}

		// Detect PHP SAPI type and thus where we need arguments
		File phpExeFile = new File(phpExeString);
		String sapiType = null;
		String phpV = null;
		PHPexeItem[] items = PHPexes.getInstance().getAllItems();
		for (PHPexeItem item : items) {
			if (item.getExecutable().equals(phpExeFile)) {
				sapiType = item.getSapiType();
				phpV = item.getVersion();
				break;
			}
		}
		String[] args = null;
		if (sapiType == PHPexeItem.SAPI_CLI) {
			args = PHPLaunchUtilities.getProgramArguments(launch
					.getLaunchConfiguration());
		}

		// define the command line for launching
		String[] cmdLine = null;
		cmdLine = PHPLaunchUtilities.getCommandLine(configuration,
				phpExe.toOSString(), tempIni.toString(), phpFile.toOSString(),
				args, phpV);

		// Launch the process
		final Process phpExeProcess = DebugPlugin.exec(cmdLine, workingDir,
				envVarString);
		// Attach a crash detector
		new Thread(new ProcessCrashDetector(launch, phpExeProcess)).start();

		IProcess eclipseProcessWrapper = null;
		if (phpExeProcess != null) {
			subMonitor.worked(10);
			eclipseProcessWrapper = DebugPlugin.newProcess(launch,
					phpExeProcess, phpExe.toOSString(), processAttributes);
			if (eclipseProcessWrapper == null) {

				// another error so we stop everything somehow
				phpExeProcess.destroy();
				subMonitor.done();
				DebugPlugin.getDefault().getLaunchManager()
						.removeLaunch(launch);
				throw new CoreException(new Status(IStatus.ERROR,
						PHPDebugPlugin.ID, 0, null, null));
			}

			// if launching in debug mode, create the debug infrastructure and
			// link it with the launched process
			if (mode.equals(ILaunchManager.DEBUG_MODE) && target != null) {
				target.setProcess(eclipseProcessWrapper);
				launch.addDebugTarget(target);
				subMonitor
						.subTask(PHPDebugCoreMessages.XDebug_ExeLaunchConfigurationDelegate_4); 
				target.waitForInitialSession(
						(DBGpBreakpointFacade) IDELayerFactory.getIDELayer(),
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
	public String[] createDebugLaunchEnvironment(
			ILaunchConfiguration configuration, String sessionID,
			String ideKey, IPath phpExe) throws CoreException {
		// create XDebug required environment variables, need the
		// session handler to start listening and generate a session id

		String configEnv = "XDEBUG_CONFIG=remote_enable=1 idekey=" + ideKey; //$NON-NLS-1$
		String extraDBGpEnv = "DBGP_IDEKEY=" + ideKey; //$NON-NLS-1$
		String sessEnv = "DBGP_COOKIE=" + sessionID; //$NON-NLS-1$

		Logger.debugMSG("env=" + configEnv + ", Cookie=" + sessEnv); //$NON-NLS-1$ //$NON-NLS-2$

		String[] envVarString = PHPLaunchUtilities.getEnvironment(
				configuration, new String[] { configEnv, extraDBGpEnv, sessEnv,
						getLibraryPath(phpExe) });
		return envVarString;
	}

	/**
	 * create the LD_LIBRARY_PATH information
	 * 
	 * @param exePath
	 *            the path to put into LD_LIBRARY_PATH
	 * @return environment string
	 */
	private String getLibraryPath(IPath exePath) {
		// TODO: Should append if already present
		StringBuffer buf = new StringBuffer();
		buf.append("LD_LIBRARY_PATH"); //$NON-NLS-1$
		buf.append('=');
		exePath = exePath.removeLastSegments(1);
		buf.append(exePath.toOSString());
		return buf.toString();
	}

	/**
	 * Displays a dialog with an error message.
	 * 
	 * @param message
	 *            The error to display.
	 */
	protected void displayErrorMessage(final String message) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				MessageDialog.openError(Display.getDefault().getActiveShell(),
						PHPDebugCoreMessages.XDebugMessage_debugError, message);
			}
		});
	}
}
