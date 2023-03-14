/*******************************************************************************
 * Copyright (c) 2012, 2016 PDT Extension Group and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.core.launch.environment;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.php.composer.core.launch.ScriptNotFoundException;

/**
 * Interface for the Environment in which a php script should be executed.
 * 
 */
public interface Environment {

	public boolean isAvailable();

	public void setUp(IProject project) throws ScriptNotFoundException;

	public ProcessBuilder getCommand() throws CoreException;
}
