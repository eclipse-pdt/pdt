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
package org.eclipse.php.core.tests.performance;

import org.eclipse.core.runtime.Plugin;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class PHPCorePerformanceTests extends Plugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.eclipse.php.core.performance.tests";

	// The shared instance
	private static PHPCorePerformanceTests plugin;

	private PerformanceMonitor perfMonitor;

	/**
	 * The constructor
	 */
	public PHPCorePerformanceTests() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.runtime.Plugins#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;

		PHPCorePlugin.toolkitInitialized = true;
		perfMonitor = new PerformanceMonitor(plugin.getBundle());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		perfMonitor.dispose();
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static PHPCorePerformanceTests getDefault() {
		return plugin;
	}

	public static PerformanceMonitor getPerformanceMonitor() {
		return plugin.perfMonitor;
	}
}
