/*******************************************************************************
 * Copyright (c) 2012, 2016 PDT Extension Group and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.ui;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.e4.core.contexts.EclipseContextFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.php.composer.core.ComposerPlugin;
import org.eclipse.php.composer.core.ComposerPreferenceConstants;
import org.eclipse.php.composer.core.log.Logger;
import org.eclipse.php.composer.core.preferences.PHPExecutableChangeListener;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class ComposerUIPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.eclipse.php.composer.ui"; //$NON-NLS-1$

	public static final Object FAMILY_COMPOSER = new Object();

	// The shared instance
	private static ComposerUIPlugin plugin;

	private IPreferenceStore corePreferenceStore;

	private IEclipseContext eclipseContext;

	/**
	 * The constructor
	 */
	public ComposerUIPlugin() {

	}

	public IPreferenceStore getCorePreferenceStore() {
		if (corePreferenceStore == null) {
			corePreferenceStore = new ScopedPreferenceStore(InstanceScope.INSTANCE, ComposerPlugin.ID);
		}

		return corePreferenceStore;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.
	 * BundleContext)
	 */
	@SuppressWarnings("deprecation")
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		eclipseContext = EclipseContextFactory.getServiceContext(context);

		try {
			PHPDebugPlugin.getDefault().getPluginPreferences().addPropertyChangeListener(
					new PHPExecutableChangeListener(ComposerPlugin.ID, ComposerPreferenceConstants.PHP_EXECUTABLE));
		} catch (Exception e) {
			Logger.logException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.
	 * BundleContext)
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
	public static ComposerUIPlugin getDefault() {
		return plugin;
	}

	public IEclipseContext getEclipseContext() {
		return eclipseContext;
	}

}
