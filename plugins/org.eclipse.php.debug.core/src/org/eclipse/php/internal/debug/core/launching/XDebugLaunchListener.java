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

import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchesListener;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.php.internal.debug.core.xdebug.IDELayerFactory;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.model.IDBGpDebugTarget;

/**
 * This class is responsible for mapping debug session id's to the ILaunch that
 * is responsible for the session.
 * 
 * @author Shalom Gibly
 */
public class XDebugLaunchListener implements ILaunchesListener {

	// private static final String SYSTEM_DEBUG_PROPERTY =
	// "org.eclipse.php.debug.ui.activeDebugging";
	private static final String SYSTEM_DEBUG_PROPERTY = IDELayerFactory
			.getIDELayer().getSystemDebugProperty();

	private static XDebugLaunchListener instance;

	private boolean webLaunchActive;

	private XDebugLaunchListener() {
	}

	public static XDebugLaunchListener getInstance() {
		if (instance == null) {
			instance = new XDebugLaunchListener();
			DebugPlugin.getDefault().getLaunchManager().addLaunchListener(
					instance);
		}
		return instance;
	}

	public static void shutdown() {
		DebugPlugin.getDefault().getLaunchManager().removeLaunchListener(
				instance);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.core.ILaunchesListener#launchesAdded(org.eclipse.debug
	 * .core.ILaunch[])
	 */
	public void launchesAdded(ILaunch[] launches) {
		updateStatus(launches, true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.core.ILaunchesListener#launchesChanged(org.eclipse.
	 * debug.core.ILaunch[])
	 */
	public void launchesChanged(ILaunch[] launches) {
		updateStatus(launches, true);
	}

	public void launchesRemoved(ILaunch[] launches) {
		updateStatus(launches, false);
	}

	/**
	 * Update the "org.eclipse.php.debug.ui.activeDebugging" system property.
	 * This method is important for any action that is defined to be visible
	 * when a debug session is active (such as the Run to Line action).
	 * 
	 * @param launches
	 */
	public void updateStatus(ILaunch[] launches, boolean added) {
		boolean hasActiveLaunch = false;
		for (int i = 0; i < launches.length; i++) {
			ILaunch launch = launches[i];
			IDebugTarget target = launch.getDebugTarget();
			if (target instanceof IDBGpDebugTarget
					&& ((IDBGpDebugTarget) target).isWebLaunch()) {
				// this is a web launch
				webLaunchActive = added;
			}
			hasActiveLaunch |= !launch.isTerminated();
		}
		System.setProperty(SYSTEM_DEBUG_PROPERTY,
				hasActiveLaunch ? "true" : "false"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public boolean isWebLaunchActive() {
		return webLaunchActive;
	}
}
