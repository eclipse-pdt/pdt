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

import java.util.Date;
import java.util.List;

import org.eclipse.php.profile.core.data.*;

/**
 * Default profile DB manager.
 */
public class ProfilerDBManager implements ProfilerDB {

	private ProfilerDB profilerDB;

	/**
	 * crates new ProfilerDBManager with profilerDB to work with
	 */
	public ProfilerDBManager(ProfilerDB profilerDB) {
		this.profilerDB = profilerDB;
	}

	/**
	 * Sets the profilerDB to work with
	 */
	public void setProfilerDB(ProfilerDB profilerDB) {
		this.profilerDB = profilerDB;
	}

	/**
	 * @param id
	 *            of the function as recieved from the debugger
	 * @return data of the function
	 */
	public ProfilerFunctionData getFunctionData(int id) {
		return profilerDB.getFunctionData(id);
	}

	/**
	 * Get the file data from profilerDB
	 * 
	 * @param fileNumber
	 *            - the file number as appeared in the original list files as
	 *            recieved from the server
	 * @return the filedata
	 */
	public ProfilerFileData getFileData(int fileNumber) {
		return profilerDB.getFileData(fileNumber);
	}

	/**
	 * Get the file data from profilerDB
	 * 
	 * @param fileName
	 *            - the file name to be requested
	 * @return the filedata
	 */
	public ProfilerFileData getFileData(String fileName) {
		return profilerDB.getFileData(fileName);
	}

	/**
	 * Gets all the files from the profiler
	 * 
	 * @return the files
	 */
	public ProfilerFileData[] getFiles() {
		return profilerDB.getFiles();
	}

	public List<ProfilerFileData> getFilesList() {
		return profilerDB.getFilesList();
	}

	/**
	 * get the call trace from the database as recieved from the debugger
	 * 
	 * @return the callTrace
	 */
	public ProfilerCallTrace getCallTrace() {
		return profilerDB.getCallTrace();
	}

	/**
	 * get the global data of the profiler
	 */
	public ProfilerGlobalData getGlobalData() {
		return profilerDB.getGlobalData();
	}

	/**
	 * cleal all information from database
	 */
	public void clearAll() {
		profilerDB.clearAll();
	}

	public void setProfilerData(ProfilerData data) {
		profilerDB.setProfilerData(data);
	}

	public ProfilerData getProfilerData() {
		return profilerDB.getProfilerData();
	}

	/**
	 * Returns the date of profiling
	 */
	public Date getProfileDate() {
		return profilerDB.getProfileDate();
	}
}