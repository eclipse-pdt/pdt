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

import org.eclipse.core.runtime.Platform;
import org.eclipse.php.debug.core.debugger.messages.IDebugResponseMessage;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.preferences.PHPDebugCorePreferenceNames;
import org.eclipse.php.internal.debug.core.zend.communication.CommunicationUtilities;
import org.eclipse.php.internal.debug.core.zend.debugger.messages.DebugMessageResponseImpl;
import org.eclipse.php.profile.core.data.ProfilerGlobalData;

/**
 * Get profiler response message.
 */
public class GetProfilerResponse extends DebugMessageResponseImpl implements IDebugResponseMessage {

	private ProfilerGlobalData profilerGlobalData;

	/**
	 * Returns the profilerGlobalData
	 */
	public ProfilerGlobalData getProfilerGlobalData() {
		return profilerGlobalData;
	}

	/**
	 * Sets the profilerGlobalData
	 */
	public void setProfilerData(ProfilerGlobalData profilerGlobalData) {
		this.profilerGlobalData = profilerGlobalData;
	}

	@Override
	public int getType() {
		return 11011;
	}

	@Override
	public void deserialize(DataInputStream in) throws IOException {
		setID(in.readInt());
		ProfilerGlobalData profilerGlobalData = new ProfilerGlobalData();
		profilerGlobalData.setURI(CommunicationUtilities.readString(in));
		profilerGlobalData.setQuery(CommunicationUtilities.readString(in));
		profilerGlobalData.setPath(CommunicationUtilities.readString(in));
		profilerGlobalData.setTimeSeconds(in.readInt());
		profilerGlobalData.setTimeMicroSeconds(in.readInt());
		profilerGlobalData.setDataSize(in.readInt());
		profilerGlobalData.setFileCount(in.readInt());

		String dummyFile = Platform.getPreferencesService().getString(PHPDebugPlugin.ID,
				PHPDebugCorePreferenceNames.ZEND_DEBUG_DUMMY_FILE, "", //$NON-NLS-1$
				null);

		for (int i = 0; i < profilerGlobalData.getFileCount(); i++) {
			String fileName = CommunicationUtilities.readString(in);
			if (i == 0 && fileName.endsWith(dummyFile)) {
				continue;
			}
			profilerGlobalData.addFileName(fileName);
		}

		setProfilerData(profilerGlobalData);
	}

	@Override
	public void serialize(DataOutputStream out) throws IOException {
		out.writeShort(getType());
		out.writeInt(getID());
		out.writeInt(getStatus());
	}
}
