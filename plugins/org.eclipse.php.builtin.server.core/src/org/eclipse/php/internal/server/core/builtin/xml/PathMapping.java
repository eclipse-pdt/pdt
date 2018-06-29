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
