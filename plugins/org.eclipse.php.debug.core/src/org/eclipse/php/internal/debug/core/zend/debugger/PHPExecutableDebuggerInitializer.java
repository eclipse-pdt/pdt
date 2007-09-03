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
package org.eclipse.php.internal.debug.core.zend.debugger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.internal.debug.core.PHPDebugCoreMessages;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.launching.PHPLaunchUtilities;
import org.eclipse.php.internal.debug.core.preferences.PHPexes;
import org.eclipse.swt.widgets.Display;

/**
 * A local PHP script debugger initializer.
 * This debugger initializer sets the necessary environment and program arguments for the PHP 
 * process and then starts the process.
 * 
 * @author Shalom Gibly
 */
public class PHPExecutableDebuggerInitializer {

	private ILaunch launch;

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
	 * @param query
	 */
	public void initializeDebug(String phpExe, String fileName, String query) {
		initializeDebug(phpExe, fileName, query, null, null);
	}

	/**
	 * Initialize the php executable debugger.
	 * 
	 * @param phpExe
	 * @param fileName
	 * @param query
	 * @param envVariables
	 * @param phpIniLocation
	 */
	public void initializeDebug(String phpExe, String fileName, String query, Map<String, String> envVariables, String phpIniLocation) {
		try {
			IPath phpExePath = new Path(phpExe);
			File workingDir = new File(phpExePath.removeLastSegments(1).toString());
			String phpConfigDir = workingDir.getAbsolutePath();
			if (phpIniLocation != null && !phpIniLocation.equals("")) {
				phpConfigDir = phpIniLocation;
			}
			String originalPHPConfigDir = phpConfigDir;

			// Important!!! 
			// Note that php executable -c parameter (for php 4) must get the path to the directory that contains the php.ini file.
			// We cannot use a full path to the php.ini file nor modify the file name! (for example php.temp.ini).
			phpConfigDir = (new File(phpConfigDir)).getParentFile().getAbsolutePath();

			// Prepare the environment
			if (envVariables == null) {
				envVariables = getAdditionalLaunchEnvironment(fileName, query, phpConfigDir, workingDir.getAbsolutePath());
			} else {
				Map<String, String> additionalLaunchEnvironment = getAdditionalLaunchEnvironment(fileName, query, phpConfigDir, workingDir.getAbsolutePath());
				additionalLaunchEnvironment.putAll(envVariables);
				envVariables = additionalLaunchEnvironment;
			}
			String[] environmetVars = PHPLaunchUtilities.getEnvironment(launch.getLaunchConfiguration(), asAttributesArray(envVariables));

			// Prepare the command line.
			String[] phpCmdArray = PHPLaunchUtilities.getCommandLine(launch.getLaunchConfiguration(), phpExe, originalPHPConfigDir, fileName, phpIniLocation);

			// Check if we need to change the executable path to a CGI executable in case we don't
			// need to support command arguments.
			if (phpCmdArray.length == 4) {
				phpCmdArray[0] = PHPexes.changeToCGI(phpCmdArray[0]);
			}

			// Execute the command line.
			Process p = Runtime.getRuntime().exec(phpCmdArray, environmetVars, workingDir);

			Runnable reader = new ProcessOutputReader(p);
			new Thread(reader).start();

		} catch (final Exception e) {
			final Display display = Display.getDefault();
			display.asyncExec(new Runnable() {
				public void run() {
					String message = e.getLocalizedMessage();
					if (message != null) {
						message = message.replaceFirst(e.getClass().getName() + ": ", "");
						MessageDialog.openError(display.getActiveShell(), "Error", NLS.bind("Error running PHP executable:\n\n{0}", message));
					}
				}
			});
			DebugPlugin.log(e);
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

	/**
	 * Returns the specific launch environment settings for this php script launch.
	 * 
	 * @param fileName
	 * @param query
	 * @param phpConfigDir
	 * @param workingDir
	 * @return A map of environment settings.
	 */
	protected Map<String, String> getAdditionalLaunchEnvironment(String fileName, String query, String phpConfigDir, String workingDir) {
		Map<String, String> env = new HashMap<String, String>();
		env.put("REQUEST_METHOD", "GET"); //$NON-NLS-1$ //$NON-NLS-2$
		env.put("SCRIPT_FILENAME", fileName); //$NON-NLS-1$
		env.put("SCRIPT_NAME", fileName); //$NON-NLS-1$
		env.put("PATH_TRANSLATED", fileName); //$NON-NLS-1$
		env.put("PATH_INFO", fileName); //$NON-NLS-1$
		env.put("QUERY_STRING", query + "&debug_host=127.0.0.1"); //$NON-NLS-1$ //$NON-NLS-2$
		env.put("REDIRECT_STATUS", "1"); //$NON-NLS-1$
		env.put("PHPRC", phpConfigDir); //$NON-NLS-1$
		String OS = System.getProperty("os.name"); //$NON-NLS-1$
		if (!OS.startsWith("Win")) { //$NON-NLS-1$
			if (OS.startsWith("Mac")) { //$NON-NLS-1$ //$NON-NLS-2$
				env.put("DYLD_LIBRARY_PATH", workingDir); //$NON-NLS-1$
			} else {
				env.put("LD_LIBRARY_PATH", workingDir); //$NON-NLS-1$
			}
		}
		return env;
	}

	private void initializeSystemEnvironmentVariables() throws IOException {

	}

	//	private void initializeSystemEnvironmentVariables() throws IOException {
	//		ArrayList<String> list = new ArrayList<String>();
	//		Process p;
	//		Runtime r = Runtime.getRuntime();
	//		String OS = System.getProperty("os.name").toLowerCase();
	//		if (OS.indexOf("windows") > -1) {
	//			if (OS.indexOf("windows 9") > -1) {
	//				p = r.exec("command.com /c set");
	//			} else {
	//				p = r.exec("cmd.exe /c set");
	//			}
	//		} else if (OS.indexOf("linux") > -1) {
	//			p = r.exec("env");
	//		} else if (OS.indexOf("unix") > -1) {
	//			p = r.exec("/bin/env");
	//		} else if (OS.indexOf("sunos") > -1) {
	//			p = r.exec("/bin/env");
	//		} else if (OS.indexOf("mac") > -1) {
	//			try {
	//				p = r.exec("env");
	//			} catch (IOException e) {
	//				p = r.exec("setenv");
	//			}
	//		} else if (OS.indexOf("freebsd") > -1) {
	//			p = r.exec("set");
	//		} else {
	//			System.out.println("OS not known: " + OS);
	//			systemEnvironmentVariables = new HashMap<String, String>(0);
	//			return;
	//		}
	//		BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
	//		String line;
	//		while ((line = br.readLine()) != null) {
	//			list.add(line);
	//		}
	//		br.close();
	//		systemEnvironmentVariables = new HashMap<String, String>();
	//		for (int i = 0; i < list.size(); i++) {
	//			String[] env = ((String) list.get(i)).split("=");
	//			if (env.length == 2) {
	//				systemEnvironmentVariables.put(env[0], env[1]);
	//			} else {
	//				if (env.length == 1) {
	//					systemEnvironmentVariables.put(env[0], "");
	//				}
	//			}
	//		}
	//	}

	// the reader reads the output of the process
	private class ProcessOutputReader implements Runnable {
		Process p;

		ProcessOutputReader(Process p) {
			this.p = p;
		}

		public void run() {
			try {
				byte buff[] = new byte[1000];
				InputStream in = p.getInputStream();
				int c = in.read(buff);
				while (c > 0) {
					c = in.read(buff);
				}
				in = p.getErrorStream();
				c = in.read(buff);
				while (c != -1) {
					c = in.read(buff);
				}
			} catch (IOException exc) {
				PHPDebugPlugin.log(exc);
			}
			// In case this thread ended and we do not have any IDebugTarget (PHPDebugTarget) hooked in the 
			// launch that was created, we can tell that there is something wrong, and probably there is no debugger
			// installed (e.g. the debugger dll/so is not properly configured in the php.ini).
			if (launch != null && launch.getDebugTarget() == null) {
				String launchName = launch.getLaunchConfiguration().getName();
				boolean isRunMode = ILaunchManager.RUN_MODE.equals(launch.getLaunchMode());
				String msg = null;
				if (isRunMode) {
					msg = MessageFormat.format(PHPDebugCoreMessages.Debugger_Error_Message_3, new String[] { launchName });
				} else {
					msg = MessageFormat.format(PHPDebugCoreMessages.Debugger_Error_Message_2, new String[] { launchName });
				}
				final String message = msg;
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						MessageDialog.openWarning(Display.getDefault().getActiveShell(), PHPDebugCoreMessages.Debugger_Error, message);
						DebugPlugin.getDefault().getLaunchManager().removeLaunch(launch);
					}
				});
			}
		}
	}
}