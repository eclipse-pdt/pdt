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

import java.util.Date;
import java.util.List;

import org.eclipse.php.profile.core.data.*;

/**
 * Common interface for profile DB implementation.
 */
public interface ProfilerDB {

	public ProfilerGlobalData getGlobalData();

	public ProfilerFunctionData getFunctionData(int id);

	public ProfilerFileData getFileData(String fileName);

	public ProfilerFileData getFileData(int number);

	public ProfilerCallTrace getCallTrace();

	public void clearAll();

	public ProfilerData getProfilerData();

	public void setProfilerData(ProfilerData data);

	public ProfilerFileData[] getFiles();

	public List<ProfilerFileData> getFilesList();

	public Date getProfileDate();
}
