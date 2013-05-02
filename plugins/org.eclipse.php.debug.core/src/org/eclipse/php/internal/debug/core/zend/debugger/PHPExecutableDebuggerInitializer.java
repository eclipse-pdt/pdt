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
package org.eclipse.php.internal.debug.core.zend.debugger;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.internal.debug.core.PHPDebugCoreMessages;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.launching.PHPLaunchUtilities;
import org.eclipse.php.internal.debug.core.preferences.PHPexeItem;
import org.eclipse.php.internal.debug.core.preferences.PHPexes;
import org.eclipse.php.internal.debug.daemon.DaemonPlugin;
import org.eclipse.swt.widgets.Display;

import com.ibm.icu.text.MessageFormat;

/**
 * A local PHP script debugger initializer. This debugger initializer sets the
 * necessary environment and program arguments for the PHP process and then
 * starts the process.
 * 
 * @author Shalom Gibly
 */
public class PHPExecutableDebuggerInitializer {

	protected ILaunch launch;

	/**
	 * Constructs a new debugger initializer.
	 * 
	 * @param launch
	 * @throws IOException
	 */
	public PHPExecutableDebuggerInitializer(ILaunch launch) throws IOException {
		this.launch = launch;
	}

	/**
	 * Initialize the php executable debugger.
	 * 
	 * @param phpExe
	 * @param fileName
	 * @param workingDir
	 * @param query
	 */
	public void initializeDebug(String phpExe, String fileName,
			String workingDir, String query) {
		initializeDebug(phpExe, fileName, workingDir, query, null, null);
	}

	/**
	 * Initialize the php executable debugger.
	 * 
	 * @param phpExe
	 * @param fileName
	 * @param workingDir
	 * @param query
	 * @param envVariables
	 * @param phpIniLocation
	 */
	public void initializeDebug(String phpExe, String fileName,
			String workingDir, String query, Map<String, String> envVariables,
			String phpIniLocation) {
		try {
			File phpExeFile = new File(phpExe);

			// Determine configuration file directory:
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

			String[] args = PHPLaunchUtilities.getProgramArguments(launch
					.getLaunchConfiguration());

			// Prepare the environment
			Map<String, String> additionalLaunchEnvironment = PHPLaunchUtilities
					.getPHPCGILaunchEnvironment(fileName, query, phpConfigDir,
							phpExeFile.getParent(),
							sapiType == PHPexeItem.SAPI_CGI ? args : null);
			if (envVariables == null) {
				envVariables = additionalLaunchEnvironment;
			} else {
				additionalLaunchEnvironment.putAll(envVariables);
				envVariables = additionalLaunchEnvironment;
			}
			String[] environmetVars = PHPLaunchUtilities.getEnvironment(
					launch.getLaunchConfiguration(),
					asAttributesArray(envVariables));

			// Prepare the command line.
			String[] phpCmdArray = PHPLaunchUtilities.getCommandLine(
					launch.getLaunchConfiguration(), phpExe, phpConfigDir,
					fileName, sapiType == PHPexeItem.SAPI_CLI ? args : null,
					phpV);

			// Make sure that we have executable permissions on the file.
			PHPexes.changePermissions(new File(phpCmdArray[0]));

			if (PHPDebugPlugin.DEBUG) {
				System.out
						.println("Executing: " + Arrays.toString(phpCmdArray)); //$NON-NLS-1$
				System.out.println("Process environment: " //$NON-NLS-1$
						+ Arrays.toString(environmetVars));
			}

			DaemonPlugin.getDefault().makeSureDebuggerInitialized(null);
			// Execute the command line.
			Process p;
			File workingDirFile = new File(workingDir);
			if (workingDirFile.exists()) {
				if (PHPDebugPlugin.DEBUG) {
					System.out.println("Working directory: " + workingDir); //$NON-NLS-1$
				}
				p = Runtime.getRuntime().exec(phpCmdArray, environmetVars,
						workingDirFile);
			} else {
				p = Runtime.getRuntime().exec(phpCmdArray, environmetVars);
			}

			// Attach a crash detector
			new Thread(new ProcessCrashDetector2(launch, p)).start();

		} catch (final Exception e) {
			final Display display = Display.getDefault();
			display.asyncExec(new Runnable() {
				public void run() {
					String message = e.getLocalizedMessage();
					if (message != null) {
						message = message.replaceFirst(e.getClass().getName()
								+ ": ", ""); //$NON-NLS-1$ //$NON-NLS-2$
						MessageDialog.openError(display.getActiveShell(),
								PHPDebugCoreMessages.PHPExecutableDebuggerInitializer_6, NLS.bind(
										PHPDebugCoreMessages.PHPExecutableDebuggerInitializer_7,
										message));
					}
				}
			});
			DebugPlugin.log(e);
		} finally {
			// Remove temporary directory
			if (phpIniLocation != null) {
				File phpIniFile = new File(phpIniLocation);
				phpIniFile.deleteOnExit();
				phpIniFile.getParentFile().deleteOnExit();
			}
		}
	}

	class ProcessCrashDetector2 extends ProcessCrashDetector {

		private ILaunch launch;

		public ProcessCrashDetector2(ILaunch launch, Process p) {
			super(launch, p);
			this.launch = launch;
		}

		public void run() {
			super.run();

			// In case this thread ended and we do not have any IDebugTarget
			// (PHPDebugTarget) hooked in the
			// launch that was created, we can tell that there is something
			// wrong, and probably there is no debugger
			// installed (e.g. the debugger dll/so is not properly configured in
			// the php.ini).
			if (launch != null && launch.getDebugTarget() == null) {
				String launchName = launch.getLaunchConfiguration().getName();
				boolean isRunMode = ILaunchManager.RUN_MODE.equals(launch
						.getLaunchMode());
				String msg = null;
				if (isRunMode) {
					msg = MessageFormat.format(
							PHPDebugCoreMessages.Debugger_Error_Message_3,
							new Object[] { launchName });
				} else {
					msg = MessageFormat.format(
							PHPDebugCoreMessages.Debugger_Error_Message_2,
							new Object[] { launchName });
				}
				final String message = msg;
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						MessageDialog.openWarning(Display.getDefault()
								.getActiveShell(),
								PHPDebugCoreMessages.Debugger_Launch_Error,
								message);
						DebugPlugin.getDefault().getLaunchManager()
								.removeLaunch(launch);
					}
				});
			}
		}
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