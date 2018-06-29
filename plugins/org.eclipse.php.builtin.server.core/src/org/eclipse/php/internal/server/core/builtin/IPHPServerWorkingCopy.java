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
