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
import org.eclipse.debug.core.*;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.IStreamsProxy;
import org.eclipse.debug.core.model.ITerminate;
import org.eclipse.debug.ui.console.IConsole;
import org.eclipse.php.internal.debug.core.PHPDebugCoreMessages;

public class PHPProcess extends PlatformObject implements IProcess {

	private ILaunch fLaunch;
	private String fName;
	private Map<String, String> fAttributes;
	private IConsole fConsole = null;
	private PHPStreamsProxy fProxy;
	private PHPHyperLink fPHPHyperLink;
	private IDebugTarget fDebugTarget;
	private int fExitValue;

	/**
	 * Creates new PHP process for given launch and with provided name.
	 * 
	 * @param launch
	 * @param name
	 */
	public PHPProcess(ILaunch launch, String name) {
		fLaunch = launch;
		fName = name;
		fProxy = new PHPStreamsProxy();
		fireCreationEvent();
	}

	@Override
	public String getLabel() {
		String suffix = null;
		if (fLaunch.getLaunchMode().equals(ILaunchManager.DEBUG_MODE)) {
			if (fLaunch instanceof PHPLaunch) {
				suffix = PHPDebugCoreMessages.PHPProcess_Zend_Debugger_suffix;
			} else if (fLaunch instanceof XDebugLaunch) {
				suffix = PHPDebugCoreMessages.PHPProcess_XDebug_suffix;
			}
		}
		return suffix != null ? (fName + ' ' + suffix) : fName;
	}

	@Override
	public ILaunch getLaunch() {
		return fLaunch;
	}

	@Override
	public IStreamsProxy getStreamsProxy() {
		return fProxy;
	}

	@Override
	public void setAttribute(String key, String value) {
		if (fAttributes == null) {
			fAttributes = new HashMap<>(5);
		}
		Object origVal = fAttributes.get(key);
		if (origVal != null && origVal.equals(value)) {
			return; // nothing changed.
		}
		fAttributes.put(key, value);
		fireChangeEvent();
	}

	@Override
	public String getAttribute(String key) {
		if (fAttributes == null) {
			return null;
		}
		return fAttributes.get(key);
	}

	@Override
	public int getExitValue() throws DebugException {
		return fExitValue;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
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

	@Override
	public boolean canTerminate() {
		return !isTerminated();
	}

	@Override
	public boolean isTerminated() {
		return fDebugTarget.isTerminated();
	}

	@Override
	public void terminate() throws DebugException {
		// Terminate debug target
		if (fDebugTarget.canTerminate()) {
			fDebugTarget.terminate();
		}
		fireTerminateEvent();
	}

	/**
	 * Sets PHP hyperlink.
	 * 
	 * @param pLink
	 */
	public void setPHPHyperLink(PHPHyperLink pLink) {
		fPHPHyperLink = pLink;
	}

	/**
	 * Returns PHP hyperlink.
	 * 
	 * @return PHP hyperlink
	 */
	public PHPHyperLink getPHPHyperLink() {
		return fPHPHyperLink;
	}

	/**
	 * Returns corresponding console.
	 * 
	 * @return console
	 */
	public IConsole getConsole() {
		return fConsole;
	}

	/**
	 * Sets corresponding console.
	 * 
	 * @param console
	 */
	public void setConsole(IConsole console) {
		fConsole = console;
	}

	/**
	 * Returns related debug target.
	 * 
	 * @return debug target
	 */
	public IDebugTarget getDebugTarget() {
		return fDebugTarget;
	}

	/**
	 * Sets related debug target.
	 * 
	 * @param target
	 */
	public void setDebugTarget(IDebugTarget target) {
		fDebugTarget = target;
	}

	/**
	 * Set up exit value.
	 * 
	 * @param exitValue
	 */
	public void setExitValue(int exitValue) {
		this.fExitValue = exitValue;
	}

	/**
	 * Fires a creation event.
	 */
	protected void fireCreationEvent() {
		fireEvent(new DebugEvent(this, DebugEvent.CREATE));
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

	/**
	 * Fires the given debug event.
	 * 
	 * @param event
	 *                  debug event to fire
	 */
	protected void fireEvent(DebugEvent event) {
		DebugPlugin manager = DebugPlugin.getDefault();
		if (manager != null) {
			manager.fireDebugEventSet(new DebugEvent[] { event });
		}
	}

}
