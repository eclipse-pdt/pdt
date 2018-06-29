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
package org.eclipse.php.composer.core.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.php.composer.core.ComposerPlugin;
import org.eclipse.php.composer.core.ComposerPreferenceConstants;

public class PreferenceInitializer extends AbstractPreferenceInitializer {

	@Override
	public void initializeDefaultPreferences() {
		IEclipsePreferences scope = DefaultScope.INSTANCE.getNode(ComposerPlugin.ID);
		scope.put(ComposerPreferenceConstants.PHP_EXECUTABLE, ""); //$NON-NLS-1$
		scope.putBoolean(ComposerPreferenceConstants.USE_PROJECT_PHAR, true);
		scope.put(ComposerPreferenceConstants.COMPOSER_PHAR, ""); //$NON-NLS-1$
		scope.putBoolean(ComposerPreferenceConstants.SAVEACTION_BUILDPATH, false);
		scope.putBoolean(ComposerPreferenceConstants.SAVEACTION_UPDATE, false);
	}
}
