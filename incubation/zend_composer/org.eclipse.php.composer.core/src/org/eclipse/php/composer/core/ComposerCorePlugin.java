/*******************************************************************************
 * Copyright (c) 2014, 2016 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.core;

import org.eclipse.core.net.proxy.IProxyService;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Composer core plug-in.
 * 
 * @author Wojciech Galanciak, 2013
 * 
 */
public class ComposerCorePlugin extends Plugin {

	public static final String PLUGIN_ID = "org.eclipse.php.composer.core"; //$NON-NLS-1$

	private static ComposerCorePlugin plugin;

	private static ServiceTracker<?, ?> proxyTracker;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext bundleContext) throws Exception {
		super.start(bundleContext);
		plugin = this;
		proxyTracker = new ServiceTracker<Object, Object>(FrameworkUtil.getBundle(this.getClass()).getBundleContext(),
				IProxyService.class.getName(), null);
		proxyTracker.open();

		// workaround to initalize preferences in core
		new ComposerPreferences().initializeDefaultPreferences();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		super.stop(bundleContext);
		plugin = null;
		proxyTracker.close();
	}

	public static IProxyService getProxyService() {
		return (IProxyService) proxyTracker.getService();
	}

	/**
	 * Returns the shared instance.
	 */
	public static ComposerCorePlugin getDefault() {
		return plugin;
	}

	public static void logError(Throwable e) {
		log(createStatus(e.getMessage(), e));
	}

	public static void logError(String message, Throwable e) {
		log(createStatus(message, e));
	}

	public static IStatus createStatus(String message, Throwable exception) {
		return new Status(IStatus.ERROR, PLUGIN_ID, message, exception);
	}

	public static void log(IStatus status) {
		getDefault().getLog().log(status);
	}

}
