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
package org.eclipse.php.debug.core.launching;

import java.io.File;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.LaunchConfigurationDelegate;
import org.eclipse.debug.ui.CommonTab;
import org.eclipse.debug.ui.RefreshTab;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.core.PHPCoreConstants;
import org.eclipse.php.core.phpIni.IniModifier;
import org.eclipse.php.debug.core.IPHPConstants;
import org.eclipse.php.debug.core.Logger;
import org.eclipse.php.debug.core.PHPDebugCoreMessages;
import org.eclipse.php.debug.core.PHPDebugPlugin;
import org.eclipse.php.debug.core.debugger.PHPExecutableDebuggerInitializer;
import org.eclipse.php.debug.core.debugger.PHPSessionLaunchMapper;
import org.eclipse.php.debug.core.debugger.parameters.IDebugParametersKeys;
import org.eclipse.php.debug.core.model.DebugSessionIdGenerator;
import org.eclipse.php.debug.core.preferences.PHPDebugCorePreferenceNames;
import org.eclipse.php.debug.core.preferences.PHPProjectPreferences;
import org.eclipse.php.ui.dialogs.saveFiles.SaveFilesHandler;
import org.eclipse.php.ui.dialogs.saveFiles.SaveFilesHandler.SaveFilesResult;
import org.eclipse.swt.widgets.Display;

public class PHPExecutableLaunchDelegate extends LaunchConfigurationDelegate {
	protected Map envVariables = null;

	public void debugPHPExecutable(final ILaunch launch, final String phpExe, final String fileToDebug) throws DebugException {
		try {
			final PHPExecutableDebuggerInitializer debuggerInitializer = new PHPExecutableDebuggerInitializer(launch);
			debuggerInitializer.initializeDebug(new File(phpExe).getAbsolutePath(), new File(fileToDebug).getAbsolutePath(), envVariables);
		} catch (final java.io.IOException e1) {
			Logger.logException("PHPDebugTarget: Debugger didn't find file to debug.", e1);
			final String errorMessage = PHPDebugCoreMessages.DebuggerFileNotFound_1;
			throw new DebugException(new Status(IStatus.ERROR, PHPDebugPlugin.getID(), IPHPConstants.INTERNAL_ERROR, errorMessage, e1));
		}
	}

	public void launch(final ILaunchConfiguration configuration, final String mode, final ILaunch launch, final IProgressMonitor monitor) throws CoreException {
		PHPLaunchUtilities.showDebugView();
		IProgressMonitor subMonitor; // the total of monitor is 100
		if (monitor.isCanceled())
			return;

		final String phpExeString = configuration.getAttribute(PHPCoreConstants.ATTR_LOCATION, (String) null);
		final String projectName = configuration.getAttribute(PHPCoreConstants.ATTR_WORKING_DIRECTORY, (String) null);
		final String fileNameString = configuration.getAttribute(PHPCoreConstants.ATTR_FILE, (String) null);
		final boolean runWithDebugInfo = configuration.getAttribute(IPHPConstants.RUN_WITH_DEBUG_INFO, true);

		if (monitor.isCanceled())
			return;

		if (fileNameString == null || fileNameString.equals(""))
			return;

		final IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		final IPath filePath = new Path(fileNameString);
		IProject project = null;
		String absolutePath = null;
		if (projectName == null) {
			final IResource res = workspaceRoot.findMember(filePath);
			if (res == null || !res.isAccessible()) {
				final Display display = Display.getDefault();
				display.asyncExec(new Runnable() {
					public void run() {
						MessageDialog.openError(display.getActiveShell(), PHPDebugCoreMessages.Debugger_LaunchError_title, NLS.bind(PHPDebugCoreMessages.Debugger_ResourceNotFound, filePath));
					}
				});
				return;
			}
			project = res.getProject();
			absolutePath = res.getLocation().toString();
		} else {
			try {
				final IPath projectPath = new Path(projectName);
				project = workspaceRoot.getProject(projectPath.lastSegment());
				absolutePath = filePath.makeAbsolute().toString();
			} catch (final Exception e) {
			}
		}

		if (project == null || !project.isAccessible()) {
			final Display display = Display.getDefault();
			display.asyncExec(new Runnable() {
				public void run() {
					MessageDialog.openError(display.getActiveShell(), PHPDebugCoreMessages.Debugger_LaunchError_title, NLS.bind(PHPDebugCoreMessages.Debugger_ResourceNotFound, filePath));
				}
			});
		}

		subMonitor = new SubProgressMonitor(monitor, 10); // 10 of 100
		//		if (!saveFiles(project, monitor)) {
		//			return;
		//		}

		File phpIni = IniModifier.findPHPIni(phpExeString);
		if (phpIni != null) {
			File tempIni = IniModifier.addIncludePath(phpIni, project);
			if (tempIni != null) {
				launch.setAttribute(IDebugParametersKeys.PHP_INI_LOCATION, tempIni.getAbsolutePath());
			}
		}

		if (mode.equals(ILaunchManager.DEBUG_MODE) || runWithDebugInfo == true) {
			boolean stopAtFirstLine = false;
			if (configuration.getAttribute(IDebugParametersKeys.OVERRIDE_FIRST_LINE_BREAKPOINT, false)) {
				stopAtFirstLine = configuration.getAttribute(IDebugParametersKeys.FIRST_LINE_BREAKPOINT, false);
			} else {
				stopAtFirstLine = PHPProjectPreferences.getStopAtFirstLine(project);
			}
			final int requestPort = PHPProjectPreferences.getDebugPort(project);

			// Set Project Name
			final String projectString = project.getFullPath().toString();

			ILaunchConfigurationWorkingCopy wc;
			if (configuration.isWorkingCopy())
				wc = (ILaunchConfigurationWorkingCopy) configuration;
			else
				wc = configuration.getWorkingCopy();
			wc.setAttribute(IPHPConstants.PHP_Project, projectString);

			// Set transfer encoding:
			wc.setAttribute(IDebugParametersKeys.TRANSFER_ENCODING, PHPProjectPreferences.getTransferEncoding(project));
			wc.doSave();

			if (monitor.isCanceled())
				return;

			// Generate a session id for this launch and put it in the map
			final int sessionID = DebugSessionIdGenerator.generateSessionID();
			PHPSessionLaunchMapper.put(sessionID, launch);

			// Define all needed debug attributes:
			launch.setAttribute(IDebugParametersKeys.PORT, Integer.toString(requestPort));
			launch.setAttribute(IDebugParametersKeys.FIRST_LINE_BREAKPOINT, Boolean.toString(stopAtFirstLine));
			launch.setAttribute(IDebugParametersKeys.SESSION_ID, Integer.toString(sessionID));

			// Trigger the debug session by initiating a debug requset to the php.exe
			debugPHPExecutable(launch, phpExeString, absolutePath);
		} else {
			// resolve location
			final IPath phpExe = new Path(phpExeString);

			if (monitor.isCanceled())
				return;

			// resolve working directory
			//            String projectFolderString = configuration.getAttribute(PHPCoreConstants.ATTR_WORKING_DIRECTORY, (String)null);

			IPath projectLocation = project.getRawLocation();
			if (projectLocation == null)
				projectLocation = project.getLocation();
			final String location = projectLocation.toOSString();
			final IPath p1 = new Path(location);
			final File projectDir = p1.toFile();

			if (monitor.isCanceled())
				return;

			//            String fileNameString = configuration.getAttribute(PHPCoreConstants.ATTR_FILE, (String)null);
			IPath phpFile = new Path(fileNameString);
			if (fileNameString.startsWith("/"))
				phpFile = phpFile.removeFirstSegments(1);

			if (monitor.isCanceled())
				return;

			final String[] envp = DebugPlugin.getDefault().getLaunchManager().getEnvironment(configuration);

			if (monitor.isCanceled())
				return;

			File workingDir = new File(phpExe.removeLastSegments(1).toString());
			String phpConfigDir = workingDir.getAbsolutePath();

			String phpIniLocation = launch.getAttribute(IDebugParametersKeys.PHP_INI_LOCATION);
			if (phpIniLocation != null && !phpIniLocation.equals("")) {
				phpConfigDir = phpIniLocation;
			}

			final String[] cmdLine = new String[] { phpExe.toOSString(), "-c", phpConfigDir, phpFile.toOSString() };

			final Process p = DebugPlugin.exec(cmdLine, workingDir, envp);
			IProcess process = null;

			// add process type to process attributes
			final Map processAttributes = new HashMap();
			String programName = phpExe.lastSegment();
			final String extension = phpExe.getFileExtension();
			if (extension != null)
				programName = programName.substring(0, programName.length() - (extension.length() + 1));
			programName = programName.toLowerCase();
			processAttributes.put(IProcess.ATTR_PROCESS_TYPE, programName);

			if (p != null) {
				subMonitor = new SubProgressMonitor(monitor, 80); // 10+80 of 100;
				subMonitor.beginTask(MessageFormat.format("start launch", new String[] { configuration.getName() }), IProgressMonitor.UNKNOWN); //$NON-NLS-1$
				process = DebugPlugin.newProcess(launch, p, phpExe.toOSString(), processAttributes);
				if (process == null) {
					p.destroy();
					throw new CoreException(new Status(IStatus.ERROR, PHPDebugPlugin.getID(), 0, null, null));
				}
				subMonitor.done();

			}
			process.setAttribute(IProcess.ATTR_CMDLINE, fileNameString);

			if (CommonTab.isLaunchInBackground(configuration)) {
				// refresh resources after process finishes
				/*
				 if (RefreshTab.getRefreshScope(configuration) != null) {
				 BackgroundResourceRefresher refresher = new BackgroundResourceRefresher(configuration, process);
				 refresher.startBackgroundRefresh();
				 }               
				 */
			} else {
				// wait for process to exit
				while (!process.isTerminated())
					try {
						if (monitor.isCanceled()) {
							process.terminate();
							break;
						}
						Thread.sleep(50);
					} catch (final InterruptedException e) {
					}

				// refresh resources
				subMonitor = new SubProgressMonitor(monitor, 10); // 10+80+10 of 100;
				RefreshTab.refreshResources(configuration, subMonitor);
			}
		}

	}

	protected boolean saveFiles(final IProject project, final IProgressMonitor monitor) {
		final Preferences prefs = PHPProjectPreferences.getModelPreferences();
		boolean autoSave = prefs.getBoolean(PHPDebugCorePreferenceNames.AUTO_SAVE_DIRTY);

		final SaveFilesResult result = SaveFilesHandler.handle(project, autoSave, true, monitor);
		if (!result.isAccepted())
			return false;
		if (result.isAutoSave() && !autoSave) {
			prefs.setValue(PHPDebugCorePreferenceNames.AUTO_SAVE_DIRTY, true);
			PHPDebugPlugin.getDefault().savePluginPreferences();
		}
		return true;

	}
}
