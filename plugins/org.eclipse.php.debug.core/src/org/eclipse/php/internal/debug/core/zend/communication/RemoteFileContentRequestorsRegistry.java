/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.zend.communication;

import java.util.*;

/**
 * Maps remote file content requestors to the requested file and line number.
 */
public class RemoteFileContentRequestorsRegistry {

	private static RemoteFileContentRequestorsRegistry instance;
	private Map<String, IRemoteFileContentRequestor> requestors;
	private List<IRemoteFileContentRequestor> externalRequestHandlers;

	public RemoteFileContentRequestorsRegistry() {
		requestors = Collections.synchronizedMap(new HashMap<String, IRemoteFileContentRequestor>());
		externalRequestHandlers = Collections.synchronizedList(new ArrayList<IRemoteFileContentRequestor>());
	}

	/**
	 * Returns instance of this registry
	 * 
	 * @return instance
	 */
	public static synchronized RemoteFileContentRequestorsRegistry getInstance() {
		if (instance == null) {
			instance = new RemoteFileContentRequestorsRegistry();
		}
		return instance;
	}

	/**
	 * Add new requester for file contents
	 * 
	 * @param requestor
	 *            File contents requester
	 * @param fileName
	 *            File name
	 * @param lineNumber
	 *            Line number
	 */
	public void addRequestor(IRemoteFileContentRequestor requestor, String fileName, int lineNumber) {
		requestors.put(fileName + ":" + lineNumber, requestor); //$NON-NLS-1$
	}

	/**
	 * Adds new external request handler.
	 * 
	 * @param requestor
	 */
	public void addExternalRequestHandler(IRemoteFileContentRequestor requestor) {
		externalRequestHandlers.add(requestor);
	}

	/**
	 * Return requester for file contents, and remove it from the registry
	 * 
	 * @param fileName
	 *            File name
	 * @param lineNumber
	 *            Line number
	 * @return requester
	 */
	public IRemoteFileContentRequestor removeRequestor(String fileName, int lineNumber) {
		return requestors.remove(fileName + ":" + lineNumber); //$NON-NLS-1$
	}

	/**
	 * Removes new external request handler.
	 * 
	 * @param requestor
	 */
	public void removeExternalRequestHandler(IRemoteFileContentRequestor requestor) {
		externalRequestHandlers.remove(requestor);
	}

	/**
	 * Handles open remote file requests that comes outside of IDE.
	 * 
	 * @param content
	 * @param serverAddress
	 * @param originalURL
	 * @param fileName
	 * @param lineNumber
	 */
	public void handleExternalRequest(byte[] content, String serverAddress, String originalURL, String fileName,
			int lineNumber) {
		for (IRemoteFileContentRequestor requestor : externalRequestHandlers) {
			requestor.fileContentReceived(content, serverAddress, originalURL, fileName, lineNumber);
			requestor.requestCompleted(null);
		}

	}

}
