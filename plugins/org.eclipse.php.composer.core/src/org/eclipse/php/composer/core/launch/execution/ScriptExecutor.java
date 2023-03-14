/*******************************************************************************
 * Copyright (c) 2012, 2016, 2017 PDT Extension Group and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *     Kaloyan Raev - [501269] externalize strings
 *******************************************************************************/
package org.eclipse.php.composer.core.launch.execution;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.debug.core.DebugException;
import org.eclipse.php.composer.core.launch.environment.Environment;
import org.eclipse.php.composer.core.log.Logger;

/**
 * Executes a PHP script using a specific {@link Environment}.
 * 
 * 
 */
public class ScriptExecutor {

	private StringBuilder outBuilder;
	private StringBuilder errBuilder;

	private long timeout = 60000;

	private Set<ExecutionResponseListener> listeners = new HashSet<>();

	private File workingDirectory;

	private Process process;

	public ScriptExecutor() {

		outBuilder = new StringBuilder();
		errBuilder = new StringBuilder();

	}

	public void addResponseListener(ExecutionResponseListener listener) {
		listeners.add(listener);
	}

	public void removeResponseListener(ExecutionResponseListener listener) {
		listeners.remove(listener);
	}

	public void execute(ProcessBuilder launch) {
		try {
			for (ExecutionResponseListener handler : listeners) {
				handler.executionAboutToStart();
			}

			Logger.debug("executing command using executable: " + launch.command().get(0));

			if (this.workingDirectory != null) {
				launch.directory(workingDirectory);
			}

			process = launch.start();
			for (ExecutionResponseListener handler : listeners) {
				handler.executionStarted();
			}
			BufferedReader outputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			while ((line = outputReader.readLine()) != null) {
				for (ExecutionResponseListener handler : listeners) {
					handler.executionMessage(line);
					outBuilder.append(line);
				}
			}
			BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			while ((line = errorReader.readLine()) != null) {
				for (ExecutionResponseListener handler : listeners) {
					handler.executionError(line);
					errBuilder.append(line);
				}
			}
			int result = process.waitFor();
			for (ExecutionResponseListener handler : listeners) {
				handler.executionFinished(outBuilder.toString(), result);
			}

		} catch (Exception e) {
			for (ExecutionResponseListener handler : listeners) {
				handler.executionFailed("", e); //$NON-NLS-1$
			}
		}
	}

	public void abort() throws DebugException {
		if (process != null && process.isAlive()) {
			process.destroyForcibly();
		}

	}

	public void setWorkingDirectory(File dir) {
		workingDirectory = dir;
	}

	public File getWorkingDirectory() {
		return workingDirectory;
	}

	public void setTimeout(long timeout) {

		if (timeout < 0) {
			throw new IllegalArgumentException(Messages.ScriptExecutor_NegativeTimeoutError);
		}

		this.timeout = timeout;

	}
}
