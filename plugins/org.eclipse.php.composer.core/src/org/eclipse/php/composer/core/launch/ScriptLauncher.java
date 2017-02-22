/*******************************************************************************
 * Copyright (c) 2012, 2016, 2017 PDT Extension Group and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *     Kaloyan Raev - [501269] externalize strings
 *******************************************************************************/
package org.eclipse.php.composer.core.launch;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.ExecuteException;
import org.eclipse.core.resources.IProject;
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
	private Set<ExecutionResponseListener> listeners = new HashSet<ExecutionResponseListener>();
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

	public void launch(String argument) throws ExecuteException, IOException, InterruptedException {
		launch(argument, new String[] {});
	}

	public void launch(String argument, String param) throws ExecuteException, IOException, InterruptedException {
		launch(argument, new String[] { param });
	}

	public void launch(String argument, String... params) throws ExecuteException, IOException, InterruptedException {
		CommandLine cmd = environment.getCommand();
		cmd.addArgument(argument);
		cmd.addArguments(params);

		executor = new ScriptExecutor();

		if (timeout != null) {
			executor.setTimeout(timeout);
		}

		Logger.debug("Setting executor working directory to " + project.getLocation().toOSString()); //$NON-NLS-1$
		executor.setWorkingDirectory(project.getLocation().toFile());

		for (ExecutionResponseListener listener : listeners) {
			executor.addResponseListener(listener);
		}

		Map<String, String> env = new HashMap<String, String>(System.getenv());
		PHPLaunchUtilities.appendExecutableToPathEnv(env, new File(cmd.getExecutable()).getParentFile());
		PHPLaunchUtilities.appendLibrarySearchPathEnv(env, new File(cmd.getExecutable()).getParentFile());

		executor.execute(cmd, env);
	}

	protected Set<ExecutionResponseListener> getListeners() {
		return listeners;
	}

	public void abort() {
		executor.abort();
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
}
