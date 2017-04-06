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

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.AbstractOperation;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.php.internal.server.core.builtin.IPHPServerConfigurationWorkingCopy;

/**
 * Configuration command.
 */
public abstract class ConfigurationCommand extends AbstractOperation {
	protected IPHPServerConfigurationWorkingCopy configuration;

	/**
	 * ConfigurationCommand constructor comment.
	 * 
	 * @param configuration
	 *            a PHP Server configuration
	 * @param label
	 *            a label
	 */
	public ConfigurationCommand(IPHPServerConfigurationWorkingCopy configuration, String label) {
		super(label);
		this.configuration = configuration;
	}

	@Override
	public IStatus redo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		return execute(monitor, info);
	}

	public abstract void execute();

	@Override
	public IStatus execute(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		execute();
		return null;
	}

	public abstract void undo();

	@Override
	public IStatus undo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		undo();
		return null;
	}
}