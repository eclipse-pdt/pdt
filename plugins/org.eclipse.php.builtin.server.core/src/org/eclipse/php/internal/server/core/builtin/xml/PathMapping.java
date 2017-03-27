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

public class PathMapping extends XMLElement {

	public String getModule() {
		return getAttributeValue("module"); //$NON-NLS-1$
	}

	public String getLocalPath() {
		return getAttributeValue("local"); //$NON-NLS-1$
	}

	public String getRemotePath() {
		return getAttributeValue("remote"); //$NON-NLS-1$
	}

	public void setModule(String module) {
		setAttributeValue("module", module); //$NON-NLS-1$
	}

	public void setLocalPath(String path) {
		setAttributeValue("local", path); //$NON-NLS-1$
	}

	public void setRemotePath(String path) {
		setAttributeValue("remote", path); //$NON-NLS-1$
	}

}
