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

import org.eclipse.php.profile.core.data.ProfilerCallTrace;
import org.eclipse.php.profile.core.data.ProfilerData;
import org.eclipse.php.profile.core.data.ProfilerFileData;
import org.eclipse.php.profile.core.data.ProfilerGlobalData;

/**
 * Common interface for Zend profiler implementation.
 */
public interface IProfiler {

	public ProfilerGlobalData getProfilerGlobalData();

	public ProfilerFileData getProfilerFileData(int fileNumber);

	public ProfilerCallTrace getProfilerCallTrace();

	public ProfilerData getProfilerData();

}