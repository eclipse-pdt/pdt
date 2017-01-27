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
package org.eclipse.php.composer.core.launch.environment;

import org.eclipse.php.composer.core.ComposerPlugin;
import org.eclipse.php.composer.core.ComposerPreferenceConstants;

public class ComposerEnvironmentFactory extends AbstractEnvironmentFactory {

	public static final String FACTORY_ID = "org.eclipse.php.composer.core.launcherfactory"; //$NON-NLS-1$

	@Override
	protected String getPluginId() {
		return ComposerPlugin.ID;
	}

	@Override
	protected PrjPharEnvironment getProjectEnvironment(String executable) {
		return new SysPhpPrjPhar(executable);
	}

	@Override
	protected String getExecutableKey() {
		return ComposerPreferenceConstants.PHP_EXECUTABLE;
	}

	@Override
	protected String getUseProjectKey() {
		return ComposerPreferenceConstants.USE_PROJECT_PHAR;
	}

	@Override
	protected String getScriptKey() {
		return ComposerPreferenceConstants.COMPOSER_PHAR;
	}

	@Override
	protected String getPreferenceQualifier() {
		return ComposerPlugin.ID;
	}
}
