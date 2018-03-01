/*******************************************************************************
 * Copyright (c) 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.server.ui.launching;

import java.text.MessageFormat;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.window.Window;
import org.eclipse.php.debug.ui.DebugServerConnectionTestRegistry;
import org.eclipse.php.debug.ui.IDebugServerConnectionTest;
import org.eclipse.php.internal.debug.core.preferences.PHPDebuggersRegistry;
import org.eclipse.php.internal.debug.ui.launching.AbstractPHPLaunchConfigurationDebuggerTab;
import org.eclipse.php.internal.debug.ui.wizards.DebuggerCompositeFragment;
import org.eclipse.php.internal.server.core.Server;
import org.eclipse.php.internal.server.core.manager.ServersManager;
import org.eclipse.php.internal.server.ui.ServerEditWizardRunner;

/**
 * Debugger settings tab for PHP web launch configurations.
 * 
 * @author Bartlomiej Laczkowski
 */
public class PHPWebPageLaunchConfigurationDebuggerTab extends AbstractPHPLaunchConfigurationDebuggerTab {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.ui.launching.
	 * AbstractPHPLaunchConfigurationDebuggerTab#handleConfigureDebugger()
	 */
	@Override
	protected void handleConfigureDebugger() {
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
	 * @see org.eclipse.php.internal.debug.ui.launching.
	 * AbstractPHPLaunchConfigurationDebuggerTab#getCurrentDebuggerId()
	 */
	@Override
	protected String getCurrentDebuggerId() {
		Server server = getServer();
		if (server == null) {
			return PHPDebuggersRegistry.NONE_DEBUGGER_ID;
		}
		return server.getDebuggerId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.ui.launching.
	 * AbstractPHPLaunchConfigurationDebuggerTab#getNoDebuggerMessage()
	 */
	@Override
	protected String getNoDebuggerMessage() {
		Server server = getServer();
		return MessageFormat.format(
				Messages.PHPWebPageLaunchConfigurationDebuggerTab_No_debugger_is_attached_to_server_configuration,
				server != null ? server.getName() : ""); //$NON-NLS-1$
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.ui.launching.
	 * AbstractPHPLaunchConfigurationDebuggerTab#updateDebugTest()
	 */
	@Override
	protected void updateDebugTest() {
		debugTesters = DebugServerConnectionTestRegistry.getTests(getCurrentDebuggerId());
		if (debugTesters.length == 0) {
			validateDebuggerBtn.setEnabled(false);
		} else {
			validateDebuggerBtn.setEnabled(true);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.ui.launching.
	 * AbstractPHPLaunchConfigurationDebuggerTab#performDebugTest()
	 */
	@Override
	protected void performDebugTest() {
		for (IDebugServerConnectionTest debugServerTester : debugTesters) {
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
