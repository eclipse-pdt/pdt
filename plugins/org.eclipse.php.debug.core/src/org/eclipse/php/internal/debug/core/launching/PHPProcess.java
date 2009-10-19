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
package org.eclipse.php.internal.debug.core.launching;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.PlatformObject;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.IStreamsProxy;
import org.eclipse.debug.core.model.ITerminate;
import org.eclipse.debug.ui.console.IConsole;

public class PHPProcess extends PlatformObject implements IProcess {

	private ILaunch fLaunch;
	private String fName;
	private Map<String, String> fAttributes;
	private boolean fTerminated;
	private IConsole fConsole = null;
	private PHPStreamsProxy fProxy;
	private PHPHyperLink fPHPHyperLink;
	private IDebugTarget fDebugTarget;
	private int fExitValue;

	public PHPProcess(ILaunch launch, String name) {
		fLaunch = launch;
		fName = name;
		fTerminated = false;
		launch.addProcess(this);
		fProxy = new PHPStreamsProxy();
		fireCreationEvent();
	}

	public String getLabel() {
		return fName;
	}

	public ILaunch getLaunch() {

		return fLaunch;
	}

	protected void setLaunch(ILaunch launch) {
		fLaunch = launch;
	}

	public IStreamsProxy getStreamsProxy() {
		return fProxy;
	}

	public void setAttribute(String key, String value) {
		if (fAttributes == null) {
			fAttributes = new HashMap<String, String>(5);
		}
		Object origVal = fAttributes.get(key);
		if (origVal != null && origVal.equals(value)) {
			return; // nothing changed.
		}
		fAttributes.put(key, value);
		fireChangeEvent();
	}

	public String getAttribute(String key) {
		if (fAttributes == null) {
			return null;
		}
		return (String) fAttributes.get(key);
	}

	public void setExitValue(int exitValue) {
		this.fExitValue = exitValue;
	}

	public int getExitValue() throws DebugException {
		return fExitValue;
	}

	public Object getAdapter(Class adapter) {
		if (adapter.equals(IProcess.class)) {
			return this;
		}
		if (adapter.equals(IDebugTarget.class)) {
			ILaunch launch = getLaunch();
			IDebugTarget[] targets = launch.getDebugTargets();
			for (int i = 0; i < targets.length; i++) {
				if (this.equals(targets[i].getProcess())) {
					return targets[i];
				}
			}
			return null;
		}
		if (adapter.equals(ITerminate.class)) {
			return this;
		}
		return super.getAdapter(adapter);
	}

	public boolean canTerminate() {
		return !fTerminated;
	}

	public boolean isTerminated() {
		return fTerminated;
	}

	public void terminate() throws DebugException {
		// fLaunch.terminate(); // No need starting from Eclipse 3.3
		fTerminated = true;
		fireTerminateEvent();
	}

	public void setPHPHyperLink(PHPHyperLink pLink) {
		fPHPHyperLink = pLink;
	}

	public PHPHyperLink getPHPHyperLink() {
		return fPHPHyperLink;
	}

	public IConsole getConsole() {
		return fConsole;
	}

	public void setConsole(IConsole console) {
		fConsole = console;
	}

	public IDebugTarget getDebugTarget() {
		return fDebugTarget;
	}

	public void setDebugTarget(IDebugTarget target) {
		fDebugTarget = target;
	}

	/**
	 * Fires a creation event.
	 */
	protected void fireCreationEvent() {
		fireEvent(new DebugEvent(this, DebugEvent.CREATE));
	}

	/**
	 * Fires the given debug event.
	 * 
	 * @param event
	 *            debug event to fire
	 */
	protected void fireEvent(DebugEvent event) {
		DebugPlugin manager = DebugPlugin.getDefault();
		if (manager != null) {
			manager.fireDebugEventSet(new DebugEvent[] { event });
		}
	}

	/**
	 * Fires a terminate event.
	 */
	protected void fireTerminateEvent() {
		fireEvent(new DebugEvent(this, DebugEvent.TERMINATE));
	}

	/**
	 * Fires a change event.
	 */
	protected void fireChangeEvent() {
		fireEvent(new DebugEvent(this, DebugEvent.CHANGE));
	}
}
