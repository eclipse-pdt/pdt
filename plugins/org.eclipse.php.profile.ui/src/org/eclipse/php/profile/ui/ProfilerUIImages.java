/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.profile.ui;

import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.Bundle;

/**
 * PHP Profiler UI images..
 */
public class ProfilerUIImages {

	private static String ICONS_PATH = "$nl$/icons/full/"; //$NON-NLS-1$
	private static ImageRegistry fgImageRegistry = null;

	// Set of predefined Image Descriptors.
	private static final String T_OBJ = ICONS_PATH + "obj16/"; //$NON-NLS-1$
	private static final String T_ELCL = ICONS_PATH + "elcl16/"; //$NON-NLS-1$
	private static final String T_ETOOL = ICONS_PATH + "etool16/"; //$NON-NLS-1$
	private static final String T_WIZBAN = ICONS_PATH + "wizban/"; //$NON-NLS-1$

	public static final String IMG_ELCL_COLLAPSE_ALL = "IMG_ELCL_COLLAPSE_ALL"; //$NON-NLS-1$
	public static final String IMG_ELCL_EXPAND_ALL = "IMG_ELCL_EXPAND_ALL"; //$NON-NLS-1$
	public static final String IMG_ELCL_SORT = "IMG_ELCL_SORT"; //$NON-NLS-1$
	public static final String IMG_ELCL_FILTER = "IMG_ELCL_FILTER"; //$NON-NLS-1$
	public static final String IMG_ELCL_PERCENTAGE = "IMG_ELCL_PERCENTAGE"; //$NON-NLS-1$
	public static final String IMG_ELCL_GROUP_BY_FILE = "IMG_ELCL_GROUP_BY_FILE"; //$NON-NLS-1$
	public static final String IMG_ELCL_GROUP_BY_FUNCTION = "IMG_ELCL_GROUP_BY_FUNCTION"; //$NON-NLS-1$
	public static final String IMG_ELCL_GROUP_BY_CLASS = "IMG_ELCL_GROUP_BY_CLASS"; //$NON-NLS-1$
	public static final String IMG_ELCL_FUNCTION_STATISTICS = "IMG_ELCL_FUNCTION_STATISTICS"; //$NON-NLS-1$
	public static final String IMG_ELCL_CODE_COVERAGE = "IMG_ELCL_CODE_COVERAGE"; //$NON-NLS-1$
	public static final String IMG_OBJ_PROCESS = "IMG_OBJ_PROCESS"; //$NON-NLS-1$
	public static final String IMG_OBJ_REPORT = "IMG_OBJ_REPORT"; //$NON-NLS-1$
	public static final String IMG_OBJ_SORT_ASCENDING = "IMG_OBJ_SORT_ASCENDING"; //$NON-NLS-1$
	public static final String IMG_OBJ_SORT_DESCENDING = "IMG_OBJ_SORT_DESCENDING"; //$NON-NLS-1$
	public static final String IMG_OBJ_FILTER = "IMG_OBJ_FILTER"; //$NON-NLS-1$
	public static final String IMG_OBJ_STAT_FILTER = "IMG_OBJ_STAT_FILTER_OBJ"; //$NON-NLS-1$
	public static final String IMG_OBJ_PROFILE_CONF = "IMG_OBJ_PROFILE_CONF"; //$NON-NLS-1$
	public static final String IMG_ETOOL_IMPORT_WIZ = "IMG_ETOOL_IMPORT_WIZ"; //$NON-NLS-1$
	public static final String IMG_ETOOL_EXPORT_WIZ = "IMG_ETOOL_EXPORT_WIZ"; //$NON-NLS-1$
	public static final String IMG_WIZBAN_EXPORT_PROFILE_SESSIONS = "IMG_WIZBAN_EXPORT_PROFILE_SESSIONS"; //$NON-NLS-1$
	public static final String IMG_WIZBAN_IMPORT_PROFILE_SESSIONS = "IMG_WIZBAN_IMPORT_PROFILE_SESSIONS"; //$NON-NLS-1$
	public static final String IMG_WIZBAN_EXPORT_HTML_REPORT = "IMG_WIZBAN_EXPORT_HTML_REPORT"; //$NON-NLS-1$

	private static void declareImages() {
		declareRegistryImage(IMG_OBJ_PROCESS, T_OBJ + "process_obj.png"); //$NON-NLS-1$
		declareRegistryImage(IMG_OBJ_REPORT, T_OBJ + "report_obj.png"); //$NON-NLS-1$
		declareRegistryImage(IMG_OBJ_SORT_ASCENDING, T_OBJ + "sort_ascending.png"); //$NON-NLS-1$
		declareRegistryImage(IMG_OBJ_SORT_DESCENDING, T_OBJ + "sort_descending.png"); //$NON-NLS-1$
		declareRegistryImage(IMG_OBJ_FILTER, T_OBJ + "filter_obj.png"); //$NON-NLS-1$
		declareRegistryImage(IMG_OBJ_STAT_FILTER, T_OBJ + "stat_filter_obj.png"); //$NON-NLS-1$
		declareRegistryImage(IMG_OBJ_PROFILE_CONF, T_OBJ + "profiler_conf.png"); //$NON-NLS-1$
		declareRegistryImage(IMG_ELCL_COLLAPSE_ALL, T_ELCL + "collapseall.png"); //$NON-NLS-1$
		declareRegistryImage(IMG_ELCL_EXPAND_ALL, T_ELCL + "expandall.png"); //$NON-NLS-1$
		declareRegistryImage(IMG_ELCL_SORT, T_ELCL + "sort.png"); //$NON-NLS-1$
		declareRegistryImage(IMG_ELCL_FILTER, T_ELCL + "filter.png"); //$NON-NLS-1$
		declareRegistryImage(IMG_ELCL_PERCENTAGE, T_ELCL + "percentage_show.png"); //$NON-NLS-1$
		declareRegistryImage(IMG_ELCL_GROUP_BY_FILE, T_ELCL + "group_by_file.png"); //$NON-NLS-1$
		declareRegistryImage(IMG_ELCL_GROUP_BY_FUNCTION, T_ELCL + "group_by_function.png"); //$NON-NLS-1$
		declareRegistryImage(IMG_ELCL_GROUP_BY_CLASS, T_ELCL + "group_by_class.png"); //$NON-NLS-1$
		declareRegistryImage(IMG_ELCL_FUNCTION_STATISTICS, T_ELCL + "functionstatistics_view.png"); //$NON-NLS-1$
		declareRegistryImage(IMG_ELCL_CODE_COVERAGE, T_ELCL + "cov_statistic_co.png"); //$NON-NLS-1$
		declareRegistryImage(IMG_ETOOL_IMPORT_WIZ, T_ETOOL + "import_wiz.png"); //$NON-NLS-1$
		declareRegistryImage(IMG_ETOOL_EXPORT_WIZ, T_ETOOL + "export_wiz.png"); //$NON-NLS-1$
		declareRegistryImage(IMG_WIZBAN_EXPORT_PROFILE_SESSIONS, T_WIZBAN + "export_profile_session_wiz.png"); //$NON-NLS-1$
		declareRegistryImage(IMG_WIZBAN_IMPORT_PROFILE_SESSIONS, T_WIZBAN + "import_profile_session_wiz.png"); //$NON-NLS-1$
		declareRegistryImage(IMG_WIZBAN_EXPORT_HTML_REPORT, T_WIZBAN + "export_profile_report_wiz.png"); //$NON-NLS-1$
	}

	/**
	 * Returns the image managed under the given key in this registry.
	 * 
	 * @param key
	 *            the image's key
	 * @return the image managed under the given key
	 */
	public static Image get(String key) {
		return getImageRegistry().get(key);
	}

	/**
	 * Returns the <code>ImageDescriptor</code> identified by the given key, or
	 * <code>null</code> if it does not exist.
	 */
	public static ImageDescriptor getImageDescriptor(String key) {
		return getImageRegistry().getDescriptor(key);
	}

	/*
	 * Helper method to access the image registry from the JDIDebugUIPlugin class.
	 */
	static ImageRegistry getImageRegistry() {
		if (fgImageRegistry == null) {
			initializeImageRegistry();
		}
		return fgImageRegistry;
	}

	private static void initializeImageRegistry() {
		fgImageRegistry = new ImageRegistry(PlatformUI.getWorkbench().getDisplay());
		declareImages();
	}

	/**
	 * Declare an Image in the registry table.
	 * 
	 * @param key
	 *            The key to use when registering the image
	 * @param path
	 *            The path where the image can be found. This path is relative to
	 *            where this plugin class is found (i.e. typically the packages
	 *            directory)
	 */
	private final static void declareRegistryImage(String key, String path) {
		ImageDescriptor desc = ImageDescriptor.getMissingImageDescriptor();
		Bundle bundle = Platform.getBundle(ProfilerUiPlugin.ID);
		URL url = null;
		if (bundle != null) {
			url = FileLocator.find(bundle, new Path(path), null);
			desc = ImageDescriptor.createFromURL(url);
		}
		fgImageRegistry.put(key, desc);
	}
}
