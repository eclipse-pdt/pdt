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
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.*;
import org.eclipse.debug.core.*;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.LaunchConfigurationDelegate;
import org.eclipse.debug.ui.CommonTab;
import org.eclipse.debug.ui.RefreshTab;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.debug.core.debugger.parameters.IDebugParametersInitializer;
import org.eclipse.php.debug.core.debugger.parameters.IDebugParametersKeys;
import org.eclipse.php.internal.debug.core.IPHPDebugConstants;
import org.eclipse.php.internal.debug.core.Logger;
import org.eclipse.php.internal.debug.core.PHPDebugCoreMessages;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.phpIni.PHPINIUtil;
import org.eclipse.php.internal.debug.core.preferences.PHPProjectPreferences;
import org.eclipse.php.internal.debug.core.preferences.PHPexeItem;
import org.eclipse.php.internal.debug.core.preferences.PHPexes;
import org.eclipse.php.internal.debug.core.zend.communication.DebuggerCommunicationDaemon;
import org.eclipse.php.internal.debug.core.zend.debugger.*;
import org.eclipse.swt.widgets.Display;

import com.ibm.icu.text.MessageFormat;

public class PHPExecutableLaunchDelegate extends LaunchConfigurationDelegate {

	public static final String SAVE_AUTOMATICALLY = "save_automatically"; //$NON-NLS-1$

	protected Map<String, String> envVariables = null;

	/**
	 * Override the extended getLaunch to create a PHPLaunch.
	 */
	public ILaunch getLaunch(ILaunchConfiguration configuration, String mode) throws CoreException {
		return new PHPLaunch(configuration, mode, null);
	}

	public void debugPHPExecutable(ILaunch launch, String phpExe, String fileToDebug) throws DebugException {
		try {
			ILaunchConfiguration configuration = launch.getLaunchConfiguration();
			boolean isProfileLaunch = launch.getLaunchMode().equals(ILaunchManager.PROFILE_MODE);
			if (isProfileLaunch) {
				final boolean enableCodeCoverage = configuration
						.getAttribute(IPHPDebugConstants.ATTR_ENABLE_CODE_COVERAGE, false);
				launch.setAttribute(IPHPDebugConstants.DEBUGGING_COLLECT_CODE_COVERAGE,
						enableCodeCoverage ? String.valueOf(1) : String.valueOf(0));
			}
		} catch (CoreException e) {
			DebugPlugin.log(e);
		}

		try {
			launch.setAttribute(IDebugParametersKeys.EXECUTABLE_LAUNCH, Boolean.toString(true));

			IDebugParametersInitializer parametersInitializer = DebugParametersInitializersRegistry
					.getBestMatchDebugParametersInitializer(launch);
			PHPExecutableDebuggerInitializer debuggerInitializer = new PHPExecutableDebuggerInitializer(launch);

			String phpExeString = new File(phpExe).getAbsolutePath();
			String fileName = new File(fileToDebug).getAbsolutePath();
			String query = PHPLaunchUtilities.generateQuery(launch, parametersInitializer);
			String iniFileLocation = launch.getAttribute(IDebugParametersKeys.PHP_INI_LOCATION);
			String workingDir = new File(fileToDebug).getParentFile().getAbsolutePath();

			debuggerInitializer.initializeDebug(phpExeString, fileName, workingDir, query, envVariables,
					iniFileLocation);

		} catch (java.io.IOException e1) {
			Logger.logException("PHPDebugTarget: Debugger didn't find file to debug.", e1); //$NON-NLS-1$
			String errorMessage = PHPDebugCoreMessages.DebuggerFileNotFound_1;
			throw new DebugException(new Status(IStatus.ERROR, PHPDebugPlugin.getID(),
					IPHPDebugConstants.INTERNAL_ERROR, errorMessage, e1));
		}
	}

	public void launch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor)
			throws CoreException {
		// Check for previous launches.
		if (!PHPLaunchUtilities.notifyPreviousLaunches(launch)) {
			monitor.setCanceled(true);
			monitor.done();
			return;
		}
		PHPLaunchUtilities.showDebugView();
		IProgressMonitor subMonitor; // the total of monitor is 100
		if (monitor.isCanceled()) {
			return;
		}
		PHPexeItem phpExeItem = PHPLaunchUtilities.getPHPExe(configuration);
		if (phpExeItem == null) {
			Logger.log(Logger.ERROR, "Launch configuration could not find PHP exe item"); //$NON-NLS-1$
			monitor.setCanceled(true);
			monitor.done();
			return;
		}
		String phpExeString = configuration.getAttribute(IPHPDebugConstants.ATTR_EXECUTABLE_LOCATION, (String) null);
		String phpIniPath = configuration.getAttribute(IPHPDebugConstants.ATTR_INI_LOCATION, (String) null);
		String fileName = configuration.getAttribute(IPHPDebugConstants.ATTR_FILE_FULL_PATH, (String) null);
		boolean runWithDebugInfo = configuration.getAttribute(IPHPDebugConstants.RUN_WITH_DEBUG_INFO, true);

		IProject project = null;
		String file = configuration.getAttribute(IPHPDebugConstants.ATTR_FILE, (String) null);
		if (file != null) {
			IResource resource = ResourcesPlugin.getWorkspace().getRoot().findMember(file);
			if (resource != null) {
				project = resource.getProject();
			} else {
				String projectName = configuration.getAttribute(IPHPDebugConstants.PHP_Project, (String) null);
				if (projectName != null) {
					IProject resolved = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
					if (resolved != null && resolved.isAccessible()) {
						project = resolved;
					}
				}
			}
		}

		if (monitor.isCanceled()) {
			return;
		}

		if (fileName == null || fileName.equals("")) { //$NON-NLS-1$
			displayErrorMessage(PHPDebugCoreMessages.PHPExecutableLaunchDelegate_0);
			return;
		}

		if (phpExeString == null) {
			displayErrorMessage(PHPDebugCoreMessages.PHPExecutableLaunchDelegate_4);
			return;
		}

		subMonitor = new SubProgressMonitor(monitor, 10); // 10 of 100

		// Locate the php.ini by using the attribute. If the attribute was null,
		// try to locate an php.ini that exists next to the executable.
		File phpIni = (phpIniPath != null && new File(phpIniPath).exists()) ? new File(phpIniPath)
				: PHPINIUtil.findPHPIni(phpExeString);
		File tempIni = PHPINIUtil.prepareBeforeLaunch(phpIni, phpExeString, project);
		launch.setAttribute(IDebugParametersKeys.PHP_INI_LOCATION, tempIni.getAbsolutePath());

		if (mode.equals(ILaunchManager.DEBUG_MODE) || runWithDebugInfo == true) {
			boolean stopAtFirstLine = configuration.getAttribute(IDebugParametersKeys.FIRST_LINE_BREAKPOINT,
					PHPProjectPreferences.getStopAtFirstLine(project));

			int requestPort = getDebugPort(phpExeItem);

			ILaunchConfigurationWorkingCopy wc;
			if (configuration.isWorkingCopy()) {
				wc = (ILaunchConfigurationWorkingCopy) configuration;
			} else {
				wc = configuration.getWorkingCopy();
			}

			// Set Project Name
			if (project != null) {
				wc.setAttribute(IPHPDebugConstants.PHP_Project, project.getFullPath().toString());
			}

			// Set transfer encoding:
			wc.setAttribute(IDebugParametersKeys.TRANSFER_ENCODING, PHPProjectPreferences.getTransferEncoding(project));
			wc.setAttribute(IDebugParametersKeys.OUTPUT_ENCODING, PHPProjectPreferences.getOutputEncoding(project));
			wc.setAttribute(IDebugParametersKeys.PHP_DEBUG_TYPE, IDebugParametersKeys.PHP_EXE_SCRIPT_DEBUG);
			wc.doSave();

			if (monitor.isCanceled()) {
				return;
			}

			// Generate a session id for this launch and put it in the map
			int sessionID = DebugSessionIdGenerator.generateSessionID();
			PHPSessionLaunchMapper.put(sessionID, launch);

			// Define all needed debug attributes:
			launch.setAttribute(IDebugParametersKeys.PORT, Integer.toString(requestPort));
			launch.setAttribute(IDebugParametersKeys.FIRST_LINE_BREAKPOINT, Boolean.toString(stopAtFirstLine));
			launch.setAttribute(IDebugParametersKeys.SESSION_ID, Integer.toString(sessionID));

			// Check that the debug daemon is functional
			// DEBUGGER - Make sure that the active debugger id is indeed Zend's
			// debugger
			if (!PHPLaunchUtilities.isDebugDaemonActive(requestPort, DebuggerCommunicationDaemon.ZEND_DEBUGGER_ID)) {
				PHPLaunchUtilities
						.showLaunchErrorMessage(NLS.bind(PHPDebugCoreMessages.ExeLaunchConfigurationDelegate_PortInUse,
								requestPort, phpExeItem.getName()));
				monitor.setCanceled(true);
				monitor.done();
				return;
			}

			// Trigger the debug session by initiating a debug requset to the
			// php.exe
			debugPHPExecutable(launch, phpExeString, fileName);

		} else {
			// resolve location
			IPath phpExe = new Path(phpExeString);

			String[] envp = DebugPlugin.getDefault().getLaunchManager().getEnvironment(configuration);
			File phpExeFile = new File(phpExeString);
			String phpIniLocation = launch.getAttribute(IDebugParametersKeys.PHP_INI_LOCATION);

			// Determine PHP configuration file location:
			String phpConfigDir = phpExeFile.getParent();
			if (phpIniLocation != null && !phpIniLocation.equals("")) { //$NON-NLS-1$
				phpConfigDir = new File(phpIniLocation).getParent();
			}

			// Detect PHP SAPI type:
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

			String[] args = PHPLaunchUtilities.getProgramArguments(launch.getLaunchConfiguration());
			String[] cmdLine = PHPLaunchUtilities.getCommandLine(launch.getLaunchConfiguration(), phpExeString,
					phpConfigDir, fileName, PHPexeItem.SAPI_CLI.equals(sapiType) ? args : null, phpV);

			// Set library search path:
			String libPath = PHPLaunchUtilities.getLibrarySearchPathEnv(phpExeFile.getParentFile());
			if (libPath != null) {
				String[] envpNew = new String[envp == null ? 1 : envp.length + 1];
				if (envp != null) {
					System.arraycopy(envp, 0, envpNew, 0, envp.length);
				}
				envpNew[envpNew.length - 1] = libPath;
				envp = envpNew;
			}

			if (monitor.isCanceled()) {
				return;
			}

			File workingDir = new File(fileName).getParentFile();
			Process p = workingDir.exists() ? DebugPlugin.exec(cmdLine, workingDir, envp)
					: DebugPlugin.exec(cmdLine, null, envp);

			// Attach a crash detector
			new Thread(new ProcessCrashDetector(launch, p)).start();

			IProcess process = null;

			// add process type to process attributes
			Map<String, String> processAttributes = new HashMap<String, String>();
			String programName = phpExe.lastSegment();
			String extension = phpExe.getFileExtension();

			if (extension != null) {
				programName = programName.substring(0, programName.length() - (extension.length() + 1));
			}

			programName = programName.toLowerCase();
			processAttributes.put(IProcess.ATTR_PROCESS_TYPE, programName);

			if (p != null) {
				subMonitor = new SubProgressMonitor(monitor, 80); // 10+80 of
																	// 100;
				subMonitor.beginTask(MessageFormat.format("start launch", new Object[] { configuration.getName() }), //$NON-NLS-1$
						IProgressMonitor.UNKNOWN);
				process = DebugPlugin.newProcess(launch, p, phpExe.toOSString(), processAttributes);
				if (process == null) {
					p.destroy();
					throw new CoreException(new Status(IStatus.ERROR, PHPDebugPlugin.getID(), 0, null, null));
				}
				subMonitor.done();
			}
			if (process != null) {
				process.setAttribute(IProcess.ATTR_CMDLINE, fileName);

				if (CommonTab.isLaunchInBackground(configuration)) {
					// refresh resources after process finishes
					/*
					 * if (RefreshTab.getRefreshScope(configuration) != null) {
					 * BackgroundResourceRefresher refresher = new
					 * BackgroundResourceRefresher(configuration, process);
					 * refresher.startBackgroundRefresh(); }
					 */
				} else {
					// wait for process to exit
					while (!process.isTerminated()) {
						try {
							if (monitor.isCanceled()) {
								process.terminate();
								break;
							}
							Thread.sleep(50);
						} catch (InterruptedException e) {
						}
					}

					// refresh resources
					subMonitor = new SubProgressMonitor(monitor, 10); // 10+80+10
																		// of
																		// 100;
					RefreshTab.refreshResources(configuration, subMonitor);
				}
			}
		}
	}

	/**
	 * @param phpExe
	 * @return debug port for given phpExe
	 * @throws CoreException
	 */
	protected int getDebugPort(PHPexeItem phpExe) throws CoreException {
		int customRequestPort = ZendDebuggerSettingsUtil.getDebugPort(phpExe.getUniqueId());
		if (customRequestPort != -1)
			return customRequestPort;
		return PHPDebugPlugin.getDebugPort(DebuggerCommunicationDaemon.ZEND_DEBUGGER_ID);
	}

	private void displayErrorMessage(final String message) {
		final Display display = Display.getDefault();
		display.asyncExec(new Runnable() {
			public void run() {
				MessageDialog.openError(display.getActiveShell(), PHPDebugCoreMessages.Debugger_LaunchError_title,
						message);
			}
		});
	}

	protected boolean saveBeforeLaunch(ILaunchConfiguration configuration, String mode, IProgressMonitor monitor)
			throws CoreException {
		String filePath = configuration.getAttribute(IPHPDebugConstants.ATTR_FILE, ""); //$NON-NLS-1$
		if ("".equals(filePath)) { //$NON-NLS-1$
			return super.saveBeforeLaunch(configuration, mode, monitor);
		}
		return super.saveBeforeLaunch(configuration, mode, monitor);
	}

}
