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

import org.eclipse.core.resources.IProject;

/**
 * Interface for retrieving the proper {@link Environment} to lauch a script for
 * a project.
 * 
 * Use {@link AbstractEnvironmentFactory} for an abstract implementation.
 * 
 */
public interface EnvironmentFactory {

	public Environment getEnvironment(IProject project);
}
