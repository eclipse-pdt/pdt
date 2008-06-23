/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.zend.debugger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.eclipse.php.internal.debug.core.PHPDebugCoreMessages;
import org.eclipse.php.internal.debug.core.launching.PHPLaunchUtilities;

/**
 * A process crash detector is a {@link Runnable} that hooks a PHP process error stream and blocks until the process terminates.
 * Then, the detector determines if the process terminated as a result of an abnormal crash, or as a result
 * of a normal termination or a PHP fatal termination.
 * The PHP termination codes are between 0 - 255, so any other exit value is considered as a program crash.
 * The crash detector displays a message to the user in case of a crash.
 * 
 * @author shalom
 * @since PDT 1.0.1
 */
public class ProcessCrashDetector implements Runnable {

	private BufferedReader errorStream;
	private Process process;

	/**
	 * Constructs a process detector on a given {@link Process}.
	 * 
	 * @param p A {@link Process}.
	 */
	public ProcessCrashDetector(Process p) {
		this.process = p;
		errorStream = new BufferedReader(new InputStreamReader(p.getErrorStream()));
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		StringBuilder builder = new StringBuilder();
		String str = null;
		try {
			while ((str = errorStream.readLine()) != null) {
				builder.append(str);
				builder.append('\n');
			}
		} catch (IOException ioe) {
		}
		try {
			int exitValue = process.exitValue();
			if (exitValue > 255 || exitValue < 0) {
				PHPLaunchUtilities.showDebuggerErrorMessage(PHPDebugCoreMessages.Debugger_General_Error, PHPDebugCoreMessages.Debugger_Error_Crash_Message);
			}
		} catch (Throwable t) {
		}
	}
}