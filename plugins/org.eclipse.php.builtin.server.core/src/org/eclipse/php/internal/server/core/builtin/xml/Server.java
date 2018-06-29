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

public class Server extends XMLElement {

	public Port getPort(int index) {
		return (Port) findElement("Port", index); //$NON-NLS-1$
	}

	public int getPortCount() {
		return sizeOfElement("Port"); //$NON-NLS-1$
	}

	public int getPathMappingCount() {
		return sizeOfElement("PathMapping"); //$NON-NLS-1$
	}

	public PathMapping getPathMapping(int index) {
		return (PathMapping) findElement("PathMapping", index); //$NON-NLS-1$
	}

}
