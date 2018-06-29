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
