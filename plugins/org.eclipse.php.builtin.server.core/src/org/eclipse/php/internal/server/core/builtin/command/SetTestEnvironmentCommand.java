/*******************************************************************************
 * Copyright (c) 2017 Alex Xu and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Alex Xu - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.server.core.builtin.command;

import org.eclipse.php.internal.server.core.builtin.IPHPServerWorkingCopy;
import org.eclipse.php.internal.server.core.builtin.Messages;

/**
 * Command to change the server test mode. The server instance directory is
 * cleared in conjunction with this command for legacy support.
 */
public class SetTestEnvironmentCommand extends ServerCommand {
	protected String oldInstanceDir;

	/**
	 * SetTestEnvironmentCommand constructor comment.
	 * 
	 * @param server
	 *            a PHP server
	 * @param te
	 *            <code>true</code> for a test environment.
	 */
	public SetTestEnvironmentCommand(IPHPServerWorkingCopy server) {
		super(server, Messages.serverEditorActionSetServerDirectory);
	}

	/**
	 * Execute the command.
	 */
	@Override
	public void execute() {
		// save old instance directory
		oldInstanceDir = server.getDocumentRootDirectory();
		// ensure instance directory is cleared
		server.setDocumentRootDirectory(null);
	}

	/**
	 * Undo the command.
	 */
	@Override
	public void undo() {
		server.setDocumentRootDirectory(oldInstanceDir);
	}
}
