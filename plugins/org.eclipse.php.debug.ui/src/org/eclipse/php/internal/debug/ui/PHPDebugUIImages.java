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
package org.eclipse.php.internal.debug.ui;

import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.osgi.framework.Bundle;

/**
 * Bundle of most images used by the PHP debug UI plug-in.
 * 
 * @author shalom
 */
public class PHPDebugUIImages {

	private static String ICONS_PATH = "$nl$/icon/full/"; //$NON-NLS-1$
	private static ImageRegistry fgImageRegistry = null;

	// Set of predefined Image Descriptors.
	private static final String T_OBJ = ICONS_PATH + "obj16/"; //$NON-NLS-1$
	private static final String T_OVR = ICONS_PATH + "ovr16/"; //$NON-NLS-1$
	private static final String T_ELCL = ICONS_PATH + "elcl16/"; //$NON-NLS-1$
	private static final String T_DLCL = ICONS_PATH + "dlcl16/"; //$NON-NLS-1$
	private static final String T_EVIEW = ICONS_PATH + "eview16/"; //$NON-NLS-1$
	private static final String T_WIZBAN = ICONS_PATH + "wizban/"; //$NON-NLS-1$

	public static final String IMG_OVR_CONDITIONAL_BREAKPOINT = "IMG_OVR_CONDITIONAL_BREAKPOINT"; //$NON-NLS-1$
	public static final String IMG_OVR_CONDITIONAL_BREAKPOINT_DISABLED = "IMG_OVR_CONDITIONAL_BREAKPOINT_DISABLED"; //$NON-NLS-1$
	public static final String IMG_OVR_MEMBER_STATIC = "IMG_OVR_MEMBER_STATIC"; //$NON-NLS-1$
	public static final String IMG_OVR_MEMBER_CONSTANT = "IMG_OVR_MEMBER_CONSTANT"; //$NON-NLS-1$

	public static final String IMG_WIZBAN_XDEBUG_CONF = "IMG_WIZBAN_XDEBUG_CONF"; //$NON-NLS-1$
	public static final String IMG_WIZBAN_ZEND_DEBUGGER_CONF = "IMG_WIZBAN_ZEND_DEBUGGER_CONF"; //$NON-NLS-1$
	public static final String IMG_WIZBAN_PHPEXE = "IMG_WIZBAN_PHPEXE"; //$NON-NLS-1$
	public static final String IMG_WIZBAN_DEBUG_PHPEXE = "IMG_WIZBAN_DEBUG_PHPEXE"; //$NON-NLS-1$
	public static final String IMG_WIZBAN_DEBUG_SERVER = "IMG_WIZBAN_DEBUG_SERVER"; //$NON-NLS-1$
	public static final String IMG_WIZBAN_MAPPING_SERVER = "IMG_WIZBAN_MAPPING_SERVER"; //$NON-NLS-1$

	public static final String IMG_OBJ_DEBUG_CONF = "IMG_OBJ_DEBUG_CONF"; //$NON-NLS-1$
	public static final String IMG_OBJ_PHP_EXE = "IMG_OBJ_PHP_EXE"; //$NON-NLS-1$
	public static final String IMG_OBJ_PHP_EXE_LAUNCH = "IMG_OBJ_PHP_EXE_LAUNCH"; //$NON-NLS-1$
	public static final String IMG_OBJ_PATH_MAPPING = "IMG_OBJ_PATH_MAPPING"; //$NON-NLS-1$
	public static final String IMG_OBJ_MEMBER_PUBLIC_ACCESS = "IMG_OBJ_MEMBER_PUBLIC_ACCESS"; //$NON-NLS-1$
	public static final String IMG_OBJ_MEMBER_PROTECTED_ACCESS = "IMG_OBJ_MEMBER_PROTECTED_ACCESS"; //$NON-NLS-1$
	public static final String IMG_OBJ_MEMBER_PRIVATE_ACCESS = "IMG_OBJ_MEMBER_PRIVATE_ACCESS"; //$NON-NLS-1$
	public static final String IMG_OBJ_MEMBER_ARRAY = "IMG_OBJ_MEMBER_ARRAY"; //$NON-NLS-1$
	public static final String IMG_OBJ_MEMBER_VIRTUAL_CONTAINER = "IMG_OBJ_MEMBER_VIRTUAL_CONTAINER"; //$NON-NLS-1$
	public static final String IMG_OBJ_MEMBER_LOCAL = "IMG_OBJ_MEMBER_LOCAL"; //$NON-NLS-1$
	public static final String IMG_OBJ_MEMBER_SUPER_GLOBAL = "IMG_OBJ_MEMBER_SUPER_GLOBAL"; //$NON-NLS-1$
	public static final String IMG_OBJ_MEMBER_VIRTUAL_CLASS = "IMG_OBJ_MEMBER_VIRTUAL_CLASS"; //$NON-NLS-1$
	public static final String IMG_OBJ_MEMBER_VIRTUAL_LENGTH = "IMG_OBJ_MEMBER_VIRTUAL_LENGTH"; //$NON-NLS-1$
	public static final String IMG_OBJ_MEMBER_VIRTUAL_UNINIT = "IMG_OBJ_MEMBER_VIRTUAL_UNINIT"; //$NON-NLS-1$
	public static final String IMG_OBJ_MEMBER_VIRTUAL_ARRAY = "IMG_OBJ_MEMBER_VIRTUAL_ARRAY"; //$NON-NLS-1$
	public static final String IMG_OBJ_EXCEPTION_ANNOTATION = "IMG_OBJ_EXCEPTION_ANNOTATION"; //$NON-NLS-1$
	public static final String IMG_OBJ_ERROR_ANNOTATION = "IMG_OBJ_ERROR_ANNOTATION"; //$NON-NLS-1$

	public static final String IMG_ELCL_EXCEPTION_BREAKPOINT = "IMG_ELCL_EXCEPTION_BREAKPOINT"; //$NON-NLS-1$
	public static final String IMG_DLCL_EXCEPTION_BREAKPOINT = "IMG_DLCL_EXCEPTION_BREAKPOINT"; //$NON-NLS-1$
	public static final String IMG_ELCL_ERROR_BREAKPOINT = "IMG_ELCL_ERROR_BREAKPOINT"; //$NON-NLS-1$
	public static final String IMG_DLCL_ERROR_BREAKPOINT = "IMG_DLCL_ERROR_BREAKPOINT"; //$NON-NLS-1$

	public static final String IMG_EVIEW_EVENT_PREV = "IMG_EVIEW_EVENT_PREVIOUS"; //$NON-NLS-1$
	public static final String IMG_EVIEW_EVENT_NEXT = "IMG_EVIEW_EVENT_NEXT"; //$NON-NLS-1$
	public static final String IMG_OBJ_REMOTE_FILE = "IMG_OBJ_REMOTE_FILE"; //$NON-NLS-1$

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
	 * Helper method to access the image registry from the JDIDebugUIPlugin
	 * class.
	 */
	static ImageRegistry getImageRegistry() {
		if (fgImageRegistry == null) {
			initializeImageRegistry();
		}
		return fgImageRegistry;
	}

	private static void initializeImageRegistry() {
		fgImageRegistry = new ImageRegistry(PHPDebugUIPlugin.getStandardDisplay());
		declareImages();
	}

	private static void declareImages() {
		declareRegistryImage(IMG_OVR_CONDITIONAL_BREAKPOINT, T_OVR + "conditional_ovr.png"); //$NON-NLS-1$
		declareRegistryImage(IMG_OVR_CONDITIONAL_BREAKPOINT_DISABLED, T_OVR + "conditional_ovr_disabled.png"); //$NON-NLS-1$
		declareRegistryImage(IMG_OVR_MEMBER_CONSTANT, T_OVR + "constant_ovr.png"); //$NON-NLS-1$
		declareRegistryImage(IMG_OVR_MEMBER_STATIC, T_OVR + "static_ovr.png"); //$NON-NLS-1$
		declareRegistryImage(IMG_WIZBAN_XDEBUG_CONF, T_WIZBAN + "xdebug_conf_wiz.png"); //$NON-NLS-1$
		declareRegistryImage(IMG_WIZBAN_ZEND_DEBUGGER_CONF, T_WIZBAN + "zend_debugger_conf_wiz.png"); //$NON-NLS-1$
		declareRegistryImage(IMG_WIZBAN_PHPEXE, T_WIZBAN + "php_exe_wiz.png"); //$NON-NLS-1$
		declareRegistryImage(IMG_WIZBAN_DEBUG_PHPEXE, T_WIZBAN + "php_exe_debug_wiz.png"); //$NON-NLS-1$
		declareRegistryImage(IMG_WIZBAN_DEBUG_SERVER, T_WIZBAN + "server_debug_wiz.png"); //$NON-NLS-1$
		declareRegistryImage(IMG_WIZBAN_MAPPING_SERVER, T_WIZBAN + "server_mapping_wiz.png"); //$NON-NLS-1$
		declareRegistryImage(IMG_OBJ_DEBUG_CONF, T_OBJ + "debug_conf.png"); //$NON-NLS-1$
		declareRegistryImage(IMG_OBJ_PHP_EXE, T_OBJ + "php_exe.png"); //$NON-NLS-1$
		declareRegistryImage(IMG_OBJ_PHP_EXE_LAUNCH, T_OBJ + "php_exec.png"); //$NON-NLS-1$
		declareRegistryImage(IMG_OBJ_PATH_MAPPING, T_OBJ + "path_mapping.png"); //$NON-NLS-1$
		declareRegistryImage(IMG_OBJ_MEMBER_LOCAL, T_OBJ + "member_local.png"); //$NON-NLS-1$
		declareRegistryImage(IMG_OBJ_MEMBER_SUPER_GLOBAL, T_OBJ + "member_super_global.png"); //$NON-NLS-1$
		declareRegistryImage(IMG_OBJ_MEMBER_VIRTUAL_CLASS, T_OBJ + "member_virtual_class.png"); //$NON-NLS-1$
		declareRegistryImage(IMG_OBJ_MEMBER_VIRTUAL_LENGTH, T_OBJ + "member_virtual_length.png"); //$NON-NLS-1$
		declareRegistryImage(IMG_OBJ_MEMBER_VIRTUAL_UNINIT, T_OBJ + "member_virtual_uninit.png"); //$NON-NLS-1$
		declareRegistryImage(IMG_OBJ_MEMBER_VIRTUAL_ARRAY, T_OBJ + "member_virtual_array.png"); //$NON-NLS-1$
		declareRegistryImage(IMG_OBJ_MEMBER_ARRAY, T_OBJ + "member_array.png"); //$NON-NLS-1$
		declareRegistryImage(IMG_OBJ_MEMBER_VIRTUAL_CONTAINER, T_OBJ + "member_virtual_container.png"); //$NON-NLS-1$
		declareRegistryImage(IMG_OBJ_MEMBER_PUBLIC_ACCESS, T_OBJ + "member_public_access.png"); //$NON-NLS-1$
		declareRegistryImage(IMG_OBJ_MEMBER_PROTECTED_ACCESS, T_OBJ + "member_protected_access.png"); //$NON-NLS-1$
		declareRegistryImage(IMG_OBJ_MEMBER_PRIVATE_ACCESS, T_OBJ + "member_private_access.png"); //$NON-NLS-1$
		declareRegistryImage(IMG_OBJ_ERROR_ANNOTATION, T_OBJ + "perror_ann.png"); //$NON-NLS-1$
		declareRegistryImage(IMG_OBJ_EXCEPTION_ANNOTATION, T_OBJ + "pexception_ann.png"); //$NON-NLS-1$
		declareRegistryImage(IMG_ELCL_EXCEPTION_BREAKPOINT, T_ELCL + "pexception_bp.png"); //$NON-NLS-1$
		declareRegistryImage(IMG_DLCL_EXCEPTION_BREAKPOINT, T_DLCL + "pexception_bp.png"); //$NON-NLS-1$
		declareRegistryImage(IMG_ELCL_ERROR_BREAKPOINT, T_ELCL + "perror_bp.png"); //$NON-NLS-1$
		declareRegistryImage(IMG_DLCL_ERROR_BREAKPOINT, T_DLCL + "perror_bp.png"); //$NON-NLS-1$

		declareRegistryImage(IMG_EVIEW_EVENT_PREV, T_EVIEW + "code_coverage_prev.png"); //$NON-NLS-1$
		declareRegistryImage(IMG_EVIEW_EVENT_NEXT, T_EVIEW + "code_coverage_next.png"); //$NON-NLS-1$
		declareRegistryImage(IMG_OBJ_REMOTE_FILE, T_OBJ + "remote_file.png"); //$NON-NLS-1$
	}

	/**
	 * Declare an Image in the registry table.
	 * 
	 * @param key
	 *            The key to use when registering the image
	 * @param path
	 *            The path where the image can be found. This path is relative
	 *            to where this plugin class is found (i.e. typically the
	 *            packages directory)
	 */
	private final static void declareRegistryImage(String key, String path) {
		ImageDescriptor desc = ImageDescriptor.getMissingImageDescriptor();
		Bundle bundle = Platform.getBundle(PHPDebugUIPlugin.ID);
		URL url = null;
		if (bundle != null) {
			url = FileLocator.find(bundle, new Path(path), null);
			desc = ImageDescriptor.createFromURL(url);
		}
		fgImageRegistry.put(key, desc);
	}
}
