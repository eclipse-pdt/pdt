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
package org.eclipse.php.profile.core.messages;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.eclipse.php.internal.debug.core.zend.communication.CommunicationUtilities;
import org.eclipse.php.internal.debug.core.zend.debugger.messages.DebugMessageResponseImpl;
import org.eclipse.php.profile.core.data.ProfilerFileData;
import org.eclipse.php.profile.core.data.ProfilerFunctionData;
import org.eclipse.php.debug.core.debugger.messages.IDebugResponseMessage;

/**
 * Get profiler file response message.
 */
public class GetProfilerFileResponse extends DebugMessageResponseImpl implements IDebugResponseMessage {

	private ProfilerFileData profilerFileData;

	/**
	 * Returns the profilerFileData
	 */
	public ProfilerFileData getProfilerFileData() {
		return profilerFileData;
	}

	/**
	 * Sets the profilerFileData
	 */
	public void setProfilerFileData(ProfilerFileData profilerFileData) {
		this.profilerFileData = profilerFileData;
	}

	public void deserialize(DataInputStream in) throws IOException {
		setID(in.readInt());
		ProfilerFileData fileData = new ProfilerFileData();
		fileData.setName(CommunicationUtilities.readString(in));
		fileData.setFunctionsCount(in.readInt());
		for (int i = 0; i < fileData.getFunctionsCount(); i++) {
			ProfilerFunctionData functionData = new ProfilerFunctionData(fileData.getName(),
					CommunicationUtilities.readString(in), // file
					// name
					// and
					// function
					// name
					in.readInt(), in.readInt(), // line number and calledID
					in.readInt(), in.readInt(), // own time(s) and own time(ms)
					in.readInt(), in.readInt(), // total time(s) and total time
					// (ms)
					in.readInt()); // calls count
			fileData.addFunction(functionData);
		}
		setProfilerFileData(fileData);
	}

	public int getType() {
		return 11012;
	}

	public void serialize(DataOutputStream out) throws IOException {
		out.writeShort(getType());
		out.writeInt(getID());
		out.writeInt(getStatus());
	}
}
