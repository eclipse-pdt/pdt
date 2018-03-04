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
package org.eclipse.php.profile.core.data;

import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.php.profile.core.PHPProfileCoreMessages;

/**
 * Profiler global data.
 */
public class ProfilerGlobalData {

	public static final String URL_NOT_AVAILABLE_MSG = PHPProfileCoreMessages.ProfilerGlobalData_0;

	private String fURI = ""; //$NON-NLS-1$
	private String fQuery = ""; //$NON-NLS-1$
	private String fOptions = ""; //$NON-NLS-1$
	private String fPath = ""; //$NON-NLS-1$
	private int fTimeSeconds = 0;
	private int fTimeMicroSeconds = 0;
	private int fDataSize = 0;
	private int fFileCount = 0;
	private ArrayList<String> fFileNames;
	private String fOriginalURL;

	private static final int CONVERTION = 1000000;

	public ProfilerGlobalData() {
		fFileNames = new ArrayList<>();
	}

	public ProfilerGlobalData(String uri, String originalURL, String query, String options, String path,
			int timeSeconds, int timeMicroSeconds, int dataSize, int fileCount, ArrayList<String> fileNames) {
		fURI = uri;
		fOriginalURL = originalURL;
		fQuery = query;
		fOptions = options;
		fPath = path;
		fTimeSeconds = timeSeconds;
		fTimeMicroSeconds = timeMicroSeconds;
		fDataSize = dataSize;
		fFileCount = fileCount;
		fFileNames = fileNames;
	}

	/**
	 * Returns the requestURI
	 */
	public String getURI() {
		return fURI;
	}

	public String getOriginalURL() {
		if (fOriginalURL == null) {
			return getURIFromQuery();
		}
		return fOriginalURL;
	}

	public void setOriginalURL(String originalURL) {
		fOriginalURL = originalURL;
	}

	/**
	 * Returns the additional options
	 */
	public String getOptions() {
		return fOptions;
	}

	/**
	 * Sets the requestURI
	 */
	public void setOptions(String options) {
		this.fOptions = options;
	}

	/**
	 * Sets the requestURI
	 */
	public void setURI(String uri) {
		this.fURI = uri;
	}

	/**
	 * Returns the query
	 */
	public String getQuery() {
		return fQuery;
	}

	/**
	 * Sets the query
	 */
	public void setQuery(String query) {
		this.fQuery = query;
	}

	/**
	 * Returns the path
	 */
	public String getPath() {
		return fPath;
	}

	/**
	 * Sets the path
	 */
	public void setPath(String path) {
		this.fPath = path;
	}

	/**
	 * Returns the time in Second
	 */
	public int getTimeSeconds() {
		return fTimeSeconds;
	}

	/**
	 * Sets the time in Second
	 */
	public void setTimeSeconds(int timeSeconds) {
		this.fTimeSeconds = timeSeconds;
	}

	/**
	 * Sets the time in Micro Second
	 */
	public void setTimeMicroSeconds(int timeMicroSeconds) {
		this.fTimeMicroSeconds = timeMicroSeconds;
	}

	/**
	 * Returns the time in Micro Seconds
	 */
	public int getTimeMicroSeconds() {
		return fTimeMicroSeconds;
	}

	/**
	 * Returns the file count
	 */
	public int getFileCount() {
		return fFileCount;
	}

	/**
	 * Sets the file count
	 */
	public void setFileCount(int fileCount) {
		this.fFileCount = fileCount;
	}

	/**
	 * add file name to the list
	 */
	public void addFileName(String fileName) {
		fFileNames.add(fileName);
	}

	/**
	 * Sets the file names
	 */
	public void setFileNames(String[] names) {
		fFileNames = new ArrayList<>(Arrays.asList(names));
	}

	/**
	 * Returns the data size
	 */
	public int getDataSize() {
		return fDataSize;
	}

	/**
	 * Sets the data size
	 */
	public void setDataSize(int dataSize) {
		this.fDataSize = dataSize;
	}

	/**
	 * Returns the global time in seconds
	 * 
	 * @return the double value of the result
	 */
	public double getGlobalTime() {
		double time = ((double) fTimeSeconds * CONVERTION + fTimeMicroSeconds) / CONVERTION;
		return time;
	}

	/**
	 * Returns the global time in milli seconds
	 * 
	 * @return the double value of the result
	 */
	public double getGlobalTimeInMilli() {
		return getGlobalTime() * 1000;
	}

	/**
	 * Returns the file names
	 */
	public String[] getFileNames() {
		String names[] = new String[fFileNames.size()];
		fFileNames.toArray(names);
		return names;
	}

	public void removeFileName(String fileName) {
		for (int i = 0; i < fFileNames.size(); i++) {
			String file = fFileNames.get(i);
			if (file.endsWith(fileName)) {
				fFileNames.remove(i);
				fFileCount--;
				break;
			}
		}
	}

	/**
	 * Returns the uri from query: original_url=...
	 */
	protected String getURIFromQuery() {
		String code = "original_url="; //$NON-NLS-1$
		String uri = ""; //$NON-NLS-1$
		if (fQuery.indexOf(code) != -1) {
			uri = fQuery.substring(fQuery.indexOf(code) + code.length());
		} else if (fOptions.indexOf(code) != -1) {
			uri = fOptions.substring(fOptions.indexOf(code) + code.length());
		}
		if (uri.equals("")) { //$NON-NLS-1$
			uri = URL_NOT_AVAILABLE_MSG;
		}
		return uri;
	}
}
