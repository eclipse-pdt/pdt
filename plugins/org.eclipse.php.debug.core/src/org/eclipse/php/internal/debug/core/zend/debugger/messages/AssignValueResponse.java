/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.zend.debugger.messages;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.eclipse.php.debug.core.debugger.messages.IDebugResponseMessage;

public class AssignValueResponse extends DebugMessageResponseImpl implements IDebugResponseMessage {

	private String variableValue = null;

	/**
	 * Sets the DefaultExpression result.
	 */
	public void setVarResult(String varResult) {
		variableValue = varResult;
	}

	/**
	 * Returns the DefaultExpression result.
	 */
	public String getVarResult() {
		return variableValue;
	}

	public void deserialize(DataInputStream in) throws IOException {
		setID(in.readInt());
		setStatus(in.readInt());
	}

	public int getType() {
		return 1033;
	}

	public void serialize(DataOutputStream out) throws IOException {
		out.writeShort(getType());
		out.writeInt(getID());
		out.writeInt(getStatus());
	}
}
