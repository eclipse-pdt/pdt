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
package org.eclipse.php.debug.core.launching;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.ISourceLocator;

/**
 * Launch Proxy.
 * 
 * @author Shalom Gibly
 *
 */
public class PHPLaunchProxy implements ILaunch {

	protected ILaunch launch;

	public PHPLaunchProxy(ILaunch launch) {
		this.launch = launch;
	}

	public ILaunch getLaunch(){
		return this.launch;
	}
	
	public void setLaunch(ILaunch launch) {
		this.launch = launch;
	}

	public Object[] getChildren() {
		return launch.getChildren();
	}

	public IDebugTarget getDebugTarget() {
		return launch.getDebugTarget();
	}

	public IProcess[] getProcesses() {
		return launch.getProcesses();
	}

	public IDebugTarget[] getDebugTargets() {
		return launch.getDebugTargets();
	}

	public void addDebugTarget(IDebugTarget target) {
		launch.addDebugTarget(target);
	}

	public void removeDebugTarget(IDebugTarget target) {
		launch.removeDebugTarget(target);
	}

	public void addProcess(IProcess process) {
		launch.addProcess(process);
	}

	public void removeProcess(IProcess process) {
		launch.removeProcess(process);
	}

	public ISourceLocator getSourceLocator() {
		return launch.getSourceLocator();
	}

	public void setSourceLocator(ISourceLocator sourceLocator) {
		launch.setSourceLocator(sourceLocator);
	}

	public String getLaunchMode() {
		return launch.getLaunchMode();
	}

	public ILaunchConfiguration getLaunchConfiguration() {
		return launch.getLaunchConfiguration();
	}

	public void setAttribute(String key, String value) {
		launch.setAttribute(key, value);
	}

	public String getAttribute(String key) {
		return launch.getAttribute(key);
	}

	public boolean hasChildren() {
		return launch.hasChildren();
	}

	public boolean canTerminate() {
		return launch.canTerminate();
	}

	public boolean isTerminated() {
		return launch.isTerminated();
	}

	public void terminate() throws DebugException {
		launch.terminate();
	}

	public Object getAdapter(Class adapter) {
		return launch.getAdapter(adapter);
	}
}
