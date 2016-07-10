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
package org.eclipse.php.composer.core;

public class ComposerPluginConstants {
	
	// old deprecated stuff
	public static final String searchURL = "http://packagist.org/search.json?q=%s";
	public static final String PREF_BUILDPATH_ENABLE = "prefs.composer.buildpath.enable";

	public static final String PREF_ENVIRONMENT = "prefs.composer.environment";

	public static final String COMPOSER_FACET = "php.composer.component";
	public static final String COMPOSER = "composer.json";
	
	// not quite sure, what this is
	public static final String AUTOLOAD_NAMESPACES = "autoload_namespaces.php";
	
	/**
	 * Default src folder constant for creating new composer projects
	 * (used in new composer project wizards)
	 */
	public static final String DEFAULT_SRC_FOLDER = "src";


}
