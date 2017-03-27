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
