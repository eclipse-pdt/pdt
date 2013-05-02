package org.eclipse.php.mylyn.ui;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class PDTMylynPlugin extends Plugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.eclipse.php.mylyn.ui"; //$NON-NLS-1$

	// The shared instance
	private static PDTMylynPlugin plugin;

	/**
	 * The constructor
	 */
	public PDTMylynPlugin() {
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
	public static PDTMylynPlugin getDefault() {
		return plugin;
	}

}
