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
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.model.ILaunchConfigurationDelegate;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.ui.CommonTab;
import org.eclipse.debug.ui.RefreshTab;
import org.eclipse.php.core.PHPCoreConstants;
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
import org.eclipse.php.ui.dialogs.saveFiles.SaveFilesRunnable;
import org.eclipse.swt.widgets.Display;

public class PHPExecutableLaunchDelegate implements ILaunchConfigurationDelegate {
	protected Map envVariables = null;

	public void launch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor) throws CoreException {
		if (monitor.isCanceled()) {
			return;
		}

		String phpExeString = configuration.getAttribute(PHPCoreConstants.ATTR_LOCATION, (String) null);
		String projectName = configuration.getAttribute(PHPCoreConstants.ATTR_WORKING_DIRECTORY, (String) null);
		String fileNameString = configuration.getAttribute(PHPCoreConstants.ATTR_FILE, (String) null);
		boolean runWithDebugInfo = configuration.getAttribute(IPHPConstants.RunWithDebugInfo, true);

		if (monitor.isCanceled()) {
			return;
		}

		if (fileNameString == null || fileNameString.equals(""))
			return;

		IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		IPath filePath = new Path(fileNameString);
		IProject project = null;
		String absolutePath = null;
		if (projectName == null) {
			IResource res = workspaceRoot.findMember(filePath);
			if (res == null)
				return;
			project = res.getProject();
			absolutePath = res.getLocation().toString();
		} else {
			try {
				IPath projectPath = new Path(projectName);
				project = workspaceRoot.getProject(projectPath.lastSegment());
				absolutePath = filePath.makeAbsolute().toString();
			} catch (Exception e) {
			}
			if (project == null) {
				return;
			}
		}
		Preferences prefs = PHPProjectPreferences.getModelPreferences();
		boolean autoSave = prefs.getBoolean(PHPDebugCorePreferenceNames.AUTO_SAVE_DIRTY);
		SaveFilesRunnable runnable = new SaveFilesRunnable(project, true, autoSave);
		Display.getDefault().syncExec(runnable);
		if (!runnable.isSaved())
			return;
		if (runnable.isAutoSaved() && !autoSave) {
			prefs.setValue(PHPDebugCorePreferenceNames.AUTO_SAVE_DIRTY, true);
			PHPDebugPlugin.getDefault().savePluginPreferences();
		}
		

		if (mode.equals(ILaunchManager.DEBUG_MODE) || runWithDebugInfo == true) {
			boolean stopAtFirstLine = PHPProjectPreferences.getStopAtFirstLine(project);
			int requestPort = PHPProjectPreferences.getDebugPort(project);

			// Set Project Name
			String projectString = project.getFullPath().toString();
			ILaunchConfigurationWorkingCopy wc = configuration.getWorkingCopy();
			wc.setAttribute(IPHPConstants.PHP_Project, projectString);
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

			// Trigger the debug session by initiating a debug requset to the php.exe
			debugPHPExecutable(launch, phpExeString, absolutePath);
		} else {
			// resolve location
			IPath phpExe = new Path(phpExeString);

			if (monitor.isCanceled()) {
				return;
			}

			// resolve working directory
			//            String projectFolderString = configuration.getAttribute(PHPCoreConstants.ATTR_WORKING_DIRECTORY, (String)null);

			IPath projectLocation = project.getRawLocation();
			if (projectLocation == null) {
				projectLocation = project.getLocation();
			}
			String location = projectLocation.toOSString();
			IPath p1 = new Path(location);
			File projectDir = p1.toFile();

			if (monitor.isCanceled()) {
				return;
			}

			//            String fileNameString = configuration.getAttribute(PHPCoreConstants.ATTR_FILE, (String)null);
			IPath phpFile = new Path(fileNameString);
			if (fileNameString.startsWith("/"))
				phpFile = phpFile.removeFirstSegments(1);

			if (monitor.isCanceled()) {
				return;
			}

			String[] envp = DebugPlugin.getDefault().getLaunchManager().getEnvironment(configuration);

			if (monitor.isCanceled()) {
				return;
			}

			String[] cmdLine = new String[] { phpExe.toOSString(), phpFile.toOSString() };

			Process p = DebugPlugin.exec(cmdLine, projectDir, envp);
			IProcess process = null;

			// add process type to process attributes
			Map processAttributes = new HashMap();
			String programName = phpExe.lastSegment();
			String extension = phpExe.getFileExtension();
			if (extension != null) {
				programName = programName.substring(0, programName.length() - (extension.length() + 1));
			}
			programName = programName.toLowerCase();
			processAttributes.put(IProcess.ATTR_PROCESS_TYPE, programName);

			if (p != null) {
				monitor.beginTask(MessageFormat.format("start launch", new String[] { configuration.getName() }), IProgressMonitor.UNKNOWN); //$NON-NLS-1$
				process = DebugPlugin.newProcess(launch, p, phpExe.toOSString(), processAttributes);
				if (process == null) {
					p.destroy();
					throw new CoreException(new Status(IStatus.ERROR, PHPDebugPlugin.getID(), 0, null, null)); //$NON-NLS-1$
				}

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
				RefreshTab.refreshResources(configuration, monitor);
			}
		}

	}

	public void debugPHPExecutable(ILaunch launch, String phpExe, String fileToDebug) throws DebugException {
		try {
			PHPExecutableDebuggerInitializer debuggerInitializer = new PHPExecutableDebuggerInitializer(launch);
			debuggerInitializer.initializeDebug(new File(phpExe).getAbsolutePath(), new File(fileToDebug).getAbsolutePath(), envVariables);
		} catch (java.io.IOException e1) {
			Logger.logException("PHPDebugTarget: Debugger didn't find file to debug.", e1);
			String errorMessage = PHPDebugCoreMessages.DebuggerFileNotFound_1;
			throw new DebugException(new Status(IStatus.ERROR, PHPDebugPlugin.getID(), IPHPConstants.INTERNAL_ERROR, errorMessage, e1));
		}
	}

}
