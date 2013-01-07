/*******************************************************************************
 * Copyright (c) 2012 Zend Technologies Ltd. 
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0 
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/epl-v10.html  
 * 
 * Contributors:
 *      Zend Technologies - initial API and implementation
 *******************************************************************************/

package org.eclipse.php.internal.debug.core.zend.debugger.messages;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.eclipse.php.debug.core.debugger.messages.IDebugRequestMessage;
import org.eclipse.php.internal.debug.core.zend.communication.CommunicationUtilities;

public class AddFilesRequest extends DebugMessageRequestImpl implements
		IDebugRequestMessage {

	private int counter;
	private String[] paths;

	public void setPaths(String[] paths) {
		this.paths = new String[paths.length];
		System.arraycopy(paths, 0, this.paths, 0, paths.length);
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	public String[] getPaths() {
		return paths;
	}

	public int getCounter() {
		return counter;
	}

	public void deserialize(DataInputStream in) throws IOException {
		setID(in.readInt());
		int pathSize = in.readInt();
		if (pathSize > 0) {
			String[] paths = new String[pathSize];
			for (int i = 0; i < pathSize; i++) {
				paths[i] = CommunicationUtilities.readString(in);
			}
			setPaths(paths);
		}
	}

	public int getType() {
		return 38;
	}

	public void serialize(DataOutputStream out) throws IOException {
		out.writeShort(getType());
		out.writeInt(getID());
		String[] paths = getPaths();
		out.writeInt(paths.length);
		for (int i = 0; i < paths.length; i++) {
			CommunicationUtilities.writeString(out, paths[i]);
		}
	}

}