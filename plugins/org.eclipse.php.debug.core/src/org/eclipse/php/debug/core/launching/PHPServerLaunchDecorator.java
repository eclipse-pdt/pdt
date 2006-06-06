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
package org.eclipse.php.debug.core.launching;

import org.eclipse.core.resources.IProject;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.php.server.apache.core.ApacheServerBehaviour;

/**
 * PHPServerLaunchDecorator has extended functionalities that can return the ApacheServerBehaviour and the IProject
 * that are related to the ILaunch.
 */
public class PHPServerLaunchDecorator extends PHPLaunchProxy {

	private ApacheServerBehaviour serverBehaviour;
	private IProject project;

	/**
	 * Constructs a new PHPServerLaunchDecorator.
	 * 
	 * @param launch
	 * @param serverBehaviour
	 * @param project
	 */
	public PHPServerLaunchDecorator(ILaunch launch, ApacheServerBehaviour serverBehaviour, IProject project) {
		super(launch);
		this.serverBehaviour = serverBehaviour;
		this.project = project;
	}

	public ApacheServerBehaviour getApacheServerBahavior() {
		return serverBehaviour;
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
