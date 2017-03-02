/*******************************************************************************
 * Copyright (c) 2012, 2016, 2017 PDT Extension Group and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *     Kaloyan Raev - [501269] externalize strings
 *******************************************************************************/
package org.eclipse.php.composer.core;

public class ComposerPluginConstants {

	// old deprecated stuff
	public static final String searchURL = "http://packagist.org/search.json?q=%s"; //$NON-NLS-1$
	public static final String PREF_BUILDPATH_ENABLE = "prefs.composer.buildpath.enable"; //$NON-NLS-1$

	public static final String PREF_ENVIRONMENT = "prefs.composer.environment"; //$NON-NLS-1$

	public static final String COMPOSER_FACET = "php.composer.component"; //$NON-NLS-1$
	public static final String COMPOSER = "composer.json"; //$NON-NLS-1$

	// not quite sure, what this is
	public static final String AUTOLOAD_NAMESPACES = "autoload_namespaces.php"; //$NON-NLS-1$

	/**
	 * Default src folder constant for creating new composer projects (used in
	 * new composer project wizards)
	 */
	public static final String DEFAULT_SRC_FOLDER = "src"; //$NON-NLS-1$

	/**
	 * Composer build path attribute name
	 * 
	 * @since 4.3
	 */
	public static final String BPE_ATTR_NAME = "composer"; //$NON-NLS-1$

	/**
	 * Composer build path attribute source value
	 * 
	 * @since 4.3
	 */
	public static final String BPE_ATTR_SOURCE = "source"; //$NON-NLS-1$

	/**
	 * Composer build path attribute vendor value
	 * 
	 * @since 4.3
	 */
	public static final String BPE_ATTR_VENDOR = "vendor"; //$NON-NLS-1$

}
