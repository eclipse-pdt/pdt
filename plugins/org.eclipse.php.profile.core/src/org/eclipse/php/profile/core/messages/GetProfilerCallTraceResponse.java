/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.profile.core.messages;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.eclipse.php.internal.debug.core.zend.debugger.messages.DebugMessageResponseImpl;
import org.eclipse.php.profile.core.data.ProfilerCallTrace;
import org.eclipse.php.profile.core.data.ProfilerCallTraceLayer;
import org.eclipse.php.debug.core.debugger.messages.IDebugResponseMessage;

/**
 * Get profiler call trace response message.
 */
public class GetProfilerCallTraceResponse extends DebugMessageResponseImpl implements IDebugResponseMessage {

	private ProfilerCallTrace callTrace;

	/**
	 * Returns the callTrace
	 */
	public ProfilerCallTrace getCallTrace() {
		return callTrace;
	}

	/**
	 * Sets the callTrace
	 */
	public void setCallTrace(ProfilerCallTrace callTrace) {
		this.callTrace = callTrace;
	}

	@Override
	public int getType() {
		return 11013;
	}

	@Override
	public void deserialize(DataInputStream in) throws IOException {
		setID(in.readInt());
		ProfilerCallTrace callTrace = new ProfilerCallTrace();
		callTrace.setLayersCount(in.readInt());
		for (int i = 0; i < callTrace.getLayersCount(); i++) {
			callTrace.addLayer(
					new ProfilerCallTraceLayer(in.readInt(), in.readInt(), in.readInt(), in.readInt(), in.readInt()));
		}
		setCallTrace(callTrace);
	}

	@Override
	public void serialize(DataOutputStream out) throws IOException {
		out.writeShort(getType());
		out.writeInt(getID());
		out.writeInt(getStatus());
	}
}
