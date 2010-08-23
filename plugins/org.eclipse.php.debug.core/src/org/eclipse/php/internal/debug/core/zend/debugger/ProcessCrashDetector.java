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

import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.php.internal.debug.core.launching.PHPProcess;

/**
 * A process crash detector is a {@link Runnable} that hooks a PHP process error
 * stream and blocks until the process terminates. Then, the detector determines
 * if the process terminated as a result of an abnormal crash, or as a result of
 * a normal termination or a PHP fatal termination. The PHP termination codes
 * are between 0 - 255, so any other exit value is considered as a program
 * crash. The crash detector displays a message to the user in case of a crash.
 * 
 * @author shalom
 * @since PDT 1.0.1
 */
public class ProcessCrashDetector implements Runnable {

	private ILaunch launch;
	private Process process;

	/**
	 * Constructs a process detector on a given {@link Process}.
	 * 
	 * @param launch
	 *            {@link ILaunch}
	 * @param p
	 *            {@link Process}.
	 */
	public ProcessCrashDetector(ILaunch launch, Process p) {
		this.launch = launch;
		this.process = p;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		try {

			int exitValue = process.waitFor();
			IDebugTarget debugTarget = launch.getDebugTarget();
			if (debugTarget != null) {
				IProcess p = debugTarget.getProcess();
				if (p instanceof PHPProcess) {
					((PHPProcess) p).setExitValue(exitValue);
				}
			}

		} catch (Throwable t) {
		} finally {

		}
	}

}