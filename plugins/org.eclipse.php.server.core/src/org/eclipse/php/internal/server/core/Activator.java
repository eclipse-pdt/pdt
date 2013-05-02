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
 *     Aptana Inc.
 *******************************************************************************/
package org.eclipse.php.internal.server.core;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.jsch.core.IJSchService;
import org.eclipse.php.internal.server.core.tunneling.SSHTunnelSession;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends Plugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.eclipse.php.server.core"; //$NON-NLS-1$

	// Debug mode identifier
	public static final boolean isDebugMode;
	static {
		String value = Platform.getDebugOption(PLUGIN_ID + "/debug"); //$NON-NLS-1$
		isDebugMode = value != null && value.equalsIgnoreCase("true"); //$NON-NLS-1$
	}

	// The shared instance
	private static Activator plugin;

	private ServiceTracker tracker;

	/**
	 * The constructor
	 */
	public Activator() {
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.runtime.Plugins#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		tracker = new ServiceTracker(getBundle().getBundleContext(),
				IJSchService.class.getName(), null);
		tracker.open();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		SSHTunnelSession.shutdown();
		tracker.close();
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * Returns an IJSchService
	 * 
	 * @return IJSchService
	 */
	public IJSchService getJSchService() {
		return (IJSchService) tracker.getService();
	}
}
