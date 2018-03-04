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
package org.eclipse.php.profile.core.engine;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.php.debug.core.debugger.IDebugHandler;
import org.eclipse.php.internal.debug.core.zend.debugger.CodeCoverageData;
import org.eclipse.php.internal.debug.core.zend.debugger.DebugError;
import org.eclipse.php.internal.debug.core.zend.debugger.IRemoteDebugger;
import org.eclipse.php.internal.debug.core.zend.model.ServerDebugHandler;
import org.eclipse.php.profile.core.PHPProfileCoreMessages;
import org.eclipse.php.profile.core.PHPProfileCorePlugin;
import org.eclipse.php.profile.core.data.ProfilerCallTrace;
import org.eclipse.php.profile.core.data.ProfilerData;
import org.eclipse.php.profile.core.data.ProfilerFileData;
import org.eclipse.php.profile.core.data.ProfilerGlobalData;

/**
 * Debug handler implementation for Zend profiler.
 */
public class ZProfiler extends ServerDebugHandler implements IProfiler, IDebugHandler {

	private ProfilerDBManager fDBManager;
	private String fAdditionalOptions = ""; //$NON-NLS-1$
	private boolean fParsingErrorOccurred;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zend.php.debug.core.debugger.AdvancedDebugHandler#
	 * createRemoteDebugger ()
	 */
	@Override
	protected IRemoteDebugger createRemoteDebugger() {
		return new ZRemoteProfiler(this, fDebugConnection);
	}

	public ProfilerDB getProfilerDB() {
		return fDBManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zend.php.profile.core.profiler.IProfiler#getProfilerGlobalData()
	 */
	@Override
	public ProfilerGlobalData getProfilerGlobalData() {
		ZRemoteProfiler remoteProfiler = (ZRemoteProfiler) getRemoteDebugger();
		return remoteProfiler.getProfilerGlobalData();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zend.php.profile.core.profiler.IProfiler#getProfilerFileData(int)
	 */
	@Override
	public ProfilerFileData getProfilerFileData(int fileNumber) {
		ZRemoteProfiler remoteProfiler = (ZRemoteProfiler) getRemoteDebugger();
		return remoteProfiler.getProfilerFileData(fileNumber);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zend.php.profile.core.profiler.IProfiler#getProfilerCallTrace()
	 */
	@Override
	public ProfilerCallTrace getProfilerCallTrace() {
		ZRemoteProfiler remoteProfiler = (ZRemoteProfiler) getRemoteDebugger();
		return remoteProfiler.getProfilerCallTrace();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zend.php.profile.core.profiler.IProfiler#getProfilerData()
	 */
	@Override
	public ProfilerData getProfilerData() {
		ZRemoteProfiler remoteProfiler = (ZRemoteProfiler) getRemoteDebugger();
		return remoteProfiler.getProfilerData();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.core.debugger.IDebugHandler#
	 * handleScriptEnded ()
	 */
	@Override
	public void handleScriptEnded() {
		try {
			if (fParsingErrorOccurred) {
				super.handleScriptEnded();
			} else {
				Job profileJob = new Job(PHPProfileCoreMessages.ZProfiler_0) {
					@Override
					protected IStatus run(IProgressMonitor monitor) {
						if (!monitor.isCanceled()) {
							try {
								fDBManager = new ProfilerDBManager(new DefaultProfilerDB(ZProfiler.this));
								ProfilerGlobalData globalData = fDBManager.getGlobalData();
								if (globalData != null) {
									globalData.setOptions(fAdditionalOptions);
								}
							} finally {
								monitor.done();
							}
						}
						return Status.OK_STATUS;
					}
				};
				profileJob.schedule();
				profileJob.join();
				// Call super
				super.handleScriptEnded();
				if (fDBManager.getGlobalData() != null) {
					CodeCoverageData[] codeCoverageData = getLastCodeCoverageData();
					if (codeCoverageData != null) {
						for (int i = 0; i < codeCoverageData.length; ++i) {
							ProfilerFileData fileData = fDBManager.getFileData(codeCoverageData[i].getFileName());
							if (fileData != null) {
								fileData.setCodeCoverageData(codeCoverageData[i]);
							}
						}
					}
					ProfileSessionsManager.addSession(fDBManager);
				}
			}
		} catch (Exception e) {
			PHPProfileCorePlugin.log(e);
		} finally {
			getDebugTarget().terminated();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.debug.core.debugger.IDebugHandler#sessionStarted
	 * (java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void sessionStarted(String fileName, String uri, String query, String options) {
		super.sessionStarted(fileName, uri, query, options);
		fParsingErrorOccurred = false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.php.internal.debug.core.model.ServerDebugHandler#
	 * parsingErrorOccured (org.eclipse.php.internal.debug.core.debugger.DebugError)
	 */
	@Override
	public void parsingErrorOccured(DebugError debugError) {
		if (DebugError.isError(debugError)) {
			fParsingErrorOccurred = true;
		}
		super.parsingErrorOccured(debugError);
	}
}
