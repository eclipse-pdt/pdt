/*******************************************************************************
 * Copyright (c) 2017 Alex Xu and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Alex Xu - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.server.core.builtin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.osgi.service.environment.Constants;

public abstract class AbstractPHPServerRunner implements IPHPServerRunner {

	/**
	 * Executes the given command line using the given working directory
	 * 
	 * @param cmdLine
	 *            the command line
	 * @param workingDirectory
	 *            the working directory
	 * @return the {@link Process}
	 * @throws CoreException
	 *             if the execution fails
	 * @see DebugPlugin#exec(String[], File)
	 */
	protected Process exec(String[] cmdLine, File workingDirectory) throws CoreException {
		cmdLine = quoteWindowsArgs(cmdLine);
		return DebugPlugin.exec(cmdLine, workingDirectory);
	}

	/**
	 * Executes the given command line using the given working directory and
	 * environment
	 * 
	 * @param cmdLine
	 *            the command line
	 * @param workingDirectory
	 *            the working directory
	 * @param envp
	 *            the environment
	 * @return the {@link Process}
	 * @throws CoreException
	 *             is the execution fails
	 * @since 3.0
	 * @see DebugPlugin#exec(String[], File, String[])
	 */
	protected Process exec(String[] cmdLine, File workingDirectory, String[] envp) throws CoreException {
		cmdLine = quoteWindowsArgs(cmdLine);
		return DebugPlugin.exec(cmdLine, workingDirectory, envp);
	}

	private static String[] quoteWindowsArgs(String[] cmdLine) {
		if (Platform.getOS().equals(Constants.OS_WIN32)) {
			String[] winCmdLine = new String[cmdLine.length];
			if (cmdLine.length > 0) {
				winCmdLine[0] = cmdLine[0];
			}
			for (int i = 1; i < cmdLine.length; i++) {
				winCmdLine[i] = winQuote(cmdLine[i]);
			}
			cmdLine = winCmdLine;
		}
		return cmdLine;
	}

	private static boolean needsQuoting(String s) {
		int len = s.length();
		if (len == 0) // empty string has to be quoted
			return true;
		if ("\"\"".equals(s)) //$NON-NLS-1$
			return false; // empty quotes must not be quoted again
		for (int i = 0; i < len; i++) {
			switch (s.charAt(i)) {
			case ' ':
			case '\t':
			case '\\':
			case '"':
				return true;
			}
		}
		return false;
	}

	private static String winQuote(String s) {
		if (!needsQuoting(s))
			return s;
		s = s.replaceAll("([\\\\]*)\"", "$1$1\\\\\""); //$NON-NLS-1$ //$NON-NLS-2$
		s = s.replaceAll("([\\\\]*)\\z", "$1$1"); //$NON-NLS-1$ //$NON-NLS-2$
		return "\"" + s + "\""; //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * Returns the given array of strings as a single space-delimited string.
	 * 
	 * @param cmdLine
	 *            array of strings
	 * @return a single space-delimited string
	 */
	protected String getCmdLineAsString(String[] cmdLine) {
		StringBuffer buff = new StringBuffer();
		for (int i = 0, numStrings = cmdLine.length; i < numStrings; i++) {
			buff.append(cmdLine[i]);
			buff.append(' ');
		}
		return buff.toString().trim();
	}

	/**
	 * Prepares the command line from the specified array of strings
	 * 
	 * @param commandLine
	 *            the command line
	 * @return the command line label
	 */
	protected String renderCommandLine(String[] commandLine) {
		return DebugPlugin.renderArguments(commandLine, null);
	}

	/**
	 * Returns a new process aborting if the process could not be created.
	 * 
	 * @param launch
	 *            the launch the process is contained in
	 * @param p
	 *            the system process to wrap
	 * @param label
	 *            the label assigned to the process
	 * @param attributes
	 *            values for the attribute map
	 * @return the new process
	 * @throws CoreException
	 *             problems occurred creating the process
	 * @since 3.0
	 */
	protected IProcess newProcess(ILaunch launch, Process p, String label, Map<String, String> attributes)
			throws CoreException {
		IProcess process = DebugPlugin.newProcess(launch, p, label, attributes);
		if (process == null) {
			p.destroy();
			abort(Messages.AbstractPHPServerRunner_0, null, 1);
		}
		return process;
	}

	/**
	 * Throws a core exception with an error status object built from the given
	 * message, lower level exception, and error code.
	 * 
	 * @param message
	 *            the status message
	 * @param exception
	 *            lower level exception associated with the error, or
	 *            <code>null</code> if none
	 * @param code
	 *            error code
	 * @throws CoreException
	 *             The exception encapsulating the reason for the abort
	 */
	protected void abort(String message, Throwable exception, int code) throws CoreException {
		throw new CoreException(new Status(IStatus.ERROR, PHPServerPlugin.PLUGIN_ID, code, message, exception));
	}

	/**
	 * Returns the default process attribute map for Java processes.
	 * 
	 * @return default process attribute map for Java processes
	 */
	protected Map<String, String> getDefaultProcessMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put(IProcess.ATTR_PROCESS_TYPE, "php"); //$NON-NLS-1$
		return map;
	}
}
