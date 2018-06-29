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
package org.eclipse.php.internal.server.core.builtin.xml;

public class Port extends XMLElement {

	public String getName() {
		return getAttributeValue("name"); //$NON-NLS-1$
	}

	public String getProtocol() {
		return getAttributeValue("protocol"); //$NON-NLS-1$
	}

	public int getPort() {
		int port = -1;
		try {
			port = Integer.parseInt(getElementValue());
		} catch (Exception e) {
			// ignore
		}
		return port;
	}

	public void setPort(int port) {
		setElementValue(getElementNode(), String.valueOf(port));
	}

}
