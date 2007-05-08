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
/**
 * 
 */
package org.eclipse.php.internal.debug.core.launching;

import java.util.Iterator;

import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.Launch;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.ISourceLocator;

/**
 * A PHP Launch.
 * This launch is more flexible in terms of terminating launches.
 * 
 * @author shalom
 */
public class PHPLaunch extends Launch {

	/**
	 * Constructs a launch with the specified attributes.
	 *
	 * @param launchConfiguration the configuration that was launched
	 * @param mode the mode of this launch - run or debug (constants
	 *  defined by <code>ILaunchManager</code>)
	 * @param locator the source locator to use for this debug session, or
	 * 	<code>null</code> if not supported
	 */
	public PHPLaunch(ILaunchConfiguration launchConfiguration, String mode, ISourceLocator locator) {
		super(launchConfiguration, mode, locator);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.core.Launch#canTerminate()
	 */
	public boolean canTerminate() {
		return !isTerminated();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.core.Launch#isTerminated()
	 */
	public boolean isTerminated() {
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

}
