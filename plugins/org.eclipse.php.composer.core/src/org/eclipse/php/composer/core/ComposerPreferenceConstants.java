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

public class ComposerPreferenceConstants {

	/**
	 * Which php executable is used for the composer eclipse plugin
	 */
	public static final String PHP_EXECUTABLE = ComposerPlugin.ID + "php_executable"; //$NON-NLS-1$

	/**
	 * Path to the composer.phar
	 */
	public static final String COMPOSER_PHAR = ComposerPlugin.ID + "composer_phar"; //$NON-NLS-1$

	/**
	 * Whether a global composer.phar is used or a project-scope composer.phar
	 */
	public static final String USE_PROJECT_PHAR = ComposerPlugin.ID + "use_project_phar"; //$NON-NLS-1$

	/**
	 * Buildpath includes rsp. excludes items either per project or global
	 */
	public static final String BUILDPATH_INCLUDES_EXCLUDES = ComposerPlugin.ID + "buildpath.includes.excludes"; //$NON-NLS-1$

	/**
	 * Shall the buildpath be updated after saving a composer.json
	 */
	public static final String SAVEACTION_BUILDPATH = ComposerPlugin.ID + "saveaction.buildpath"; //$NON-NLS-1$

	/**
	 * Shall `composer.phar update` being run after saving a composer.json
	 */
	public static final String SAVEACTION_UPDATE = ComposerPlugin.ID + "saveaction.update"; //$NON-NLS-1$

}
