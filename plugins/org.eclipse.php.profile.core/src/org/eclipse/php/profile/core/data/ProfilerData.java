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
package org.eclipse.php.profile.core.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Profiler data.
 */
public class ProfilerData {

	private ProfilerGlobalData globalData;
	private List<ProfilerFileData> filesData;
	private ProfilerCallTrace callTrace;

	/**
	 * creates new ProfilerData
	 */
	public ProfilerData() {
		init();
	}

	public ProfilerData(ProfilerGlobalData globalData, List<ProfilerFileData> filesData, ProfilerCallTrace callTrace) {
		this.globalData = globalData;
		this.filesData = filesData;
		this.callTrace = callTrace;
	}

	/**
	 * get the global data of the profiler
	 * 
	 */
	public ProfilerGlobalData getGlobalData() {
		return globalData;
	}

	/**
	 * Sets the global data
	 * 
	 * @param globalData
	 */
	public void setGlobalData(ProfilerGlobalData globalData) {
		this.globalData = globalData;
	}

	/**
	 * get the files data of the profiler
	 */
	public ProfilerFileData[] getFiles() {
		ProfilerFileData[] pfd = new ProfilerFileData[filesData.size()];
		filesData.toArray(pfd);
		return pfd;
	}

	/**
	 * get the files data of the profiler as ArrayList
	 */
	public List<ProfilerFileData> getFilesList() {
		return filesData;
	}

	/**
	 * Sets the files data
	 * 
	 * @param filesData
	 */
	public void setFilesData(List<ProfilerFileData> filesData) {
		this.filesData = filesData;
	}

	/**
	 * get the call trace of the profiler
	 */
	public ProfilerCallTrace getCallTrace() {
		return callTrace;
	}

	/**
	 * Sets the call trace data
	 * 
	 * @param callTrace
	 */
	public void setCallTrace(ProfilerCallTrace callTrace) {
		this.callTrace = callTrace;
	}

	/**
	 * Adds file to the arrayList
	 */
	public void addFile(ProfilerFileData fileData) {
		filesData.add(fileData);
	}

	private void init() {
		filesData = new ArrayList<>();
	}

}
