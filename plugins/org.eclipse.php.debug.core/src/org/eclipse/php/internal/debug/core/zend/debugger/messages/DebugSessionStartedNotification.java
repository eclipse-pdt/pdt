/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
/*
 * StartDebugNotification.java
 *
 */

package org.eclipse.php.internal.debug.core.zend.debugger.messages;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URLDecoder;

import org.eclipse.php.debug.core.debugger.messages.IDebugNotificationMessage;
import org.eclipse.php.internal.debug.core.zend.communication.CommunicationUtilities;

/**
 * @author guy
 */
public class DebugSessionStartedNotification extends
		DebugMessageNotificationImpl implements IDebugNotificationMessage {

	private String fileName = ""; //$NON-NLS-1$
	private String uri = ""; //$NON-NLS-1$
	private String query = ""; //$NON-NLS-1$
	private String additionalOptions = ""; //$NON-NLS-1$
	private int protocolID;

	/**
	 * Sets the file name.
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * Returns the file name.
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * Sets the uri.
	 */
	public void setUri(String uri) {
		this.uri = uri;
	}

	/**
	 * Returns the uri.
	 */
	public String getUri() {
		return uri;
	}

	/**
	 * Returns the query - the query as requsted from the ZDE
	 */
	public String getQuery() {
		return query;
	}

	/**
	 * Sets the query - the query as requsted from the ZDE
	 */
	public void setQuery(String query) {
		this.query = query;
	}

	/**
	 * Returns the additional options
	 */
	public String getOptions() {
		return additionalOptions;
	}

	/**
	 * Sets the options
	 */
	public void setOptions(String options) {
		this.additionalOptions = options;
	}

	/**
	 * Stets the server protocol id.
	 */
	public void setServerProtocol(int serverProtocolID) {
		this.protocolID = serverProtocolID;
	}

	/**
	 * Returns the server protocol ID.
	 */
	public int getServerProtocolID() {
		return protocolID;
	}

	public void deserialize(DataInputStream in) throws IOException {
		setServerProtocol(in.readInt()); // read the protocal id (For future
											// use);
		setFileName(CommunicationUtilities.readString(in));
		setUri(CommunicationUtilities.readString(in));
		setQuery(URLDecoder.decode(CommunicationUtilities.readString(in),
				"UTF-8")); //$NON-NLS-1$
		setOptions(CommunicationUtilities.readString(in));
	}

	public int getType() {
		return 2005;
	}

	public void serialize(DataOutputStream out) throws IOException {
		out.writeShort(getType());
		CommunicationUtilities.writeString(out, getFileName());
		CommunicationUtilities.writeString(out, getUri());
	}
}