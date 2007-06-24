/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.analysis;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.ISourceLocator;

/**
 * The launch item for analysis application
 * @author Roy, 2007
 *
 */
public class AnalysisLaunch implements ILaunch {

	public void addDebugTarget(IDebugTarget target) {

	}

	public void addProcess(IProcess process) {

	}

	public String getAttribute(String key) {

		return null;
	}

	public Object[] getChildren() {

		return null;
	}

	public IDebugTarget getDebugTarget() {

		return null;
	}

	public IDebugTarget[] getDebugTargets() {

		return null;
	}

	public ILaunchConfiguration getLaunchConfiguration() {

		return null;
	}

	public String getLaunchMode() {

		return null;
	}

	public IProcess[] getProcesses() {

		return null;
	}

	public ISourceLocator getSourceLocator() {

		return null;
	}

	public boolean hasChildren() {

		return false;
	}

	public void removeDebugTarget(IDebugTarget target) {

	}

	public void removeProcess(IProcess process) {

	}

	public void setAttribute(String key, String value) {

	}

	public void setSourceLocator(ISourceLocator sourceLocator) {

	}

	public boolean canTerminate() {

		return false;
	}

	public boolean isTerminated() {

		return false;
	}

	public void terminate() throws DebugException {

	}

	public Object getAdapter(Class adapter) {

		return null;
	}

}
