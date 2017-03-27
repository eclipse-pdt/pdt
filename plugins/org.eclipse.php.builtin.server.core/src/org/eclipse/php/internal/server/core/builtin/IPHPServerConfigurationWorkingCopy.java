/*******************************************************************************
 * Copyright (c) 2017 Alex Xu and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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