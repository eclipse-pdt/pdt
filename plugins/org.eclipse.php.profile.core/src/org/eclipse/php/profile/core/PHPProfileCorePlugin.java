/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.profile.core;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.php.profile.core.engine.PHPLaunchListener;
import org.osgi.framework.BundleContext;

/**
 * PHP Zend profile core plug-in..
 */
public class PHPProfileCorePlugin extends Plugin {

	public static final String ID = "org.eclipse.php.profile.core"; //$NON-NLS-1$
	public static final int INTERNAL_ERROR = 10001;

	private PHPLaunchListener listener;

	// The shared instance.
	private static PHPProfileCorePlugin plugin;

	/**
	 * The constructor.
	 */
	public PHPProfileCorePlugin() {
		plugin = this;
	}

	/**
	 * This method is called upon plug-in activation
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		listener = new PHPLaunchListener();
		DebugPlugin.getDefault().getLaunchManager().addLaunchListener(listener);
	}

	/**
	 * This method is called when the plug-in is stopped
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
		DebugPlugin.getDefault().getLaunchManager().removeLaunchListener(listener);
		plugin = null;
	}

	public static void log(IStatus status) {
		getDefault().getLog().log(status);
	}

	public static void log(Throwable e) {
		log(new Status(IStatus.ERROR, ID, INTERNAL_ERROR, "PHPProfile plugin internal error", e)); //$NON-NLS-1$
	}

	public static void logErrorMessage(String message) {
		log(new Status(IStatus.ERROR, ID, INTERNAL_ERROR, message, null));
	}

	/**
	 * Returns the shared instance.
	 */
	public static PHPProfileCorePlugin getDefault() {
		return plugin;
	}

}
