/*******************************************************************************
 * Copyright (c) 2017 Alex Xu and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Alex Xu - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.server.ui.builtin;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class PHPServerUIPlugin extends AbstractUIPlugin {

	protected Map<String, ImageDescriptor> imageDescriptors = new HashMap<>();

	// base url for icons
	private static URL ICON_BASE_URL;

	private static final String URL_OBJ = "full/obj16/"; //$NON-NLS-1$
	private static final String URL_WIZBAN = "full/wizban/"; //$NON-NLS-1$

	// The plug-in ID
	public static final String PLUGIN_ID = "org.eclipse.php.builtin.server.ui"; //$NON-NLS-1$

	public static final String IMG_WIZ_PHP_SERVER = "IMG_WIZ_PHP_SERVER"; //$NON-NLS-1$
	public static final String IMG_MODULE_PHP = "IMG_MODULE_PHP"; //$NON-NLS-1$
	public static final String IMG_PORT = "IMG_PORT"; //$NON-NLS-1$

	// The shared instance
	private static PHPServerUIPlugin plugin;

	/**
	 * The constructor
	 */
	public PHPServerUIPlugin() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.
	 * BundleContext)
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.
	 * BundleContext)
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static PHPServerUIPlugin getDefault() {
		return plugin;
	}

	@Override
	protected ImageRegistry createImageRegistry() {
		ImageRegistry registry = new ImageRegistry();

		registerImage(registry, IMG_WIZ_PHP_SERVER, URL_WIZBAN + "server_wiz.png"); //$NON-NLS-1$
		registerImage(registry, IMG_MODULE_PHP, URL_WIZBAN + "php_project_obj.png"); //$NON-NLS-1$
		registerImage(registry, IMG_PORT, URL_OBJ + "port.gif"); //$NON-NLS-1$

		return registry;
	}

	/**
	 * Return the image with the given key from the image registry.
	 * 
	 * @param key
	 *            java.lang.String
	 * @return org.eclipse.jface.parts.IImage
	 */
	public static Image getImage(String key) {
		return getDefault().getImageRegistry().get(key);
	}

	/**
	 * Return the image with the given key from the image registry.
	 * 
	 * @param key
	 *            java.lang.String
	 * @return org.eclipse.jface.parts.IImage
	 */
	public static ImageDescriptor getImageDescriptor(String key) {
		try {
			getDefault().getImageRegistry();
			return (ImageDescriptor) getDefault().imageDescriptors.get(key);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Register an image with the registry.
	 * 
	 * @param key
	 *            java.lang.String
	 * @param partialURL
	 *            java.lang.String
	 */
	private void registerImage(ImageRegistry registry, String key, String partialURL) {
		if (ICON_BASE_URL == null) {
			String pathSuffix = "icons/"; //$NON-NLS-1$
			ICON_BASE_URL = plugin.getBundle().getEntry(pathSuffix);
		}

		try {
			ImageDescriptor id = ImageDescriptor.createFromURL(new URL(ICON_BASE_URL, partialURL));
			registry.put(key, id);
			imageDescriptors.put(key, id);
		} catch (Exception e) {
			Trace.trace(Trace.WARNING, "Error registering image", e); //$NON-NLS-1$
		}
	}

}
