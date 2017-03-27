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
package org.eclipse.php.internal.server.core.builtin.xml;

public class PathMapping extends XMLElement {

	public String getModule() {
		return getAttributeValue("module");
	}

	public String getLocalPath() {
		return getAttributeValue("local");
	}

	public String getRemotePath() {
		return getAttributeValue("remote");
	}

	public void setModule(String module) {
		setAttributeValue("module", module);
	}

	public void setLocalPath(String path) {
		setAttributeValue("local", path);
	}

	public void setRemotePath(String path) {
		setAttributeValue("remote", path);
	}

}
