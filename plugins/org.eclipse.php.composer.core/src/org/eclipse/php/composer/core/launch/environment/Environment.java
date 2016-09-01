/*******************************************************************************
 * Copyright (c) 2012, 2016 PDT Extension Group and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.core.launch.environment;

import org.apache.commons.exec.CommandLine;
import org.eclipse.core.resources.IProject;
import org.eclipse.php.composer.core.launch.ScriptNotFoundException;

/**
 * Interface for the Environment in which a php script should be executed.
 * 
 */
public interface Environment {

	public boolean isAvailable();

	public void setUp(IProject project) throws ScriptNotFoundException;

	public CommandLine getCommand();
}
