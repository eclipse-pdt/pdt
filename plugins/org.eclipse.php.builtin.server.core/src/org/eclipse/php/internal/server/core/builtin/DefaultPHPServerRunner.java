/*******************************************************************************
 * Copyright (c) 2017 Alex Xu and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Alex Xu - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.server.core.builtin;

import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.osgi.util.NLS;
import org.eclipse.wst.server.core.util.SocketUtil;

public class DefaultPHPServerRunner extends AbstractPHPServerRunner {

	private int fPort;

	@Override
	public void run(PHPServerRunnerConfiguration configuration, ILaunch launch, IProgressMonitor monitor)
			throws CoreException {
		if (monitor == null) {
			monitor = new NullProgressMonitor();
		}

		IProgressMonitor subMonitor = SubMonitor.convert(monitor, 1);
		subMonitor.beginTask(Messages.DefaultPHPServerRunner_Launching_server____1, 2);
		subMonitor.subTask(Messages.DefaultPHPServerRunner_Constructing_command_line____2);

		fPort = configuration.getPort();

		String[] cmdLine = fetchCmdLineFromConf(configuration);

		subMonitor.worked(1);

		subMonitor.subTask(Messages.DefaultPHPServerRunner_Starting_server____3);
		// check for cancellation
		if (monitor.isCanceled()) {
			return;
		}
		String workingDirectory = configuration.getWorkingDirectory();
		Process p = exec(cmdLine, new File(configuration.getWorkingDirectory()));
		if (p == null) {
			return;
		}

		// check for cancellation
		if (monitor.isCanceled()) {
			p.destroy();
			return;
		}

		String timestamp = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM)
				.format(new Date(System.currentTimeMillis()));
		IProcess process = newProcess(launch, p, renderProcessLabel(cmdLine, timestamp), getDefaultProcessMap());
		process.setAttribute(DebugPlugin.ATTR_PATH, cmdLine[0]);
		process.setAttribute(IProcess.ATTR_CMDLINE, renderCommandLine(cmdLine));
		String ltime = launch.getAttribute(DebugPlugin.ATTR_LAUNCH_TIMESTAMP);
		process.setAttribute(DebugPlugin.ATTR_LAUNCH_TIMESTAMP, ltime != null ? ltime : timestamp);
		if (workingDirectory != null) {
			process.setAttribute(DebugPlugin.ATTR_WORKING_DIRECTORY, workingDirectory);
		}
		// if (envp != null) {
		// Arrays.sort(envp);
		// StringBuilder buff = new StringBuilder();
		// for (int i = 0; i < envp.length; i++) {
		// buff.append(envp[i]);
		// if (i < envp.length - 1) {
		// buff.append('\n');
		// }
		// }
		// process.setAttribute(DebugPlugin.ATTR_ENVIRONMENT, buff.toString());
		// }
		subMonitor.worked(1);
		subMonitor.done();
	}

	protected String[] fetchCmdLineFromConf(PHPServerRunnerConfiguration configuration) {
		String phpIniFile = configuration.getIniFilePath();
		String hostname = configuration.getHost();

		// workaround for bug 518158
		if (SocketUtil.isLocalhost(hostname)) {
			hostname = "127.0.0.1"; //$NON-NLS-1$
		}

		List<String> commands = new ArrayList<>();
		commands.add(configuration.getExeFilePath());
		commands.add("-S"); //$NON-NLS-1$
		commands.add(hostname + ':' + getServerPort());
		commands.add("-t"); //$NON-NLS-1$
		commands.add(configuration.getWorkingDirectory());
		if (StringUtils.isNotEmpty(phpIniFile)) {
			commands.add("-n"); //$NON-NLS-1$
			commands.add("-c"); //$NON-NLS-1$
			commands.add(phpIniFile);
		}
		return commands.toArray(new String[0]);
	}

	/**
	 * Returns the 'rendered' name for the specified command line
	 * 
	 * @param commandLine
	 *            the command line
	 * @param timestamp
	 *            the run-at time for the process
	 * @return the name for the process
	 */
	public static String renderProcessLabel(String[] commandLine, String timestamp) {
		String format = Messages.DefaultPHPServerRunner_0__0____1___2;
		return NLS.bind(format, new String[] { commandLine[0], timestamp });
	}

	@Override
	public void stop() {
	}

	@Override
	public int getServerPort() {
		return fPort;
	}

}
