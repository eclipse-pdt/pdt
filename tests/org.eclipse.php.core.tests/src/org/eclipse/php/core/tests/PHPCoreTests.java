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
package org.eclipse.php.core.tests;

import org.eclipse.core.runtime.Plugin;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
@SuppressWarnings("restriction")
public class PHPCoreTests extends Plugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.eclipse.php.core.tests";

	// The shared instance
	private static PHPCoreTests plugin;

	/**
	 * The constructor
	 */
	public PHPCoreTests() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.runtime.Plugins#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;

		PHPCorePlugin.toolkitInitialized = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static PHPCoreTests getDefault() {
		return plugin;
	}

}
