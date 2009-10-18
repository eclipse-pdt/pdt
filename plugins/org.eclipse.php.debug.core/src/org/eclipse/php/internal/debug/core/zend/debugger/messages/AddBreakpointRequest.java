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
package org.eclipse.php.internal.debug.core.zend.debugger.messages;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.eclipse.php.debug.core.debugger.messages.IDebugRequestMessage;
import org.eclipse.php.internal.debug.core.zend.communication.CommunicationUtilities;
import org.eclipse.php.internal.debug.core.zend.debugger.Breakpoint;

/**
 * Request to add a new break point.
 * 
 * @author guy
 */
public class AddBreakpointRequest extends DebugMessageRequestImpl implements
		IDebugRequestMessage {

	private Breakpoint breakPoint;

	/**
	 * Sets the break point.
	 */
	public void setBreakpoint(Breakpoint breakPoint) {
		this.breakPoint = breakPoint;
	}

	/**
	 * Returns the breakpoint.
	 */
	public Breakpoint getBreakpoint() {
		return breakPoint;
	}

	public int getType() {
		return 21;
	}

	public void deserialize(DataInputStream in) throws IOException {
		setID(in.readInt());
		setBreakpoint(CommunicationUtilities.readBreakpoint(in));
	}

	public void serialize(DataOutputStream out) throws IOException {
		out.writeShort(getType());
		out.writeInt(getID());
		CommunicationUtilities.writeBreakpoint(out, getBreakpoint());
	}
}