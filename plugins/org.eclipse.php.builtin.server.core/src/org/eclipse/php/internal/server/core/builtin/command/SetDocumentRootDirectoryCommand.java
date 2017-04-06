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
 * Command to change the deploy directory
 */
public class SetDocumentRootDirectoryCommand extends ServerCommand {
	protected String instanceDir;
	protected String oldInstanceDir;

	/**
	 * Constructs command to set the instance directory. Setting the instance
	 * directory also sets testEnvironment true;
	 * 
	 * @param server
	 *            a PHP server
	 * @param instanceDir
	 *            instance directory to set
	 */
	public SetDocumentRootDirectoryCommand(IPHPServerWorkingCopy server, String instanceDir) {
		super(server, Messages.serverEditorActionSetServerDirectory);
		this.instanceDir = instanceDir;
	}

	/**
	 * Execute setting the deploy directory
	 */
	@Override
	public void execute() {
		oldInstanceDir = server.getDocumentRootDirectory();
		server.setDocumentRootDirectory(instanceDir);
	}

	/**
	 * Restore prior deploy directory
	 */
	@Override
	public void undo() {
		server.setDocumentRootDirectory(oldInstanceDir);
	}
}
