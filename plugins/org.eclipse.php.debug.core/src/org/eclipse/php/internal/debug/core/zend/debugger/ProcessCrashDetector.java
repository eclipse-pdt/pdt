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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.internal.ui.DebugUIPlugin;
import org.eclipse.debug.internal.ui.preferences.IDebugPreferenceConstants;
import org.eclipse.debug.internal.ui.views.console.ProcessConsole;
import org.eclipse.php.internal.debug.core.IPHPDebugConstants;
import org.eclipse.php.internal.debug.core.launching.PHPProcess;
import org.eclipse.php.internal.debug.core.preferences.PHPDebugCorePreferenceNames;
import org.eclipse.php.internal.debug.core.zend.communication.DebuggerCommunicationDaemon;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleListener;
import org.eclipse.ui.console.IOConsoleOutputStream;

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
public class ProcessCrashDetector implements Runnable, IConsoleListener {

	private ILaunch launch;
	private Process process;
	private ProcessConsole console;

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
			boolean streamerReqd = false;
			try {
				ILaunchConfiguration config = launch.getLaunchConfiguration();
				if (config.getAttribute(
						PHPDebugCorePreferenceNames.PHP_DEBUGGER_ID, "") //$NON-NLS-1$
						.equals(DebuggerCommunicationDaemon.ZEND_DEBUGGER_ID)) {
					streamerReqd = config.getAttribute(
							IPHPDebugConstants.RUN_WITH_DEBUG_INFO, true)
							|| launch.getLaunchMode().equals(
									ILaunchManager.DEBUG_MODE);
				}
			} catch (CoreException e) {
			}
			if (streamerReqd) {

				StreamGobbler errorGobbler = new StreamGobbler(
						process.getErrorStream(), true);
				StreamGobbler inputGobbler = new StreamGobbler(
						process.getInputStream(), false);

				ConsolePlugin.getDefault().getConsoleManager()
						.addConsoleListener(this);

				errorGobbler.start();
				inputGobbler.start();
			}
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
			ConsolePlugin.getDefault().getConsoleManager()
					.removeConsoleListener(this);
		}
	}

	class StreamGobbler extends Thread {
		InputStream is;
		StringBuilder buf;
		boolean isError;
		private IOConsoleOutputStream os;

		StreamGobbler(InputStream is, boolean isError) {
			this.is = is;
			this.buf = new StringBuilder();
			this.isError = isError;
		}

		public void run() {
			try {
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);

				String line;
				while ((line = br.readLine()) != null) {
					if (!isError) {
						continue;
					}
					if (console != null) {
						if (os == null) {
							os = console.newOutputStream();
							if (isError) {
								Display.getDefault().syncExec(new Runnable() {
									public void run() {
										os.setColor(DebugUIPlugin
												.getPreferenceColor(IDebugPreferenceConstants.CONSOLE_SYS_ERR_COLOR));
									}
								});
							}
						}
						os.write(buf.toString());
						os.write(line + '\n');
						buf.delete(0, buf.length());
					} else {
						buf.append(line).append('\n');
					}
				}
			} catch (IOException ioe) {
				// PHPDebugPlugin.log(ioe);
			} finally {
				if (os != null) {
					try {
						os.close();
					} catch (IOException e) {
						// PHPDebugPlugin.log(e);
					}
				}
			}
		}
	}

	public void consolesAdded(IConsole[] consoles) {
		for (IConsole console : consoles) {
			if (console instanceof ProcessConsole) {
				this.console = (ProcessConsole) console;
			}
		}
	}

	public void consolesRemoved(IConsole[] consoles) {
	}
}