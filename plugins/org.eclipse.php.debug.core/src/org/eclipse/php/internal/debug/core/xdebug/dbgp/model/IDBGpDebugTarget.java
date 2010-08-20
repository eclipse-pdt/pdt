/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.xdebug.dbgp.model;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.php.internal.debug.core.pathmapper.PathMapper;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.DBGpBreakpointFacade;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.DBGpPreferences;

public interface IDBGpDebugTarget extends IDebugTarget {

	public void waitForInitialSession(DBGpBreakpointFacade facade,
			DBGpPreferences sessionPrefs, IProgressMonitor launchMonitor);

	public void setPathMapper(PathMapper mapper);

	public boolean isWebLaunch();

	public void configureInitialState(DBGpBreakpointFacade facade,
			DBGpPreferences sessionPrefs);
}
