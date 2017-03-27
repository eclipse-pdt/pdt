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
package org.eclipse.php.internal.server.core.builtin;

import java.util.List;

import org.eclipse.wst.server.core.ServerPort;

/**
 * 
 */
public interface IPHPServerConfiguration {

	/**
	 * Returns a ServerPort that this configuration uses.
	 *
	 * @return the server ports
	 */
	public List<ServerPort> getServerPorts();

	/**
	 * Return a list of the web modules in this server.
	 * 
	 * @return the web modules
	 */
	public List<WebModule> getWebModules();

}