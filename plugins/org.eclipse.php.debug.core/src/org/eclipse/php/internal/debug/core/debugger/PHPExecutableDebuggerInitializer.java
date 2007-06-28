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
package org.eclipse.php.internal.debug.core.debugger;

import java.io.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.internal.debug.core.PHPDebugCoreMessages;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.swt.widgets.Display;

public class PHPExecutableDebuggerInitializer {

	private HashMap systemEnvironmentVariables = null;
	private ILaunch launch;

	public PHPExecutableDebuggerInitializer(ILaunch launch) throws IOException {
		this.launch = launch;
		// Set a launch attribute to indicate that this is an executable launch.
		initializeSystemEnvironmentVariables();
	}

	public PHPExecutableDebuggerInitializer() throws IOException {
		// Set a launch attribute to indicate that this is an executable launch.
		initializeSystemEnvironmentVariables();
	}

	public void initializeDebug(String phpExe, String fileName, String query) {

		initializeDebug(phpExe, fileName, query, null, null);
	}

	public void initializeDebug(String phpExe, String fileName, String query, Map envVariables, String phpIniLocation) {
		try {
			IPath phpExePath = new Path(phpExe);
			File workingDir = new File(phpExePath.removeLastSegments(1).toString());
			String phpConfigDir = workingDir.getAbsolutePath();
			if (phpIniLocation != null && !phpIniLocation.equals("")) {
				phpConfigDir = phpIniLocation;
			}

			// Important!!! 
			// Note that php executable -c parameter (for php 4) must get the path to the directory that contains the php.ini file.
			// We cannot use a full path to the php.ini file nor modify the file name! (for example php.temp.ini).
			phpConfigDir = (new File(phpConfigDir)).getParentFile().getAbsolutePath();

			systemEnvironmentVariables.put("REQUEST_METHOD", "GET");
			systemEnvironmentVariables.put("SCRIPT_FILENAME", fileName);
			systemEnvironmentVariables.put("SCRIPT_NAME", fileName);
			systemEnvironmentVariables.put("PATH_TRANSLATED", fileName);
			systemEnvironmentVariables.put("PATH_INFO", fileName);
			systemEnvironmentVariables.put("QUERY_STRING", query + "&debug_host=127.0.0.1");
			systemEnvironmentVariables.put("REDIRECT_STATUS", "1");
			systemEnvironmentVariables.put("PHPRC", phpConfigDir);
			if (envVariables != null) {
				systemEnvironmentVariables.putAll(envVariables);
			}

			String[] combinedEnvVars = mapAsArray(systemEnvironmentVariables);

			String[] phpCmdArray = { phpExe, "-c", phpConfigDir, fileName };
			Process p = Runtime.getRuntime().exec(phpCmdArray, combinedEnvVars, workingDir);

			Runnable reader = new ProcessOutputReader(p);
			new Thread(reader).start();

		} catch (final Exception e) {
			final Display display = Display.getDefault();
			display.asyncExec(new Runnable() {
				public void run() {
					String message = e.getLocalizedMessage();
					message = message.replaceFirst(e.getClass().getName() + ": ", "");
					MessageDialog.openError(display.getActiveShell(), "Error", NLS.bind("Error running PHP executable:\n\n{0}", message));
				}
			});
			DebugPlugin.log(e);
		}
	}

	private String[] mapAsArray(Map map) {
		String[] strArr = new String[map.size()];
		Iterator entries = map.entrySet().iterator();
		int index = 0;
		while (entries.hasNext()) {
			Entry entry = (Entry) entries.next();
			strArr[index++] = entry.getKey() + "=" + entry.getValue();
		}
		return strArr;
	}

	private void initializeSystemEnvironmentVariables() throws IOException {
		ArrayList list = new ArrayList();
		Process p;
		Runtime r = Runtime.getRuntime();
		String OS = System.getProperty("os.name").toLowerCase();
		if (OS.indexOf("windows") > -1) {
			if (OS.indexOf("windows 9") > -1) {
				p = r.exec("command.com /c set");
			} else {
				p = r.exec("cmd.exe /c set");
			}
		} else if (OS.indexOf("linux") > -1) {
			p = r.exec("env");
		} else if (OS.indexOf("unix") > -1) {
			p = r.exec("/bin/env");
		} else if (OS.indexOf("sunos") > -1) {
			p = r.exec("/bin/env");
		} else if (OS.indexOf("mac") > -1) {
			try {
				p = r.exec("env");
			} catch (IOException e) {
				p = r.exec("setenv");
			}
		} else if (OS.indexOf("freebsd") > -1) {
			p = r.exec("set");
		} else {
			System.out.println("OS not known: " + OS);
			systemEnvironmentVariables = new HashMap(0);
			return;
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line;
		while ((line = br.readLine()) != null) {
			list.add(line);
		}
		br.close();
		systemEnvironmentVariables = new HashMap();
		for (int i = 0; i < list.size(); i++) {
			String[] env = ((String) list.get(i)).split("=");
			if (env.length == 2) {
				systemEnvironmentVariables.put(env[0], env[1]);
			} else {
				if (env.length == 1) {
					systemEnvironmentVariables.put(env[0], "");
				}
			}
		}
	}

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