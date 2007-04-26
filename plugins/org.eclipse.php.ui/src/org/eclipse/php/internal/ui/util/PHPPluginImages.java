/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;

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

	// Determine display depth. If depth > 4 then we use high color images. Otherwise low color
	// images are used
	static {
		fgIconBaseURL = PHPUiPlugin.getDefault().getBundle().getEntry("/icons/full/"); //$NON-NLS-1$
	}

	// The plug-in registry
	private static ImageRegistry fgImageRegistry = null;
	private static HashMap fgAvoidSWTErrorMap = null;

	private static final String T_OBJ = "obj16"; //$NON-NLS-1$
	private static final String T_OVR = "ovr16"; //$NON-NLS-1$
	private static final String T_WIZBAN = "wizban"; //$NON-NLS-1$
	private static final String T_ELCL = "elcl16"; //$NON-NLS-1$
	private static final String T_DLCL = "dlcl16"; //$NON-NLS-1$
	private static final String T_ETOOL = "etool16"; //$NON-NLS-1$
	private static final String T_EVIEW = "eview16"; //$NON-NLS-1$
	private static final String CLASS_BROWSER = "classBrowser"; //$NON-NLS-1$

	public static final String IMG_MISC_PUBLIC = NAME_PREFIX + "phpfunctiondata_pub.gif"; //$NON-NLS-1$
	public static final String IMG_MISC_PROTECTED = NAME_PREFIX + "phpfunctiondata_pro.gif"; //$NON-NLS-1$
	public static final String IMG_MISC_PRIVATE = NAME_PREFIX + "phpfunctiondata_pri.gif"; //$NON-NLS-1$
	public static final String IMG_MISC_DEFAULT = NAME_PREFIX + "phpfunctiondata_pub.gif"; //$NON-NLS-1$

	public static final String IMG_FIELD_PUBLIC = NAME_PREFIX + "phpuservar_pub.gif"; //$NON-NLS-1$
	public static final String IMG_FIELD_PROTECTED = NAME_PREFIX + "phpuservar_pro.gif"; //$NON-NLS-1$
	public static final String IMG_FIELD_PRIVATE = NAME_PREFIX + "phpuservar_pri.gif"; //$NON-NLS-1$
	public static final String IMG_FIELD_DEFAULT = NAME_PREFIX + "phpuservar_pub.gif"; //$NON-NLS-1$
	public static final String IMG_OBJS_GHOST = NAME_PREFIX + "ghost.gif"; //$NON-NLS-1$
	public static final String IMG_OBJS_CLASS = NAME_PREFIX + "phpclassdata.gif"; //$NON-NLS-1$
	public static final String IMG_OBJS_CLASSALT = NAME_PREFIX + "phpclassdata.gif"; //$NON-NLS-1$
	public static final String IMG_OBJS_CLASS_DEFAULT = NAME_PREFIX + "phpclassdata.gif"; //$NON-NLS-1$
	public static final String IMG_OBJS_INTERFACE = NAME_PREFIX + "phpinterfacedata.gif"; //$NON-NLS-1$
	public static final String IMG_CONSTANT = NAME_PREFIX + "phpconstantdata.gif"; //$NON-NLS-1$
	public static final String IMG_KEYWORD = NAME_PREFIX + "phpkeyword.gif"; //$NON-NLS-1$
	public static final String IMG_OBJS_CUNIT = NAME_PREFIX + "phpfile.gif"; //$NON-NLS-1$
	public static final String IMG_OBJS_INCLUDE = NAME_PREFIX + "include_file.gif"; //$NON-NLS-1$
	public static final String IMG_OBJS_CUNIT_RESOURCE = NAME_PREFIX + "jcu_resource_obj.gif"; //$NON-NLS-1$
	public static final String IMG_OBJS_PHP_FOLDER = NAME_PREFIX + "script_folder_obj.gif"; //$NON-NLS-1$
	public static final String IMG_OBJS_EMPTY_PACK_RESOURCE = NAME_PREFIX + "empty_pack_fldr_obj.gif"; //$NON-NLS-1$
	public static final String IMG_OBJS_EMPTY_PHP_FOLDER = NAME_PREFIX + "empty_pack_obj.gif"; //$NON-NLS-1$
	public static final String IMG_OBJS_PHPFOLDER_ROOT = NAME_PREFIX + "folderroot_obj.gif"; //$NON-NLS-1$
	public static final String IMG_OBJS_MISSING_PHPFOLDER_ROOT = NAME_PREFIX + "folderroot_nonexist_obj.gif"; //$NON-NLS-1$
	public static final String IMG_OBJS_PHP_MODEL = NAME_PREFIX + "script_model_obj.gif"; //$NON-NLS-1$
	public static final String IMG_OBJS_UNKNOWN = NAME_PREFIX + "unknown_obj.gif"; //$NON-NLS-1$
	public static final String IMG_OBJS_ADD_PHP_PROJECT = NAME_PREFIX + "add_php_project.gif"; //$NON-NLS-1$
	public static final String IMG_OBJS_ADD_PHP_FILE = NAME_PREFIX + "add_php_file.gif"; //$NON-NLS-1$
	public static final String IMG_OBJS_ADD_PHP_APP = NAME_PREFIX + "add_php_app.gif"; //$NON-NLS-1$
	public static final String IMG_OBJS_PHP_SEARCH = NAME_PREFIX + "php_search_obj.gif"; //$NON-NLS-1$
	public static final String IMG_OBJS_PHP_APP = NAME_PREFIX + "php_app.gif"; //$NON-NLS-1$
	public static final String IMG_OBJS_PHP_PROJECT = NAME_PREFIX + "php_project_obj.gif"; //$NON-NLS-1$
	public static final String IMG_OBJS_PHP_CLASSES_GROUP = NAME_PREFIX + "class_group.gif"; //$NON-NLS-1$
	public static final String IMG_OBJS_PHP_CONSTANTS_GROUP = NAME_PREFIX + "const_group.gif"; //$NON-NLS-1$
	public static final String IMG_OBJS_PHP_FUNCTIONS_GROUP = NAME_PREFIX + "func_group.gif"; //$NON-NLS-1$

	public static final String IMG_OBJS_ZIP = NAME_PREFIX + "zip_obj.gif"; //$NON-NLS-1$
	public static final String IMG_OBJS_EXTZIP = NAME_PREFIX + "zip_l_obj.gif"; //$NON-NLS-1$
	public static final String IMG_OBJS_ENV_VAR = NAME_PREFIX + "envvar_obj.gif"; //$NON-NLS-1$
	public static final String IMG_OBJS_MISSING_ENV_VAR = NAME_PREFIX + "envvar_nonexist_obj.gif"; //$NON-NLS-1$
	public static final String IMG_OBJS_LIBRARY = NAME_PREFIX + "library_obj.gif"; //$NON-NLS-1$

	public static final String IMG_TEMPLATE = NAME_PREFIX + "phptpldata16.gif"; //$NON-NLS-1$

	public static final ImageDescriptor DESC_MISC_PUBLIC = createManaged(T_OBJ, IMG_MISC_PUBLIC);
	public static final ImageDescriptor DESC_MISC_PROTECTED = createManaged(T_OBJ, IMG_MISC_PROTECTED);
	public static final ImageDescriptor DESC_MISC_PRIVATE = createManaged(T_OBJ, IMG_MISC_PRIVATE);
	public static final ImageDescriptor DESC_MISC_DEFAULT = createManaged(T_OBJ, IMG_MISC_DEFAULT);

	public static final ImageDescriptor DESC_FIELD_PUBLIC = createManaged(T_OBJ, IMG_FIELD_PUBLIC);
	public static final ImageDescriptor DESC_FIELD_PROTECTED = createManaged(T_OBJ, IMG_FIELD_PROTECTED);
	public static final ImageDescriptor DESC_FIELD_PRIVATE = createManaged(T_OBJ, IMG_FIELD_PRIVATE);
	public static final ImageDescriptor DESC_FIELD_DEFAULT = createManaged(T_OBJ, IMG_FIELD_DEFAULT);
	public static final ImageDescriptor DESC_OBJS_GHOST = createManaged(T_OBJ, IMG_OBJS_GHOST);
	public static final ImageDescriptor DESC_OBJS_CUNIT = createManaged(T_OBJ, IMG_OBJS_CUNIT);
	public static final ImageDescriptor DESC_OBJS_INCLUDE = createManaged(T_OBJ, IMG_OBJS_INCLUDE);
	public static final ImageDescriptor DESC_OBJS_CUNIT_RESOURCE = createManaged(T_OBJ, IMG_OBJS_CUNIT_RESOURCE);
	public static final ImageDescriptor DESC_OBJS_PHP_FOLDER = createManaged(T_OBJ, IMG_OBJS_PHP_FOLDER);
	public static final ImageDescriptor DESC_OBJS_EMPTY_PHP_FOLDER_RESOURCES = createManaged(T_OBJ, IMG_OBJS_EMPTY_PACK_RESOURCE);
	public static final ImageDescriptor DESC_OBJS_EMPTY_PHP_FOLDER = createManaged(T_OBJ, IMG_OBJS_EMPTY_PHP_FOLDER);
	public static final ImageDescriptor DESC_OBJS_PHPFOLDER_ROOT = createManaged(T_OBJ, IMG_OBJS_PHPFOLDER_ROOT);
	public static final ImageDescriptor DESC_OBJS_MISSING_PHPFOLDER_ROOT = createManaged(T_OBJ, IMG_OBJS_MISSING_PHPFOLDER_ROOT);
	public static final ImageDescriptor DESC_OBJS_PHP_MODEL = createManaged(T_OBJ, IMG_OBJS_PHP_MODEL);
	public static final ImageDescriptor DESC_OBJ_PHP_CLASSES_GROUP = createManaged(T_OBJ, IMG_OBJS_PHP_CLASSES_GROUP);
	public static final ImageDescriptor DESC_OBJ_PHP_CONSTANTS_GROUP = createManaged(T_OBJ, IMG_OBJS_PHP_CONSTANTS_GROUP);
	public static final ImageDescriptor DESC_OBJ_PHP_FUNCTIONS_GROUP = createManaged(T_OBJ, IMG_OBJS_PHP_FUNCTIONS_GROUP);
	//
	public static final ImageDescriptor DESC_OBJS_CLASS = createManaged(T_OBJ, IMG_OBJS_CLASS);
	public static final ImageDescriptor DESC_OBJS_CLASS_DEFAULT = createManaged(T_OBJ, IMG_OBJS_CLASS_DEFAULT);
	public static final ImageDescriptor DESC_OBJS_INTERFACE = createManaged(T_OBJ, IMG_OBJS_INTERFACE);
	public static final ImageDescriptor DESC_CONSTANT = createManaged(T_OBJ, IMG_CONSTANT);
	public static final ImageDescriptor DESC_KEYWORD = createManaged(T_OBJ, IMG_KEYWORD);
	public static final ImageDescriptor DESC_OBJS_CLASSALT = createManaged(T_OBJ, IMG_OBJS_CLASSALT);
	public static final ImageDescriptor DESC_OBJS_UNKNOWN = createManaged(T_OBJ, IMG_OBJS_UNKNOWN);
	public static final ImageDescriptor DESC_OBJS_WARNING = create(T_OBJ, "warning_obj.gif"); //$NON-NLS-1$
	public static final ImageDescriptor DESC_OVR_RUN = create(T_OVR, "run_co.gif"); //$NON-NLS-1$
	public static final ImageDescriptor DESC_OVR_WARNING = create(T_OVR, "warning_co.gif"); //$NON-NLS-1$
	public static final ImageDescriptor DESC_OVR_ERROR = create(T_OVR, "error_co.gif"); //$NON-NLS-1$
	public static final ImageDescriptor DESC_OVR_OVERRIDES = create(T_OVR, "over_co.gif"); //$NON-NLS-1$
	public static final ImageDescriptor DESC_OVR_CONSTRUCTOR = create(T_OVR, "constr_ovr.gif"); //$NON-NLS-1$
	public static final ImageDescriptor DESC_OVR_DEPRECATED = create(T_OVR, "deprecated.gif"); //$NON-NLS-1$
	public static final ImageDescriptor DESC_OVR_CONSTANT = create(T_OVR, "constant_co.gif"); //$NON-NLS-1$ 	
	public static final ImageDescriptor DESC_OVR_ABSTRACT = create(T_OVR, "abstract_co.gif"); //$NON-NLS-1$ 	
	public static final ImageDescriptor DESC_OVR_FINAL = create(T_OVR, "final_co.gif"); //$NON-NLS-1$ 	
	public static final ImageDescriptor DESC_OVR_STATIC = create(T_OVR, "static_co.gif"); //$NON-NLS-1$ 	

	public static final ImageDescriptor DESC_OBJS_ZIP = createManaged(T_OBJ, IMG_OBJS_ZIP);
	public static final ImageDescriptor DESC_OBJS_EXTZIP = createManaged(T_OBJ, IMG_OBJS_EXTZIP);
	public static final ImageDescriptor DESC_OBJS_LIBRARY = createManaged(T_OBJ, IMG_OBJS_LIBRARY);
	public static final ImageDescriptor DESC_OBJS_ENV_VAR = createManaged(T_OBJ, IMG_OBJS_ENV_VAR);
	public static final ImageDescriptor DESC_OBJS_MISSING_ENV_VAR = createManaged(T_OBJ, IMG_OBJS_MISSING_ENV_VAR);

	public static final ImageDescriptor DESC_OBJS_ADD_PHP_PROJECT = createManaged(T_OBJ, IMG_OBJS_ADD_PHP_PROJECT);
	public static final ImageDescriptor DESC_OBJS_ADD_PHP_FILE = createManaged(T_OBJ, IMG_OBJS_ADD_PHP_FILE);
	public static final ImageDescriptor DESC_OBJS_ADD_PHP_APP = createManaged(T_OBJ, IMG_OBJS_ADD_PHP_APP);
	public static final ImageDescriptor DESC_OBJS_PHP_SEARCH = createManaged(T_OBJ, IMG_OBJS_PHP_SEARCH);
	public static final ImageDescriptor DESC_OBJS_PHP_APP = createManaged(T_OBJ, IMG_OBJS_PHP_APP);
	public static final ImageDescriptor DESC_OBJS_PHP_PROJECT = createManaged(T_OBJ, IMG_OBJS_PHP_PROJECT);

	public static final ImageDescriptor DESC_ELCL_FILTER = create(T_ELCL, "filter_ps.gif"); //$NON-NLS-1$
	public static final ImageDescriptor DESC_DLCL_FILTER = create(T_DLCL, "filter_ps.gif"); //$NON-NLS-1$

	public static final ImageDescriptor DESC_WIZBAN_ADD_LIBRARY = create(T_WIZBAN, "addlibrary_wiz.gif");//$NON-NLS-1$
	public static final ImageDescriptor DESC_WIZBAN_ADD_PHP_PROJECT = create(T_WIZBAN, "newphpprj_wiz.gif");//$NON-NLS-1$
	public static final ImageDescriptor DESC_WIZBAN_ADD_PHP_FILE = create(T_WIZBAN, "newpfile_wiz.gif");//$NON-NLS-1$
	public static final ImageDescriptor DESC_TOOL_INCLUDEPATH_ORDER = create(T_OBJ, "cp_order_obj.gif"); //$NON-NLS-1$

	public static final ImageDescriptor DESC_TEMPLATE = createManaged(CLASS_BROWSER, IMG_TEMPLATE);//$NON-NLS-1$

	/**
	 * Returns the image managed under the given key in this registry.
	 *
	 * @param key the image's key
	 * @return the image managed under the given key
	 */
	public static Image get(String key) {
		return getImageRegistry().get(key);
	}

	/**
	 * Sets the three image descriptors for enabled, disabled, and hovered to an action. The actions
	 * are retrieved from the *tool16 folders.
	 *
	 * @param action	the action
	 * @param iconName	the icon name
	 */
	public static void setToolImageDescriptors(IAction action, String iconName) {
		setImageDescriptors(action, "tool16", iconName); //$NON-NLS-1$
	}

	/**
	 * Sets the three image descriptors for enabled, disabled, and hovered to an action. The actions
	 * are retrieved from the *lcl16 folders.
	 *
	 * @param action	the action
	 * @param iconName	the icon name
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
			for (Iterator iter = fgAvoidSWTErrorMap.keySet().iterator(); iter.hasNext();) {
				String key = (String) iter.next();
				fgImageRegistry.put(key, (ImageDescriptor) fgAvoidSWTErrorMap.get(key));
			}
			fgAvoidSWTErrorMap = null;
		}
		return fgImageRegistry;
	}

	//---- Helper methods to access icons on the file system --------------------------------------

	private static void setImageDescriptors(IAction action, String type, String relPath) {

		try {
			ImageDescriptor id = ImageDescriptor.createFromURL(makeIconFileURL("d" + type, relPath)); //$NON-NLS-1$
			if (id != null)
				action.setDisabledImageDescriptor(id);
		} catch (MalformedURLException e) {
		}

		/*
		 try {
		 ImageDescriptor id= ImageDescriptor.createFromURL(makeIconFileURL("c" + type, relPath)); //$NON-NLS-1$
		 if (id != null)
		 action.setHoverImageDescriptor(id);
		 } catch (MalformedURLException e) {
		 }
		 */

		ImageDescriptor descriptor = create("e" + type, relPath); //$NON-NLS-1$
		action.setHoverImageDescriptor(descriptor);
		action.setImageDescriptor(descriptor);
	}

	private static ImageDescriptor createManaged(String prefix, String name) {
		return createManaged(prefix, name, false, 0, null);
	}

	private static ImageDescriptor createManaged(String prefix, String name, boolean createAsComposite, int flags, Point size) {
		try {
			ImageDescriptor result = ImageDescriptor.createFromURL(makeIconFileURL(prefix, name.substring(NAME_PREFIX_LENGTH)));
			if (createAsComposite) {
				result = new PHPElementImageDescriptor(result, flags, size);
			}
			if (fgAvoidSWTErrorMap == null) {
				fgAvoidSWTErrorMap = new HashMap();
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

	private static ImageDescriptor createManaged(String prefix, String name, String key) {
		try {
			ImageDescriptor result = ImageDescriptor.createFromURL(makeIconFileURL(prefix, name.substring(NAME_PREFIX_LENGTH)));
			if (fgAvoidSWTErrorMap == null) {
				fgAvoidSWTErrorMap = new HashMap();
			}
			fgAvoidSWTErrorMap.put(key, result);
			if (fgImageRegistry != null) {
				PHPUiPlugin.logErrorMessage("Image registry already defined"); //$NON-NLS-1$
			}
			return result;
		} catch (MalformedURLException e) {
			return ImageDescriptor.getMissingImageDescriptor();
		}
	}

	private static ImageDescriptor create(String prefix, String name) {
		try {
			return ImageDescriptor.createFromURL(makeIconFileURL(prefix, name));
		} catch (MalformedURLException e) {
			return ImageDescriptor.getMissingImageDescriptor();
		}
	}

	private static URL makeIconFileURL(String prefix, String name) throws MalformedURLException {
		if (fgIconBaseURL == null)
			throw new MalformedURLException();

		StringBuffer buffer = new StringBuffer(prefix);
		buffer.append('/');
		buffer.append(name);
		return new URL(fgIconBaseURL, buffer.toString());
	}
}
