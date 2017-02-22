/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Michał Niewrzał (Rogue Wave Software Inc.) - initial implementation
 *******************************************************************************/
package org.eclipse.php.composer.ui.terminal;

import java.io.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.ExecuteException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.php.composer.core.ComposerPlugin;
import org.eclipse.php.composer.core.launch.ScriptLauncher;
import org.eclipse.php.composer.core.launch.ScriptNotFoundException;
import org.eclipse.php.composer.core.launch.environment.Environment;
import org.eclipse.php.composer.core.launch.execution.ExecutionResponseListener;
import org.eclipse.php.composer.core.log.Logger;
import org.eclipse.php.internal.debug.core.launching.PHPLaunchUtilities;
import org.eclipse.tm.terminal.view.core.interfaces.ITerminalService.Done;
import org.eclipse.tm.terminal.view.core.interfaces.ITerminalServiceOutputStreamMonitorListener;
import org.eclipse.tm.terminal.view.core.interfaces.constants.ITerminalsConnectorConstants;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;

public class ComposerLauncher extends ScriptLauncher {

	private final static String WINDOWS_END_OF_LINE = "\r\n"; //$NON-NLS-1$

	private Environment environment;
	private IProject project;
	private TerminalConsole terminalConsole;

	public ComposerLauncher(Environment environment, IProject project) throws ScriptNotFoundException {
		super(environment, project);
		this.environment = environment;
		this.project = project;
	}

	@Override
	public void launch(String argument, String... params) throws ExecuteException, IOException, InterruptedException {
		CommandLine cmd = environment.getCommand();
		cmd.addArgument(argument);
		cmd.addArguments(params);

		Map<String, String> env = new HashMap<String, String>(System.getenv());
		PHPLaunchUtilities.appendExecutableToPathEnv(env, new File(cmd.getExecutable()).getParentFile());
		PHPLaunchUtilities.appendLibrarySearchPathEnv(env, new File(cmd.getExecutable()).getParentFile());

		Map<String, Object> properties = new HashMap<String, Object>();
		List<String> envs = new ArrayList<>(env.size());
		for (String key : env.keySet()) {
			envs.add(key + '=' + env.get(key));
		}
		properties.put(ITerminalsConnectorConstants.PROP_PROCESS_ENVIRONMENT, envs.toArray(new String[envs.size()]));
		properties.put(ITerminalsConnectorConstants.PROP_PROCESS_MERGE_ENVIRONMENT, true);
		ITerminalServiceOutputStreamMonitorListener[] outListeners = new ITerminalServiceOutputStreamMonitorListener[] {
				new ITerminalServiceOutputStreamMonitorListener() {

					@Override
					public void onContentReadFromStream(byte[] byteBuffer, int bytesRead) {
						for (ExecutionResponseListener handler : getListeners()) {
							handler.executionMessage(new String(byteBuffer, 0, bytesRead));
						}
					}

				} };
		properties.put(ITerminalsConnectorConstants.PROP_STDOUT_LISTENERS, outListeners);

		// workaround for colored output on Windows
		if (Platform.OS_WIN32.equals(Platform.getOS())) {
			StringBuilder builder = new StringBuilder();
			builder.append("@echo off").append(WINDOWS_END_OF_LINE); // $NON-NLS-1$

			// 65001 - UTF-8
			builder.append("chcp 65001").append(WINDOWS_END_OF_LINE); // $NON-NLS-1$

			builder.append("cls").append(WINDOWS_END_OF_LINE); //$NON-NLS-1$
			builder.append(escapePath(cmd.getExecutable())).append(' ');
			for (String arg : cmd.getArguments()) {
				builder.append(arg).append(' ');
			}

			File file = File.createTempFile("composer_windows_", ".bat"); // $NON-NLS-1$ //$NON-NLS-2$
			file.deleteOnExit();
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(file));) {
				writer.write(builder.toString());
			} catch (FileNotFoundException ex) {
				ComposerPlugin.logException(ex);
			}

			properties.put(ITerminalsConnectorConstants.PROP_PROCESS_PATH, "cmd"); //$NON-NLS-1$
			String args = "/C " + file.getAbsolutePath(); //$NON-NLS-1$
			properties.put(ITerminalsConnectorConstants.PROP_PROCESS_ARGS, args);
		} else {
			properties.put(ITerminalsConnectorConstants.PROP_PROCESS_PATH, escapePath(cmd.getExecutable()));

			StringBuilder builder = new StringBuilder();
			for (String arg : cmd.getArguments()) {
				builder.append(arg + ' ');
			}
			properties.put(ITerminalsConnectorConstants.PROP_PROCESS_ARGS, builder.toString());
		}

		Logger.debug("Setting executor working directory to " + project.getLocation().toOSString()); //$NON-NLS-1$
		properties.put(ITerminalsConnectorConstants.PROP_PROCESS_WORKING_DIR, project.getLocation().toOSString());

		String title = MessageFormat.format(Messages.ComposerConsoleManager_ConsoleLabel,
				Messages.ComposerConsoleManager_ConsoleName, project.getName());
		IConsoleManager consoleManager = ConsolePlugin.getDefault().getConsoleManager();

		final CountDownLatch latch = new CountDownLatch(1);
		terminalConsole = new TerminalConsole(title, 0, properties, new Done() {

			@Override
			public void done(IStatus status) {
				latch.countDown();
			}
		});

		for (ExecutionResponseListener handler : getListeners()) {
			handler.executionStarted();
		}

		consoleManager.addConsoles(new IConsole[] { terminalConsole });
		consoleManager.showConsoleView(terminalConsole);

		latch.await();

		for (ExecutionResponseListener handler : getListeners()) {
			handler.executionFinished("", 0); //$NON-NLS-1$
		}
	}

	@Override
	public void abort() {
		terminalConsole.getTerminalConnector().disconnect();
	}

	private String escapePath(String path) {
		return '\"' + path.replace("\\", "\\\\") + '\"'; //$NON-NLS-1$ //$NON-NLS-2$
	}

}
