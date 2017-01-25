/*******************************************************************************
 * Copyright (c) 2009, 2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.ui.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map.Entry;

import org.eclipse.dltk.ui.DLTKPluginImages;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;

/**
 * Bundle of most images used by the PHP plug-in.
 */
public class PHPPluginImages {

	private static final String NAME_PREFIX = "org.eclipse.php.ui."; //$NON-NLS-1$
	private static final int NAME_PREFIX_LENGTH = NAME_PREFIX.length();

	private static URL fgIconBaseURL = null;

	// Determine display depth. If depth > 4 then we use high color images.
	// Otherwise low color
	// images are used
	static {
		fgIconBaseURL = PHPUiPlugin.getDefault().getBundle().getEntry("/icons/full/"); //$NON-NLS-1$
	}

	// The plug-in registry
	private static ImageRegistry fgImageRegistry = null;
	private static HashMap<String, ImageDescriptor> fgAvoidSWTErrorMap = null;

	private static final String T_OBJ = "obj16"; //$NON-NLS-1$
	private static final String T_OVR = "ovr16"; //$NON-NLS-1$
	public static final String T_WIZBAN = "wizban"; //$NON-NLS-1$
	private static final String T_ELCL = "elcl16"; //$NON-NLS-1$
	private static final String T_DLCL = "dlcl16"; //$NON-NLS-1$
	private static final String CLASS_BROWSER = "classBrowser"; //$NON-NLS-1$

	public static final String IMG_MISC_PUBLIC = NAME_PREFIX + "phpfunctiondata_pub.png"; //$NON-NLS-1$
	public static final String IMG_MISC_PROTECTED = NAME_PREFIX + "phpfunctiondata_pro.png"; //$NON-NLS-1$
	public static final String IMG_MISC_PRIVATE = NAME_PREFIX + "phpfunctiondata_pri.png"; //$NON-NLS-1$

	public static final String IMG_FIELD_PUBLIC = NAME_PREFIX + "phpuservar_pub.png"; //$NON-NLS-1$
	public static final String IMG_FIELD_PROTECTED = NAME_PREFIX + "phpuservar_pro.png"; //$NON-NLS-1$
	public static final String IMG_FIELD_PRIVATE = NAME_PREFIX + "phpuservar_pri.png"; //$NON-NLS-1$
	public static final String IMG_FIELD_DEFAULT = NAME_PREFIX + "phpuservar_pub.png"; //$NON-NLS-1$
	public static final String IMG_OBJS_INTERFACE = NAME_PREFIX + "phpinterfacedata.png"; //$NON-NLS-1$
	public static final String IMG_CONSTANT = NAME_PREFIX + "phpconstantdata.png"; //$NON-NLS-1$
	public static final String IMG_OBJS_CUNIT = NAME_PREFIX + "phpfile.png"; //$NON-NLS-1$
	public static final String IMG_OBJS_CUNIT_RESOURCE = NAME_PREFIX + "php_resource_obj.png"; //$NON-NLS-1$
	public static final String IMG_OBJS_PHP_FOLDER = NAME_PREFIX + "folder_opened.png"; //$NON-NLS-1$
	public static final String IMG_OBJS_PHP_FILE = NAME_PREFIX + "phpfile.png"; //$NON-NLS-1$
	public static final String IMG_OBJS_EMPTY_PACK_RESOURCE = NAME_PREFIX + "empty_pack_fldr_obj.gif"; //$NON-NLS-1$
	public static final String IMG_OBJS_PHPFOLDER_ROOT = NAME_PREFIX + "folderroot_obj.png"; //$NON-NLS-1$
	public static final String IMG_OBJS_PHP_LIBFOLDER = NAME_PREFIX + "libfolder_obj.png"; //$NON-NLS-1$
	public static final String IMG_OBJS_UNKNOWN = NAME_PREFIX + "unknown_obj.png"; //$NON-NLS-1$
	public static final String IMG_OBJS_PHP_PROJECT = NAME_PREFIX + "php_project_obj.png"; //$NON-NLS-1$
	public static final String IMG_OBJS_PHP_CLASSES_GROUP = NAME_PREFIX + "class_group.png"; //$NON-NLS-1$
	public static final String IMG_OBJS_PHP_CONSTANTS_GROUP = NAME_PREFIX + "const_group.png"; //$NON-NLS-1$
	public static final String IMG_OBJS_PHP_FUNCTIONS_GROUP = NAME_PREFIX + "func_group.png"; //$NON-NLS-1$
	public static final String IMG_OBJS_PHP_NAMESPACES_GROUP = NAME_PREFIX + "namespace_group.png"; //$NON-NLS-1$
	public static final String IMG_OBJS_OCCURRENCES = NAME_PREFIX + "searchm_obj.png"; //$NON-NLS-1$
	public static final String IMG_OBJS_TRAIT = NAME_PREFIX + "trait_obj.png"; //$NON-NLS-1$

	public static final String IMG_OBJS_EXTZIP = NAME_PREFIX + "zip_l_obj.png"; //$NON-NLS-1$
	public static final String IMG_OBJS_ENV_VAR = NAME_PREFIX + "envvar_obj.png"; //$NON-NLS-1$
	public static final String IMG_OBJS_LIBRARY = NAME_PREFIX + "library_obj.png"; //$NON-NLS-1$

	public static final String IMG_TEMPLATE = NAME_PREFIX + "phptpldata16.png"; //$NON-NLS-1$
	public static final String IMG_OBJS_EXTJAR = NAME_PREFIX + "jar_l_obj.png"; //$NON-NLS-1$

	public static final String IMG_CORRECTION_MULTI_FIX = NAME_PREFIX + "correction_multi_fix.gif"; //$NON-NLS-1$

	public static final ImageDescriptor DESC_MISC_PUBLIC = createManaged(T_OBJ, IMG_MISC_PUBLIC);
	public static final ImageDescriptor DESC_MISC_PROTECTED = createManaged(T_OBJ, IMG_MISC_PROTECTED);
	public static final ImageDescriptor DESC_MISC_PRIVATE = createManaged(T_OBJ, IMG_MISC_PRIVATE);

	public static final ImageDescriptor DESC_FIELD_PUBLIC = createManaged(T_OBJ, IMG_FIELD_PUBLIC);
	public static final ImageDescriptor DESC_FIELD_PROTECTED = createManaged(T_OBJ, IMG_FIELD_PROTECTED);
	public static final ImageDescriptor DESC_FIELD_PRIVATE = createManaged(T_OBJ, IMG_FIELD_PRIVATE);
	public static final ImageDescriptor DESC_FIELD_DEFAULT = createManaged(T_OBJ, IMG_FIELD_DEFAULT);
	public static final ImageDescriptor DESC_OBJS_CUNIT = createManaged(T_OBJ, IMG_OBJS_CUNIT);
	public static final ImageDescriptor DESC_OBJS_CUNIT_RESOURCE = createManaged(T_OBJ, IMG_OBJS_CUNIT_RESOURCE);
	public static final ImageDescriptor DESC_OBJS_EMPTY_PHP_FOLDER_RESOURCES = createManaged(T_OBJ,
			IMG_OBJS_EMPTY_PACK_RESOURCE);
	public static final ImageDescriptor DESC_OBJS_PHPFOLDER_ROOT = createManaged(T_OBJ, IMG_OBJS_PHPFOLDER_ROOT);
	public static final ImageDescriptor DESC_OBJS_PHP_LIBFOLDER = createManaged(T_OBJ, IMG_OBJS_PHP_LIBFOLDER);
	public static final ImageDescriptor DESC_OBJ_PHP_CLASSES_GROUP = createManaged(T_OBJ, IMG_OBJS_PHP_CLASSES_GROUP);
	public static final ImageDescriptor DESC_OBJ_PHP_CONSTANTS_GROUP = createManaged(T_OBJ,
			IMG_OBJS_PHP_CONSTANTS_GROUP);
	public static final ImageDescriptor DESC_OBJ_PHP_FUNCTIONS_GROUP = createManaged(T_OBJ,
			IMG_OBJS_PHP_FUNCTIONS_GROUP);
	public static final ImageDescriptor DESC_OBJ_PHP_NAMESPACES_GROUP = createManaged(T_OBJ,
			IMG_OBJS_PHP_NAMESPACES_GROUP);
	public static final ImageDescriptor DESC_OBJS_OCCURRENCES = createManaged(T_OBJ, IMG_OBJS_OCCURRENCES);
	//
	public static final ImageDescriptor DESC_OBJS_INTERFACE = createManaged(T_OBJ, IMG_OBJS_INTERFACE);
	public static final ImageDescriptor DESC_CONSTANT = createManaged(T_OBJ, IMG_CONSTANT);
	public static final ImageDescriptor DESC_OBJS_CLASSALT = DLTKPluginImages.DESC_OBJS_CLASS;
	public static final ImageDescriptor DESC_OBJS_UNKNOWN = createManaged(T_OBJ, IMG_OBJS_UNKNOWN);
	public static final ImageDescriptor DESC_OVR_RUN = create(T_OVR, "run_co.gif"); //$NON-NLS-1$
	public static final ImageDescriptor DESC_OVR_WARNING = DLTKPluginImages.DESC_OVR_WARNING;
	public static final ImageDescriptor DESC_OVR_ERROR = DLTKPluginImages.DESC_OVR_ERROR;
	public static final ImageDescriptor DESC_OVR_OVERRIDES = DLTKPluginImages.DESC_OVR_OVERRIDES;
	public static final ImageDescriptor DESC_OVR_CONSTANT = create(T_OVR, "constant_co.png"); //$NON-NLS-1$
	public static final ImageDescriptor DESC_OVR_ABSTRACT = DLTKPluginImages.DESC_OVR_ABSTRACT;
	public static final ImageDescriptor DESC_OVR_FINAL = DLTKPluginImages.DESC_OVR_FINAL;
	public static final ImageDescriptor DESC_OVR_STATIC = DLTKPluginImages.DESC_OVR_STATIC;

	public static final ImageDescriptor DESC_OBJ_OVERRIDES = create(T_OBJ, "over_co.png"); //$NON-NLS-1$
	public static final ImageDescriptor DESC_OBJ_IMPLEMENTS = create(T_OBJ, "implm_co.png"); //$NON-NLS-1$

	public static final ImageDescriptor DESC_WIZBAN_ADD_PHP_PROJECT = create(T_WIZBAN, "newphpprj_wiz.png"); //$NON-NLS-1$
	public static final ImageDescriptor DESC_WIZBAN_ADD_PHP_FILE = create(T_WIZBAN, "newpfile_wiz.png"); //$NON-NLS-1$

	public static final ImageDescriptor DESC_TEMPLATE = createManaged(CLASS_BROWSER, IMG_TEMPLATE);

	public static final ImageDescriptor DESC_OBJS_LIBRARY = createManaged(T_OBJ, IMG_OBJS_LIBRARY);
	public static final ImageDescriptor DESC_OBJS_TRAIT = createManaged(T_OBJ, IMG_OBJS_TRAIT);

	public static final ImageDescriptor DESC_DLCL_CONFIGURE_ANNOTATIONS = create(T_DLCL, "configure_annotations.gif"); //$NON-NLS-1$
	public static final ImageDescriptor DESC_ELCL_CONFIGURE_ANNOTATIONS = create(T_ELCL, "configure_annotations.gif"); //$NON-NLS-1$

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
	 * Sets the three image descriptors for enabled, disabled, and hovered to an
	 * action. The actions are retrieved from the *tool16 folders.
	 * 
	 * @param action
	 *            the action
	 * @param iconName
	 *            the icon name
	 */
	public static void setToolImageDescriptors(IAction action, String iconName) {
		setImageDescriptors(action, "tool16", iconName); //$NON-NLS-1$
	}

	/**
	 * Sets the three image descriptors for enabled, disabled, and hovered to an
	 * action. The actions are retrieved from the *lcl16 folders.
	 * 
	 * @param action
	 *            the action
	 * @param iconName
	 *            the icon name
	 */
	public static void setLocalImageDescriptors(IAction action, String iconName) {
		setImageDescriptors(action, "lcl16", iconName); //$NON-NLS-1$
	}

	/*
	 * Helper method to access the image registry from the PHPPlugin class.
	 */
	static ImageRegistry getImageRegistry() {
		if (fgImageRegistry == null) {
			fgImageRegistry = new ImageRegistry();
			for (Entry<String, ImageDescriptor> entry : fgAvoidSWTErrorMap.entrySet()) {
				fgImageRegistry.put(entry.getKey(), entry.getValue());
			}
			fgAvoidSWTErrorMap = null;
		}
		return fgImageRegistry;
	}

	// ---- Helper methods to access icons on the file system
	// --------------------------------------

	private static void setImageDescriptors(IAction action, String type, String relPath) {

		try {
			ImageDescriptor id = ImageDescriptor.createFromURL(makeIconFileURL("d" + type, relPath)); //$NON-NLS-1$
			if (id != null)
				action.setDisabledImageDescriptor(id);
		} catch (MalformedURLException e) {
		}

		/*
		 * try { ImageDescriptor id=
		 * ImageDescriptor.createFromURL(makeIconFileURL("c" + type, relPath));
		 * if (id != null) action.setHoverImageDescriptor(id); } catch
		 * (MalformedURLException e) { }
		 */

		ImageDescriptor descriptor = create("e" + type, relPath); //$NON-NLS-1$
		action.setHoverImageDescriptor(descriptor);
		action.setImageDescriptor(descriptor);
	}

	private static ImageDescriptor createManaged(String prefix, String name) {
		return createManaged(prefix, name, false, 0, null);
	}

	private static ImageDescriptor createManaged(String prefix, String name, boolean createAsComposite, int flags,
			Point size) {
		try {
			ImageDescriptor result = ImageDescriptor
					.createFromURL(makeIconFileURL(prefix, name.substring(NAME_PREFIX_LENGTH)));
			if (createAsComposite) {
				result = new PHPElementImageDescriptor(result, flags, size);
			}
			if (fgAvoidSWTErrorMap == null) {
				fgAvoidSWTErrorMap = new HashMap<String, ImageDescriptor>();
			}
			fgAvoidSWTErrorMap.put(name, result);
			if (fgImageRegistry != null) {
				PHPUiPlugin.logErrorMessage("Image registry already defined"); //$NON-NLS-1$
			}
			return result;
		} catch (MalformedURLException e) {
			return ImageDescriptor.getMissingImageDescriptor();
		}
	}

	public static ImageDescriptor create(String prefix, String name) {
		try {
			return ImageDescriptor.createFromURL(makeIconFileURL(prefix, name));
		} catch (MalformedURLException e) {
			return ImageDescriptor.getMissingImageDescriptor();
		}
	}

	public static URL makeIconFileURL(String prefix, String name) throws MalformedURLException {
		if (fgIconBaseURL == null)
			throw new MalformedURLException();

		StringBuilder buffer = new StringBuilder(prefix);
		buffer.append('/');
		buffer.append(name);
		return new URL(fgIconBaseURL, buffer.toString());
	}

	public static ImageDescriptor create(URL fgIconBaseURL, String prefix, String name) {
		try {
			return ImageDescriptor.createFromURL(makeIconFileURL(fgIconBaseURL, prefix, name));
		} catch (MalformedURLException e) {
			return ImageDescriptor.getMissingImageDescriptor();
		}
	}

	public static URL makeIconFileURL(URL fgIconBaseURL, String prefix, String name) throws MalformedURLException {
		if (fgIconBaseURL == null)
			throw new MalformedURLException();

		StringBuilder buffer = new StringBuilder(prefix);
		buffer.append('/');
		buffer.append(name);
		return new URL(fgIconBaseURL, buffer.toString());
	}
}
