/*******************************************************************************
 * Copyright (c) 2015 Zend Technologies and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.zend.communication;

/**
 * Used for handling file content response that was called on-demand.
 */
public interface IRemoteFileContentRequestor {

	/**
	 * This method is called when file content requested by this class is received
	 * 
	 * @param content
	 *            File content in bytes
	 * @param fileName
	 *            Requested file name
	 * @param lineNumber
	 *            Requested line number
	 */
	public void fileContentReceived(byte[] content, String serverAddress, String originalURL, String fileName,
			int lineNumber);

	/**
	 * This method is called when the HTTP request to the debug server is finished.
	 * 
	 * @param e
	 *            Exception if occurred or <code>null</code> in case of success
	 */
	public void requestCompleted(Exception e);
}
