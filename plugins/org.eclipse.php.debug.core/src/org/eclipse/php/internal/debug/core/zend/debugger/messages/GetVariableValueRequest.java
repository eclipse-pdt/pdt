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
 * GetVariableValueRequest.java
 *
 */

package org.eclipse.php.internal.debug.core.zend.debugger.messages;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.eclipse.php.debug.core.debugger.messages.IDebugRequestMessage;
import org.eclipse.php.internal.debug.core.zend.communication.CommunicationUtilities;

/**
 * @author guy
 */
public class GetVariableValueRequest extends DebugMessageRequestImpl implements
		IDebugRequestMessage {

	private String var;
	private int depth;
	private String[] path;

	/**
	 * Sets the variable.
	 */
	public void setVar(String var) {
		this.var = var;
	}

	/**
	 * Returns the variable.
	 */
	public String getVar() {
		return var;
	}

	/**
	 * Sets the depth.
	 */
	public void setDepth(int depth) {
		this.depth = depth;
	}

	/**
	 * Returns the depth.
	 */
	public int getDepth() {
		return depth;
	}

	/**
	 * Sets the pet of the expression.
	 */
	public void setPath(String[] path) {
		if (path == null) {
			this.path = new String[0];
			return;
		}
		this.path = new String[path.length];
		System.arraycopy(path, 0, this.path, 0, path.length);
	}

	/**
	 * Returns the path.
	 */
	public String[] getPath() {
		return path;
	}

	public void deserialize(DataInputStream in) throws IOException {
		setID(in.readInt());
		setVar(CommunicationUtilities.readEncodedString(in,
				getTransferEncoding()));
		setDepth(in.readInt());
		int pathSize = in.readInt();
		if (pathSize > 0) {
			String[] path = new String[pathSize];
			for (int i = 0; i < pathSize; i++) {
				path[i] = CommunicationUtilities.readString(in);
			}
			setPath(path);
		}
	}

	public int getType() {
		return 32;
	}

	public void serialize(DataOutputStream out) throws IOException {
		out.writeShort(getType());
		out.writeInt(getID());
		CommunicationUtilities.writeEncodedString(out, getVar(),
				getTransferEncoding());
		out.writeInt(getDepth());
		String[] path = getPath();
		out.writeInt(path.length);
		for (int i = 0; i < path.length; i++) {
			CommunicationUtilities.writeString(out, path[i]);
		}
	}
}