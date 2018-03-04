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
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.eclipse.php.internal.core.util.collections.IntHashtable;
import org.eclipse.php.profile.core.data.*;

/**
 * Default profiler db implementation.
 */
public class DefaultProfilerDB implements ProfilerDB {

	private IntHashtable fFunctionsHash = new IntHashtable(); // hashtable of
																// funtion_id
																// and
																// functionData
	private Map<String, ProfilerFileData> fFilesHash = new Hashtable<>(); // hashtable
																			// of
																			// file
																			// name
	// and fileData
	private ProfilerData fProfilerData;
	private Date fProfileDate;

	/**
	 * Constructs profiler db from the connection with profiler
	 * 
	 * @param ZProfiler
	 *            connection manager
	 */
	public DefaultProfilerDB(ZProfiler connectionManager) {
		fProfilerData = connectionManager.getProfilerData();
		buildFilesHashtable();
		buildFunctionsHashtable();
		fProfileDate = new Date();
	}

	/**
	 * Constructs profiler db from the specified profiler data. May be useful when
	 * restoring profiler db from file.
	 * 
	 * @param ProfilerData
	 *            data
	 * @param Date
	 *            date
	 */
	public DefaultProfilerDB(ProfilerData data, Date date) {
		fProfilerData = data;
		fProfileDate = date;
		buildFilesHashtable();
		buildFunctionsHashtable();
	}

	private void buildFilesHashtable() {
		fFilesHash.clear();
		ProfilerFileData[] files = getProfilerData().getFiles();
		for (int i = 0; i < files.length; i++) {
			ProfilerFileData file = files[i];
			fFilesHash.put(file.getName(), file);
		}

	}

	/**
	 * insert the functions in the hashtable before starting profiling progress
	 */
	private void buildFunctionsHashtable() {
		fFunctionsHash.clear();
		ProfilerFileData[] files = getProfilerData().getFiles();
		if (files != null) {
			for (int i = 0; i < files.length; i++) { // to check if this is
														// necessary instead of
														// going over all files
														// and functions every
														// time
				ProfilerFileData fileData = files[i];
				ProfilerFunctionData[] functionsData = fileData.getFunctions();
				if (functionsData != null) {
					for (int j = 0; j < functionsData.length; j++) {
						fFunctionsHash.put(functionsData[j].getID(), functionsData[j]);
					}
				}
			}
		}
	}

	/**
	 * @param id
	 *            of the function as recieved from the debugger
	 * @return data of the function
	 */
	@Override
	public ProfilerFunctionData getFunctionData(int id) {
		return (ProfilerFunctionData) fFunctionsHash.get(id);
	}

	/**
	 * Get the file data from the profilerdata or from the debugger
	 * 
	 * @param fileName
	 *            - the file name to be requested
	 * @return the filedata
	 */
	@Override
	public ProfilerFileData getFileData(String fileName) {
		if (fFilesHash.get(fileName) != null) {
			return fFilesHash.get(fileName);
		}
		return null;
	}

	/**
	 * Get the file data from the profilerdata or from the debugger
	 * 
	 * @param fileNumber
	 *            - the file number to be requested
	 * @return the filedata
	 */
	@Override
	public ProfilerFileData getFileData(int fileNumber) {
		ProfilerFileData[] filesList = getProfilerData().getFiles();
		if (filesList != null && filesList.length > fileNumber) {
			return filesList[fileNumber];
		}
		return null;
	}

	/**
	 * Gets all the files from the profiler
	 * 
	 * @return the files
	 */
	@Override
	public ProfilerFileData[] getFiles() {
		return fProfilerData.getFiles();
	}

	@Override
	public List<ProfilerFileData> getFilesList() {
		return fProfilerData.getFilesList();
	}

	/**
	 * get the call trace from the database as recieved from the debugger
	 * 
	 * @return the callTrace
	 */
	@Override
	public ProfilerCallTrace getCallTrace() {
		return getProfilerData().getCallTrace();
	}

	/**
	 * Gets the global data of the profiler
	 */
	@Override
	public ProfilerGlobalData getGlobalData() {
		return getProfilerData().getGlobalData();
	}

	/**
	 * cleal all information from database and reset profiler
	 */
	@Override
	public void clearAll() {
		fFunctionsHash.clear();
		fFilesHash.clear();
		fProfilerData = null;
	}

	/**
	 * get the current profiler used. create a new one if it doesn't exist
	 * 
	 */
	@Override
	public ProfilerData getProfilerData() {
		if (fProfilerData == null) {
			fProfilerData = new ProfilerData();
		}
		return fProfilerData;
	}

	@Override
	public void setProfilerData(ProfilerData profiler) {
		this.fProfilerData = profiler;
	}

	/**
	 * Returns profile date
	 */
	@Override
	public Date getProfileDate() {
		return fProfileDate;
	}
}