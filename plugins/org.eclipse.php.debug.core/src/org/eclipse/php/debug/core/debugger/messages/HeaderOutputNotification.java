/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
/*
 * HeaderOutputNotification.java
 *
 */

package org.eclipse.php.debug.core.debugger.messages;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.eclipse.php.debug.core.communication.CommunicationUtilities;

/**
 * @author guy
 */
public class HeaderOutputNotification extends DebugMessageNotificationImpl implements IDebugNotificationMessage {

	private String output;

	public String getOutput() {
		return this.output;
	}

	public void setOutput(String outputText) {
		this.output = outputText;
	}

	public String toString() {
		return super.toString() + ":" + output;
	}

	public void deserialize(DataInputStream in) throws IOException {
		setOutput(CommunicationUtilities.readString(in));
	}

	public int getType() {
		return 2008;
	}

	public void serialize(DataOutputStream out) throws IOException {
		out.writeShort(getType());
		CommunicationUtilities.writeString(out, getOutput());
	}
}