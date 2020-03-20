/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.phpunit.ui.launch;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.*;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.debug.core.debugger.parameters.IDebugParametersInitializer;
import org.eclipse.php.debug.core.debugger.parameters.IDebugParametersKeys;
import org.eclipse.php.internal.debug.core.IPHPDebugConstants;
import org.eclipse.php.internal.debug.core.Logger;
import org.eclipse.php.internal.debug.core.PHPDebugCoreMessages;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.launching.DebugSessionIdGenerator;
import org.eclipse.php.internal.debug.core.launching.PHPLaunchUtilities;
import org.eclipse.php.internal.debug.core.preferences.PHPProjectPreferences;
import org.eclipse.php.internal.debug.core.preferences.PHPexeItem;
import org.eclipse.php.internal.debug.core.preferences.PHPexes;
import org.eclipse.php.internal.debug.core.zend.communication.DebuggerCommunicationDaemon;
import org.eclipse.php.internal.debug.core.zend.debugger.DebugParametersInitializersRegistry;
import org.eclipse.php.internal.debug.core.zend.debugger.PHPSessionLaunchMapper;
import org.eclipse.php.internal.debug.core.zend.debugger.ProcessCrashDetector;
import org.eclipse.php.internal.debug.core.zend.debugger.ZendDebuggerSettingsUtil;
import org.eclipse.php.internal.debug.daemon.DaemonPlugin;
import org.eclipse.php.phpunit.PHPUnitPlugin;
import org.eclipse.php.phpunit.launch.PHPUnitLaunchAttributes;
import org.eclipse.swt.widgets.Display;

import java.text.MessageFormat;

public class PHPUnitZDLauncher extends PHPUnitBasicLauncher {

	class CrashDetector extends ProcessCrashDetector {

		private ILaunch launch;

		public CrashDetector(ILaunch launch, Process p) {
			super(launch, p);
			this.launch = launch;
		}

		@Override
		public void run() {
			super.run();
			/*
			 * In case this thread ended and we do not have any IDebugTarget
			 * (PHPDebugTarget) hooked in the launch that was created, we can tell that
			 * there is something wrong, and probably there is no debugger installed (e.g.
			 * the debugger dll/so is not properly configured in the php.ini).
			 */
			if (launch != null && launch.getDebugTarget() == null) {
				String launchName = launch.getLaunchConfiguration().getName();
				boolean isRunMode = ILaunchManager.RUN_MODE.equals(launch.getLaunchMode());
				String msg = null;
				if (isRunMode) {
					msg = MessageFormat.format(PHPDebugCoreMessages.Debugger_Error_Message_3,
							new Object[] { launchName });
				} else {
					msg = MessageFormat.format(PHPDebugCoreMessages.Debugger_Error_Message_2,
							new Object[] { launchName });
				}
				final String message = msg;
				Display.getDefault().asyncExec(() -> {
					MessageDialog.openWarning(Display.getDefault().getActiveShell(),
							PHPDebugCoreMessages.Debugger_Launch_Error, message);
					DebugPlugin.getDefault().getLaunchManager().removeLaunch(launch);
				});
			}
		}
	}

	public PHPUnitZDLauncher(ILaunchConfiguration configuration, ILaunch launch, PHPUnitOptionsList optionsList) {
		super(configuration, launch, optionsList);
	}

	@Override
	protected void launchDebugMode(String fileName, File workingDir, String phpExeString, IProject project,
			Map<String, String> envVariables, IProgressMonitor monitor) throws CoreException {
		launchDebugOrProfileMode(fileName, workingDir, phpExeString, project, envVariables, monitor);
	}

	@Override
	protected void launchProfileMode(String fileName, File workingDir, String phpExeString, IProject project,
			Map<String, String> envVariables, IProgressMonitor monitor) throws CoreException {
		launchDebugOrProfileMode(fileName, workingDir, phpExeString, project, envVariables, monitor);
	}

	private void launchDebugOrProfileMode(String fileName, File workingDir, String phpExeString, IProject project,
			Map<String, String> envVariables, IProgressMonitor monitor) throws CoreException {
		PHPexeItem phpExeItem = PHPLaunchUtilities.getPHPExe(configuration);
		int requestPort = getDebugPort(phpExeItem);
		// Check that the debug daemon is running
		if (!PHPLaunchUtilities.isDebugDaemonActive(requestPort, DebuggerCommunicationDaemon.ZEND_DEBUGGER_ID)) {
			PHPLaunchUtilities.showLaunchErrorMessage(NLS.bind(
					PHPDebugCoreMessages.ExeLaunchConfigurationDelegate_PortInUse, requestPort, phpExeItem.getName()));
			monitor.setCanceled(true);
			monitor.done();
			return;
		}
		boolean stopAtFirstLine = configuration.getAttribute(IDebugParametersKeys.FIRST_LINE_BREAKPOINT,
				PHPProjectPreferences.getStopAtFirstLine(project));
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
		if (!wc.hasAttribute(PHPUnitLaunchAttributes.ATTRIBUTE_RERUN)) {
			wc.doSave();
		}

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
		// Trigger the session by initiating a debug request to the php.exe
		try {
			launch.setAttribute(IDebugParametersKeys.EXECUTABLE_LAUNCH, Boolean.toString(true));
			IDebugParametersInitializer parametersInitializer = DebugParametersInitializersRegistry
					.getBestMatchDebugParametersInitializer(launch);
			String query = PHPLaunchUtilities.generateQuery(launch, parametersInitializer);
			String iniFileLocation = launch.getAttribute(IDebugParametersKeys.PHP_INI_LOCATION);
			File phpExeFile = new File(phpExeString);
			// Determine configuration file directory:
			String phpConfigDir = phpExeFile.getParent();
			if (iniFileLocation != null && !iniFileLocation.isEmpty()) {
				phpConfigDir = new File(iniFileLocation).getParent();
			}
			// Detect PHP SAPI type:
			String sapiType = null;
			PHPexeItem[] items = PHPexes.getInstance().getAllItems();
			for (PHPexeItem item : items) {
				if (item.getExecutable().equals(phpExeFile)) {
					sapiType = item.getSapiType();
					break;
				}
			}
			String[] args = PHPLaunchUtilities.getProgramArguments(launch.getLaunchConfiguration());
			// Prepare the environment
			Map<String, String> additionalLaunchEnvironment = PHPLaunchUtilities.getPHPCGILaunchEnvironment(fileName,
					query, phpConfigDir, phpExeFile.getParent(), sapiType == PHPexeItem.SAPI_CGI ? args : null);
			if (envVariables == null) {
				envVariables = additionalLaunchEnvironment;
			} else {
				additionalLaunchEnvironment.putAll(envVariables);
				envVariables = additionalLaunchEnvironment;
			}
			String[] environmetVars = PHPLaunchUtilities.getEnvironment(launch.getLaunchConfiguration(),
					asAttributesArray(envVariables));
			// Prepare the command line.
			String[] phpCmdArray = getCommandLine(project, phpExeString, phpConfigDir, fileName,
					sapiType == PHPexeItem.SAPI_CLI ? args : null);
			List<String> allArgs = new ArrayList<>();
			allArgs.addAll(Arrays.asList(phpCmdArray));
			allArgs.addAll(optionsList.getList());
			phpCmdArray = allArgs.toArray(new String[0]);
			// Make sure that we have executable permissions on the file.
			PHPexes.changePermissions(new File(phpCmdArray[0]));
			if (PHPDebugPlugin.DEBUG) {
				System.out.println("Executing: " + Arrays.toString(phpCmdArray)); //$NON-NLS-1$
				System.out.println("Process environment: " //$NON-NLS-1$
						+ Arrays.toString(environmetVars));
			}
			DaemonPlugin.getDefault().makeSureDebuggerInitialized(null);
			// Execute the command line.
			Process p = Runtime.getRuntime().exec(phpCmdArray, environmetVars, workingDir);
			// Attach a crash detector
			new Thread(new CrashDetector(launch, p)).start();
		} catch (java.io.IOException e1) {
			Logger.logException("PHPDebugTarget: Debugger didn't find file to debug.", e1); //$NON-NLS-1$
			String errorMessage = PHPDebugCoreMessages.DebuggerFileNotFound_1;
			throw new DebugException(new Status(IStatus.ERROR, PHPDebugPlugin.getID(),
					IPHPDebugConstants.INTERNAL_ERROR, errorMessage, e1));
		} catch (CoreException e) {
			PHPUnitPlugin.log(e);
		}
	}

	/**
	 * @param phpExe
	 * @return debug port for given phpExe
	 * @throws CoreException
	 */
	protected int getDebugPort(PHPexeItem phpExe) throws CoreException {
		int customRequestPort = ZendDebuggerSettingsUtil.getDebugPort(phpExe.getUniqueId());
		if (customRequestPort != -1) {
			return customRequestPort;
		}
		return PHPDebugPlugin.getDebugPort(DebuggerCommunicationDaemon.ZEND_DEBUGGER_ID);
	}

	private String[] asAttributesArray(Map<String, String> attributesMap) {
		String[] attributes = new String[attributesMap.size()];
		int index = 0;
		for (Map.Entry<String, String> entry : attributesMap.entrySet()) {
			attributes[index++] = entry.getKey() + '=' + entry.getValue();
		}
		return attributes;
	}

}
