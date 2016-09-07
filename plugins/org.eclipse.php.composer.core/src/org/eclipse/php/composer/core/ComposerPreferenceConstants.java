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

public class ComposerPreferenceConstants {

	/**
	 * Which php executable is used for the composer eclipse plugin
	 */
	public static final String PHP_EXECUTABLE = ComposerPlugin.ID + "php_executable";

	/**
	 * Path to the composer.phar
	 */
	public static final String COMPOSER_PHAR = ComposerPlugin.ID + "composer_phar";

	/**
	 * Whether a global composer.phar is used or a project-scope composer.phar
	 */
	public static final String USE_PROJECT_PHAR = ComposerPlugin.ID + "use_project_phar";

	/**
	 * Buildpath includes rsp. excludes items either per project or global
	 */
	public static final String BUILDPATH_INCLUDES_EXCLUDES = ComposerPlugin.ID + "buildpath.includes.excludes";

	/**
	 * Shall the buildpath be updated after saving a composer.json
	 */
	public static final String SAVEACTION_BUILDPATH = ComposerPlugin.ID + "saveaction.buildpath";

	/**
	 * Shall `composer.phar update` being run after saving a composer.json
	 */
	public static final String SAVEACTION_UPDATE = ComposerPlugin.ID + "saveaction.update";

}
