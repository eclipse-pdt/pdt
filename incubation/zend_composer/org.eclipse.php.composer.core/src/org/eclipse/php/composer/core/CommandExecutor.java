/*******************************************************************************
 * Copyright (c) 2013, 2016 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.core;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.jobs.Job;

/**
 * 
 */
public class CommandExecutor {

	protected class MonitorCanceledJob extends Job {

		private IProgressMonitor toMonitor;
		private CommandExecutor cmd;

		public MonitorCanceledJob(CommandExecutor cmd, IProgressMonitor toMonitor) {
			super("Cancel Process Job"); //$NON-NLS-1$
			this.toMonitor = toMonitor;
			this.cmd = cmd;
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			while (cmd.process != null) {
				if (toMonitor.isCanceled()) {
					cmd.process.destroy();
					break;
				} else {
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						ComposerCorePlugin.logError(e);
					}
				}
			}
			return org.eclipse.core.runtime.Status.OK_STATUS;
		}
	}

	protected Process process;

	protected ILogDevice ouputLogDevice;
	protected ILogDevice errorLogDevice;
	protected String workingDirectory;
	protected Map<String, String> environmentVars;

	protected StringBuffer commandOutput;
	protected StringBuffer commandError;
	protected AsyncStreamReader commandOutThread;
	protected AsyncStreamReader commandErrorThread;

	protected String[] command;

	public CommandExecutor() {
		this.environmentVars = new HashMap<String, String>();
	}

	public void setCommand(List<String> command) {
		setCommand(command.toArray(new String[command.size()]));
	}

	public void setCommand(String... command) {
		this.command = command;
	}

	public void setOutputLogDevice(ILogDevice logDevice) {
		ouputLogDevice = logDevice;
	}

	public void setErrorLogDevice(ILogDevice logDevice) {
		errorLogDevice = logDevice;
	}

	public void setWorkingDirectory(String workingDirectory) {
		this.workingDirectory = workingDirectory;
	}

	public void setEnvironmentVar(String name, String value) {
		if (environmentVars == null) {
			environmentVars = new HashMap<String, String>();
		}
		environmentVars.put(name, value);
	}

	public String getCommandOutput() {
		return commandOutput.toString();
	}

	public String getCommandError() {
		return commandError.toString();
	}

	public int run(IProgressMonitor monitor) throws IOException {
		process = runCommandHelper();
		MonitorCanceledJob cancelJob = new MonitorCanceledJob(this, monitor);
		cancelJob.setSystem(true);
		cancelJob.schedule();
		startOutputAndErrorReadThreads(process.getInputStream(), process.getErrorStream());
		updateLogDeviceOutputStream(process.getOutputStream());
		int exitStatus = -1;
		try {
			exitStatus = process.waitFor();
		} catch (InterruptedException e) {
			ComposerCorePlugin.logError(e);
		} finally {
			process = null;
			notifyOutputAndErrorReadThreadsToStopReading();
		}
		return exitStatus;
	}

	protected Process runCommandHelper() throws IOException {
		ProcessBuilder pBuilder = new ProcessBuilder(command);
		pBuilder.environment().putAll(getEnvTokens());
		if (workingDirectory != null) {
			pBuilder.directory(new File(workingDirectory));
		}
		return pBuilder.start();
	}

	protected void startOutputAndErrorReadThreads(InputStream processOut, InputStream processErr) {
		commandOutput = new StringBuffer();
		commandOutThread = new AsyncStreamReader(processOut, commandOutput, ouputLogDevice);
		commandOutThread.start();
		commandError = new StringBuffer();
		commandErrorThread = new ErrorAsyncStreamReader(processErr, commandError, errorLogDevice);
		commandErrorThread.start();
	}

	protected void updateLogDeviceOutputStream(OutputStream outputStream) {
		if (ouputLogDevice != null) {
			ouputLogDevice.updateOutputStream(outputStream);
		}
	}

	protected void notifyOutputAndErrorReadThreadsToStopReading() {
		commandOutThread.stopReading();
		commandErrorThread.stopReading();
	}

	protected Map<String, String> getEnvTokens() {
		Map<String, String> currentVars = System.getenv();
		Set<String> currentKeys = currentVars.keySet();
		for (String key : currentKeys) {
			if (!environmentVars.containsKey(key)) {
				environmentVars.put(key, currentVars.get(key));
			}
		}
		return environmentVars;
	}

	public String[] getCommand() {
		return command;
	}

}