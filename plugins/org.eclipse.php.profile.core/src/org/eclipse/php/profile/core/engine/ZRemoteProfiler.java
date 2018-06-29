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
package org.eclipse.php.profile.core.engine;

import org.eclipse.core.runtime.Platform;
import org.eclipse.php.debug.core.debugger.IDebugHandler;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.preferences.PHPDebugCorePreferenceNames;
import org.eclipse.php.internal.debug.core.zend.communication.DebugConnection;
import org.eclipse.php.internal.debug.core.zend.debugger.RemoteDebugger;
import org.eclipse.php.profile.core.PHPProfileCorePlugin;
import org.eclipse.php.profile.core.data.ProfilerCallTrace;
import org.eclipse.php.profile.core.data.ProfilerData;
import org.eclipse.php.profile.core.data.ProfilerFileData;
import org.eclipse.php.profile.core.data.ProfilerFunctionData;
import org.eclipse.php.profile.core.data.ProfilerGlobalData;
import org.eclipse.php.profile.core.messages.GetProfilerCallTraceRequest;
import org.eclipse.php.profile.core.messages.GetProfilerCallTraceResponse;
import org.eclipse.php.profile.core.messages.GetProfilerFileRequest;
import org.eclipse.php.profile.core.messages.GetProfilerFileResponse;
import org.eclipse.php.profile.core.messages.GetProfilerRequest;
import org.eclipse.php.profile.core.messages.GetProfilerResponse;

/**
 * This class extends Zend remote debugger, and adds functionality for
 * profiling.
 */
public class ZRemoteProfiler extends RemoteDebugger {

	public ZRemoteProfiler(IDebugHandler debugHandler, DebugConnection debugConnection) {
		super(debugHandler, debugConnection);
	}

	public ProfilerGlobalData getProfilerGlobalData() {
		if (!this.isActive()) {
			return null;
		}
		ProfilerGlobalData globalData;
		try {
			// basic profiler info
			GetProfilerRequest getProfilerRequest = new GetProfilerRequest();
			GetProfilerResponse profilerResponse = (GetProfilerResponse) getConnection()
					.sendRequest(getProfilerRequest);
			if (profilerResponse == null) {
				return null;
			}
			globalData = profilerResponse.getProfilerGlobalData();
		} catch (Exception e) {
			PHPProfileCorePlugin.log(e);
			return null;
		}
		return globalData;
	}

	public ProfilerFileData getProfilerFileData(int fileNumber) {
		if (!this.isActive()) {
			return null;
		}
		ProfilerFileData fileData;
		try {
			GetProfilerFileRequest getProfilerFileRequest = new GetProfilerFileRequest();
			getProfilerFileRequest.setFileNumber(fileNumber);
			GetProfilerFileResponse getProfilerFileResponse = (GetProfilerFileResponse) getConnection()
					.sendRequest(getProfilerFileRequest);
			if (getProfilerFileResponse == null) {
				return null;
			}
			fileData = getProfilerFileResponse.getProfilerFileData();

			String localFileName = convertToLocalFilename(fileData.getName(), null, null);
			fileData.setLocalName(localFileName);

			ProfilerFunctionData[] functions = fileData.getFunctions();
			for (int i = 0; i < functions.length; ++i) {
				functions[i].setLocalFileName(localFileName);
			}

		} catch (Exception e) {
			PHPProfileCorePlugin.log(e);
			return null;
		}
		return fileData;
	}

	public ProfilerCallTrace getProfilerCallTrace() {
		if (!this.isActive()) {
			return null;
		}
		ProfilerCallTrace callTrace;
		try {
			GetProfilerCallTraceRequest getProfilerCallTraceRequest = new GetProfilerCallTraceRequest();
			GetProfilerCallTraceResponse getProfilerCallTraceResponse = (GetProfilerCallTraceResponse) getConnection()
					.sendRequest(getProfilerCallTraceRequest);
			if (getProfilerCallTraceResponse == null) {
				return null;
			}
			callTrace = getProfilerCallTraceResponse.getCallTrace();
		} catch (Exception e) {
			PHPProfileCorePlugin.log(e);
			return null;
		}
		return callTrace;
	}

	public ProfilerData getProfilerData() {
		ProfilerData profilerData = new ProfilerData();
		ProfilerGlobalData profilerGlobalData = getProfilerGlobalData();
		if (profilerGlobalData == null) {
			return null;
		}
		profilerGlobalData.setOriginalURL(getDebugHandler().getDebugTarget().getURL());
		profilerData.setGlobalData(profilerGlobalData);

		String dummyFile = Platform.getPreferencesService().getString(PHPDebugPlugin.ID,
				PHPDebugCorePreferenceNames.ZEND_DEBUG_DUMMY_FILE, "", //$NON-NLS-1$
				null);
		boolean isDummyFiltered = false;

		int fileCount = profilerGlobalData.getFileCount();
		for (int i = 0; i < fileCount; i++) {
			ProfilerFileData fileData = getProfilerFileData(i);
			if (fileData != null) {
				if (i == 0 && fileData.getName().endsWith(dummyFile)) {
					isDummyFiltered = true;
				} else {
					profilerData.addFile(fileData);
				}
			}
		}

		ProfilerCallTrace callTrace = getProfilerCallTrace();
		if (callTrace != null) {
			if (isDummyFiltered) {
				callTrace.removeFirstLayer();
			}
			profilerData.setCallTrace(callTrace);
		}
		return profilerData;
	}
}
