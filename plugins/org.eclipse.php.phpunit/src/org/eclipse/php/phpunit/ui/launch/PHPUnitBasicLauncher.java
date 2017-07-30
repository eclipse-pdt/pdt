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
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.ui.CommonTab;
import org.eclipse.debug.ui.RefreshTab;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.php.core.project.ProjectOptions;
import org.eclipse.php.debug.core.debugger.parameters.IDebugParametersKeys;
import org.eclipse.php.internal.debug.core.IPHPDebugConstants;
import org.eclipse.php.internal.debug.core.PHPDebugCoreMessages;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.launching.PHPLaunchUtilities;
import org.eclipse.php.internal.debug.core.preferences.PHPexeItem;
import org.eclipse.php.internal.debug.core.preferences.PHPexes;
import org.eclipse.php.internal.debug.core.zend.debugger.ProcessCrashDetector;
import org.eclipse.swt.widgets.Display;

public class PHPUnitBasicLauncher {

	protected ILaunchConfiguration configuration;
	protected ILaunch launch;
	protected PHPUnitOptionsList optionsList;

	public PHPUnitBasicLauncher(ILaunchConfiguration configuration, ILaunch launch, PHPUnitOptionsList optionsList) {
		this.configuration = configuration;
		this.launch = launch;
		this.optionsList = optionsList;
	}

	public void launch(String mode, IProject project, File workingDir, Map<String, String> envVariables,
			IProgressMonitor monitor) throws CoreException {
		// Check for previous launches.
		if (!PHPLaunchUtilities.notifyPreviousLaunches(launch)) {
			monitor.setCanceled(true);
			monitor.done();
			return;
		}
		if (monitor.isCanceled()) {
			return;
		}
		String phpExeString = configuration.getAttribute(IPHPDebugConstants.ATTR_EXECUTABLE_LOCATION, (String) null);
		String fileName = configuration.getAttribute(IPHPDebugConstants.ATTR_FILE_FULL_PATH, (String) null);
		if (monitor.isCanceled()) {
			return;
		}
		if (phpExeString == null) {
			displayErrorMessage(PHPDebugCoreMessages.PHPExecutableLaunchDelegate_4);
			return;
		}
		// Locate the php.ini by using the attribute. If the attribute was null,
		// try to locate an php.ini that exists next to the executable.
		// File phpIni = (phpIniPath != null && new File(phpIniPath).exists()) ?
		// new File(phpIniPath)
		// : PHPINIUtil.findPHPIni(phpExeString);
		// File tempIni = PHPINIUtil.prepareBeforeLaunch(phpIni, phpExeString,
		// project);
		// launch.setAttribute(IDebugParametersKeys.PHP_INI_LOCATION,
		// tempIni.getAbsolutePath());
		if (mode.equals(ILaunchManager.PROFILE_MODE)) {
			launchProfileMode(fileName, workingDir, phpExeString, project, envVariables, monitor);
		} else if (mode.equals(ILaunchManager.DEBUG_MODE)) {
			launchDebugMode(fileName, workingDir, phpExeString, project, envVariables, monitor);
		} else if (mode.equals(ILaunchManager.RUN_MODE)) {
			launchRunMode(fileName, workingDir, phpExeString, project, envVariables, monitor);
		}
	}

	protected void launchRunMode(String fileName, File workingDir, String phpExeString, IProject project,
			Map<String, String> envVariables, IProgressMonitor monitor) throws CoreException {
		// Resolve location
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
		// String phpV = null;
		PHPexeItem[] items = PHPexes.getInstance().getAllItems();
		for (PHPexeItem item : items) {
			if (item.getExecutable().equals(phpExeFile)) {
				sapiType = item.getSapiType();
				// phpV = item.getVersion();
				break;
			}
		}
		String[] args = PHPLaunchUtilities.getProgramArguments(launch.getLaunchConfiguration());
		String[] cmdLine = getCommandLine(project, phpExeString, phpConfigDir, fileName,
				PHPexeItem.SAPI_CLI.equals(sapiType) ? args : null);
		// Set library search path
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
		List<String> allArgs = new ArrayList<>();
		allArgs.addAll(Arrays.asList(cmdLine));
		allArgs.addAll(optionsList.getList());
		cmdLine = allArgs.toArray(new String[0]);
		if (PHPDebugPlugin.DEBUG) {
			System.out.println("Executing: " + Arrays.toString(cmdLine)); //$NON-NLS-1$
			System.out.println("Process environment: " //$NON-NLS-1$
					+ Arrays.toString(envp));
		}
		Process p = workingDir.exists() ? DebugPlugin.exec(cmdLine, workingDir, envp)
				: DebugPlugin.exec(cmdLine, null, envp);
		// Attach a crash detector
		new Thread(new ProcessCrashDetector(launch, p)).start();
		IProcess process = null;
		// Add process type to process attributes
		Map<String, String> processAttributes = new HashMap<>();
		String programName = phpExe.lastSegment();
		String extension = phpExe.getFileExtension();
		if (extension != null) {
			programName = programName.substring(0, programName.length() - (extension.length() + 1));
		}
		programName = programName.toLowerCase();
		processAttributes.put(IProcess.ATTR_PROCESS_TYPE, programName);
		IProgressMonitor subMonitor = new SubProgressMonitor(monitor, 10);
		if (p != null) {
			subMonitor = new SubProgressMonitor(monitor, 80);
			process = DebugPlugin.newProcess(launch, p, phpExe.toOSString(), processAttributes);
			if (process == null) {
				p.destroy();
				throw new CoreException(new Status(IStatus.ERROR, PHPDebugPlugin.getID(), 0, null, null));
			}
			subMonitor.done();
			process.setAttribute(IProcess.ATTR_CMDLINE, fileName);
		}
		if (CommonTab.isLaunchInBackground(configuration)) {
			// Refresh resources after process finishes
			/*
			 * if (RefreshTab.getRefreshScope(configuration) != null) {
			 * BackgroundResourceRefresher refresher = new
			 * BackgroundResourceRefresher(configuration, process);
			 * refresher.startBackgroundRefresh(); }
			 */
		} else {
			// Wait for process to exit
			while (process != null && !process.isTerminated()) {
				try {
					if (monitor.isCanceled()) {
						process.terminate();
						break;
					}
					Thread.sleep(50);
				} catch (InterruptedException e) {
				}
			}
			// Refresh resources
			subMonitor = new SubProgressMonitor(monitor, 10);
			RefreshTab.refreshResources(configuration, subMonitor);
		}
	}

	protected void launchDebugMode(String fileName, File workingDir, String phpExeString, IProject project,
			Map<String, String> envVariables, IProgressMonitor monitor) throws CoreException {
		launchRunMode(fileName, workingDir, phpExeString, project, envVariables, monitor);
	}

	protected void launchProfileMode(String fileName, File workingDir, String phpExeString, IProject project,
			Map<String, String> envVariables, IProgressMonitor monitor) throws CoreException {
		launchRunMode(fileName, workingDir, phpExeString, project, envVariables, monitor);
	}

	public String[] getCommandLine(IProject project, String phpExe, String phpConfigDir, String scriptPath,
			String[] args) throws CoreException {
		// Check if we should treat ASP tags as PHP tags
		String aspTags = ProjectOptions.isSupportingASPTags(project) ? "on" //$NON-NLS-1$
				: "off"; //$NON-NLS-1$
		String shortOpenTag = ProjectOptions.useShortTags(project) ? "on" //$NON-NLS-1$
				: "off"; //$NON-NLS-1$

		List<String> cmdLineList = new LinkedList<>();
		cmdLineList.addAll(Arrays.asList(new String[] { phpExe, "-c", //$NON-NLS-1$
				phpConfigDir, "-d", "asp_tags=" + aspTags, "-d", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				"short_open_tag=" + shortOpenTag, scriptPath })); //$NON-NLS-1$
		if (args != null) {
			cmdLineList.addAll(Arrays.asList(args));
		}
		return cmdLineList.toArray(new String[cmdLineList.size()]);
	}

	protected void displayErrorMessage(final String message) {
		final Display display = Display.getDefault();
		display.asyncExec(() -> MessageDialog.openError(display.getActiveShell(),
				PHPDebugCoreMessages.Debugger_LaunchError_title, message));
	}

}
