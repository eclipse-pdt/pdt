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

public interface IPHPServerWorkingCopy extends IPHPServer {

	/**
	 * Sets the instance directory for the server. If set to null, the instance
	 * directory is derived from the testEnvironment setting.'
	 * 
	 * @param documentRootDir
	 *            absolule path to the document root directory.
	 */
	public void setDocumentRootDirectory(String documentRootDir);

}
