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
package org.eclipse.php.debug.core.debugger;

import java.io.*;
import java.util.ArrayList;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.php.debug.core.PHPDebugPlugin;
import org.eclipse.php.debug.core.debugger.parameters.IDebugParametersInitializer;
import org.eclipse.php.debug.core.debugger.parameters.IDebugParametersKeys;

public class PHPExecutableDebuggerInitializer {

	private String[] systemEnvironmentVariables = null;
	private ILaunch launch;

	public PHPExecutableDebuggerInitializer(ILaunch launch) throws IOException {
		this.launch = launch;
		initializeSystemEnvironmentVariables();
	}

	public void initializeDebug(String phpExePath, String fileName, int debugPort, boolean stopAtFirstLine) {
		initializeDebug(phpExePath, fileName, debugPort, "", stopAtFirstLine);
	}

	public void initializeDebug(String phpExe, String fileName, int debugPort, String extendedParameters, boolean stopAtFirstLine) {
		try {
			IPath phpExePath = new Path(phpExe);
			File workingDir = new File(phpExePath.removeLastSegments(1).toString());
			String phpConfigDir = workingDir.getAbsolutePath();

			IDebugParametersInitializer parametersInitializer = DebugParametersInitializersRegistry.getBestMatchDebugParametersInitializer(launch.getLaunchMode());
			parametersInitializer.addParameter(IDebugParametersKeys.PORT, new Integer(debugPort));
			parametersInitializer.addParameter(IDebugParametersKeys.FIRST_LINE_BREAKPOINT, Boolean.valueOf(stopAtFirstLine));

			String combinedEnvVars[] = new String[systemEnvironmentVariables.length + 8];
			String[] additionalVars = new String[] { "REQUEST_METHOD=GET", "SCRIPT_FILENAME=" + fileName, "SCRIPT_NAME=" + fileName, "PATH_TRANSLATED=" + fileName, "PATH_INFO=" + fileName, "QUERY_STRING=" + parametersInitializer.generateQuery() + extendedParameters + "&debug_host=127.0.0.1",
				"REDIRECT_STATUS=1", "PHPRC=" + phpConfigDir, };

			System.arraycopy(systemEnvironmentVariables, 0, combinedEnvVars, 0, systemEnvironmentVariables.length);
			System.arraycopy(additionalVars, 0, combinedEnvVars, combinedEnvVars.length - 8, additionalVars.length);

			String[] phpCmdArray = { phpExe, "-c", phpConfigDir, fileName };
			Process p = Runtime.getRuntime().exec(phpCmdArray, combinedEnvVars, workingDir);

			Runnable reader = new ProcessOutputReader(p);
			new Thread(reader).start();

		} catch (Exception e) {
			System.out.println("PHP Executable debugger error: " + e.getMessage());
		}
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
			systemEnvironmentVariables = new String[0];
			return;
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line;
		while ((line = br.readLine()) != null) {
			list.add(line);
		}
		br.close();
		systemEnvironmentVariables = new String[list.size()];
		list.toArray(systemEnvironmentVariables);
	}

	// the reader reads the output of the process
	private static class ProcessOutputReader implements Runnable {
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
		}
	}

}