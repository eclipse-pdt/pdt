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
