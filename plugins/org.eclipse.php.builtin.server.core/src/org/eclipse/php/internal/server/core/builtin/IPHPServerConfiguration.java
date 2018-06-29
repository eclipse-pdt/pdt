/*******************************************************************************
 * Copyright (c) 2017 Alex Xu and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Alex Xu - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.server.core.builtin;

import java.util.List;

import org.eclipse.wst.server.core.ServerPort;

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
