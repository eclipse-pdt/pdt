/*******************************************************************************
 * Copyright (c) 2009, 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.server.core.builtin.command;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.AbstractOperation;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.php.internal.server.core.builtin.IPHPServerWorkingCopy;
import org.eclipse.php.internal.server.core.builtin.PHPServer;

/**
 * A command on a PHP server.
 */
public abstract class ServerCommand extends AbstractOperation {

	protected PHPServer server;

	/**
	 * ServerCommand constructor comment.
	 * 
	 * @param server
	 *            a PHP server
	 * @param label
	 *            a label
	 */
	public ServerCommand(IPHPServerWorkingCopy server, String label) {
		super(label);
		this.server = (PHPServer) server;
	}

	public IStatus redo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		return execute(monitor, info);
	}

	public abstract void execute();

	public IStatus execute(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		execute();
		return null;
	}

	public abstract void undo();

	public IStatus undo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		undo();
		return null;
	}
}