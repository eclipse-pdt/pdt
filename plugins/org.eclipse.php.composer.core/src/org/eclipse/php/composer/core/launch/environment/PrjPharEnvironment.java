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
import org.eclipse.core.resources.IResource;
import org.eclipse.php.composer.core.launch.ScriptNotFoundException;

/**
 * 
 * An abstract environment for a user selected PHP executable but a project
 * specific script to be launched.
 * 
 * Implement getScript to pass your script to the launcher.
 * 
 */
public abstract class PrjPharEnvironment implements Environment {

	protected String phar;

	public void setUp(IProject project) throws ScriptNotFoundException {
		IResource phar = getScript(project);
		if (phar == null) {
			throw new ScriptNotFoundException("No script found in project " + project.getName());
		}

		if (phar.getFullPath().segmentCount() != 2) {
			throw new ScriptNotFoundException(
					"The script file in project " + project.getName() + " is in the wrong location.");
		}

		this.phar = phar.getFullPath().removeFirstSegments(1).toOSString();
	}

	protected abstract IResource getScript(IProject project);

}
