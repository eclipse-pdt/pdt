/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.launching;

import org.eclipse.core.resources.IProject;
import org.eclipse.debug.core.ILaunch;

/**
 * PHPServerLaunchDecorator has extended functionalities that can return the IProject
 * that is related to the ILaunch.
 */
public class PHPServerLaunchDecorator extends PHPLaunchProxy {

	private IProject project;

	/**
	 * Constructs a new PHPServerLaunchDecorator.
	 * 
	 * @param launch
	 * @param serverBehaviour
	 * @param project
	 */
	public PHPServerLaunchDecorator(ILaunch launch, IProject project) {
		super(launch);
		this.project = project;
	}

	public IProject getProject() {
		return project;
	}

	public boolean equals(Object obj) {
		if (obj instanceof PHPServerLaunchDecorator) {
			obj = ((PHPServerLaunchDecorator) obj).launch;
		}
		return launch.equals(obj);
	}

	public int hashCode() {
		return launch.hashCode();
	}
}
