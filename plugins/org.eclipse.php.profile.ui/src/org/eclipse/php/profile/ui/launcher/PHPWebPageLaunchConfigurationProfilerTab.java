/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.profile.ui.launcher;

import java.text.MessageFormat;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.window.Window;
import org.eclipse.php.debug.ui.DebugServerConnectionTestRegistry;
import org.eclipse.php.debug.ui.IDebugServerConnectionTest;
import org.eclipse.php.internal.debug.core.preferences.PHPDebuggersRegistry;
import org.eclipse.php.internal.debug.ui.wizards.DebuggerCompositeFragment;
import org.eclipse.php.internal.server.core.Server;
import org.eclipse.php.internal.server.core.manager.ServersManager;
import org.eclipse.php.internal.server.ui.ServerEditWizardRunner;

/**
 * Debugger settings tab for PHP web launch configurations.
 * 
 * @author Bartlomiej Laczkowski
 */
public class PHPWebPageLaunchConfigurationProfilerTab extends AbstractPHPLaunchConfigurationProfilerTab {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.profile.ui.launcher.
	 * AbstractPHPLaunchConfigurationProfilerTab#handleConfigureProfiler()
	 */
	@Override
	protected void handleConfigureProfiler() {
		Server server = getServer();
		if (server == null || ServersManager.isNoneServer(server)) {
			return;
		}
		NullProgressMonitor monitor = new NullProgressMonitor();
		if (ServerEditWizardRunner.runWizard(server, DebuggerCompositeFragment.ID) == Window.CANCEL) {
			monitor.setCanceled(true);
			return;
		}
		ServersManager.save();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.profile.ui.launcher.
	 * AbstractPHPLaunchConfigurationProfilerTab#getCurrentProfilerId()
	 */
	@Override
	protected String getCurrentProfilerId() {
		Server server = getServer();
		if (server == null) {
			return PHPDebuggersRegistry.NONE_DEBUGGER_ID;
		}
		return server.getDebuggerId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.profile.ui.launcher.
	 * AbstractPHPLaunchConfigurationProfilerTab#getNoProfilerMessage()
	 */
	@Override
	protected String getNoProfilerMessage() {
		Server server = getServer();
		return MessageFormat.format(Messages.PHPWebPageLaunchConfigurationProfilerTab_No_profiler_is_attached,
				server != null ? server.getName() : ""); //$NON-NLS-1$
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.profile.ui.launcher.
	 * AbstractPHPLaunchConfigurationProfilerTab#updateProfileTest()
	 */
	@Override
	protected void updateProfileTest() {
		profileTesters = DebugServerConnectionTestRegistry.getTests(getCurrentProfilerId());
		if (profileTesters.length == 0) {
			validateProfilerBtn.setEnabled(false);
		} else {
			validateProfilerBtn.setEnabled(true);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.ui.launching.
	 * AbstractPHPLaunchConfigurationDebuggerTab#performDebugTest()
	 */
	@Override
	protected void performProfileTest() {
		for (IDebugServerConnectionTest debugServerTester : profileTesters) {
			debugServerTester.testConnection(getServer(), getShell());
		}
	}

	private Server getServer() {
		try {
			String serverName = getConfiguration().getAttribute(Server.NAME, ""); //$NON-NLS-1$
			Server server = ServersManager.getServer(serverName);
			return server;
		} catch (CoreException e) {
			// Should not happen
		}
		return null;
	}

}
