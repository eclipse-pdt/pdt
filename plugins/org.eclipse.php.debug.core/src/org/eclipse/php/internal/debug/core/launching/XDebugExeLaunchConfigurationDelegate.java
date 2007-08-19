/*******************************************************************************
 * Copyright (c) 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial implementation
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
import org.eclipse.php.internal.core.phpIni.IniModifier;
import org.eclipse.php.internal.core.project.PHPNature;
import org.eclipse.php.internal.debug.core.IPHPConstants;
import org.eclipse.php.internal.debug.core.Logger;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.xdebug.GeneralUtils;
import org.eclipse.php.internal.debug.core.xdebug.IDELayer;
import org.eclipse.php.internal.debug.core.xdebug.IDELayerFactory;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.DBGpBreakpointFacade;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.model.DBGpTarget;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.session.DBGpSessionHandler;
import org.eclipse.swt.widgets.Display;

public class XDebugExeLaunchConfigurationDelegate extends LaunchConfigurationDelegate {

	public void launch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor) throws CoreException {

		if (!mode.equals(ILaunchManager.DEBUG_MODE)) {
			DebugPlugin.getDefault().getLaunchManager().removeLaunch(launch);
			return;
		}

		if (monitor.isCanceled()) {
			DebugPlugin.getDefault().getLaunchManager().removeLaunch(launch);
			return;
		}

		IDELayer idelayer = IDELayerFactory.getIDELayer();

		// get the exe, project name, file name
		final String phpExeString = configuration.getAttribute(idelayer.getEXELocationAttrName(), (String) null);
		final String phpScriptString = configuration.getAttribute(idelayer.getEXEFileAttrName(), (String) null);
		if (phpScriptString == null || phpScriptString.trim().length() == 0) {
			DebugPlugin.getDefault().getLaunchManager().removeLaunch(launch);
			displayErrorMessage("No script specified");
			return;
		}

		if (monitor.isCanceled()) {
			DebugPlugin.getDefault().getLaunchManager().removeLaunch(launch);
			return;
		}

		// locate the project and absolute path of the file
		final IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		final IPath filePath = new Path(phpScriptString);
		final IResource res = workspaceRoot.findMember(filePath);
		if (res == null) {
			DebugPlugin.getDefault().getLaunchManager().removeLaunch(launch);
			displayErrorMessage("Specified script cannot be found");
			return;
		}
		IProject project = res.getProject();

		// check the launch for stop at first line, if not there go to project specifics
		boolean stopAtFirstLine = false;
		if (configuration.getAttribute(idelayer.getEXEOverrideFirstLineAttrName(), false)) {
			stopAtFirstLine = configuration.getAttribute(idelayer.getEXEFirstLineAttrName(), false);
		} else {
			stopAtFirstLine = idelayer.getStopAtFirstLine(project);
		}

		// cannot support project level properties with regard to port.
		// final int requestPort = PHPProjectPreferences.getDebugPort(project);

		// Set Project Name as this is required by the source lookup computer delegate
		final String projectString = project.getFullPath().toString();
		ILaunchConfigurationWorkingCopy wc;
		if (configuration.isWorkingCopy()) {
			wc = (ILaunchConfigurationWorkingCopy) configuration;
		} else {
			wc = configuration.getWorkingCopy();
		}
		wc.setAttribute(IPHPConstants.PHP_Project, projectString);

		if (monitor.isCanceled()) {
			DebugPlugin.getDefault().getLaunchManager().removeLaunch(launch);
			return;
		}

		// resolve php exe location
		final IPath phpExe = new Path(phpExeString);

		// resolve working directory
		IPath projectLocation = project.getRawLocation();
		if (projectLocation == null)
			projectLocation = project.getLocation();
		final String location = projectLocation.toOSString();
		final IPath p1 = new Path(location);
		final File projectDir = p1.toFile();

		// resolve the php script
		IPath phpFile = new Path(phpScriptString);
		if (phpScriptString.startsWith("/"))
			phpFile = phpFile.removeFirstSegments(1);

		if (monitor.isCanceled()) {
			DebugPlugin.getDefault().getLaunchManager().removeLaunch(launch);
			return;
		}

		// Resolve the PHP ini location
		File phpIni = IniModifier.findPHPIni(phpExeString);
		if (/*project != dummyProject &&*/project.hasNature(PHPNature.ID)) {
			if (phpIni != null) {
				File tempIni = IniModifier.addIncludePath(phpIni, project);
				if (tempIni != null) {
					wc.setAttribute(IDebugParametersKeys.PHP_INI_LOCATION, tempIni.getAbsolutePath());
				}
			}
		} else {
			if (phpIni != null) {
				wc.setAttribute(IDebugParametersKeys.PHP_INI_LOCATION, phpIni.getAbsolutePath());
			}
		}
		wc.doSave();

		String sessionID = DBGpSessionHandler.getInstance().generateSessionId();
		String ideKey = DBGpSessionHandler.getInstance().getIDEKey();

		// create any environment variables, and build the command line  
		String[] envVarString = createLaunchEnvironment(configuration, sessionID, ideKey);
		String[] cmdLine = createCommandLine(configuration, projectDir.toString(), phpExe.toOSString(), phpFile.toOSString());

		// add process type to process attributes, basically the name of the exe that was launched
		final Map<String, String> processAttributes = new HashMap<String, String>();
		String programName = phpExe.lastSegment();
		final String extension = phpExe.getFileExtension();
		if (extension != null) {
			programName = programName.substring(0, programName.length() - (extension.length() + 1));
		}
		programName = programName.toLowerCase();

		// used by the console colorer extension to determine what class to use
		// should allow the console color providers and line trackers to work
		//process.setAttribute(IProcess.ATTR_PROCESS_TYPE, IPHPConstants.PHPProcessType);

		processAttributes.put(IProcess.ATTR_PROCESS_TYPE, programName);
		// used by the Console to give that console a name
		processAttributes.put(IProcess.ATTR_CMDLINE, phpScriptString);

		if (monitor.isCanceled()) {
			DebugPlugin.getDefault().getLaunchManager().removeLaunch(launch);
			return;
		}

		DBGpTarget target = new DBGpTarget(launch, null, phpFile.toOSString(), ideKey, sessionID, stopAtFirstLine);
		DBGpSessionHandler.getInstance().addSessionListener(target);

		// launch PHP with a working directory of the project directory + environment vars
		IProgressMonitor subMonitor = new SubProgressMonitor(monitor, 30);
		subMonitor.beginTask("Launching script", 10);

		final Process phpExeProcess = DebugPlugin.exec(cmdLine, projectDir, envVarString);
		IProcess eclipseProcessWrapper = null;
		if (phpExeProcess != null) {
			subMonitor.worked(10);
			eclipseProcessWrapper = DebugPlugin.newProcess(launch, phpExeProcess, phpExe.toOSString(), processAttributes);
			if (eclipseProcessWrapper == null) {

				// another error so we stop everything somehow
				phpExeProcess.destroy();
				subMonitor.done();
				DebugPlugin.getDefault().getLaunchManager().removeLaunch(launch);
				throw new CoreException(new Status(IStatus.ERROR, PHPDebugPlugin.ID, 0, null, null));
			}
			target.setProcess(eclipseProcessWrapper);
			launch.addDebugTarget(target);
			subMonitor.subTask("waiting for XDebug session");
			target.waitForInitialSession((DBGpBreakpointFacade) IDELayerFactory.getIDELayer(), GeneralUtils.createSessionPreferences(), monitor);

		} else {
			// we did not launch
			DBGpSessionHandler.getInstance().removeSessionListener(target);
			DebugPlugin.getDefault().getLaunchManager().removeLaunch(launch);
		}
		subMonitor.done();
	}

	/**
	 * create any environment variables that may be required
	 * @param configuration launch configuration
	 * @param sessionID the DBGp Session Id
	 * @param ideKey the DBGp ide key
	 * @return string array containing the environment
	 * @throws CoreException rethrown exception
	 */
	public String[] createLaunchEnvironment(ILaunchConfiguration configuration, String sessionID, String ideKey) throws CoreException {
		// create XDebug required environment variables, need the
		// session handler to start listening and generate a session id      

		String configEnv = "XDEBUG_CONFIG=remote_enable=1 idekey=" + ideKey;
		String extraDBGpEnv = "DBGP_IDEKEY=" + ideKey;
		String sessEnv = "DBGP_COOKIE=" + sessionID;

		Logger.debugMSG("env=" + configEnv + ", Cookie=" + sessEnv);

		String[] envVarString = PHPLaunchUtilities.getEnvironment(configuration, new String[] { configEnv, extraDBGpEnv, sessEnv });
		return envVarString;
	}

	/**
	 * create an appropriate command line invocation
	 * @param configuration the launch configuration
	 * @param exeName the name of the exe
	 * @param scriptName the name of the script
	 * @param sessionID the DBGp Session id if needed
	 * @param ideKey the DBGp idekey if needed.
	 * @return the command line to be invoked.
	 * @throws CoreException rethrown exception
	 */
	public String[] createCommandLine(ILaunchConfiguration configuration, String phpConfigDir, String exeName, String scriptName) throws CoreException {
		String phpIniLocation = configuration.getAttribute(IDebugParametersKeys.PHP_INI_LOCATION, "");
		return PHPLaunchUtilities.getCommandLine(configuration, exeName, phpConfigDir, scriptName, phpIniLocation);
	}

	/**
	 * Displays a dialog with an error message.
	 * 
	 * @param message The error to display.
	 */
	protected void displayErrorMessage(final String message) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				MessageDialog.openError(Display.getDefault().getActiveShell(), "Debug Error", message);
			}
		});
	}
}
