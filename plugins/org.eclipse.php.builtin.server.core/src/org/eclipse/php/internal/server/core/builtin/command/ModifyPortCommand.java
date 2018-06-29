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
package org.eclipse.php.internal.server.core.builtin.command;

import java.util.Iterator;

import org.eclipse.php.internal.server.core.builtin.IPHPServerConfigurationWorkingCopy;
import org.eclipse.php.internal.server.core.builtin.Messages;
import org.eclipse.wst.server.core.ServerPort;

/**
 * Command to change the configuration port.
 */
public class ModifyPortCommand extends ConfigurationCommand {
	protected String id;
	protected int port;
	protected int oldPort;

	/**
	 * ModifyPortCommand constructor.
	 * 
	 * @param configuration
	 *            a PHP Server configuration
	 * @param id
	 *            a port id
	 * @param port
	 *            new port number
	 */
	public ModifyPortCommand(IPHPServerConfigurationWorkingCopy configuration, String id, int port) {
		super(configuration, Messages.configurationEditorActionModifyPort);
		this.id = id;
		this.port = port;
	}

	/**
	 * Execute the command.
	 */
	@Override
	public void execute() {
		// find old port number
		Iterator<ServerPort> iterator = configuration.getServerPorts().iterator();
		while (iterator.hasNext()) {
			ServerPort temp = iterator.next();
			if (id.equals(temp.getId())) {
				oldPort = temp.getPort();
			}
		}

		// make the change
		configuration.modifyServerPort(id, port);
	}

	/**
	 * Undo the command.
	 */
	@Override
	public void undo() {
		configuration.modifyServerPort(id, oldPort);
	}
}