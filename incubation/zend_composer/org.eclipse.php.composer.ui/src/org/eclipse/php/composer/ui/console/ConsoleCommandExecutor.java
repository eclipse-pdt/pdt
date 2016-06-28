/*******************************************************************************
 * Copyright (c) 2016 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.ui.console;

import java.io.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.php.composer.core.ComposerPreferences;
import org.eclipse.php.composer.core.DefaultCommandExecutor;
import org.eclipse.php.composer.core.ICommandExecutor;
import org.eclipse.php.composer.core.utils.EnvironmentUtils;
import org.eclipse.php.composer.internal.ui.ComposerUIPlugin;
import org.eclipse.tm.terminal.view.core.interfaces.ITerminalService.Done;
import org.eclipse.tm.terminal.view.core.interfaces.constants.ITerminalsConnectorConstants;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;

public class ConsoleCommandExecutor extends DefaultCommandExecutor implements ICommandExecutor {

	private final static String WINDOWS_END_OF_LINE = "\r\n"; //$NON-NLS-1$

	@Override
	public int run(IProgressMonitor monitor) throws IOException {
		configureProxy();

		Map<String, Object> properties = new HashMap<String, Object>();

		Map<String, String> envMap = getEnvTokens();
		List<String> envs = new ArrayList<>(envMap.size());
		for (String key : envMap.keySet()) {
			envs.add(key + '=' + envMap.get(key));
		}
		properties.put(ITerminalsConnectorConstants.PROP_PROCESS_ENVIRONMENT, envs.toArray(new String[envs.size()]));
		properties.put(ITerminalsConnectorConstants.PROP_PROCESS_MERGE_ENVIRONMENT, true);

		if (EnvironmentUtils.isWindows()) {
			StringBuilder builder = new StringBuilder();
			builder.append("@echo off").append(WINDOWS_END_OF_LINE); //$NON-NLS-1$
			// 65001 - UTF-8
			builder.append("chcp 65001").append(WINDOWS_END_OF_LINE); //$NON-NLS-1$
			builder.append("cls").append(WINDOWS_END_OF_LINE); //$NON-NLS-1$
			for (int i = 0; i < getCommand().length; i++) {
				builder.append(escapePath(getCommand()[i])).append(' ');
			}

			File file = File.createTempFile("composer_windows_", ".bat"); //$NON-NLS-1$ //$NON-NLS-2$
			file.deleteOnExit();
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(file));) {
				writer.write(builder.toString());
			} catch (FileNotFoundException ex) {
				ComposerUIPlugin.logError(ex);
			}

			properties.put(ITerminalsConnectorConstants.PROP_PROCESS_PATH, "cmd"); //$NON-NLS-1$
			String args = "/C " + file.getAbsolutePath(); //$NON-NLS-1$
			properties.put(ITerminalsConnectorConstants.PROP_PROCESS_ARGS, args);
		} else {
			properties.put(ITerminalsConnectorConstants.PROP_PROCESS_PATH, escapePath(getCommand()[0]));
			String args = ""; //$NON-NLS-1$
			for (int i = 1; i < getCommand().length; i++) {
				args += escapePath(getCommand()[i]) + ' ';
			}
			properties.put(ITerminalsConnectorConstants.PROP_PROCESS_ARGS, args);
		}

		properties.put(ITerminalsConnectorConstants.PROP_PROCESS_WORKING_DIR, workingDirectory);

		String exeName = ComposerPreferences.get(ComposerPreferences.PHP_EXEC_NODE);
		String title = MessageFormat.format(Messages.ComposerConsoleManager_ConsoleLabel,
				Messages.ComposerConsoleManager_ConsoleName, exeName);

		final AtomicBoolean closed = new AtomicBoolean(false);

		IConsoleManager consoleManager = ConsolePlugin.getDefault().getConsoleManager();
		TerminalConsole terminalConsole = new TerminalConsole(title, 0, properties, new Done() {

			@Override
			public void done(IStatus status) {
				closed.set(true);
			}
		});
		consoleManager.addConsoles(new IConsole[] { terminalConsole });
		consoleManager.showConsoleView(terminalConsole);

		while (!closed.get()) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				ComposerUIPlugin.logError(e);
			}
		}
		return 0;
	}

	public String escapePath(String path) {
		return '\"' + path.replace("\\", "\\\\") + '\"'; //$NON-NLS-1$ //$NON-NLS-2$
	}

}
