/*******************************************************************************
 * Copyright (c) 2012, 2016 PDT Extension Group and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.ui.preferences;

import org.eclipse.php.composer.core.ComposerPlugin;
import org.eclipse.php.composer.core.ComposerPreferenceConstants;
import org.eclipse.php.composer.ui.preferences.launcher.LauncherKeyBag;
import org.eclipse.php.internal.ui.preferences.util.Key;

public class ComposerLauncherBag implements LauncherKeyBag {

	private final Key exeKey = new Key(ComposerPlugin.ID, ComposerPreferenceConstants.PHP_EXECUTABLE);
	private final Key pharKey = new Key(ComposerPlugin.ID, ComposerPreferenceConstants.COMPOSER_PHAR);
	private final Key useKey = new Key(ComposerPlugin.ID, ComposerPreferenceConstants.USE_PROJECT_PHAR);

	@Override
	public Key[] getAllKeys() {
		return new Key[] { exeKey, pharKey, useKey };
	}

	@Override
	public Key getPHPExecutableKey() {
		return exeKey;
	}

	@Override
	public Key getScriptKey() {
		return pharKey;
	}

	@Override
	public Key getUseProjectKey() {
		return useKey;
	}
}
