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

public interface IPHPServerConfigurationWorkingCopy extends IPHPServerConfiguration {

	/**
	 * Modify the port with the given id.
	 *
	 * @param id
	 *            java.lang.String
	 * @param port
	 *            int
	 */
	public void modifyServerPort(String id, int port);

}