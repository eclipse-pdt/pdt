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
 * GetStackVariableValueResponse.java
 *
 * Created on 29 אפרי? 2001, 17:27
 */

package org.eclipse.php.debug.core.debugger.messages;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.eclipse.php.debug.core.communication.CommunicationUtilities;

/**
 * @author guy
 */
public class GetStackVariableValueResponse extends DebugMessageResponseImpl implements IDebugResponseMessage {

	private String varResult;

	/**
	 * Sets the DefaultExpression result.
	 */
	public void setVarResult(String varResult) {
		this.varResult = varResult;
	}

	/**
	 * Returns the DefaultExpression result.
	 */
	public String getVarResult() {
		return varResult;
	}

	public void deserialize(DataInputStream in) throws IOException {
		setID(in.readInt());
		setStatus(in.readInt());
		setVarResult(CommunicationUtilities.readString(in));
	}

	public int getType() {
		return 1035;
	}

	public void serialize(DataOutputStream out) throws IOException {
		out.writeShort(getType());
		out.writeInt(getID());
		out.writeInt(getStatus());
		CommunicationUtilities.writeString(out, getVarResult());
	}
}