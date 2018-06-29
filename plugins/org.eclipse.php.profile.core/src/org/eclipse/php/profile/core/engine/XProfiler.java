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

import org.eclipse.php.debug.core.debugger.IDebugHandler;
import org.eclipse.php.internal.debug.core.zend.debugger.DebugError;
import org.eclipse.php.internal.debug.core.zend.debugger.IRemoteDebugger;
import org.eclipse.php.internal.debug.core.zend.model.ServerDebugHandler;
import org.eclipse.php.profile.core.data.ProfilerCallTrace;
import org.eclipse.php.profile.core.data.ProfilerData;
import org.eclipse.php.profile.core.data.ProfilerFileData;
import org.eclipse.php.profile.core.data.ProfilerGlobalData;

public class XProfiler extends ServerDebugHandler implements IProfiler, IDebugHandler {

	private ProfilerDBManager fDBManager;
	private boolean fParsingErrorOccurred;

	@Override
	protected IRemoteDebugger createRemoteDebugger() {
		return new ZRemoteProfiler(this, fDebugConnection);
	}

	public ProfilerDB getProfilerDB() {
		return fDBManager;
	}

	@Override
	public ProfilerGlobalData getProfilerGlobalData() {
		return null;
	}

	@Override
	public ProfilerFileData getProfilerFileData(int fileNumber) {
		return null;
	}

	@Override
	public ProfilerCallTrace getProfilerCallTrace() {
		return null;
	}

	@Override
	public ProfilerData getProfilerData() {
		return null;
	}

	@Override
	public void handleScriptEnded() {

	}

	@Override
	public void sessionStarted(String fileName, String uri, String query, String options) {

	}

	@Override
	public void parsingErrorOccured(DebugError debugError) {

	}

}
