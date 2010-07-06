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
/**
 * 
 */
package org.eclipse.php.internal.debug.core.launching;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.Launch;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.ISourceLocator;

/**
 * A PHP Launch. This launch is more flexible in terms of terminating launches.
 * 
 * @author shalom
 */
public class PHPLaunch extends Launch {

	private boolean pretendsRunning;

	/**
	 * Constructs a launch with the specified attributes.
	 * 
	 * @param launchConfiguration
	 *            the configuration that was launched
	 * @param mode
	 *            the mode of this launch - run or debug (constants defined by
	 *            <code>ILaunchManager</code>)
	 * @param locator
	 *            the source locator to use for this debug session, or
	 *            <code>null</code> if not supported
	 */
	public PHPLaunch(ILaunchConfiguration launchConfiguration, String mode,
			ISourceLocator locator) {
		super(launchConfiguration, mode, locator);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.Launch#canTerminate()
	 */
	public boolean canTerminate() {
		return !isTerminated();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.Launch#isTerminated()
	 */
	public boolean isTerminated() {
		if (pretendsRunning) {
			return false;
		}

		if (getProcesses0().isEmpty() && getDebugTargets0().isEmpty()) {
			return true;
		}

		Iterator processes = getProcesses0().iterator();
		while (processes.hasNext()) {
			IProcess process = (IProcess) processes.next();
			if (!process.isTerminated()) {
				return false;
			}
		}

		Iterator targets = getDebugTargets0().iterator();
		while (targets.hasNext()) {
			IDebugTarget target = (IDebugTarget) targets.next();
			if (!(target.isTerminated() || target.isDisconnected())) {
				return false;
			}
		}

		return true;
	}

	public Object[] getChildren() {
		// screen any dead targets in case we have at least one live target.
		List targets = getDebugTargets0();
		List toRemove = new ArrayList(targets.size());
		if (targets.size() > 1) {
			IDebugTarget[] targetsArr = new IDebugTarget[targets.size()];
			targets.toArray(targetsArr);
			for (int i = 0; i < targetsArr.length; i++) {
				if (targetsArr[i].isTerminated()) {
					toRemove.add(targetsArr[i]);
				}
			}
			if (toRemove.size() < targets.size()) {
				// we have some connected and some terminated.
				// remove the terminated.
				targets.removeAll(toRemove);
			}
		}
		return super.getChildren();
	}

	public void pretendRunning(boolean running) {
		this.pretendsRunning = running;
	}

}
