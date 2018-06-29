/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
/*
 * HeaderOutputNotification.java
 *
 */

package org.eclipse.php.internal.debug.core.zend.debugger.messages;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.eclipse.php.debug.core.debugger.messages.IDebugNotificationMessage;
import org.eclipse.php.internal.debug.core.zend.communication.CommunicationUtilities;

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

	@Override
	public void deserialize(DataInputStream in) throws IOException {
		setOutput(CommunicationUtilities.readString(in));
	}

	@Override
	public int getType() {
		return 2008;
	}

	@Override
	public void serialize(DataOutputStream out) throws IOException {
		out.writeShort(getType());
		CommunicationUtilities.writeString(out, getOutput());
	}
}