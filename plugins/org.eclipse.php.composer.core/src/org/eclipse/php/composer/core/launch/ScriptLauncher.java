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
package org.eclipse.php.composer.core.launch;

import java.io.File;
import java.io.IOException;
import java.util.*;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugException;
import org.eclipse.php.composer.core.launch.environment.Environment;
import org.eclipse.php.composer.core.launch.execution.ExecutionResponseListener;
import org.eclipse.php.composer.core.launch.execution.ScriptExecutor;
import org.eclipse.php.composer.core.log.Logger;
import org.eclipse.php.internal.debug.core.launching.PHPLaunchUtilities;

/**
 * 
 * Uses a {@link ScriptExecutor} to launch a PHP script with a specific
 * {@link Environment}.
 * 
 * You can use an {@link ExecutionResponseListener} to get retrieve the output
 * of the executed script.
 *
 */
public class ScriptLauncher {

	private Environment environment;
	private IProject project;
	private ScriptExecutor executor;
	private Set<ExecutionResponseListener> listeners = new HashSet<>();
	private Integer timeout = null;

	public ScriptLauncher(Environment environment, IProject project) throws ScriptNotFoundException {
		this.environment = environment;
		this.project = project;
		this.environment.setUp(project);
	}

	public void addResponseListener(ExecutionResponseListener listener) {
		listeners.add(listener);
	}

	public void removeResponseListener(ExecutionResponseListener listener) {
		listeners.remove(listener);
	}

	public void launch(String argument) throws IOException, CoreException, InterruptedException {
		launch(argument, new String[] {});
	}

	public void launch(String argument, String param) throws IOException, CoreException, InterruptedException {
		launch(argument, new String[] { param });
	}

	protected ProcessBuilder prepare(String argument, String... params)
			throws IOException, InterruptedException, CoreException {
		ProcessBuilder cmd = environment.getCommand();
		cmd.command().add(argument);
		cmd.command().addAll(Arrays.asList(params));

		executor = new ScriptExecutor();

		if (timeout != null) {
			executor.setTimeout(timeout);
		}

		Logger.debug("Setting executor working directory to " + project.getLocation().toOSString()); //$NON-NLS-1$
		cmd.directory(new File(project.getLocation().toOSString()));

		for (ExecutionResponseListener listener : listeners) {
			executor.addResponseListener(listener);
		}

		Map<String, String> env = new HashMap<>(System.getenv());

		PHPLaunchUtilities.appendExecutableToPathEnv(env, new File(cmd.command().get(0)).getParentFile());
		PHPLaunchUtilities.appendLibrarySearchPathEnv(env, new File(cmd.command().get(0)).getParentFile());
		cmd.environment().putAll(env);

		return cmd;
	}

	public void launch(String argument, String... params) throws IOException, InterruptedException, CoreException {

		ProcessBuilder cmd = prepare(argument, params);
		executor.execute(cmd);

	}

	protected Set<ExecutionResponseListener> getListeners() {
		return listeners;
	}

	public void abort() {
		try {
			executor.abort();
		} catch (DebugException e) {
			Logger.debug(e.getMessage());
		}
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
}
